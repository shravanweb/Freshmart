import { gql } from "@apollo/client";
import Env from "../classes/Env";
import GraphQLClientInit from "./GraphQLClientInit";
import LocalDataStore from "./LocalDataStore";

export interface CategoryRecord {
  id: number;
  name: string;
  code: string;
  description: string;
  status: string;
}

export interface CategoryInput {
  id?: number;
  name: string;
  code: string;
  description: string;
  status: string;
  organization: number;
}

export interface CategoryMutationResult {
  success: boolean;
  errors: string[];
  category: CategoryRecord | null;
}

function enumName(value: unknown): string {
  if (value == null) {
    return "";
  }
  if (typeof value === "string") {
    return value;
  }
  if (typeof value === "object" && "name" in (value as Record<string, unknown>)) {
    return String((value as { name: string }).name ?? "");
  }
  return String(value);
}

function parseId(value: unknown): number {
  if (value == null) {
    return 0;
  }
  if (typeof value === "number" && Number.isFinite(value)) {
    return value;
  }
  if (typeof value === "string" && value.trim() !== "") {
    const parsed = Number(value);
    return Number.isFinite(parsed) ? parsed : 0;
  }
  return 0;
}

export function formatCategoryApiError(error: unknown): string {
  const message = error instanceof Error ? error.message : String(error);
  if (
    message.includes("504") ||
    message.includes("503") ||
    message.includes("ECONNREFUSED") ||
    message.includes("Failed to fetch") ||
    message.includes("NetworkError")
  ) {
    return "Backend server is not responding. Please start Spring Boot (port 8080) and try again.";
  }
  if (message.includes("401") || message.includes("403") || message.includes("AuthFail")) {
    return "Session expired. Please login again.";
  }
  return message;
}

function mapCategory(raw: Record<string, unknown>): CategoryRecord {
  return {
    id: parseId(raw.id),
    name: String(raw.name ?? ""),
    code: String(raw.code ?? ""),
    description: String(raw.description ?? ""),
    status: enumName(raw.status),
  };
}

const CATEGORY_FIELDS = `
  id
  name
  code
  description
  status
`;

export function categoryImageSlug(code: string): string {
  return code
    .trim()
    .toLowerCase()
    .replace(/[^a-z0-9]+/g, "-")
    .replace(/^-+|-+$/g, "");
}

function resolveAssetUrl(url?: string): string {
  const trimmed = (url ?? "").trim();
  if (!trimmed) {
    return "";
  }
  if (trimmed.startsWith("http://") || trimmed.startsWith("https://")) {
    return trimmed;
  }
  const base = Env.get().resolvedHttpUrl.replace(/\/$/, "");
  return trimmed.startsWith("/") ? `${base}${trimmed}` : `${base}/${trimmed}`;
}

export default class CategoryApi {
  private static async client() {
    GraphQLClientInit.token = await LocalDataStore.get().getToken();
    return GraphQLClientInit.get();
  }

  public static async resolveImageUrl(code: string): Promise<string> {
    const normalized = (code ?? "").trim().toLowerCase();
    if (!normalized) {
      return "";
    }
    const baseUrl = Env.get().resolvedHttpUrl;
    const response = await fetch(`${baseUrl}/api/public/categories`, {
      method: "GET",
      headers: { Accept: "application/json" },
    });
    if (!response.ok) {
      return "";
    }
    const data = (await response.json()) as {
      items?: { code?: string; imageUrl?: string }[];
    };
    const match = (data.items ?? []).find(
      (item) => (item.code ?? "").trim().toLowerCase() === normalized
    );
    return resolveAssetUrl(match?.imageUrl);
  }

  public static async uploadCategoryImage(code: string, file: File): Promise<string> {
    const token = await LocalDataStore.get().getToken();
    const formData = new FormData();
    formData.append("code", code);
    formData.append("file", file);
    const response = await fetch(`${Env.get().resolvedHttpUrl}/api/category-image`, {
      method: "POST",
      headers: token ? { Authorization: `Bearer ${token}` } : {},
      body: formData,
    });
    if (!response.ok) {
      const message = await response.text();
      throw new Error(message || `Image upload failed (${response.status})`);
    }
    const data = (await response.json()) as { imageUrl?: string };
    return resolveAssetUrl(data.imageUrl);
  }

  public static async getAllCategories(
    organizationId: number
  ): Promise<CategoryRecord[]> {
    const client = await CategoryApi.client();
    const { data, errors } = await client.query({
      query: gql`
        query GetAllProductCategories($organization: Long) {
          getAllProductCategories(in: { organization: $organization }) {
            items {
              ${CATEGORY_FIELDS}
            }
          }
        }
      `,
      variables: { organization: organizationId },
      fetchPolicy: "no-cache",
    });

    if (errors?.length) {
      throw new Error(errors.map((e) => e.message).join("; "));
    }

    const items = data?.getAllProductCategories?.items ?? [];
    return items.map((item: Record<string, unknown>) => mapCategory(item));
  }

  public static async createCategory(
    input: CategoryInput
  ): Promise<CategoryMutationResult> {
    return CategoryApi.mutateCategory("createProductCategory", input);
  }

  public static async updateCategory(
    input: CategoryInput
  ): Promise<CategoryMutationResult> {
    return CategoryApi.mutateCategory("updateProductCategory", input);
  }

  public static async deleteCategory(
    id: number
  ): Promise<CategoryMutationResult> {
    const client = await CategoryApi.client();
    const { data, errors } = await client.mutate({
      mutation: gql`
        mutation DeleteProductCategory($id: Long) {
          deleteProductCategory(input: $id) {
            status
            errors
          }
        }
      `,
      variables: { id },
    });

    if (errors?.length) {
      return {
        success: false,
        errors: errors.map((e) => e.message),
        category: null,
      };
    }

    const result = data?.deleteProductCategory;
    return {
      success: result?.status === "Success",
      errors: result?.errors ?? [],
      category: null,
    };
  }

  private static async mutateCategory(
    operation: "createProductCategory" | "updateProductCategory",
    input: CategoryInput
  ): Promise<CategoryMutationResult> {
    const client = await CategoryApi.client();
    const gqlInput: Record<string, unknown> = {
      name: input.name,
      code: input.code,
      status: input.status,
      organization: input.organization,
    };

    if (input.id != null && input.id > 0) {
      gqlInput.id = input.id;
    }
    if (input.description) {
      gqlInput.description = input.description;
    }

    try {
      const { data, errors } = await client.mutate({
        mutation: gql`
          mutation MutateProductCategory($input: ProductCategoryEntityInput!) {
            ${operation}(input: $input) {
              status
              errors
              value {
                ${CATEGORY_FIELDS}
              }
            }
          }
        `,
        variables: { input: gqlInput },
        errorPolicy: "all",
      });

      if (errors?.length) {
        return {
          success: false,
          errors: errors.map((e) => e.message),
          category: null,
        };
      }

      const result = data?.[operation];
      if (!result) {
        return {
          success: false,
          errors: ["No response from server."],
          category: null,
        };
      }

      return {
        success: result?.status === "Success",
        errors: result?.errors ?? [],
        category: result?.value ? mapCategory(result.value) : null,
      };
    } catch (e: unknown) {
      return { success: false, errors: [formatCategoryApiError(e)], category: null };
    }
  }
}
