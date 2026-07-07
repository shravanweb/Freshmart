import LoginResult from "./LoginResult";
import Env from "./Env";
import { ApolloClient, gql } from "@apollo/client";
import * as __d3ett from "../rocket/D3ETemplate";
import { D3ETemplate } from "../rocket/D3ETemplate";
import PurchaseOrder from "../models/PurchaseOrder";
import InventoryMovementsByDateRange from "./InventoryMovementsByDateRange";
import UserProfileByUser from "./UserProfileByUser";
import GraphQLClientInit from "../utils/GraphQLClientInit";
import LocalDataStore from "../utils/LocalDataStore";
import PurchaseOrderItem from "./PurchaseOrderItem";
import AllPurchaseOrdersRequest from "../models/AllPurchaseOrdersRequest";
import AllProductsRequest from "../models/AllProductsRequest";
import MessageDispatch from "../rocket/MessageDispatch";
import InventoryMovement from "../models/InventoryMovement";
import OutOfStockItems from "./OutOfStockItems";
import LowStockItems from "./LowStockItems";
import AllInAppNotificationsRequest from "../models/AllInAppNotificationsRequest";
import UserProfileByUserRequest from "../models/UserProfileByUserRequest";
import UnreadNotificationCount from "./UnreadNotificationCount";
import WarehouseStock from "../models/WarehouseStock";
import AllInAppNotifications from "./AllInAppNotifications";
import Product from "../models/Product";
import AllPurchaseOrders from "./AllPurchaseOrders";
import StockBatch from "../models/StockBatch";
import OutOfStockItemsRequest from "../models/OutOfStockItemsRequest";
import OrganizationItem from "./OrganizationItem";
import UserProfile from "../models/UserProfile";
import ExpiringBatchesRequest from "../models/ExpiringBatchesRequest";
import ReferenceCatch from "../utils/ReferenceCatch";
import ExpiringBatches from "./ExpiringBatches";
import UnreadNotificationCountRequest from "../models/UnreadNotificationCountRequest";
import PurchaseOrderItemRequest from "../models/PurchaseOrderItemRequest";
import Organization from "../models/Organization";
import BaseUser from "../models/BaseUser";
import User from "../models/User";
import JSONUtils from "../utils/JSONUtils";
import InAppNotification from "../models/InAppNotification";
import InventoryMovementsByDateRangeRequest from "../models/InventoryMovementsByDateRangeRequest";
import OrganizationItemRequest from "../models/OrganizationItemRequest";
import LowStockItemsRequest from "../models/LowStockItemsRequest";
import AllProducts from "./AllProducts";

export default class Query {
  private _client: ApolloClient<any>;
  private static _queryObject: Query;
  private _referenceCatch: ReferenceCatch;
  private constructor() {}
  private static _init() {
    let q = new Query();

    q._client = GraphQLClientInit.get();

    q._referenceCatch = ReferenceCatch.get();

    return q;
  }
  public static get() {
    if (Query._queryObject == null) {
      Query._queryObject = Query._init();
    }

    return Query._queryObject;
  }

  private userFromToken(token: string, email: string): User {
    try {
      const payload = JSON.parse(
        atob(token.split(".")[1].replace(/-/g, "+").replace(/_/g, "/"))
      );
      const user = new User({ email: email || payload.sub || "" });
      if (payload.id != null) {
        user.id = Number(payload.id);
      }
      return user;
    } catch {
      return new User({ email });
    }
  }

  private async fetchLoginResult(
    operation: string,
    document: string,
    variables: Record<string, string | undefined>
  ): Promise<LoginResult> {
    GraphQLClientInit.token = await LocalDataStore.get().getToken();

    try {
      const { data, errors } = await this._client.query({
        query: gql(document),
        variables,
        fetchPolicy: "no-cache",
      });

      if (errors?.length) {
        return new LoginResult({
          success: false,
          failureMessage: errors[0].message,
        });
      }

      const raw = data?.[operation];
      if (!raw) {
        return new LoginResult({
          success: false,
          failureMessage: "No response from server.",
        });
      }

      const result = new LoginResult({
        success: raw.success ?? false,
        failureMessage: raw.failureMessage ?? raw.loginResult ?? "",
        token: raw.token ?? "",
      });

      if (raw.userObject) {
        result.setUserObject(JSONUtils.fromJson(raw.userObject) as BaseUser);
      } else if (result.success && result.token) {
        const email =
          variables.email?.toLowerCase() ?? variables.username ?? "";
        result.setUserObject(this.userFromToken(result.token, email));
      }

      if (result.success) {
        await LocalDataStore.get().setUser(result.userObject, result.token);
      }

      return result;
    } catch (e) {
      return new LoginResult({
        success: false,
        failureMessage: "Connection failed: " + e.toString(),
      });
    }
  }

