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

type _LineItemEditorWidgetOnAddLine = () => void;

type _LineItemEditorWidgetOnRemoveLine = (lineIndex: number) => void;

type _LineItemEditorWidgetOnLineChange = (lineIndex: number) => void;

type _AddLineRefOnPressed = (d3eState: LineItemEditorWidgetRefs) => void;

export interface LineItemEditorWidgetProps extends BaseUIProps {
  key?: string;
  mode?: string;
  onAddLine?: _LineItemEditorWidgetOnAddLine;
  onRemoveLine?: _LineItemEditorWidgetOnRemoveLine;
  onLineChange?: _LineItemEditorWidgetOnLineChange;
}
/// To store state data for LineItemEditorWidget
class LineItemEditorWidgetRefs {
  public addLineRef: AddLineRefState = new AddLineRefState();
}

interface AddLineRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: LineItemEditorWidgetRefs;
  _onAddLineHandler?: _AddLineRefOnPressed;
}

class AddLineRefState extends ObjectObservable {
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

class _AddLineRefWithState extends ObservableComponent<AddLineRefWithStateProps> {
  addLineRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: AddLineRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get addLineRef(): AddLineRefState {
    return this.props.d3eState.addLineRef;
  }
  public get d3eState(): LineItemEditorWidgetRefs {
    return this.props.d3eState;
  }
  public get _onAddLineHandler(): _AddLineRefOnPressed {
    return this.props._onAddLineHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("addLineRef", null, this.addLineRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["addLineRef"], this.rebuild);
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    return TextButton({
      label: "Add Line",
      disable: this.addLineRef.disable,
      onPressed: () => {
        this._onAddLineHandler(this.d3eState);
      },
      onFocusChange: (val) => {},
      className: "primary",
    });
  }
}
function AddLineRefWithState(props: AddLineRefWithStateProps) {
  return React.createElement(_AddLineRefWithState, props);
}

class _LineItemEditorWidgetState extends ObservableComponent<LineItemEditorWidgetProps> {
  static defaultProps = {
    mode: "edit",
    onAddLine: null,
    onRemoveLine: null,
    onLineChange: null,
  };
  d3eState: LineItemEditorWidgetRefs = new LineItemEditorWidgetRefs();
  lineCount: number = 0;
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: LineItemEditorWidgetProps) {
    super(props);

    this.initState();
  }
  public get mode(): string {
    return this.props.mode;
  }
  public initState() {
    super.initState();

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["addLineRef", "lineCount", "mode"], this.rebuild);
  }
  public componentDidUpdate(prevProps: LineItemEditorWidgetProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.mode !== this.props.mode) {
      this.fire("mode", this);
    }
  }
  public setLineCount(val: number): void {
    let isValChanged: boolean = this.lineCount !== val;

    if (!isValChanged) {
      return;
    }

    this.lineCount = val;

    this.fire("lineCount", this);
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
                data:
                  this.mode + " \u00B7 " + this.lineCount.toString() + " lines",
                className: "x278b",
                key: "0",
              }),
              AddLineRefWithState({
                d3eState: this.d3eState,
                _onAddLineHandler: this.onAddLineHandler,
                key: "1",
              }),
            ],
            key: "0",
          }),
          ui.Container({
            child: TextView({
              data:
                this.mode +
                " mode \u2014 bind line collection from parent document`",
              className: "x9f",
            }),
            className: "glassPanel xaff",
            key: "1",
          }),
        ],
      }),
      className: ui.join(
        "GlassCardStyle_PrimaryButtonStyle_GlassPanelStyle LineItemEditorWidget glassCardFlat ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public onAddLineHandler = (d3eState: LineItemEditorWidgetRefs): void => {
    this.onAddLine();
  };
  public get onAddLine(): _LineItemEditorWidgetOnAddLine {
    return this.props.onAddLine;
  }
  public get onRemoveLine(): _LineItemEditorWidgetOnRemoveLine {
    return this.props.onRemoveLine;
  }
  public get onLineChange(): _LineItemEditorWidgetOnLineChange {
    return this.props.onLineChange;
  }
  public get addLineRef() {
    return this.d3eState.addLineRef;
  }
}
export default function LineItemEditorWidget(props: LineItemEditorWidgetProps) {
  return React.createElement(_LineItemEditorWidgetState, {
    ..._LineItemEditorWidgetState.defaultProps,
    ...props,
  });
}
