package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.Product;
import models.StockTransfer;
import models.StockTransferLine;
import models.UnitOfMeasure;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class StockTransferLineRepository extends AbstractD3ERepository<StockTransferLine> {
  public int getTypeIndex() {
    return SchemaConstants.StockTransferLine;
  }

  public List<StockTransferLine> getByProduct(Product product) {
    String queryStr = "SELECT a._id from _stock_transfer_line a where a._product_id = :product";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "product", product);
    return getAllXsByY(query);
  }

  public List<StockTransferLine> getByUom(UnitOfMeasure uom) {
    String queryStr = "SELECT a._id from _stock_transfer_line a where a._uom_id = :uom";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "uom", uom);
    return getAllXsByY(query);
  }

  public List<StockTransferLine> getByStockTransfer(StockTransfer stockTransfer) {
    String queryStr =
        "SELECT a._id from _stock_transfer_line a where a._stock_transfer_id = :stockTransfer";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "stockTransfer", stockTransfer);
    return getAllXsByY(query);
  }
}
