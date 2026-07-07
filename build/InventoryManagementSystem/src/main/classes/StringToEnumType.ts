import * as ui from "../native";
import DBSaveStatus from "../utils/DBSaveStatus";
import AppUserRole from "./AppUserRole";
import ResultStatus from "./ResultStatus";
import PurchaseOrderStatus from "./PurchaseOrderStatus";
import ConnectionStatus from "./ConnectionStatus";
import IconType from "./IconType";
import GradientType from "./GradientType";

class StringToEnumType {
  public static stringToAlignment(str: string): ui.Alignment {
    switch (str) {
      case "topLeft": {
        return ui.Alignment.topLeft;
      }

      case "topCenter": {
        return ui.Alignment.topCenter;
      }

      case "topRight": {
        return ui.Alignment.topRight;
      }

      case "centerLeft": {
        return ui.Alignment.centerLeft;
      }

      case "center": {
        return ui.Alignment.center;
      }

      case "centerRight": {
        return ui.Alignment.centerRight;
      }

      case "bottomLeft": {
        return ui.Alignment.bottomLeft;
      }

      case "bottomCenter": {
        return ui.Alignment.bottomCenter;
      }

      case "bottomRight": {
        return ui.Alignment.bottomRight;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToAppUserRole(str: string): AppUserRole {
    switch (str) {
      case "OrganizationAdmin": {
        return AppUserRole.OrganizationAdmin;
      }

      case "StoreManager": {
        return AppUserRole.StoreManager;
      }

      case "WarehouseManager": {
        return AppUserRole.WarehouseManager;
      }

      case "PurchaseManager": {
        return AppUserRole.PurchaseManager;
      }

      case "InventoryClerk": {
        return AppUserRole.InventoryClerk;
      }

      case "SalesStaff": {
        return AppUserRole.SalesStaff;
      }

      case "Accountant": {
        return AppUserRole.Accountant;
      }

      case "Viewer": {
        return AppUserRole.Viewer;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToBlendMode(str: string): ui.BlendMode {
    switch (str) {
      case "clear": {
        return ui.BlendMode.clear;
      }

      case "src": {
        return ui.BlendMode.src;
      }

      case "dst": {
        return ui.BlendMode.dst;
      }

      case "srcOver": {
        return ui.BlendMode.srcOver;
      }

      case "dstOver": {
        return ui.BlendMode.dstOver;
      }

      case "srcIn": {
        return ui.BlendMode.srcIn;
      }

      case "dstIn": {
        return ui.BlendMode.dstIn;
      }

      case "srcOut": {
        return ui.BlendMode.srcOut;
      }

      case "dstOut": {
        return ui.BlendMode.dstOut;
      }

      case "srcATop": {
        return ui.BlendMode.srcATop;
      }

      case "dstATop": {
        return ui.BlendMode.dstATop;
      }

      case "xor": {
        return ui.BlendMode.xor;
      }

      case "plus": {
        return ui.BlendMode.plus;
      }

      case "modulate": {
        return ui.BlendMode.modulate;
      }

      case "screen": {
        return ui.BlendMode.screen;
      }

      case "overlay": {
        return ui.BlendMode.overlay;
      }

      case "darken": {
        return ui.BlendMode.darken;
      }

      case "lighten": {
        return ui.BlendMode.lighten;
      }

      case "colorDodge": {
        return ui.BlendMode.colorDodge;
      }

      case "colorBurn": {
        return ui.BlendMode.colorBurn;
      }

      case "hardLight": {
        return ui.BlendMode.hardLight;
      }

      case "softLight": {
        return ui.BlendMode.softLight;
      }

      case "difference": {
        return ui.BlendMode.difference;
      }

      case "exclusion": {
        return ui.BlendMode.exclusion;
      }

      case "multiply": {
        return ui.BlendMode.multiply;
      }

      case "hue": {
        return ui.BlendMode.hue;
      }

      case "saturation": {
        return ui.BlendMode.saturation;
      }

      case "color": {
        return ui.BlendMode.color;
      }

      case "luminosity": {
        return ui.BlendMode.luminosity;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToBorderStyle(str: string): ui.BorderStyle {
    switch (str) {
      case "none": {
        return ui.BorderStyle.none;
      }

      case "solid": {
        return ui.BorderStyle.solid;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToBoxFit(str: string): ui.BoxFit {
    switch (str) {
      case "fill": {
        return ui.BoxFit.fill;
      }

      case "contain": {
        return ui.BoxFit.contain;
      }

      case "cover": {
        return ui.BoxFit.cover;
      }

      case "fitWidth": {
        return ui.BoxFit.fitWidth;
      }

      case "fitHeight": {
        return ui.BoxFit.fitHeight;
      }

      case "none": {
        return ui.BoxFit.none;
      }

      case "scaleDown": {
        return ui.BoxFit.scaleDown;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToBoxShape(str: string): ui.BoxShape {
    switch (str) {
      case "rectangle": {
        return ui.BoxShape.rectangle;
      }

      case "circle": {
        return ui.BoxShape.circle;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToBrightness(str: string): ui.Brightness {
    switch (str) {
      case "dark": {
        return ui.Brightness.dark;
      }

      case "light": {
        return ui.Brightness.light;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToClip(str: string): ui.Clip {
    switch (str) {
      case "none": {
        return ui.Clip.none;
      }

      case "hardEdge": {
        return ui.Clip.hardEdge;
      }

      case "antiAlias": {
        return ui.Clip.antiAlias;
      }

      case "antiAliasWithSaveLayer": {
        return ui.Clip.antiAliasWithSaveLayer;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToConnectionStatus(str: string): ConnectionStatus {
    switch (str) {
      case "Connecting": {
        return ConnectionStatus.Connecting;
      }

      case "Connected": {
        return ConnectionStatus.Connected;
      }

      case "ConnectionBusy": {
        return ConnectionStatus.ConnectionBusy;
      }

      case "ConnectionNormal": {
        return ConnectionStatus.ConnectionNormal;
      }

      case "ConnectionFailed": {
        return ConnectionStatus.ConnectionFailed;
      }

      case "RestoreFailed": {
        return ConnectionStatus.RestoreFailed;
      }

      case "AuthFailed": {
        return ConnectionStatus.AuthFailed;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToCrossAxisAlignment(str: string): ui.CrossAxisAlignment {
    switch (str) {
      case "start": {
        return ui.CrossAxisAlignment.start;
      }

      case "end": {
        return ui.CrossAxisAlignment.end;
      }

      case "center": {
        return ui.CrossAxisAlignment.center;
      }

      case "stretch": {
        return ui.CrossAxisAlignment.stretch;
      }

      case "baseline": {
        return ui.CrossAxisAlignment.baseline;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToDBSaveStatus(str: string): DBSaveStatus {
    switch (str) {
      case "New": {
        return DBSaveStatus.New;
      }

      case "Changed": {
        return DBSaveStatus.Changed;
      }

      case "Saved": {
        return DBSaveStatus.Saved;
      }

      case "Deleted": {
        return DBSaveStatus.Deleted;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToDragStartBehavior(str: string): ui.DragStartBehavior {
    switch (str) {
      case "down": {
        return ui.DragStartBehavior.down;
      }

      case "start": {
        return ui.DragStartBehavior.start;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToFilterQuality(str: string): ui.FilterQuality {
    switch (str) {
      case "none": {
        return ui.FilterQuality.none;
      }

      case "low": {
        return ui.FilterQuality.low;
      }

      case "medium": {
        return ui.FilterQuality.medium;
      }

      case "high": {
        return ui.FilterQuality.high;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToFontStyle(str: string): ui.FontStyle {
    switch (str) {
      case "normal": {
        return ui.FontStyle.normal;
      }

      case "italic": {
        return ui.FontStyle.italic;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToFontWeight(str: string): ui.FontWeight {
    switch (str) {
      case "w100": {
        return ui.FontWeight.w100;
      }

      case "w200": {
        return ui.FontWeight.w200;
      }

      case "w300": {
        return ui.FontWeight.w300;
      }

      case "w400": {
        return ui.FontWeight.w400;
      }

      case "w500": {
        return ui.FontWeight.w500;
      }

      case "w600": {
        return ui.FontWeight.w600;
      }

      case "w700": {
        return ui.FontWeight.w700;
      }

      case "w800": {
        return ui.FontWeight.w800;
      }

      case "w900": {
        return ui.FontWeight.w900;
      }

      case "normal": {
        return ui.FontWeight.normal;
      }

      case "bold": {
        return ui.FontWeight.bold;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToGradientType(str: string): GradientType {
    switch (str) {
      case "Color": {
        return GradientType.Color;
      }

      case "LinearGradient": {
        return GradientType.LinearGradient;
      }

      case "SweepGradient": {
        return GradientType.SweepGradient;
      }

      case "RadialGradient": {
        return GradientType.RadialGradient;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToIconType(str: string): IconType {
    switch (str) {
      case "SVG": {
        return IconType.SVG;
      }

      case "Font": {
        return IconType.Font;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToImageFrom(str: string): ui.ImageFrom {
    switch (str) {
      case "Network": {
        return ui.ImageFrom.Network;
      }

      case "Asset": {
        return ui.ImageFrom.Asset;
      }

      case "Project": {
        return ui.ImageFrom.Project;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToImageRepeat(str: string): ui.ImageRepeat {
    switch (str) {
      case "noRepeat": {
        return ui.ImageRepeat.noRepeat;
      }

      case "repeat": {
        return ui.ImageRepeat.repeat;
      }

      case "repeatX": {
        return ui.ImageRepeat.repeatX;
      }

      case "repeatY": {
        return ui.ImageRepeat.repeatY;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToKeyEventResult(str: string): ui.KeyEventResult {
    switch (str) {
      case "handled": {
        return ui.KeyEventResult.handled;
      }

      case "ignored": {
        return ui.KeyEventResult.ignored;
      }

      case "skipRemainingHandlers": {
        return ui.KeyEventResult.skipRemainingHandlers;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToLogicalKeyboardKey(str: string): ui.LogicalKeyboardKey {
    switch (str) {
      case "escape": {
        return ui.LogicalKeyboardKey.escape;
      }

      case "enter": {
        return ui.LogicalKeyboardKey.enter;
      }

      case "backspace": {
        return ui.LogicalKeyboardKey.backspace;
      }

      case "tab": {
        return ui.LogicalKeyboardKey.tab;
      }

      case "space": {
        return ui.LogicalKeyboardKey.space;
      }

      case "minus": {
        return ui.LogicalKeyboardKey.minus;
      }

      case "equal": {
        return ui.LogicalKeyboardKey.equal;
      }

      case "delete": {
        return ui.LogicalKeyboardKey.delete;
      }

      case "end": {
        return ui.LogicalKeyboardKey.end;
      }

      case "arrowRight": {
        return ui.LogicalKeyboardKey.arrowRight;
      }

      case "arrowLeft": {
        return ui.LogicalKeyboardKey.arrowLeft;
      }

      case "arrowDown": {
        return ui.LogicalKeyboardKey.arrowDown;
      }

      case "arrowUp": {
        return ui.LogicalKeyboardKey.arrowUp;
      }

      case "controlLeft": {
        return ui.LogicalKeyboardKey.controlLeft;
      }

      case "shiftLeft": {
        return ui.LogicalKeyboardKey.shiftLeft;
      }

      case "altLeft": {
        return ui.LogicalKeyboardKey.altLeft;
      }

      case "metaLeft": {
        return ui.LogicalKeyboardKey.metaLeft;
      }

      case "controlRight": {
        return ui.LogicalKeyboardKey.controlRight;
      }

      case "shiftRight": {
        return ui.LogicalKeyboardKey.shiftRight;
      }

      case "altRight": {
        return ui.LogicalKeyboardKey.altRight;
      }

      case "metaRight": {
        return ui.LogicalKeyboardKey.metaRight;
      }

      case "save": {
        return ui.LogicalKeyboardKey.save;
      }

      case "redo": {
        return ui.LogicalKeyboardKey.redo;
      }

      case "shift": {
        return ui.LogicalKeyboardKey.shift;
      }

      case "meta": {
        return ui.LogicalKeyboardKey.meta;
      }

      case "alt": {
        return ui.LogicalKeyboardKey.alt;
      }

      case "control": {
        return ui.LogicalKeyboardKey.control;
      }

      case "zoomIn": {
        return ui.LogicalKeyboardKey.zoomIn;
      }

      case "zoomOut": {
        return ui.LogicalKeyboardKey.zoomOut;
      }

      case "zoomToggle": {
        return ui.LogicalKeyboardKey.zoomToggle;
      }

      case "close": {
        return ui.LogicalKeyboardKey.close;
      }

      case "copy": {
        return ui.LogicalKeyboardKey.copy;
      }

      case "cut": {
        return ui.LogicalKeyboardKey.cut;
      }

      case "undo": {
        return ui.LogicalKeyboardKey.undo;
      }

      case "again": {
        return ui.LogicalKeyboardKey.again;
      }

      case "select": {
        return ui.LogicalKeyboardKey.select;
      }

      case "open": {
        return ui.LogicalKeyboardKey.open;
      }

      case "home": {
        return ui.LogicalKeyboardKey.home;
      }

      case "insert": {
        return ui.LogicalKeyboardKey.insert;
      }

      case "capsLock": {
        return ui.LogicalKeyboardKey.capsLock;
      }

      case "slash": {
        return ui.LogicalKeyboardKey.slash;
      }

      case "f1": {
        return ui.LogicalKeyboardKey.f1;
      }

      case "f2": {
        return ui.LogicalKeyboardKey.f2;
      }

      case "f3": {
        return ui.LogicalKeyboardKey.f3;
      }

      case "f4": {
        return ui.LogicalKeyboardKey.f4;
      }

      case "f5": {
        return ui.LogicalKeyboardKey.f5;
      }

      case "f6": {
        return ui.LogicalKeyboardKey.f6;
      }

      case "f7": {
        return ui.LogicalKeyboardKey.f7;
      }

      case "f8": {
        return ui.LogicalKeyboardKey.f8;
      }

      case "f9": {
        return ui.LogicalKeyboardKey.f9;
      }

      case "f10": {
        return ui.LogicalKeyboardKey.f10;
      }

      case "f11": {
        return ui.LogicalKeyboardKey.f11;
      }

      case "f12": {
        return ui.LogicalKeyboardKey.f12;
      }

      case "keyA": {
        return ui.LogicalKeyboardKey.keyA;
      }

      case "keyB": {
        return ui.LogicalKeyboardKey.keyB;
      }

      case "keyC": {
        return ui.LogicalKeyboardKey.keyC;
      }

      case "keyD": {
        return ui.LogicalKeyboardKey.keyD;
      }

      case "keyE": {
        return ui.LogicalKeyboardKey.keyE;
      }

      case "keyF": {
        return ui.LogicalKeyboardKey.keyF;
      }

      case "keyG": {
        return ui.LogicalKeyboardKey.keyG;
      }

      case "keyH": {
        return ui.LogicalKeyboardKey.keyH;
      }

      case "keyI": {
        return ui.LogicalKeyboardKey.keyI;
      }

      case "keyJ": {
        return ui.LogicalKeyboardKey.keyJ;
      }

      case "keyK": {
        return ui.LogicalKeyboardKey.keyK;
      }

      case "keyL": {
        return ui.LogicalKeyboardKey.keyL;
      }

      case "keyM": {
        return ui.LogicalKeyboardKey.keyM;
      }

      case "keyN": {
        return ui.LogicalKeyboardKey.keyN;
      }

      case "keyO": {
        return ui.LogicalKeyboardKey.keyO;
      }

      case "keyP": {
        return ui.LogicalKeyboardKey.keyP;
      }

      case "keyQ": {
        return ui.LogicalKeyboardKey.keyQ;
      }

      case "keyR": {
        return ui.LogicalKeyboardKey.keyR;
      }

      case "keyS": {
        return ui.LogicalKeyboardKey.keyS;
      }

      case "keyT": {
        return ui.LogicalKeyboardKey.keyT;
      }

      case "keyU": {
        return ui.LogicalKeyboardKey.keyU;
      }

      case "keyV": {
        return ui.LogicalKeyboardKey.keyV;
      }

      case "keyW": {
        return ui.LogicalKeyboardKey.keyW;
      }

      case "keyX": {
        return ui.LogicalKeyboardKey.keyX;
      }

      case "keyY": {
        return ui.LogicalKeyboardKey.keyY;
      }

      case "keyZ": {
        return ui.LogicalKeyboardKey.keyZ;
      }

      case "digit1": {
        return ui.LogicalKeyboardKey.digit1;
      }

      case "digit2": {
        return ui.LogicalKeyboardKey.digit2;
      }

      case "digit3": {
        return ui.LogicalKeyboardKey.digit3;
      }

      case "digit4": {
        return ui.LogicalKeyboardKey.digit4;
      }

      case "digit5": {
        return ui.LogicalKeyboardKey.digit5;
      }

      case "digit6": {
        return ui.LogicalKeyboardKey.digit6;
      }

      case "digit7": {
        return ui.LogicalKeyboardKey.digit7;
      }

      case "digit8": {
        return ui.LogicalKeyboardKey.digit8;
      }

      case "digit9": {
        return ui.LogicalKeyboardKey.digit9;
      }

      case "digit0": {
        return ui.LogicalKeyboardKey.digit0;
      }

      case "pageUp": {
        return ui.LogicalKeyboardKey.pageUp;
      }

      case "pageDown": {
        return ui.LogicalKeyboardKey.pageDown;
      }

      case "bracketLeft": {
        return ui.LogicalKeyboardKey.bracketLeft;
      }

      case "bracketRight": {
        return ui.LogicalKeyboardKey.bracketRight;
      }

      case "numpadAdd": {
        return ui.LogicalKeyboardKey.numpadAdd;
      }

      case "numpadSubtract": {
        return ui.LogicalKeyboardKey.numpadSubtract;
      }

      case "quote": {
        return ui.LogicalKeyboardKey.quote;
      }

      case "comma": {
        return ui.LogicalKeyboardKey.comma;
      }

      case "period": {
        return ui.LogicalKeyboardKey.period;
      }

      case "exclamation": {
        return ui.LogicalKeyboardKey.exclamation;
      }

      case "at": {
        return ui.LogicalKeyboardKey.at;
      }

      case "numberSign": {
        return ui.LogicalKeyboardKey.numberSign;
      }

      case "dollar": {
        return ui.LogicalKeyboardKey.dollar;
      }

      case "numpadEnter": {
        return ui.LogicalKeyboardKey.numpadEnter;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToMainAxisAlignment(str: string): ui.MainAxisAlignment {
    switch (str) {
      case "start": {
        return ui.MainAxisAlignment.start;
      }

      case "end": {
        return ui.MainAxisAlignment.end;
      }

      case "center": {
        return ui.MainAxisAlignment.center;
      }

      case "spaceBetween": {
        return ui.MainAxisAlignment.spaceBetween;
      }

      case "spaceAround": {
        return ui.MainAxisAlignment.spaceAround;
      }

      case "spaceEvenly": {
        return ui.MainAxisAlignment.spaceEvenly;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToMainAxisSize(str: string): ui.MainAxisSize {
    switch (str) {
      case "min": {
        return ui.MainAxisSize.min;
      }

      case "max": {
        return ui.MainAxisSize.max;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToPurchaseOrderStatus(str: string): PurchaseOrderStatus {
    switch (str) {
      case "Draft": {
        return PurchaseOrderStatus.Draft;
      }

      case "Submitted": {
        return PurchaseOrderStatus.Submitted;
      }

      case "Approved": {
        return PurchaseOrderStatus.Approved;
      }

      case "PartiallyReceived": {
        return PurchaseOrderStatus.PartiallyReceived;
      }

      case "Received": {
        return PurchaseOrderStatus.Received;
      }

      case "Cancelled": {
        return PurchaseOrderStatus.Cancelled;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToResultStatus(str: string): ResultStatus {
    switch (str) {
      case "Success": {
        return ResultStatus.Success;
      }

      case "Errors": {
        return ResultStatus.Errors;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToTextAlign(str: string): ui.TextAlign {
    switch (str) {
      case "left": {
        return ui.TextAlign.left;
      }

      case "right": {
        return ui.TextAlign.right;
      }

      case "center": {
        return ui.TextAlign.center;
      }

      case "justify": {
        return ui.TextAlign.justify;
      }

      case "start": {
        return ui.TextAlign.start;
      }

      case "end": {
        return ui.TextAlign.end;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToTextBaseline(str: string): ui.TextBaseline {
    switch (str) {
      case "alphabetic": {
        return ui.TextBaseline.alphabetic;
      }

      case "ideographic": {
        return ui.TextBaseline.ideographic;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToTextCapitalization(str: string): ui.TextCapitalization {
    switch (str) {
      case "words": {
        return ui.TextCapitalization.words;
      }

      case "sentences": {
        return ui.TextCapitalization.sentences;
      }

      case "characters": {
        return ui.TextCapitalization.characters;
      }

      case "none": {
        return ui.TextCapitalization.none;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToTextDecoration(str: string): ui.TextDecoration {
    switch (str) {
      case "none": {
        return ui.TextDecoration.none;
      }

      case "underline": {
        return ui.TextDecoration.underline;
      }

      case "overline": {
        return ui.TextDecoration.overline;
      }

      case "lineThrough": {
        return ui.TextDecoration.lineThrough;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToTextDecorationStyle(
    str: string
  ): ui.TextDecorationStyle {
    switch (str) {
      case "solid": {
        return ui.TextDecorationStyle.solid;
      }

      case "double": {
        return ui.TextDecorationStyle.double;
      }

      case "dotted": {
        return ui.TextDecorationStyle.dotted;
      }

      case "dashed": {
        return ui.TextDecorationStyle.dashed;
      }

      case "wavy": {
        return ui.TextDecorationStyle.wavy;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToTextDirection(str: string): ui.TextDirection {
    switch (str) {
      case "rtl": {
        return ui.TextDirection.rtl;
      }

      case "ltr": {
        return ui.TextDirection.ltr;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToTextInputType(str: string): ui.TextInputType {
    switch (str) {
      case "text": {
        return ui.TextInputType.text;
      }

      case "multiline": {
        return ui.TextInputType.multiline;
      }

      case "number": {
        return ui.TextInputType.number;
      }

      case "phone": {
        return ui.TextInputType.phone;
      }

      case "datetime": {
        return ui.TextInputType.datetime;
      }

      case "emailAddress": {
        return ui.TextInputType.emailAddress;
      }

      case "url": {
        return ui.TextInputType.url;
      }

      case "visiblePassword": {
        return ui.TextInputType.visiblePassword;
      }

      case "name": {
        return ui.TextInputType.name;
      }

      case "streetAddress": {
        return ui.TextInputType.streetAddress;
      }

      case "none": {
        return ui.TextInputType.none;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToTextOverflow(str: string): ui.TextOverflow {
    switch (str) {
      case "clip": {
        return ui.TextOverflow.clip;
      }

      case "fade": {
        return ui.TextOverflow.fade;
      }

      case "ellipsis": {
        return ui.TextOverflow.ellipsis;
      }

      case "visible": {
        return ui.TextOverflow.visible;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToTileMode(str: string): ui.TileMode {
    switch (str) {
      case "clamp": {
        return ui.TileMode.clamp;
      }

      case "repeated": {
        return ui.TileMode.repeated;
      }

      case "mirror": {
        return ui.TileMode.mirror;
      }

      case "decal": {
        return ui.TileMode.decal;
      }
      default: {
        return null;
      }
    }
  }
  public static stringToVerticalDirection(str: string): ui.VerticalDirection {
    switch (str) {
      case "up": {
        return ui.VerticalDirection.up;
      }

      case "down": {
        return ui.VerticalDirection.down;
      }
      default: {
        return null;
      }
    }
  }
}
