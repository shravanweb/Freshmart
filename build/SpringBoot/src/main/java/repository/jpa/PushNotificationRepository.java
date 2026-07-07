package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.BaseUser;
import models.PushNotification;
import models.UserDevice;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class PushNotificationRepository extends AbstractD3ERepository<PushNotification> {
  public int getTypeIndex() {
    return SchemaConstants.PushNotification;
  }

  public List<PushNotification> findByUsers(BaseUser users) {
    String queryStr =
        "select a._push_notification_id from _push_notification_users_fab88b a where a._users_id = :users";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "users", users);
    return getAllXsByY(query);
  }

  public List<PushNotification> findByFailedDevices(UserDevice failedDevices) {
    String queryStr =
        "select a._push_notification_id from _push_notification_failed_devices_d63aa4 a where a._failed_devices_id = :failedDevices";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "failedDevices", failedDevices);
    return getAllXsByY(query);
  }
}
