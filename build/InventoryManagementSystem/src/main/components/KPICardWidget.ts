import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import TextView from "./TextView";
import { BuildContext } from "../classes/BuildContext";

export interface KPICardWidgetProps extends BaseUIProps {
  key?: string;
  label: string;
  value: string;
  icon?: string;
  trend?: string;
  variant?: string;
}

class _KPICardWidgetState extends ObservableComponent<KPICardWidgetProps> {
  static defaultProps = {
    label: "",
    value: "",
    icon: "",
    trend: "",
    variant: "a",
  };
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: KPICardWidgetProps) {
    super(props);

    this.initState();
  }
  public get label(): string {
    return this.props.label;
  }
  public get value(): string {
    return this.props.value;
  }
  public get icon(): string {
    return this.props.icon;
  }
  public get trend(): string {
    return this.props.trend;
  }
  public get variant(): string {
    return this.props.variant;
  }
  public initState() {
    super.initState();

    this.initListeners();

    this.enableBuild = false;
  }
  public initListeners(): void {
    this.on(["icon", "label", "trend", "value", "variant"], this.rebuild);
  }
  public componentDidUpdate(prevProps: KPICardWidgetProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.label !== this.props.label) {
      this.fire("label", this);
    }

    if (prevProps.value !== this.props.value) {
      this.fire("value", this);
    }

    if (prevProps.icon !== this.props.icon) {
      this.fire("icon", this);
    }

    if (prevProps.trend !== this.props.trend) {
      this.fire("trend", this);
    }

    if (prevProps.variant !== this.props.variant) {
      this.fire("variant", this);
    }
  }
  public variantClassName(): string {
    switch (this.variant) {
      case "b":
        return "statCardB";
      case "c":
        return "statCardC";
      case "d":
        return "statCardD";
      case "e":
        return "statCardE";
      default:
        return "statCardA";
    }
  }
  public render(): ReactNode {
    return ui.Container({
      child: ui.Column({
        mainAxisSize: ui.MainAxisSize.min,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          ui.Row({
            mainAxisAlignment: ui.MainAxisAlignment.spaceBetween,
            crossAxisAlignment: ui.CrossAxisAlignment.start,
            mainAxisSize: ui.MainAxisSize.max,
            children: [
              this.icon !== null && this.icon.isNotEmpty
                ? TextView({ data: this.icon, className: "statCardIcon", key: "0" })
                : null,
              this.trend !== null && this.trend.isNotEmpty
                ? TextView({ data: this.trend, className: "statCardTrend", key: "1" })
                : null,
            ],
            key: "0",
          }),
          TextView({ data: this.value, className: "statValue", key: "1" }),
          TextView({ data: this.label, className: "statLabel", key: "2" }),
        ],
      }),
      className: ui.join(
        ui.join(
          "StatCardStyle KPICardWidget statCard ",
          this.variantClassName()
        ),
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
}
export default function KPICardWidget(props: KPICardWidgetProps) {
  return React.createElement(_KPICardWidgetState, {
    ..._KPICardWidgetState.defaultProps,
    ...props,
  });
}
