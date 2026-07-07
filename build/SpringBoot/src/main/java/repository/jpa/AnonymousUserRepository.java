package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.AnonymousUser;
import models.UserDevice;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class AnonymousUserRepository extends AbstractD3ERepository<AnonymousUser> {
  public int getTypeIndex() {
    return SchemaConstants.AnonymousUser;
  }

  public List<AnonymousUser> getByDevices(UserDevice devices) {
    String queryStr =
        "SELECT a._id from _anonymous_user a left join _base_user b on b._id = a._id where b._devices_id = :devices";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "devices", devices);
    return getAllXsByY(query);
  }
}
