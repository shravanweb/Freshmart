package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OrderColumn;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.D3EPersistanceList;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

@SolrDocument(collection = "PushNotification")
@Entity
public class PushNotification extends CreatableObject {
  public static final int _USERS = 0;
  public static final int _SKIPTHISDEVICE = 1;
  public static final int _DEVICETOKEN = 2;
  public static final int _TITLE = 3;
  public static final int _BODY = 4;
  public static final int _PATH = 5;
  public static final int _DATA = 6;
  public static final int _FAILED = 7;
  public static final int _FAILEDDEVICES = 8;

  @OrderColumn @ManyToMany
  private List<BaseUser> users = D3EPersistanceList.reference(this, _USERS);

  private boolean skipThisDevice = false;
  private String deviceToken;
  private String title;
  private String body;
  private String path;
  @ElementCollection private List<String> data = D3EPersistanceList.primitive(this, _DATA);
  private boolean failed = false;

  @OrderColumn @ManyToMany
  private List<UserDevice> failedDevices = D3EPersistanceList.reference(this, _FAILEDDEVICES);

  public PushNotification() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.PushNotification;
  }

  @Override
  public String _type() {
    return "PushNotification";
  }

  @Override
  public int _fieldsCount() {
    return 9;
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

  public void addToData(String val, long index) {
    if (index == -1) {
      this.data.add(val);
    } else {
      this.data.add(((int) index), val);
    }
  }

  public void removeFromData(String val) {
    this.data.remove(val);
  }

  public void addToFailedDevices(UserDevice val, long index) {
    if (index == -1) {
      this.failedDevices.add(val);
    } else {
      this.failedDevices.add(((int) index), val);
    }
  }

  public void removeFromFailedDevices(UserDevice val) {
    this.failedDevices.remove(val);
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

  public boolean isSkipThisDevice() {
    _checkProxy();
    return this.skipThisDevice;
  }

  public void setSkipThisDevice(boolean skipThisDevice) {
    _checkProxy();
    if (Objects.equals(this.skipThisDevice, skipThisDevice)) {
      return;
    }
    fieldChanged(_SKIPTHISDEVICE, this.skipThisDevice, skipThisDevice);
    this.skipThisDevice = skipThisDevice;
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

  public String getTitle() {
    _checkProxy();
    return this.title;
  }

  public void setTitle(String title) {
    _checkProxy();
    if (Objects.equals(this.title, title)) {
      return;
    }
    fieldChanged(_TITLE, this.title, title);
    this.title = title;
  }

  public String getBody() {
    _checkProxy();
    return this.body;
  }

  public void setBody(String body) {
    _checkProxy();
    if (Objects.equals(this.body, body)) {
      return;
    }
    fieldChanged(_BODY, this.body, body);
    this.body = body;
  }

  public String getPath() {
    _checkProxy();
    return this.path;
  }

  public void setPath(String path) {
    _checkProxy();
    if (Objects.equals(this.path, path)) {
      return;
    }
    fieldChanged(_PATH, this.path, path);
    this.path = path;
  }

  public List<String> getData() {
    return this.data;
  }

  public void setData(List<String> data) {
    if (Objects.equals(this.data, data)) {
      return;
    }
    ((D3EPersistanceList<String>) this.data).setAll(data);
  }

  public boolean isFailed() {
    _checkProxy();
    return this.failed;
  }

  public void setFailed(boolean failed) {
    _checkProxy();
    if (Objects.equals(this.failed, failed)) {
      return;
    }
    fieldChanged(_FAILED, this.failed, failed);
    this.failed = failed;
  }

  public List<UserDevice> getFailedDevices() {
    return this.failedDevices;
  }

  public void setFailedDevices(List<UserDevice> failedDevices) {
    if (Objects.equals(this.failedDevices, failedDevices)) {
      return;
    }
    ((D3EPersistanceList<UserDevice>) this.failedDevices).setAll(failedDevices);
  }

  public String displayName() {
    return "PushNotification";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof PushNotification && super.equals(a);
  }

  public PushNotification deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    PushNotification _obj = ((PushNotification) dbObj);
    _obj.setUsers(users);
    _obj.setSkipThisDevice(skipThisDevice);
    _obj.setDeviceToken(deviceToken);
    _obj.setTitle(title);
    _obj.setBody(body);
    _obj.setPath(path);
    _obj.setData(data);
    _obj.setFailed(failed);
    _obj.setFailedDevices(failedDevices);
  }

  public PushNotification cloneInstance(PushNotification cloneObj) {
    if (cloneObj == null) {
      cloneObj = new PushNotification();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setUsers(new ArrayList<>(this.getUsers()));
    cloneObj.setSkipThisDevice(this.isSkipThisDevice());
    cloneObj.setDeviceToken(this.getDeviceToken());
    cloneObj.setTitle(this.getTitle());
    cloneObj.setBody(this.getBody());
    cloneObj.setPath(this.getPath());
    cloneObj.setData(new ArrayList<>(this.getData()));
    cloneObj.setFailed(this.isFailed());
    cloneObj.setFailedDevices(new ArrayList<>(this.getFailedDevices()));
    return cloneObj;
  }

  public PushNotification createNewInstance() {
    return new PushNotification();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.addAll(this.users);
    _refs.addAll(this.failedDevices);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
