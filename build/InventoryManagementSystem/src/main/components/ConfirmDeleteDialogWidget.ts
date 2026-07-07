import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import ObjectObservable from "../utils/ObjectObservable";
import TextButton from "./TextButton";
import MaterialIcons from "../icons/MaterialIcons";
import TextView from "./TextView";
import IconButton from "./IconButton";
import { BuildContext } from "../classes/BuildContext";

type _ConfirmDeleteDialogWidgetOnConfirm = () => void;

type _ConfirmDeleteDialogWidgetOnCancel = () => void;

type _CloseRefOnPressed = (d3eState: ConfirmDeleteDialogWidgetRefs) => void;

type _CancelRefOnPressed = (d3eState: ConfirmDeleteDialogWidgetRefs) => void;

type _ConfirmRefOnPressed = (d3eState: ConfirmDeleteDialogWidgetRefs) => void;

export interface ConfirmDeleteDialogWidgetProps extends BaseUIProps {
  key?: string;
  entityName: string;
  message: string;
  onConfirm?: _ConfirmDeleteDialogWidgetOnConfirm;
  onCancel?: _ConfirmDeleteDialogWidgetOnCancel;
}
/// To store state data for ConfirmDeleteDialogWidget
class ConfirmDeleteDialogWidgetRefs {
  public cancelRef: CancelRefState = new CancelRefState();
  public closeRef: CloseRefState = new CloseRefState();
  public confirmRef: ConfirmRefState = new ConfirmRefState();
}

interface ConfirmRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: ConfirmDeleteDialogWidgetRefs;
  _onConfirmHandler?: _ConfirmRefOnPressed;
}

class ConfirmRefState extends ObjectObservable {
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

class _ConfirmRefWithState extends ObservableComponent<ConfirmRefWithStateProps> {
  confirmRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: ConfirmRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get confirmRef(): ConfirmRefState {
    return this.props.d3eState.confirmRef;
  }
  public get d3eState(): ConfirmDeleteDialogWidgetRefs {
    return this.props.d3eState;
  }
  public get _onConfirmHandler(): _ConfirmRefOnPressed {
    return this.props._onConfirmHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("confirmRef", null, this.confirmRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["confirmRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return TextButton({
      label: "Delete",
      disable: this.confirmRef.disable,
      onPressed: () => {
        this._onConfirmHandler(this.d3eState);
      },
      onFocusChange: (val) => {},
      className: "destructive",
    });
  }
}
function ConfirmRefWithState(props: ConfirmRefWithStateProps) {
  return React.createElement(_ConfirmRefWithState, props);
}

interface CancelRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: ConfirmDeleteDialogWidgetRefs;
  _onCancelHandler?: _CancelRefOnPressed;
}

class CancelRefState extends ObjectObservable {
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

class _CancelRefWithState extends ObservableComponent<CancelRefWithStateProps> {
  cancelRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: CancelRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get cancelRef(): CancelRefState {
    return this.props.d3eState.cancelRef;
  }
  public get d3eState(): ConfirmDeleteDialogWidgetRefs {
    return this.props.d3eState;
  }
  public get _onCancelHandler(): _CancelRefOnPressed {
    return this.props._onCancelHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("cancelRef", null, this.cancelRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["cancelRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return ui.Container({
      margin: ui.EdgeInsets.fromLTRB(0.0, 0.0, 12.0, 0.0, new Map()),
      child: TextButton({
        label: "Cancel",
        disable: this.cancelRef.disable,
        onPressed: () => {
          this._onCancelHandler(this.d3eState);
        },
        onFocusChange: (val) => {},
        className: "secondary",
        key: "0",
      }),
      className: "secondary xc0",
    });
  }
}
function CancelRefWithState(props: CancelRefWithStateProps) {
  return React.createElement(_CancelRefWithState, props);
}

interface CloseRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: ConfirmDeleteDialogWidgetRefs;
  _onCloseHandler?: _CloseRefOnPressed;
}

