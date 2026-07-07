package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.Product;
import models.SalesOrder;
import models.SalesOrderLine;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class SalesOrderLineRepository extends AbstractD3ERepository<SalesOrderLine> {
  public int getTypeIndex() {
    return SchemaConstants.SalesOrderLine;
  }

  public List<SalesOrderLine> getByProduct(Product product) {
    String queryStr = "SELECT a._id from _sales_order_line a where a._product_id = :product";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "product", product);
    return getAllXsByY(query);
  }

  public List<SalesOrderLine> getBySalesOrder(SalesOrder salesOrder) {
    String queryStr = "SELECT a._id from _sales_order_line a where a._sales_order_id = :salesOrder";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "salesOrder", salesOrder);
    return getAllXsByY(query);
  }
}
