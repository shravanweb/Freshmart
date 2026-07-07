import { gql } from "@apollo/client";
import Env from "../classes/Env";
import GraphQLClientInit from "./GraphQLClientInit";
import LocalDataStore from "./LocalDataStore";

export interface PurchaseOrderInput {
  id?: number;
  poNumber?: string;
  vendor: number;
  warehouse: number;
  orderDate: string;
  expectedDeliveryDate?: string;
  status?: string;
  taxAmount?: number;
  notes?: string;
  organization: number;
}

export interface PurchaseOrderLineInput {
  id?: number;
  lineNumber: number;
  product: number;
  orderedQuantity: number;
  unitPrice: number;
  uom?: number;
  purchaseOrder: number;
}

export interface PurchaseOrderLineRecord {
  id: number;
  lineNumber: number;
  productId: number;
  orderedQuantity: number;
  unitPrice: number;
}

export interface PurchaseOrderMutationResult {
  success: boolean;
  errors: string[];
  purchaseOrderId: number;
}

export interface PurchaseOrderNotifyResult {
  success: boolean;
  errors: string[];
  message: string;
}

const PO_FIELDS = `
  id
  poNumber
  status
`;

export default class PurchaseOrderApi {
  private static generatePoNumber(): string {
    const year = new Date().getFullYear();
    const suffix = Date.now().toString().slice(-6);
    return `PO-${year}-${suffix}`;
  }

  private static async client() {
    GraphQLClientInit.token = await LocalDataStore.get().getToken();
    return GraphQLClientInit.get();
  }

  public static async createPurchaseOrder(
    input: PurchaseOrderInput
  ): Promise<PurchaseOrderMutationResult> {
    return PurchaseOrderApi.mutatePo("createPurchaseOrder", input);
  }

  public static async updatePurchaseOrder(
    input: PurchaseOrderInput
  ): Promise<PurchaseOrderMutationResult> {
    return PurchaseOrderApi.mutatePo("updatePurchaseOrder", input);
  }

  public static async deletePurchaseOrder(
    id: number
  ): Promise<PurchaseOrderMutationResult> {
    const client = await PurchaseOrderApi.client();
    try {
      const { data, errors } = await client.mutate({
        mutation: gql`
          mutation DeletePurchaseOrder($id: Long) {
            deletePurchaseOrder(input: $id) {
              status
              errors
            }
          }
        `,
        variables: { id },
        errorPolicy: "all",
      });

      if (errors?.length) {
        return {
          success: false,
          errors: errors.map((e) => e.message),
          purchaseOrderId: 0,
        };
      }

      const result = data?.deletePurchaseOrder;
      return {
        success: result?.status === "Success",
        errors: result?.errors ?? [],
        purchaseOrderId: 0,
      };
    } catch (e: unknown) {
      const message = e instanceof Error ? e.message : String(e);
      return { success: false, errors: [message], purchaseOrderId: 0 };
    }
  }

  public static async getPurchaseOrderLines(
    purchaseOrderId: number
  ): Promise<PurchaseOrderLineRecord[]> {
    const token = await LocalDataStore.get().getToken();
    try {
      const response = await fetch(
        Env.get().resolvedHttpUrl +
          `/api/purchase-order/${purchaseOrderId}/lines`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            ...(token ? { Authorization: "Bearer " + token } : {}),
          },
        }
      );

      const bodyText = await response.text();
      let payload: unknown = [];
      try {
        payload = bodyText ? JSON.parse(bodyText) : [];
      } catch (_ignored) {
        payload = [];
      }

      if (!response.ok) {
        if (response.status === 404) {
          throw new Error(
            "Lines API not found. Restart Spring Boot backend and try again."
          );
        }
        throw new Error("Failed to load purchase order line items.");
      }

      if (!Array.isArray(payload)) {
        return [];
      }

