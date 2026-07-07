import React, { ReactNode, ReactText, Ref, RefObject } from "react";
import { Runnable } from "../classes/core";
import Duration from "../core/Duration";
import EventBus from "../utils/EventBus";
import LayoutBounds from "./LayoutBounds";
import ObservableComponent from "./ObservableComponent";
import * as ui from "../native/index";
import { PopupEvent } from "./PopupEvent";
import { ScrollData } from "./ScrollView2";
import PopupTargetController from "./PopupTargetController";
import { OverlayController } from "../native/ui/Overlay";
import LayoutAware from "../native/ui/LayoutAware";
import PageNavigator from "../classes/PageNavigator";
import Timer from "../utils/Timer";
import { BuildContext } from "../classes/BuildContext";
import { StyleThemeData } from "./ThemeWrapper";

type _OnShowChange = () => void;

type PopupBuilder = (context: any) => ReactNode;

interface PopupWrapperProps {
  popup: Popup;
  child: ReactNode;
}

class _PopupWrapper extends React.Component<PopupWrapperProps> {
  wrapperRef: RefObject<any>;
  pos: ui.Offset;
  constructor(props) {
    super(props);
    this.wrapperRef = React.createRef();
    this.handleClickOutside = this.handleClickOutside.bind(this);
  }

  componentDidMount() {
    document.addEventListener("mousedown", this.handleClickOutside);
  }

  componentWillUnmount() {
    document.removeEventListener("mousedown", this.handleClickOutside);
  }

  handleClickOutside(event: MouseEvent) {
    // Check if event page position is with in client rect of the popup
    let pos = new ui.Offset({ dx: event.pageX, dy: event.pageY });
    let rect: DOMRect = this.wrapperRef?.current?.getBoundingClientRect();

    if (
      rect &&
      !ui.Rect.fromLTWH(rect.left, rect.top, rect.width, rect.height).contains(
        pos
      )
    ) {
      if (this.props.popup.autoClose) {
        new Timer(new Duration({ milliseconds: 200 }), () => {
          this.props.popup.dispose();
        });
      }
    }
  }

  renderAgainBasedOnChildSize(): void {
    new Timer(new Duration({ microseconds: 200 }), () => {
      if (this.wrapperRef && this.wrapperRef.current) {
        let newPos = this.positionPopup();
        if (newPos.dx != this.pos.dx || newPos.dy != this.pos.dy) {
          this.pos = newPos;
          this.setState({});
          console.log(newPos);
        }
      } else {
        //Ref is not ready yet. Try again.. Not really sure if this is needed
        this.renderAgainBasedOnChildSize();
      }
    });
  }

  positionPopup(): ui.Offset {
    let target = this.props.popup.target;
    let routerOffset = ui.Offset.zero;
    let childRect =
      this.wrapperRef && this.wrapperRef.current?.getBoundingClientRect();
    return positionDependentBox({
      target:
        target != null
          ? target.topLeft.sub(routerOffset).and(target.size)
          : undefined,
      size: new ui.Size({
        width: window.innerWidth,
        height: window.innerHeight,
      }),
      childSize: new ui.Size({
        width: childRect ? childRect.width : 0,
        height: childRect ? childRect.height : 0,
      }),
      verticalOffset: this.props.popup.verticalOffset,
      horizontalOffset: this.props.popup.horizontalOffset,
      side: this.props.popup.position,
    });
  }

