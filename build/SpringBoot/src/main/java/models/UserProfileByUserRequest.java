package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class UserProfileByUserRequest extends CreatableObject {
  public static final int _USER = 0;
  private User user;

  public UserProfileByUserRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.UserProfileByUserRequest;
  }

  @Override
  public String _type() {
    return "UserProfileByUserRequest";
  }

  @Override
  public int _fieldsCount() {
    return 1;
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

  public String displayName() {
    return "UserProfileByUserRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof UserProfileByUserRequest && super.equals(a);
  }

  public UserProfileByUserRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    UserProfileByUserRequest _obj = ((UserProfileByUserRequest) dbObj);
    _obj.setUser(user);
  }

  public UserProfileByUserRequest cloneInstance(UserProfileByUserRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new UserProfileByUserRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setUser(this.getUser());
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public UserProfileByUserRequest createNewInstance() {
    return new UserProfileByUserRequest();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.user);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
