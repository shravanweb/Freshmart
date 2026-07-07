import React from "react";
import { ReactNode } from "react";
import ObservableState from "../utils/ObservableState";
import * as ui from "../native";
import ObservableComponent from "./ObservableComponent";
import BaseUIProps, { copyBaseUIProps } from "../native/ui/BaseUIProps";
import ObjectObservable from "../utils/ObjectObservable";
import ListWrapper from "../utils/ListWrapper";
import Organization from "../models/Organization";
import TextButton from "./TextButton";
import MaterialIcons from "../icons/MaterialIcons";
import User from "../models/User";
import TextView from "./TextView";
import InAppNotification from "../models/InAppNotification";
import IconView from "./IconView";
import { BuildContext } from "../classes/BuildContext";

type _NotificationCenterWidgetOnMarkRead = (
  notification: InAppNotification
) => void;

type _NotificationCenterWidgetOnViewAll = () => void;

type _ViewAllRefOnPressed = (d3eState: NotificationCenterWidgetRefs) => void;

export interface NotificationCenterWidgetProps extends BaseUIProps {
  key?: string;
  user: User;
  organization: Organization;
  unreadCount?: number;
  notifications?: Array<InAppNotification>;
  _notificationsHash?: number;
  onMarkRead?: _NotificationCenterWidgetOnMarkRead;
  onViewAll?: _NotificationCenterWidgetOnViewAll;
}
/// To store state data for NotificationCenterWidget
class NotificationCenterWidgetRefs {
  public viewAllRef: ViewAllRefState = new ViewAllRefState();
}

interface ViewAllRefWithStateProps extends BaseUIProps {
  key?: string;
  d3eState: NotificationCenterWidgetRefs;
  _onViewAllHandler?: _ViewAllRefOnPressed;
  notifications: Array<InAppNotification>;
  organization: Organization;
  user: User;
}

class ViewAllRefState extends ObjectObservable {
  private _disable: boolean = false;
  public get disable(): boolean {
    return this._disable;
  }
  public setDisable(val: boolean) {
    let isValChanged: boolean = this._disable !== val;

    if (!isValChanged) {
      return;
    }

    this._disable = val;

    this.fire("disable", this);
  }
}

class _ViewAllRefWithState extends ObservableComponent<ViewAllRefWithStateProps> {
  viewAllRefFocusNode: ui.FocusNode = new ui.FocusNode();
  public constructor(props: ViewAllRefWithStateProps) {
    super(props);

    this.initState();
  }
  public get notifications(): Array<InAppNotification> {
    return this.props.notifications;
  }
  public get organization(): Organization {
    return this.props.organization;
  }
  public get user(): User {
    return this.props.user;
  }
  public get viewAllRef(): ViewAllRefState {
    return this.props.d3eState.viewAllRef;
  }
  public get d3eState(): NotificationCenterWidgetRefs {
    return this.props.d3eState;
  }
  public get _onViewAllHandler(): _ViewAllRefOnPressed {
    return this.props._onViewAllHandler;
  }
  public initState() {
    super.initState();

    this.updateObservable("viewAllRef", null, this.viewAllRef);

    this.initListeners();

    this.enableBuild = true;
  }
  public initListeners(): void {
    this.updateSyncProperty("organization", this.props.organization);

    this.updateSyncProperty("user", this.props.user);

    this.on(
      [
        "notifications",
        "organization",
        "organization.name",
        "user",
        "user.email",
        "viewAllRef",
      ],
      this.rebuild
    );
  }
  public componentDidUpdate(prevProps: ViewAllRefWithStateProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.organization !== this.props.organization) {
      this.updateObservable(
        "organization",
        prevProps.organization,
        this.props.organization
      );

      this.fire("organization", this);
    }

    if (prevProps.user !== this.props.user) {
      this.updateObservable("user", prevProps.user, this.props.user);

      this.fire("user", this);
    }
  }
  public dispose(): void {
    super.dispose();
  }
  public render(): ReactNode {
    const userEmail = this.user !== null ? this.user.email : "";
    const orgName =
      this.organization !== null ? this.organization.name : "";

    return ui.Container({
      margin: ui.EdgeInsets.fromLTRB(12.0, 0.0, 0.0, 0.0, new Map()),
      child: TextButton({
        label:
          userEmail +
          " \u00B7 " +
          this.notifications.length.toString() +
          " in " +
          orgName,
        disable: this.viewAllRef.disable,
        onPressed: () => {
          this._onViewAllHandler(this.d3eState);
        },
        onFocusChange: (val) => {},
      }),
      className: "xc6",
    });
  }
}
function ViewAllRefWithState(props: ViewAllRefWithStateProps) {
  return React.createElement(_ViewAllRefWithState, props);
}

