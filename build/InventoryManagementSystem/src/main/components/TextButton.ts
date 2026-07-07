import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import ObjectObservable from "../utils/ObjectObservable";
import TextView from "./TextView";

type _TextButtonOnPressed = () => void;

type _TextButtonOnLongPressed = () => void;

type _TextChildWrapperOnKey = (
  focusNode: ui.FocusNode,
  event: ui.RawKeyEvent,
  d3eState: TextButtonRefs
) => ui.KeyEventResult;

type _TextChildWrapperOnTap = (d3eState: TextButtonRefs) => void;

type _TextChildWrapperOnLongPress = (d3eState: TextButtonRefs) => void;

type _TextButtonFocusChange = (val: boolean) => void;

export interface TextButtonProps extends BaseUIProps {
  key?: string;
  label: string;
  disable?: boolean;
  onPressed?: _TextButtonOnPressed;
  onLongPressed?: _TextButtonOnLongPressed;
  _onFocusChange?: _TextButtonFocusChange;
}
/// To store state data for TextButton
class TextButtonRefs {
  focusNode: ui.FocusNode = new ui.FocusNode();
  public textChildWrapper: TextChildWrapperState = new TextChildWrapperState();
}

interface TextChildWrapperWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: TextButtonRefs;
  _onHandleLongPress?: _TextChildWrapperOnLongPress;
  _onHandleTap?: _TextChildWrapperOnTap;
  _onKey?: _TextChildWrapperOnKey;
  _onFocusChange: _TextButtonFocusChange;
  disable: boolean;
  label: string;
}

class TextChildWrapperState extends ObjectObservable {
  private _focus: boolean = false;
  private _disabled: boolean = false;
  public get focus(): boolean {
    return this._focus;
  }
  public setFocus(val: boolean) {
    let isValChanged: boolean = this._focus !== val;

    if (!isValChanged) {
      return;
    }

    this._focus = val;

    this.fire("focus", this);
  }
  public get disabled(): boolean {
    return this._disabled;
  }
  public setDisabled(val: boolean) {
    let isValChanged: boolean = this._disabled !== val;

    if (!isValChanged) {
      return;
    }

    this._disabled = val;

    this.fire("disabled", this);
  }
}

class _TextChildWrapperWithState extends ObservableComponent<TextChildWrapperWithStateProps> {
  public constructor(props: TextChildWrapperWithStateProps) {
    super(props);

    this.initState();
  }
  public get disable(): boolean {
    return this.props.disable;
  }
  public get label(): string {
    return this.props.label;
  }
  public get textChildWrapper(): TextChildWrapperState {
    return this.props.d3eState.textChildWrapper;
  }
  public get d3eState(): TextButtonRefs {
    return this.props.d3eState;
  }
  public get _onHandleLongPress(): _TextChildWrapperOnLongPress {
    return this.props._onHandleLongPress;
  }
  public get _onHandleTap(): _TextChildWrapperOnTap {
    return this.props._onHandleTap;
  }
  public get _onKey(): _TextChildWrapperOnKey {
    return this.props._onKey;
  }
  public get _onFocusChange(): _TextButtonFocusChange {
    return this.props._onFocusChange;
  }
  public initState() {
    super.initState();

    this.updateObservable("textChildWrapper", null, this.textChildWrapper);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(
      ["disable", "label", "textChildWrapper", "textChildWrapper.disabled"],
      this.rebuild
    );
  }
  public textChildWrapperOnFocusChange(val): void {
    return this.textChildWrapper.setFocus(val);
  }
  public dispose(): void {
    this.textChildWrapper.setFocus(false);

    super.dispose();
  }
  public render(): ReactNode {
    return ui.Focus({
      focusNode: this.props.d3eState.focusNode ?? new ui.FocusNode(),
      child: ui.Container({
        states: ui.joinStates(
          { "data-disabled": this.d3eState.textChildWrapper.disabled },
          this.props.states
        ),
        child: ui.Center({
          widthFactor: 1.0,
          heightFactor: 1.0,
          child: TextView({ data: this.label }),
        }),
        onTap: (e) => {
          e.stopPropagation();

          this._onHandleTap(this.d3eState);
        },
        onLongPress: () => {
          this._onHandleLongPress(this.d3eState);
        },
        onFocusKey: (focusNode, event) => {
          return this._onKey(focusNode, event, this.d3eState);
        },
        className: ui.join("TextButton xec9 ", this.props.className ?? ""),
        ...copyBaseUIProps(this.props),
      }),
      onFocusKey: (focusNode, event) => {
        return this._onKey(focusNode, event, this.d3eState);
      },
      onFocusChange: (val) => {
        this.textChildWrapperOnFocusChange(val);

        this.props.onFocusChange(val);
      },
    });
  }
}
function TextChildWrapperWithState(props: TextChildWrapperWithStateProps) {
  return React.createElement(_TextChildWrapperWithState, props);
}