  public async currentUser(): Promise<BaseUser> {
    GraphQLClientInit.token = await LocalDataStore.get().getToken();

    return await LocalDataStore.get().currentUser();
  }
  public async logout(): Promise<boolean> {
    LocalDataStore.get().setUser(null, null);

    await MessageDispatch.get().logout();

    return true;
  }
  public async loginInventoryManagementSystemUserWithEmailAndPassword(
    usage: number,
    d3eParams?: Partial<{
      email: string;
      password: string;
      deviceToken: string;
    }>
  ): Promise<LoginResult> {
    return this.fetchLoginResult(
      "loginInventoryManagementSystemUserWithEmailAndPassword",
      `
        query Login($email: String, $password: String) {
          loginInventoryManagementSystemUserWithEmailAndPassword(
            email: $email
            password: $password
          ) {
            success
            failureMessage
            loginResult
            token
            userObject {
              __typename
              ... on User {
                id
                localId
                email
                organization {
                  id
                  localId
                }
              }
            }
          }
        }
      `,
      {
        email: d3eParams?.email,
        password: d3eParams?.password,
      }
    );
  }
  public async sendInventoryManagementSystemPasswordResetOtp(
    email: string
  ): Promise<LoginResult> {
    GraphQLClientInit.token = await LocalDataStore.get().getToken();

    const { data } = await this._client.query({
      query: gql`
        query SendPasswordResetOtp($email: String) {
          sendInventoryManagementSystemPasswordResetOtp(email: $email) {
            success
            failureMessage
            token
          }
        }
      `,
      variables: { email },
      fetchPolicy: "no-cache",
    });

    const result = data.sendInventoryManagementSystemPasswordResetOtp;

    return new LoginResult({
      success: result.success,
      failureMessage: result.failureMessage ?? "",
      token: result.token ?? "",
    });
  }

  public async resetInventoryManagementSystemPasswordWithOtp(
    token: string,
    code: string,
    newPassword: string
  ): Promise<LoginResult> {
    GraphQLClientInit.token = await LocalDataStore.get().getToken();

    const { data } = await this._client.query({
      query: gql`
        query ResetPasswordWithOtp(
          $token: String
          $code: String
          $newPassword: String
        ) {
          resetInventoryManagementSystemPasswordWithOtp(
            token: $token
            code: $code
            newPassword: $newPassword
          ) {
            success
            failureMessage
          }
        }
      `,
      variables: { token, code, newPassword },
      fetchPolicy: "no-cache",
    });

    const result = data.resetInventoryManagementSystemPasswordWithOtp;

    return new LoginResult({
      success: result.success,
      failureMessage: result.failureMessage ?? "",
    });
  }

