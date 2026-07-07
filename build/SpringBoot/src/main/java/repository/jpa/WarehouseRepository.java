package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.Organization;
import models.Store;
import models.Warehouse;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class WarehouseRepository extends AbstractD3ERepository<Warehouse> {
  public int getTypeIndex() {
    return SchemaConstants.Warehouse;
  }

  public List<Warehouse> getByStore(Store store) {
    String queryStr = "SELECT a._id from _warehouse a where a._store_id = :store";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "store", store);
    return getAllXsByY(query);
  }

  public List<Warehouse> getByOrganization(Organization organization) {
    String queryStr = "SELECT a._id from _warehouse a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }
}
