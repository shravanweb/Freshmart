# BuildACompleteInventory — D3E Blueprint (continued from Section Q)

## Q. FILE / FOLDER STRUCTURE (continued)

```
├── Report/
│   ├── StockValuationReport.d3e
│   ├── InventoryMovementReport.d3e
│   ├── LowStockReport.d3e
│   ├── ExpiryReport.d3e
│   ├── PurchaseOrderStatusReport.d3e
│   ├── SalesSummaryReport.d3e
│   ├── SupplierPerformanceReport.d3e
│   └── ProfitabilitySnapshotReport.d3e
├── Page/
│   ├── LandingPage.d3e
│   ├── LoginPage.d3e
│   ├── ForgotPasswordPage.d3e
│   ├── AcceptInvitationPage.d3e
│   ├── ChangePasswordPage.d3e
│   ├── DashboardPage.d3e
│   ├── OrganizationListPage.d3e
│   ├── OrganizationFormPage.d3e
│   ├── OrganizationSettingsPage.d3e
│   ├── StoreListPage.d3e
│   ├── StoreFormPage.d3e
│   ├── StoreDetailPage.d3e
│   ├── WarehouseListPage.d3e
│   ├── WarehouseFormPage.d3e
│   ├── WarehouseDetailPage.d3e
│   ├── UnitOfMeasureListPage.d3e
│   ├── UnitOfMeasureFormPage.d3e
│   ├── ProductCategoryListPage.d3e
│   ├── ProductCategoryFormPage.d3e
│   ├── ProductListPage.d3e
│   ├── ProductFormPage.d3e
│   ├── ProductDetailPage.d3e
│   ├── WarehouseStockListPage.d3e
│   ├── StockBatchListPage.d3e
│   ├── InventoryMovementListPage.d3e
│   ├── InventoryAdjustmentListPage.d3e
│   ├── InventoryAdjustmentFormPage.d3e
│   ├── StockAlertListPage.d3e
│   ├── SupplierListPage.d3e
│   ├── SupplierFormPage.d3e
│   ├── PurchaseOrderListPage.d3e
│   ├── PurchaseOrderFormPage.d3e
│   ├── PurchaseOrderDetailPage.d3e
│   ├── GoodsReceiptListPage.d3e
│   ├── GoodsReceiptFormPage.d3e
│   ├── GoodsReceiptDetailPage.d3e
│   ├── StockTransferListPage.d3e
│   ├── StockTransferFormPage.d3e
│   ├── StockTransferDetailPage.d3e
│   ├── SalesOrderListPage.d3e
│   ├── SalesOrderFormPage.d3e
│   ├── SalesOrderDetailPage.d3e
│   ├── SalesReturnListPage.d3e
│   ├── SalesReturnFormPage.d3e
│   ├── SalesReturnDetailPage.d3e
│   ├── StockValuationReportPage.d3e
│   ├── MovementReportPage.d3e
│   ├── LowStockReportPage.d3e
│   ├── ExpiryReportPage.d3e
│   ├── SalesSummaryReportPage.d3e
│   ├── PurchaseReportPage.d3e
│   ├── UserListPage.d3e
│   ├── UserFormPage.d3e
│   ├── UserInvitePage.d3e
│   ├── AuditLogListPage.d3e
│   ├── NotificationPreferencesPage.d3e
│   └── ProfileSettingsPage.d3e
├── Widget/
│   ├── IMSidebarWidget.d3e
│   ├── IMSAppHeaderWidget.d3e
│   ├── LoginFormWidget.d3e
│   ├── OtpLoginWidget.d3e
│   ├── KPICardWidget.d3e
│   ├── LowStockTableWidget.d3e
│   ├── ExpiryAlertWidget.d3e
│   ├── RecentMovementsWidget.d3e
│   ├── NotificationCenterWidget.d3e
│   ├── ProductSearchFieldWidget.d3e
│   ├── ProductPickerWidget.d3e
│   ├── SupplierPickerWidget.d3e
│   ├── WarehousePickerWidget.d3e
│   ├── LineItemEditorWidget.d3e
│   ├── StatusBadgeWidget.d3e
│   ├── StockLevelBadgeWidget.d3e
│   ├── BatchSelectorWidget.d3e
│   ├── DocumentActionBarWidget.d3e
│   ├── ConfirmDeleteDialogWidget.d3e
│   ├── DateRangeFilterWidget.d3e
│   ├── OrganizationSelectorWidget.d3e
│   ├── InviteUserFormWidget.d3e
│   ├── AuditLogRowWidget.d3e
│   ├── ReportExportButtonWidget.d3e
│   ├── EmptyStateWidget.d3e
│   └── IMSHeroWidget.d3e
├── D3EClass/
│   ├── InventoryAuthService.d3et
│   ├── OrganizationService.d3et
│   ├── StoreService.d3et
│   ├── WarehouseService.d3et
│   ├── UnitOfMeasureService.d3et
│   ├── ProductCategoryService.d3et
│   ├── ProductService.d3et
│   ├── WarehouseStockService.d3et
│   ├── StockBatchService.d3et
│   ├── SupplierService.d3et
│   ├── PurchaseOrderService.d3et
│   ├── GoodsReceiptService.d3et
│   ├── StockTransferService.d3et
│   ├── InventoryAdjustmentService.d3et
│   ├── SalesOrderService.d3et
│   ├── SalesReturnService.d3et
│   ├── StockAlertService.d3et
│   ├── InventoryMovementService.d3et
│   ├── AuditLogService.d3et
│   ├── UserManagementService.d3et
│   ├── NotificationService.d3et
│   └── ReportService.d3et
├── ScheduledAction/
│   ├── LowStockAlertJob.d3e
│   ├── ExpiryAlertJob.d3e
│   ├── ExpiredBatchCleanupJob.d3e
│   ├── InvitationExpiryJob.d3e
│   └── AuditLogRetentionJob.d3e
├── UserTypes/
│   ├── AnonymousUser.d3e
│   └── BuildACompleteInventoryUser.d3e
├── UserManagement/
│   ├── UserRole.d3e
│   ├── AnonymousUserType.d3e
│   └── AuthenticatedUserType.d3e
├── StyleThemes/
│   └── IMSTheme.d3e
├── Styles/
│   ├── IMSBaseStyle.d3e
│   ├── IMSCardStyle.d3e
│   ├── IMSFormStyle.d3e
│   ├── IMSTableStyle.d3e
│   ├── IMSSidebarStyle.d3e
│   ├── IMSAuthStyle.d3e
│   ├── IMSStatusBadgeStyle.d3e
│   └── IMSDashboardChartStyle.d3e
├── Migrator/
│   ├── M001_InitialSchema.d3e
│   ├── M002_SeedSystemUserRoles.d3e
│   └── M003_SeedNotificationTemplates.d3e
├── D3EConfig/
│   ├── AppSettings.d3e
│   └── InventoryThresholds.d3e
└── WidgetTestData/
    ├── KPICardWidgetTestData.d3e
    ├── ProductPickerWidgetTestData.d3e
    └── StatusBadgeWidgetTestData.d3e
```

