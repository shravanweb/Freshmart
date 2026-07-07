import React, { ReactNode } from "react";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import User from "../models/User";
import UserProfile from "../models/UserProfile";
import Organization from "../models/Organization";
import PageNavigator from "../classes/PageNavigator";
import AppRouteNavigator from "../utils/AppRouteNavigator";
import AppLogout from "../utils/AppLogout";
import Query from "../classes/Query";
import UserProfileByUser from "../classes/UserProfileByUser";
import UserProfileByUserRequest from "../models/UserProfileByUserRequest";
import MessageDispatch from "../rocket/MessageDispatch";
import { UsageConstants } from "../rocket/D3ETemplate";
import IMSidebarWidget from "./IMSidebarWidget";
import IMSAppHeaderWidget from "./IMSAppHeaderWidget";
import ScrollView2 from "./ScrollView2";
import TextView from "./TextView";
import TextButton from "./TextButton";
import IconButton from "./IconButton";
import MaterialIcons from "../icons/MaterialIcons";
import EmptyStateWidget from "./EmptyStateWidget";
import ConfirmDeleteDialogWidget from "./ConfirmDeleteDialogWidget";
import Popup from "./Popup";
import SupplierApi, { SupplierRecord } from "../utils/SupplierApi";
import ProductApi, { ProductRecord } from "../utils/ProductApi";
import CollectionUtils from "../utils/CollectionUtils";
import { BuildContext } from "../classes/BuildContext";
import { renderEntityStatusCell } from "../utils/EntityTableHelpers";

export interface SupplierListPageProps extends BaseUIProps {
  key?: string;
  user: User;
}

const STATUS_OPTIONS = [
  { value: "Active", label: "Active" },
  { value: "Inactive", label: "Inactive" },
  { value: "Archived", label: "Archived" },
];

const STATUS_FILTER_OPTIONS = [
  { value: "", label: "All statuses" },
  ...STATUS_OPTIONS,
];

class _SupplierListPageState extends ObservableComponent<SupplierListPageProps> {
  static defaultProps = { user: null };
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;

  userProfileData: UserProfileByUser = null;
  userProfile: UserProfile = null;
  organization: Organization = null;
  suppliers: SupplierRecord[] = [];
  searchTerm: string = "";
  statusFilter: string = "";
  isLoading: boolean = true;
  loadError: string = "";
  saveNotice: string = "";
  showFormModal: boolean = false;
  editingSupplier: SupplierRecord | null = null;
  isSaving: boolean = false;
  formError: string = "";
  formName: string = "";
  formCode: string = "";
  formContactPerson: string = "";
  formEmail: string = "";
  formPhone: string = "";
  formAddress: string = "";
  formPaymentTerms: string = "";
  formTaxId: string = "";
  formRating: string = "";
  formStatus: string = "Active";
  catalogProducts: ProductRecord[] = [];
  formSelectedProductIds: number[] = [];
  formProductSearch: string = "";
  isProductsLoading: boolean = false;
  showDetailModal: boolean = false;
  detailSupplier: SupplierRecord | null = null;
  detailProducts: ProductRecord[] = [];
  detailLoading: boolean = false;
  detailError: string = "";
  selectedEntity: SupplierRecord = null;
  deleteDialogPopup: Popup;

  public constructor(props: SupplierListPageProps) {
    super(props);
    this.initState();
  }

  public get user(): User {
    return this.props.user;
  }

  public initState() {
    super.initState();
    this.initListeners();
    this.enableBuild = true;
  }

  public initListeners(): void {
    this.updateSyncProperty("user", this.props.user);
    this.on(["user"], this.computeUserProfileData);
    this.computeUserProfileData();
    this.on(["userProfileData", "userProfileData.items"], this.computeUserProfile);
    this.computeUserProfile();
    this.on(["userProfile", "userProfile.organization"], this.computeOrganization);
    this.computeOrganization();
    this.on(["organization"], this.loadSuppliers);
    this.on(
      [
        "suppliers",
        "searchTerm",
        "statusFilter",
        "isLoading",
        "loadError",
        "saveNotice",
        "showFormModal",
        "isSaving",
        "formError",
        "formName",
        "formCode",
        "formContactPerson",
        "formEmail",
        "formPhone",
        "formAddress",
        "formPaymentTerms",
        "formTaxId",
        "formRating",
        "formStatus",
        "catalogProducts",
        "formSelectedProductIds",
        "formProductSearch",
        "isProductsLoading",
        "showDetailModal",
        "detailSupplier",
        "detailProducts",
        "detailLoading",
        "detailError",
        "editingSupplier",
        "user",
        "userProfile",
        "organization",
      ],
      this.rebuild
    );
  }

  public componentDidUpdate(prevProps: SupplierListPageProps): void {
    super.componentDidUpdate(prevProps);
    if (prevProps.user !== this.props.user) {
      this.updateObservable("user", prevProps.user, this.props.user);
      this.fire("user", this);
    }
  }

