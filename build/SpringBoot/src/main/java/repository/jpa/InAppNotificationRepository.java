package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.InAppNotification;
import models.Organization;
import models.User;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class InAppNotificationRepository extends AbstractD3ERepository<InAppNotification> {
  public int getTypeIndex() {
    return SchemaConstants.InAppNotification;
  }

  public List<InAppNotification> getByRecipient(User recipient) {
    String queryStr = "SELECT a._id from _in_app_notification a where a._recipient_id = :recipient";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "recipient", recipient);
    return getAllXsByY(query);
  }

  public List<InAppNotification> getByOrganization(Organization organization) {
    String queryStr =
        "SELECT a._id from _in_app_notification a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }
}
