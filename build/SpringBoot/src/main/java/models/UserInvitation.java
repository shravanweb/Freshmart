package models;

import classes.AppUserRole;
import classes.InvitationStatus;
import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

@SolrDocument(collection = "UserInvitation")
@Entity
public class UserInvitation extends CreatableObject {
  public static final int _EMAIL = 0;
  public static final int _APPROLE = 1;
  public static final int _INVITEDBY = 2;
  public static final int _TOKEN = 3;
  public static final int _STATUS = 4;
  public static final int _EXPIRESAT = 5;
  public static final int _ACCEPTEDAT = 6;
  public static final int _ORGANIZATION = 7;
  @NotNull private String email;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private AppUserRole appRole = AppUserRole.OrganizationAdmin;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private User invitedBy;

  @NotNull private String token;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private InvitationStatus status = InvitationStatus.Pending;

  @NotNull private LocalDateTime expiresAt;
  private LocalDateTime acceptedAt;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  private transient UserInvitation old;

  public UserInvitation() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.UserInvitation;
  }

  @Override
  public String _type() {
    return "UserInvitation";
  }

  @Override
  public int _fieldsCount() {
    return 8;
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public String getEmail() {
    _checkProxy();
    return this.email;
  }

  public void setEmail(String email) {
    _checkProxy();
    if (Objects.equals(this.email, email)) {
      return;
    }
    fieldChanged(_EMAIL, this.email, email);
    this.email = email;
  }

  public AppUserRole getAppRole() {
    _checkProxy();
    return this.appRole;
  }

  public void setAppRole(AppUserRole appRole) {
    _checkProxy();
    if (Objects.equals(this.appRole, appRole)) {
      return;
    }
    fieldChanged(_APPROLE, this.appRole, appRole);
    this.appRole = appRole;
  }

  public User getInvitedBy() {
    _checkProxy();
    return this.invitedBy;
  }

  public void setInvitedBy(User invitedBy) {
    _checkProxy();
    if (Objects.equals(this.invitedBy, invitedBy)) {
      return;
    }
    fieldChanged(_INVITEDBY, this.invitedBy, invitedBy);
    this.invitedBy = invitedBy;
  }

  public String getToken() {
    _checkProxy();
    return this.token;
  }

  public void setToken(String token) {
    _checkProxy();
    if (Objects.equals(this.token, token)) {
      return;
    }
    fieldChanged(_TOKEN, this.token, token);
    this.token = token;
  }

  public InvitationStatus getStatus() {
    _checkProxy();
    return this.status;
  }

  public void setStatus(InvitationStatus status) {
    _checkProxy();
    if (Objects.equals(this.status, status)) {
      return;
    }
    fieldChanged(_STATUS, this.status, status);
    this.status = status;
  }

  public LocalDateTime getExpiresAt() {
    _checkProxy();
    return this.expiresAt;
  }

  public void setExpiresAt(LocalDateTime expiresAt) {
    _checkProxy();
    if (Objects.equals(this.expiresAt, expiresAt)) {
      return;
    }
    fieldChanged(_EXPIRESAT, this.expiresAt, expiresAt);
    this.expiresAt = expiresAt;
  }

  public LocalDateTime getAcceptedAt() {
    _checkProxy();
    return this.acceptedAt;
  }

  public void setAcceptedAt(LocalDateTime acceptedAt) {
    _checkProxy();
    if (Objects.equals(this.acceptedAt, acceptedAt)) {
      return;
    }
    fieldChanged(_ACCEPTEDAT, this.acceptedAt, acceptedAt);
    this.acceptedAt = acceptedAt;
  }

  public Organization getOrganization() {
    _checkProxy();
    return this.organization;
  }

  public void setOrganization(Organization organization) {
    _checkProxy();
    if (Objects.equals(this.organization, organization)) {
      return;
    }
    fieldChanged(_ORGANIZATION, this.organization, organization);
    this.organization = organization;
  }

  public UserInvitation getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((UserInvitation) old);
  }

  public String displayName() {
    return this.getEmail();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof UserInvitation && super.equals(a);
  }

  public UserInvitation deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    UserInvitation _obj = ((UserInvitation) dbObj);
    _obj.setEmail(email);
    _obj.setAppRole(appRole);
    _obj.setInvitedBy(invitedBy);
    _obj.setToken(token);
    _obj.setStatus(status);
    _obj.setExpiresAt(expiresAt);
    _obj.setAcceptedAt(acceptedAt);
    _obj.setOrganization(organization);
  }

  public UserInvitation cloneInstance(UserInvitation cloneObj) {
    if (cloneObj == null) {
      cloneObj = new UserInvitation();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setEmail(this.getEmail());
    cloneObj.setAppRole(this.getAppRole());
    cloneObj.setInvitedBy(this.getInvitedBy());
    cloneObj.setToken(this.getToken());
    cloneObj.setStatus(this.getStatus());
    cloneObj.setExpiresAt(this.getExpiresAt());
    cloneObj.setAcceptedAt(this.getAcceptedAt());
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public UserInvitation createNewInstance() {
    return new UserInvitation();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.invitedBy);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
