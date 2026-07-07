package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.AuditLog;
import models.Organization;
import models.User;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class AuditLogRepository extends AbstractD3ERepository<AuditLog> {
  public int getTypeIndex() {
    return SchemaConstants.AuditLog;
  }

  public List<AuditLog> getByPerformedBy(User performedBy) {
    String queryStr = "SELECT a._id from _audit_log a where a._performed_by_id = :performedBy";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "performedBy", performedBy);
    return getAllXsByY(query);
  }

  public List<AuditLog> getByOrganization(Organization organization) {
    String queryStr = "SELECT a._id from _audit_log a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }
}
