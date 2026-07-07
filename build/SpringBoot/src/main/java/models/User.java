package models;

import classes.AppUserRole;
import classes.EntityStatus;
import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

@SolrDocument(collection = "User")
@Entity
public class User extends BaseUser {
  public static final int _EMAIL = 3;
  public static final int _PASSWORD = 4;
  public static final int _ROLE = 5;
  public static final int _STATUS = 6;
  public static final int _ORGANIZATION = 7;
  @NotNull private String email;
  @NotNull private String password;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private AppUserRole role = AppUserRole.Viewer;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private EntityStatus status = EntityStatus.Active;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  public User() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.User;
  }

  @Override
  public String _type() {
    return "User";
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

  public String getPassword() {
    _checkProxy();
    return this.password;
  }

  public void setPassword(String password) {
    _checkProxy();
    if (Objects.equals(this.password, password)) {
      return;
    }
    fieldChanged(_PASSWORD, this.password, password);
    this.password = password;
  }

  public AppUserRole getRole() {
    _checkProxy();
    return this.role;
  }

  public void setRole(AppUserRole role) {
    _checkProxy();
    if (Objects.equals(this.role, role)) {
      return;
    }
    fieldChanged(_ROLE, this.role, role);
    this.role = role;
  }

  public EntityStatus getStatus() {
    _checkProxy();
    return this.status;
  }

  public void setStatus(EntityStatus status) {
    _checkProxy();
    if (Objects.equals(this.status, status)) {
      return;
    }
    fieldChanged(_STATUS, this.status, status);
    this.status = status;
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

  public String displayName() {
    return this.getEmail();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof User && super.equals(a);
  }

  public User deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    User _obj = ((User) dbObj);
    _obj.setEmail(email);
    _obj.setPassword(password);
    _obj.setRole(role);
    _obj.setStatus(status);
    _obj.setOrganization(organization);
  }

  public User cloneInstance(User cloneObj) {
    if (cloneObj == null) {
      cloneObj = new User();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setEmail(this.getEmail());
    cloneObj.setPassword(this.getPassword());
    cloneObj.setRole(this.getRole());
    cloneObj.setStatus(this.getStatus());
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public User createNewInstance() {
    return new User();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }

  @Override
  protected void _handleChildChange(int _childIdx, boolean set) {
    super._handleChildChange(_childIdx, set);
  }
}