  public setUserProfileData(val: UserProfileByUser): void {
    if (this.userProfileData === val) return;
    this.updateObservable("userProfileData", this.userProfileData, val);
    MessageDispatch.get().dispose(this.userProfileData);
    this.userProfileData = val;
    this.fire("userProfileData", this);
  }

  public computeUserProfileData = async (): Promise<void> => {
    try {
      this.setUserProfileData(
        await Query.get().getUserProfileByUser(
          UsageConstants.QUERY_GETUSERPROFILEBYUSER_PURCHASEORDERLISTPAGE_PROPERTIES_USERPROFILEDATA_COMPUTATION,
          new UserProfileByUserRequest({ user: this.user }),
          { synchronize: true }
        )
      );
    } catch (exception) {
      console.log(" exception in computeUserProfileData : " + exception.toString());
      this.setUserProfileData(null);
    }
  };

  public setUserProfile(val: UserProfile): void {
    if (this.userProfile === val) return;
    this.updateObservable("userProfile", this.userProfile, val);
    this.userProfile = val;
    this.fire("userProfile", this);
  }

  public computeUserProfile = (): void => {
    this.setUserProfile(
      this.userProfileData !== null ? this.userProfileData.items.first : null
    );
  };

  public setOrganization(val: Organization): void {
    if (this.organization === val) return;
    this.updateObservable("organization", this.organization, val);
    this.organization = val;
    this.fire("organization", this);
  }

  public computeOrganization = (): void => {
    this.setOrganization(
      this.userProfile !== null ? this.userProfile.organization : null
    );
  };

  public setSuppliers(val: SupplierRecord[]): void {
    if (CollectionUtils.isNotEquals(this.suppliers, val)) {
      this.suppliers = val;
      this.fire("suppliers", this);
    }
  }

  public setSearchTerm(val: string): void {
    if (this.searchTerm === val) return;
    this.searchTerm = val;
    this.fire("searchTerm", this);
  }

  public setStatusFilter(val: string): void {
    if (this.statusFilter === val) return;
    this.statusFilter = val;
    this.fire("statusFilter", this);
  }

  public get filteredSuppliers(): SupplierRecord[] {
    let list = this.suppliers;
    const term = this.searchTerm?.trim().toLowerCase() ?? "";
    if (term) {
      list = list.filter(
        (s) =>
          s.name.toLowerCase().includes(term) ||
          s.code.toLowerCase().includes(term) ||
          s.contactPerson.toLowerCase().includes(term) ||
          s.email.toLowerCase().includes(term)
      );
    }
    if (this.statusFilter) {
      list = list.filter((s) => s.status === this.statusFilter);
    }
    return list;
  }

  public loadSuppliers = async (): Promise<void> => {
    if (this.organization == null) {
      this.isLoading = false;
      this.loadError = "";
      this.setSuppliers([]);
      this.fire("isLoading", this);
      return;
    }

    this.isLoading = true;
    this.loadError = "";
    this.fire("isLoading", this);

    try {
      const orgId = this.organization?.id ?? this.user?.organization?.id;
      const items = await SupplierApi.getAllSuppliers(orgId);
      this.setSuppliers(items);
      this.loadError = "";
    } catch (exception) {
      this.setSuppliers([]);
      this.loadError = "Failed to load suppliers: " + exception.toString();
    } finally {
      this.isLoading = false;
      this.fire("isLoading", this);
      this.fire("loadError", this);
    }
  };

  public openCreateForm = (): void => {
    this.saveNotice = "";
    this.fire("saveNotice", this);
    this.editingSupplier = null;
    this.formName = "";
    this.formCode = "";
    this.formContactPerson = "";
    this.formEmail = "";
    this.formPhone = "";
    this.formAddress = "";
    this.formPaymentTerms = "";
    this.formTaxId = "";
    this.formRating = "";
    this.formStatus = "Active";
    this.formSelectedProductIds = [];
    this.formProductSearch = "";
    this.formError = "";
    this.showFormModal = true;
    this.fire("showFormModal", this);
    void this.loadCatalogProducts();
  };

  public openEditForm = (supplier: SupplierRecord): void => {
    this.editingSupplier = supplier;
    this.formName = supplier.name;
    this.formCode = supplier.code;
    this.formContactPerson = supplier.contactPerson ?? "";
    this.formEmail = supplier.email ?? "";
    this.formPhone = supplier.phone ?? "";
    this.formAddress = supplier.address ?? "";
    this.formPaymentTerms = supplier.paymentTerms ?? "";
    this.formTaxId = supplier.taxId ?? "";
    this.formRating =
      supplier.rating != null && supplier.rating > 0
        ? String(supplier.rating)
        : "";
    this.formStatus = supplier.status || "Active";
    this.formProductSearch = "";
    this.formError = "";
    this.showFormModal = true;
    this.fire("showFormModal", this);
    void this.loadSupplierFormProducts(supplier.id);
  };

  public closeForm = (): void => {
    this.showFormModal = false;
    this.editingSupplier = null;
    this.formError = "";
    this.formSelectedProductIds = [];
    this.formProductSearch = "";
    this.fire("showFormModal", this);
  };

