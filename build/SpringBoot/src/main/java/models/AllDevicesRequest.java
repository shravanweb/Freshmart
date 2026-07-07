package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.D3EPersistanceList;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class AllDevicesRequest extends CreatableObject {
  public static final int _USERS = 0;
  private List<BaseUser> users = D3EPersistanceList.reference(this, _USERS);

  public AllDevicesRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.AllDevicesRequest;
  }

  @Override
  public String _type() {
    return "AllDevicesRequest";
  }

  @Override
  public int _fieldsCount() {
    return 1;
  }

  public void addToUsers(BaseUser val, long index) {
    if (index == -1) {
      this.users.add(val);
    } else {
      this.users.add(((int) index), val);
    }
  }

  public void removeFromUsers(BaseUser val) {
    this.users.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public List<BaseUser> getUsers() {
    return this.users;
  }

  public void setUsers(List<BaseUser> users) {
    if (Objects.equals(this.users, users)) {
      return;
    }
    ((D3EPersistanceList<BaseUser>) this.users).setAll(users);
  }

  public String displayName() {
    return "AllDevicesRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof AllDevicesRequest && super.equals(a);
  }

  public AllDevicesRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    AllDevicesRequest _obj = ((AllDevicesRequest) dbObj);
    _obj.setUsers(users);
  }

  public AllDevicesRequest cloneInstance(AllDevicesRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new AllDevicesRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setUsers(new ArrayList<>(this.getUsers()));
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public AllDevicesRequest createNewInstance() {
    return new AllDevicesRequest();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.addAll(this.users);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
