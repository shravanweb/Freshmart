import { gql } from "@apollo/client";
import GraphQLClientInit from "./GraphQLClientInit";
import LocalDataStore from "./LocalDataStore";

export interface StoreRecord {
  id: number;
  name: string;
  code: string;
  storeType: string;
  address: string;
  phone: string;
  email: string;
  status: string;
  managerUserId: number;
  managerName: string;
  managerEmail: string;
}

export interface StoreInput {
  id?: number;
  name: string;
  code: string;
  storeType: string;
  address: string;
  phone: string;
  email: string;
  status: string;
  organization: number;
  managerUserId?: number | null;
}

export interface StoreMutationResult {
  success: boolean;
  errors: string[];
  store: StoreRecord | null;
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

function mapStore(raw: Record<string, unknown>): StoreRecord {
  const manager = (raw.manager ?? null) as Record<string, unknown> | null;

  return {
    id: parseId(raw.id),
    name: String(raw.name ?? ""),
    code: String(raw.code ?? ""),
    storeType: enumName(raw.storeType),
    address: String(raw.address ?? ""),
    phone: String(raw.phone ?? ""),
    email: String(raw.email ?? ""),
    status: enumName(raw.status),
    managerUserId: manager ? parseId(manager.id) : 0,
    managerName: "",
    managerEmail: manager ? String(manager.email ?? "") : "",
  };
}

const STORE_FIELDS = `
  id
  name
  code
  storeType
  address
  phone
  email
  status
  manager {
    id
    email
  }
`;

export default class StoreApi {
  private static async client() {
    GraphQLClientInit.token = await LocalDataStore.get().getToken();
    return GraphQLClientInit.get();
  }

  public static async getAllStores(organizationId: number): Promise<StoreRecord[]> {
    const client = await StoreApi.client();
    const { data, errors } = await client.query({
      query: gql`
        query GetAllStores($organization: Long) {
          getAllStores(in: { organization: $organization }) {
            items {
              ${STORE_FIELDS}
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

    const items = data?.getAllStores?.items ?? [];
    return items.map((item: Record<string, unknown>) => mapStore(item));
  }

  public static async createStore(input: StoreInput): Promise<StoreMutationResult> {
    return StoreApi.mutateStore("createStore", input);
  }

  public static async updateStore(input: StoreInput): Promise<StoreMutationResult> {
    return StoreApi.mutateStore("updateStore", input);
  }

  public static async deleteStore(id: number): Promise<StoreMutationResult> {
    const client = await StoreApi.client();
    const { data, errors } = await client.mutate({
      mutation: gql`
        mutation DeleteStore($id: Long) {
          deleteStore(input: $id) {
            status
            errors
          }
        }
      `,
      variables: { id },
    });

    if (errors?.length) {
      return { success: false, errors: errors.map((e) => e.message), store: null };
    }

    const result = data?.deleteStore;
    return {
      success: result?.status === "Success",
      errors: result?.errors ?? [],
      store: null,
    };
  }

  private static async mutateStore(
    operation: "createStore" | "updateStore",
    input: StoreInput
  ): Promise<StoreMutationResult> {
    const client = await StoreApi.client();
    const gqlInput: Record<string, unknown> = {
      name: input.name,
      code: input.code,
      storeType: input.storeType,
      status: input.status,
      organization: input.organization,
    };
    if (input.id != null && input.id > 0) {
      gqlInput.id = input.id;
    }
    if (input.address) {
      gqlInput.address = input.address;
    }
    if (input.phone) {
      gqlInput.phone = input.phone;
    }
    if (input.email) {
      gqlInput.email = input.email;
    }
    if (input.managerUserId !== undefined) {
      gqlInput.manager =
        input.managerUserId != null && input.managerUserId > 0
          ? input.managerUserId
          : null;
    }

    try {
      const { data, errors } = await client.mutate({
        mutation: gql`
          mutation MutateStore($input: StoreEntityInput!) {
            ${operation}(input: $input) {
              status
              errors
              value {
                ${STORE_FIELDS}
              }
            }
          }
        `,
        variables: { input: gqlInput },
        errorPolicy: "all",
      });

      if (errors?.length) {
        return { success: false, errors: errors.map((e) => e.message), store: null };
      }

      const result = data?.[operation];
      if (!result) {
        return { success: false, errors: ["No response from server."], store: null };
      }

      return {
        success: result?.status === "Success",
        errors: result?.errors ?? [],
        store: result?.value ? mapStore(result.value) : null,
      };
    } catch (e: unknown) {
      const message = e instanceof Error ? e.message : String(e);
      return { success: false, errors: [message], store: null };
    }
  }
}
