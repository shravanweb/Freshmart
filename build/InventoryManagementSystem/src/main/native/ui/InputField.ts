import React from "react";
import Duration from "../../core/Duration";
import Timer from "../../utils/Timer";
import { Brightness } from "../classes/Brightness";
import { Color } from "../classes/Color";
import { DragStartBehavior } from "../classes/DragStartBehavior";
import { EdgeInsets } from "../classes/EdgeInsets";
import { FocusNode } from "../classes/FocusNode";
import { Radius } from "../classes/Radius";
import { ScrollPhysics } from "../classes/ScrollPhysics";
import StrutStyle from "../classes/StrutStyle";
import { TextAlign } from "../classes/TextAlign";
import { TextDirection } from "../classes/TextDirection";
import { TextEditingController } from "../classes/TextEditingController";
import { TextInputType } from "../classes/TextInputType";
import TextStyle from "../classes/TextStyle";
import ToolbarOptions from "../classes/ToolbarOptions";
import BaseUIProps, { processBaseUIProps } from "./BaseUIProps";

let idCounter = 0;

function generateId(prefix = "input") {
  return `${prefix}-${++idCounter}`;
}

interface InputFieldProps extends BaseUIProps {
  id?: string;
  name?: string;
  value?: string;
  padding?: EdgeInsets;
  inputFormatter?: String;
  controller?: TextEditingController;
  keyboardType?: TextInputType;
  style?: TextStyle;
  strutStyle?: StrutStyle;
  textAlign?: TextAlign;
  textDirection?: TextDirection;
  autofocus?: boolean; //TODO
  obscureText?: boolean;
  autocorrect?: boolean; // TODO
  maxLines?: number; // TODO
  minLines?: number; // TODO
  expands?: boolean; // TODO
  maxLength?: number; // TODO
  maxLengthEnforced?: boolean; // TODO
  enabled?: boolean;
  disable?: boolean;
  cornerRadius?: number;
  iWidth?: number; // TODO
  iHeight?: number; // TODO
  cursorWidth?: number; // TODO
  cursorRadius?: Radius; // TODO
  cursorColor?: Color; // TODO
  placeHolderColor?: Color; //TODO
  keyboardAppearance?: Brightness; // TODO
  scrollPadding?: EdgeInsets; // TODO
  enableInteractiveSelection?: boolean; // TODO
  dragStartBehavior?: DragStartBehavior; // TODO
  scrollPhysics?: ScrollPhysics; // TODO
  focusNode?: FocusNode; // TODO
  readOnly?: boolean;
  toolbarOptions?: ToolbarOptions; // TODO
  showCursor?: boolean; // TODO
  enableSuggestions?: boolean; // TODO
  dense?: boolean; // TODO
  placeHolder?: string;
  activeColor?: Color;
  inActiveColor?: Color;
  backgroundColor?: Color;
  isRenderIngnores?: boolean;
  onChanged?: (text: string) => void;
  onEditingComplete?: () => void;
  onSubmitted?: (text: string) => void;
  onTap?: (event?: any) => void;
  onFocusChange?: (val: boolean) => void;
}

class _InputField extends React.Component<InputFieldProps, {}> {
  static defaultProps = {
    id: null,
    name: "",
    autofocus: false,
    obscureText: false,
    autocorrect: true,
    maxLines: 1,
    minLines: 0,
    expands: false,
    maxLengthEnforced: true,
    cursorWidth: 2.0,
    scrollPadding: { top: 20.0, left: 20.0, right: 20.0, bottom: 20.0 },
    enableInteractiveSelection: true,
    dragStartBehavior: DragStartBehavior.start,
    readOnly: false,
    showCursor: true,
    enableSuggestions: true,
    dense: false,
    cornerRadius: 0.0,
    iWidth: 1.0,
    iHeight: 1.0,
    activeColor: new Color(0xff14acff),
    inActiveColor: new Color(0xffbfbfbf),
  };

  private ref: HTMLInputElement;
  private _controller: TextEditingController;
  private _focus: FocusNode;
  private _isMounted: boolean;

  constructor(props: InputFieldProps) {
    super(props);
    this.onChange = this.onChange.bind(this);
    this.onFocusNodeHandler = this.onFocusNodeHandler.bind(this);
    this.onInputSubmitted = this.onInputSubmitted.bind(this);
    this.handleClick = this.handleClick.bind(this);
    this.handleFocus = this.handleFocus.bind(this);
    this.handleKeyPress = this.handleKeyPress.bind(this);
    this.handleRef = this.handleRef.bind(this);
    this.handleTextChange = this.handleTextChange.bind(this);
    this.handleSelectionChange = this.handleSelectionChange.bind(this);
    this.state = {};
    this.init();
  }

