import React, { ReactNode } from "react";
import { Radius } from "../classes/Radius";
import { RadiusExt } from "../classes/RadiusExt";
import ScrollbarOrientation from "../classes/ScrollbarOrientation";
import { ScrollController } from "../classes/ScrollController";
import ScrollNotificationPredicate from "../classes/ScrollNotificationPredicate";
import BaseUIProps from "./BaseUIProps";

interface ScrollbarProps extends BaseUIProps {
  kye?: string;
  child: ReactNode;
  isAlwaysShown?: boolean;
  controller?: ScrollController;
  thumbVisibility?: boolean;
  trackVisibility?: boolean;
  showTrackOnHover?: boolean;
  thickness?: number;
  hoverThickness?: number;
  radius?: Radius;
  notificationPredicate?: ScrollNotificationPredicate;
  interactive?: boolean;
  scrollbarOrientation?: ScrollbarOrientation;
}

class _Scrollbar extends React.Component<ScrollbarProps, {}> {
  isIOS(): boolean {
    // TODO Theme.of(context).platform == TargetPlatform.iOS
    return false;
  }

  render(): React.ReactNode {
    if (this.isIOS()) {
      return CupertinoScrollbar({
        thumbVisibility:
          this.props.isAlwaysShown ?? this.props.thumbVisibility ?? false,
        thickness: this.props.thickness ?? _CupertinoScrollbar.defaultThickness,
        thicknessWhileDragging:
          this.props.thickness ??
          _CupertinoScrollbar.defaultThicknessWhileDragging,
        radius: this.props.radius ?? _CupertinoScrollbar.defaultRadius,
        radiusWhileDragging:
          this.props.radius ?? _CupertinoScrollbar.defaultRadiusWhileDragging,
        controller: this.props.controller,
        notificationPredicate: this.props.notificationPredicate,
        scrollbarOrientation: this.props.scrollbarOrientation,
        child: this.props.child,
      });
    }
    return MaterialScrollbar({
      controller: this.props.controller,
      thumbVisibility: this.props.isAlwaysShown ?? this.props.thumbVisibility,
      trackVisibility: this.props.trackVisibility,
      showTrackOnHover: this.props.showTrackOnHover,
      hoverThickness: this.props.hoverThickness,
      thickness: this.props.thickness,
      radius: this.props.radius,
      notificationPredicate: this.props.notificationPredicate,
      interactive: this.props.interactive,
      scrollbarOrientation: this.props.scrollbarOrientation,
      child: this.props.child,
    });
  }
}

interface CupertinoScrollbarProps extends BaseUIProps {
  key?: string;
  child: ReactNode;
  controller?: ScrollController;
  thumbVisibility?: boolean;
  thickness?: number;
  thicknessWhileDragging?: number;
  radius?: Radius;
  radiusWhileDragging?: Radius;
  notificationPredicate?: ScrollNotificationPredicate;
  scrollbarOrientation?: ScrollNotificationPredicate;
}

class _CupertinoScrollbar extends React.Component<CupertinoScrollbarProps, {}> {
  static defaultThickness = 3;

  /// Default value for [thicknessWhileDragging] if it's not specified in
  /// [CupertinoScrollbar].
  static defaultThicknessWhileDragging = 8.0;

  /// Default value for [radius] if it's not specified in [CupertinoScrollbar].
  static defaultRadius = RadiusExt.circular(1.5);

  /// Default value for [radiusWhileDragging] if it's not specified in
  /// [CupertinoScrollbar].
  static defaultRadiusWhileDragging = RadiusExt.circular(4.0);

  render(): React.ReactNode {
    return this.props.child;
  }
}

function CupertinoScrollbar(props: CupertinoScrollbarProps) {
  return React.createElement(_CupertinoScrollbar, props);
}

interface MaterialScrollbarProps extends BaseUIProps {
  key?: string;
  child: ReactNode;
  controller?: ScrollController;
  thumbVisibility?: boolean;
  trackVisibility?: boolean;
  showTrackOnHover?: boolean;
  hoverThickness?: number;
  thickness?: number;
  radius?: Radius;
  notificationPredicate?: ScrollNotificationPredicate;
  interactive?: boolean;
  scrollbarOrientation?: ScrollNotificationPredicate;
}

class _MaterialScrollbar extends React.Component<MaterialScrollbarProps, {}> {
  render(): React.ReactNode {
    return this.props.child;
  }
}

function MaterialScrollbar(props: MaterialScrollbarProps) {
  return React.createElement(_MaterialScrollbar, props);
}

export default function Scrollbar(props: ScrollbarProps) {
  return React.createElement(_Scrollbar, props);
}
