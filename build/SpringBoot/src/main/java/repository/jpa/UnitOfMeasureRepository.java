package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.Organization;
import models.UnitOfMeasure;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class UnitOfMeasureRepository extends AbstractD3ERepository<UnitOfMeasure> {
  public int getTypeIndex() {
    return SchemaConstants.UnitOfMeasure;
  }

  public List<UnitOfMeasure> getByOrganization(Organization organization) {
    String queryStr =
        "SELECT a._id from _unit_of_measure a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }
}
