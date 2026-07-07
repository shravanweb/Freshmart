import React, { Component, createRef, ReactNode } from "react";
import { PublicProductRecord } from "../utils/LandingCatalogApi";
import { formatCatalogPrice, resolveProductImage } from "./LandingProductGrid";

export interface ProductRelatedCarouselProps {
  products: PublicProductRecord[];
  loading?: boolean;
  onProductClick: (product: PublicProductRecord) => void;
}

export default class ProductRelatedCarousel extends Component<ProductRelatedCarouselProps> {
  private trackRef = createRef<HTMLDivElement>();

  private scrollBy = (direction: number): void => {
    const track = this.trackRef.current;
    if (!track) {
      return;
    }

    track.scrollBy({ left: direction * 280, behavior: "smooth" });
  };

  private renderCard(product: PublicProductRecord): ReactNode {
    return React.createElement(
      "article",
      {
        className: "productDetailRelatedCard landingProductCard landingProductCardClickable",
        key: "related-" + product.id,
        onClick: () => this.props.onProductClick(product),
        onKeyDown: (event: React.KeyboardEvent) => {
          if (event.key === "Enter" || event.key === " ") {
            event.preventDefault();
            this.props.onProductClick(product);
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
          product.categoryName || "Fresh"
        )
      ),
      React.createElement(
        "div",
        { className: "landingProductBody" },
        React.createElement("span", { className: "landingProductName" }, product.name),
        React.createElement(
          "div",
          { className: "landingProductPriceRow" },
          React.createElement(
            "span",
            { className: "landingProductPrice" },
            formatCatalogPrice(product.sellingPrice)
          )
        ),
        React.createElement(
          "button",
          {
            className: "landingAddBtn",
            type: "button",
            onClick: (event: React.MouseEvent) => {
              event.stopPropagation();
              this.props.onProductClick(product);
            },
          },
          "View Product"
        )
      )
    );
  }

  public render(): ReactNode {
    const { products, loading } = this.props;

    return React.createElement(
      "section",
      { className: "productDetailRelatedSection" },
      React.createElement(
        "div",
        { className: "productDetailRelatedHead" },
        React.createElement("h2", { className: "productDetailSectionTitle" }, "Related Products"),
        products.length > 3
          ? React.createElement(
              "div",
              { className: "productDetailRelatedNav" },
              React.createElement(
                "button",
                {
                  type: "button",
                  className: "productDetailRelatedNavBtn",
                  "aria-label": "Scroll related products left",
                  onClick: () => this.scrollBy(-1),
                },
                "‹"
              ),
              React.createElement(
                "button",
                {
                  type: "button",
                  className: "productDetailRelatedNavBtn",
                  "aria-label": "Scroll related products right",
                  onClick: () => this.scrollBy(1),
                },
                "›"
              )
            )
          : null
      ),
      loading
        ? React.createElement("p", { className: "productDetailRelatedStatus" }, "Loading related products...")
        : products.length === 0
          ? React.createElement(
              "p",
              { className: "productDetailRelatedStatus" },
              "No related products right now."
            )
          : React.createElement(
              "div",
              { className: "productDetailRelatedTrack", ref: this.trackRef },
              products.map((product) => this.renderCard(product))
            )
    );
  }
}
