import { PublicProductRecord } from "./LandingCatalogApi";

export type CategoryFilterGroup =
  | "brands"
  | "flavours"
  | "packSizes"
  | "productTypes"
  | "namkeens";

export interface CategoryFilterSelections {
  brands: string[];
  flavours: string[];
  packSizes: string[];
  productTypes: string[];
  namkeens: string[];
}

export interface CategoryFilterOptions {
  brands: string[];
  flavours: string[];
  packSizes: string[];
  productTypes: string[];
  namkeens: string[];
}

export interface ProductFilterMeta {
  brand: string;
  flavour: string;
  packSize: string;
  productType: string;
  namkeen: string;
}

const EMPTY_SELECTIONS: CategoryFilterSelections = {
  brands: [],
  flavours: [],
  packSizes: [],
  productTypes: [],
  namkeens: [],
};

const PACK_SIZE_PATTERN =
  /,\s*(\d+(?:\.\d+)?\s*(?:g|kg|ml|l|ltr|litre|litres|pcs|pc|pack|pieces?))\s*$/i;

const FLAVOUR_KEYWORDS = [
  "Cream & Onion",
  "Peri Peri",
  "Aloo Bhujia",
  "Black Currant",
  "Chilli",
  "Masala",
  "Classic",
  "Original",
  "Butter",
  "Cheese",
  "Tomato",
  "Mint",
  "Lemon",
  "Chocolate",
  "Vanilla",
  "Strawberry",
  "Mango",
  "Tangy",
  "Spicy",
  "Sweet",
  "Salted",
  "Chatpata",
  "Jeera",
  "Garlic",
  "Herbs",
  "Barbeque",
  "Pudina",
];

const PRODUCT_TYPE_KEYWORDS = [
  "Potato Chips",
  "French Fries",
  "Ice Cream",
  "Namkeen",
  "Biscuits",
  "Cookies",
  "Soft Drink",
  "Juice",
  "Noodles",
  "Pasta",
  "Soap",
  "Shampoo",
  "Detergent",
  "Rice",
  "Atta",
  "Dal",
  "Cooking Oil",
  "Ghee",
  "Milk",
  "Curd",
  "Paneer",
  "Chocolate",
  "Candy",
  "Peanuts",
  "Popcorn",
  "Chips",
  "Fries",
  "Drinks",
  "Snacks",
  "Beverages",
];

const NAMKEEN_KEYWORDS = [
  "Aloo Bhujia",
  "Moong Dal",
  "Ratlami Sev",
  "Mixture",
  "Bhujia",
  "Namkeen",
  "Sev",
  "Chivda",
  "Khakra",
  "Mathri",
  "Farsan",
  "Kachori",
];

const KNOWN_BRANDS = [
  "Bingo!",
  "Amul",
  "Lay's",
  "Lays",
  "Kurkure",
  "Haldiram's",
  "Haldirams",
  "Britannia",
  "Parle",
  "Cadbury",
  "Nestle",
  "Maggi",
  "Tata",
  "Fortune",
  "Surf Excel",
  "Dove",
  "Ponds",
  "Colgate",
  "Pepsodent",
  "Real",
  "Frooti",
  "Uncle Chipps",
  "Pringles",
  "Too Yumm",
  "Balaji",
  "ITC",
  "MTR",
  "Patanjali",
];

function normalizeLabel(value: string): string {
  return value.replace(/\s+/g, " ").trim();
}

function findKeyword(name: string, keywords: string[]): string {
  const lower = name.toLowerCase();
  for (const keyword of keywords) {
    if (lower.includes(keyword.toLowerCase())) {
      return keyword;
    }
  }
  return "";
}

function extractBrand(name: string): string {
  for (const brand of KNOWN_BRANDS) {
    if (name.toLowerCase().startsWith(brand.toLowerCase())) {
      return brand;
    }
  }

  const beforeComma = name.split(",")[0]?.trim() ?? name;
  const bangIndex = beforeComma.indexOf("!");
  if (bangIndex >= 0) {
    return normalizeLabel(beforeComma.substring(0, bangIndex + 1));
  }

  const words = beforeComma.split(/\s+/);
  if (words.length >= 2 && /^[A-Z]/.test(words[1])) {
    return normalizeLabel(words.slice(0, 2).join(" "));
  }
  return normalizeLabel(words[0] ?? "Other");
}

