import { FontWeight } from "./FontWeight";
import { TextBaseline } from "./TextBaseline";
import { TextDecoration } from "./TextDecoration";
import { TextDecorationStyle } from "./TextDecorationStyle";
import { Color } from "./Color";
import { FontStyle } from "./FontStyle";
import Locale from "./Locale";
import { Shadow } from "./Shadow";
import { TextOverflow } from "./TextOverflow";
import { TextAlign } from "./TextAlign";
import { TextDirection } from "./TextDirection";
import StrutStyle from "./StrutStyle";
import ParagraphStyle from "./ParagraphStyle";
import { TextHeightBehavior } from "./TextHeightBehavior";
import { TextLeadingDistribution } from "./TextLeadingDistribution";

export default class TextStyle {
  static _kDefaultFontSize = 14.0;

  inherit?: boolean;
  color?: Color;
  backgroundColor?: Color;
  background?: Color;
  foreground?: Color;
  fontSize?: number;
  fontWeight?: FontWeight;
  fontStyle?: FontStyle;
  letterSpacing?: number;
  wordSpacing?: number;
  textBaseline?: TextBaseline;
  height?: number;
  locale?: Locale;
  shadows?: Array<Shadow>;
  decoration?: TextDecoration;
  decorationColor?: Color;
  decorationStyle?: TextDecorationStyle;
  decorationThickness?: number;
  debugLabel?: string;
  fontFamily?: string;
  fontFamilyFallback?: Array<string>;
  leadingDistribution?: TextLeadingDistribution;
  // fontFeatures?: FontFeature
  packageValue?: string;
  overflow?: TextOverflow;

  constructor(props: Partial<TextStyle>) {
    Object.assign(this, props);
  }

  copyWith(props: Partial<TextStyle>): TextStyle {
    return new TextStyle({
      inherit: props.inherit ?? this.inherit,
      color: props.color ?? this.color,
      backgroundColor:
        this.background == null && props.background == null
          ? props.backgroundColor ?? this.backgroundColor
          : null,
      fontSize: props.fontSize ?? this.fontSize,
      fontWeight: props.fontWeight ?? this.fontWeight,
      fontStyle: props.fontStyle ?? this.fontStyle,
      letterSpacing: props.letterSpacing ?? this.letterSpacing,
      wordSpacing: props.wordSpacing ?? this.wordSpacing,
      textBaseline: props.textBaseline ?? this.textBaseline,
      height: props.height ?? this.height,
      // leadingDistribution: props.leadingDistribution ?? this.leadingDistribution,
      locale: props.locale ?? this.locale,
      foreground: props.foreground ?? this.foreground,
      background: props.background ?? this.background,
      shadows: props.shadows ?? this.shadows,
      // fontFeatures: props.fontFeatures ?? this.fontFeatures,
      decoration: props.decoration ?? this.decoration,
      decorationColor: props.decorationColor ?? this.decorationColor,
      decorationStyle: props.decorationStyle ?? this.decorationStyle,
      decorationThickness:
        props.decorationThickness ?? this.decorationThickness,
      debugLabel: props.debugLabel,
      fontFamily: props.fontFamily ?? this.fontFamily,
      fontFamilyFallback: props.fontFamilyFallback ?? this.fontFamilyFallback,
      packageValue: props.packageValue ?? this.packageValue,
      overflow: props.overflow ?? this.overflow,
    });
  }

  getParagraphStyle(props: {
    textAlign?: TextAlign;
    textDirection?: TextDirection;
    textScaleFactor?: number;
    ellipsis?: string;
    maxLines?: number;
    textHeightBehavior?: TextHeightBehavior;
    locale?: Locale;
    fontFamily?: string;
    fontSize?: number;
    fontWeight?: FontWeight;
    fontStyle?: FontStyle;
    height?: number;
    strutStyle?: StrutStyle;
  }): ParagraphStyle {
    if (!props.textScaleFactor) {
      props.textScaleFactor = 1.0;
    }
    let leadingDistribution = this.leadingDistribution;
    let effectiveTextHeightBehavior =
      props.textHeightBehavior ??
      (leadingDistribution == null
        ? null
        : new TextHeightBehavior({ leadingDistribution: leadingDistribution }));
    return new ParagraphStyle({
      textAlign: props.textAlign,
      textDirection: props.textDirection,
      // Here, we establish the contents of this TextStyle as the paragraph's default font
      // unless an override is passed in.
      fontWeight: props.fontWeight ?? this.fontWeight,
      fontStyle: props.fontStyle ?? this.fontStyle,
      fontFamily: props.fontFamily ?? this.fontFamily,
      fontSize:
        (props.fontSize ?? this.fontSize ?? TextStyle._kDefaultFontSize) *
        props.textScaleFactor,
      height: props.height ?? this.height,
      textHeightBehavior: effectiveTextHeightBehavior,
      strutStyle:
        props.strutStyle == null
          ? null
          : new StrutStyle({
              fontFamily: props.strutStyle.fontFamily,
              fontFamilyFallback: props.strutStyle.fontFamilyFallback,
              fontSize:
                props.strutStyle.fontSize == null
                  ? null
                  : props.strutStyle.fontSize! * props.textScaleFactor,
              height: props.strutStyle.height,
              leading: props.strutStyle.leading,
              fontWeight: props.strutStyle.fontWeight,
              fontStyle: props.strutStyle.fontStyle,
              forceStrutHeight: props.strutStyle.forceStrutHeight,
            }),
      maxLines: props.maxLines,
      ellipsis: props.ellipsis,
      locale: props.locale,
    });
  }
  merge(other: TextStyle): TextStyle {
    if (other == null) {
      return this;
    }
    return this.copyWith(other);
  }

  textDecoration(): string {
    switch (this.decoration) {
      case TextDecoration.overline:
        return "overline";
      case TextDecoration.underline:
        return "underline";
      case TextDecoration.lineThrough:
        return "line-through";
    }
    return "none";
  }

  toCss() {
    let style: any = {};
    if (this.color) {
      style.color = this.color.toHexa();
    }
    if (this.fontWeight) {
      style.fontWeight = FontWeight[this.fontWeight];
    }
    if (this.backgroundColor) {
      style.background = this.backgroundColor.toHexa();
    }
    if (this.fontFamily) {
      style.fontFamily = this.fontFamily;
    }
    if (this.fontSize) {
      style.fontSize = this.fontSize;
    }
    if (this.fontStyle) {
      style.fontStyle = FontStyle[this.fontStyle];
    }
    if (this.letterSpacing) {
      style.letterSpacing = this.letterSpacing + "px";
    }
    if (this.wordSpacing) {
      style.wordSpacing = this.wordSpacing + "px";
    }
    if (this.decoration) {
      style.textDecorationLine = this.textDecoration();
    }
    if (this.decorationColor) {
      style.textDecorationColor = this.decorationColor.toHexa();
    }
    if (this.decorationStyle) {
      style.textDecorationStyle = TextDecorationStyle[this.decorationStyle];
    }
    if (this.decorationThickness) {
      style.textDecorationThickness = this.decorationThickness + "px";
    }
    return style;
  }
}
