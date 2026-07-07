package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class UnreadNotificationCountRequest extends CreatableObject {
  public static final int _ORGANIZATION = 0;
  public static final int _USER = 1;
  private Organization organization;
  private User user;

  public UnreadNotificationCountRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.UnreadNotificationCountRequest;
  }

  @Override
  public String _type() {
    return "UnreadNotificationCountRequest";
  }

  @Override
  public int _fieldsCount() {
    return 2;
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
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

  public String displayName() {
    return "UnreadNotificationCountRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof UnreadNotificationCountRequest && super.equals(a);
  }

  public UnreadNotificationCountRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    UnreadNotificationCountRequest _obj = ((UnreadNotificationCountRequest) dbObj);
    _obj.setOrganization(organization);
    _obj.setUser(user);
  }

  public UnreadNotificationCountRequest cloneInstance(UnreadNotificationCountRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new UnreadNotificationCountRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setOrganization(this.getOrganization());
    cloneObj.setUser(this.getUser());
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public UnreadNotificationCountRequest createNewInstance() {
    return new UnreadNotificationCountRequest();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.organization);
    _refs.add(this.user);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
