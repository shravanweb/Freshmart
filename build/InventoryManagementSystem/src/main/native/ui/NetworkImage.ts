import React from "react";
import { BoxFit } from "../classes/BoxFit";
import { Color } from "../classes/Color";
import { ImageFrom } from "../classes/ImageFrom";
import BaseUIProps, { copyBaseUIProps } from "./BaseUIProps";
import ImageView from "./ImageView";
import * as ui from "..";
interface NetworkImageProps extends BaseUIProps {
  url: string;
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

class _NetworkImage extends React.Component<NetworkImageProps, {}> {
  static defaultProps = {
    fit: BoxFit.contain,
    with: 0,
    height: 0,
  };
  render() {
    return ImageView({
      imageUrl: this.props.url,
      imageType: ImageFrom.Network,
      fit: this.props.fit,
      color: this.props.color,
      topLeftRadius: this.props.topLeftRadius,
      topRightRadius: this.props.topRightRadius,
      bottomLeftRadius: this.props.bottomLeftRadius,
      bottomRightRadius: this.props.bottomRightRadius,
      className: this.props.className,
      alignment: this.props.alignment,
      colorBlendMode: this.props.colorBlendMode,
      filterQuality: this.props.filterQuality,
      ...copyBaseUIProps(this.props),
    });
  }
}

export default function NetworkImage(props: NetworkImageProps) {
  return React.createElement(_NetworkImage, {
    ..._NetworkImage.defaultProps,
    ...props,
  });
}