  componentDidMount() {
    this._isMounted = true;
  }

  componentWillUnmount() {
    this._isMounted = false;
  }

  get controller() {
    return this._controller;
  }

  get focus() {
    return this._focus;
  }

  setIsActive(isActive: boolean) {
    if (!this._isMounted) {
      return;
    }
    this.setState({
      isActive: isActive,
    });
  }

  setIsActiveField(isActiveField: boolean) {
    if (!this._isMounted) {
      return;
    }
    this.setState({
      isActiveField: isActiveField,
    });
  }

  init() {
    this._controller = this.props.controller || new TextEditingController();
    this.controller.connect(this);
    this._focus = this.props.focusNode || new FocusNode();
    this.focus.connect(this.ref);
    this.focus.addListener(this.onFocusNodeHandler);
    if (this.focus) {
      this.handleFocus();
    }
  }

  onFocusNodeHandler() {
    if (this._focus.hasFocus) {
      this.setIsActive(true);
      this.setIsActiveField(true);
      if (this.props.onTap) {
        this.props.onTap();
      }
    } else {
      this.setIsActive(false);
      this.setIsActiveField(false);
      if (this.props.onEditingComplete) {
        this.props.onEditingComplete();
      }
    }
  }

  onChange(event) {
    let value = event.target.value;
    this.controller.text = value;
    this.handleSelectionChange();
  }

  onInputSubmitted(text: string) {
    this.setIsActive(false);
    this.props.onSubmitted?.(text);
  }

  handleFocus(blur: boolean = false) {
    if (blur) {
      this.focus.removeFocus();
    } else {
      this.focus.requestFocus();
    }
  }

  handleSelectionChange() {
    let ref = this.ref;
    if (ref !== null && typeof ref === "object") {
      let start = ref!.selectionStart || 0;
      let end = ref!.selectionEnd || 0;
      this.controller.createSelection(start, end);
    }
  }

  handleTextChange(text: string) {
    this.props.onChanged?.(text);
    this.ref.value = text;
  }

  handleRef(e: HTMLInputElement) {
    this.ref = e;
    this.focus.connect(this.ref);
    if (e?.value) {
      let prevVal: string = e.value;
      this.ref.addEventListener("input", function (e) {
        if (this.checkValidity()) {
          prevVal = this.value;
        } else {
          this.value = prevVal;
        }
      });
    }
  }

  handleClick() {
    this.handleFocus();
    this.handleSelectionChange();
    if (this.props.onTap) {
      this.props.onTap();
    }
  }

  handleKeyPress(event) {
    if (event.key === "Enter") {
      this.onInputSubmitted(this.props.value || "");
    }
  }

  inputType(): string {
    if (this.props.keyboardType) {
      switch (this.props.keyboardType) {
        case TextInputType.text:
          return "text";
        case TextInputType.multiline:
          return "text";
        case TextInputType.numberValue:
          return "number";
        case TextInputType.number:
          return "number";
        case TextInputType.phone:
          return "tel";
        case TextInputType.datetime:
          return "datetime-local";
        case TextInputType.emailAddress:
          return "email";
        case TextInputType.url:
          return "url";
        case TextInputType.visiblePassword:
          return "password";
        case TextInputType.name:
          return "text";
        case TextInputType.streetAddress:
          return "text";
        case TextInputType.none:
          return "text";
      }
    }
    return "none";
  }

  applyTextSyle(inputStyles: any) {
    if (this.props.style) {
      var style = this.props.style;
      if (style.color) {
        inputStyles.color = style.color.toHexa();
      }
      if (style.fontSize) {
        inputStyles.fontSize = style.fontSize;
      }
      if (style.fontWeight) {
        inputStyles.fontWeight = style.fontWeight;
      }
    }
  }

  applyStructStyle(inputStyles: any) {
    if (this.props.strutStyle) {
      var strutStyle = this.props.strutStyle;
      if (strutStyle.fontFamily) {
        inputStyles.fontFamily = strutStyle.fontFamily;
      }
      if (strutStyle.fontSize) {
        inputStyles.fontSize = strutStyle.fontSize;
      }
    }
  }

