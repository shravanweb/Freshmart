import React from "react";
import { Alignment } from "../classes/Alignment";
import { BlendMode } from "../classes/BlendMode";
import { BoxFit } from "../classes/BoxFit";
import { Clip } from "../classes/Clip";
import { Color } from "../classes/Color";
import { ImageFrom } from "../classes/ImageFrom";
import { BaseComponentUtil } from "../ui/BaseComponent";
import BaseUIProps, { processBaseUIProps } from "../ui/BaseUIProps";

interface SVGViewProps extends BaseUIProps {
  svgType: ImageFrom;
  svgData?: string;
  assetPath?: string;
  networkURL?: string;
  projectPath?: string;
  placeHolderUrl?: string;
  color?: Color;
  colorBlendMode?: BlendMode;
  fit?: BoxFit;
  alignment?: Alignment;
  width?: number;
  height?: number;
  allowDrawingOutsideViewBox?: boolean;
  matchTextDirection?: boolean;
  clipBehavior?: Clip;
}

interface ShimmerProps {
  width?: number;
  height?: number;
}

const LinearShimmer: React.FC<ShimmerProps> = ({ width, height }) => {
  const [animationValue, setAnimationValue] = React.useState(0);

  React.useEffect(() => {
    let animationFrameId: number;
    let startTime: number | null = null;
    const duration = 2000;

    const animate = (timestamp: number) => {
      if (!startTime) startTime = timestamp;
      const elapsed = timestamp - startTime;
      const progress = (elapsed % duration) / duration;

      setAnimationValue(progress);
      animationFrameId = requestAnimationFrame(animate);
    };

    animationFrameId = requestAnimationFrame(animate);

    return () => {
      if (animationFrameId) {
        cancelAnimationFrame(animationFrameId);
      }
    };
  }, []);

  const containerStyle: React.CSSProperties = {
    position: "relative",
    width: width ? `${width}px` : "100%",
    height: height ? `${height}px` : "100%",
    backgroundColor: "#F5F5F5",
    overflow: "hidden",
  };

  const gradientStops = [
    Math.max(0, animationValue - 0.5),
    animationValue,
    Math.min(1, animationValue + 0.5),
  ];

  const shimmerStyle: React.CSSProperties = {
    position: "absolute",
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    background: `linear-gradient(135deg, 
      #E0E0E0 ${gradientStops[0] * 100}%, 
      #F5F5F5 ${gradientStops[1] * 100}%, 
      #E0E0E0 ${gradientStops[2] * 100}%)`,
    backgroundSize: "200% 200%",
  };

  return React.createElement(
    "div",
    { style: containerStyle },
    React.createElement("div", { style: shimmerStyle })
  );
};

class _SVGView extends React.Component<
  SVGViewProps,
  { svgContent: string | null; isLoading: boolean; error: boolean }
