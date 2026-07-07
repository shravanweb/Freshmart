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

type _IMSHeroWidgetOnGetStarted = () => void;

type _CtaRefOnPressed = (d3eState: IMSHeroWidgetRefs) => void;

export interface IMSHeroWidgetProps extends BaseUIProps {
  key?: string;
  onGetStarted?: _IMSHeroWidgetOnGetStarted;
}
/// To store state data for IMSHeroWidget
class IMSHeroWidgetRefs {
  public ctaRef: CtaRefState = new CtaRefState();
}

interface CtaRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: IMSHeroWidgetRefs;
  _onCtaPressedHandler?: _CtaRefOnPressed;
}

class CtaRefState extends ObjectObservable {
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

class _CtaRefWithState extends ObservableComponent<CtaRefWithStateProps> {
  ctaRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: CtaRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get ctaRef(): CtaRefState {
    return this.props.d3eState.ctaRef;
  }
  public get d3eState(): IMSHeroWidgetRefs {
    return this.props.d3eState;
  }
  public get _onCtaPressedHandler(): _CtaRefOnPressed {
    return this.props._onCtaPressedHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("ctaRef", null, this.ctaRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["ctaRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return TextButton({
      label: "Get Started",
      disable: this.ctaRef.disable,
      onPressed: () => {
        this._onCtaPressedHandler(this.d3eState);
      },
      onFocusChange: (val) => {},
      className: "primary",
    });
  }
}
function CtaRefWithState(props: CtaRefWithStateProps) {
  return React.createElement(_CtaRefWithState, props);
}

class _IMSHeroWidgetState extends ObservableComponent<IMSHeroWidgetProps> {
  d3eState: IMSHeroWidgetRefs = new IMSHeroWidgetRefs();
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: IMSHeroWidgetProps) {
    super(props);

    this.initState();
  }
  public initState() {
    super.initState();

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["ctaRef"], this.rebuild);
  }
  public render(): ReactNode {
    let cStyle = this.context.theme;

    return ui.Container({
      child: ui.Column({
        mainAxisSize: ui.MainAxisSize.min,
        crossAxisAlignment: ui.CrossAxisAlignment.center,
        children: [
          TextView({
            data: "BuildACompleteInventory",
            textAlign: ui.TextAlign.center,
            className: "x868",
            key: "0",
          }),
          TextView({
            data: "Multi-tenant inventory management for retail and warehouse operations",
            textAlign: ui.TextAlign.center,
            className: "x83",
            key: "1",
          }),
          CtaRefWithState({
            d3eState: this.d3eState,
            _onCtaPressedHandler: this.onCtaPressedHandler,
            key: "2",
          }),
        ],
      }),
      className: ui.join(
        "GlassPanelStyle_PrimaryButtonStyle IMSHeroWidget glassPanel xc1 ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public onCtaPressedHandler = (d3eState: IMSHeroWidgetRefs): void => {
    this.onGetStarted();
  };
  public get onGetStarted(): _IMSHeroWidgetOnGetStarted {
    return this.props.onGetStarted;
  }
  public get ctaRef() {
    return this.d3eState.ctaRef;
  }
}
export default function IMSHeroWidget(props: IMSHeroWidgetProps) {
  return React.createElement(_IMSHeroWidgetState, props);
}
