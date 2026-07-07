package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.NotificationTemplate;
import models.Organization;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class NotificationTemplateRepository extends AbstractD3ERepository<NotificationTemplate> {
  public int getTypeIndex() {
    return SchemaConstants.NotificationTemplate;
  }

  public List<NotificationTemplate> getByOrganization(Organization organization) {
    String queryStr =
        "SELECT a._id from _notification_template a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }
}