**Construct counts (MVP):**

| Folder | File count |
|--------|------------|
| Model | 30 |
| Struct | 8 |
| OptionSets | 26 |
| DataQuery | 44 |
| QueryUsage | 40 |
| Report | 8 |
| Page | 52 |
| Widget | 25 |
| D3EClass | 22 |
| ScheduledAction | 5 |
| UserTypes | 2 |
| StyleThemes | 1 |
| Styles | 8 |
| Migrator | 3 |
| D3EConfig | 2 |

---

## R. BUILD ORDER (Phase 1 MVP only)

### R.1 Foundation (Week 1–2)

**Goal:** Project shell, auth, tenant scaffolding, theming.

| Step | Constructs | Dependencies |
|------|------------|--------------|
| 1 | `Project.d3e`, `D3EConfig/AppSettings.d3e`, `D3EConfig/InventoryThresholds.d3e` | d3e.core |
| 2 | `StyleThemes/IMSTheme.d3e`, all `Styles/*` | UIImprovement reference patterns |
| 3 | `OptionSets/EntityStatus`, `AppUserRole`, `OrganizationStatus`, `InvitationStatus` | — |
| 4 | `Model/Organization`, `Model/UserProfile`, `Model/UserRole`, `Model/UserInvitation` | OptionSets |
| 5 | `UserManagement/*` exemplars, `UserTypes/AnonymousUser`, `UserTypes/BuildACompleteInventoryUser` | UserProfile, BaseUser |
| 6 | `D3EClass/InventoryAuthService`, `D3EClass/UserManagementService`, `D3EClass/AuditLogService` | core OTP/Email |
| 7 | `Struct/LoginResult` | — |
| 8 | Auth pages: `LandingPage`, `LoginPage`, `ForgotPasswordPage`, `AcceptInvitationPage`, `ChangePasswordPage` | Auth widgets |
| 9 | Auth widgets: `LoginFormWidget`, `OtpLoginWidget`, `IMSHeroWidget` | Styles |
| 10 | `Application/BuildACompleteInventoryApplication.d3e` | LandingPage root |
| 11 | `Migrator/M001_InitialSchema`, `M002_SeedSystemUserRoles` | Organization, UserRole |

