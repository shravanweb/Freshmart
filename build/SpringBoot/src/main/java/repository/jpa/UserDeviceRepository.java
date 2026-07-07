package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.BaseUser;
import models.UserDevice;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class UserDeviceRepository extends AbstractD3ERepository<UserDevice> {
  public int getTypeIndex() {
    return SchemaConstants.UserDevice;
  }

  public List<UserDevice> getByUser(BaseUser user) {
    String queryStr = "SELECT a._id from _user_device a where a._user_id = :user";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "user", user);
    return getAllXsByY(query);
  }
}
