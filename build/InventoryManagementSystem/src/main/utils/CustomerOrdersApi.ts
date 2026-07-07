import Env from "../classes/Env";

export interface CustomerOrderRecord {
  id: number;
  orderNumber: string;
  orderDate: string;
  status: string;
  paymentStatus: string;
  totalAmount: number;
  customerName: string;
}

export interface CustomerOrderLineRecord {
  productId: number;
  productName: string;
  quantity: number;
  unitPrice: number;
  lineTotal: number;
}

export interface CustomerOrderDetailRecord extends CustomerOrderRecord {
  subtotal: number;
  customerPhone: string;
  lines: CustomerOrderLineRecord[];
}

export default class CustomerOrdersApi {
  public static async getOrdersByEmail(email: string): Promise<CustomerOrderRecord[]> {
    if (!email.trim()) {
      return [];
    }

    const baseUrl = Env.get().resolvedHttpUrl;
    const response = await fetch(
      `${baseUrl}/api/public/customer/orders-by-email?email=${encodeURIComponent(email.trim().toLowerCase())}`,
      {
        method: "GET",
        headers: { Accept: "application/json" },
      }
    );

    if (!response.ok) {
      if (response.status === 404) {
        throw new Error("ORDER_API_NOT_FOUND");
      }
      throw new Error(`Request failed (${response.status})`);
    }

    const data = (await response.json()) as { items?: Record<string, unknown>[] };
    const items: Record<string, unknown>[] = Array.isArray(data.items) ? data.items : [];
    return Array.from(
      items.map((item) => ({
        id: Number(item.id ?? 0),
        orderNumber: String(item.orderNumber ?? ""),
        orderDate: String(item.orderDate ?? ""),
        status: String(item.status ?? ""),
        paymentStatus: String(item.paymentStatus ?? ""),
        totalAmount: Number(item.totalAmount ?? 0),
        customerName: String(item.customerName ?? ""),
      }))
    );
  }

  public static async getOrdersByPhone(phone: string): Promise<CustomerOrderRecord[]> {
    if (!phone.trim()) {
      return [];
    }

    const baseUrl = Env.get().resolvedHttpUrl;
    const response = await fetch(
      `${baseUrl}/api/public/customer/orders?phone=${encodeURIComponent(phone.trim())}`,
      {
        method: "GET",
        headers: { Accept: "application/json" },
      }
    );

    if (!response.ok) {
      if (response.status === 404) {
        throw new Error("ORDER_API_NOT_FOUND");
      }
      throw new Error(`Request failed (${response.status})`);
    }

    const data = (await response.json()) as { items?: Record<string, unknown>[] };
    const items: Record<string, unknown>[] = Array.isArray(data.items) ? data.items : [];
    return Array.from(
      items.map((item) => ({
        id: Number(item.id ?? 0),
        orderNumber: String(item.orderNumber ?? ""),
        orderDate: String(item.orderDate ?? ""),
        status: String(item.status ?? ""),
        paymentStatus: String(item.paymentStatus ?? ""),
        totalAmount: Number(item.totalAmount ?? 0),
        customerName: String(item.customerName ?? ""),
      }))
    );
  }

  public static async getOrderById(
    orderId: number,
    phone: string
  ): Promise<CustomerOrderDetailRecord | null> {
    if (!phone.trim() || orderId <= 0) {
      return null;
    }

    const baseUrl = Env.get().resolvedHttpUrl;
    const response = await fetch(
      `${baseUrl}/api/public/customer/orders/${orderId}?phone=${encodeURIComponent(phone.trim())}`,
      {
        method: "GET",
        headers: { Accept: "application/json" },
      }
    );

    if (!response.ok) {
      return null;
    }

    const data = (await response.json()) as Record<string, unknown>;
    const rawLines = Array.isArray(data.lines) ? data.lines : [];
    const lines: CustomerOrderLineRecord[] = Array.from(
      rawLines.map((entry) => {
        const row = entry as Record<string, unknown>;
        return {
          productId: Number(row.productId ?? 0),
          productName: String(row.productName ?? ""),
          quantity: Number(row.quantity ?? 0),
          unitPrice: Number(row.unitPrice ?? 0),
          lineTotal: Number(row.lineTotal ?? 0),
        };
      })
    );

    return {
      id: Number(data.id ?? 0),
      orderNumber: String(data.orderNumber ?? ""),
      orderDate: String(data.orderDate ?? ""),
      status: String(data.status ?? ""),
      paymentStatus: String(data.paymentStatus ?? ""),
      totalAmount: Number(data.totalAmount ?? 0),
      customerName: String(data.customerName ?? ""),
      subtotal: Number(data.subtotal ?? 0),
      customerPhone: String(data.customerPhone ?? ""),
      lines,
    };
  }

  public static async getOrderByIdForEmail(
    orderId: number,
    email: string
  ): Promise<CustomerOrderDetailRecord | null> {
    if (!email.trim() || orderId <= 0) {
      return null;
    }

    const baseUrl = Env.get().resolvedHttpUrl;
    const response = await fetch(
      `${baseUrl}/api/public/customer/orders/${orderId}/by-email?email=${encodeURIComponent(email.trim().toLowerCase())}`,
      {
        method: "GET",
        headers: { Accept: "application/json" },
      }
    );

    if (!response.ok) {
      return null;
    }

    const data = (await response.json()) as Record<string, unknown>;
    const rawLines = Array.isArray(data.lines) ? data.lines : [];
    const lines: CustomerOrderLineRecord[] = Array.from(
      rawLines.map((entry) => {
        const row = entry as Record<string, unknown>;
        return {
          productId: Number(row.productId ?? 0),
          productName: String(row.productName ?? ""),
          quantity: Number(row.quantity ?? 0),
          unitPrice: Number(row.unitPrice ?? 0),
          lineTotal: Number(row.lineTotal ?? 0),
        };
      })
    );

    return {
      id: Number(data.id ?? 0),
      orderNumber: String(data.orderNumber ?? ""),
      orderDate: String(data.orderDate ?? ""),
      status: String(data.status ?? ""),
      paymentStatus: String(data.paymentStatus ?? ""),
      totalAmount: Number(data.totalAmount ?? 0),
      customerName: String(data.customerName ?? ""),
      subtotal: Number(data.subtotal ?? 0),
      customerPhone: String(data.customerPhone ?? ""),
      lines,
    };
  }
}