  public async registerInventoryManagementSystemUser(
    usage: number,
    d3eParams?: Partial<{
      displayName: string;
      email: string;
      password: string;
      appRole: string;
      deviceToken: string;
    }>
  ): Promise<LoginResult> {
    return this.fetchLoginResult(
      "registerInventoryManagementSystemUser",
      `
        query Register($displayName: String, $email: String, $password: String, $appRole: AppUserRole) {
          registerInventoryManagementSystemUser(
            displayName: $displayName
            email: $email
            password: $password
            appRole: $appRole
          ) {
            success
            failureMessage
            token
            userObject {
              __typename
              ... on User {
                id
                localId
                email
                organization {
                  id
                  localId
                }
              }
            }
          }
        }
      `,
      {
        displayName: d3eParams?.displayName,
        email: d3eParams?.email,
        password: d3eParams?.password,
        appRole: d3eParams?.appRole ?? "Viewer",
      }
    );
  }
  public async getAllInAppNotifications(
    usage: number,
    request: AllInAppNotificationsRequest,
    d3eParams?: Partial<{ synchronize: boolean }>
  ): Promise<AllInAppNotifications> {
    let synchronize = d3eParams?.synchronize;

    return MessageDispatch.get().dataQuery(
      "AllInAppNotifications",
      usage,
      true,
      request,
      { synchronize: synchronize }
    );
  }
  public async getAllProducts(
    usage: number,
    request: AllProductsRequest,
    d3eParams?: Partial<{ synchronize: boolean }>
  ): Promise<AllProducts> {
    let synchronize = d3eParams?.synchronize;

    return MessageDispatch.get().dataQuery(
      "AllProducts",
      usage,
      true,
      request,
      { synchronize: synchronize }
    );
  }
  public async getAllPurchaseOrders(
    usage: number,
    request: AllPurchaseOrdersRequest,
    d3eParams?: Partial<{ synchronize: boolean }>
  ): Promise<AllPurchaseOrders> {
    let synchronize = d3eParams?.synchronize;

    return MessageDispatch.get().dataQuery(
      "AllPurchaseOrders",
      usage,
      true,
      request,
      { synchronize: synchronize }
    );
  }
  public async getExpiringBatches(
    usage: number,
    request: ExpiringBatchesRequest,
    d3eParams?: Partial<{ synchronize: boolean }>
  ): Promise<ExpiringBatches> {
    let synchronize = d3eParams?.synchronize;

    return MessageDispatch.get().dataQuery(
      "ExpiringBatches",
      usage,
      true,
      request,
      { synchronize: synchronize }
    );
  }
  public async getInventoryMovementsByDateRange(
    usage: number,
    request: InventoryMovementsByDateRangeRequest,
    d3eParams?: Partial<{ synchronize: boolean }>
  ): Promise<InventoryMovementsByDateRange> {
    let synchronize = d3eParams?.synchronize;

    return MessageDispatch.get().dataQuery(
      "InventoryMovementsByDateRange",
      usage,
      true,
      request,
      { synchronize: synchronize }
    );
  }
  public async getLowStockItems(
    usage: number,
    request: LowStockItemsRequest,
    d3eParams?: Partial<{ synchronize: boolean }>
  ): Promise<LowStockItems> {
    let synchronize = d3eParams?.synchronize;

    return MessageDispatch.get().dataQuery(
      "LowStockItems",
      usage,
      true,
      request,
      { synchronize: synchronize }
    );
  }
  public async getOrganizationItem(
    usage: number,
    request: OrganizationItemRequest,
    d3eParams?: Partial<{ synchronize: boolean }>
  ): Promise<OrganizationItem> {
    let synchronize = d3eParams?.synchronize;

    return MessageDispatch.get().dataQuery(
      "OrganizationItem",
      usage,
      true,
      request,
      { synchronize: synchronize }
    );
  }
  public async getOutOfStockItems(
    usage: number,
    request: OutOfStockItemsRequest,
    d3eParams?: Partial<{ synchronize: boolean }>
  ): Promise<OutOfStockItems> {
    let synchronize = d3eParams?.synchronize;

    return MessageDispatch.get().dataQuery(
      "OutOfStockItems",
      usage,
      true,
      request,
      { synchronize: synchronize }
    );
  }
  public async getPurchaseOrderItem(
    usage: number,
    request: PurchaseOrderItemRequest,
    d3eParams?: Partial<{ synchronize: boolean }>
  ): Promise<PurchaseOrderItem> {
    let synchronize = d3eParams?.synchronize;

    return MessageDispatch.get().dataQuery(
      "PurchaseOrderItem",
      usage,
      true,
      request,
      { synchronize: synchronize }
    );
  }
  public async getUnreadNotificationCount(
    usage: number,
    request: UnreadNotificationCountRequest,
    d3eParams?: Partial<{ synchronize: boolean }>
  ): Promise<UnreadNotificationCount> {
    let synchronize = d3eParams?.synchronize;

    return MessageDispatch.get().dataQuery(
      "UnreadNotificationCount",
      usage,
      true,
      request,
      { synchronize: synchronize }
    );
  }
  public async getUserProfileByUser(
    usage: number,
    request: UserProfileByUserRequest,
    d3eParams?: Partial<{ synchronize: boolean }>
  ): Promise<UserProfileByUser> {
    let synchronize = d3eParams?.synchronize;

    return MessageDispatch.get().dataQuery(
      "UserProfileByUser",
      usage,
      true,
      request,
      { synchronize: synchronize }
    );
  }
}
