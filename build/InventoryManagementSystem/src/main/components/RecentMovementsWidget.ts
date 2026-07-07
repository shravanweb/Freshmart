import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import InventoryMovement from "../models/InventoryMovement";
import ListWrapper from "../utils/ListWrapper";
import TextView from "./TextView";
import { BuildContext } from "../classes/BuildContext";

type _RecentMovementsWidgetOnViewMovement = (
  movement: InventoryMovement
) => void;

export interface RecentMovementsWidgetProps extends BaseUIProps {
  key?: string;
  movements?: Array<InventoryMovement>;
  _movementsHash?: number;
  onViewMovement?: _RecentMovementsWidgetOnViewMovement;
}

class _RecentMovementsWidgetState extends ObservableComponent<RecentMovementsWidgetProps> {
  static defaultProps = { movements: [], onViewMovement: null };
  movementCount: string = "";
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: RecentMovementsWidgetProps) {
    super(props);

    this.initState();
  }
  public get movements(): Array<InventoryMovement> {
    return this.props.movements;
  }
  public initState() {
    super.initState();

    this.initListeners();

    this.enableBuild = false;
  }
  public initListeners(): void {
    this.subscribeToList(this.movements, "movements");

    this.updateSyncCollProperty("movements", this.props.movements);

    this.on(["movements"], this.computeMovementCount);

    this.computeMovementCount();

    this.on(["movementCount"], this.rebuild);
  }
  public componentDidUpdate(prevProps: RecentMovementsWidgetProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.movements !== this.props.movements) {
      this.updateObservableColl(
        "movements",
        prevProps.movements,
        this.props.movements
      );

      this.fire("movements", this);
    }
  }
  public setMovementCount(val: string): void {
    let isValChanged: boolean = this.movementCount !== val;

    if (!isValChanged) {
      return;
    }

    this.movementCount = val;

    this.fire("movementCount", this);
  }
  public computeMovementCount = (): void => {
    try {
      this.setMovementCount(
        this.movements !== null ? this.movements.length.toString() : "0"
      );
    } catch (exception) {
      console.log(
        " exception in computeMovementCount : " + exception.toString()
      );

      this.setMovementCount("");
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
              TextView({
                data: "Recent Movements",
                className: "xbb",
                key: "0",
              }),
              TextView({
                data: this.movementCount + " entries",
                className: "x94",
                key: "1",
              }),
            ],
            key: "0",
          }),
          ui.Container({
            child: TextView({
              data: "Bind movement rows from InventoryMovementsByDateRange query",
              className: "xb1e",
            }),
            className: "glassPanel x0fc",
            key: "1",
          }),
        ],
      }),
      className: ui.join(
        "GlassCardStyle RecentMovementsWidget glassCardFlat ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public get onViewMovement(): _RecentMovementsWidgetOnViewMovement {
    return this.props.onViewMovement;
  }
}
export default function RecentMovementsWidget(
  props: RecentMovementsWidgetProps
) {
  return React.createElement(_RecentMovementsWidgetState, {
    ..._RecentMovementsWidgetState.defaultProps,
    ...props,
  });
}
