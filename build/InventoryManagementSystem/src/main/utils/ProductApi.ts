import { gql } from "@apollo/client";
import Env from "../classes/Env";
import GraphQLClientInit from "./GraphQLClientInit";
import LocalDataStore from "./LocalDataStore";

export interface ProductRecord {
  id: number;
  sku: string;
  name: string;
  description: string;
  barcode: string;
  purchasePrice: number;
  sellingPrice: number;
  reorderLevel: number;
  reorderQuantity: number;
  status: string;
  categoryId: number;
  categoryName: string;
}

export interface ProductInput {
  id?: number;
  sku: string;
  name: string;
  description?: string;
  barcode?: string;
  purchasePrice?: number;
  sellingPrice?: number;
  reorderLevel?: number;
  reorderQuantity?: number;
  status?: string;
  organization: number;
  category?: number;
  baseUom?: number;
}

export interface ProductMutationResult {
  success: boolean;
  errors: string[];
  product: ProductRecord | null;
}

function enumName(value: unknown): string {
  if (value == null) return "";
  if (typeof value === "string") return value;
  if (typeof value === "object" && "name" in (value as Record<string, unknown>)) {
    return String((value as { name: string }).name ?? "");
  }
  return String(value);
}

function parseId(value: unknown): number {
  if (value == null) return 0;
  if (typeof value === "number" && Number.isFinite(value)) return value;
  if (typeof value === "string" && value.trim() !== "") {
    const parsed = Number(value);
    return Number.isFinite(parsed) ? parsed : 0;
  }
  return 0;
}

function parseNumber(value: unknown): number {
  if (value == null) return 0;
  const parsed = Number(value);
  return Number.isFinite(parsed) ? parsed : 0;
}

function resolveImageUrl(url: string): string {
  const trimmed = (url ?? "").trim();
  if (!trimmed) return "";
  if (
    trimmed.startsWith("http://") ||
    trimmed.startsWith("https://") ||
    trimmed.startsWith("blob:")
  ) {
    return trimmed;
  }
  const base = Env.get().resolvedHttpUrl.replace(/\/$/, "");
  return trimmed.startsWith("/") ? `${base}${trimmed}` : `${base}/${trimmed}`;
}

function mapProduct(raw: Record<string, unknown>): ProductRecord {
  const category = (raw.category ?? {}) as Record<string, unknown>;
  return {
    id: parseId(raw.id),
    sku: String(raw.sku ?? ""),
    name: String(raw.name ?? ""),
    description: String(raw.description ?? ""),
    barcode: String(raw.barcode ?? ""),
    purchasePrice: parseNumber(raw.purchasePrice),
    sellingPrice: parseNumber(raw.sellingPrice),
    reorderLevel: parseNumber(raw.reorderLevel),
    reorderQuantity: parseNumber(raw.reorderQuantity),
    status: enumName(raw.status),
    categoryId: parseId(category.id),
    categoryName: String(category.name ?? ""),
  };
}

const PRODUCT_FIELDS = `
  id
  sku
  name
  description
  barcode
  purchasePrice
  sellingPrice
  reorderLevel
  reorderQuantity
  status
  category {
    id
    name
  }
`;

export default class ProductApi {
  private static async client() {
    GraphQLClientInit.token = await LocalDataStore.get().getToken();
    return GraphQLClientInit.get();
  }

  private static async resolveDefaults(organizationId: number): Promise<{
    categoryId: number | null;
    baseUomId: number | null;
  }> {
    const client = await ProductApi.client();
    const { data } = await client.query({
      query: gql`
        query ProductDefaults($organization: Long) {
          categories: getAllProductCategories(in: { organization: $organization }) {
            items { id }
          }
          uoms: getAllUnitOfMeasures(in: { organization: $organization }) {
            items { id }
          }
        }
      `,
      variables: { organization: organizationId },
      fetchPolicy: "no-cache",
    });
    return {
      categoryId: data?.categories?.items?.[0]?.id ?? null,
      baseUomId: data?.uoms?.items?.[0]?.id ?? null,
    };
  }

  public static async getAllProducts(organizationId: number): Promise<ProductRecord[]> {
    const client = await ProductApi.client();
    const { data, errors } = await client.query({
      query: gql`
        query GetAllProducts($organization: Long) {
          getAllProducts(in: { organization: $organization }) {
            items { ${PRODUCT_FIELDS} }
          }
        }
      `,
      variables: { organization: organizationId },
      fetchPolicy: "no-cache",
    });
    if (errors?.length) {
      throw new Error(errors.map((e) => e.message).join("; "));
    }
    const items = data?.getAllProducts?.items ?? [];
    return items.map((item: Record<string, unknown>) => mapProduct(item));
  }

  public static async getProductsByVendor(
    organizationId: number,
    vendorId: number
  ): Promise<ProductRecord[]> {
    const token = await LocalDataStore.get().getToken();
    const response = await fetch(
      `${Env.get().resolvedHttpUrl}/api/products-by-vendor?organizationId=${encodeURIComponent(String(organizationId))}&vendorId=${encodeURIComponent(String(vendorId))}`,
      {
        method: "GET",
        headers: token ? { Authorization: `Bearer ${token}` } : {},
      }
    );
    if (!response.ok) {
      const message = await response.text();
      throw new Error(
        message || `Failed to load supplier products (${response.status})`
      );
    }
    const data = (await response.json()) as { items?: Record<string, unknown>[] };
    const items = data.items ?? [];
    return Array.from(items, (item: Record<string, unknown>) => mapProduct(item));
  }

