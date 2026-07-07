import React, { ReactNode } from "react";
import * as ui from "../native";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";

export interface ScrollAnimatedContainerProps extends BaseUIProps {
  child?: ReactNode;
  animationClass?: string; // animate.css class
  threshold?: number; // 0-1, when to trigger
  triggerOnce?: boolean; // animate only once
  duration?: string; // animation duration
  delay?: string; // animation delay
  [key: string]: any;
}

class _ScrollAnimatedContainer extends React.Component<
  ScrollAnimatedContainerProps,
  { isVisible: boolean; hasAnimated: boolean }
> {
  static defaultProps = {
    animationClass: "fadeInUp",
    threshold: 0.1,
    triggerOnce: false,
    duration: "1s",
    delay: "0s",
  };

  private elementRef = React.createRef<HTMLElement>();
  private observer: IntersectionObserver | null = null;

  constructor(props: ScrollAnimatedContainerProps) {
    super(props);
    this.state = {
      isVisible: false,
      hasAnimated: false,
    };
  }

  componentDidMount() {
    this.setupObserver();
  }

  componentWillUnmount() {
    if (this.observer) {
      this.observer.disconnect();
    }
  }

  setupObserver() {
    const options = {
      threshold:
        typeof this.props.threshold === "string"
          ? parseFloat(this.props.threshold)
          : this.props.threshold,
      rootMargin: "0px",
    };

    this.observer = new IntersectionObserver((entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          this.setState({ isVisible: true });
          if (this.props.triggerOnce && !this.state.hasAnimated) {
            this.setState({ hasAnimated: true });
          }
        } else if (!this.props.triggerOnce) {
          // Reset visibility when element leaves viewport (only if triggerOnce is false)
          this.setState({ isVisible: false });
        }
      });
    }, options);

    // Observe the element once rendered
    setTimeout(() => {
      const element = this.elementRef.current;
      if (element && this.observer) {
        this.observer.observe(element);
      }
    }, 100);
  }

  render(): ReactNode {
    const { isVisible, hasAnimated } = this.state;
    // If triggerOnce is true and has animated, keep showing animation
    // Otherwise, only show animation when visible
    const shouldAnimate = this.props.triggerOnce
      ? isVisible || hasAnimated
      : isVisible;

    // Build animation class names
    // Automatically add animate__ prefix if not already present
    const fullAnimationClass = this.props.animationClass?.startsWith(
      "animate__"
    )
      ? this.props.animationClass
      : `animate__${this.props.animationClass}`;

    const animationClasses = shouldAnimate
      ? `animate__animated ${fullAnimationClass}`
      : "";

    // Combine classNames
    const finalClassName = ui.join(
      animationClasses,
      this.props.className ?? ""
    );

    // Animation styles: duration/delay + opacity 0.2 when not yet animating
    const animationStyle = {
      "--animate-duration": this.props.duration,
      "--animate-delay": this.props.delay,
      ...(!shouldAnimate ? { opacity: 0.2 } : {}),
    } as React.CSSProperties;

    // Build container props, excluding our custom animation props
    const {
      child,
      animationClass,
      threshold,
      triggerOnce,
      duration,
      delay,
      ...containerProps
    } = this.props;

    return React.createElement(
      "div",
      {
        ref: this.elementRef,
        style: animationStyle,
      },
      ui.Container({
        ...containerProps,
        child: child,
        className: finalClassName,
        ...copyBaseUIProps(this.props),
      })
    );
  }
}

export default function ScrollAnimatedContainer(
  props: ScrollAnimatedContainerProps
) {
  return React.createElement(_ScrollAnimatedContainer, {
    ..._ScrollAnimatedContainer.defaultProps,
    ...props,
  });
}
