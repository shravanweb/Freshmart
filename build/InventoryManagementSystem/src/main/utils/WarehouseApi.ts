import { gql } from "@apollo/client";
import GraphQLClientInit from "./GraphQLClientInit";
import LocalDataStore from "./LocalDataStore";

export interface WarehouseRecord {
  id: number;
  name: string;
  code: string;
  warehouseType: string;
  address: string;
  isDefault: boolean;
  status: string;
}

export interface WarehouseInput {
  id?: number;
  name: string;
  code: string;
  warehouseType: string;
  address: string;
  isDefault: boolean;
  status: string;
  organization: number;
}

export interface WarehouseMutationResult {
  success: boolean;
  errors: string[];
  warehouse: WarehouseRecord | null;
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

function mapWarehouse(raw: Record<string, unknown>): WarehouseRecord {
  return {
    id: parseId(raw.id),
    name: String(raw.name ?? ""),
    code: String(raw.code ?? ""),
    warehouseType: enumName(raw.warehouseType),
    address: String(raw.address ?? ""),
    isDefault: Boolean(raw.isDefault),
    status: enumName(raw.status),
  };
}

const WAREHOUSE_FIELDS = `
  id
  name
  code
  warehouseType
  address
  isDefault
  status
`;

export default class WarehouseApi {
  private static async client() {
    GraphQLClientInit.token = await LocalDataStore.get().getToken();
    return GraphQLClientInit.get();
  }

  public static async getAllWarehouses(
    organizationId: number
  ): Promise<WarehouseRecord[]> {
    const client = await WarehouseApi.client();
    const { data, errors } = await client.query({
      query: gql`
        query GetAllWarehouses($organization: Long) {
          getAllWarehouses(in: { organization: $organization }) {
            items {
              ${WAREHOUSE_FIELDS}
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

    const items = data?.getAllWarehouses?.items ?? [];
    return items.map((item: Record<string, unknown>) => mapWarehouse(item));
  }

  public static async createWarehouse(
    input: WarehouseInput
  ): Promise<WarehouseMutationResult> {
    return WarehouseApi.mutateWarehouse("createWarehouse", input);
  }

  public static async updateWarehouse(
    input: WarehouseInput
  ): Promise<WarehouseMutationResult> {
    return WarehouseApi.mutateWarehouse("updateWarehouse", input);
  }

  public static async deleteWarehouse(
    id: number
  ): Promise<WarehouseMutationResult> {
    const client = await WarehouseApi.client();
    const { data, errors } = await client.mutate({
      mutation: gql`
        mutation DeleteWarehouse($id: Long) {
          deleteWarehouse(input: $id) {
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
        warehouse: null,
      };
    }

    const result = data?.deleteWarehouse;
    return {
      success: result?.status === "Success",
      errors: result?.errors ?? [],
      warehouse: null,
    };
  }

  private static async mutateWarehouse(
    operation: "createWarehouse" | "updateWarehouse",
    input: WarehouseInput
  ): Promise<WarehouseMutationResult> {
    const client = await WarehouseApi.client();
    const gqlInput: Record<string, unknown> = {
      name: input.name,
      code: input.code,
      warehouseType: input.warehouseType,
      status: input.status,
      isDefault: input.isDefault,
      organization: input.organization,
    };

    if (input.id != null && input.id > 0) {
      gqlInput.id = input.id;
    }
    if (input.address) {
      gqlInput.address = input.address;
    }

    try {
      const { data, errors } = await client.mutate({
        mutation: gql`
          mutation MutateWarehouse($input: WarehouseEntityInput!) {
            ${operation}(input: $input) {
              status
              errors
              value {
                ${WAREHOUSE_FIELDS}
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
          warehouse: null,
        };
      }

      const result = data?.[operation];
      if (!result) {
        return {
          success: false,
          errors: ["No response from server."],
          warehouse: null,
        };
      }

      return {
        success: result?.status === "Success",
        errors: result?.errors ?? [],
        warehouse: result?.value ? mapWarehouse(result.value) : null,
      };
    } catch (e: unknown) {
      const message = e instanceof Error ? e.message : String(e);
      return { success: false, errors: [message], warehouse: null };
    }
  }
}
