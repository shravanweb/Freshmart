import { ReactNode } from "react";
import TextView from "../components/TextView";

export function renderEntityStatusCell(status: string): ReactNode {
  const normalized = status || "Active";
  const statusClass =
    normalized === "Active"
      ? "entityStatusActive"
      : normalized === "Inactive"
        ? "entityStatusInactive"
        : "entityStatusArchived";

  return TextView({
    data: normalized,
    className: `entityCell entityStatus ${statusClass}`,
    key: "status",
  });
}

export function formatEnumLabel(value: string): string {
  if (!value) {
    return "—";
  }
  return value.replace(/([A-Z])/g, " $1").trim();
}
