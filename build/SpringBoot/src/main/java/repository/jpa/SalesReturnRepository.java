package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.Organization;
import models.SalesOrder;
import models.SalesReturn;
import models.SalesReturnLine;
import models.Store;
import models.User;
import models.Warehouse;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class SalesReturnRepository extends AbstractD3ERepository<SalesReturn> {
  public int getTypeIndex() {
    return SchemaConstants.SalesReturn;
  }

  public List<SalesReturn> getBySalesOrder(SalesOrder salesOrder) {
    String queryStr = "SELECT a._id from _sales_return a where a._sales_order_id = :salesOrder";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "salesOrder", salesOrder);
    return getAllXsByY(query);
  }

  public List<SalesReturn> getByStore(Store store) {
    String queryStr = "SELECT a._id from _sales_return a where a._store_id = :store";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "store", store);
    return getAllXsByY(query);
  }

  public List<SalesReturn> getByWarehouse(Warehouse warehouse) {
    String queryStr = "SELECT a._id from _sales_return a where a._warehouse_id = :warehouse";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "warehouse", warehouse);
    return getAllXsByY(query);
  }

  public List<SalesReturn> getByProcessedBy(User processedBy) {
    String queryStr = "SELECT a._id from _sales_return a where a._processed_by_id = :processedBy";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "processedBy", processedBy);
    return getAllXsByY(query);
  }

  public List<SalesReturn> findByLines(SalesReturnLine lines) {
    String queryStr = "SELECT a._sales_return_id from _sales_return_line a where ";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "lines", lines);
    return getAllXsByY(query);
  }

  public List<SalesReturn> getByOrganization(Organization organization) {
    String queryStr = "SELECT a._id from _sales_return a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }
}