  render(): React.ReactNode {
    let style: any = {};
    let className = "";
    if (this.props.popup.model) {
      className += " model";
    }
    if (this.props.popup.target != null) {
      this.pos = this.positionPopup();
      style.top = this.pos.dy;
      style.left = this.pos.dx;
      this.renderAgainBasedOnChildSize();
    } else {
      className += " " + ui.PopUpPosition[this.props.popup.position];
    }

    let child = React.createElement(
      BuildContext.Provider,
      {
        value: {
          ...this.context,
          popup: this.props.popup,
          theme: StyleThemeData.current,
        },
      },
      this.props.child
    );

    if (!this.props.popup.target) {
      return React.createElement(
        "ui-popup-wrapper",
        {
          class: className,
        },
        React.createElement(
          "ui-popup",
          {
            ref: this.wrapperRef,
            class: className,
            style: style,
          },
          child
        )
      );
    } else {
      return React.createElement(
        "ui-popup",
        {
          ref: this.wrapperRef,
          class: className,
          style: style,
        },
        child
      );
    }
  }
}

function PopupWrapper(props: PopupWrapperProps): ReactNode {
  return React.createElement(_PopupWrapper, props);
}

interface PopupOverlayProps {
  popup?: Popup;
  offset?: ui.Offset;
  model?: boolean;
  mouseMove?: _PointerCallback;
  mouseDown?: _PointerCallback;
  mouseUp?: _PointerCallback;
  float?: boolean;
  popUpchild?: ReactNode;
  verticalOffset?: number;
  horizontalOffset?: number;
  position?: ui.PopUpPosition;
  boundsCallBack?: _BoundsCallback;
  constraints?: ui.BoxConstraints;
  showConnector?: boolean;
  color?: ui.Color;
  positionX?: number;
  positionY?: number;
  autoClose?: boolean;
  target?: ui.Rect;
}

class _PopupOverlay extends React.Component<PopupOverlayProps> {
  body(routerOffset: ui.Offset) {
    return ui.Listener({
      onPointerDown: (e) => this.props.mouseDown(e.localPosition),
      onPointerUp: (e) => this.props.mouseUp(e.localPosition),
      onPointerMove: (e) => this.props.mouseMove(e.localPosition),
      child: ui.Container({
        color: new ui.Color(0xffffffff),
        child: this.props.popUpchild,
      }),
    });
  }

  render(): React.ReactNode {
    return this.body(null);
  }
}

function PopupOverlay(props: PopupOverlayProps): ReactNode {
  return React.createElement(_PopupOverlay, props);
}

interface DirectionalityProps {
  textDirection?: ui.TextDirection;
  child: ReactNode;
}

function Directionality(props: DirectionalityProps): ReactNode {
  return props.child;
}

export default class Popup {
  controller: PopupTargetController;
  _scrolldata: ScrollData;
  _scrollOffset: ui.Offset;
  _context: any;

  child: ReactNode;

  model: boolean;

  autoClose: boolean;

  float: boolean;

  constraints: ui.BoxConstraints;

  verticalOffset: number;

  horizontalOffset: number;

  position: ui.PopUpPosition;

  onClose: _OnShowChange;

  showConnector: boolean;

  color: ui.Color;

  positionX: number;

  positionY: number;

  target: ui.Rect;

  takeFocus: boolean;

  static _defaultPreferBelow: boolean = true;
  static _defaultShowConnector: boolean = false;
  static _defaultConstriants: ui.BoxConstraints = new ui.BoxConstraints({});
  static openPopupsCount: number = 0;

  preferBelow: boolean;
  _entry: ui.OverlayEntry;
  currentPos: ui.Offset;
  childRect: ui.Rect;
  _placeHolderBounds: ui.Rect;
  _mouseDownAt: ui.Offset;
  _positionAtMouseDown: ui.Offset;
  _lastFocus: ui.FocusNode;
  _routerBounds: ui.Offset = ui.Offset.zero;

  constructor(props: Partial<Popup>) {
    Object.assign(this, props);
  }

  initState() {
    // TODO, in the application any mouse event should call this handler.
    // GestureBinding.instance.pointerRouter.addGlobalRoute(this._handlePointerEvent);
  }

  _mouseDown = (offset: ui.Offset) => {
    this._mouseDownAt = offset;
    this._positionAtMouseDown = this.currentPos;
  };

