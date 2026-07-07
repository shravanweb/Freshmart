import Env from "../classes/Env";
import { CartItem } from "./CartSession";

export type PaymentMethod = "cod" | "online";

export interface CheckoutRequest {
  paymentMethod: PaymentMethod;
  customerName: string;
  customerPhone: string;
  deliveryCity: string;
  deliveryAddress: string;
  deliveryFee: number;
  items: CartItem[];
}

export interface CheckoutResult {
  success: boolean;
  orderId?: number;
  orderNumber?: string;
  paymentMethod?: string;
  totalAmount?: number;
  deliveryFee?: number;
  errors?: string[];
}

export default class CustomerCheckoutApi {
  public static async placeOrder(request: CheckoutRequest): Promise<CheckoutResult> {
    const baseUrl = Env.get().resolvedHttpUrl;
    const response = await fetch(`${baseUrl}/api/public/checkout`, {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        paymentMethod: request.paymentMethod,
        customerName: request.customerName,
        customerPhone: request.customerPhone,
        deliveryCity: request.deliveryCity,
        deliveryAddress: request.deliveryAddress,
        deliveryFee: request.deliveryFee,
        items: request.items.map((item) => ({
          productId: item.productId,
          quantity: item.quantity,
          unitPrice: item.sellingPrice,
        })),
      }),
    });

    let data: CheckoutResult;
    try {
      data = (await response.json()) as CheckoutResult;
    } catch {
      return {
        success: false,
        errors: [
          response.status === 404
            ? "Checkout service is unavailable. Please restart the server and try again."
            : "Checkout failed. Please try again.",
        ],
      };
    }

    if (!response.ok && (!data.errors || data.errors.length === 0)) {
      return {
        success: false,
        errors: ["Checkout failed. Please try again."],
      };
    }
    return data;
  }
}
