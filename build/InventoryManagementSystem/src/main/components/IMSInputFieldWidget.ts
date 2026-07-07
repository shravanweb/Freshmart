import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import TextView from "./TextView";
import { BuildContext } from "../classes/BuildContext";

type _InputRefOnChanged = (text: string) => void;

export interface IMSInputFieldWidgetProps extends BaseUIProps {
  key?: string;
  label: string;
  text: string;
  placeHolder?: string;
  obscureText?: boolean;
  errorText?: string;
  keyboardType?: string;
}

class _IMSInputFieldWidgetState extends ObservableComponent<IMSInputFieldWidgetProps> {
  static defaultProps = {
    label: "",
    text: "",
    placeHolder: "",
    obscureText: false,
    errorText: "",
    keyboardType: "text",
  };
  inputRefController: ui.TextEditingController = new ui.TextEditingController();
  inputRefFocusNode: ui.FocusNode = new ui.FocusNode();
  fieldValue: string = "";
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: IMSInputFieldWidgetProps) {
    super(props);

    this.initState();
  }
  public get label(): string {
    return this.props.label;
  }
  public get text(): string {
    return this.props.text;
  }
  public get placeHolder(): string {
    return this.props.placeHolder;
  }
  public get obscureText(): boolean {
    return this.props.obscureText;
  }
  public get errorText(): string {
    return this.props.errorText;
  }
  public get keyboardType(): string {
    return this.props.keyboardType;
  }
  public initState() {
    super.initState();

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.on(["text"], this.computeFieldValue);

    this.computeFieldValue();

    this.on(
      [
        "errorText",
        "fieldValue",
        "keyboardType",
        "label",
        "obscureText",
        "placeHolder",
      ],
      this.rebuild
    );
  }
  public componentDidUpdate(prevProps: IMSInputFieldWidgetProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.label !== this.props.label) {
      this.fire("label", this);
    }

    if (prevProps.text !== this.props.text) {
      this.fire("text", this);
    }

    if (prevProps.placeHolder !== this.props.placeHolder) {
      this.fire("placeHolder", this);
    }

    if (prevProps.obscureText !== this.props.obscureText) {
      this.fire("obscureText", this);
    }

    if (prevProps.errorText !== this.props.errorText) {
      this.fire("errorText", this);
    }

    if (prevProps.keyboardType !== this.props.keyboardType) {
      this.fire("keyboardType", this);
    }
  }
  public setFieldValue(val: string): void {
    let isValChanged: boolean = this.fieldValue !== val;

    if (!isValChanged) {
      return;
    }

    this.fieldValue = val;

    this.fire("fieldValue", this);
  }
  public computeFieldValue = (): void => {
    this.setFieldValue(this.text);
  };
  public render(): ReactNode {
    let cStyle = this.context.theme;

    return ui.Container({
      child: ui.Column({
        mainAxisSize: ui.MainAxisSize.min,
        crossAxisAlignment: ui.CrossAxisAlignment.stretch,
        children: [
          TextView({
            data: this.label + (this.keyboardType !== "text" ? "" : ""),
            className: "fieldLabel",
            key: "0",
          }),
          ui.InputField({
            value: this.fieldValue,
            placeHolder: this.placeHolder,
            obscureText: this.obscureText,
            controller: this.inputRefController,
            onChanged: (text) => {
              this.inputRefonChanged(text);

              this.onInputChangedHandler(text);
            },
            onFocusChange: (val) => {},
            focusNode: this.inputRefFocusNode,
            key: "1",
          }),
          this.errorText !== null && this.errorText.isNotEmpty
            ? TextView({ data: this.errorText, className: "fieldError x3c" })
            : [],
        ],
      }),
      className: ui.join(
        "FormFieldStyle IMSInputFieldWidget formField ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public onInputChangedHandler = (text: string): void => {
    text = text;
  };
  public inputRefonChanged = (val: string): void => {
    this.setFieldValue(val);
  };
}
export default function IMSInputFieldWidget(props: IMSInputFieldWidgetProps) {
  return React.createElement(_IMSInputFieldWidgetState, {
    ..._IMSInputFieldWidgetState.defaultProps,
    ...props,
  });
}