class _NotificationCenterWidgetState extends ObservableComponent<NotificationCenterWidgetProps> {
  static defaultProps = {
    user: null,
    organization: null,
    unreadCount: 0,
    notifications: [],
    onMarkRead: null,
    onViewAll: null,
  };
  d3eState: NotificationCenterWidgetRefs = new NotificationCenterWidgetRefs();
  badgeLabel: string = "";
  static contextType = BuildContext;
  context: React.ContextType<typeof BuildContext>;
  public constructor(props: NotificationCenterWidgetProps) {
    super(props);

    this.initState();
  }
  public get user(): User {
    return this.props.user;
  }
  public get organization(): Organization {
    return this.props.organization;
  }
  public get unreadCount(): number {
    return this.props.unreadCount;
  }
  public get notifications(): Array<InAppNotification> {
    return this.props.notifications;
  }
  public initState() {
    super.initState();

    this.initListeners();

    this.enableBuild = false;
  }
  public initListeners(): void {
    this.updateSyncProperty("user", this.props.user);

    this.updateSyncProperty("organization", this.props.organization);

    this.subscribeToList(this.notifications, "notifications");

    this.updateSyncCollProperty("notifications", this.props.notifications);

    this.on(["unreadCount"], this.computeBadgeLabel);

    this.computeBadgeLabel();

    this.on(
      [
        "badgeLabel",
        "notifications",
        "organization",
        "unreadCount",
        "user",
        "viewAllRef",
      ],
      this.rebuild
    );
  }
  public componentDidUpdate(prevProps: NotificationCenterWidgetProps): void {
    super.componentDidUpdate(prevProps);

    if (prevProps.user !== this.props.user) {
      this.updateObservable("user", prevProps.user, this.props.user);

      this.fire("user", this);
    }

    if (prevProps.organization !== this.props.organization) {
      this.updateObservable(
        "organization",
        prevProps.organization,
        this.props.organization
      );

      this.fire("organization", this);
    }

    if (prevProps.unreadCount !== this.props.unreadCount) {
      this.fire("unreadCount", this);
    }

    if (prevProps.notifications !== this.props.notifications) {
      this.updateObservableColl(
        "notifications",
        prevProps.notifications,
        this.props.notifications
      );

      this.fire("notifications", this);
    }
  }
  public setBadgeLabel(val: string): void {
    let isValChanged: boolean = this.badgeLabel !== val;

    if (!isValChanged) {
      return;
    }

    this.badgeLabel = val;

    this.fire("badgeLabel", this);
  }
  public computeBadgeLabel = (): void => {
    try {
      this.setBadgeLabel(
        this.unreadCount > 0 ? this.unreadCount.toString() : ""
      );
    } catch (exception) {
      console.log(" exception in computeBadgeLabel : " + exception.toString());

      this.setBadgeLabel("");
    }
  };
  public render(): ReactNode {
    let cStyle = this.context.theme;
    const isCompact = (this.props.className ?? "").includes("headerCompact");
    const badgeLabel =
      this.unreadCount > 0 ? this.unreadCount.toString() : "";

    return ui.Container({
      child: ui.Row({
        mainAxisSize: ui.MainAxisSize.min,
        crossAxisAlignment: ui.CrossAxisAlignment.center,
        children: [
          IconView({
            icon: MaterialIcons.notifications,
            size: 22,
            color: cStyle.c4,
            className: "xa3",
            key: "0",
          }),
          this.unreadCount > 0
            ? TextView({ data: badgeLabel, className: "x14", key: "1" })
            : null,
          !isCompact
            ? ViewAllRefWithState({
                d3eState: this.d3eState,
                _onViewAllHandler: this.onViewAllHandler,
                notifications: this.notifications,
                organization: this.organization,
                user: this.user,
                key: "2",
              })
            : null,
        ],
      }),
      className: ui.join(
        "GlassPanelStyle NotificationCenterWidget glassPanel x126 ",
        this.props.className ?? ""
      ),
      ...copyBaseUIProps(this.props),
    });
  }
  public onViewAllHandler = (d3eState: NotificationCenterWidgetRefs): void => {
    this.onViewAll();
  };
  public get onMarkRead(): _NotificationCenterWidgetOnMarkRead {
    return this.props.onMarkRead;
  }
  public get onViewAll(): _NotificationCenterWidgetOnViewAll {
    return this.props.onViewAll;
  }
  public get viewAllRef() {
    return this.d3eState.viewAllRef;
  }
}
export default function NotificationCenterWidget(
  props: NotificationCenterWidgetProps
) {
  return React.createElement(_NotificationCenterWidgetState, {
    ..._NotificationCenterWidgetState.defaultProps,
    ...props,
  });
}
