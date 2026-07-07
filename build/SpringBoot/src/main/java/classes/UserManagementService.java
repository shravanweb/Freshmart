package classes;

import models.User;
import models.UserInvitation;
import models.UserProfile;
import models.UserRole;
import store.Database;

public class UserManagementService {
  public UserManagementService() {}

  public static User createUser(User user) {
    if (user == null) {
      throw new RuntimeException("User cannot be null");
    }
    Database.get().save(user);
    return user;
  }

  public static User updateUser(User user) {
    if (user == null) {
      throw new RuntimeException("User cannot be null");
    }
    Database.get().save(user);
    return user;
  }

  public static void deleteUser(User user) {
    if (user == null) {
      return;
    }
    Database.get().delete(user);
  }

  public static UserProfile createUserProfile(UserProfile userProfile) {
    if (userProfile == null) {
      throw new RuntimeException("UserProfile cannot be null");
    }
    Database.get().save(userProfile);
    return userProfile;
  }

  public static UserProfile updateUserProfile(UserProfile userProfile) {
    if (userProfile == null) {
      throw new RuntimeException("UserProfile cannot be null");
    }
    Database.get().save(userProfile);
    return userProfile;
  }

  public static void deleteUserProfile(UserProfile userProfile) {
    if (userProfile == null) {
      return;
    }
    Database.get().delete(userProfile);
  }

  public static UserInvitation createUserInvitation(UserInvitation userInvitation) {
    if (userInvitation == null) {
      throw new RuntimeException("UserInvitation cannot be null");
    }
    Database.get().save(userInvitation);
    return userInvitation;
  }

  public static UserInvitation updateUserInvitation(UserInvitation userInvitation) {
    if (userInvitation == null) {
      throw new RuntimeException("UserInvitation cannot be null");
    }
    Database.get().save(userInvitation);
    return userInvitation;
  }

  public static void deleteUserInvitation(UserInvitation userInvitation) {
    if (userInvitation == null) {
      return;
    }
    Database.get().delete(userInvitation);
  }

  public static UserRole createUserRole(UserRole userRole) {
    if (userRole == null) {
      throw new RuntimeException("UserRole cannot be null");
    }
    Database.get().save(userRole);
    return userRole;
  }

  public static UserRole updateUserRole(UserRole userRole) {
    if (userRole == null) {
      throw new RuntimeException("UserRole cannot be null");
    }
    Database.get().save(userRole);
    return userRole;
  }

  public static void deleteUserRole(UserRole userRole) {
    if (userRole == null) {
      return;
    }
    Database.get().delete(userRole);
  }
}
