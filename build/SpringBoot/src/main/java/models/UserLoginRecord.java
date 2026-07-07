package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

@SolrDocument(collection = "UserLoginRecord")
@Entity
public class UserLoginRecord extends CreatableObject {
  public static final int _USER = 0;
  public static final int _TIMESTAMP = 1;
  public static final int _IPADDRESS = 2;
  public static final int _BROWSER = 3;
  public static final int _DEVICE = 4;
  public static final int _LOCATION = 5;
  public static final int _SUCCESS = 6;
  public static final int _FAILUREREASON = 7;

  @ManyToOne(fetch = FetchType.LAZY)
  private BaseUser user;

  private LocalDateTime timeStamp;
  private String iPAddress;
  private String browser;
  private String device;
  private String location;
  private boolean success = false;
  private String failureReason;

  public UserLoginRecord() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.UserLoginRecord;
  }

  @Override
  public String _type() {
    return "UserLoginRecord";
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

  public LocalDateTime getTimeStamp() {
    _checkProxy();
    return this.timeStamp;
  }

  public void setTimeStamp(LocalDateTime timeStamp) {
    _checkProxy();
    if (Objects.equals(this.timeStamp, timeStamp)) {
      return;
    }
    fieldChanged(_TIMESTAMP, this.timeStamp, timeStamp);
    this.timeStamp = timeStamp;
  }

  public String getIPAddress() {
    _checkProxy();
    return this.iPAddress;
  }

  public void setIPAddress(String iPAddress) {
    _checkProxy();
    if (Objects.equals(this.iPAddress, iPAddress)) {
      return;
    }
    fieldChanged(_IPADDRESS, this.iPAddress, iPAddress);
    this.iPAddress = iPAddress;
  }

  public String getBrowser() {
    _checkProxy();
    return this.browser;
  }

  public void setBrowser(String browser) {
    _checkProxy();
    if (Objects.equals(this.browser, browser)) {
      return;
    }
    fieldChanged(_BROWSER, this.browser, browser);
    this.browser = browser;
  }

  public String getDevice() {
    _checkProxy();
    return this.device;
  }

  public void setDevice(String device) {
    _checkProxy();
    if (Objects.equals(this.device, device)) {
      return;
    }
    fieldChanged(_DEVICE, this.device, device);
    this.device = device;
  }

  public String getLocation() {
    _checkProxy();
    return this.location;
  }

  public void setLocation(String location) {
    _checkProxy();
    if (Objects.equals(this.location, location)) {
      return;
    }
    fieldChanged(_LOCATION, this.location, location);
    this.location = location;
  }

  public boolean isSuccess() {
    _checkProxy();
    return this.success;
  }

  public void setSuccess(boolean success) {
    _checkProxy();
    if (Objects.equals(this.success, success)) {
      return;
    }
    fieldChanged(_SUCCESS, this.success, success);
    this.success = success;
  }

  public String getFailureReason() {
    _checkProxy();
    return this.failureReason;
  }

  public void setFailureReason(String failureReason) {
    _checkProxy();
    if (Objects.equals(this.failureReason, failureReason)) {
      return;
    }
    fieldChanged(_FAILUREREASON, this.failureReason, failureReason);
    this.failureReason = failureReason;
  }

  public String displayName() {
    return "UserLoginRecord";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof UserLoginRecord && super.equals(a);
  }

  public UserLoginRecord deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    UserLoginRecord _obj = ((UserLoginRecord) dbObj);
    _obj.setUser(user);
    _obj.setTimeStamp(timeStamp);
    _obj.setIPAddress(iPAddress);
    _obj.setBrowser(browser);
    _obj.setDevice(device);
    _obj.setLocation(location);
    _obj.setSuccess(success);
    _obj.setFailureReason(failureReason);
  }

  public UserLoginRecord cloneInstance(UserLoginRecord cloneObj) {
    if (cloneObj == null) {
      cloneObj = new UserLoginRecord();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setUser(this.getUser());
    cloneObj.setTimeStamp(this.getTimeStamp());
    cloneObj.setIPAddress(this.getIPAddress());
    cloneObj.setBrowser(this.getBrowser());
    cloneObj.setDevice(this.getDevice());
    cloneObj.setLocation(this.getLocation());
    cloneObj.setSuccess(this.isSuccess());
    cloneObj.setFailureReason(this.getFailureReason());
    return cloneObj;
  }

  public UserLoginRecord createNewInstance() {
    return new UserLoginRecord();
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