      const lines: PurchaseOrderLineRecord[] = [];
      for (const item of payload as Record<string, unknown>[]) {
        lines.push({
          id: Number(item.id ?? 0),
          lineNumber: Number(item.lineNumber ?? 0),
          productId: Number(item.productId ?? 0),
          orderedQuantity: Number(item.orderedQuantity ?? 0),
          unitPrice: Number(item.unitPrice ?? 0),
        });
      }
      lines.sort((a, b) => a.lineNumber - b.lineNumber);
      return lines;
    } catch (e: unknown) {
      const message = e instanceof Error ? e.message : String(e);
      throw new Error(message);
    }
  }

  public static async notifySupplier(
    purchaseOrderId: number
  ): Promise<PurchaseOrderNotifyResult> {
    const token = await LocalDataStore.get().getToken();
    try {
      const response = await fetch(
        Env.get().resolvedHttpUrl +
          `/api/purchase-order/${purchaseOrderId}/notify-supplier`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            ...(token ? { Authorization: "Bearer " + token } : {}),
          },
          body: "{}",
        }
      );

      const bodyText = await response.text();
      let payload: Record<string, unknown> = {};
      try {
        payload = bodyText ? JSON.parse(bodyText) : {};
      } catch (_ignored) {
        payload = {};
      }

      if (!response.ok) {
        if (response.status === 404) {
          return {
            success: false,
            errors: [
              "Email API not found. Restart Spring Boot backend (build/SpringBoot) and try again.",
            ],
            message: "",
          };
        }
        return {
          success: false,
          errors: [
            typeof payload?.message === "string"
              ? payload.message
              : Array.isArray(payload?.errors) && payload.errors.length > 0
              ? String(payload.errors[0])
              : "Supplier notification request failed.",
          ],
          message: "",
        };
      }

      const notifyErrors: string[] = [];
      if (Array.isArray(payload?.errors)) {
        for (const item of payload.errors as unknown[]) {
          notifyErrors.push(String(item));
        }
      }

      return {
        success: Boolean(payload?.success),
        errors: notifyErrors,
        message: typeof payload?.message === "string" ? payload.message : "",
      };
    } catch (e: unknown) {
      const message = e instanceof Error ? e.message : String(e);
      return { success: false, errors: [message], message: "" };
    }
  }

  public static async createPurchaseOrderLine(
    input: PurchaseOrderLineInput
  ): Promise<{ success: boolean; errors: string[] }> {
    const client = await PurchaseOrderApi.client();
    const gqlInput: Record<string, unknown> = {
      lineNumber: input.lineNumber,
      product: input.product,
      orderedQuantity: input.orderedQuantity,
      unitPrice: input.unitPrice,
      purchaseOrder: input.purchaseOrder,
    };

    if (input.uom != null && input.uom > 0) {
      gqlInput.uom = input.uom;
    }

    try {
      const { data, errors } = await client.mutate({
        mutation: gql`
          mutation CreatePurchaseOrderLine($input: PurchaseOrderLineEntityInput!) {
            createPurchaseOrderLine(input: $input) {
              status
              errors
              value {
                id
                lineNumber
              }
            }
          }
        `,
        variables: { input: gqlInput },
        errorPolicy: "all",
      });

      if (errors?.length) {
        return { success: false, errors: errors.map((e) => e.message) };
      }

      const result = data?.createPurchaseOrderLine;
      return {
        success: result?.status === "Success",
        errors: result?.errors ?? [],
      };
    } catch (e: unknown) {
      const message = e instanceof Error ? e.message : String(e);
      return { success: false, errors: [message] };
    }
  }

  private static async mutatePo(
    operation: "createPurchaseOrder" | "updatePurchaseOrder",
    input: PurchaseOrderInput
  ): Promise<PurchaseOrderMutationResult> {
    const client = await PurchaseOrderApi.client();
    const gqlInput: Record<string, unknown> = {
      vendor: input.vendor,
      warehouse: input.warehouse,
      orderDate: input.orderDate,
      status: input.status ?? "Draft",
      organization: input.organization,
    };

    if (input.id != null && input.id > 0) {
      gqlInput.id = input.id;
    }
    if (input.expectedDeliveryDate) {
      gqlInput.expectedDeliveryDate = input.expectedDeliveryDate;
    }
    if (input.taxAmount != null) {
      gqlInput.taxAmount = input.taxAmount;
    }
    if (input.notes) {
      gqlInput.notes = input.notes;
    }
    if (operation === "createPurchaseOrder") {
      gqlInput.poNumber = input.poNumber?.trim() || PurchaseOrderApi.generatePoNumber();
    } else if (input.poNumber?.trim()) {
      gqlInput.poNumber = input.poNumber.trim();
    }

    try {
      const { data, errors } = await client.mutate({
        mutation: gql`
          mutation MutatePurchaseOrder($input: PurchaseOrderEntityInput!) {
            ${operation}(input: $input) {
              status
              errors
              value {
                ${PO_FIELDS}
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
          purchaseOrderId: 0,
        };
      }

      const result = data?.[operation];
      if (!result) {
        return {
          success: false,
          errors: ["No response from server."],
          purchaseOrderId: 0,
        };
      }

      const poId = Number(result?.value?.id ?? 0);
      return {
        success: result?.status === "Success",
        errors: result?.errors ?? [],
        purchaseOrderId: Number.isFinite(poId) ? poId : 0,
      };
    } catch (e: unknown) {
      const message = e instanceof Error ? e.message : String(e);
      return { success: false, errors: [message], purchaseOrderId: 0 };
    }
  }
}
