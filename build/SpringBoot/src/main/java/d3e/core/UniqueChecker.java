package d3e.core;

import models.OneTimePassword;
import models.Organization;
import models.User;
import models.UserInvitation;
import models.UserProfile;

public class UniqueChecker {
  public static boolean checkTokenUniqueInOneTimePassword(
      OneTimePassword oneTimePassword, String token) {
    return QueryProvider.get().checkTokenUniqueInOneTimePassword(oneTimePassword.getId(), token);
  }

  public static boolean checkNameUniqueInOrganization(Organization organization, String name) {
    return QueryProvider.get().checkNameUniqueInOrganization(organization.getId(), name);
  }

  public static boolean checkCodeUniqueInOrganization(Organization organization, String code) {
    return QueryProvider.get().checkCodeUniqueInOrganization(organization.getId(), code);
  }

  public static boolean checkUserEmailUniqueInOrganization(
      Organization organization, User user, String email) {
    return QueryProvider.get()
        .checkUserEmailUniqueInOrganization(organization.getId(), user.getId(), email);
  }

  public static boolean checkUserPasswordUniqueInOrganization(
      Organization organization, User user, String password) {
    return QueryProvider.get()
        .checkUserPasswordUniqueInOrganization(organization.getId(), user.getId(), password);
  }

  public static boolean checkUserInvitationTokenUniqueInOrganization(
      Organization organization, UserInvitation userInvitation, String token) {
    return QueryProvider.get()
        .checkUserInvitationTokenUniqueInOrganization(
            organization.getId(), userInvitation.getId(), token);
  }

  public static boolean checkUserProfileUserUniqueInOrganization(
      Organization organization, UserProfile userProfile, User user) {
    return QueryProvider.get()
        .checkUserProfileUserUniqueInOrganization(organization.getId(), userProfile.getId(), user);
  }
}
