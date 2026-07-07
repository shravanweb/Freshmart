package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class UserDevicesRequest extends CreatableObject {
  public static final int _USER = 0;
  public static final int _TOKEN = 1;
  private BaseUser user;
  private String token;

  public UserDevicesRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.UserDevicesRequest;
  }

  @Override
  public String _type() {
    return "UserDevicesRequest";
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

  public BaseUser getUser() {
    _checkProxy();
    return this.user;
  }

  public void setUser(BaseUser user) {
    _checkProxy();
    if (Objects.equals(this.user, user)) {
      return;
    }
    fieldChanged(_USER, this.user, user);
    this.user = user;
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

  public String displayName() {
    return "UserDevicesRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof UserDevicesRequest && super.equals(a);
  }

  public UserDevicesRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    UserDevicesRequest _obj = ((UserDevicesRequest) dbObj);
    _obj.setUser(user);
    _obj.setToken(token);
  }

  public UserDevicesRequest cloneInstance(UserDevicesRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new UserDevicesRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setUser(this.getUser());
    cloneObj.setToken(this.getToken());
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public UserDevicesRequest createNewInstance() {
    return new UserDevicesRequest();
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
