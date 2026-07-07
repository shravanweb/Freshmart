import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import ObjectObservable from "../utils/ObjectObservable";
import Product from "../models/Product";
import ListWrapper from "../utils/ListWrapper";
import TextButton from "./TextButton";
import TextView from "./TextView";
import WarehouseStock from "../models/WarehouseStock";
import { BuildContext } from "../classes/BuildContext";

type _LowStockTableWidgetOnViewProduct = (product: Product) => void;

type _LowStockTableWidgetOnCreatePO = () => void;

type _CreatePoRefOnPressed = (d3eState: LowStockTableWidgetRefs) => void;

export interface LowStockTableWidgetProps extends BaseUIProps {
  key?: string;
  items?: Array<WarehouseStock>;
  _itemsHash?: number;
  onViewProduct?: _LowStockTableWidgetOnViewProduct;
  onCreatePO?: _LowStockTableWidgetOnCreatePO;
}
/// To store state data for LowStockTableWidget
class LowStockTableWidgetRefs {
  public createPoRef: CreatePoRefState = new CreatePoRefState();
}

interface CreatePoRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: LowStockTableWidgetRefs;
  _onCreatePoHandler?: _CreatePoRefOnPressed;
}

class CreatePoRefState extends ObjectObservable {
  private _disable: boolean = false;
  public get disable(): boolean {
    return this._disable;
  }
  public setDisable(val: boolean) {
    let isValChanged: boolean = this._disable !== val;

    if (!isValChanged) {
      return;
    }

    this._disable = val;

    this.fire("disable", this);
  }
}

class _CreatePoRefWithState extends ObservableComponent<CreatePoRefWithStateProps> {
  createPoRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: CreatePoRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get createPoRef(): CreatePoRefState {
    return this.props.d3eState.createPoRef;
  }
  public get d3eState(): LowStockTableWidgetRefs {
    return this.props.d3eState;
  }
  public get _onCreatePoHandler(): _CreatePoRefOnPressed {
    return this.props._onCreatePoHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("createPoRef", null, this.createPoRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["createPoRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return TextButton({
      label: "Create PO",
      disable: this.createPoRef.disable,
      onPressed: () => {
        this._onCreatePoHandler(this.d3eState);
      },
      onFocusChange: (val) => {},
      className: "primary",
    });
  }
}
function CreatePoRefWithState(props: CreatePoRefWithStateProps) {
  return React.createElement(_CreatePoRefWithState, props);
}

class _LowStockTableWidgetState extends ObservableComponent<LowStockTableWidgetProps> {
  static defaultProps = { items: [], onViewProduct: null, onCreatePO: null };
  d3eState: LowStockTableWidgetRefs = new LowStockTableWidgetRefs();
  rowCountLabel: string = "";
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: LowStockTableWidgetProps) {
    super(props);

    this.initState();
  }
  public get items(): Array<WarehouseStock> {
    return this.props.items;
  }
  public initState() {
    super.initState();

    this.initListeners();

    this.enableBuild = false;
  }
  public initListeners(): void {
    this.subscribeToList(this.items, "items");

    this.updateSyncCollProperty("items", this.props.items);

    this.on(["items"], this.computeRowCountLabel);

    this.computeRowCountLabel();

    this.on(["createPoRef", "rowCountLabel"], this.rebuild);
  }
  public componentDidUpdate(prevProps: LowStockTableWidgetProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.items !== this.props.items) {
      this.updateObservableColl("items", prevProps.items, this.props.items);

      this.fire("items", this);
    }
  }
  public setRowCountLabel(val: string): void {
    let isValChanged: boolean = this.rowCountLabel !== val;

    if (!isValChanged) {
      return;
    }

    this.rowCountLabel = val;

    this.fire("rowCountLabel", this);
  }
  public computeRowCountLabel = (): void => {
    try {
      this.setRowCountLabel(
        this.items !== null
          ? this.items.length.toString() + " items"
          : "0 items"
      );
    } catch (exception) {
      console.log(
        " exception in computeRowCountLabel : " + exception.toString()
      );

      this.setRowCountLabel("");
    }
  };
  public render(): ReactNode {
    let cStyle = this.context.theme;
    const rowCountLabel =
      this.items !== null
        ? this.items.length.toString() + " items"
        : "0 items";

    return ui.Container({
      child: ui.Column({
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          ui.Row({
            mainAxisAlignment: ui.MainAxisAlignment.spaceBetween,
            crossAxisAlignment: ui.CrossAxisAlignment.center,
            mainAxisSize: ui.MainAxisSize.max,
            children: [
              TextView({ data: "Low Stock Items", className: "xeb", key: "0" }),
              CreatePoRefWithState({
                d3eState: this.d3eState,
                _onCreatePoHandler: this.onCreatePoHandler,
                key: "1",
              }),
            ],
            key: "0",
          }),
          TextView({ data: rowCountLabel, className: "x33", key: "1" }),
          ui.Container({
            child: TextView({
              data: "Bind warehouse stock rows from LowStockItems query",
              className: "x84",
            }),
            className: "glassPanel x90",
            key: "2",
          }),
        ],
      }),
      className: ui.join(
        "GlassCardStyle_PrimaryButtonStyle LowStockTableWidget glassCardFlat ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public onCreatePoHandler = (d3eState: LowStockTableWidgetRefs): void => {
    this.onCreatePO();
  };
  public get onViewProduct(): _LowStockTableWidgetOnViewProduct {
    return this.props.onViewProduct;
  }
  public get onCreatePO(): _LowStockTableWidgetOnCreatePO {
    return this.props.onCreatePO;
  }
  public get createPoRef() {
    return this.d3eState.createPoRef;
  }
}
export default function LowStockTableWidget(props: LowStockTableWidgetProps) {
  return React.createElement(_LowStockTableWidgetState, {
    ..._LowStockTableWidgetState.defaultProps,
    ...props,
  });
}
