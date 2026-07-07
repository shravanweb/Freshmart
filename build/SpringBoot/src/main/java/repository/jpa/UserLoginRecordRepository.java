package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.BaseUser;
import models.UserLoginRecord;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class UserLoginRecordRepository extends AbstractD3ERepository<UserLoginRecord> {
  public int getTypeIndex() {
    return SchemaConstants.UserLoginRecord;
  }

  public List<UserLoginRecord> getByUser(BaseUser user) {
    String queryStr = "SELECT a._id from _user_login_record a where a._user_id = :user";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "user", user);
    return getAllXsByY(query);
  }
}