> {
  static defaultProps = {
    alignment: Alignment.center,
    fit: BoxFit.contain,
    clipBehavior: Clip.hardEdge,
    allowDrawingOutsideViewBox: false,
    matchTextDirection: false,
    colorBlendMode: BlendMode.srcIn,
  };

  constructor(props: SVGViewProps) {
    super(props);
    this.state = {
      svgContent: null,
      isLoading: false,
      error: false,
    };
  }

  componentDidMount() {
    this.loadSVG();
  }

  componentDidUpdate(prevProps: SVGViewProps) {
    if (
      prevProps.svgType !== this.props.svgType ||
      prevProps.networkURL !== this.props.networkURL ||
      prevProps.assetPath !== this.props.assetPath ||
      prevProps.projectPath !== this.props.projectPath ||
      prevProps.svgData !== this.props.svgData
    ) {
      this.loadSVG();
    }
  }

  async loadSVG() {
    const {
      svgType,
      networkURL,
      assetPath,
      projectPath,
      svgData,
      placeHolderUrl,
    } = this.props;

    let url: string | null = null;

    if (svgType === ImageFrom.Asset) {
      url = assetPath || placeHolderUrl || "images/placeholder.svg";
    } else if (svgType === ImageFrom.Network) {
      url = networkURL || null;
      if (!url || url.trim() === "") {
        this.setState({ svgContent: null, isLoading: false, error: true });
        return;
      }
    } else if (svgType === ImageFrom.Project) {
      url = projectPath || placeHolderUrl || "images/placeholder.svg";
    } else if (svgData && svgData.trim()) {
      this.setState({ svgContent: svgData, isLoading: false, error: false });
      return;
    } else {
      this.setState({ svgContent: null, isLoading: false, error: true });
      return;
    }

    if (!url) {
      this.setState({ svgContent: null, isLoading: false, error: true });
      return;
    }

    this.setState({ isLoading: true, error: false });
    try {
      const response = await fetch(url);
      if (!response.ok) {
        throw new Error(`Failed to load SVG: ${response.status}`);
      }

      const content = await response.text();

      if (!content.includes("<svg")) {
        throw new Error("Invalid SVG content");
      }

      this.setState({ svgContent: content, isLoading: false, error: false });
    } catch (error) {
      console.error("Error loading SVG:", error);
      this.setState({ isLoading: false, error: true });
    }
  }

  getAlignmentStyle(): React.CSSProperties {
    const alignment = this.props.alignment || Alignment.center;
    const alignmentMap: { [key: string]: React.CSSProperties } = {
      [Alignment.topLeft]: {
        justifyContent: "flex-start",
        alignItems: "flex-start",
      },
      [Alignment.topCenter]: {
        justifyContent: "flex-start",
        alignItems: "center",
      },
      [Alignment.topRight]: {
        justifyContent: "flex-start",
        alignItems: "flex-end",
      },
      [Alignment.centerLeft]: {
        justifyContent: "center",
        alignItems: "flex-start",
      },
      [Alignment.center]: { justifyContent: "center", alignItems: "center" },
      [Alignment.centerRight]: {
        justifyContent: "center",
        alignItems: "flex-end",
      },
      [Alignment.bottomLeft]: {
        justifyContent: "flex-end",
        alignItems: "flex-start",
      },
      [Alignment.bottomCenter]: {
        justifyContent: "flex-end",
        alignItems: "center",
      },
      [Alignment.bottomRight]: {
        justifyContent: "flex-end",
        alignItems: "flex-end",
      },
    };
    return alignmentMap[alignment] || alignmentMap[Alignment.center];
  }

  getBoxFitStyle(): React.CSSProperties {
    const style: React.CSSProperties = {};

    switch (this.props.fit) {
      case BoxFit.fill:
        style.width = "100%";
        style.height = "100%";
        break;
      case BoxFit.contain:
        style.maxWidth = "100%";
        style.maxHeight = "100%";
        style.width = "auto";
        style.height = "auto";
        break;
      case BoxFit.cover:
        style.minWidth = "100%";
        style.minHeight = "100%";
        style.width = "100%";
        style.height = "100%";
        break;
      case BoxFit.none:
        break;
      case BoxFit.scaleDown:
        style.maxWidth = "100%";
        style.maxHeight = "100%";
        break;
      case BoxFit.fitWidth:
        style.width = "100%";
        style.height = "auto";
        break;
      case BoxFit.fitHeight:
        style.width = "auto";
        style.height = "100%";
        break;
      default:
        style.maxWidth = "100%";
        style.maxHeight = "100%";
    }

    return style;
  }

  applySVGColorAndBlendMode(svgContent: string): string {
    const { color, colorBlendMode } = this.props;

    if (!color) {
      return svgContent;
    }

    const blendMode = colorBlendMode || BlendMode.srcIn;

    if (blendMode === BlendMode.srcIn) {
      try {
        const parser = new DOMParser();
        const svgDoc = parser.parseFromString(svgContent, "image/svg+xml");
        const svgElement = svgDoc.querySelector("svg");

        if (svgElement && !svgDoc.querySelector("parsererror")) {
          const colorHex = color.toHexa();

          const elements = svgElement.querySelectorAll(
            "path, circle, rect, ellipse, polygon, polyline, line, text"
          );

          elements.forEach((elem) => {
            const currentFill = elem.getAttribute("fill");
            if (
              !currentFill ||
              (currentFill !== "none" && currentFill !== "transparent")
            ) {
              elem.setAttribute("fill", colorHex);
            }
          });

          return new XMLSerializer().serializeToString(svgElement);
        }
      } catch (err) {
        console.error("Error applying color to SVG:", err);
      }
    }

    return svgContent;
  }

  renderPlaceholder() {
    const { placeHolderUrl, width, height } = this.props;

    if (placeHolderUrl) {
      const style: React.CSSProperties = {
        display: "inline-flex",
        ...this.getAlignmentStyle(),
      };

      if (width) style.width = `${width}px`;
      if (height) style.height = `${height}px`;

      BaseComponentUtil.applyBaseComponentProps(style, this.props);

      const imgStyle: React.CSSProperties = this.getBoxFitStyle();

      switch (this.props.fit) {
        case BoxFit.fill:
          imgStyle.objectFit = "fill";
          break;
        case BoxFit.contain:
          imgStyle.objectFit = "contain";
          break;
        case BoxFit.cover:
          imgStyle.objectFit = "cover";
          break;
        case BoxFit.none:
          imgStyle.objectFit = "none";
          break;
        case BoxFit.scaleDown:
          imgStyle.objectFit = "scale-down";
          break;
        default:
          imgStyle.objectFit = "contain";
      }

      return React.createElement("img", {
        src: placeHolderUrl,
        alt: "Placeholder",
        style: { ...style, ...imgStyle },
        ...processBaseUIProps(this.props),
      });
    }

    return React.createElement(LinearShimmer, { width, height });
  }

  render() {
    const { svgContent, isLoading, error } = this.state;
    const {
      width,
      height,
      allowDrawingOutsideViewBox,
      matchTextDirection,
      clipBehavior,
    } = this.props;

    if (isLoading || error || !svgContent) {
      return this.renderPlaceholder();
    }

    const style: React.CSSProperties = {
      display: "inline-flex",
      ...this.getAlignmentStyle(),
      overflow: allowDrawingOutsideViewBox ? "visible" : "hidden",
    };

    if (width) style.width = `${width}px`;
    if (height) style.height = `${height}px`;

    BaseComponentUtil.applyBaseComponentProps(style, this.props);

    const clipBehaviorValue = clipBehavior || Clip.hardEdge;
    if (
      clipBehaviorValue === Clip.hardEdge ||
      clipBehaviorValue === Clip.antiAlias
    ) {
      style.overflow = "hidden";
    }

    if (matchTextDirection) {
      style.direction = "inherit";
    }

    const processedSvg = this.applySVGColorAndBlendMode(svgContent);

    const svgWrapperStyle: React.CSSProperties = {
      ...this.getBoxFitStyle(),
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
    };

    return React.createElement(
      "div",
      {
        className: this.props.className,
        style: style,
        ...processBaseUIProps(this.props),
      },
      React.createElement("div", {
        style: svgWrapperStyle,
        dangerouslySetInnerHTML: { __html: processedSvg },
      })
    );
  }
}

export default function SVGView(props: SVGViewProps) {
  return React.createElement(_SVGView, {
    ..._SVGView.defaultProps,
    ...props,
  });
}
