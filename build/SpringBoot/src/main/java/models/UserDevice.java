package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

@SolrDocument(collection = "UserDevice")
@Entity
public class UserDevice extends CreatableObject {
  public static final int _USER = 0;
  public static final int _DEVICETOKEN = 1;

  @ManyToOne(fetch = FetchType.LAZY)
  private BaseUser user;

  private String deviceToken;

  public UserDevice() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.UserDevice;
  }

  @Override
  public String _type() {
    return "UserDevice";
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
    if (this.user != null) {
      this.user.setDevices(null);
    }
    this.user = user;
    if (this.user != null) {
      this.user.setDevices(this);
    }
  }

  public String getDeviceToken() {
    _checkProxy();
    return this.deviceToken;
  }

  public void setDeviceToken(String deviceToken) {
    _checkProxy();
    if (Objects.equals(this.deviceToken, deviceToken)) {
      return;
    }
    fieldChanged(_DEVICETOKEN, this.deviceToken, deviceToken);
    this.deviceToken = deviceToken;
  }

  public String displayName() {
    return "";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof UserDevice && super.equals(a);
  }

  public UserDevice deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    UserDevice _obj = ((UserDevice) dbObj);
    _obj.setUser(user);
    _obj.setDeviceToken(deviceToken);
  }

  public UserDevice cloneInstance(UserDevice cloneObj) {
    if (cloneObj == null) {
      cloneObj = new UserDevice();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setUser(this.getUser());
    cloneObj.setDeviceToken(this.getDeviceToken());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public UserDevice createNewInstance() {
    return new UserDevice();
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