  public loadCatalogProducts = async (): Promise<void> => {
    const orgId = this.organization?.id ?? this.user?.organization?.id;
    if (!orgId) {
      this.catalogProducts = [];
      this.fire("catalogProducts", this);
      return;
    }

    this.isProductsLoading = true;
    this.fire("isProductsLoading", this);

    try {
      const allProducts = await ProductApi.getAllProducts(orgId);
      this.catalogProducts = Array.from(
        Array.from(allProducts).filter((product) => product.status === "Active")
      );
      this.fire("catalogProducts", this);
    } catch (exception) {
      this.catalogProducts = [];
      this.formError = "Failed to load products: " + exception.toString();
      this.fire("catalogProducts", this);
      this.fire("formError", this);
    } finally {
      this.isProductsLoading = false;
      this.fire("isProductsLoading", this);
    }
  };

  public loadSupplierFormProducts = async (vendorId: number): Promise<void> => {
    const orgId = this.organization?.id ?? this.user?.organization?.id;
    if (!orgId) {
      this.catalogProducts = [];
      this.formSelectedProductIds = [];
      this.fire("catalogProducts", this);
      this.fire("formSelectedProductIds", this);
      return;
    }

    this.isProductsLoading = true;
    this.fire("isProductsLoading", this);

    try {
      const [allProducts, vendorProducts] = await Promise.all([
        ProductApi.getAllProducts(orgId),
        ProductApi.getProductsByVendor(orgId, vendorId),
      ]);
      this.catalogProducts = Array.from(
        Array.from(allProducts).filter((product) => product.status === "Active")
      );
      this.formSelectedProductIds = Array.from(
        vendorProducts,
        (product) => product.id
      );
      this.fire("catalogProducts", this);
      this.fire("formSelectedProductIds", this);
    } catch (exception) {
      this.catalogProducts = [];
      this.formSelectedProductIds = [];
      this.formError = "Failed to load supplier products: " + exception.toString();
      this.fire("catalogProducts", this);
      this.fire("formSelectedProductIds", this);
      this.fire("formError", this);
    } finally {
      this.isProductsLoading = false;
      this.fire("isProductsLoading", this);
    }
  };

  public get filteredCatalogProducts(): ProductRecord[] {
    const term = this.formProductSearch.trim().toLowerCase();
    if (!term) {
      return this.catalogProducts;
    }
    return Array.from(
      Array.from(this.catalogProducts).filter(
        (product) =>
          product.name.toLowerCase().includes(term) ||
          product.sku.toLowerCase().includes(term)
      )
    );
  }

  public isProductSelected = (productId: number): boolean => {
    return this.formSelectedProductIds.includes(productId);
  };

  public toggleProductSelection = (productId: number): void => {
    if (this.isProductSelected(productId)) {
      this.formSelectedProductIds = Array.from(this.formSelectedProductIds).filter(
        (id) => id !== productId
      );
    } else {
      this.formSelectedProductIds = [
        ...Array.from(this.formSelectedProductIds),
        productId,
      ];
    }
    this.fire("formSelectedProductIds", this);
  };

  public saveForm = async (): Promise<void> => {
    const name = this.formName.trim();
    const code = this.formCode.trim();
    if (!name) {
      this.formError = "Name is required.";
      this.fire("formError", this);
      return;
    }
    if (!code) {
      this.formError = "Code is required.";
      this.fire("formError", this);
      return;
    }

    const orgId = this.organization?.id ?? this.user?.organization?.id;
    if (!orgId) {
      this.formError = "Organization not found.";
      this.fire("formError", this);
      return;
    }

    const supplierId = this.editingSupplier?.id;
    if (this.editingSupplier && (!supplierId || supplierId <= 0)) {
      this.formError = "Supplier ID is missing. Refresh the page and try again.";
      this.fire("formError", this);
      return;
    }

    let rating: number | null = null;
    const ratingText = this.formRating.trim();
    if (ratingText) {
      const parsed = Number(ratingText);
      if (!Number.isFinite(parsed) || parsed < 1 || parsed > 5) {
        this.formError = "Rating must be a number between 1 and 5.";
        this.fire("formError", this);
        return;
      }
      rating = parsed;
    }

    this.isSaving = true;
    this.formError = "";
    this.fire("isSaving", this);

    const input = {
      id: supplierId,
      name,
      code,
      contactPerson: this.formContactPerson.trim(),
      email: this.formEmail.trim(),
      phone: this.formPhone.trim(),
      address: this.formAddress.trim(),
      paymentTerms: this.formPaymentTerms.trim(),
      taxId: this.formTaxId.trim(),
      rating,
      status: this.formStatus,
      organization: orgId,
    };

    try {
      const result = this.editingSupplier
        ? await SupplierApi.updateSupplier(input)
        : await SupplierApi.createSupplier(input);

      if (!result.success) {
        this.formError =
          result.errors.length > 0
            ? result.errors.join(", ")
            : "Failed to save supplier.";
        return;
      }

      const savedVendorId = result.supplier?.id ?? supplierId ?? 0;
      if (savedVendorId > 0) {
        const productResult = await ProductApi.assignProductsToVendor(
          orgId,
          savedVendorId,
          this.formSelectedProductIds
        );
        if (!productResult.success) {
          this.formError =
            productResult.errors.length > 0
              ? productResult.errors.join(", ")
              : "Supplier saved, but product assignment failed.";
          return;
        }
      }

      const createdEmail = this.formEmail.trim();
      this.saveNotice =
        !this.editingSupplier && createdEmail
          ? `Supplier created. Welcome email sent to ${createdEmail}.`
          : this.editingSupplier
            ? "Supplier updated successfully."
            : "Supplier created successfully.";
      this.fire("saveNotice", this);

      this.closeForm();
      await this.loadSuppliers();
    } catch (exception) {
      this.formError = "Save failed: " + exception.toString();
    } finally {
      this.isSaving = false;
      this.fire("isSaving", this);
      this.fire("formError", this);
    }
  };