class _TextButtonState extends ObservableComponent<TextButtonProps> {
  static defaultProps = {
    label: "",
    disable: false,
    onPressed: null,
    onLongPressed: null,
  };
  d3eState: TextButtonRefs = new TextButtonRefs();
  public constructor(props: TextButtonProps) {
    super(props);

    this.initState();
  }
  public get label(): string {
    return this.props.label;
  }
  public get disable(): boolean {
    return this.props.disable;
  }
  public initState() {
    super.initState();

    this.initListeners();

    this.on(["disable"], this.onDisabledChanged);

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["disable", "label", "textChildWrapper"], this.rebuild);
  }
  public componentDidUpdate(prevProps: TextButtonProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.label !== this.props.label) {
      this.fire("label", this);
    }

    if (prevProps.disable !== this.props.disable) {
      this.fire("disable", this);
    }
  }
  public render(): ReactNode {
    return TextChildWrapperWithState({
      d3eState: this.d3eState,
      _onHandleLongPress: this.onHandleLongPress,
      _onHandleTap: this.onHandleTap,
      _onKey: this.onKey,
      disable: this.disable,
      label: this.label,
      _onFocusChange: this.onFocusChange,
      className: this.props.className ?? "",
      ...copyBaseUIProps(this.props),
    });
  }
  public onHandleTap = (d3eState: TextButtonRefs): void => {
    if (this.onPressed !== null && !d3eState.textChildWrapper.disabled) {
      this.onPressed();
    }
  };
  public onHandleLongPress = (d3eState: TextButtonRefs): void => {
    if (this.onLongPressed !== null && !d3eState.textChildWrapper.disabled) {
      this.onLongPressed();
    }
  };
  public onKey = (
    focusNode: ui.FocusNode,
    event: ui.RawKeyEvent,
    d3eState: TextButtonRefs
  ): ui.KeyEventResult => {
    if (
      event instanceof ui.RawKeyDownEvent &&
      !d3eState.textChildWrapper.disabled
    ) {
      if (
        event.logicalKey === ui.LogicalKeyboardKey.enter ||
        event.logicalKey === ui.LogicalKeyboardKey.space ||
        event.logicalKey === ui.LogicalKeyboardKey.numpadEnter
      ) {
        if (this.onPressed !== null) {
          this.onPressed();
        }

        return ui.KeyEventResult.handled;
      }
    }

    return ui.KeyEventResult.ignored;
  };
  public onDisabledChanged = (): void => {
    this.d3eState.textChildWrapper.setDisabled(this.disable);
  };
  public get onPressed(): _TextButtonOnPressed {
    return this.props.onPressed;
  }
  public get onLongPressed(): _TextButtonOnLongPressed {
    return this.props.onLongPressed;
  }
  public get onFocusChange(): _TextButtonFocusChange {
    return this.props.onFocusChange;
  }
  public get textChildWrapper() {
    return this.d3eState.textChildWrapper;
  }
}
export default function TextButton(props: TextButtonProps) {
  return React.createElement(_TextButtonState, {
    ..._TextButtonState.defaultProps,
    ...props,
  });
}
