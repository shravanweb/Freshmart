package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.SupplierContact;
import models.Vendor;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class SupplierContactRepository extends AbstractD3ERepository<SupplierContact> {
  public int getTypeIndex() {
    return SchemaConstants.SupplierContact;
  }

  public List<SupplierContact> getByVendor(Vendor vendor) {
    String queryStr = "SELECT a._id from _supplier_contact a where a._vendor_id = :vendor";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "vendor", vendor);
    return getAllXsByY(query);
  }
}