  applyTextAlign(inputStyles: any) {
    if (this.props.textAlign) {
      switch (this.props.textAlign) {
        case TextAlign.left:
          inputStyles.textAlign = "left";
          break;
        case TextAlign.right:
          inputStyles.textAlign = "right";
          break;
        case TextAlign.center:
          inputStyles.textAlign = "center";
          break;
        case TextAlign.justify:
          inputStyles.textAlign = "justify";
          break;
      }
    }
  }

  applyColors(inputStyles: any) {
    if (this._focus.hasFocus) {
      if (this.props.activeColor) {
        if (this.props.activeColor.value !== 0xff14acff) {
          inputStyles.borderColor = this.props.activeColor.toHexa();
          inputStyles.borderWidth = 1.0;
        }
      }
    } else {
      if (this.props.inActiveColor) {
        if (this.props.inActiveColor.value !== 0xffbfbfbf) {
          inputStyles.borderColor = this.props.inActiveColor.toHexa();
          inputStyles.borderWidth = 1.0;
        }
      }
    }
  }

  private applyPadding(style: any) {
    if (this.props.padding == null) {
      return;
    }
    if (this.props.padding.asDirection) {
      let v: number = this.props.padding.vertical ?? 0,
        h: number = this.props.padding.horizontal ?? 0;
      Object.assign(style, {
        paddingTop: v,
        paddingBottom: v,
        paddingLeft: h,
        paddingRight: h,
      });
    } else {
      let t: number = this.props.padding.top ?? 0,
        l: number = this.props.padding.left ?? 0,
        r: number = this.props.padding.right ?? 0,
        b: number = this.props.padding.bottom ?? 0;
      Object.assign(style, {
        paddingTop: t,
        paddingBottom: b,
        paddingLeft: l,
        paddingRight: r,
      });
    }
  }

  render() {
    var inputProps: any = {};
    var inputStyles: any = {};
    inputProps.style = inputStyles;

    const id = this.props.id ?? generateId("input");
    const name = this.props.name ?? id;

    inputProps.id = id;
    inputProps.name = name;
    var type = this.inputType();
    if (type != null) {
      inputProps.type = type;
      inputStyles.type = type;
    }
    this.applyTextSyle(inputStyles);
    this.applyStructStyle(inputStyles);
    this.applyTextAlign(inputStyles);
    this.applyColors(inputStyles);
    if (this.props.textDirection) {
      if (this.props.textDirection === TextDirection.rtl) {
        inputStyles.direction = "rtl";
      } else {
        inputStyles.direction = "ltr";
      }
    }
    if (this.props.cornerRadius) {
      inputStyles.borderRadius = this.props.cornerRadius;
    }
    if (this.props.disable) {
      inputProps.disabled = true;
    }
    if (this.props.readOnly) {
      inputProps.readOnly = true;
    }
    if (this.props.placeHolder) {
      inputProps.placeholder = this.props.placeHolder;
    }
    if (this.props.inputFormatter) {
      inputProps.pattern = this.props.inputFormatter;
    }
    if (this.props.padding) {
      this.applyPadding(inputStyles);
    }
    if (this.props.backgroundColor) {
      inputStyles.backgroundColor = this.props.backgroundColor.toHexa();
    }

    inputProps.onSelect = this.handleSelectionChange;
    inputProps.onInput = this.onChange;
    inputProps.ref = this.handleRef;
    inputProps.onClick = this.handleClick;
    inputProps.onFocus = (e) => this.handleFocus();
    inputProps.onBlur = (e) => this.handleFocus(true);
    inputProps.onKeyPress = this.handleKeyPress;
    inputProps.className =
      "InputField " +
      (this._focus.hasFocus ? "active " : "") +
      (this.props.className ?? "");
    if (this.props.obscureText) {
      inputProps.type = "password";
    }
    if (this.props.value) {
      new Timer(new Duration({ milliseconds: 10 }), () => {
        if (this.ref) {
          this.ref.value = this.props.value;
        }
      });
    }

    if (this.props.keyboardType === TextInputType.multiline) {
      return React.createElement("textarea", {
        ...inputProps,
        ...processBaseUIProps(this.props),
        ref: this.handleRef,
      });
    } else {
      return React.createElement("input", {
        ...inputProps,
        ...processBaseUIProps(this.props),
        ref: this.handleRef,
      });
    }
  }
}

export default function InputField(props: InputFieldProps) {
  return React.createElement(_InputField, {
    ..._InputField.defaultProps,
    ...props,
  });
}