**Exit criteria:** User can accept invite, log in via password/OTP, land on empty dashboard shell.

---

### R.2 Org, Stores, Warehouses (Week 2–3)

**Goal:** Multi-tenant location hierarchy.

| Step | Constructs |
|------|------------|
| 1 | `OptionSets/StoreType`, `WarehouseType` |
| 2 | `Model/Store`, `Model/Warehouse` |
| 3 | `DataQuery`: AllStores, StoreById, AllWarehouses, WarehousesByStore |
| 4 | `QueryUsage` for store/warehouse pages |
| 5 | `D3EClass/OrganizationService`, `StoreService`, `WarehouseService` |
| 6 | Pages: OrganizationList/Form/Settings, StoreList/Form/Detail, WarehouseList/Form/Detail |
| 7 | Widgets: `IMSidebarWidget`, `IMSAppHeaderWidget`, `WarehousePickerWidget`, `StatusBadgeWidget`, `ConfirmDeleteDialogWidget` |
| 8 | `Page/DashboardPage` (shell only — KPI placeholders) |

**Exit criteria:** OrgAdmin can create stores and warehouses; sidebar navigation works.

---

### R.3 Product Catalog (Week 3–4)

**Goal:** Full product master data.

| Step | Constructs |
|------|------------|
| 1 | `OptionSets/UomType`, `ProductStatus` |
| 2 | `Model/UnitOfMeasure`, `ProductCategory`, `Product` |
| 3 | `ModelPatterns/*` (reference archetypes) |
| 4 | `Struct/ProductSearchRequest` |
| 5 | `DataQuery`: AllUnitOfMeasures, AllProductCategories, AllProducts, ProductsByCategory, ProductSearch, ProductById |
| 6 | `D3EClass/UnitOfMeasureService`, `ProductCategoryService`, `ProductService` |
| 7 | Pages: UOM List/Form, Category List/Form, Product List/Form/Detail |
| 8 | Widgets: `ProductSearchFieldWidget`, `ProductPickerWidget`, `EmptyStateWidget` |
| 9 | File upload via `ProductService.uploadProductImage` |

**Exit criteria:** Full product CRUD with categories, UOM, images, reorder levels, batch/expiry flags.

---

### R.4 Inventory Core (Week 4–5)

**Goal:** Real-time stock, batches, movements, adjustments.

| Step | Constructs |
|------|------------|
| 1 | `OptionSets/BatchStatus`, `AdjustmentReason`, `AdjustmentStatus`, `MovementType`, `MovementDirection`, `MovementReferenceType`, `StockAlertType`, `AlertStatus` |
| 2 | `Model/WarehouseStock`, `StockBatch`, `InventoryAdjustment`, `InventoryAdjustmentLine`, `InventoryMovement`, `StockAlert` |
| 3 | `Struct/StockAvailabilityCheck`, `DashboardMetrics` (partial) |
| 4 | `DataQuery`: WarehouseStockByWarehouse, WarehouseStockByProduct, LowStockItems, OutOfStockItems, AllStockBatches, ExpiringBatches, AllInventoryAdjustments, AllInventoryMovements, InventoryMovementsByDateRange, AllStockAlerts, DashboardMetrics |
| 5 | `D3EClass/WarehouseStockService`, `StockBatchService`, `InventoryAdjustmentService`, `InventoryMovementService`, `StockAlertService` |
| 6 | Model actions: stock recalculation, low-stock check, movement posting |
| 7 | Pages: WarehouseStockList, StockBatchList, InventoryAdjustmentList/Form, InventoryMovementList, StockAlertList |
| 8 | Widgets: `StockLevelBadgeWidget`, `BatchSelectorWidget`, `LowStockTableWidget`, `ExpiryAlertWidget`, `RecentMovementsWidget` |
| 9 | `ScheduledAction/LowStockAlertJob`, `ExpiryAlertJob`, `ExpiredBatchCleanupJob` |
| 10 | `Migrator/M003_SeedNotificationTemplates` |

