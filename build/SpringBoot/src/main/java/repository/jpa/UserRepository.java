package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.Organization;
import models.User;
import models.UserDevice;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class UserRepository extends AbstractD3ERepository<User> {
  public int getTypeIndex() {
    return SchemaConstants.User;
  }

  public List<User> getByDevices(UserDevice devices) {
    String queryStr =
        "SELECT a._id from _user a left join _base_user b on b._id = a._id where b._devices_id = :devices";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "devices", devices);
    return getAllXsByY(query);
  }

  public boolean checkUserEmailUniqueInOrganization(Long masterId, Long id, String email) {
    String queryStr =
        "SELECT CASE WHEN count(*) > 0 THEN false ELSE true END as _a from _user a where a._email = :email and a._id != :id and a._organization_id = :masterId";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "email", email);
    QueryImplUtil.setParameter(query, "id", id);
    QueryImplUtil.setParameter(query, "masterId", masterId);
    return checkUnique(query);
  }

  public User getByEmail(String email) {
    String queryStr = "SELECT a._id from _user a where a._email = :email";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "email", email);
    return getXByY(query);
  }

  public boolean checkUserPasswordUniqueInOrganization(Long masterId, Long id, String password) {
    String queryStr =
        "SELECT CASE WHEN count(*) > 0 THEN false ELSE true END as _a from _user a where a._password = :password and a._id != :id and a._organization_id = :masterId";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "password", password);
    QueryImplUtil.setParameter(query, "id", id);
    QueryImplUtil.setParameter(query, "masterId", masterId);
    return checkUnique(query);
  }

  public User getByPassword(String password) {
    String queryStr = "SELECT a._id from _user a where a._password = :password";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "password", password);
    return getXByY(query);
  }

  public List<User> getByOrganization(Organization organization) {
    String queryStr = "SELECT a._id from _user a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }
}
