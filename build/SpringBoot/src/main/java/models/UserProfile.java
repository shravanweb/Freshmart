package models;

import classes.AppUserRole;
import classes.EntityStatus;
import d3e.core.CloneContext;
import d3e.core.DFile;
import d3e.core.SchemaConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderColumn;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.D3EPersistanceList;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

@SolrDocument(collection = "UserProfile")
@Entity
public class UserProfile extends CreatableObject {
  public static final int _USER = 0;
  public static final int _DISPLAYNAME = 1;
  public static final int _PHONE = 2;
  public static final int _AVATAR = 3;
  public static final int _APPROLE = 4;
  public static final int _ASSIGNEDSTORES = 5;
  public static final int _ASSIGNEDWAREHOUSES = 6;
  public static final int _STATUS = 7;
  public static final int _LASTLOGINAT = 8;
  public static final int _ORGANIZATION = 9;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @NotNull private String displayName;
  private String phone;

  @ManyToOne(fetch = FetchType.LAZY)
  private DFile avatar;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private AppUserRole appRole = AppUserRole.OrganizationAdmin;

  @OrderColumn @ManyToMany
  private List<Store> assignedStores = D3EPersistanceList.reference(this, _ASSIGNEDSTORES);

  @OrderColumn @ManyToMany
  private List<Warehouse> assignedWarehouses =
      D3EPersistanceList.reference(this, _ASSIGNEDWAREHOUSES);

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private EntityStatus status = EntityStatus.Active;

  private LocalDateTime lastLoginAt;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  private transient UserProfile old;

  public UserProfile() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.UserProfile;
  }

  @Override
  public String _type() {
    return "UserProfile";
  }

  @Override
  public int _fieldsCount() {
    return 10;
  }

  public void addToAssignedStores(Store val, long index) {
    if (index == -1) {
      this.assignedStores.add(val);
    } else {
      this.assignedStores.add(((int) index), val);
    }
  }

  public void removeFromAssignedStores(Store val) {
    this.assignedStores.remove(val);
  }

  public void addToAssignedWarehouses(Warehouse val, long index) {
    if (index == -1) {
      this.assignedWarehouses.add(val);
    } else {
      this.assignedWarehouses.add(((int) index), val);
    }
  }

  public void removeFromAssignedWarehouses(Warehouse val) {
    this.assignedWarehouses.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public User getUser() {
    _checkProxy();
    return this.user;
  }

  public void setUser(User user) {
    _checkProxy();
    if (Objects.equals(this.user, user)) {
      return;
    }
    fieldChanged(_USER, this.user, user);
    this.user = user;
  }

  public String getDisplayName() {
    _checkProxy();
    return this.displayName;
  }

  public void setDisplayName(String displayName) {
    _checkProxy();
    if (Objects.equals(this.displayName, displayName)) {
      return;
    }
    fieldChanged(_DISPLAYNAME, this.displayName, displayName);
    this.displayName = displayName;
  }

  public String getPhone() {
    _checkProxy();
    return this.phone;
  }

  public void setPhone(String phone) {
    _checkProxy();
    if (Objects.equals(this.phone, phone)) {
      return;
    }
    fieldChanged(_PHONE, this.phone, phone);
    this.phone = phone;
  }

  public DFile getAvatar() {
    _checkProxy();
    return this.avatar;
  }

  public void setAvatar(DFile avatar) {
    _checkProxy();
    if (Objects.equals(this.avatar, avatar)) {
      return;
    }
    fieldChanged(_AVATAR, this.avatar, avatar);
    this.avatar = avatar;
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

  public List<Store> getAssignedStores() {
    return this.assignedStores;
  }

  public void setAssignedStores(List<Store> assignedStores) {
    if (Objects.equals(this.assignedStores, assignedStores)) {
      return;
    }
    ((D3EPersistanceList<Store>) this.assignedStores).setAll(assignedStores);
  }

  public List<Warehouse> getAssignedWarehouses() {
    return this.assignedWarehouses;
  }

  public void setAssignedWarehouses(List<Warehouse> assignedWarehouses) {
    if (Objects.equals(this.assignedWarehouses, assignedWarehouses)) {
      return;
    }
    ((D3EPersistanceList<Warehouse>) this.assignedWarehouses).setAll(assignedWarehouses);
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

  public LocalDateTime getLastLoginAt() {
    _checkProxy();
    return this.lastLoginAt;
  }

  public void setLastLoginAt(LocalDateTime lastLoginAt) {
    _checkProxy();
    if (Objects.equals(this.lastLoginAt, lastLoginAt)) {
      return;
    }
    fieldChanged(_LASTLOGINAT, this.lastLoginAt, lastLoginAt);
    this.lastLoginAt = lastLoginAt;
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

  public UserProfile getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((UserProfile) old);
  }

  public String displayName() {
    return this.getDisplayName();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof UserProfile && super.equals(a);
  }

  public UserProfile deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    UserProfile _obj = ((UserProfile) dbObj);
    _obj.setUser(user);
    _obj.setDisplayName(displayName);
    _obj.setPhone(phone);
    _obj.setAvatar(avatar);
    _obj.setAppRole(appRole);
    _obj.setAssignedStores(assignedStores);
    _obj.setAssignedWarehouses(assignedWarehouses);
    _obj.setStatus(status);
    _obj.setLastLoginAt(lastLoginAt);
    _obj.setOrganization(organization);
  }

  public UserProfile cloneInstance(UserProfile cloneObj) {
    if (cloneObj == null) {
      cloneObj = new UserProfile();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setUser(this.getUser());
    cloneObj.setDisplayName(this.getDisplayName());
    cloneObj.setPhone(this.getPhone());
    cloneObj.setAvatar(this.getAvatar());
    cloneObj.setAppRole(this.getAppRole());
    cloneObj.setAssignedStores(new ArrayList<>(this.getAssignedStores()));
    cloneObj.setAssignedWarehouses(new ArrayList<>(this.getAssignedWarehouses()));
    cloneObj.setStatus(this.getStatus());
    cloneObj.setLastLoginAt(this.getLastLoginAt());
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public UserProfile createNewInstance() {
    return new UserProfile();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.user);
    _refs.add(this.avatar);
    _refs.addAll(this.assignedStores);
    _refs.addAll(this.assignedWarehouses);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
