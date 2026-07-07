import React, { ReactNode } from "react";
import ObservableComponent from "./ObservableComponent";
import * as ui from "../native/index";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";

export interface MarqueeTextProps extends BaseUIProps {
  data: string;
  direction?: "left" | "right" | "up" | "down";
  speed?: number; // pixels per second
  pauseOnHover?: boolean;
  style?: ui.TextStyle;
  textAlign?: ui.TextAlign;
}

class _MarqueeText extends ObservableComponent<MarqueeTextProps> {
  private containerRef = React.createRef<HTMLDivElement>();
  private animationId: number | null = null;
  private startTime: number = 0;
  private paused: boolean = false;
  private currentOffset: number = 0;

  static defaultProps = {
    direction: "left" as const,
    speed: 50, // pixels per second
    pauseOnHover: true,
  };

  componentDidMount() {
    this.startAnimation();
  }

  componentWillUnmount() {
    this.stopAnimation();
  }

  startAnimation = () => {
    if (this.animationId !== null) return;

    this.startTime = performance.now();
    const animate = (currentTime: number) => {
      if (!this.containerRef.current) {
        this.animationId = requestAnimationFrame(animate);
        return;
      }

      if (!this.paused) {
        const elapsed = (currentTime - this.startTime) / 1000; // Convert to seconds
        this.currentOffset = elapsed * (this.props.speed || 50);
      }

      const container = this.containerRef.current;
      const content = container.querySelector(
        ".marquee-content"
      ) as HTMLElement;

      if (content) {
        const contentWidth = content.scrollWidth / 3; // Since we have 3 copies
        const contentHeight = content.scrollHeight / 3;

        if (
          this.props.direction === "left" ||
          this.props.direction === "right"
        ) {
          const direction = this.props.direction === "left" ? -1 : 1;
          const offset = this.currentOffset % contentWidth;
          content.style.transform = `translateX(${direction * offset}px)`;
        } else {
          const direction = this.props.direction === "up" ? -1 : 1;
          const offset = this.currentOffset % contentHeight;
          content.style.transform = `translateY(${direction * offset}px)`;
        }
      }

      this.animationId = requestAnimationFrame(animate);
    };

    this.animationId = requestAnimationFrame(animate);
  };

  stopAnimation = () => {
    if (this.animationId !== null) {
      cancelAnimationFrame(this.animationId);
      this.animationId = null;
    }
  };

  handleMouseEnter = () => {
    if (this.props.pauseOnHover) {
      this.paused = true;
    }
  };

  handleMouseLeave = () => {
    if (this.props.pauseOnHover) {
      this.paused = false;
      this.startTime =
        performance.now() - this.currentOffset / (this.props.speed || 50);
    }
  };

  render(): ReactNode {
    const { data, direction } = this.props;
    const isHorizontal = direction === "left" || direction === "right";

    // Duplicate content for seamless loop
    const marqueeContent = React.createElement(
      "div",
      {
        className: "marquee-content",
        style: {
          display: "inline-flex",
          whiteSpace: "nowrap",
          willChange: "transform",
        } as React.CSSProperties,
      },
      React.createElement(
        "span",
        {
          style: {
            marginRight: isHorizontal ? "50px" : "0",
            marginBottom: !isHorizontal ? "50px" : "0",
          } as React.CSSProperties,
        },
        data
      ),
      React.createElement(
        "span",
        {
          style: {
            marginRight: isHorizontal ? "50px" : "0",
            marginBottom: !isHorizontal ? "50px" : "0",
          } as React.CSSProperties,
        },
        data
      ),
      React.createElement("span", null, data)
    );

    const containerStyle: React.CSSProperties = {
      overflow: "hidden",
      width: "100%",
      height: isHorizontal ? "auto" : "100px",
      position: "relative",
    };

    return React.createElement(
      "div",
      {
        ref: this.containerRef,
        className: "MarqueeText",
        style: containerStyle,
        onMouseEnter: this.handleMouseEnter,
        onMouseLeave: this.handleMouseLeave,
      },
      marqueeContent
    );
  }
}

export default function MarqueeText(props: MarqueeTextProps) {
  return React.createElement(_MarqueeText, {
    ..._MarqueeText.defaultProps,
    ...props,
  });
}
