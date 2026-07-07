import React, { ReactNode } from "react";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import User from "../models/User";
import UserProfile from "../models/UserProfile";
import Organization from "../models/Organization";
import AppRouteNavigator from "../utils/AppRouteNavigator";
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
import ProductApi, { ProductRecord } from "../utils/ProductApi";
import CategoryApi, { CategoryRecord } from "../utils/CategoryApi";
import CollectionUtils from "../utils/CollectionUtils";
import { BuildContext } from "../classes/BuildContext";
import AppLogout from "../utils/AppLogout";
import { renderEntityStatusCell } from "../utils/EntityTableHelpers";

export interface ProductListPageProps extends BaseUIProps {
  key?: string;
  user: User;
}

const STATUS_OPTIONS = [
  { value: "Active", label: "Active" },
  { value: "Inactive", label: "Inactive" },
  { value: "Discontinued", label: "Discontinued" },
];

const STATUS_FILTER_OPTIONS = [
  { value: "", label: "All statuses" },
  ...STATUS_OPTIONS,
];

class _ProductListPageState extends ObservableComponent<ProductListPageProps> {
  static defaultProps = { user: null };
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;

  userProfileData: UserProfileByUser = null;
  userProfile: UserProfile = null;
  organization: Organization = null;
  products: ProductRecord[] = [];
  categoryOptions: CategoryRecord[] = [];
  searchTerm: string = "";
  statusFilter: string = "";
  isLoading: boolean = true;
  loadError: string = "";
  showFormModal: boolean = false;
  editingProduct: ProductRecord | null = null;
  isSaving: boolean = false;
  formError: string = "";
  formSku: string = "";
  formName: string = "";
  formDescription: string = "";
  formBarcode: string = "";
  formSellingPrice: string = "";
  formPurchasePrice: string = "";
  formCategoryId: string = "";
  formStatus: string = "Active";
  formExistingImages: string[] = [];
  formPendingImageFiles: File[] = [];
  formPendingImagePreviews: string[] = [];
  formImagesToDelete: string[] = [];
  selectedEntity: ProductRecord = null;
  deleteDialogPopup: Popup;

  public constructor(props: ProductListPageProps) {
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
    this.on(["organization"], this.loadProducts);
    this.on(["organization"], this.loadCategoryOptions);
    this.on(
      [
        "products",
        "categoryOptions",
        "searchTerm",
        "statusFilter",
        "isLoading",
        "loadError",
        "showFormModal",
        "isSaving",
        "formError",
        "formSku",
        "formName",
        "formDescription",
        "formBarcode",
        "formSellingPrice",
        "formPurchasePrice",
        "formCategoryId",
        "formStatus",
        "formExistingImages",
        "formPendingImagePreviews",
        "editingProduct",
        "user",
        "userProfile",
        "organization",
      ],
      this.rebuild
    );
  }

