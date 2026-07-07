import { gql } from "@apollo/client";
import GraphQLClientInit from "./GraphQLClientInit";
import LocalDataStore from "./LocalDataStore";

export interface SupplierRecord {
  id: number;
  name: string;
  code: string;
  contactPerson: string;
  email: string;
  phone: string;
  address: string;
  paymentTerms: string;
  taxId: string;
  rating: number | null;
  status: string;
}

export interface SupplierInput {
  id?: number;
  name: string;
  code: string;
  contactPerson: string;
  email: string;
  phone: string;
  address: string;
  paymentTerms: string;
  taxId: string;
  rating?: number | null;
  status: string;
  organization: number;
}

export interface SupplierMutationResult {
  success: boolean;
  errors: string[];
  supplier: SupplierRecord | null;
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

function mapSupplier(raw: Record<string, unknown>): SupplierRecord {
  const ratingRaw = raw.rating;
  let rating: number | null = null;
  if (ratingRaw != null && ratingRaw !== "") {
    const parsed = Number(ratingRaw);
    rating = Number.isFinite(parsed) ? parsed : null;
  }

  return {
    id: parseId(raw.id),
    name: String(raw.name ?? ""),
    code: String(raw.code ?? ""),
    contactPerson: String(raw.contactPerson ?? ""),
    email: String(raw.email ?? ""),
    phone: String(raw.phone ?? ""),
    address: String(raw.address ?? ""),
    paymentTerms: String(raw.paymentTerms ?? ""),
    taxId: String(raw.taxId ?? ""),
    rating,
    status: enumName(raw.status),
  };
}

const SUPPLIER_FIELDS = `
  id
  name
  code
  contactPerson
  email
  phone
  address
  paymentTerms
  taxId
  rating
  status
`;

export default class SupplierApi {
  private static async client() {
    GraphQLClientInit.token = await LocalDataStore.get().getToken();
    return GraphQLClientInit.get();
  }

  public static async getAllSuppliers(
    organizationId: number
  ): Promise<SupplierRecord[]> {
    const client = await SupplierApi.client();
    const { data, errors } = await client.query({
      query: gql`
        query GetAllSuppliers($organization: Long) {
          getAllSuppliers(in: { organization: $organization }) {
            items {
              ${SUPPLIER_FIELDS}
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

    const items = data?.getAllSuppliers?.items ?? [];
    return items.map((item: Record<string, unknown>) => mapSupplier(item));
  }

  public static async createSupplier(
    input: SupplierInput
  ): Promise<SupplierMutationResult> {
    return SupplierApi.mutateSupplier("createVendor", input);
  }

  public static async updateSupplier(
    input: SupplierInput
  ): Promise<SupplierMutationResult> {
    return SupplierApi.mutateSupplier("updateVendor", input);
  }

  public static async deleteSupplier(id: number): Promise<SupplierMutationResult> {
    const client = await SupplierApi.client();
    const { data, errors } = await client.mutate({
      mutation: gql`
        mutation DeleteVendor($id: Long) {
          deleteVendor(input: $id) {
            status
            errors
          }
        }
      `,
      variables: { id },
    });

    if (errors?.length) {
      return { success: false, errors: errors.map((e) => e.message), supplier: null };
    }

    const result = data?.deleteVendor;
    return {
      success: result?.status === "Success",
      errors: result?.errors ?? [],
      supplier: null,
    };
  }

  private static async mutateSupplier(
    operation: "createVendor" | "updateVendor",
    input: SupplierInput
  ): Promise<SupplierMutationResult> {
    const client = await SupplierApi.client();
    const gqlInput: Record<string, unknown> = {
      name: input.name,
      code: input.code,
      status: input.status,
      organization: input.organization,
    };

    if (input.id != null && input.id > 0) {
      gqlInput.id = input.id;
    }
    if (input.contactPerson) {
      gqlInput.contactPerson = input.contactPerson;
    }
    if (input.email) {
      gqlInput.email = input.email;
    }
    if (input.phone) {
      gqlInput.phone = input.phone;
    }
    if (input.address) {
      gqlInput.address = input.address;
    }
    if (input.paymentTerms) {
      gqlInput.paymentTerms = input.paymentTerms;
    }
    if (input.taxId) {
      gqlInput.taxId = input.taxId;
    }
    if (input.rating != null) {
      gqlInput.rating = input.rating;
    }

    try {
      const { data, errors } = await client.mutate({
        mutation: gql`
          mutation MutateVendor($input: VendorEntityInput!) {
            ${operation}(input: $input) {
              status
              errors
              value {
                ${SUPPLIER_FIELDS}
              }
            }
          }
        `,
        variables: { input: gqlInput },
        errorPolicy: "all",
      });

      if (errors?.length) {
        return { success: false, errors: errors.map((e) => e.message), supplier: null };
      }

      const result = data?.[operation];
      if (!result) {
        return { success: false, errors: ["No response from server."], supplier: null };
      }

      return {
        success: result?.status === "Success",
        errors: result?.errors ?? [],
        supplier: result?.value ? mapSupplier(result.value) : null,
      };
    } catch (e: unknown) {
      const message = e instanceof Error ? e.message : String(e);
      return { success: false, errors: [message], supplier: null };
    }
  }
}
