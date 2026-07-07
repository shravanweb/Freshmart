package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.Organization;
import models.SupplierContact;
import models.Vendor;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class VendorRepository extends AbstractD3ERepository<Vendor> {
  public int getTypeIndex() {
    return SchemaConstants.Vendor;
  }

  public List<Vendor> findByContacts(SupplierContact contacts) {
    String queryStr = "SELECT a._vendor_id from _supplier_contact a where ";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "contacts", contacts);
    return getAllXsByY(query);
  }

  public List<Vendor> getByOrganization(Organization organization) {
    String queryStr = "SELECT a._id from _vendor a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }
}
