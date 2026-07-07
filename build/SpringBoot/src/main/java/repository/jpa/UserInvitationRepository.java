package repository.jpa;

import d3e.core.SchemaConstants;
import java.util.List;
import models.Organization;
import models.User;
import models.UserInvitation;
import org.springframework.stereotype.Service;
import store.Query;
import store.QueryImplUtil;

@Service
public class UserInvitationRepository extends AbstractD3ERepository<UserInvitation> {
  public int getTypeIndex() {
    return SchemaConstants.UserInvitation;
  }

  public List<UserInvitation> getByInvitedBy(User invitedBy) {
    String queryStr = "SELECT a._id from _user_invitation a where a._invited_by_id = :invitedBy";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "invitedBy", invitedBy);
    return getAllXsByY(query);
  }

  public boolean checkUserInvitationTokenUniqueInOrganization(
      Long masterId, Long id, String token) {
    String queryStr =
        "SELECT CASE WHEN count(*) > 0 THEN false ELSE true END as _a from _user_invitation a where a._token = :token and a._id != :id and a._organization_id = :masterId";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "token", token);
    QueryImplUtil.setParameter(query, "id", id);
    QueryImplUtil.setParameter(query, "masterId", masterId);
    return checkUnique(query);
  }

  public UserInvitation getByToken(String token) {
    String queryStr = "SELECT a._id from _user_invitation a where a._token = :token";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "token", token);
    return getXByY(query);
  }

  public List<UserInvitation> getByOrganization(Organization organization) {
    String queryStr =
        "SELECT a._id from _user_invitation a where a._organization_id = :organization";
    Query query = em().createNativeQuery(queryStr);
    QueryImplUtil.setParameter(query, "organization", organization);
    return getAllXsByY(query);
  }
}
