import PageNavigator from "../classes/PageNavigator";
import Query from "../classes/Query";
import User from "../models/User";
import UserProfile from "../models/UserProfile";
import UserProfileByUserRequest from "../models/UserProfileByUserRequest";
import CustomerSession from "./CustomerSession";
import CustomerProfileStorage from "./CustomerProfileStorage";
import CustomerProfileApi from "./CustomerProfileApi";
import StaffProfileApi from "./StaffProfileApi";
import { UsageConstants } from "../rocket/D3ETemplate";

export default class PostAuthNavigation {
  public static async routeAfterAuth(
    navigator: PageNavigator,
    user: User,
    token: string,
    options?: { displayName?: string; target?: string }
  ): Promise<void> {
    StaffProfileApi.clearCache();
    const target = options?.target ?? "main";
    const fallbackName = options?.displayName ?? user.email.split("@")[0];
    const profile = await PostAuthNavigation.loadProfile(user);
    const roleName = profile?.appRole?.name ?? "Viewer";
    const displayName = profile?.displayName || fallbackName;

    if (roleName === "Viewer") {
      CustomerSession.save({
        displayName,
        email: user.email,
        token,
      });
      try {
        const remoteProfile = await CustomerProfileApi.getProfile(user.email);
        if (remoteProfile?.phone?.trim()) {
          CustomerProfileStorage.savePhoneForEmail(user.email, remoteProfile.phone.trim());
        }
        if (remoteProfile?.displayName?.trim()) {
          CustomerSession.updateDisplayName(remoteProfile.displayName.trim(), true);
        }
      } catch {
        // Profile API may be unavailable during startup.
      }
      navigator.pushLandingPage({ user, target, replace: true });
      return;
    }

    CustomerSession.clear();
    try {
      await StaffProfileApi.getProfile();
    } catch {
      // Header will retry loading the avatar if prefetch fails.
    }
    navigator.pushDashboardPage({ user, target, replace: true });
  }

  private static async loadProfile(user: User): Promise<UserProfile | null> {
    try {
      const profileData = await Query.get().getUserProfileByUser(
        UsageConstants.QUERY_GETUSERPROFILEBYUSER_DASHBOARDPAGE_PROPERTIES_USERPROFILEDATA_COMPUTATION,
        new UserProfileByUserRequest({ user })
      );

      return profileData?.items?.first ?? null;
    } catch {
      return null;
    }
  }
}
