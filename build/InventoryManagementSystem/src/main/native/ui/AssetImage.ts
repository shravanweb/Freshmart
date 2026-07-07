import React from "react";
import { BoxFit } from "../classes/BoxFit";
import { Color } from "../classes/Color";
import { ImageView } from "../index";
import { ImageFrom } from "../classes/ImageFrom";
import BaseUIProps, { copyBaseUIProps } from "./BaseUIProps";
import * as ui from "..";
interface AssetImageProps extends BaseUIProps {
  path: string;
  fit?: BoxFit;
  color?: Color;
  width?: number;
  height?: number;
  topLeftRadius?: number;
  topRightRadius?: number;
  bottomLeftRadius?: number;
  bottomRightRadius?: number;
  alignment?: ui.Alignment;
  colorBlendMode?: ui.BlendMode;
  filterQuality?: ui.FilterQuality;
}

class _AssetImage extends React.Component<AssetImageProps, {}> {
  static defaultProps = {
    fit: BoxFit.contain,
    with: 0,
    height: 0,
  };
  render() {
    return ImageView({
      imageUrl: this.props.path,
      imageType: ImageFrom.Asset,
      fit: this.props.fit,
      color: this.props.color,
      topLeftRadius: this.props.topLeftRadius,
      topRightRadius: this.props.topRightRadius,
      bottomLeftRadius: this.props.bottomLeftRadius,
      bottomRightRadius: this.props.bottomRightRadius,
      alignment: this.props.alignment,
      colorBlendMode: this.props.colorBlendMode,
      filterQuality: this.props.filterQuality,
      className: this.props.className,
      ...copyBaseUIProps(this.props),
    });
  }
}

export default function AssetImage(props: AssetImageProps) {
  return React.createElement(_AssetImage, {
    ..._AssetImage.defaultProps,
    ...props,
  });
}
