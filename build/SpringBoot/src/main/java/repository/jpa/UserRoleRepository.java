package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.Organization;
import models.UserRole;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class UserRoleRepository extends AbstractD3ERepository<UserRole> {
  public int getTypeIndex() {
    return SchemaConstants.UserRole;
  }

  public List<UserRole> getByOrganization(Organization organization) {
    String queryStr = "SELECT a._id from _user_role a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }
}
