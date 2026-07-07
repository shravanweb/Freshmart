import { Canvas } from "./Canvas";
import { Offset } from "./Offset";
import Paragraph from "./Paragraph";
import { ParagraphBuilder } from "./ParagraphBuilder";
import { ParagraphConstraints } from "./ParagraphConstraints";
import ParagraphStyle from "./ParagraphStyle";
import StrutStyle from "./StrutStyle";
import { TextAlign } from "./TextAlign";
import { TextDirection } from "./TextDirection";
import { TextHeightBehavior } from "./TextHeightBehavior";
import { TextSpan } from "./TextSpan";
import Locale from "./Locale";
import { TextWidthBasis } from "./TextWidthBasis";
import MathExt from "../../classes/MathExt";
import TextStyle from "./TextStyle";
import { PainterTextStyle } from "./PainterTextStyle";

export class TextPainter {
  static _kDefaultFontSize = 14.0;
  text: TextSpan;
  textDirection: TextDirection;
  textAlign: TextAlign;
  textScaleFactor: number;
  textWidthBasis: TextWidthBasis;

  _paragraph: Paragraph = null;
  _lastMinWidth: number = null;
  _lastMaxWidth: number = null;
  _maxLines: number = 0;
  _textHeightBehavior: TextHeightBehavior;
  _ellipsis: string;
  _locale: Locale;
  _strutStyle: StrutStyle;
  _rebuildParagraphForPaint: boolean;

  constructor(params: Partial<TextPainter>) {
    Object.assign(this, params);
  }

  layout(canvas: Canvas, props: { minWidth: number; maxWidth: number }) {
    if (
      this._paragraph != null &&
      props.minWidth == this._lastMinWidth &&
      props.maxWidth == this._lastMaxWidth
    ) {
      return;
    }

    if (this._rebuildParagraphForPaint || this._paragraph == null) {
      this._createParagraph();
    }
    this._lastMinWidth = props.minWidth;
    this._lastMaxWidth = props.maxWidth;
    // A change in layout invalidates the cached caret and line metrics as well.
    // this._lineMetricsCache = null;
    // this._previousCaretPosition = null;
    // this._previousCaretPrototype = null;
    this._layoutParagraph(canvas, props.minWidth, props.maxWidth);
    // this._inlinePlaceholderBoxes = this._paragraph!.getBoxesForPlaceholders();
  }

  get width(): number {
    return this._applyFloatingPointHack(
      this.textWidthBasis == TextWidthBasis.longestLine
        ? this._paragraph!.longestLine
        : this._paragraph!.width
    );
  }

  get height(): number {
    return this._applyFloatingPointHack(this._paragraph!.height);
  }

  get maxIntrinsicWidth(): number {
    return this._applyFloatingPointHack(this._paragraph!.maxIntrinsicWidth);
  }

  paint(canvas: Canvas, offset: Offset) {
    let minWidth = this._lastMinWidth;
    let maxWidth = this._lastMaxWidth;
    if (this._paragraph == null || minWidth == null || maxWidth == null) {
      // throw StateError(
      //   'TextPainter.paint called when text geometry was not yet calculated.\n'
      //   'Please call layout() before paint() to position the text before painting it.',
      // );
    }

    if (this._paragraph == null) {
      this._createParagraph();
      // Unfortunately we have to redo the layout using the same constraints,
      // since we've created a new ui.Paragraph. But there's no extra work being
      // done: if _needsPaint is true and _paragraph is not null, the previous
      // `layout` call didn't invoke _layoutParagraph.
      this._layoutParagraph(canvas, minWidth, maxWidth);
    }

    canvas.drawParagraph(this._paragraph!, offset);
  }

  _createParagraph() {
    let builder = new ParagraphBuilder(this._createParagraphStyle());
    // this.text.build(builder, textScaleFactor: this.textScaleFactor, dimensions: _placeholderDimensions);
    // _inlinePlaceholderScales = builder.placeholderScales;
    this._paragraph = builder.build();
    this._paragraph.text = this.text.text;
    this._rebuildParagraphForPaint = false;
    this._paragraph.paintStyle = new PainterTextStyle({
      color: this.text.style.color,
      fontFamily: this.text.style.fontFamily,
      fontSize: this.text.style.fontSize,
    });
  }

  _createParagraphStyle(defaultTextDirection?: TextDirection) {
    return (
      this.text!.style?.getParagraphStyle({
        textAlign: this.textAlign,
        textDirection: this.textDirection ?? defaultTextDirection,
        textScaleFactor: this.textScaleFactor,
        maxLines: this._maxLines,
        textHeightBehavior: this._textHeightBehavior,
        ellipsis: this._ellipsis,
        locale: this._locale,
        strutStyle: this._strutStyle,
      }) ??
      new ParagraphStyle({
        textAlign: this.textAlign,
        textDirection: this.textDirection ?? defaultTextDirection,
        // Use the default font size to multiply by as RichText does not
        // perform inheriting [TextStyle]s and would otherwise
        // fail to apply textScaleFactor.
        fontSize: TextPainter._kDefaultFontSize * this.textScaleFactor,
        maxLines: this._maxLines,
        textHeightBehavior: this._textHeightBehavior,
        ellipsis: this._ellipsis,
        locale: this._locale,
      })
    );
  }

  _layoutParagraph(canvas: Canvas, minWidth: number, maxWidth: number) {
    this._paragraph!.layout(
      canvas,
      new ParagraphConstraints({ width: maxWidth })
    );
    if (minWidth != maxWidth) {
      let newWidth = 0;
      switch (this.textWidthBasis) {
        case TextWidthBasis.longestLine:
          // The parent widget expects the paragraph to be exactly
          // `TextPainter.width` wide, if that value satisfies the constraints
          // it gave to the TextPainter. So when `textWidthBasis` is longestLine,
          // the paragraph's width needs to be as close to the width of its
          // longest line as possible.
          newWidth = this._applyFloatingPointHack(this._paragraph!.longestLine);
          break;
        case TextWidthBasis.parent:
          newWidth = this.maxIntrinsicWidth;
          break;
      }
      newWidth = MathExt.clampDouble(newWidth, minWidth, maxWidth);
      if (newWidth != this._applyFloatingPointHack(this._paragraph!.width)) {
        this._paragraph!.layout(
          canvas,
          new ParagraphConstraints({ width: newWidth })
        );
      }
    }
  }

  _applyFloatingPointHack(layoutValue: number) {
    return layoutValue.ceilToDouble();
  }
}
