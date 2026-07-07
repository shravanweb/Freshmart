package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.Product;
import models.SalesReturn;
import models.SalesReturnLine;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class SalesReturnLineRepository extends AbstractD3ERepository<SalesReturnLine> {
  public int getTypeIndex() {
    return SchemaConstants.SalesReturnLine;
  }

  public List<SalesReturnLine> getByProduct(Product product) {
    String queryStr = "SELECT a._id from _sales_return_line a where a._product_id = :product";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "product", product);
    return getAllXsByY(query);
  }

  public List<SalesReturnLine> getBySalesReturn(SalesReturn salesReturn) {
    String queryStr =
        "SELECT a._id from _sales_return_line a where a._sales_return_id = :salesReturn";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "salesReturn", salesReturn);
    return getAllXsByY(query);
  }
}