  public onDeleteHandler = (supplier: SupplierRecord): void => {
    this.selectedEntity = supplier;
    this.showDeleteDialogPopup();
  };

  public onConfirmDeleteHandler = async (): Promise<void> => {
    const supplier = this.selectedEntity;
    this.hideDeleteDialogPopup();
    this.selectedEntity = null;

    if (!supplier?.id) return;

    try {
      const result = await SupplierApi.deleteSupplier(supplier.id);
      if (!result.success) {
        this.loadError =
          result.errors.length > 0
            ? result.errors.join(", ")
            : "Failed to delete supplier.";
        this.fire("loadError", this);
        return;
      }
      await this.loadSuppliers();
    } catch (exception) {
      this.loadError = "Delete failed: " + exception.toString();
      this.fire("loadError", this);
    }
  };

  public onCancelDeleteHandler = (): void => {
    this.hideDeleteDialogPopup();
    this.selectedEntity = null;
  };

  public showDeleteDialogPopup(): void {
    this.deleteDialogPopup?.dispose();
    this.deleteDialogPopup = new Popup({
      model: true,
      autoClose: false,
      float: false,
      takeFocus: true,
      position: ui.PopUpPosition.Center,
      child: ConfirmDeleteDialogWidget({
        entityName: "Supplier",
        message: `Are you sure you want to delete "${this.selectedEntity?.name ?? "this supplier"}"?`,
        onConfirm: () => {
          this.onConfirmDeleteHandler();
        },
        onCancel: () => {
          this.onCancelDeleteHandler();
        },
      }),
    });
    this.deleteDialogPopup.showPopup(this.context);
  }

  public hideDeleteDialogPopup(): void {
    this.deleteDialogPopup?.dispose();
  }

