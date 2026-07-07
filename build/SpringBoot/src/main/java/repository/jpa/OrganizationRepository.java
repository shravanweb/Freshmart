package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.Organization;
import models.User;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class OrganizationRepository extends AbstractD3ERepository<Organization> {
  public int getTypeIndex() {
    return SchemaConstants.Organization;
  }

  public boolean checkNameUnique(Long id, String name) {
    String queryStr =
        "SELECT CASE WHEN count(*) > 0 THEN false ELSE true END as _a from _organization a where a._name = :name and a._id != :id";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "name", name);
    QueryImplUtil.setParameter(query, "id", id);
    return checkUnique(query);
  }

  public Organization getByName(String name) {
    String queryStr = "SELECT a._id from _organization a where a._name = :name";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "name", name);
    return getXByY(query);
  }

  public boolean checkCodeUnique(Long id, String code) {
    String queryStr =
        "SELECT CASE WHEN count(*) > 0 THEN false ELSE true END as _a from _organization a where a._code = :code and a._id != :id";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "code", code);
    QueryImplUtil.setParameter(query, "id", id);
    return checkUnique(query);
  }

  public Organization getByCode(String code) {
    String queryStr = "SELECT a._id from _organization a where a._code = :code";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "code", code);
    return getXByY(query);
  }

  public List<Organization> getByCreatedBy(User createdBy) {
    String queryStr = "SELECT a._id from _organization a where a._created_by_id = :createdBy";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "createdBy", createdBy);
    return getAllXsByY(query);
  }
}
