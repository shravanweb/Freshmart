package classes;

import java.time.LocalDateTime;
import models.InAppNotification;
import models.NotificationTemplate;
import store.Database;

public class NotificationService {
  public NotificationService() {}

  public static InAppNotification createInAppNotification(InAppNotification inAppNotification) {
    if (inAppNotification == null) {
      throw new RuntimeException("InAppNotification cannot be null");
    }
    inAppNotification.setCreatedAt(LocalDateTime.now());
    Database.get().save(inAppNotification);
    return inAppNotification;
  }

  public static InAppNotification updateInAppNotification(InAppNotification inAppNotification) {
    if (inAppNotification == null) {
      throw new RuntimeException("InAppNotification cannot be null");
    }
    Database.get().save(inAppNotification);
    return inAppNotification;
  }

  public static void deleteInAppNotification(InAppNotification inAppNotification) {
    if (inAppNotification == null) {
      return;
    }
    Database.get().delete(inAppNotification);
  }

  public static NotificationTemplate createNotificationTemplate(
      NotificationTemplate notificationTemplate) {
    if (notificationTemplate == null) {
      throw new RuntimeException("NotificationTemplate cannot be null");
    }
    Database.get().save(notificationTemplate);
    return notificationTemplate;
  }

  public static NotificationTemplate updateNotificationTemplate(
      NotificationTemplate notificationTemplate) {
    if (notificationTemplate == null) {
      throw new RuntimeException("NotificationTemplate cannot be null");
    }
    Database.get().save(notificationTemplate);
    return notificationTemplate;
  }

  public static void deleteNotificationTemplate(NotificationTemplate notificationTemplate) {
    if (notificationTemplate == null) {
      return;
    }
    Database.get().delete(notificationTemplate);
  }
}
