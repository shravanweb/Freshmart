package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.Organization;
import models.SalesOrder;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class SalesOrderRepository extends AbstractD3ERepository<SalesOrder> {
  public int getTypeIndex() {
    return SchemaConstants.SalesOrder;
  }

  public List<SalesOrder> getByStore(models.Store store) {
    String queryStr = "SELECT a._id from _sales_order a where a._store_id = :store";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "store", store);
    return getAllXsByY(query);
  }

  public List<SalesOrder> getByWarehouse(models.Warehouse warehouse) {
    String queryStr = "SELECT a._id from _sales_order a where a._warehouse_id = :warehouse";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "warehouse", warehouse);
    return getAllXsByY(query);
  }

  public List<SalesOrder> getBySoldBy(models.User soldBy) {
    String queryStr = "SELECT a._id from _sales_order a where a._sold_by_id = :soldBy";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "soldBy", soldBy);
    return getAllXsByY(query);
  }

  public List<SalesOrder> findByLines(models.SalesOrderLine lines) {
    String queryStr = "SELECT a._sales_order_id from _sales_order_line a where ";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "lines", lines);
    return getAllXsByY(query);
  }

  public List<SalesOrder> getByOrganization(Organization organization) {
    String queryStr = "SELECT a._id from _sales_order a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }

  public List<SalesOrder> getByCustomerPhone(Organization organization, String phone) {
    String queryStr =
        "SELECT a._id from _sales_order a where a._organization_id = :organization"
            + " and a._customer_phone = :phone order by a._order_date desc";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    QueryImplUtil.setParameter(query, "phone", phone);
    return getAllXsByY(query);
  }

  public List<SalesOrder> getByCustomerDisplayName(Organization organization, String displayName) {
    String queryStr =
        "SELECT a._id from _sales_order a where a._organization_id = :organization"
            + " and (a._customer_name = :displayName"
            + " or a._customer_name like :displayNamePrefix)"
            + " order by a._order_date desc";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    QueryImplUtil.setParameter(query, "displayName", displayName);
    QueryImplUtil.setParameter(query, "displayNamePrefix", displayName + " |%");
    return getAllXsByY(query);
  }
}