  public static async assignProductsToVendor(
    organizationId: number,
    vendorId: number,
    productIds: number[]
  ): Promise<{ success: boolean; errors: string[] }> {
    const token = await LocalDataStore.get().getToken();
    const response = await fetch(`${Env.get().resolvedHttpUrl}/api/vendor-products`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        ...(token ? { Authorization: `Bearer ${token}` } : {}),
      },
      body: JSON.stringify({
        organizationId,
        vendorId,
        productIds,
      }),
    });
    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || `Failed to save supplier products (${response.status})`);
    }
    const data = (await response.json()) as { success?: boolean; errors?: string[] };
    return {
      success: data.success === true,
      errors: data.errors ?? [],
    };
  }

  public static async createProduct(input: ProductInput): Promise<ProductMutationResult> {
    return ProductApi.mutateProduct("createProduct", input);
  }

  public static async updateProduct(input: ProductInput): Promise<ProductMutationResult> {
    return ProductApi.mutateProduct("updateProduct", input);
  }

  public static async getProductImages(productId: number): Promise<string[]> {
    const token = await LocalDataStore.get().getToken();
    const response = await fetch(
      `${Env.get().resolvedHttpUrl}/api/product-image?productId=${encodeURIComponent(String(productId))}`,
      {
        method: "GET",
        headers: token ? { Authorization: `Bearer ${token}` } : {},
      }
    );
    if (!response.ok) {
      return [];
    }
    const data = (await response.json()) as { items?: string[] };
    return (data.items ?? []).map((item) => resolveImageUrl(String(item)));
  }

  public static async uploadProductImages(
    productId: number,
    files: File[]
  ): Promise<string[]> {
    if (!files.length) {
      return [];
    }
    const token = await LocalDataStore.get().getToken();
    const formData = new FormData();
    formData.append("productId", String(productId));
    for (const file of files) {
      formData.append("file", file);
    }
    const response = await fetch(`${Env.get().resolvedHttpUrl}/api/product-image`, {
      method: "POST",
      headers: token ? { Authorization: `Bearer ${token}` } : {},
      body: formData,
    });
    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || `Image upload failed (${response.status})`);
    }
    const data = (await response.json()) as { items?: string[] };
    return (data.items ?? []).map((item) => resolveImageUrl(String(item)));
  }

  public static async deleteProductImage(
    productId: number,
    filename: string
  ): Promise<void> {
    const token = await LocalDataStore.get().getToken();
    const query =
      `productId=${encodeURIComponent(String(productId))}` +
      `&filename=${encodeURIComponent(filename)}`;
    const response = await fetch(`${Env.get().resolvedHttpUrl}/api/product-image?${query}`, {
      method: "DELETE",
      headers: token ? { Authorization: `Bearer ${token}` } : {},
    });
    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || `Image delete failed (${response.status})`);
    }
  }

  public static async deleteProduct(id: number): Promise<ProductMutationResult> {
    const client = await ProductApi.client();
    const { data, errors } = await client.mutate({
      mutation: gql`
        mutation DeleteProduct($id: Long) {
          deleteProduct(input: $id) { status errors }
        }
      `,
      variables: { id },
    });
    if (errors?.length) {
      return { success: false, errors: errors.map((e) => e.message), product: null };
    }
    const result = data?.deleteProduct;
    return {
      success: result?.status === "Success",
      errors: result?.errors ?? [],
      product: null,
    };
  }

  private static async mutateProduct(
    operation: "createProduct" | "updateProduct",
    input: ProductInput
  ): Promise<ProductMutationResult> {
    const client = await ProductApi.client();
    let category = input.category;
    let baseUom = input.baseUom;

    if (!category || !baseUom) {
      const defaults = await ProductApi.resolveDefaults(input.organization);
      category = category ?? defaults.categoryId ?? undefined;
      baseUom = baseUom ?? defaults.baseUomId ?? undefined;
    }

    if (!category || !baseUom) {
      return {
        success: false,
        errors: [
          "No product category or unit of measure found. Please add a category and UOM first.",
        ],
        product: null,
      };
    }

    const gqlInput: Record<string, unknown> = {
      sku: input.sku,
      name: input.name,
      status: input.status ?? "Active",
      organization: input.organization,
      category,
      baseUom,
      trackBatch: false,
      trackExpiry: false,
    };

    if (input.id != null && input.id > 0) gqlInput.id = input.id;
    if (input.description) gqlInput.description = input.description;
    if (input.barcode) gqlInput.barcode = input.barcode;
    if (input.purchasePrice != null) gqlInput.purchasePrice = input.purchasePrice;
    if (input.sellingPrice != null) gqlInput.sellingPrice = input.sellingPrice;
    if (input.reorderLevel != null) gqlInput.reorderLevel = input.reorderLevel;
    if (input.reorderQuantity != null) gqlInput.reorderQuantity = input.reorderQuantity;

    try {
      const { data, errors } = await client.mutate({
        mutation: gql`
          mutation MutateProduct($input: ProductEntityInput!) {
            ${operation}(input: $input) {
              status
              errors
              value { ${PRODUCT_FIELDS} }
            }
          }
        `,
        variables: { input: gqlInput },
        errorPolicy: "all",
      });

      if (errors?.length) {
        return { success: false, errors: errors.map((e) => e.message), product: null };
      }

      const result = data?.[operation];
      return {
        success: result?.status === "Success",
        errors: result?.errors ?? [],
        product: result?.value ? mapProduct(result.value) : null,
      };
    } catch (e: unknown) {
      const message = e instanceof Error ? e.message : String(e);
      return { success: false, errors: [message], product: null };
    }
  }
}