**Exit criteria:** Stock levels update on adjustment confirm; low-stock and expiry alerts fire; movement log populated.

---

### R.5 Procurement (Week 5–6)

**Goal:** Supplier → PO → Goods Receipt → stock increase.

| Step | Constructs |
|------|------------|
| 1 | `OptionSets/PurchaseOrderStatus`, `GoodsReceiptStatus` |
| 2 | `Model/Supplier`, `SupplierContact`, `PurchaseOrder`, `PurchaseOrderLine`, `GoodsReceipt`, `GoodsReceiptLine` |
| 3 | `Struct/PurchaseOrderSummary`, `ApproveDocumentRequest` |
| 4 | `DataQuery`: AllSuppliers, SupplierById, AllPurchaseOrders, PurchaseOrdersByStatus, PurchaseOrderById, AllGoodsReceipts, GoodsReceiptById |
| 5 | `D3EClass/SupplierService`, `PurchaseOrderService`, `GoodsReceiptService` |
| 6 | PO workflow: Draft → Submitted → Approved → PartiallyReceived → Received |
| 7 | GR confirm posts inventory via `InventoryMovementService` |
| 8 | Pages: Supplier List/Form, PO List/Form/Detail, GR List/Form/Detail |
| 9 | Widgets: `SupplierPickerWidget`, `LineItemEditorWidget`, `DocumentActionBarWidget` |
| 10 | Notification flows: PO approval, goods receipt confirmed |
| 11 | `Report/PurchaseOrderStatusReport`, `SupplierPerformanceReport` |

**Exit criteria:** End-to-end procurement increases warehouse stock and updates PO received quantities.

---

### R.6 Stock Transfers (Week 6–7)

**Goal:** Inter-warehouse transfers with approval.

| Step | Constructs |
|------|------------|
| 1 | `OptionSets/StockTransferStatus` |
| 2 | `Model/StockTransfer`, `StockTransferLine` |
| 3 | `DataQuery`: AllStockTransfers, StockTransferById |
| 4 | `D3EClass/StockTransferService` (reserve → approve → complete) |
| 5 | Transfer posts TransferOut + TransferIn movements |
| 6 | Pages: StockTransfer List/Form/Detail |
| 7 | Notification flows: transfer approval, transfer completed |

**Exit criteria:** Approved transfer moves stock between warehouses with full audit trail.

---

### R.7 Sales & Returns (Week 7–8)

**Goal:** Sales deduction and return restocking.

| Step | Constructs |
|------|------------|
| 1 | `OptionSets/SalesOrderStatus`, `PaymentStatus`, `SalesReturnStatus`, `ReturnReason` |
| 2 | `Model/SalesOrder`, `SalesOrderLine`, `SalesReturn`, `SalesReturnLine` |
| 3 | `DataQuery`: AllSalesOrders, SalesOrdersByStore, SalesOrderById, AllSalesReturns |
| 4 | `D3EClass/SalesOrderService`, `SalesReturnService` |
| 5 | FIFO batch deduction when `product.trackBatch` |
| 6 | `WarehouseStockService.getStockAvailability` pre-confirm check |
| 7 | Pages: SalesOrder List/Form/Detail, SalesReturn List/Form/Detail |
| 8 | `Report/SalesSummaryReport`, `ProfitabilitySnapshotReport` |

**Exit criteria:** Confirmed sale reduces stock; confirmed return restores stock; availability validated before sale.

---

### R.8 Dashboard, Reports & Admin (Week 8–9)

**Goal:** Operational visibility, user admin, audit.