  private renderTextField(
    label: string,
    value: string,
    placeHolder: string,
    onChange: (val: string) => void,
    required?: boolean
  ): ReactNode {
    return ui.Container({
      child: ui.Column({
        mainAxisSize: ui.MainAxisSize.min,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          label
            ? TextView({
                data: required ? label + " *" : label,
                className: "fieldLabel",
                key: "0",
              })
            : null,
          ui.InputField({
            value,
            placeHolder,
            onChanged: onChange,
            onFocusChange: () => {},
            key: "1",
          }),
        ],
      }),
      className: "FormFieldStyle IMSInputFieldWidget formField storeFormField",
      key: label || placeHolder,
    });
  }

  private renderSelectField(
    label: string,
    value: string,
    options: { value: string; label: string }[],
    onChange: (val: string) => void,
    required?: boolean
  ): ReactNode {
    return ui.Container({
      child: ui.Column({
        mainAxisSize: ui.MainAxisSize.min,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          TextView({
            data: required ? label + " *" : label,
            className: "fieldLabel",
            key: "0",
          }),
          React.createElement(
            "select",
            {
              className: "roleSelect",
              value,
              disabled: this.isSaving,
              onChange: (event: React.ChangeEvent<HTMLSelectElement>) => {
                onChange(event.target.value);
              },
              key: "1",
            },
            options.map((option) =>
              React.createElement(
                "option",
                { value: option.value, key: option.value },
                option.label
              )
            )
          ),
        ],
      }),
      className: "FormFieldStyle IMSInputFieldWidget formField storeFormField",
      key: label,
    });
  }

  private renderProductPicker(): ReactNode {
    const selectedCount = this.formSelectedProductIds.length;
    const filteredProducts = this.filteredCatalogProducts;

    return ui.Container({
      child: ui.Column({
        mainAxisSize: ui.MainAxisSize.min,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          TextView({
            data: "Products Supplied",
            className: "fieldLabel",
            key: "0",
          }),
          ui.InputField({
            value: this.formProductSearch,
            placeHolder: "Search products by name or SKU",
            onChanged: (text: string) => {
              this.formProductSearch = text;
              this.fire("formProductSearch", this);
            },
            onFocusChange: () => {},
            key: "1",
          }),
          this.isProductsLoading
            ? TextView({
                data: "Loading products...",
                className: "storeCountLabel",
                key: "2",
              })
            : filteredProducts.length === 0
              ? TextView({
                  data: this.catalogProducts.length
                    ? "No products match your search."
                    : "Add products in Catalog before linking them to a supplier.",
                  className: "storeCountLabel",
                  key: "3",
                })
              : React.createElement(
                  "div",
                  { className: "supplierProductPicker", key: "4" },
                  filteredProducts.map((product) =>
                    React.createElement(
                      "label",
                      {
                        className: "supplierProductOption",
                        key: String(product.id),
                      },
                      React.createElement("input", {
                        type: "checkbox",
                        checked: this.isProductSelected(product.id),
                        disabled: this.isSaving,
                        onChange: () => this.toggleProductSelection(product.id),
                      }),
                      React.createElement(
                        "span",
                        null,
                        `${product.sku} — ${product.name}`
                      )
                    )
                  )
                ),
          TextView({
            data:
              selectedCount === 1
                ? "1 product selected"
                : `${selectedCount} products selected`,
            className: "storeCountLabel",
            key: "5",
          }),
        ],
      }),
      className:
        "FormFieldStyle IMSInputFieldWidget formField storeFormField productFormFullWidth",
      key: "products",
    });
  }

  private renderFormModal(): ReactNode {
    const title = this.editingSupplier ? "Edit Supplier" : "Add Supplier";
    return React.createElement(
      "div",
      {
        className: "storeModalOverlay",
        onClick: () => {
          if (!this.isSaving) this.closeForm();
        },
      },
      React.createElement(
        "div",
        {
          className: "storeModalPanel glassCard productModalPanel",
          onClick: (e: React.MouseEvent) => e.stopPropagation(),
          role: "dialog",
          "aria-modal": "true",
        },
        React.createElement(
          "div",
          { className: "storeModalHeader" },
          React.createElement("h2", { className: "storeModalTitle" }, title),
          React.createElement(
            "button",
            {
              type: "button",
              className: "storeModalClose",
              "aria-label": "Close",
              disabled: this.isSaving,
              onClick: () => this.closeForm(),
            },
            "✕"
          )
        ),
        React.createElement(
          "div",
          { className: "storeModalBody productModalBody" },
          React.createElement(
            "div",
            { className: "productFormGrid" },
            this.renderTextField("Name", this.formName, "Supplier name", (val) => {
              this.formName = val;
              this.fire("formName", this);
            }, true),
            this.renderTextField("Code", this.formCode, "Supplier code", (val) => {
              this.formCode = val;
              this.fire("formCode", this);
            }, true),
            this.renderTextField(
              "Contact Person",
              this.formContactPerson,
              "Primary contact",
              (val) => {
                this.formContactPerson = val;
                this.fire("formContactPerson", this);
              }
            ),
            this.renderTextField("Email", this.formEmail, "supplier@company.com", (val) => {
              this.formEmail = val;
              this.fire("formEmail", this);
            }),
            this.renderTextField("Phone", this.formPhone, "Phone number", (val) => {
              this.formPhone = val;
              this.fire("formPhone", this);
            }),
            this.renderTextField(
              "Payment Terms",
              this.formPaymentTerms,
              "Net 30, COD, etc.",
              (val) => {
                this.formPaymentTerms = val;
                this.fire("formPaymentTerms", this);
              }
            ),
            ui.Container({
              child: ui.Column({
                mainAxisSize: ui.MainAxisSize.min,
                crossAxisAlignment: ui.CrossAxisAlignment.stretch,
                children: [
                  TextView({ data: "Address", className: "fieldLabel", key: "0" }),
                  ui.InputField({
                    value: this.formAddress,
                    placeHolder: "Supplier address",
                    onChanged: (text: string) => {
                      this.formAddress = text;
                      this.fire("formAddress", this);
                    },
                    onFocusChange: () => {},
                    key: "1",
                  }),
                ],
              }),
              className:
                "FormFieldStyle IMSInputFieldWidget formField storeFormField productFormFullWidth",
              key: "address",
            }),
            this.renderTextField("Tax ID", this.formTaxId, "Tax identification", (val) => {
              this.formTaxId = val;
              this.fire("formTaxId", this);
            }),
            this.renderTextField("Rating (1-5)", this.formRating, "Optional", (val) => {
              this.formRating = val;
              this.fire("formRating", this);
            }),
            this.renderSelectField(
              "Status",
              this.formStatus,
              STATUS_OPTIONS,
              (val) => {
                this.formStatus = val;
                this.fire("formStatus", this);
              }
            ),
            this.renderProductPicker(),
            this.formError
              ? React.createElement(
                  "div",
                  { className: "productFormFullWidth", key: "error-wrap" },
                  TextView({
                    data: this.formError,
                    className: "storeFormError",
                    key: "error",
                  })
                )
              : null
          )
        ),
        React.createElement(
          "div",
          { className: "storeModalFooter" },
          TextButton({
            label: "Cancel",
            disable: this.isSaving,
            onPressed: () => this.closeForm(),
            onFocusChange: () => {},
            className: "secondary",
          }),
          TextButton({
            label: this.isSaving ? "Saving..." : "Save",
            disable: this.isSaving,
            onPressed: () => {
              this.saveForm();
            },
            onFocusChange: () => {},
            className: "primary",
          })
        )
      )
    );
  }

  public openDetailView = (supplier: SupplierRecord): void => {
    this.detailSupplier = supplier;
    this.detailProducts = [];
    this.detailError = "";
    this.showDetailModal = true;
    this.fire("showDetailModal", this);
    this.fire("detailSupplier", this);
    void this.loadSupplierDetail(supplier);
  };

  public closeDetailView = (): void => {
    this.showDetailModal = false;
    this.detailSupplier = null;
    this.detailProducts = [];
    this.detailError = "";
    this.detailLoading = false;
    this.fire("showDetailModal", this);
    this.fire("detailSupplier", this);
    this.fire("detailProducts", this);
    this.fire("detailError", this);
    this.fire("detailLoading", this);
  };

  private loadSupplierDetail = async (supplier: SupplierRecord): Promise<void> => {
    const orgId = this.organization?.id ?? this.user?.organization?.id;
    if (!orgId || !supplier?.id) {
      this.detailError = "Supplier not found.";
      this.fire("detailError", this);
      return;
    }

    this.detailLoading = true;
    this.detailError = "";
    this.fire("detailLoading", this);
    this.fire("detailError", this);

    try {
      this.detailProducts = await ProductApi.getProductsByVendor(orgId, supplier.id);
      this.fire("detailProducts", this);
    } catch (exception) {
      this.detailProducts = [];
      this.detailError = "Failed to load supplier products: " + exception.toString();
      this.fire("detailProducts", this);
      this.fire("detailError", this);
    } finally {
      this.detailLoading = false;
      this.fire("detailLoading", this);
    }
  };

  private renderDetailField(label: string, value: string, key: string): ReactNode {
    return React.createElement(
      "div",
      { className: "detailField", key },
      React.createElement("span", { className: "detailFieldLabel" }, label),
      React.createElement("span", { className: "detailFieldValue" }, value || "—")
    );
  }

  private renderDetailModal(): ReactNode {
    const supplier = this.detailSupplier;
    if (!supplier) {
      return null;
    }

    const productItems = Array.from(this.detailProducts).map((product) =>
      React.createElement(
        "div",
        { className: "detailListItem", key: String(product.id) },
        React.createElement("span", { className: "detailListItemSku" }, product.sku),
        React.createElement("span", { className: "detailListItemName" }, product.name)
      )
    );

    const metaParts = [
      supplier.code ? `Code ${supplier.code}` : "",
      supplier.email || "",
      supplier.phone || "",
    ].filter((part) => part.length > 0);

    return React.createElement(
      "div",
      {
        className: "storeModalOverlay",
        onClick: () => this.closeDetailView(),
      },
      React.createElement(
        "div",
        {
          className: "storeModalPanel glassCard productModalPanel entityDetailModal",
          onClick: (event: React.MouseEvent) => event.stopPropagation(),
          role: "dialog",
          "aria-modal": "true",
        },
        React.createElement(
          "div",
          { className: "storeModalHeader entityDetailHeader" },
          React.createElement(
            "div",
            { className: "entityDetailHeaderMain" },
            React.createElement(
              "div",
              { className: "entityDetailTitleRow" },
              React.createElement(
                "h2",
                { className: "storeModalTitle entityDetailTitle" },
                supplier.name
              ),
              React.createElement(
                "div",
                { className: "entityDetailStatusWrap" },
                renderEntityStatusCell(supplier.status)
              )
            ),
            metaParts.length > 0
              ? React.createElement(
                  "p",
                  { className: "entityDetailMeta" },
                  metaParts.join(" · ")
                )
              : null
          ),
          React.createElement(
            "button",
            {
              type: "button",
              className: "storeModalClose",
              "aria-label": "Close",
              onClick: () => this.closeDetailView(),
            },
            "✕"
          )
        ),
        React.createElement(
          "div",
          { className: "storeModalBody productModalBody entityDetailBody" },
          React.createElement(
            "div",
            { className: "detailGrid" },
            this.renderDetailField("Contact Person", supplier.contactPerson, "contact"),
            this.renderDetailField("Email", supplier.email, "email"),
            this.renderDetailField("Phone", supplier.phone, "phone"),
            this.renderDetailField("Payment Terms", supplier.paymentTerms, "payment-terms"),
            this.renderDetailField("Tax ID", supplier.taxId, "tax-id"),
            this.renderDetailField(
              "Rating",
              supplier.rating != null && supplier.rating > 0
                ? `${supplier.rating} / 5`
                : "",
              "rating"
            ),
            React.createElement(
              "div",
              { className: "detailField detailFieldFull", key: "address" },
              React.createElement("span", { className: "detailFieldLabel" }, "Address"),
              React.createElement(
                "span",
                { className: "detailFieldValue" },
                supplier.address || "—"
              )
            )
          ),
          React.createElement(
            "div",
            { className: "entityDetailSectionHead" },
            React.createElement("h3", { className: "entityDetailSectionTitle" }, "Products Supplied"),
            React.createElement(
              "span",
              { className: "entityDetailSectionCount" },
              this.detailLoading
                ? "Loading..."
                : `${productItems.length} product${productItems.length === 1 ? "" : "s"}`
            )
          ),
          this.detailLoading
            ? React.createElement("p", { className: "storeCountLabel" }, "Loading products...")
            : this.detailError
              ? TextView({ data: this.detailError, className: "storeFormError" })
              : productItems.length === 0
                ? React.createElement(
                    "p",
                    { className: "storeCountLabel" },
                    "No products linked to this supplier."
                  )
                : React.createElement("div", { className: "detailList" }, ...productItems)
        ),
        React.createElement(
          "div",
          { className: "storeModalFooter" },
          TextButton({
            label: "Close",
            onPressed: () => this.closeDetailView(),
            onFocusChange: () => {},
            className: "secondary",
          }),
          TextButton({
            label: "Edit",
            onPressed: () => {
              this.closeDetailView();
              this.openEditForm(supplier);
            },
            onFocusChange: () => {},
            className: "primary",
          })
        )
      )
    );
  }

  private renderTableRow(supplier: SupplierRecord, index: number): ReactNode {
    return React.createElement(
      "div",
      {
        className: "entityTableRow entityTableRowClickable",
        key: "row-" + index,
        onClick: () => this.openDetailView(supplier),
        role: "button",
        tabIndex: 0,
        onKeyDown: (event: React.KeyboardEvent) => {
          if (event.key === "Enter" || event.key === " ") {
            event.preventDefault();
            this.openDetailView(supplier);
          }
        },
      },
      ui.Row({
        mainAxisSize: ui.MainAxisSize.max,
        crossAxisAlignment: ui.CrossAxisAlignment.center,
        children: [
          TextView({
            data: supplier.name,
            className: "entityCell entityCellName",
            key: "0",
          }),
          TextView({
            data: supplier.code || "—",
            className: "entityCell",
            key: "1",
          }),
          TextView({
            data: supplier.contactPerson || "—",
            className: "entityCell",
            key: "2",
          }),
          TextView({
            data: supplier.phone || "—",
            className: "entityCell",
            key: "3",
          }),
          renderEntityStatusCell(supplier.status),
          React.createElement(
            "div",
            {
              className: "entityActionsCell",
              onClick: (event: React.MouseEvent) => event.stopPropagation(),
              onKeyDown: (event: React.KeyboardEvent) => event.stopPropagation(),
              key: "actions-wrap",
            },
            ui.Row({
              mainAxisSize: ui.MainAxisSize.min,
              children: [
                IconButton({
                  icon: MaterialIcons.edit,
                  onPressed: () => this.openEditForm(supplier),
                  onFocusChange: () => {},
                  className: "entityActionIcon entityActionEdit",
                  key: "0",
                }),
                IconButton({
                  icon: MaterialIcons.delete_outline,
                  onPressed: () => this.onDeleteHandler(supplier),
                  onFocusChange: () => {},
                  className: "entityActionIcon entityActionDelete",
                  key: "1",
                }),
              ],
              className: "entityActionsCell",
              key: "4",
            })
          ),
        ],
        className: "entityTableGrid storeEntityTableGrid",
      })
    );
  }

  public render(): ReactNode {
    const filtered = this.filteredSuppliers;
    const countLabel =
      filtered.length + " record" + (filtered.length === 1 ? "" : "s");

    return ui.Container({
      child: ui.Row({
        mainAxisSize: ui.MainAxisSize.max,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          IMSidebarWidget({
            currentUser: this.user,
            userProfile: this.userProfile,
            organization: this.organization,
            activeRoute: "/procurement/suppliers",
            onNavigate: (route) => {
              this.onNavigateHandler(route);
            },
            key: "0",
          }),
          ui.Container({
            child: ui.Column({
              crossAxisAlignment: ui.CrossAxisAlignment.stretch,
              mainAxisSize: ui.MainAxisSize.max,
              children: [
                IMSAppHeaderWidget({
                  title: "Suppliers",
                  organization: this.organization,
                  onLogout: () => {
                    void AppLogout.signOut(this.navigator);
                  },
                  onProfile: () => {
                    this.navigator.pushProfileSettingsPage({
                      user: this.user,
                      target: "main",
                      replace: false,
                    });
                  },
                  key: "0",
                }),
                ScrollView2({
                  child: ui.Container({
                    child: ui.Column({
                      crossAxisAlignment: ui.CrossAxisAlignment.stretch,
                      children: [
                        ui.Row({
                          mainAxisAlignment: ui.MainAxisAlignment.spaceBetween,
                          crossAxisAlignment: ui.CrossAxisAlignment.center,
                          mainAxisSize: ui.MainAxisSize.max,
                          children: [
                            TextView({
                              data: "Manage procurement partners and linked products.",
                              className: "pageToolbarHint",
                              key: "0",
                            }),
                            TextButton({
                              label: "+ Add Supplier",
                              onPressed: () => this.openCreateForm(),
                              onFocusChange: () => {},
                              className: "primary",
                              key: "1",
                            }),
                          ],
                          className: "storeToolbarRow pageToolbarRow",
                          key: "toolbar",
                        }),
                        ui.Row({
                          mainAxisSize: ui.MainAxisSize.max,
                          crossAxisAlignment: ui.CrossAxisAlignment.center,
                          children: [
                            ui.Container({
                              child: this.renderTextField(
                                "",
                                this.searchTerm,
                                "Search supplier...",
                                (val) => this.setSearchTerm(val)
                              ),
                              className: "storeSearchField",
                              key: "0",
                            }),
                            ui.Container({
                              child: this.renderSelectField(
                                "",
                                this.statusFilter,
                                STATUS_FILTER_OPTIONS,
                                (val) => this.setStatusFilter(val)
                              ),
                              className: "storeStatusFilter",
                              key: "1",
                            }),
                            TextView({
                              data: countLabel,
                              className: "entityFilterCount",
                              key: "2",
                            }),
                          ],
                          className: "storeFilterRow entityFilterRow",
                          key: "filters",
                        }),
                        this.saveNotice
                          ? TextView({
                              data: this.saveNotice,
                              className: "storeSaveNotice",
                              key: "saveNotice",
                            })
                          : null,
                        this.loadError
                          ? TextView({
                              data: this.loadError,
                              className: "storeFormError",
                              key: "loadError",
                            })
                          : null,
                        this.isLoading
                          ? TextView({
                              data: "Loading suppliers...",
                              className: "storeCountLabel",
                              key: "loading",
                            })
                          : filtered.length === 0
                            ? EmptyStateWidget({
                                title: "No suppliers",
                                message:
                                  "Add a supplier to begin managing procurement partners.",
                                actionLabel: "+ Add Supplier",
                                onAction: () => this.openCreateForm(),
                                key: "empty",
                              })
                            : ui.Container({
                                child: ui.Column({
                                  crossAxisAlignment: ui.CrossAxisAlignment.stretch,
                                  children: [
                                    ui.Container({
                                      child: ui.Row({
                                        mainAxisSize: ui.MainAxisSize.max,
                                        children: [
                                          TextView({
                                            data: "NAME",
                                            className: "entityHeaderCell",
                                            key: "0",
                                          }),
                                          TextView({
                                            data: "CODE",
                                            className: "entityHeaderCell",
                                            key: "1",
                                          }),
                                          TextView({
                                            data: "CONTACT",
                                            className: "entityHeaderCell",
                                            key: "2",
                                          }),
                                          TextView({
                                            data: "PHONE",
                                            className: "entityHeaderCell",
                                            key: "3",
                                          }),
                                          TextView({
                                            data: "STATUS",
                                            className: "entityHeaderCell",
                                            key: "4",
                                          }),
                                          TextView({
                                            data: "ACTIONS",
                                            className:
                                              "entityHeaderCell entityHeaderActions",
                                            key: "5",
                                          }),
                                        ],
                                        className:
                                          "entityTableGrid storeEntityTableGrid",
                                      }),
                                      className: "entityTableHeader",
                                      key: "header",
                                    }),
                                    ...filtered.map((supplier, index) =>
                                      this.renderTableRow(supplier, index)
                                    ),
                                  ],
                                }),
                                className: "entityTableWrap",
                                key: "table",
                              }),
                      ],
                    }),
                    padding: ui.EdgeInsets.all(24.0),
                  }),
                  scrollDirection: ui.Axis.vertical,
                  className: "pageContent",
                }),
                this.showFormModal ? this.renderFormModal() : null,
                this.showDetailModal ? this.renderDetailModal() : null,
              ],
            }),
            className: "storeMainContent",
            key: "1",
          }),
        ],
      }),
      className: ui.join(
        "PageBackgroundStyle_GlassPanelStyle_GlassCardStyle_GlassSidebarStyle_PrimaryButtonStyle_FormFieldStyle SupplierListPage pageBackground ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }

  public onNavigateHandler = (route: string): void => {
    AppRouteNavigator.navigate(this.navigator, route, this.user, {
      target: "main",
      replace: true,
    });
  };

  public dispose(): void {
    MessageDispatch.get().dispose(this.userProfileData);
    this.deleteDialogPopup?.dispose();
    super.dispose();
  }

  public get navigator(): PageNavigator {
    return PageNavigator.of(this.context);
  }
}

export default function SupplierListPage(props: SupplierListPageProps) {
  return React.createElement(_SupplierListPageState, {
    ..._SupplierListPageState.defaultProps,
    ...props,
  });
}