class CloseRefState extends ObjectObservable {
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

class _CloseRefWithState extends ObservableComponent<CloseRefWithStateProps> {
  closeRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: CloseRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get closeRef(): CloseRefState {
    return this.props.d3eState.closeRef;
  }
  public get d3eState(): ConfirmDeleteDialogWidgetRefs {
    return this.props.d3eState;
  }
  public get _onCloseHandler(): _CloseRefOnPressed {
    return this.props._onCloseHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("closeRef", null, this.closeRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["closeRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return IconButton({
      icon: MaterialIcons.close,
      disable: this.closeRef.disable,
      onPressed: () => {
        this._onCloseHandler(this.d3eState);
      },
      onFocusChange: (val) => {},
    });
  }
}
function CloseRefWithState(props: CloseRefWithStateProps) {
  return React.createElement(_CloseRefWithState, props);
}

class _ConfirmDeleteDialogWidgetState extends ObservableComponent<ConfirmDeleteDialogWidgetProps> {
  static defaultProps = {
    entityName: "",
    message: "",
    onConfirm: null,
    onCancel: null,
  };
  d3eState: ConfirmDeleteDialogWidgetRefs = new ConfirmDeleteDialogWidgetRefs();
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: ConfirmDeleteDialogWidgetProps) {
    super(props);

    this.initState();
  }
  public get entityName(): string {
    return this.props.entityName;
  }
  public get message(): string {
    return this.props.message;
  }
  public initState() {
    super.initState();

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(
      ["cancelRef", "closeRef", "confirmRef", "entityName", "message"],
      this.rebuild
    );
  }
  public componentDidUpdate(prevProps: ConfirmDeleteDialogWidgetProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.entityName !== this.props.entityName) {
      this.fire("entityName", this);
    }

    if (prevProps.message !== this.props.message) {
      this.fire("message", this);
    }
  }
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
                data: "Delete " + this.entityName,
                className: "x76",
                key: "0",
              }),
              CloseRefWithState({
                d3eState: this.d3eState,
                _onCloseHandler: this.onCloseHandler,
                key: "1",
              }),
            ],
            key: "0",
          }),
          TextView({ data: this.message, className: "x963", key: "1" }),
          ui.Row({
            mainAxisAlignment: ui.MainAxisAlignment.end,
            mainAxisSize: ui.MainAxisSize.max,
            children: [
              CancelRefWithState({
                d3eState: this.d3eState,
                _onCancelHandler: this.onCancelHandler,
                key: "0",
              }),
              ConfirmRefWithState({
                d3eState: this.d3eState,
                _onConfirmHandler: this.onConfirmHandler,
                key: "1",
              }),
            ],
            key: "2",
          }),
        ],
      }),
      className: ui.join(
        "GlassCardStyle_PrimaryButtonStyle ConfirmDeleteDialogWidget glassCard x2b ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public onCloseHandler = (d3eState: ConfirmDeleteDialogWidgetRefs): void => {
    this.onCancel();
  };
  public onCancelHandler = (d3eState: ConfirmDeleteDialogWidgetRefs): void => {
    this.onCancel();
  };
  public onConfirmHandler = (d3eState: ConfirmDeleteDialogWidgetRefs): void => {
    this.onConfirm();
  };
  public get onConfirm(): _ConfirmDeleteDialogWidgetOnConfirm {
    return this.props.onConfirm;
  }
  public get onCancel(): _ConfirmDeleteDialogWidgetOnCancel {
    return this.props.onCancel;
  }
  public get cancelRef() {
    return this.d3eState.cancelRef;
  }
  public get closeRef() {
    return this.d3eState.closeRef;
  }
  public get confirmRef() {
    return this.d3eState.confirmRef;
  }
}
export default function ConfirmDeleteDialogWidget(
  props: ConfirmDeleteDialogWidgetProps
) {
  return React.createElement(_ConfirmDeleteDialogWidgetState, {
    ..._ConfirmDeleteDialogWidgetState.defaultProps,
    ...props,
  });
}
