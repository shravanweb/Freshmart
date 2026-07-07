import * as ui from "../native/index";

export class InputFieldHelper {
  constructor() {}
  public static getMarginInfo(): ui.EdgeInsets {
    return ui.EdgeInsets.all(0.0);
  }

  public static getPaddingInfo(
    dense: boolean,
    padding: ui.EdgeInsets
  ): ui.EdgeInsets {
    if (padding != null) {
      return padding;
    }
    if (dense) {
      return ui.EdgeInsets.only({
        left: 2.0,
        right: 0.0,
        top: 2.0,
        bottom: 2.0,
      });
    } else {
      return ui.EdgeInsets.only({
        left: 8.0,
        right: 8.0,
        top: 5.0,
        bottom: 5.0,
      });
    }
  }

  public static getDefaultBorderColor(): ui.Color {
    return ui.HexColor.fromHexInt(0xffbfbfbf);
  }

  public static getBorderInfo(borderColor: ui.Color): ui.Border {
    return ui.Border.all({
      width: 1.0,
      color:
        borderColor != null
          ? borderColor
          : InputFieldHelper.getDefaultBorderColor(),
    });
  }

  public static validateFocusNode(node: ui.FocusNode): ui.FocusNode {
    if (node != null) {
      return node;
    } else {
      return new ui.FocusNode();
    }
  }

  public static validateTextStyle(textStyle: ui.TextStyle): ui.TextStyle {
    if (textStyle != null) {
      return textStyle;
    } else {
      return new ui.TextStyle({ color: ui.HexColor.fromHexInt(0xff262626) });
    }
  }

  public static validatePalceHolderStyle(
    textStyle: ui.TextStyle,
    placeHolderColor: ui.Color
  ): ui.TextStyle {
    if (textStyle != null) {
      return textStyle.copyWith({
        color:
          placeHolderColor != null
            ? placeHolderColor
            : new ui.Color(0x61000000),
      });
    } else {
      return new ui.TextStyle({ color: new ui.Color(0x61000000) });
    }
  }

  public static getActiveBorderColor(cursorColor: ui.Color): ui.Color {
    if (cursorColor != null) {
      return cursorColor;
    } else {
      return ui.HexColor.fromHexInt(0xff14acff);
    }
  }

  public static getScrollPaddingInfo(padding: ui.EdgeInsets): ui.EdgeInsets {
    if (padding != null) {
      return padding;
    } else {
      return ui.EdgeInsets.only({
        top: 20.0,
        left: 20.0,
        right: 20.0,
        bottom: 20.0,
      });
    }
  }

  public static getToolBarOptions(
    toolBar: ui.ToolbarOptions,
    obscureText: boolean
  ): ui.ToolbarOptions {
    if (toolBar != null) {
      return toolBar;
    } else {
      if (obscureText) {
        return new ui.ToolbarOptions({ selectAll: true, paste: true });
      } else {
        return new ui.ToolbarOptions({
          copy: true,
          cut: true,
          selectAll: true,
          paste: true,
        });
      }
    }
  }

  public static getErrorTextStyle(): ui.TextStyle {
    return new ui.TextStyle({
      color: ui.HexColor.fromHexInt(0xffff0000),
      fontSize: 10.0,
    });
  }
}
