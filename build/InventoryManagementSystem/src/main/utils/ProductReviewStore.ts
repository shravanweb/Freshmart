export interface ProductReview {
  id: string;
  author: string;
  rating: number;
  comment: string;
  createdAt: string;
  isCustomer?: boolean;
}

const STORAGE_PREFIX = "freshmart_product_reviews_";

const SEED_AUTHORS = ["Priya R.", "Arjun M.", "Sneha K.", "Rahul D.", "Ananya P."];
const SEED_COMMENTS = [
  "Great quality and fresh. Delivery was on time!",
  "Value for money. Will order again from FreshMart.",
  "Exactly as described. Happy with the purchase.",
  "Good packaging and product quality. Recommended.",
  "Fresh stock and fair price. Satisfied customer.",
];

function seedReviewsForProduct(productId: number): ProductReview[] {
  const count = 3 + (productId % 2);
  const reviews: ProductReview[] = [];

  for (let index = 0; index < count; index++) {
    const authorIndex = (productId + index) % SEED_AUTHORS.length;
    const commentIndex = (productId + index * 2) % SEED_COMMENTS.length;
    const rating = 4 + ((productId + index) % 2);

    reviews.push({
      id: "seed-" + productId + "-" + index,
      author: SEED_AUTHORS[authorIndex],
      rating,
      comment: SEED_COMMENTS[commentIndex],
      createdAt: new Date(Date.now() - (index + 2) * 86400000 * 4).toISOString(),
    });
  }

  return reviews;
}

export default class ProductReviewStore {
  private static storageKey(productId: number): string {
    return STORAGE_PREFIX + productId;
  }

  public static getReviews(productId: number): ProductReview[] {
    const seeds = seedReviewsForProduct(productId);
    const saved = ProductReviewStore.loadSaved(productId);
    return [...saved, ...seeds];
  }

  public static getAverageRating(productId: number): number {
    const reviews = ProductReviewStore.getReviews(productId);
    if (reviews.length === 0) {
      return 0;
    }

    const total = reviews.reduce((sum, review) => sum + review.rating, 0);
    return Math.round((total / reviews.length) * 10) / 10;
  }

  public static addReview(
    productId: number,
    author: string,
    rating: number,
    comment: string
  ): ProductReview {
    const review: ProductReview = {
      id: "user-" + Date.now(),
      author,
      rating: Math.min(5, Math.max(1, Math.round(rating))),
      comment: comment.trim(),
      createdAt: new Date().toISOString(),
      isCustomer: true,
    };

    const saved = ProductReviewStore.loadSaved(productId);
    saved.unshift(review);

    if (typeof window !== "undefined") {
      localStorage.setItem(
        ProductReviewStore.storageKey(productId),
        JSON.stringify(saved)
      );
    }

    return review;
  }

  private static loadSaved(productId: number): ProductReview[] {
    if (typeof window === "undefined") {
      return [];
    }

    try {
      const raw = localStorage.getItem(ProductReviewStore.storageKey(productId));
      if (!raw) {
        return [];
      }

      const parsed = JSON.parse(raw) as ProductReview[];
      return Array.isArray(parsed) ? parsed : [];
    } catch {
      return [];
    }
  }
}