  _mouseMove = (offset: ui.Offset) => {
    // TODO
    // this.currentPos = this._positionAtMouseDown + (offset - this._mouseDownAt);
    // this._entry?.markNeedsBuild();
  };

  _mouseUp = (offset: ui.Offset) => {
    // TODO
    // this.currentPos = this._positionAtMouseDown + (offset - this._mouseDownAt);
    // this._entry?.markNeedsBuild();
  };

  boundsCallBack = (
    offset: ui.Offset,
    childSize: ui.Size,
    routerBounds: ui.Offset
  ) => {
    this.currentPos = offset;
    this.childRect = offset.and(childSize);
    this._routerBounds = routerBounds;
  };

  hidePopup({ closeCall = false }: { closeCall?: boolean }) {
    if (closeCall && this.onClose != null) {
      this.onClose();
      //ToSend Event when hiding by clicking outside the popup
    }
    Popup.openPopupsCount--;
    EventBus.get().fire(new PopupEvent(Popup.openPopupsCount));
    this._removeEntry();
    if (this.takeFocus) {
      this._lastFocus?.requestFocus();
    }
  }

  showPopup(context: any) {
    if (this._entry != null) {
      return;
    }
    Popup.openPopupsCount++; // TODO
    // this._lastFocus = ui.FocusManager.instance.primaryFocus;
    // EventBus.get().fire(new PopupEvent(Popup.openPopupsCount));
    this.initState();
    this._context = context;
    // this._scrolldata = ui.ScrollWrapper.of(context);
    // if (this._scrolldata != null) {
    //     this._scrollOffset = new ui.Offset({ dx: this._scrolldata.dx, dy: this._scrolldata.dy });
    // }
    // this._scrolldata?.addListener(this.onTargetUpdate);
    this._createNewEntry(context);
  }

  onTargetUpdate = () => {
    this.controller.overlay.remove(this._entry);
    this._createNewEntry(this._context);
  };

  _resolvedTarget(): ui.Rect {
    var res = this.target; // TODO
    // if (this._scrolldata != null && this.target != null) {
    //     var currentScrollOffset: ui.Offset = new ui.Offset({ dx: this._scrolldata.dx, dy: this._scrolldata.dy });
    //     var diff: ui.Offset = currentScrollOffset.minus(this._scrollOffset);

    //     res = this.target.translate(-diff.dx, -diff.dy);
    // }
    return res;
  }

  _createNewEntry(context: any) {
    // We create this widget outside of the overlay entry's builder to prevent
    // updated values from happening to leak into the overlay when the overlay
    // rebuilds.
    // TODO
    var size: ui.Size = ui.MediaQuery.of(context).size;
    this._entry = new ui.OverlayEntry({
      builder: (context) =>
        Directionality({
          //TODO textDirection: Directionality.of(context),
          child: PopupWrapper({
            popup: this,
            child: ui.ConstrainedBox({
              constraints: new ui.BoxConstraints({
                maxWidth: size.width,
                maxHeight: size.height,
              }),
              child: PopupOverlay({
                popup: this,
                boundsCallBack: this.boundsCallBack,
                popUpchild: this.child,
                model: this.model,
                float: this.float,
                mouseDown: this._mouseDown,
                mouseMove: this._mouseMove,
                mouseUp: this._mouseUp,
                offset: this.currentPos,
                verticalOffset: this.verticalOffset,
                horizontalOffset: this.horizontalOffset,
                positionX: this.positionX,
                positionY: this.positionY,
                position: this.position,
                constraints: this.constraints,
                showConnector: this.showConnector,
                autoClose: this.autoClose,
                target: this._resolvedTarget(),
                color: this.color,
              }),
            }),
          }),
        }),
    });
    PageNavigator.defaultOverlay.insert(this._entry);
  }

  _removeEntry() {
    PageNavigator.defaultOverlay.remove(this._entry);
    this._entry = null;
    this._scrolldata?.removeListener(this.onTargetUpdate);
  }

