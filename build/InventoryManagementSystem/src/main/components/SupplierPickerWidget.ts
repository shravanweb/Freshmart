import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import ObjectObservable from "../utils/ObjectObservable";
import IMSInputFieldWidget from "./IMSInputFieldWidget";
import Vendor from "../models/Vendor";
import Organization from "../models/Organization";
import TextButton from "./TextButton";
import { BuildContext } from "../classes/BuildContext";

type _SupplierPickerWidgetOnSupplierSelected = (vendor: Vendor) => void;

type _SelectRefOnPressed = (d3eState: SupplierPickerWidgetRefs) => void;

export interface SupplierPickerWidgetProps extends BaseUIProps {
  key?: string;
  organization: Organization;
  onSupplierSelected?: _SupplierPickerWidgetOnSupplierSelected;
}
/// To store state data for SupplierPickerWidget
class SupplierPickerWidgetRefs {
  public selectRef: SelectRefState = new SelectRefState();
}

interface SelectRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: SupplierPickerWidgetRefs;
  _onSelectHandler?: _SelectRefOnPressed;
  selectedSupplier: Vendor;
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
  public get selectedSupplier(): Vendor {
    return this.props.selectedSupplier;
  }
  public get d3eState(): SupplierPickerWidgetRefs {
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
    this.updateSyncProperty("selectedSupplier", this.props.selectedSupplier);

    this.on(["selectRef", "selectedSupplier"], this.rebuild);
  }
  public componentDidUpdate(prevProps: SelectRefWithStateProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.selectedSupplier !== this.props.selectedSupplier) {
      this.updateObservable(
        "selectedSupplier",
        prevProps.selectedSupplier,
        this.props.selectedSupplier
      );

      this.fire("selectedSupplier", this);
    }
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return ui.Container({
      margin: ui.EdgeInsets.fromLTRB(0.0, 12.0, 0.0, 0.0, new Map()),
      child: TextButton({
        label: "Select Vendor",
        disable: this.selectedSupplier === null,
        onPressed: () => {
          this._onSelectHandler(this.d3eState);
        },
        onFocusChange: (val) => {},
        className: "primary",
        key: "0",
      }),
      className: "primary x8f1",
    });
  }
}
function SelectRefWithState(props: SelectRefWithStateProps) {
  return React.createElement(_SelectRefWithState, props);
}

class _SupplierPickerWidgetState extends ObservableComponent<SupplierPickerWidgetProps> {
  static defaultProps = { organization: null, onSupplierSelected: null };
  d3eState: SupplierPickerWidgetRefs = new SupplierPickerWidgetRefs();
  searchTerm: string = "";
  selectedSupplier: Vendor = null;
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: SupplierPickerWidgetProps) {
    super(props);

    this.initState();
  }
  public get organization(): Organization {
    return this.props.organization;
  }
  public initState() {
    super.initState();

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.updateSyncProperty("organization", this.props.organization);

    this.on(
      [
        "organization",
        "organization.name",
        "searchTerm",
        "selectRef",
        "selectedSupplier",
      ],
      this.rebuild
    );
  }
  public componentDidUpdate(prevProps: SupplierPickerWidgetProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.organization !== this.props.organization) {
      this.updateObservable(
        "organization",
        prevProps.organization,
        this.props.organization
      );

      this.fire("organization", this);
    }
  }
  public setSearchTerm(val: string): void {
    let isValChanged: boolean = this.searchTerm !== val;

    if (!isValChanged) {
      return;
    }

    this.searchTerm = val;

    this.fire("searchTerm", this);
  }
  public setSelectedSupplier(val: Vendor): void {
    let isValChanged: boolean = this.selectedSupplier !== val;

    if (!isValChanged) {
      return;
    }

    this.updateObservable("selectedSupplier", this.selectedSupplier, val);

    this.selectedSupplier = val;

    this.fire("selectedSupplier", this);
  }
  public render(): ReactNode {
    let cStyle = this.context.theme;

    return ui.Container({
      child: ui.Column({
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          IMSInputFieldWidget({
            label: "Vendor",
            text: this.searchTerm,
            placeHolder:
              this.organization !== null
                ? "Search suppliers in " + this.organization.name
                : "Search suppliers",
            key: "0",
          }),
          SelectRefWithState({
            d3eState: this.d3eState,
            _onSelectHandler: this.onSelectHandler,
            selectedSupplier: this.selectedSupplier,
            key: "1",
          }),
        ],
      }),
      className: ui.join(
        "GlassPanelStyle_FormFieldStyle_PrimaryButtonStyle SupplierPickerWidget glassPanel x2f ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public onSelectHandler = (d3eState: SupplierPickerWidgetRefs): void => {
    if (this.selectedSupplier !== null) {
      this.onSupplierSelected(this.selectedSupplier);
    }
  };
  public get onSupplierSelected(): _SupplierPickerWidgetOnSupplierSelected {
    return this.props.onSupplierSelected;
  }
  public get selectRef() {
    return this.d3eState.selectRef;
  }
}
export default function SupplierPickerWidget(props: SupplierPickerWidgetProps) {
  return React.createElement(_SupplierPickerWidgetState, {
    ..._SupplierPickerWidgetState.defaultProps,
    ...props,
  });
}
