import React, { Component, ReactNode } from "react";
import CustomerSession from "../utils/CustomerSession";
import ProductReviewStore, { ProductReview } from "../utils/ProductReviewStore";

export interface ProductReviewsPanelProps {
  productId: number;
}

interface ProductReviewsPanelState {
  reviews: ProductReview[];
  averageRating: number;
  draftRating: number;
  draftComment: string;
  formError: string;
  formSuccess: string;
  customerLoggedIn: boolean;
  customerName: string;
}

function renderStars(rating: number, keyPrefix: string): ReactNode {
  const stars: ReactNode[] = [];

  for (let index = 1; index <= 5; index++) {
    const filled = rating >= index - 0.25;
    stars.push(
      React.createElement(
        "span",
        {
          className: "productDetailStar" + (filled ? " productDetailStarFilled" : ""),
          key: keyPrefix + "-star-" + index,
        },
        "★"
      )
    );
  }

  return React.createElement("span", { className: "productDetailStars" }, stars);
}

function formatReviewDate(value: string): string {
  try {
    return new Date(value).toLocaleDateString("en-IN", {
      day: "numeric",
      month: "short",
      year: "numeric",
    });
  } catch {
    return "";
  }
}

export default class ProductReviewsPanel extends Component<
  ProductReviewsPanelProps,
  ProductReviewsPanelState
> {
  public constructor(props: ProductReviewsPanelProps) {
    super(props);
    const session = CustomerSession.load();

    this.state = {
      reviews: ProductReviewStore.getReviews(props.productId),
      averageRating: ProductReviewStore.getAverageRating(props.productId),
      draftRating: 5,
      draftComment: "",
      formError: "",
      formSuccess: "",
      customerLoggedIn: CustomerSession.isActive(),
      customerName: session?.displayName || session?.email?.split("@")[0] || "Customer",
    };
  }

  public componentDidUpdate(prevProps: ProductReviewsPanelProps): void {
    if (prevProps.productId !== this.props.productId) {
      this.reloadReviews();
    }
  }

  private reloadReviews(): void {
    this.setState({
      reviews: ProductReviewStore.getReviews(this.props.productId),
      averageRating: ProductReviewStore.getAverageRating(this.props.productId),
      draftComment: "",
      draftRating: 5,
      formError: "",
      formSuccess: "",
    });
  }

  private setDraftRating = (rating: number): void => {
    this.setState({ draftRating: rating, formError: "" });
  };

  private submitReview = (): void => {
    if (!this.state.customerLoggedIn) {
      this.setState({ formError: "Please login to write a review." });
      return;
    }

    const comment = this.state.draftComment.trim();
    if (comment.length < 8) {
      this.setState({ formError: "Please write at least 8 characters in your review." });
      return;
    }

    ProductReviewStore.addReview(
      this.props.productId,
      this.state.customerName,
      this.state.draftRating,
      comment
    );

    this.setState({
      reviews: ProductReviewStore.getReviews(this.props.productId),
      averageRating: ProductReviewStore.getAverageRating(this.props.productId),
      draftComment: "",
      draftRating: 5,
      formError: "",
      formSuccess: "Thank you! Your review has been posted.",
    });

    window.setTimeout(() => {
      this.setState({ formSuccess: "" });
    }, 3000);
  };

  public render(): ReactNode {
    const { reviews, averageRating } = this.state;

    return React.createElement(
      "section",
      { className: "productDetailReviewsSection", id: "product-reviews" },
      React.createElement(
        "div",
        { className: "productDetailReviewsHead" },
        React.createElement("h2", { className: "productDetailSectionTitle" }, "Ratings & Reviews"),
        React.createElement(
          "div",
          { className: "productDetailReviewsSummary" },
          React.createElement("span", { className: "productDetailReviewsScore" }, String(averageRating)),
          renderStars(averageRating, "summary"),
          React.createElement(
            "span",
            { className: "productDetailReviewsCount" },
            reviews.length + " review" + (reviews.length === 1 ? "" : "s")
          )
        )
      ),
      React.createElement(
        "div",
        { className: "productDetailReviewList" },
        reviews.map((review) =>
          React.createElement(
            "article",
            { className: "productDetailReviewCard", key: review.id },
            React.createElement(
              "div",
              { className: "productDetailReviewCardHead" },
              React.createElement(
                "div",
                { className: "productDetailReviewAuthorBlock" },
                React.createElement(
                  "span",
                  { className: "productDetailReviewAvatar" },
                  review.author.charAt(0).toUpperCase()
                ),
                React.createElement(
                  "div",
                  null,
                  React.createElement(
                    "strong",
                    { className: "productDetailReviewAuthor" },
                    review.author
                  ),
                  React.createElement(
                    "span",
                    { className: "productDetailReviewDate" },
                    formatReviewDate(review.createdAt)
                  )
                )
              ),
              renderStars(review.rating, review.id)
            ),
            React.createElement("p", { className: "productDetailReviewText" }, review.comment)
          )
        )
      ),
      React.createElement(
        "div",
        { className: "productDetailReviewForm" },
        React.createElement("h3", { className: "productDetailReviewFormTitle" }, "Write a review"),
        !this.state.customerLoggedIn
          ? React.createElement(
              "p",
              { className: "productDetailReviewFormHint" },
              "Login to share your experience with this product."
            )
          : null,
        React.createElement(
          "div",
          { className: "productDetailReviewFormRating" },
          React.createElement("span", { className: "productDetailReviewFormLabel" }, "Your rating"),
          React.createElement(
            "div",
            { className: "productDetailReviewRatingPick" },
            [1, 2, 3, 4, 5].map((rating) =>
              React.createElement(
                "button",
                {
                  type: "button",
                  className:
                    "productDetailReviewRatingBtn" +
                    (this.state.draftRating >= rating ? " productDetailReviewRatingBtnActive" : ""),
                  key: "pick-" + rating,
                  onClick: () => this.setDraftRating(rating),
                  "aria-label": rating + " stars",
                },
                "★"
              )
            )
          )
        ),
        React.createElement("textarea", {
          className: "productDetailReviewTextarea",
          rows: 4,
          placeholder: "Share your experience with this product...",
          value: this.state.draftComment,
          onChange: (event: React.ChangeEvent<HTMLTextAreaElement>) =>
            this.setState({ draftComment: event.target.value, formError: "" }),
          disabled: !this.state.customerLoggedIn,
        }),
        this.state.formError
          ? React.createElement("p", { className: "productDetailReviewFormError" }, this.state.formError)
          : null,
        this.state.formSuccess
          ? React.createElement(
              "p",
              { className: "productDetailReviewFormSuccess" },
              this.state.formSuccess
            )
          : null,
        React.createElement(
          "button",
          {
            className: "productDetailReviewSubmitBtn",
            type: "button",
            onClick: this.submitReview,
            disabled: !this.state.customerLoggedIn,
          },
          "Submit Review"
        )
      )
    );
  }
}
