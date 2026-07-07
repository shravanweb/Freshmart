package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.InventoryAdjustment;
import models.InventoryAdjustmentLine;
import models.Product;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class InventoryAdjustmentLineRepository
    extends AbstractD3ERepository<InventoryAdjustmentLine> {
  public int getTypeIndex() {
    return SchemaConstants.InventoryAdjustmentLine;
  }

  public List<InventoryAdjustmentLine> getByProduct(Product product) {
    String queryStr =
        "SELECT a._id from _inventory_adjustment_line a where a._product_id = :product";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "product", product);
    return getAllXsByY(query);
  }

  public List<InventoryAdjustmentLine> getByInventoryAdjustment(
      InventoryAdjustment inventoryAdjustment) {
    String queryStr =
        "SELECT a._id from _inventory_adjustment_line a where a._inventory_adjustment_id = :inventoryAdjustment";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "inventoryAdjustment", inventoryAdjustment);
    return getAllXsByY(query);
  }
}
