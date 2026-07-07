package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.Organization;
import models.Store;
import models.User;
import models.UserProfile;
import models.Warehouse;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class UserProfileRepository extends AbstractD3ERepository<UserProfile> {
  public int getTypeIndex() {
    return SchemaConstants.UserProfile;
  }

  public boolean checkUserProfileUserUniqueInOrganization(Long masterId, Long id, User user) {
    String queryStr =
        "SELECT CASE WHEN count(*) > 0 THEN false ELSE true END as _a from _user_profile a where a._user_id = :user and a._id != :id and a._organization_id = :masterId";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "user", user);
    QueryImplUtil.setParameter(query, "id", id);
    QueryImplUtil.setParameter(query, "masterId", masterId);
    return checkUnique(query);
  }

  public UserProfile getByUser(User user) {
    String queryStr = "SELECT a._id from _user_profile a where a._user_id = :user";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "user", user);
    return getXByY(query);
  }

  public List<UserProfile> findByAssignedStores(Store assignedStores) {
    String queryStr =
        "select a._user_profile_id from _user_profile_assigned_stores_dd1471 a where a._assigned_stores_id = :assignedStores";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "assignedStores", assignedStores);
    return getAllXsByY(query);
  }

  public List<UserProfile> findByAssignedWarehouses(Warehouse assignedWarehouses) {
    String queryStr =
        "select a._user_profile_id from _user_profile_assigned_warehouses_17ffb1 a where a._assigned_warehouses_id = :assignedWarehouses";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "assignedWarehouses", assignedWarehouses);
    return getAllXsByY(query);
  }

  public List<UserProfile> getByOrganization(Organization organization) {
    String queryStr = "SELECT a._id from _user_profile a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }
}
