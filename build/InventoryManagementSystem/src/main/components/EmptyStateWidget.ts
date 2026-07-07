import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import ObjectObservable from "../utils/ObjectObservable";
import TextButton from "./TextButton";
import TextView from "./TextView";
import { BuildContext } from "../classes/BuildContext";

type _EmptyStateWidgetOnAction = () => void;

type _ActionRefOnPressed = (d3eState: EmptyStateWidgetRefs) => void;

export interface EmptyStateWidgetProps extends BaseUIProps {
  key?: string;
  title: string;
  message: string;
  actionLabel?: string;
  onAction?: _EmptyStateWidgetOnAction;
}
/// To store state data for EmptyStateWidget
class EmptyStateWidgetRefs {
  public actionRef: ActionRefState = new ActionRefState();
}

interface ActionRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: EmptyStateWidgetRefs;
  _onActionHandler?: _ActionRefOnPressed;
  actionLabel: string;
}

class ActionRefState extends ObjectObservable {
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

class _ActionRefWithState extends ObservableComponent<ActionRefWithStateProps> {
  actionRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: ActionRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get actionLabel(): string {
    return this.props.actionLabel;
  }
  public get actionRef(): ActionRefState {
    return this.props.d3eState.actionRef;
  }
  public get d3eState(): EmptyStateWidgetRefs {
    return this.props.d3eState;
  }
  public get _onActionHandler(): _ActionRefOnPressed {
    return this.props._onActionHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("actionRef", null, this.actionRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["actionLabel", "actionRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return ui.Container({
      margin: ui.EdgeInsets.fromLTRB(0.0, 24.0, 0.0, 0.0, new Map()),
      child: TextButton({
        label: this.actionLabel,
        disable: this.actionRef.disable,
        onPressed: () => {
          this._onActionHandler(this.d3eState);
        },
        onFocusChange: (val) => {},
        className: "primary",
        key: "0",
      }),
      className: "primary x41",
    });
  }
}
function ActionRefWithState(props: ActionRefWithStateProps) {
  return React.createElement(_ActionRefWithState, props);
}

class _EmptyStateWidgetState extends ObservableComponent<EmptyStateWidgetProps> {
  static defaultProps = {
    title: "",
    message: "",
    actionLabel: "",
    onAction: null,
  };
  d3eState: EmptyStateWidgetRefs = new EmptyStateWidgetRefs();
  hasAction: boolean = false;
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: EmptyStateWidgetProps) {
    super(props);

    this.initState();
  }
  public get title(): string {
    return this.props.title;
  }
  public get message(): string {
    return this.props.message;
  }
  public get actionLabel(): string {
    return this.props.actionLabel;
  }
  public initState() {
    super.initState();

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["actionLabel"], this.computeHasAction);

    this.computeHasAction();

    this.on(
      ["actionLabel", "actionRef", "hasAction", "message", "title"],
      this.rebuild
    );
  }
  public componentDidUpdate(prevProps: EmptyStateWidgetProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.title !== this.props.title) {
      this.fire("title", this);
    }

    if (prevProps.message !== this.props.message) {
      this.fire("message", this);
    }

    if (prevProps.actionLabel !== this.props.actionLabel) {
      this.fire("actionLabel", this);
    }
  }
  public setHasAction(val: boolean): void {
    let isValChanged: boolean = this.hasAction !== val;

    if (!isValChanged) {
      return;
    }

    this.hasAction = val;

    this.fire("hasAction", this);
  }
  public computeHasAction = (): void => {
    try {
      this.setHasAction(
        this.actionLabel !== null && this.actionLabel.isNotEmpty
      );
    } catch (exception) {
      console.log(" exception in computeHasAction : " + exception.toString());

      this.setHasAction(false);
    }
  };
  public render(): ReactNode {
    let cStyle = this.context.theme;

    return ui.Container({
      child: ui.Column({
        mainAxisAlignment: ui.MainAxisAlignment.center,
        crossAxisAlignment: ui.CrossAxisAlignment.center,
        mainAxisSize: ui.MainAxisSize.min,
        children: [
          TextView({
            data: this.title,
            textAlign: ui.TextAlign.center,
            className: "x3b",
            key: "0",
          }),
          TextView({
            data: this.message,
            textAlign: ui.TextAlign.center,
            className: "x7e",
            key: "1",
          }),
          this.hasAction
            ? ActionRefWithState({
                d3eState: this.d3eState,
                _onActionHandler: this.onActionHandler,
                actionLabel: this.actionLabel,
              })
            : [],
        ],
      }),
      className: ui.join(
        "GlassCardStyle_PrimaryButtonStyle EmptyStateWidget glassCard x4a ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public onActionHandler = (d3eState: EmptyStateWidgetRefs): void => {
    this.onAction();
  };
  public get onAction(): _EmptyStateWidgetOnAction {
    return this.props.onAction;
  }
  public get actionRef() {
    return this.d3eState.actionRef;
  }
}
export default function EmptyStateWidget(props: EmptyStateWidgetProps) {
  return React.createElement(_EmptyStateWidgetState, {
    ..._EmptyStateWidgetState.defaultProps,
    ...props,
  });
}
