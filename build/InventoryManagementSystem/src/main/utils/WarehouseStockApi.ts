import { gql } from "@apollo/client";
import GraphQLClientInit from "./GraphQLClientInit";
import LocalDataStore from "./LocalDataStore";

export interface StockRecord {
  id: number;
  warehouseId: number;
  warehouseName: string;
  productId: number;
  productName: string;
  productSku: string;
  quantityOnHand: number;
  quantityAvailable: number;
  averageCost: number;
}

export interface StockInput {
  id?: number;
  warehouse: number;
  product: number;
  quantityOnHand: number;
  quantityReserved?: number;
  averageCost?: number;
  organization: number;
}

export interface StockMutationResult {
  success: boolean;
  errors: string[];
}

function parseId(value: unknown): number {
  if (value == null) return 0;
  if (typeof value === "number" && Number.isFinite(value)) return value;
  if (typeof value === "string" && value.trim() !== "") {
    const parsed = Number(value);
    return Number.isFinite(parsed) ? parsed : 0;
  }
  return 0;
}

function parseNumber(value: unknown): number {
  if (value == null) return 0;
  const parsed = Number(value);
  return Number.isFinite(parsed) ? parsed : 0;
}

function mapStock(raw: Record<string, unknown>): StockRecord {
  const warehouse = (raw.warehouse ?? {}) as Record<string, unknown>;
  const product = (raw.product ?? {}) as Record<string, unknown>;
  return {
    id: parseId(raw.id),
    warehouseId: parseId(warehouse.id),
    warehouseName: String(warehouse.name ?? ""),
    productId: parseId(product.id),
    productName: String(product.name ?? ""),
    productSku: String(product.sku ?? ""),
    quantityOnHand: parseNumber(raw.quantityOnHand),
    quantityAvailable: parseNumber(raw.quantityAvailable),
    averageCost: parseNumber(raw.averageCost),
  };
}

const STOCK_FIELDS = `
  id
  quantityOnHand
  quantityReserved
  quantityAvailable
  averageCost
  warehouse { id name }
  product { id name sku }
`;

export default class WarehouseStockApi {
  private static async client() {
    GraphQLClientInit.token = await LocalDataStore.get().getToken();
    return GraphQLClientInit.get();
  }

  public static async getStockLevels(
    organizationId: number,
    warehouseId?: number
  ): Promise<StockRecord[]> {
    const client = await WarehouseStockApi.client();
    const { data, errors } = await client.query({
      query: gql`
        query GetStockValuation($organization: Long, $warehouse: Long) {
          getStockValuationReport(in: { organization: $organization, warehouse: $warehouse }) {
            items { ${STOCK_FIELDS} }
          }
        }
      `,
      variables: {
        organization: organizationId,
        warehouse: warehouseId ?? null,
      },
      fetchPolicy: "no-cache",
    });

    if (errors?.length) {
      throw new Error(errors.map((e) => e.message).join("; "));
    }

    const items = data?.getStockValuationReport?.items ?? [];
    return items.map((item: Record<string, unknown>) => mapStock(item)).toList();
  }

  public static async upsertStock(input: StockInput): Promise<StockMutationResult> {
    const existing = await WarehouseStockApi.getStockLevels(input.organization, input.warehouse);
    const match = existing.find(
      (item) => item.productId === input.product && item.warehouseId === input.warehouse
    );

    if (match?.id) {
      return WarehouseStockApi.updateStock({
        ...input,
        id: match.id,
        quantityOnHand: match.quantityOnHand + input.quantityOnHand,
      });
    }

    return WarehouseStockApi.createStock(input);
  }

  public static async createStock(input: StockInput): Promise<StockMutationResult> {
    return WarehouseStockApi.mutateStock("createWarehouseStock", input);
  }

  public static async updateStock(input: StockInput): Promise<StockMutationResult> {
    if (!input.id) {
      return { success: false, errors: ["Stock ID is required for update."] };
    }
    return WarehouseStockApi.mutateStock("updateWarehouseStock", input);
  }

  private static async mutateStock(
    operation: "createWarehouseStock" | "updateWarehouseStock",
    input: StockInput
  ): Promise<StockMutationResult> {
    const client = await WarehouseStockApi.client();
    const gqlInput: Record<string, unknown> = {
      warehouse: input.warehouse,
      product: input.product,
      quantityOnHand: input.quantityOnHand,
      quantityReserved: input.quantityReserved ?? 0,
      organization: input.organization,
    };

    if (input.id != null && input.id > 0) gqlInput.id = input.id;
    if (input.averageCost != null) gqlInput.averageCost = input.averageCost;

    try {
      const { data, errors } = await client.mutate({
        mutation: gql`
          mutation MutateWarehouseStock($input: WarehouseStockEntityInput!) {
            ${operation}(input: $input) {
              status
              errors
              value { id }
            }
          }
        `,
        variables: { input: gqlInput },
        errorPolicy: "all",
      });

      if (errors?.length) {
        return { success: false, errors: errors.map((e) => e.message) };
      }

      const result = data?.[operation];
      return {
        success: result?.status === "Success",
        errors: result?.errors ?? [],
      };
    } catch (e: unknown) {
      const message = e instanceof Error ? e.message : String(e);
      return { success: false, errors: [message] };
    }
  }
}
