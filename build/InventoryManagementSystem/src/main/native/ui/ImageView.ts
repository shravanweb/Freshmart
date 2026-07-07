import React from "react";
import { BlendMode } from "../classes/BlendMode";
import { BoxFit } from "../classes/BoxFit";
import { Color } from "../classes/Color";
import { FilterQuality } from "../classes/FilterQuality";
import { ImageFrom } from "../classes/ImageFrom";
import { ImageRepeat } from "../classes/ImageRepeat";
import { BaseComponentUtil } from "./BaseComponent";
import BaseUIProps, { processBaseUIProps } from "./BaseUIProps";
import * as ui from "..";

interface ImageViewProps extends BaseUIProps {
  imageType: ImageFrom;
  imageUrl: string;
  placeHolderUrl?: string;

  color?: Color;
  colorBlendMode?: BlendMode;

  fit?: BoxFit;
  repeat?: ImageRepeat;

  filterQuality?: FilterQuality;

  topLeftRadius?: number;
  topRightRadius?: number;
  bottomLeftRadius?: number;
  bottomRightRadius?: number;

  alignment?: ui.Alignment;
}

interface ImageViewState {
  loaded: boolean;
  failed: boolean;
}

class _ImageView extends React.Component<ImageViewProps, ImageViewState> {
  static defaultProps = {
    repeat: ImageRepeat.noRepeat,
    filterQuality: FilterQuality.low,
    fit: BoxFit.cover,
  };

  constructor(props: ImageViewProps) {
    super(props);

    this.state = {
      loaded: false,
      failed: false,
    };
  }

  private getImageUrl(): string {
    // You can expand this based on ImageFrom type
    switch (this.props.imageType) {
      case ImageFrom.Network:
        return this.props.imageUrl;

      case ImageFrom.Asset:
        return this.props.imageUrl;

      default:
        return this.props.imageUrl;
    }
  }

  private getObjectFit(): string | undefined {
    switch (this.props.fit) {
      case BoxFit.fill:
        return "fill";

      case BoxFit.contain:
        return "contain";

      case BoxFit.cover:
        return "cover";

      case BoxFit.none:
        return "none";

      case BoxFit.scaleDown:
        return "scale-down";

      case BoxFit.fitWidth:
        return "contain";

      case BoxFit.fitHeight:
        return "contain";

      default:
        return "cover";
    }
  }

  private getObjectPosition(): string {
    switch (this.props.alignment) {
      case ui.Alignment.topLeft:
        return "left top";

      case ui.Alignment.topCenter:
        return "center top";

      case ui.Alignment.topRight:
        return "right top";

      case ui.Alignment.centerLeft:
        return "left center";

      case ui.Alignment.center:
        return "center center";

      case ui.Alignment.centerRight:
        return "right center";

      case ui.Alignment.bottomLeft:
        return "left bottom";

      case ui.Alignment.bottomCenter:
        return "center bottom";

      case ui.Alignment.bottomRight:
        return "right bottom";

      default:
        return "center center";
    }
  }

  private getBackgroundRepeat(): string {
    switch (this.props.repeat) {
      case ImageRepeat.repeat:
        return "repeat";

      case ImageRepeat.repeatX:
        return "repeat-x";

      case ImageRepeat.repeatY:
        return "repeat-y";

      case ImageRepeat.noRepeat:
      default:
        return "no-repeat";
    }
  }

  private getImageRendering(): string | undefined {
    switch (this.props.filterQuality) {
      case FilterQuality.none:
        return "pixelated";

      case FilterQuality.low:
        return "auto";

      case FilterQuality.medium:
        return "smooth";

      case FilterQuality.high:
        return "high-quality";

      default:
        return "auto";
    }
  }

  private getBlendMode(): string | undefined {
    switch (this.props.colorBlendMode) {
      case BlendMode.multiply:
        return "multiply";

      case BlendMode.screen:
        return "screen";

      case BlendMode.overlay:
        return "overlay";

      case BlendMode.darken:
        return "darken";

      case BlendMode.lighten:
        return "lighten";

      default:
        return undefined;
    }
  }

  private getBorderRadiusStyles() {
    return {
      borderTopLeftRadius: this.props.topLeftRadius,
      borderTopRightRadius: this.props.topRightRadius,
      borderBottomLeftRadius: this.props.bottomLeftRadius,
      borderBottomRightRadius: this.props.bottomRightRadius,
    };
  }

  private shouldUseBackgroundMode(): boolean {
    return (
      this.props.repeat !== ImageRepeat.noRepeat ||
      this.props.color != null ||
      this.props.colorBlendMode != null
    );
  }

  private populateImageStyles(style: any) {
    BaseComponentUtil.applyBaseComponentProps(style, this.props);

    Object.assign(style, this.getBorderRadiusStyles());

    style.objectFit = this.getObjectFit();
    style.objectPosition = this.getObjectPosition();
    style.imageRendering = this.getImageRendering();

    if (this.props.fit === BoxFit.fitWidth) {
      style.width = "100%";
      style.height = "auto";
    }

    if (this.props.fit === BoxFit.fitHeight) {
      style.height = "100%";
      style.width = "auto";
    }
  }

  private populateBackgroundStyles(style: any) {
    BaseComponentUtil.applyBaseComponentProps(style, this.props);

    Object.assign(style, this.getBorderRadiusStyles());

    style.backgroundImage = `url(${this.getImageUrl()})`;
    style.backgroundRepeat = this.getBackgroundRepeat();
    style.backgroundPosition = this.getObjectPosition();
    style.backgroundSize = this.getObjectFit();

    style.overflow = "hidden";

    if (this.props.color) {
      style.backgroundColor =
        typeof this.props.color === "string"
          ? this.props.color
          : this.props.color.toString();

      style.backgroundBlendMode = this.getBlendMode();
    }
  }

  renderImageMode() {
    let style: any = {};

    this.populateImageStyles(style);

    const src =
      !this.state.loaded && this.props.placeHolderUrl
        ? this.props.placeHolderUrl
        : this.getImageUrl();

    return React.createElement("img", {
      src,
      class:
        (this.props.className ? this.props.className + " " : "") + "ImageView",

      style,

      onLoad: () => {
        if (!this.state.loaded) {
          this.setState({
            loaded: true,
          });
        }
      },

      onError: () => {
        this.setState({
          failed: true,
        });
      },

      ...processBaseUIProps(this.props),
    });
  }

  renderBackgroundMode() {
    let style: any = {};

    this.populateBackgroundStyles(style);

    return React.createElement(
      "div",
      {
        className:
          (this.props.className ? this.props.className + " " : "") +
          "ImageView",

        style,

        ...processBaseUIProps(this.props),
      },
      null
    );
  }

  render() {
    if (this.shouldUseBackgroundMode()) {
      return this.renderBackgroundMode();
    }

    return this.renderImageMode();
  }
}

export default function ImageView(props: ImageViewProps) {
  return React.createElement(_ImageView, {
    ..._ImageView.defaultProps,
    ...props,
  });
}