function extractPackSize(name: string): string {
  const match = name.match(PACK_SIZE_PATTERN);
  if (match?.[1]) {
    return normalizeLabel(match[1]);
  }

  const inline = name.match(/\b(\d+(?:\.\d+)?\s*(?:g|kg|ml|l))\b/i);
  return inline?.[1] ? normalizeLabel(inline[1]) : "";
}

export function parseProductFilterMeta(product: PublicProductRecord): ProductFilterMeta {
  const name = product.name ?? "";

  return {
    brand: extractBrand(name) || "Other",
    flavour: findKeyword(name, FLAVOUR_KEYWORDS),
    packSize: extractPackSize(name),
    productType: findKeyword(name, PRODUCT_TYPE_KEYWORDS),
    namkeen: findKeyword(name, NAMKEEN_KEYWORDS),
  };
}

function uniqueSorted(values: string[]): string[] {
  const seen = new Set<string>();
  const result: string[] = [];
  for (const value of values) {
    const label = normalizeLabel(value);
    if (!label || seen.has(label.toLowerCase())) {
      continue;
    }
    seen.add(label.toLowerCase());
    result.push(label);
  }
  result.sort((a, b) => a.localeCompare(b, undefined, { sensitivity: "base" }));
  return result;
}

export function buildCategoryFilterOptions(
  products: PublicProductRecord[]
): CategoryFilterOptions {
  const brands: string[] = [];
  const flavours: string[] = [];
  const packSizes: string[] = [];
  const productTypes: string[] = [];
  const namkeens: string[] = [];

  for (const product of products) {
    const meta = parseProductFilterMeta(product);
    brands.push(meta.brand);
    if (meta.flavour) {
      flavours.push(meta.flavour);
    }
    if (meta.packSize) {
      packSizes.push(meta.packSize);
    }
    if (meta.productType) {
      productTypes.push(meta.productType);
    }
    if (meta.namkeen) {
      namkeens.push(meta.namkeen);
    }
  }

  return {
    brands: uniqueSorted(brands),
    flavours: uniqueSorted(flavours),
    packSizes: uniqueSorted(packSizes),
    productTypes: uniqueSorted(productTypes),
    namkeens: uniqueSorted(namkeens),
  };
}

function matchesGroup(
  value: string,
  selected: string[],
  allowEmptyMatch: boolean
): boolean {
  if (selected.length === 0) {
    return true;
  }
  if (!value) {
    return allowEmptyMatch;
  }
  return selected.some((item) => item.toLowerCase() === value.toLowerCase());
}

export function filterCategoryProducts(
  products: PublicProductRecord[],
  selections: CategoryFilterSelections
): PublicProductRecord[] {
  const hasAnySelection =
    selections.brands.length > 0 ||
    selections.flavours.length > 0 ||
    selections.packSizes.length > 0 ||
    selections.productTypes.length > 0 ||
    selections.namkeens.length > 0;

  if (!hasAnySelection) {
    return products;
  }

  return products.filter((product) => {
    const meta = parseProductFilterMeta(product);
    return (
      matchesGroup(meta.brand, selections.brands, false) &&
      matchesGroup(meta.flavour, selections.flavours, false) &&
      matchesGroup(meta.packSize, selections.packSizes, false) &&
      matchesGroup(meta.productType, selections.productTypes, false) &&
      matchesGroup(meta.namkeen, selections.namkeens, false)
    );
  });
}

export function hasActiveCategoryFilters(
  selections: CategoryFilterSelections = EMPTY_SELECTIONS
): boolean {
  return (
    selections.brands.length > 0 ||
    selections.flavours.length > 0 ||
    selections.packSizes.length > 0 ||
    selections.productTypes.length > 0 ||
    selections.namkeens.length > 0
  );
}

export function createEmptyCategoryFilterSelections(): CategoryFilterSelections {
  return {
    brands: [],
    flavours: [],
    packSizes: [],
    productTypes: [],
    namkeens: [],
  };
}

export const CATEGORY_FILTER_GROUPS: Array<{
  key: CategoryFilterGroup;
  label: string;
}> = [
  { key: "brands", label: "Brands" },
  { key: "flavours", label: "Flavours" },
  { key: "packSizes", label: "Pack Size" },
  { key: "productTypes", label: "Product Type" },
  { key: "namkeens", label: "Namkeens" },
];
