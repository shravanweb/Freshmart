package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.Organization;
import models.Store;
import models.User;
import models.Warehouse;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class StoreRepository extends AbstractD3ERepository<Store> {
  public int getTypeIndex() {
    return SchemaConstants.Store;
  }

  public List<Store> getByManager(User manager) {
    String queryStr = "SELECT a._id from _store a where a._manager_id = :manager";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "manager", manager);
    return getAllXsByY(query);
  }

  public List<Store> getByDefaultWarehouse(Warehouse defaultWarehouse) {
    String queryStr =
        "SELECT a._id from _store a where a._default_warehouse_id = :defaultWarehouse";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "defaultWarehouse", defaultWarehouse);
    return getAllXsByY(query);
  }

  public List<Store> findByWarehouses(Warehouse warehouses) {
    String queryStr = "SELECT a._store_id from _warehouse a where ";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "warehouses", warehouses);
    return getAllXsByY(query);
  }

  public List<Store> getByOrganization(Organization organization) {
    String queryStr = "SELECT a._id from _store a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }
}