| Step | Constructs |
|------|------------|
| 1 | `Model/AuditLog`, `InAppNotification`, `NotificationTemplate` |
| 2 | `OptionSets/AuditAction`, `NotificationType`, `NotificationChannel` |
| 3 | `Struct/StockValuationRow`, `InventoryMovementReportRow` |
| 4 | `DataQuery`: AllAuditLogs, AllUserProfiles, UserProfileByUser, AllUserInvitations, AllInAppNotifications, UnreadNotificationCount, StockValuationReport, MovementReportRows |
| 5 | `D3EClass/NotificationService`, `ReportService` |
| 6 | `Report/StockValuationReport`, `InventoryMovementReport`, `LowStockReport`, `ExpiryReport` |
| 7 | Pages: Dashboard (full KPIs), all Report pages, UserList/Form/Invite, AuditLogList, NotificationPreferences, ProfileSettings |
| 8 | Widgets: `KPICardWidget`, `NotificationCenterWidget`, `DateRangeFilterWidget`, `ReportExportButtonWidget`, `InviteUserFormWidget`, `AuditLogRowWidget` |
| 9 | `ScheduledAction/InvitationExpiryJob`, `AuditLogRetentionJob` |
| 10 | Complete `QueryUsage/*` wiring for all pages |
| 11 | `WidgetTestData/*` for Studio preview |

**Exit criteria:** Dashboard shows live KPIs; all 8 reports render; user invite flow works; audit log captures CRUD and approvals.

---

### R.9 Integration & Hardening (Week 9–10)

**Goal:** End-to-end validation, RBAC enforcement, MVP release.

| Step | Activity |
|------|----------|
| 1 | Verify `BuildACompleteInventoryUser` modelAccess conditions per role |
| 2 | Tenant isolation: all DataQueries filter `row.organization == inputs.organization` |
| 3 | Unique constraints scoped per organization on all code/SKU/barcode fields |
| 4 | Workflow guards: no edit on confirmed documents; status transition validation |
| 5 | Delete confirmations on all list/detail pages |
| 6 | AuditLogService hooked into all D3EClass create/update/delete/approve methods |
| 7 | NotificationService hooked into alerts, approvals, invitations |
| 8 | Cross-module smoke tests: PO → GR → stock; Sale → movement; Transfer → both warehouses; Return → restock |
| 9 | Role matrix validation (Section P): each role sees only permitted pages |
| 10 | Performance: indexes on (organization, warehouse, product), (organization, status), (organization, createdAt) |

**MVP release checklist:**

- [ ] 30 models with CRUD services
- [ ] 52 pages with list + create/edit + delete confirm
- [ ] 44 DataQueries with QueryUsage wiring
- [ ] 8 reports + dashboard KPIs
- [ ] 5 scheduled jobs
- [ ] 12 notification flows
- [ ] 8 roles via single authenticated UserType
- [ ] Multi-tenant org/store/warehouse hierarchy
- [ ] Batch/expiry tracking
- [ ] Full inventory movement audit trail

---

## Appendix: Key Business Flows (MVP)

### Flow 1: Purchase-to-Stock
```
Supplier → PurchaseOrder (Draft) → Submit → Approve
  → GoodsReceipt (Draft) → Confirm
    → WarehouseStock.quantityOnHand +=
    → StockBatch created (if trackBatch)
    → InventoryMovement (PurchaseReceipt, In)
    → PurchaseOrderLine.receivedQuantity updated
    → PurchaseOrder.status → PartiallyReceived | Received
```

### Flow 2: Sale-to-Deduct
```
SalesOrder (Draft) → add lines → StockAvailabilityCheck
  → Confirm → WarehouseStock.quantityOnHand -=
    → StockBatch FIFO deduct (if trackBatch)
    → InventoryMovement (SalesIssue, Out)
    → SalesOrder.status → Confirmed → Completed
```

### Flow 3: Inter-Warehouse Transfer
```
StockTransfer (Draft) → Submit → Approve
  → source WarehouseStock.quantityReserved +=
  → Complete → source onHand -=, dest onHand +=
    → Movements: TransferOut (source), TransferIn (dest)
    → StockTransfer.status → Completed
```

### Flow 4: Low-Stock Alert
```
WarehouseStock.OnUpdate → checkLowStock action
  → if quantityAvailable <= product.reorderLevel
    → StockAlert (LowStock | OutOfStock)
    → InAppNotification + Email (LOW_STOCK_ALERT)
  → Daily LowStockAlertJob scans all orgs as backup
```

---

*End of BuildACompleteInventory Phase 1 MVP Blueprint.*
