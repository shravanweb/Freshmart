import { gql } from "@apollo/client";
import Env from "../classes/Env";
import GraphQLClientInit from "./GraphQLClientInit";
import LocalDataStore from "./LocalDataStore";

export interface StaffUserRecord {
  id: number;
  userId: number;
  displayName: string;
  email: string;
  appRole: string;
  status: string;
  avatarUrl: string;
}

export interface CreateStaffInput {
  displayName: string;
  email: string;
  password: string;
  appRole: string;
}

export interface UpdateStaffInput {
  profileId: number;
  userId: number;
  organizationId: number;
  displayName: string;
  appRole: string;
  status: string;
  password?: string;
}

export interface StaffMutationResult {
  success: boolean;
  errors: string[];
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

function resolveAvatarUrl(avatarId: string): string {
  if (!avatarId) {
    return "";
  }
  const path = `/api/download/${avatarId}?inline=true`;
  const base = Env.get().resolvedHttpUrl.replace(/\/$/, "");
  return `${base}${path}`;
}

function mapStaffUser(raw: Record<string, unknown>): StaffUserRecord {
  const user = (raw.user ?? {}) as Record<string, unknown>;
  const avatar = (raw.avatar ?? {}) as Record<string, unknown>;
  const avatarId = String(avatar.id ?? "");

  return {
    id: parseId(raw.id),
    userId: parseId(user.id),
    displayName: String(raw.displayName ?? ""),
    email: String(user.email ?? ""),
    appRole: enumName(raw.appRole),
    status: enumName(raw.status),
    avatarUrl: avatarId ? resolveAvatarUrl(avatarId) : "",
  };
}

const STAFF_USER_FIELDS = `
  id
  displayName
  appRole
  status
  avatar {
    id
  }
  user {
    id
    email
  }
`;

export default class StaffUserApi {
  private static async client() {
    GraphQLClientInit.token = await LocalDataStore.get().getToken();
    return GraphQLClientInit.get();
  }

  public static async getAllStaffUsers(
    organizationId: number
  ): Promise<StaffUserRecord[]> {
    const client = await StaffUserApi.client();
    const { data, errors } = await client.query({
      query: gql`
        query GetAllUserProfiles($organization: Long) {
          getAllUserProfiles(in: { organization: $organization }) {
            items {
              ${STAFF_USER_FIELDS}
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

    const items = data?.getAllUserProfiles?.items ?? [];
    return items
      .map((item: Record<string, unknown>) => mapStaffUser(item))
      .filter((item: StaffUserRecord) => item.appRole !== "Viewer");
  }

  public static async getStoreManagers(
    organizationId: number
  ): Promise<StaffUserRecord[]> {
    const staff = await StaffUserApi.getAllStaffUsers(organizationId);
    return staff.filter((item) => item.appRole === "StoreManager");
  }

  public static async createStaffUser(
    input: CreateStaffInput
  ): Promise<StaffMutationResult> {
    const client = await StaffUserApi.client();

    try {
      const { data, errors } = await client.query({
        query: gql`
          query CreateStaffUser(
            $displayName: String
            $email: String
            $password: String
            $appRole: AppUserRole
          ) {
            createStaffUser(
              displayName: $displayName
              email: $email
              password: $password
              appRole: $appRole
            ) {
              success
              failureMessage
            }
          }
        `,
        variables: {
          displayName: input.displayName,
          email: input.email,
          password: input.password,
          appRole: input.appRole,
        },
        fetchPolicy: "no-cache",
      });

      if (errors?.length) {
        return { success: false, errors: errors.map((e) => e.message) };
      }

      const result = data?.createStaffUser;
      if (!result) {
        return { success: false, errors: ["No response from server."] };
      }

      return {
        success: Boolean(result.success),
        errors: result.success ? [] : [result.failureMessage ?? "Failed to create user."],
      };
    } catch (e: unknown) {
      const message = e instanceof Error ? e.message : String(e);
      return { success: false, errors: [message] };
    }
  }

  public static async updateStaffUser(
    input: UpdateStaffInput
  ): Promise<StaffMutationResult> {
    const client = await StaffUserApi.client();

    try {
      const profileInput: Record<string, unknown> = {
        id: input.profileId,
        displayName: input.displayName,
        appRole: input.appRole,
        status: input.status,
        organization: input.organizationId,
      };

      const { data: profileData, errors: profileErrors } = await client.mutate({
        mutation: gql`
          mutation UpdateUserProfile($input: UserProfileEntityInput!) {
            updateUserProfile(input: $input) {
              status
              errors
              value {
                id
              }
            }
          }
        `,
        variables: { input: profileInput },
        errorPolicy: "all",
      });

      if (profileErrors?.length) {
        return { success: false, errors: profileErrors.map((e) => e.message) };
      }

      const profileResult = profileData?.updateUserProfile;
      if (!profileResult || profileResult.status !== "Success") {
        return {
          success: false,
          errors: profileResult?.errors ?? ["Failed to update user profile."],
        };
      }

      const userInput: Record<string, unknown> = {
        id: input.userId,
        role: input.appRole,
        status: input.status,
        organization: input.organizationId,
      };
      if (input.password && input.password.length >= 6) {
        userInput.password = input.password;
      }

      const { data: userData, errors: userErrors } = await client.mutate({
        mutation: gql`
          mutation UpdateUser($input: UserEntityInput!) {
            updateUser(input: $input) {
              status
              errors
              value {
                id
              }
            }
          }
        `,
        variables: { input: userInput },
        errorPolicy: "all",
      });

      if (userErrors?.length) {
        return { success: false, errors: userErrors.map((e) => e.message) };
      }

      const userResult = userData?.updateUser;
      if (!userResult || userResult.status !== "Success") {
        return {
          success: false,
          errors: userResult?.errors ?? ["Failed to update user account."],
        };
      }

      return { success: true, errors: [] };
    } catch (e: unknown) {
      const message = e instanceof Error ? e.message : String(e);
      return { success: false, errors: [message] };
    }
  }
}
