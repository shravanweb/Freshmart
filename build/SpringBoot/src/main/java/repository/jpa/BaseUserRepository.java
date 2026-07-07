package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.BaseUser;
import models.UserDevice;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class BaseUserRepository extends AbstractD3ERepository<BaseUser> {
  public int getTypeIndex() {
    return SchemaConstants.BaseUser;
  }

  public List<BaseUser> getByDevices(UserDevice devices) {
    String queryStr = "SELECT a._id from _base_user a where a._devices_id = :devices";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "devices", devices);
    return getAllXsByY(query);
  }
}
