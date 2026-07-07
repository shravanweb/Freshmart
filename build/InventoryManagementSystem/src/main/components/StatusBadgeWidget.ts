import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import TextView from "./TextView";
import { BuildContext } from "../classes/BuildContext";

export interface StatusBadgeWidgetProps extends BaseUIProps {
  key?: string;
  status: string;
  optionSetType?: string;
}

class _StatusBadgeWidgetState extends ObservableComponent<StatusBadgeWidgetProps> {
  static defaultProps = { status: "", optionSetType: "" };
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: StatusBadgeWidgetProps) {
    super(props);

    this.initState();
  }
  public get status(): string {
    return this.props.status;
  }
  public get optionSetType(): string {
    return this.props.optionSetType;
  }
  public initState() {
    super.initState();

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["optionSetType", "status"], this.rebuild);
  }
  public componentDidUpdate(prevProps: StatusBadgeWidgetProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.status !== this.props.status) {
      this.fire("status", this);
    }

    if (prevProps.optionSetType !== this.props.optionSetType) {
      this.fire("optionSetType", this);
    }
  }
  public render(): ReactNode {
    let cStyle = this.context.theme;

    return ui.Container({
      child: TextView({
        data:
          this.optionSetType !== null && this.optionSetType.isNotEmpty
            ? this.status + " (" + this.optionSetType + ")"
            : this.status,
        className: "x36e",
      }),
      className: ui.join(
        "GlassPanelStyle StatusBadgeWidget glassPanel xe844 ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
}
export default function StatusBadgeWidget(props: StatusBadgeWidgetProps) {
  return React.createElement(_StatusBadgeWidgetState, {
    ..._StatusBadgeWidgetState.defaultProps,
    ...props,
  });
}