  public componentDidUpdate(prevProps: ProductListPageProps): void {
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

  public setProducts(val: ProductRecord[]): void {
    if (CollectionUtils.isNotEquals(this.products, val)) {
      this.products = val;
      this.fire("products", this);
    }
  }

  public setCategoryOptions(val: CategoryRecord[]): void {
    if (CollectionUtils.isNotEquals(this.categoryOptions, val)) {
      this.categoryOptions = val;
      this.fire("categoryOptions", this);
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

  public get filteredProducts(): ProductRecord[] {
    let list = this.products;
    const term = this.searchTerm?.trim().toLowerCase() ?? "";
    if (term) {
      list = list.filter(
        (item) =>
          item.name.toLowerCase().includes(term) ||
          item.sku.toLowerCase().includes(term) ||
          item.barcode.toLowerCase().includes(term)
      );
    }
    if (this.statusFilter) {
      list = list.filter((item) => item.status === this.statusFilter);
    }
    return list;
  }

  public loadCategoryOptions = async (): Promise<void> => {
    if (this.organization == null) {
      this.setCategoryOptions([]);
      return;
    }
    try {
      const orgId = this.organization?.id ?? this.user?.organization?.id;
      this.setCategoryOptions(await CategoryApi.getAllCategories(orgId));
    } catch {
      this.setCategoryOptions([]);
    }
  };

  public loadProducts = async (): Promise<void> => {
    if (this.organization == null) {
      this.isLoading = false;
      this.loadError = "";
      this.setProducts([]);
      this.fire("isLoading", this);
      return;
    }

    this.isLoading = true;
    this.loadError = "";
    this.fire("isLoading", this);

    try {
      const orgId = this.organization?.id ?? this.user?.organization?.id;
      const items = await ProductApi.getAllProducts(orgId);
      this.setProducts(items);
      this.loadError = "";
    } catch (exception) {
      this.setProducts([]);
      this.loadError = "Failed to load products: " + exception.toString();
    } finally {
      this.isLoading = false;
      this.fire("isLoading", this);
      this.fire("loadError", this);
    }
  };

  private imageFilenameFromUrl(url: string): string {
    const parts = url.split("/");
    return parts[parts.length - 1] ?? "";
  }

  private clearFormImages(): void {
    for (const preview of this.formPendingImagePreviews) {
      if (preview.startsWith("blob:")) {
        URL.revokeObjectURL(preview);
      }
    }
    this.formExistingImages = [];
    this.formPendingImageFiles = [];
    this.formPendingImagePreviews = [];
    this.formImagesToDelete = [];
    this.fire("formExistingImages", this);
    this.fire("formPendingImagePreviews", this);
  }

  private async loadFormImages(productId: number): Promise<void> {
    const images = await ProductApi.getProductImages(productId);
    this.formExistingImages = images;
    this.fire("formExistingImages", this);
  }

  public onFormImagesSelected = (event: React.ChangeEvent<HTMLInputElement>): void => {
    const fileList = event.target.files;
    if (!fileList || fileList.length === 0) {
      return;
    }
    const nextFiles = [...this.formPendingImageFiles];
    const nextPreviews = [...this.formPendingImagePreviews];
    for (let index = 0; index < fileList.length; index++) {
      const file = fileList.item(index);
      if (!file) {
        continue;
      }
      nextFiles.push(file);
      nextPreviews.push(URL.createObjectURL(file));
    }
    this.formPendingImageFiles = nextFiles;
    this.formPendingImagePreviews = nextPreviews;
    this.fire("formPendingImagePreviews", this);
    event.target.value = "";
  };

  public removePendingImage = (index: number): void => {
    const preview = this.formPendingImagePreviews[index];
    if (preview?.startsWith("blob:")) {
      URL.revokeObjectURL(preview);
    }
    this.formPendingImageFiles = this.formPendingImageFiles.filter((_, i) => i !== index);
    this.formPendingImagePreviews = this.formPendingImagePreviews.filter((_, i) => i !== index);
    this.fire("formPendingImagePreviews", this);
  };

  public removeExistingImage = (imageUrl: string): void => {
    const filename = this.imageFilenameFromUrl(imageUrl);
    if (!filename) {
      return;
    }
    if (!this.formImagesToDelete.includes(filename)) {
      this.formImagesToDelete.push(filename);
    }
    this.formExistingImages = this.formExistingImages.filter((url) => url !== imageUrl);
    this.fire("formExistingImages", this);
  };

  public openCreateForm = (): void => {
    this.editingProduct = null;
    this.formSku = "";
    this.formName = "";
    this.formDescription = "";
    this.formBarcode = "";
    this.formSellingPrice = "";
    this.formPurchasePrice = "";
    this.formCategoryId =
      this.categoryOptions.length > 0 ? String(this.categoryOptions[0].id) : "";
    this.formStatus = "Active";
    this.formError = "";
    this.clearFormImages();
    this.showFormModal = true;
    this.fire("showFormModal", this);
  };

  public openEditForm = (product: ProductRecord): void => {
    this.editingProduct = product;
    this.formSku = product.sku;
    this.formName = product.name;
    this.formDescription = product.description ?? "";
    this.formBarcode = product.barcode ?? "";
    this.formSellingPrice =
      product.sellingPrice > 0 ? String(product.sellingPrice) : "";
    this.formPurchasePrice =
      product.purchasePrice > 0 ? String(product.purchasePrice) : "";
    this.formCategoryId =
      product.categoryId > 0 ? String(product.categoryId) : "";
    this.formStatus = product.status || "Active";
    this.formError = "";
    this.clearFormImages();
    this.showFormModal = true;
    this.fire("showFormModal", this);
    void this.loadFormImages(product.id);
  };

  public closeForm = (): void => {
    this.showFormModal = false;
    this.editingProduct = null;
    this.formError = "";
    this.clearFormImages();
    this.fire("showFormModal", this);
  };

  public saveForm = async (): Promise<void> => {
    const sku = this.formSku.trim();
    const name = this.formName.trim();
    if (!sku) {
      this.formError = "SKU is required.";
      this.fire("formError", this);
      return;
    }
    if (!name) {
      this.formError = "Name is required.";
      this.fire("formError", this);
      return;
    }

    const orgId = this.organization?.id ?? this.user?.organization?.id;
    if (!orgId) {
      this.formError = "Organization not found.";
      this.fire("formError", this);
      return;
    }

    const productId = this.editingProduct?.id;
    if (this.editingProduct && (!productId || productId <= 0)) {
      this.formError = "Product ID is missing. Refresh the page and try again.";
      this.fire("formError", this);
      return;
    }

    this.isSaving = true;
    this.formError = "";
    this.fire("isSaving", this);

    const input = {
      id: productId,
      sku,
      name,
      description: this.formDescription.trim(),
      barcode: this.formBarcode.trim(),
      sellingPrice: this.formSellingPrice ? Number(this.formSellingPrice) : undefined,
      purchasePrice: this.formPurchasePrice ? Number(this.formPurchasePrice) : undefined,
      category: this.formCategoryId ? Number(this.formCategoryId) : undefined,
      status: this.formStatus,
      organization: orgId,
    };

    try {
      const result = this.editingProduct
        ? await ProductApi.updateProduct(input)
        : await ProductApi.createProduct(input);

      if (!result.success) {
        this.formError =
          result.errors.length > 0
            ? result.errors.join(", ")
            : "Failed to save product.";
        return;
      }

      const savedProductId = result.product?.id ?? productId ?? 0;
      if (savedProductId > 0) {
        for (const filename of this.formImagesToDelete) {
          await ProductApi.deleteProductImage(savedProductId, filename);
        }
        if (this.formPendingImageFiles.length > 0) {
          await ProductApi.uploadProductImages(savedProductId, this.formPendingImageFiles);
        }
      }

      this.closeForm();
      await this.loadProducts();
    } catch (exception) {
      this.formError = "Save failed: " + exception.toString();
    } finally {
      this.isSaving = false;
      this.fire("isSaving", this);
      this.fire("formError", this);
    }
  };

  public onDeleteHandler = (product: ProductRecord): void => {
    this.selectedEntity = product;
    this.showDeleteDialogPopup();
  };

  public onConfirmDeleteHandler = async (): Promise<void> => {
    const product = this.selectedEntity;
    this.hideDeleteDialogPopup();
    this.selectedEntity = null;

    if (!product?.id) return;

    try {
      const result = await ProductApi.deleteProduct(product.id);
      if (!result.success) {
        this.loadError =
          result.errors.length > 0
            ? result.errors.join(", ")
            : "Failed to delete product.";
        this.fire("loadError", this);
        return;
      }
      await this.loadProducts();
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
        entityName: "Product",
        message: `Are you sure you want to delete "${this.selectedEntity?.name ?? "this product"}"?`,
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

  private renderProductImagesField(): ReactNode {
    const hasImages =
      this.formExistingImages.length > 0 || this.formPendingImagePreviews.length > 0;

    return React.createElement(
      "div",
      { className: "productImagesUploadField productFormFullWidth", key: "product-images" },
      React.createElement("span", { className: "fieldLabel" }, "Product Images"),
      hasImages
        ? React.createElement(
            "div",
            { className: "productImagesPreviewGrid" },
            this.formExistingImages.map((imageUrl) =>
              React.createElement(
                "div",
                { className: "productImagePreviewCard", key: "existing-" + imageUrl },
                React.createElement("img", {
                  src: `${imageUrl}?v=${Date.now()}`,
                  alt: "Product image",
                  className: "productImagePreviewThumb",
                }),
                React.createElement(
                  "button",
                  {
                    type: "button",
                    className: "productImageRemoveBtn",
                    disabled: this.isSaving,
                    onClick: () => this.removeExistingImage(imageUrl),
                    "aria-label": "Remove image",
                  },
                  "✕"
                )
              )
            ),
            this.formPendingImagePreviews.map((preview, index) =>
              React.createElement(
                "div",
                { className: "productImagePreviewCard", key: "pending-" + index },
                React.createElement("img", {
                  src: preview,
                  alt: "New product image",
                  className: "productImagePreviewThumb",
                }),
                React.createElement(
                  "button",
                  {
                    type: "button",
                    className: "productImageRemoveBtn",
                    disabled: this.isSaving,
                    onClick: () => this.removePendingImage(index),
                    "aria-label": "Remove image",
                  },
                  "✕"
                )
              )
            )
          )
        : React.createElement(
            "div",
            { className: "productImagesEmptyState" },
            "No images yet. Upload one or more product photos."
          ),
      React.createElement("input", {
        type: "file",
        accept: "image/jpeg,image/png,image/webp",
        multiple: true,
        disabled: this.isSaving,
        className: "categoryImageFileInput",
        onChange: this.onFormImagesSelected,
      }),
      React.createElement(
        "p",
        { className: "categoryImageHint" },
        "Upload one or more JPG, PNG or WebP images. The first image is used on the landing page."
      )
    );
  }

  private renderFormModal(): ReactNode {
    const title = this.editingProduct ? "Edit Product" : "Add Product";
    const categorySelectOptions: { value: string; label: string }[] = [];
    for (const item of this.categoryOptions) {
      categorySelectOptions.push({
        value: String(item.id),
        label: item.name,
      });
    }
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
            this.renderTextField("SKU", this.formSku, "Product SKU", (val) => {
              this.formSku = val;
              this.fire("formSku", this);
            }, true),
            this.renderTextField("Name", this.formName, "Product name", (val) => {
              this.formName = val;
              this.fire("formName", this);
            }, true),
            ui.Container({
              child: ui.Column({
                mainAxisSize: ui.MainAxisSize.min,
                crossAxisAlignment: ui.CrossAxisAlignment.stretch,
                children: [
                  TextView({ data: "Description", className: "fieldLabel", key: "0" }),
                  ui.InputField({
                    value: this.formDescription,
                    placeHolder: "Product description",
                    onChanged: (text: string) => {
                      this.formDescription = text;
                      this.fire("formDescription", this);
                    },
                    onFocusChange: () => {},
                    key: "1",
                  }),
                ],
              }),
              className:
                "FormFieldStyle IMSInputFieldWidget formField storeFormField productFormFullWidth",
              key: "description",
            }),
            this.renderTextField("Barcode", this.formBarcode, "Barcode", (val) => {
              this.formBarcode = val;
              this.fire("formBarcode", this);
            }),
            this.renderTextField(
              "Purchase Price",
              this.formPurchasePrice,
              "0.00",
              (val) => {
                this.formPurchasePrice = val;
                this.fire("formPurchasePrice", this);
              }
            ),
            this.renderTextField(
              "Selling Price",
              this.formSellingPrice,
              "0.00",
              (val) => {
                this.formSellingPrice = val;
                this.fire("formSellingPrice", this);
              }
            ),
            categorySelectOptions.length > 0
              ? this.renderSelectField(
                  "Category",
                  this.formCategoryId,
                  categorySelectOptions,
                  (val) => {
                    this.formCategoryId = val;
                    this.fire("formCategoryId", this);
                  },
                  true
                )
              : null,
            this.renderSelectField(
              "Status",
              this.formStatus,
              STATUS_OPTIONS,
              (val) => {
                this.formStatus = val;
                this.fire("formStatus", this);
              }
            ),
            this.renderProductImagesField(),
            this.formError
              ? TextView({
                  data: this.formError,
                  className: "storeFormError productFormFullWidth",
                  key: "error",
                })
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

  private renderTableRow(product: ProductRecord, index: number): ReactNode {
    return ui.Container({
      child: ui.Row({
        mainAxisSize: ui.MainAxisSize.max,
        crossAxisAlignment: ui.CrossAxisAlignment.center,
        children: [
          TextView({
            data: product.sku || "—",
            className: "entityCell",
            key: "0",
          }),
          TextView({
            data: product.name,
            className: "entityCell entityCellName",
            key: "1",
          }),
          TextView({
            data: product.barcode || "—",
            className: "entityCell",
            key: "2",
          }),
          TextView({
            data: product.sellingPrice > 0 ? product.sellingPrice.toFixed(2) : "—",
            className: "entityCell",
            key: "3",
          }),
          renderEntityStatusCell(product.status),
          ui.Row({
            mainAxisSize: ui.MainAxisSize.min,
            children: [
              IconButton({
                icon: MaterialIcons.edit,
                onPressed: () => this.openEditForm(product),
                onFocusChange: () => {},
                className: "entityActionIcon entityActionEdit",
                key: "0",
              }),
              IconButton({
                icon: MaterialIcons.delete_outline,
                onPressed: () => this.onDeleteHandler(product),
                onFocusChange: () => {},
                className: "entityActionIcon entityActionDelete",
                key: "1",
              }),
            ],
            className: "entityActionsCell",
            key: "4",
          }),
        ],
        className: "entityTableGrid productEntityTableGrid",
      }),
      className: "entityTableRow",
      key: "row-" + index,
    });
  }

  public render(): ReactNode {
    const filtered = this.filteredProducts;
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
            activeRoute: "/catalog/products",
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
                  title: "Products",
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
                              data: "Products",
                              className: "storePageTitle",
                              key: "0",
                            }),
                            TextButton({
                              label: "+ Add Product",
                              onPressed: () => this.openCreateForm(),
                              onFocusChange: () => {},
                              className: "primary",
                              key: "1",
                            }),
                          ],
                          className: "storeToolbarRow",
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
                                "Search product...",
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
                        this.loadError
                          ? TextView({
                              data: this.loadError,
                              className: "storeFormError",
                              key: "loadError",
                            })
                          : null,
                        this.isLoading
                          ? TextView({
                              data: "Loading products...",
                              className: "storeCountLabel",
                              key: "loading",
                            })
                          : filtered.length === 0
                            ? EmptyStateWidget({
                                title: "No products",
                                message: "Add a product to build your catalog.",
                                actionLabel: "+ Add Category",
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
                                            data: "SKU",
                                            className: "entityHeaderCell",
                                            key: "0",
                                          }),
                                          TextView({
                                            data: "NAME",
                                            className: "entityHeaderCell",
                                            key: "1",
                                          }),
                                          TextView({
                                            data: "BARCODE",
                                            className: "entityHeaderCell",
                                            key: "2",
                                          }),
                                          TextView({
                                            data: "PRICE",
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
                                            className: "entityHeaderCell entityHeaderActions",
                                            key: "5",
                                          }),
                                        ],
                                        className: "entityTableGrid productEntityTableGrid",
                                      }),
                                      className: "entityTableHeader",
                                      key: "header",
                                    }),
                                    ...filtered.map((item, index) =>
                                      this.renderTableRow(item, index)
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
              ],
            }),
            className: "storeMainContent",
            key: "1",
          }),
        ],
      }),
      className: ui.join(
        "PageBackgroundStyle_GlassPanelStyle_GlassCardStyle_GlassSidebarStyle_PrimaryButtonStyle_FormFieldStyle ProductListPage pageBackground ",
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
    this.hideDeleteDialogPopup();
    super.dispose();
  }
}

export default function ProductListPage(props: ProductListPageProps) {
  return React.createElement(_ProductListPageState, {
    ..._ProductListPageState.defaultProps,
    ...props,
  });
}
