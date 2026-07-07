package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.Organization;
import models.StockTransfer;
import models.StockTransferLine;
import models.User;
import models.Warehouse;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class StockTransferRepository extends AbstractD3ERepository<StockTransfer> {
  public int getTypeIndex() {
    return SchemaConstants.StockTransfer;
  }

  public List<StockTransfer> getBySourceWarehouse(Warehouse sourceWarehouse) {
    String queryStr =
        "SELECT a._id from _stock_transfer a where a._source_warehouse_id = :sourceWarehouse";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "sourceWarehouse", sourceWarehouse);
    return getAllXsByY(query);
  }

  public List<StockTransfer> getByDestinationWarehouse(Warehouse destinationWarehouse) {
    String queryStr =
        "SELECT a._id from _stock_transfer a where a._destination_warehouse_id = :destinationWarehouse";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "destinationWarehouse", destinationWarehouse);
    return getAllXsByY(query);
  }

  public List<StockTransfer> getByRequestedBy(User requestedBy) {
    String queryStr = "SELECT a._id from _stock_transfer a where a._requested_by_id = :requestedBy";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "requestedBy", requestedBy);
    return getAllXsByY(query);
  }

  public List<StockTransfer> getByApprovedBy(User approvedBy) {
    String queryStr = "SELECT a._id from _stock_transfer a where a._approved_by_id = :approvedBy";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "approvedBy", approvedBy);
    return getAllXsByY(query);
  }

  public List<StockTransfer> findByLines(StockTransferLine lines) {
    String queryStr = "SELECT a._stock_transfer_id from _stock_transfer_line a where ";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "lines", lines);
    return getAllXsByY(query);
  }

  public List<StockTransfer> getByOrganization(Organization organization) {
    String queryStr =
        "SELECT a._id from _stock_transfer a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }
}
