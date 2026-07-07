import React, { ReactElement, ReactNode } from "react";
import CartSession from "../utils/CartSession";
import { PublicProductRecord } from "../utils/LandingCatalogApi";

const CAT_IMG = "/images/categories/";
const DEFAULT_CATEGORY_IMAGE = CAT_IMG + "default.jpg";
const INTERNAL_UOM_CODES = ["CS", "EA", "HR", "CTN", "BOX", "CASE"];

function extractPackFromName(name: string): string {
  const packMatch = name.match(/(\d+(?:\.\d+)?)\s*(ml|l|g|kg|gm|pcs|pc|pack|pkt)\b/i);
  if (packMatch) {
    return packMatch[0];
  }
  const commaPart = name.split(",").pop()?.trim() ?? "";
  if (commaPart.length > 0 && commaPart.length <= 28 && /\d/.test(commaPart)) {
    return commaPart;
  }
  return "";
}

function productSizeLabel(product: PublicProductRecord): string {
  const fromName = extractPackFromName(product.name);
  if (fromName) {
    return fromName;
  }
  const symbol = (product.uomSymbol ?? "").trim().toUpperCase();
  if (symbol && !INTERNAL_UOM_CODES.includes(symbol)) {
    return product.uomName ?? symbol;
  }
  return "";
}

export function formatCatalogPrice(value: number): string {
  if (!Number.isFinite(value) || value <= 0) {
    return "—";
  }
  return "₹" + (value % 1 === 0 ? value.toFixed(0) : value.toFixed(2));
}

export function resolveProductImage(product: PublicProductRecord): string {
  if (product.imageUrls && product.imageUrls.length > 0) {
    return product.imageUrls[0];
  }
  if (product.imageUrl) {
    return product.imageUrl;
  }
  if (product.categoryCode) {
    const slug = product.categoryCode.trim().toLowerCase().replace(/[^a-z0-9]+/g, "-");
    return CAT_IMG + slug + ".jpg";
  }
  return DEFAULT_CATEGORY_IMAGE;
}

export interface LandingProductGridProps {
  products: PublicProductRecord[];
  customerLoggedIn: boolean;
  wishlistIds: number[];
  showNewBadge?: boolean;
  gridClassName?: string;
  cardClassName?: string;
  onProductClick: (product: PublicProductRecord) => void;
  onToggleWishlist: (product: PublicProductRecord, event: React.MouseEvent) => void;
  onLogin: () => void;
}

function renderCartControl(
  product: PublicProductRecord,
  customerLoggedIn: boolean,
  onLogin: () => void
): ReactNode {
  const stockAvailable = CartSession.availableStock(product);
  const cartQty = CartSession.getQuantity(product.id);

  if (!customerLoggedIn) {
    return React.createElement(
      "button",
      {
        className: "landingAddBtn",
        type: "button",
        onClick: (event: React.MouseEvent) => {
          event.stopPropagation();
          onLogin();
        },
      },
      "Add to Cart"
    );
  }

  if (CartSession.isOutOfStock(product)) {
    return React.createElement(
      "button",
      {
        className: "landingAddBtn landingAddBtnDisabled",
        type: "button",
        disabled: true,
        onClick: (event: React.MouseEvent) => event.stopPropagation(),
      },
      "Out of Stock"
    );
  }

  if (cartQty > 0) {
    return React.createElement(
      "div",
      {
        className: "landingCardQtyStepper",
        onClick: (event: React.MouseEvent) => event.stopPropagation(),
      },
      React.createElement(
        "button",
        {
          className: "landingCardQtyBtn",
          type: "button",
          onClick: (event: React.MouseEvent) => {
            event.stopPropagation();
            CartSession.adjustQuantity(product, -1);
          },
          "aria-label": "Decrease quantity",
        },
        "−"
      ),
      React.createElement("span", { className: "landingCardQtyValue" }, String(cartQty)),
      React.createElement(
        "button",
        {
          className: "landingCardQtyBtn",
          type: "button",
          disabled: CartSession.hasStockData(product) && cartQty >= stockAvailable,
          onClick: (event: React.MouseEvent) => {
            event.stopPropagation();
            CartSession.adjustQuantity(product, 1);
          },
          "aria-label": "Increase quantity",
        },
        "+"
      )
    );
  }

  return React.createElement(
    "button",
    {
      className: "landingAddBtn",
      type: "button",
      onClick: (event: React.MouseEvent) => {
        event.stopPropagation();
        CartSession.addProduct(product, 1);
      },
    },
    "Add to Cart"
  );
}

export function LandingProductGrid(props: LandingProductGridProps): ReactElement | null {
  if (props.products.length === 0) {
    return React.createElement(
      "div",
      { className: "catalogEmptyState" },
      React.createElement("p", null, "No products available right now.")
    );
  }

  const rowClass = props.gridClassName ?? "landingProductRow";
  const cardClass = props.cardClassName
    ? "landingProductCard " + props.cardClassName
    : "landingProductCard";

  return React.createElement(
    "div",
    { className: rowClass },
    props.products.map((product) => {
      const showOldPrice =
        product.purchasePrice > 0 && product.purchasePrice > product.sellingPrice;
      const sizeLabel = productSizeLabel(product);
      const wishlisted = props.wishlistIds.includes(product.id);

      return React.createElement(
        "article",
        {
          className: cardClass + " landingProductCardClickable",
          key: "product-" + product.id,
          onClick: () => props.onProductClick(product),
          onKeyDown: (event: React.KeyboardEvent) => {
            if (event.key === "Enter" || event.key === " ") {
              event.preventDefault();
              props.onProductClick(product);
            }
          },
          role: "button",
          tabIndex: 0,
          "aria-label": "View " + product.name,
        },
        React.createElement(
          "div",
          { className: "landingProductImageWrap" },
          React.createElement("img", {
            src: resolveProductImage(product),
            alt: product.name,
            className: "landingProductImage",
            loading: "lazy",
          }),
          React.createElement(
            "span",
            { className: "landingProductBadge" },
            props.showNewBadge ? "New" : product.categoryName || "Fresh"
          ),
          React.createElement(
            "button",
            {
              className:
                "landingWishlistBtn" + (wishlisted ? " landingWishlistBtnActive" : ""),
              type: "button",
              "aria-label": wishlisted ? "Remove from wishlist" : "Add to wishlist",
              "aria-pressed": wishlisted,
              onClick: (event: React.MouseEvent) => props.onToggleWishlist(product, event),
            },
            wishlisted ? "♥" : "♡"
          )
        ),
        React.createElement(
          "div",
          { className: "landingProductBody" },
          React.createElement("span", { className: "landingProductName" }, product.name),
          sizeLabel
            ? React.createElement("span", { className: "landingProductSize" }, sizeLabel)
            : null,
          React.createElement(
            "div",
            { className: "landingProductPriceRow" },
            React.createElement(
              "span",
              { className: "landingProductPrice" },
              formatCatalogPrice(product.sellingPrice)
            ),
            showOldPrice
              ? React.createElement(
                  "span",
                  { className: "landingProductOldPrice" },
                  formatCatalogPrice(product.purchasePrice)
                )
              : null
          ),
          renderCartControl(product, props.customerLoggedIn, props.onLogin)
        )
      );
    })
  );
}
