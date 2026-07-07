import React, { ReactNode } from "react";
import * as ui from "../native";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";

export type MotionTrigger =
  | "hover"
  | "click"
  | "focus"
  | "mount"
  | "state"
  | "custom";

export interface MotionAnimatedContainerProps extends BaseUIProps {
  child?: ReactNode;
  animationClass?: string;
  duration?: string;
  delay?: string;
  trigger?: MotionTrigger;
  triggerOnce?: boolean;
  resetOnTrigger?: boolean;

  animateOnState?: boolean;

  onTrigger?: () => void;

  hoverAnimationClass?: string; // different animation on hover
  clickAnimationClass?: string; // different animation on click
  focusAnimationClass?: string; // different animation on focus

  [key: string]: any;
}

class _MotionAnimatedContainer extends React.Component<
  MotionAnimatedContainerProps,
  {
    isAnimating: boolean;
    hasAnimated: boolean;
    currentAnimationClass: string;
    animationKey: number;
  }
> {
  static defaultProps = {
    animationClass: "fadeIn",
    trigger: "mount" as MotionTrigger,
    triggerOnce: false,
    resetOnTrigger: false,
    duration: "1s",
    delay: "0s",
    animateOnState: false,
  };

  private elementRef = React.createRef<HTMLElement>();
  private animationCounter: number = 0;
  /** Tracks if pointer is inside so we only animate on first enter, not on cursor move. */
  private isPointerInside: boolean = false;
  /** Delay clearing isPointerInside on exit so spurious exit/enter from moving over children don't retrigger. */
  private hoverExitTimeoutId: ReturnType<typeof setTimeout> | null = null;
  private readonly HOVER_EXIT_DELAY_MS = 80;

  constructor(props: MotionAnimatedContainerProps) {
    super(props);
    this.state = {
      isAnimating: false,
      hasAnimated: false,
      currentAnimationClass: props.animationClass || "fadeIn",
      animationKey: 0,
    };
  }

  componentDidMount() {
    // Trigger mount animation if trigger is 'mount'
    if (this.props.trigger === "mount") {
      setTimeout(() => {
        this.startAnimation();
      }, 100);
    }

    // Trigger state animation if animateOnState is true
    if (this.props.trigger === "state" && this.props.animateOnState) {
      setTimeout(() => {
        this.startAnimation();
      }, 100);
    }
  }

  componentWillUnmount() {
    if (this.hoverExitTimeoutId !== null) {
      clearTimeout(this.hoverExitTimeoutId);
      this.hoverExitTimeoutId = null;
    }
  }

  componentDidUpdate(prevProps: MotionAnimatedContainerProps, prevState: any) {
    // Handle state-based triggers
    if (this.props.trigger === "state") {
      if (this.props.animateOnState !== prevProps.animateOnState) {
        if (this.props.animateOnState) {
          this.startAnimation();
        } else if (this.props.resetOnTrigger) {
          this.resetAnimation();
        }
      }
    }

    // Apply animation classes directly to DOM only for mount/state triggers.
    // For hover/click/focus, startAnimation/resetAnimation already update the DOM;
    // syncing here would remove-then-add the class and restart the animation multiple times.
    const isEventTrigger =
      this.props.trigger === "hover" ||
      this.props.trigger === "click" ||
      this.props.trigger === "focus";
    if (!isEventTrigger && this.elementRef.current) {
      const element = this.elementRef.current as HTMLElement;
      const container =
        element.querySelector("ui-container") ||
        (element.firstElementChild as HTMLElement);

      if (container) {
        const { isAnimating, currentAnimationClass } = this.state;
        const fullAnimationClass = currentAnimationClass?.startsWith(
          "animate__"
        )
          ? currentAnimationClass
          : `animate__${currentAnimationClass}`;

        // Remove all animation classes first (remove any animate__ class)
        const classesToRemove = Array.from(container.classList).filter((cls) =>
          cls.startsWith("animate__")
        );
        classesToRemove.forEach((cls) => container.classList.remove(cls));

        // Add animation class if should animate
        if (isAnimating) {
          container.classList.add("animate__animated", fullAnimationClass);
        }
      }
    }
  }

  startAnimation = () => {
    if (this.props.triggerOnce && this.state.hasAnimated) {
      return; // Don't animate again if triggerOnce is true
    }

    // Increment counter to force re-render and re-trigger animation
    this.animationCounter++;

    // Directly manipulate DOM for immediate effect (especially important for click animations)
    if (this.elementRef.current) {
      const element = this.elementRef.current as HTMLElement;
      const container =
        element.querySelector("ui-container") ||
        (element.firstElementChild as HTMLElement);

      if (container) {
        const { currentAnimationClass } = this.state;
        const fullAnimationClass = currentAnimationClass?.startsWith(
          "animate__"
        )
          ? currentAnimationClass
          : `animate__${currentAnimationClass}`;

        // Remove all animation classes first
        const classesToRemove = Array.from(container.classList).filter((cls) =>
          cls.startsWith("animate__")
        );
        classesToRemove.forEach((cls) => container.classList.remove(cls));

        // Force reflow to ensure class removal is processed
        void (container as HTMLElement).offsetWidth;

        // Add animation classes immediately
        container.classList.add("animate__animated", fullAnimationClass);
      }
    }

    // Update state
    this.setState({
      isAnimating: true,
      hasAnimated: true,
      animationKey: this.animationCounter,
    });

    // If resetOnTrigger, reset after animation completes
    if (this.props.resetOnTrigger && !this.props.triggerOnce) {
      const duration = this.parseDuration(this.props.duration || "1s");
      setTimeout(() => {
        this.resetAnimation();
      }, duration);
    } else if (!this.props.triggerOnce && this.props.trigger !== "hover") {
      // For non-triggerOnce animations (except hover), reset after animation completes
      const duration = this.parseDuration(this.props.duration || "1s");
      setTimeout(() => {
        this.resetAnimation();
      }, duration);
    }
  };

  resetAnimation = () => {
    // Remove animation classes from DOM
    if (this.elementRef.current) {
      const element = this.elementRef.current as HTMLElement;
      const container =
        element.querySelector("ui-container") ||
        (element.firstElementChild as HTMLElement);

      if (container) {
        const classesToRemove = Array.from(container.classList).filter((cls) =>
          cls.startsWith("animate__")
        );
        classesToRemove.forEach((cls) => container.classList.remove(cls));
      }
    }

    this.setState({ isAnimating: false });

    // Small delay before allowing animation again
    setTimeout(() => {
      if (!this.props.triggerOnce) {
        this.setState({ hasAnimated: false });
      }
    }, 50);
  };

  parseDuration = (duration: string): number => {
    // Convert "1s" to 1000ms, "0.5s" to 500ms, etc.
    const match = duration.match(/(\d+\.?\d*)/);
    if (match) {
      const value = parseFloat(match[1]);
      return duration.includes("ms") ? value : value * 1000;
    }
    return 1000; // default 1 second
  };

  handleHoverEnter = () => {
    if (this.props.trigger !== "hover") return;
    // Cancel any pending "pointer left" so moving over children doesn't count as exit
    if (this.hoverExitTimeoutId !== null) {
      clearTimeout(this.hoverExitTimeoutId);
      this.hoverExitTimeoutId = null;
    }
    // Only run animation when pointer first enters; ignore re-enters from cursor move
    if (this.isPointerInside) return;
    this.isPointerInside = true;

    const animationClass =
      this.props.hoverAnimationClass || this.props.animationClass;
    this.setState(
      {
        currentAnimationClass: animationClass || "fadeIn",
        hasAnimated: false, // Reset to allow animation on each hover session
      },
      () => {
        this.startAnimation();
      }
    );
  };

  handleHoverExit = () => {
    if (this.props.trigger !== "hover") return;
    // Delay clearing so exit+enter from moving over children doesn't retrigger animation
    if (this.hoverExitTimeoutId !== null) clearTimeout(this.hoverExitTimeoutId);
    this.hoverExitTimeoutId = setTimeout(() => {
      this.hoverExitTimeoutId = null;
      this.isPointerInside = false;
      if (!this.props.triggerOnce) {
        this.resetAnimation();
      }
    }, this.HOVER_EXIT_DELAY_MS);
  };

  handleClick = (event: React.MouseEvent<HTMLElement>) => {
    if (this.props.trigger === "click") {
      const animationClass =
        this.props.clickAnimationClass || this.props.animationClass;
      this.setState(
        {
          currentAnimationClass: animationClass || "bounce",
          hasAnimated: false, // Reset to allow animation on each click
        },
        () => {
          this.startAnimation();
        }
      );
    }

    // Call original onTap if provided
    if (this.props.onTap) {
      this.props.onTap(event);
    }

    // Don't prevent default or stop propagation - let button clicks work normally
  };

  handleFocus = (focused: boolean) => {
    if (this.props.trigger === "focus") {
      if (focused) {
        const animationClass =
          this.props.focusAnimationClass || this.props.animationClass;
        this.setState({ currentAnimationClass: animationClass || "pulse" });
        this.startAnimation();
      } else if (!this.props.triggerOnce) {
        this.resetAnimation();
      }
    }

    // Call original onFocusChange if provided
    if (this.props.onFocusChange) {
      this.props.onFocusChange(focused);
    }
  };

  // Expose method for custom triggers
  triggerAnimation = () => {
    this.startAnimation();
  };

  render(): ReactNode {
    const { isAnimating, hasAnimated, currentAnimationClass } = this.state;

    // Determine if animation should be active
    const shouldAnimate = this.props.triggerOnce
      ? isAnimating || hasAnimated
      : isAnimating;

    // Build animation class names
    // Automatically add animate__ prefix if not already present
    const fullAnimationClass = currentAnimationClass?.startsWith("animate__")
      ? currentAnimationClass
      : `animate__${currentAnimationClass}`;

    // For non-triggerOnce animations, we need to remove and re-add the class to re-trigger
    const animationClasses = shouldAnimate
      ? `animate__animated ${fullAnimationClass}`
      : "";

    // Combine classNames
    const finalClassName = ui.join(
      animationClasses,
      this.props.className ?? ""
    );

    // Animation styles
    const animationStyle = {
      "--animate-duration": this.props.duration,
      "--animate-delay": this.props.delay,
    } as React.CSSProperties;

    // Build container props, excluding our custom animation props
    const {
      child,
      animationClass,
      trigger,
      triggerOnce,
      resetOnTrigger,
      duration,
      delay,
      animateOnState,
      onTrigger,
      hoverAnimationClass,
      clickAnimationClass,
      focusAnimationClass,
      ...containerProps
    } = this.props;

    // Setup event handlers based on trigger type
    const eventHandlers: any = {};

    // Hover: use native DOM on the wrapper div so enter fires only once (not on cursor move inside)
    if (this.props.trigger === "hover") {
      // Don't pass onEnter/onExit to Container - we handle hover only on the wrapper div
      eventHandlers.onEnter = this.props.onEnter;
      eventHandlers.onExit = this.props.onExit;
    }

    // Note: Click handling is done via native onClick on the wrapper div
    // This allows it to work with buttons that use onPressed
    if (this.props.trigger === "click") {
      // Still provide onTap for compatibility
      eventHandlers.onTap = (e: React.MouseEvent<HTMLElement>) => {
        this.handleClick(e);
      };
    }

    if (this.props.trigger === "focus") {
      eventHandlers.onFocusChange = (focused: boolean) => {
        this.handleFocus(focused);
      };
    }

    // Merge with existing handlers
    const mergedProps = {
      ...copyBaseUIProps(this.props),
      ...eventHandlers,
    };

    // Add native click handler for click trigger; for hover use native mouse enter/leave on div
    const divProps: any = {
      ref: this.elementRef,
      style: animationStyle,
    };

    if (this.props.trigger === "hover") {
      divProps.onMouseEnter = (e: React.MouseEvent<HTMLDivElement>) => {
        this.handleHoverEnter();
        this.props.onEnter?.(e as any);
      };
      divProps.onMouseLeave = (e: React.MouseEvent<HTMLDivElement>) => {
        this.handleHoverExit();
        this.props.onExit?.(e as any);
      };
    }

    if (this.props.trigger === "click") {
      // Use capture phase to catch clicks before they're stopped by buttons
      divProps.onClickCapture = (e: React.MouseEvent<HTMLDivElement>) => {
        this.handleClick(e);
        // Don't prevent default or stop propagation to allow button clicks to work
      };
      // Also add regular onClick as fallback
      divProps.onClick = (e: React.MouseEvent<HTMLDivElement>) => {
        this.handleClick(e);
      };
    }

    return React.createElement(
      "div",
      divProps,
      ui.Container({
        ...containerProps,
        child: child,
        className: finalClassName,
        ...mergedProps,
      })
    );
  }
}

export default function MotionAnimatedContainer(
  props: MotionAnimatedContainerProps
) {
  return React.createElement(_MotionAnimatedContainer, {
    ..._MotionAnimatedContainer.defaultProps,
    ...props,
  });
}
