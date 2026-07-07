const STORAGE_KEY = "freshmart_wishlist";

export default class WishlistSession {
  public static getIds(): number[] {
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
      const ids: number[] = [];
      for (const item of parsed) {
        const id = Number(item);
        if (Number.isFinite(id) && id > 0 && !ids.includes(id)) {
          ids.push(id);
        }
      }
      return ids;
    } catch {
      return [];
    }
  }

  public static has(productId: number): boolean {
    return WishlistSession.getIds().includes(productId);
  }

  public static toggle(productId: number): boolean {
    const ids = WishlistSession.getIds();
    const index = ids.indexOf(productId);
    if (index >= 0) {
      ids.splice(index, 1);
      WishlistSession.save(ids);
      return false;
    }
    ids.push(productId);
    WishlistSession.save(ids);
    return true;
  }

  private static save(ids: number[]): void {
    if (typeof window === "undefined") {
      return;
    }
    localStorage.setItem(STORAGE_KEY, JSON.stringify(ids));
  }
}
