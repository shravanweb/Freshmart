package models;

import classes.AppUserRole;
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

@SolrDocument(collection = "UserRole")
@Entity
public class UserRole extends CreatableObject {
  public static final int _NAME = 0;
  public static final int _ROLECODE = 1;
  public static final int _DESCRIPTION = 2;
  public static final int _ISSYSTEM = 3;
  public static final int _PERMISSIONS = 4;
  public static final int _ORGANIZATION = 5;
  @NotNull private String name;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private AppUserRole roleCode = AppUserRole.OrganizationAdmin;

  private String description;
  private boolean isSystem = false;
  private String permissions;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  public UserRole() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.UserRole;
  }

  @Override
  public String _type() {
    return "UserRole";
  }

  @Override
  public int _fieldsCount() {
    return 6;
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public String getName() {
    _checkProxy();
    return this.name;
  }

  public void setName(String name) {
    _checkProxy();
    if (Objects.equals(this.name, name)) {
      return;
    }
    fieldChanged(_NAME, this.name, name);
    this.name = name;
  }

  public AppUserRole getRoleCode() {
    _checkProxy();
    return this.roleCode;
  }

  public void setRoleCode(AppUserRole roleCode) {
    _checkProxy();
    if (Objects.equals(this.roleCode, roleCode)) {
      return;
    }
    fieldChanged(_ROLECODE, this.roleCode, roleCode);
    this.roleCode = roleCode;
  }

  public String getDescription() {
    _checkProxy();
    return this.description;
  }

  public void setDescription(String description) {
    _checkProxy();
    if (Objects.equals(this.description, description)) {
      return;
    }
    fieldChanged(_DESCRIPTION, this.description, description);
    this.description = description;
  }

  public boolean isIsSystem() {
    _checkProxy();
    return this.isSystem;
  }

  public void setIsSystem(boolean isSystem) {
    _checkProxy();
    if (Objects.equals(this.isSystem, isSystem)) {
      return;
    }
    fieldChanged(_ISSYSTEM, this.isSystem, isSystem);
    this.isSystem = isSystem;
  }

  public String getPermissions() {
    _checkProxy();
    return this.permissions;
  }

  public void setPermissions(String permissions) {
    _checkProxy();
    if (Objects.equals(this.permissions, permissions)) {
      return;
    }
    fieldChanged(_PERMISSIONS, this.permissions, permissions);
    this.permissions = permissions;
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
    return this.getName();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof UserRole && super.equals(a);
  }

  public UserRole deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    UserRole _obj = ((UserRole) dbObj);
    _obj.setName(name);
    _obj.setRoleCode(roleCode);
    _obj.setDescription(description);
    _obj.setIsSystem(isSystem);
    _obj.setPermissions(permissions);
    _obj.setOrganization(organization);
  }

  public UserRole cloneInstance(UserRole cloneObj) {
    if (cloneObj == null) {
      cloneObj = new UserRole();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setName(this.getName());
    cloneObj.setRoleCode(this.getRoleCode());
    cloneObj.setDescription(this.getDescription());
    cloneObj.setIsSystem(this.isIsSystem());
    cloneObj.setPermissions(this.getPermissions());
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public UserRole createNewInstance() {
    return new UserRole();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
