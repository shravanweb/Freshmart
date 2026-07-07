import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import StockBatch from "../models/StockBatch";
import ListWrapper from "../utils/ListWrapper";
import TextView from "./TextView";
import { BuildContext } from "../classes/BuildContext";

type _ExpiryAlertWidgetOnViewBatch = (batch: StockBatch) => void;

export interface ExpiryAlertWidgetProps extends BaseUIProps {
  key?: string;
  batches?: Array<StockBatch>;
  _batchesHash?: number;
  onViewBatch?: _ExpiryAlertWidgetOnViewBatch;
}

class _ExpiryAlertWidgetState extends ObservableComponent<ExpiryAlertWidgetProps> {
  static defaultProps = { batches: [], onViewBatch: null };
  batchCount: string = "";
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: ExpiryAlertWidgetProps) {
    super(props);

    this.initState();
  }
  public get batches(): Array<StockBatch> {
    return this.props.batches;
  }
  public initState() {
    super.initState();

    this.initListeners();

    this.enableBuild = false;
  }
  public initListeners(): void {
    this.subscribeToList(this.batches, "batches");

    this.updateSyncCollProperty("batches", this.props.batches);

    this.on(["batches"], this.computeBatchCount);

    this.computeBatchCount();

    this.on(["batchCount"], this.rebuild);
  }
  public componentDidUpdate(prevProps: ExpiryAlertWidgetProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.batches !== this.props.batches) {
      this.updateObservableColl(
        "batches",
        prevProps.batches,
        this.props.batches
      );

      this.fire("batches", this);
    }
  }
  public setBatchCount(val: string): void {
    let isValChanged: boolean = this.batchCount !== val;

    if (!isValChanged) {
      return;
    }

    this.batchCount = val;

    this.fire("batchCount", this);
  }
  public computeBatchCount = (): void => {
    try {
      this.setBatchCount(
        this.batches !== null ? this.batches.length.toString() : "0"
      );
    } catch (exception) {
      console.log(" exception in computeBatchCount : " + exception.toString());

      this.setBatchCount("");
    }
  };
  public render(): ReactNode {
    let cStyle = this.context.theme;

    return ui.Container({
      child: ui.Column({
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          ui.Row({
            mainAxisAlignment: ui.MainAxisAlignment.spaceBetween,
            crossAxisAlignment: ui.CrossAxisAlignment.center,
            mainAxisSize: ui.MainAxisSize.max,
            children: [
              TextView({ data: "Expiring Soon", className: "x0bd", key: "0" }),
              TextView({
                data: this.batchCount + " batches",
                className: "x48",
                key: "1",
              }),
            ],
            key: "0",
          }),
          ui.Container({
            child: TextView({
              data: "Bind expiring batch rows from ExpiringBatches query",
              className: "x08",
            }),
            className: "glassPanel x34",
            key: "1",
          }),
        ],
      }),
      className: ui.join(
        "GlassCardStyle ExpiryAlertWidget glassCardFlat ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public get onViewBatch(): _ExpiryAlertWidgetOnViewBatch {
    return this.props.onViewBatch;
  }
}
export default function ExpiryAlertWidget(props: ExpiryAlertWidgetProps) {
  return React.createElement(_ExpiryAlertWidgetState, {
    ..._ExpiryAlertWidgetState.defaultProps,
    ...props,
  });
}