  _handlePointerEvent(event: PointerEvent) {
    // TODO
    // if (this._entry == null || !(event instanceof ui.PointerDownEvent)) {
    //     return;
    // }
    // var pos = event.position - this._routerBounds;
    // if (this.autoClose && this.childRect != null && !this.childRect.contains(pos)) {
    //     new Timer(new Duration({ milliseconds: 200 }), () => {
    //         this.hidePopup({ closeCall: true });
    //     });
    // }
  }

  _onPlaceholderBounds(bounds: ui.Rect, offset: ui.Offset) {
    this._placeHolderBounds = bounds;
  }

  dispose() {
    if (this._entry != null) {
      // TODO remove this global event listener
      // GestureBinding.instance.pointerRouter
      //     .removeGlobalRoute(this._handlePointerEvent);
      Popup.openPopupsCount--;
      EventBus.get().fire(new PopupEvent(Popup.openPopupsCount));
      this._removeEntry();
      if (this.takeFocus) {
        this._lastFocus?.requestFocus();
      }
    }
  }
}

/// A delegate for computing the layout of a popup to be displayed above or
/// bellow a target specified in the global coordinate system.
type _BoundsCall = (offset: ui.Offset, size: ui.Size) => void;

type BoundsUpdater = (rect: ui.Rect, scrollX: number, scrollY: number) => void;
type _BoundsCallback = (
  offset: ui.Offset,
  size: ui.Size,
  routerBounds: ui.Offset
) => void;
type _PointerCallback = (offset: ui.Offset) => void;

function positionDependentBox(props: {
  size: ui.Size;
  childSize: ui.Size;
  side: ui.PopUpPosition;
  target: ui.Rect;
  verticalOffset: number;
  horizontalOffset: number;
}): ui.Offset {
  if (props.verticalOffset == undefined) {
    props.verticalOffset = 0.0;
  }
  if (props.horizontalOffset == undefined) {
    props.horizontalOffset = 0.0;
  }
  var y = 0;
  var x = 0;
  if (
    props.side == ui.PopUpPosition.Bottom ||
    props.side == ui.PopUpPosition.Top
  ) {
    var fitsBelow =
      props.target.bottom + props.verticalOffset + props.childSize.height <=
      props.size.height;
    var fitsAbove =
      props.target.top - props.verticalOffset - props.childSize.height >= 0;
    var tooltipBelow =
      props.side == ui.PopUpPosition.Bottom
        ? fitsBelow
        : !(fitsAbove || !fitsBelow);
    if (tooltipBelow) {
      y = Math.min(
        props.target.bottom + props.verticalOffset,
        props.size.height
      );
    } else {
      y = props.target.top - props.verticalOffset - props.childSize.height;
    }
    if (y < 0) {
      y = 0;
    }
    x = props.target.left;
    if (x < 0) {
      x = 0;
    }
    if (x + props.childSize.width > props.size.width) {
      x = props.size.width - props.childSize.width;
    }
  } else {
    var fitsRight =
      props.target.right + props.horizontalOffset + props.childSize.width <=
      props.size.width;
    var fitsLeft =
      props.target.left - props.horizontalOffset - props.childSize.width >= 0;
    var tooltipRight =
      props.side == ui.PopUpPosition.Right
        ? fitsRight || !fitsLeft
        : !(fitsLeft || !fitsRight);
    if (tooltipRight) {
      x = Math.min(
        props.target.right + props.horizontalOffset,
        props.size.width
      );
    } else {
      x = props.target.left - props.horizontalOffset - props.childSize.width;
    }
    y = props.target.top;
    if (y < 0) {
      y = 0;
    }
    if (y + props.childSize.height > props.size.height) {
      y = props.size.height - props.childSize.height;
    }
  }
  return new ui.Offset({ dx: x, dy: y });
}
