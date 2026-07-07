import React, { Component, ReactNode } from "react";
import CustomerSession from "../utils/CustomerSession";
import CartSession from "../utils/CartSession";
import WishlistSession from "../utils/WishlistSession";
import { PublicProductRecord } from "../utils/LandingCatalogApi";
import { LandingProductGrid } from "./LandingProductGrid";

export interface CustomerLiveProductGridProps {
  products: PublicProductRecord[];
  showNewBadge?: boolean;
  onProductClick: (product: PublicProductRecord) => void;
  onLogin: () => void;
}

interface CustomerLiveProductGridState {
  customerLoggedIn: boolean;
  wishlistIds: number[];
  cartTick: number;
}

export default class CustomerLiveProductGrid extends Component<
  CustomerLiveProductGridProps,
  CustomerLiveProductGridState
> {
  private sessionUnsub: (() => void) | null = null;
  private cartUnsub: (() => void) | null = null;

  public constructor(props: CustomerLiveProductGridProps) {
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
  }

  public componentWillUnmount(): void {
    this.sessionUnsub?.();
    this.sessionUnsub = null;
    this.cartUnsub?.();
    this.cartUnsub = null;
  }

  private toggleWishlist = (product: PublicProductRecord, event: React.MouseEvent): void => {
    event.stopPropagation();
    event.preventDefault();
    WishlistSession.toggle(product.id);
    this.setState({ wishlistIds: WishlistSession.getIds() });
  };

  public render(): ReactNode {
    void this.state.cartTick;
    return React.createElement(LandingProductGrid, {
      products: this.props.products,
      customerLoggedIn: this.state.customerLoggedIn,
      wishlistIds: this.state.wishlistIds,
      showNewBadge: this.props.showNewBadge,
      onProductClick: this.props.onProductClick,
      onToggleWishlist: this.toggleWishlist,
      onLogin: this.props.onLogin,
    });
  }
}
