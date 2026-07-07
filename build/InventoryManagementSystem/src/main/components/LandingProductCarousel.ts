import React, { Component, createRef, ReactNode } from "react";
import CustomerSession from "../utils/CustomerSession";
import CartSession from "../utils/CartSession";
import WishlistSession from "../utils/WishlistSession";
import { PublicProductRecord } from "../utils/LandingCatalogApi";
import { LandingProductGrid } from "./LandingProductGrid";

const AUTO_PLAY_MS = 4500;

export interface LandingProductCarouselProps {
  products: PublicProductRecord[];
  title: string;
  subtitle: string;
  headExtra?: ReactNode;
  onProductClick: (product: PublicProductRecord) => void;
  onLogin: () => void;
}

interface LandingProductCarouselState {
  customerLoggedIn: boolean;
  wishlistIds: number[];
  cartTick: number;
}

export default class LandingProductCarousel extends Component<
  LandingProductCarouselProps,
  LandingProductCarouselState
> {
  private trackRef = createRef<HTMLDivElement>();
  private autoTimer: ReturnType<typeof setInterval> | null = null;
  private paused = false;
  private sessionUnsub: (() => void) | null = null;
  private cartUnsub: (() => void) | null = null;

  public constructor(props: LandingProductCarouselProps) {
    super(props);
    this.state = {
      customerLoggedIn: CustomerSession.isActive(),
      wishlistIds: WishlistSession.getIds(),
      cartTick: 0,
    };
  }

  public componentDidMount(): void {
    this.sessionUnsub = CustomerSession.onChange(() => {
      this.setState({ customerLoggedIn: CustomerSession.isActive() });
    });
    this.cartUnsub = CartSession.onChange(() => {
      this.setState((prev) => ({ cartTick: prev.cartTick + 1 }));
    });
    this.startAutoPlay();
  }

  public componentDidUpdate(prevProps: LandingProductCarouselProps): void {
    if (prevProps.products !== this.props.products) {
      this.startAutoPlay();
    }
  }

  public componentWillUnmount(): void {
    this.stopAutoPlay();
    this.sessionUnsub?.();
    this.sessionUnsub = null;
    this.cartUnsub?.();
    this.cartUnsub = null;
  }

  private startAutoPlay(): void {
    this.stopAutoPlay();
    if (this.props.products.length <= 1) {
      return;
    }
    this.autoTimer = setInterval(() => {
      if (!this.paused) {
        this.scrollStep(1);
      }
    }, AUTO_PLAY_MS);
  }

  private stopAutoPlay(): void {
    if (this.autoTimer != null) {
      clearInterval(this.autoTimer);
      this.autoTimer = null;
    }
  }

  private getScrollStep(): number {
    const track = this.trackRef.current;
    if (!track) {
      return 246;
    }
    const card = track.querySelector(".landingProductCarouselCard") as HTMLElement | null;
    if (!card) {
      return 246;
    }
    const styles = window.getComputedStyle(track);
    const gap = Number.parseFloat(styles.columnGap || styles.gap || "18") || 18;
    return card.offsetWidth + gap;
  }

  private scrollStep = (direction: 1 | -1): void => {
    const track = this.trackRef.current;
    if (!track) {
      return;
    }

    const step = this.getScrollStep();
    const maxScroll = Math.max(0, track.scrollWidth - track.clientWidth);

    if (direction > 0 && track.scrollLeft >= maxScroll - 2) {
      track.scrollTo({ left: 0, behavior: "smooth" });
      return;
    }

    if (direction < 0 && track.scrollLeft <= 2) {
      track.scrollTo({ left: maxScroll, behavior: "smooth" });
      return;
    }

    track.scrollBy({ left: direction * step, behavior: "smooth" });
  };

  private handleMouseEnter = (): void => {
    this.paused = true;
  };

  private handleMouseLeave = (): void => {
    this.paused = false;
  };

  private toggleWishlist = (product: PublicProductRecord, event: React.MouseEvent): void => {
    event.stopPropagation();
    event.preventDefault();
    WishlistSession.toggle(product.id);
    this.setState({ wishlistIds: WishlistSession.getIds() });
  };

  public render(): ReactNode {
    void this.state.cartTick;
    const { products, title, subtitle, headExtra } = this.props;
    const showNav = products.length > 1;

    return React.createElement(
      React.Fragment,
      null,
      React.createElement(
        "div",
        { className: "landingSectionHead landingProductsHead" },
        React.createElement(
          "div",
          { className: "landingProductsHeadText" },
          React.createElement("h2", { className: "landingSectionTitle" }, title),
          React.createElement("p", { className: "landingSectionSubtitle" }, subtitle)
        ),
        React.createElement(
          "div",
          { className: "landingProductCarouselHeadActions" },
          headExtra ?? null,
          showNav
            ? React.createElement(
                "div",
                { className: "landingProductCarouselNav" },
                React.createElement(
                  "button",
                  {
                    type: "button",
                    className: "landingProductCarouselNavBtn",
                    "aria-label": "Scroll products left",
                    onClick: () => this.scrollStep(-1),
                  },
                  React.createElement(
                    "svg",
                    { viewBox: "0 0 24 24", "aria-hidden": "true" },
                    React.createElement("path", {
                      d: "M14.7 6.3a1 1 0 010 1.4L10.41 12l4.3 4.3a1 1 0 11-1.42 1.4l-5-5a1 1 0 010-1.4l5-5a1 1 0 011.42 0z",
                      fill: "currentColor",
                    })
                  )
                ),
                React.createElement(
                  "button",
                  {
                    type: "button",
                    className: "landingProductCarouselNavBtn",
                    "aria-label": "Scroll products right",
                    onClick: () => this.scrollStep(1),
                  },
                  React.createElement(
                    "svg",
                    { viewBox: "0 0 24 24", "aria-hidden": "true" },
                    React.createElement("path", {
                      d: "M9.3 6.3a1 1 0 011.4 0l5 5a1 1 0 010 1.4l-5 5a1 1 0 11-1.4-1.4L13.59 12 9.3 7.7a1 1 0 010-1.4z",
                      fill: "currentColor",
                    })
                  )
                )
              )
            : null
        )
      ),
      React.createElement(
        "div",
        {
          className: "landingProductCarouselViewport",
          onMouseEnter: this.handleMouseEnter,
          onMouseLeave: this.handleMouseLeave,
        },
        React.createElement(
          "div",
          { className: "landingProductCarouselTrack", ref: this.trackRef },
          React.createElement(LandingProductGrid, {
            products,
            customerLoggedIn: this.state.customerLoggedIn,
            wishlistIds: this.state.wishlistIds,
            onProductClick: this.props.onProductClick,
            onToggleWishlist: this.toggleWishlist,
            onLogin: this.props.onLogin,
            gridClassName: "landingProductCarouselRow",
            cardClassName: "landingProductCarouselCard",
          })
        )
      )
    );
  }
}
