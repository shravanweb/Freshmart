import { PublicProductRecord } from "./LandingCatalogApi";

export interface CartItem {
  productId: number;
  name: string;
  sellingPrice: number;
  imageUrl?: string;
  quantity: number;
  quantityAvailable: number;
}

export interface CartMutationResult {
  success: boolean;
  message?: string;
  quantity: number;
}

const STORAGE_KEY = "freshmart_cart";
const UNKNOWN_STOCK_LIMIT = 99;

type CartListener = () => void;

function productImage(product: PublicProductRecord): string | undefined {
  if (product.imageUrls && product.imageUrls.length > 0) {
    return product.imageUrls[0];
  }
  return product.imageUrl;
}

export default class CartSession {
  private static listeners: CartListener[] = [];

  public static onChange(listener: CartListener): () => void {
    CartSession.listeners.push(listener);
    return () => {
      CartSession.listeners = CartSession.listeners.filter((item) => item !== listener);
    };
  }

  private static notify(): void {
    CartSession.listeners.forEach((listener) => listener());
  }

  public static getItems(): CartItem[] {
    if (typeof window === "undefined") {
      return [];
    }

    try {
      const raw = localStorage.getItem(STORAGE_KEY);
      if (!raw) {
        return [];
      }

      const parsed = JSON.parse(raw) as unknown;
      if (!Array.isArray(parsed)) {
        return [];
      }

      const items: CartItem[] = [];
      for (const entry of parsed) {
        if (!entry || typeof entry !== "object") {
          continue;
        }
        const row = entry as Record<string, unknown>;
        const productId = Number(row.productId);
        const quantity = Number(row.quantity);
        if (!Number.isFinite(productId) || productId <= 0) {
          continue;
        }
        if (!Number.isFinite(quantity) || quantity <= 0) {
          continue;
        }
        items.push({
          productId,
          name: String(row.name ?? ""),
          sellingPrice: Number(row.sellingPrice ?? 0),
          imageUrl: row.imageUrl != null ? String(row.imageUrl) : undefined,
          quantity,
          quantityAvailable: Number(row.quantityAvailable ?? 0),
        });
      }
      return items;
    } catch {
      return [];
    }
  }

  public static getQuantity(productId: number): number {
    const item = CartSession.getItems().find((entry) => entry.productId === productId);
    return item?.quantity ?? 0;
  }

  public static getTotalCount(): number {
    return CartSession.getItems().reduce((sum, item) => sum + item.quantity, 0);
  }

  public static hasStockData(product: PublicProductRecord): boolean {
    return (
      product.quantityAvailable != null && Number.isFinite(Number(product.quantityAvailable))
    );
  }

  public static stockLimit(product: PublicProductRecord): number | null {
    if (!CartSession.hasStockData(product)) {
      return null;
    }
    return Math.max(0, Math.floor(Number(product.quantityAvailable)));
  }

  public static isOutOfStock(product: PublicProductRecord): boolean {
    const limit = CartSession.stockLimit(product);
    return limit !== null && limit <= 0;
  }

  public static availableStock(product: PublicProductRecord): number {
    const limit = CartSession.stockLimit(product);
    if (limit === null) {
      return UNKNOWN_STOCK_LIMIT;
    }
    return limit;
  }

  public static maxAddable(product: PublicProductRecord): number {
    const inCart = CartSession.getQuantity(product.id);
    return Math.max(0, CartSession.availableStock(product) - inCart);
  }

  public static addProduct(
    product: PublicProductRecord,
    quantity = 1
  ): CartMutationResult {
    if (CartSession.isOutOfStock(product)) {
      return {
        success: false,
        message: "Out of stock",
        quantity: CartSession.getQuantity(product.id),
      };
    }

    const maxAddable = CartSession.maxAddable(product);
    if (maxAddable <= 0) {
      return {
        success: false,
        message: "Only " + CartSession.availableStock(product) + " available",
        quantity: CartSession.getQuantity(product.id),
      };
    }

    const addQty = Math.min(Math.max(1, Math.floor(quantity)), maxAddable);
    const items = CartSession.getItems();
    const index = items.findIndex((item) => item.productId === product.id);
    const stock = CartSession.availableStock(product);

    if (index >= 0) {
      items[index] = {
        ...items[index],
        name: product.name,
        sellingPrice: product.sellingPrice,
        imageUrl: productImage(product),
        quantity: items[index].quantity + addQty,
        quantityAvailable: stock,
      };
    } else {
      items.push({
        productId: product.id,
        name: product.name,
        sellingPrice: product.sellingPrice,
        imageUrl: productImage(product),
        quantity: addQty,
        quantityAvailable: stock,
      });
    }

    CartSession.save(items);
    return {
      success: true,
      quantity: CartSession.getQuantity(product.id),
    };
  }

  public static setQuantity(
    productId: number,
    quantity: number,
    product?: PublicProductRecord
  ): CartMutationResult {
    const items = CartSession.getItems();
    const index = items.findIndex((item) => item.productId === productId);

    if (index < 0) {
      if (!product || quantity <= 0) {
        return { success: false, message: "Item not in cart", quantity: 0 };
      }
      return CartSession.addProduct(product, quantity);
    }

    if (product && CartSession.isOutOfStock(product)) {
      items.splice(index, 1);
      CartSession.save(items);
      return {
        success: false,
        message: "Out of stock",
        quantity: 0,
      };
    }

    const stock =
      product != null
        ? CartSession.availableStock(product)
        : Math.max(1, Math.floor(items[index].quantityAvailable));

    if (quantity <= 0) {
      items.splice(index, 1);
      CartSession.save(items);
      return { success: true, quantity: 0 };
    }

    const nextQty = Math.min(Math.floor(quantity), stock);
    if (nextQty <= 0) {
      items.splice(index, 1);
      CartSession.save(items);
      return {
        success: false,
        message: "Out of stock",
        quantity: 0,
      };
    }

    items[index] = {
      ...items[index],
      quantity: nextQty,
      quantityAvailable: stock,
      ...(product
        ? {
            name: product.name,
            sellingPrice: product.sellingPrice,
            imageUrl: productImage(product),
          }
        : {}),
    };

    CartSession.save(items);
    return { success: true, quantity: nextQty };
  }

  public static adjustQuantity(
    product: PublicProductRecord,
    delta: number
  ): CartMutationResult {
    const current = CartSession.getQuantity(product.id);
    if (current <= 0 && delta > 0) {
      return CartSession.addProduct(product, 1);
    }
    return CartSession.setQuantity(product.id, current + delta, product);
  }

  public static clear(): void {
    if (typeof window === "undefined") {
      return;
    }
    localStorage.removeItem(STORAGE_KEY);
    CartSession.notify();
  }

  private static save(items: CartItem[]): void {
    if (typeof window === "undefined") {
      return;
    }
    localStorage.setItem(STORAGE_KEY, JSON.stringify(items));
    CartSession.notify();
  }
}
