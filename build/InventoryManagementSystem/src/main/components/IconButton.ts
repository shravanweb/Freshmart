import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import ObjectObservable from "../utils/ObjectObservable";
import IconView from "./IconView";

type _IconButtonOnPressed = () => void;

type _IconButtonOnLongPressed = () => void;

type _IconChildWrapperOnKey = (
  focusNode: ui.FocusNode,
  event: ui.RawKeyEvent,
  d3eState: IconButtonRefs
) => ui.KeyEventResult;

type _IconChildWrapperOnTap = (d3eState: IconButtonRefs) => void;

type _IconChildWrapperOnLongPress = (d3eState: IconButtonRefs) => void;

type _IconButtonFocusChange = (val: boolean) => void;

export interface IconButtonProps extends BaseUIProps {
  key?: string;
  icon: ui.IconData;
  disable?: boolean;
  onPressed?: _IconButtonOnPressed;
  onLongPressed?: _IconButtonOnLongPressed;
  _onFocusChange?: _IconButtonFocusChange;
}
/// To store state data for IconButton
class IconButtonRefs {
  focusNode: ui.FocusNode = new ui.FocusNode();
  public iconChildWrapper: IconChildWrapperState = new IconChildWrapperState();
}

interface IconChildWrapperWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: IconButtonRefs;
  _onHandleLongPress?: _IconChildWrapperOnLongPress;
  _onHandleTap?: _IconChildWrapperOnTap;
  _onKey?: _IconChildWrapperOnKey;
  _onFocusChange: _IconButtonFocusChange;
  disable: boolean;
  icon: ui.IconData;
}

class IconChildWrapperState extends ObjectObservable {
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

class _IconChildWrapperWithState extends ObservableComponent<IconChildWrapperWithStateProps> {
  public constructor(props: IconChildWrapperWithStateProps) {
    super(props);

    this.initState();
  }
  public get disable(): boolean {
    return this.props.disable;
  }
  public get icon(): ui.IconData {
    return this.props.icon;
  }
  public get iconChildWrapper(): IconChildWrapperState {
    return this.props.d3eState.iconChildWrapper;
  }
  public get d3eState(): IconButtonRefs {
    return this.props.d3eState;
  }
  public get _onHandleLongPress(): _IconChildWrapperOnLongPress {
    return this.props._onHandleLongPress;
  }
  public get _onHandleTap(): _IconChildWrapperOnTap {
    return this.props._onHandleTap;
  }
  public get _onKey(): _IconChildWrapperOnKey {
    return this.props._onKey;
  }
  public get _onFocusChange(): _IconButtonFocusChange {
    return this.props._onFocusChange;
  }
  public initState() {
    super.initState();

    this.updateObservable("iconChildWrapper", null, this.iconChildWrapper);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(
      ["disable", "icon", "iconChildWrapper", "iconChildWrapper.disabled"],
      this.rebuild
    );
  }
  public iconChildWrapperOnFocusChange(val): void {
    return this.iconChildWrapper.setFocus(val);
  }
  public dispose(): void {
    this.iconChildWrapper.setFocus(false);

    super.dispose();
  }
  public render(): ReactNode {
    return ui.Focus({
      focusNode: this.props.d3eState.focusNode ?? new ui.FocusNode(),
      child: ui.Container({
        states: ui.joinStates(
          { "data-disabled": this.d3eState.iconChildWrapper.disabled },
          this.props.states
        ),
        child: ui.Center({
          widthFactor: 1.0,
          heightFactor: 1.0,
          child: IconView({ icon: this.icon }),
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
        className: ui.join("IconButton x1a7 ", this.props.className ?? ""),
        ...copyBaseUIProps(this.props),
      }),
      onFocusKey: (focusNode, event) => {
        return this._onKey(focusNode, event, this.d3eState);
      },
      onFocusChange: (val) => {
        this.iconChildWrapperOnFocusChange(val);

        this.props.onFocusChange(val);
      },
    });
  }
}
function IconChildWrapperWithState(props: IconChildWrapperWithStateProps) {
  return React.createElement(_IconChildWrapperWithState, props);
}

class _IconButtonState extends ObservableComponent<IconButtonProps> {
  static defaultProps = {
    icon: null,
    disable: false,
    onPressed: null,
    onLongPressed: null,
  };
  d3eState: IconButtonRefs = new IconButtonRefs();
  public constructor(props: IconButtonProps) {
    super(props);

    this.initState();
  }
  public get icon(): ui.IconData {
    return this.props.icon;
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
    this.on(["disable", "icon", "iconChildWrapper"], this.rebuild);
  }
  public componentDidUpdate(prevProps: IconButtonProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.icon !== this.props.icon) {
      this.fire("icon", this);
    }

    if (prevProps.disable !== this.props.disable) {
      this.fire("disable", this);
    }
  }
  public render(): ReactNode {
    return IconChildWrapperWithState({
      d3eState: this.d3eState,
      _onHandleLongPress: this.onHandleLongPress,
      _onHandleTap: this.onHandleTap,
      _onKey: this.onKey,
      disable: this.disable,
      icon: this.icon,
      _onFocusChange: this.onFocusChange,
      className: this.props.className ?? "",
      ...copyBaseUIProps(this.props),
    });
  }
  public onHandleTap = (d3eState: IconButtonRefs): void => {
    if (this.onPressed !== null && !d3eState.iconChildWrapper.disabled) {
      this.onPressed();
    }
  };
  public onHandleLongPress = (d3eState: IconButtonRefs): void => {
    if (this.onLongPressed !== null && !d3eState.iconChildWrapper.disabled) {
      this.onLongPressed();
    }
  };
  public onKey = (
    focusNode: ui.FocusNode,
    event: ui.RawKeyEvent,
    d3eState: IconButtonRefs
  ): ui.KeyEventResult => {
    if (
      event instanceof ui.RawKeyDownEvent &&
      !d3eState.iconChildWrapper.disabled
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
    this.d3eState.iconChildWrapper.setDisabled(this.disable);
  };
  public get onPressed(): _IconButtonOnPressed {
    return this.props.onPressed;
  }
  public get onLongPressed(): _IconButtonOnLongPressed {
    return this.props.onLongPressed;
  }
  public get onFocusChange(): _IconButtonFocusChange {
    return this.props.onFocusChange;
  }
  public get iconChildWrapper() {
    return this.d3eState.iconChildWrapper;
  }
}
export default function IconButton(props: IconButtonProps) {
  return React.createElement(_IconButtonState, {
    ..._IconButtonState.defaultProps,
    ...props,
  });
}
