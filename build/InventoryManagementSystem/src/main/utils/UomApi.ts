import { gql } from "@apollo/client";
import GraphQLClientInit from "./GraphQLClientInit";
import LocalDataStore from "./LocalDataStore";

export interface UomRecord {
  id: number;
  name: string;
  symbol: string;
  uomType: string;
  baseFactor: number;
  status: string;
}

export interface UomInput {
  id?: number;
  name: string;
  symbol: string;
  uomType: string;
  baseFactor: number;
  status: string;
  organization: number;
}

export interface UomMutationResult {
  success: boolean;
  errors: string[];
  uom: UomRecord | null;
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

function parseNumber(value: unknown, fallback = 0): number {
  if (typeof value === "number" && Number.isFinite(value)) {
    return value;
  }
  if (typeof value === "string" && value.trim() !== "") {
    const parsed = Number(value);
    return Number.isFinite(parsed) ? parsed : fallback;
  }
  return fallback;
}

export function formatUomApiError(error: unknown): string {
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

function mapUom(raw: Record<string, unknown>): UomRecord {
  return {
    id: parseId(raw.id),
    name: String(raw.name ?? ""),
    symbol: String(raw.symbol ?? ""),
    uomType: enumName(raw.uomType),
    baseFactor: parseNumber(raw.baseFactor, 1),
    status: enumName(raw.status),
  };
}

const UOM_FIELDS = `
  id
  name
  symbol
  uomType
  baseFactor
  status
`;

export default class UomApi {
  private static async client() {
    GraphQLClientInit.token = await LocalDataStore.get().getToken();
    return GraphQLClientInit.get();
  }

  public static async getAllUnitOfMeasures(organizationId: number): Promise<UomRecord[]> {
    const client = await UomApi.client();
    const { data, errors } = await client.query({
      query: gql`
        query GetAllUnitOfMeasures($organization: Long) {
          getAllUnitOfMeasures(in: { organization: $organization }) {
            items {
              ${UOM_FIELDS}
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

    const items = data?.getAllUnitOfMeasures?.items ?? [];
    return items.map((item: Record<string, unknown>) => mapUom(item));
  }

  public static async createUnitOfMeasure(input: UomInput): Promise<UomMutationResult> {
    return UomApi.mutateUom("createUnitOfMeasure", input);
  }

  public static async updateUnitOfMeasure(input: UomInput): Promise<UomMutationResult> {
    return UomApi.mutateUom("updateUnitOfMeasure", input);
  }

  public static async deleteUnitOfMeasure(id: number): Promise<UomMutationResult> {
    const client = await UomApi.client();
    const { data, errors } = await client.mutate({
      mutation: gql`
        mutation DeleteUnitOfMeasure($id: Long) {
          deleteUnitOfMeasure(input: $id) {
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
        uom: null,
      };
    }

    const result = data?.deleteUnitOfMeasure;
    return {
      success: result?.status === "Success",
      errors: result?.errors ?? [],
      uom: null,
    };
  }

  private static async mutateUom(
    operation: "createUnitOfMeasure" | "updateUnitOfMeasure",
    input: UomInput
  ): Promise<UomMutationResult> {
    const client = await UomApi.client();
    const gqlInput: Record<string, unknown> = {
      name: input.name,
      symbol: input.symbol,
      uomType: input.uomType,
      baseFactor: input.baseFactor,
      status: input.status,
      organization: input.organization,
    };

    if (input.id != null && input.id > 0) {
      gqlInput.id = input.id;
    }

    try {
      const { data, errors } = await client.mutate({
        mutation: gql`
          mutation MutateUnitOfMeasure($input: UnitOfMeasureEntityInput!) {
            ${operation}(input: $input) {
              status
              errors
              value {
                ${UOM_FIELDS}
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
          uom: null,
        };
      }

      const result = data?.[operation];
      if (!result) {
        return {
          success: false,
          errors: ["No response from server."],
          uom: null,
        };
      }

      return {
        success: result?.status === "Success",
        errors: result?.errors ?? [],
        uom: result?.value ? mapUom(result.value) : null,
      };
    } catch (e: unknown) {
      return { success: false, errors: [formatUomApiError(e)], uom: null };
    }
  }
}
