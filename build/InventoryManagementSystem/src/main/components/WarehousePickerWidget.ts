import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import ObjectObservable from "../utils/ObjectObservable";
import Store from "../models/Store";
import Organization from "../models/Organization";
import TextButton from "./TextButton";
import TextView from "./TextView";
import Warehouse from "../models/Warehouse";
import { BuildContext } from "../classes/BuildContext";

type _WarehousePickerWidgetOnWarehouseSelected = (warehouse: Warehouse) => void;

type _SelectRefOnPressed = (d3eState: WarehousePickerWidgetRefs) => void;

export interface WarehousePickerWidgetProps extends BaseUIProps {
  key?: string;
  organization: Organization;
  store?: Store;
  onWarehouseSelected?: _WarehousePickerWidgetOnWarehouseSelected;
}
/// To store state data for WarehousePickerWidget
class WarehousePickerWidgetRefs {
  public selectRef: SelectRefState = new SelectRefState();
}

interface SelectRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: WarehousePickerWidgetRefs;
  _onSelectHandler?: _SelectRefOnPressed;
  selectedWarehouse: Warehouse;
}

class SelectRefState extends ObjectObservable {}

class _SelectRefWithState extends ObservableComponent<SelectRefWithStateProps> {
  selectRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: SelectRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get selectRef(): SelectRefState {
    return this.props.d3eState.selectRef;
  }
  public get selectedWarehouse(): Warehouse {
    return this.props.selectedWarehouse;
  }
  public get d3eState(): WarehousePickerWidgetRefs {
    return this.props.d3eState;
  }
  public get _onSelectHandler(): _SelectRefOnPressed {
    return this.props._onSelectHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("selectRef", null, this.selectRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.updateSyncProperty("selectedWarehouse", this.props.selectedWarehouse);

    this.on(["selectRef", "selectedWarehouse"], this.rebuild);
  }
  public componentDidUpdate(prevProps: SelectRefWithStateProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.selectedWarehouse !== this.props.selectedWarehouse) {
      this.updateObservable(
        "selectedWarehouse",
        prevProps.selectedWarehouse,
        this.props.selectedWarehouse
      );

      this.fire("selectedWarehouse", this);
    }
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return TextButton({
      label: "Select Warehouse",
      disable: this.selectedWarehouse === null,
      onPressed: () => {
        this._onSelectHandler(this.d3eState);
      },
      onFocusChange: (val) => {},
      className: "primary",
    });
  }
}
function SelectRefWithState(props: SelectRefWithStateProps) {
  return React.createElement(_SelectRefWithState, props);
}

class _WarehousePickerWidgetState extends ObservableComponent<WarehousePickerWidgetProps> {
  static defaultProps = {
    organization: null,
    store: null,
    onWarehouseSelected: null,
  };
  d3eState: WarehousePickerWidgetRefs = new WarehousePickerWidgetRefs();
  selectedWarehouse: Warehouse = null;
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: WarehousePickerWidgetProps) {
    super(props);

    this.initState();
  }
  public get organization(): Organization {
    return this.props.organization;
  }
  public get store(): Store {
    return this.props.store;
  }
  public initState() {
    super.initState();

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.updateSyncProperty("organization", this.props.organization);

    this.updateSyncProperty("store", this.props.store);

    this.on(
      [
        "organization",
        "organization.name",
        "selectRef",
        "selectedWarehouse",
        "store",
        "store.name",
      ],
      this.rebuild
    );
  }
  public componentDidUpdate(prevProps: WarehousePickerWidgetProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.organization !== this.props.organization) {
      this.updateObservable(
        "organization",
        prevProps.organization,
        this.props.organization
      );

      this.fire("organization", this);
    }

    if (prevProps.store !== this.props.store) {
      this.updateObservable("store", prevProps.store, this.props.store);

      this.fire("store", this);
    }
  }
  public setSelectedWarehouse(val: Warehouse): void {
    let isValChanged: boolean = this.selectedWarehouse !== val;

    if (!isValChanged) {
      return;
    }

    this.updateObservable("selectedWarehouse", this.selectedWarehouse, val);

    this.selectedWarehouse = val;

    this.fire("selectedWarehouse", this);
  }

  private resolvedContextLabel(): string {
    if (this.organization == null) {
      return "Loading organization...";
    }

    const orgName =
      this.organization.name != null && this.organization.name.isNotEmpty
        ? this.organization.name
        : "Organization";

    if (this.store == null) {
      return orgName;
    }

    const storeName =
      this.store.name != null && this.store.name.isNotEmpty
        ? this.store.name
        : "";

    return storeName ? `${orgName} · ${storeName}` : orgName;
  }

  private resolvedSelectionLabel(): string {
    if (this.selectedWarehouse == null) {
      return "No warehouse selected";
    }

    return this.selectedWarehouse.name != null &&
      this.selectedWarehouse.name.isNotEmpty
      ? this.selectedWarehouse.name
      : "Selected warehouse";
  }

  public render(): ReactNode {
    return ui.Container({
      child: ui.Column({
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          TextView({ data: "Warehouse", className: "fieldLabel", key: "0" }),
          ui.Container({
            child: TextView({
              data: this.resolvedContextLabel(),
              className: "xfb9a",
            }),
            className: "x0dc",
            key: "1",
          }),
          ui.Container({
            child: TextView({
              data: this.resolvedSelectionLabel(),
              className: "fieldValue",
            }),
            className: "x0dc",
            key: "2",
          }),
          SelectRefWithState({
            d3eState: this.d3eState,
            _onSelectHandler: this.onSelectHandler,
            selectedWarehouse: this.selectedWarehouse,
            key: "3",
          }),
        ],
      }),
      className: ui.join(
        "GlassPanelStyle_FormFieldStyle_PrimaryButtonStyle WarehousePickerWidget glassPanel xd9d ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public onSelectHandler = (d3eState: WarehousePickerWidgetRefs): void => {
    if (this.selectedWarehouse !== null) {
      this.onWarehouseSelected(this.selectedWarehouse);
    }
  };
  public get onWarehouseSelected(): _WarehousePickerWidgetOnWarehouseSelected {
    return this.props.onWarehouseSelected;
  }
  public get selectRef() {
    return this.d3eState.selectRef;
  }
}
export default function WarehousePickerWidget(
  props: WarehousePickerWidgetProps
) {
  return React.createElement(_WarehousePickerWidgetState, {
    ..._WarehousePickerWidgetState.defaultProps,
    ...props,
  });
}
