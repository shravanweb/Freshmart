package models;

import classes.AuditAction;
import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

@SolrDocument(collection = "AuditLog")
@Entity
public class AuditLog extends CreatableObject {
  public static final int _ENTITYTYPE = 0;
  public static final int _ENTITYID = 1;
  public static final int _ACTION = 2;
  public static final int _PERFORMEDBY = 3;
  public static final int _PERFORMEDAT = 4;
  public static final int _OLDVALUES = 5;
  public static final int _NEWVALUES = 6;
  public static final int _IPADDRESS = 7;
  public static final int _USERAGENT = 8;
  public static final int _ORGANIZATION = 9;
  @NotNull private String entityType;
  @NotNull private String entityId;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private AuditAction action = AuditAction.Create;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private User performedBy;

  @NotNull private LocalDateTime performedAt;
  private String oldValues;
  private String newValues;
  private String ipAddress;
  private String userAgent;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  private transient AuditLog old;

  public AuditLog() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.AuditLog;
  }

  @Override
  public String _type() {
    return "AuditLog";
  }

  @Override
  public int _fieldsCount() {
    return 10;
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public String getEntityType() {
    _checkProxy();
    return this.entityType;
  }

  public void setEntityType(String entityType) {
    _checkProxy();
    if (Objects.equals(this.entityType, entityType)) {
      return;
    }
    fieldChanged(_ENTITYTYPE, this.entityType, entityType);
    this.entityType = entityType;
  }

  public String getEntityId() {
    _checkProxy();
    return this.entityId;
  }

  public void setEntityId(String entityId) {
    _checkProxy();
    if (Objects.equals(this.entityId, entityId)) {
      return;
    }
    fieldChanged(_ENTITYID, this.entityId, entityId);
    this.entityId = entityId;
  }

  public AuditAction getAction() {
    _checkProxy();
    return this.action;
  }

  public void setAction(AuditAction action) {
    _checkProxy();
    if (Objects.equals(this.action, action)) {
      return;
    }
    fieldChanged(_ACTION, this.action, action);
    this.action = action;
  }

  public User getPerformedBy() {
    _checkProxy();
    return this.performedBy;
  }

  public void setPerformedBy(User performedBy) {
    _checkProxy();
    if (Objects.equals(this.performedBy, performedBy)) {
      return;
    }
    fieldChanged(_PERFORMEDBY, this.performedBy, performedBy);
    this.performedBy = performedBy;
  }

  public LocalDateTime getPerformedAt() {
    _checkProxy();
    return this.performedAt;
  }

  public void setPerformedAt(LocalDateTime performedAt) {
    _checkProxy();
    if (Objects.equals(this.performedAt, performedAt)) {
      return;
    }
    fieldChanged(_PERFORMEDAT, this.performedAt, performedAt);
    this.performedAt = performedAt;
  }

  public String getOldValues() {
    _checkProxy();
    return this.oldValues;
  }

  public void setOldValues(String oldValues) {
    _checkProxy();
    if (Objects.equals(this.oldValues, oldValues)) {
      return;
    }
    fieldChanged(_OLDVALUES, this.oldValues, oldValues);
    this.oldValues = oldValues;
  }

  public String getNewValues() {
    _checkProxy();
    return this.newValues;
  }

  public void setNewValues(String newValues) {
    _checkProxy();
    if (Objects.equals(this.newValues, newValues)) {
      return;
    }
    fieldChanged(_NEWVALUES, this.newValues, newValues);
    this.newValues = newValues;
  }

  public String getIpAddress() {
    _checkProxy();
    return this.ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    _checkProxy();
    if (Objects.equals(this.ipAddress, ipAddress)) {
      return;
    }
    fieldChanged(_IPADDRESS, this.ipAddress, ipAddress);
    this.ipAddress = ipAddress;
  }

  public String getUserAgent() {
    _checkProxy();
    return this.userAgent;
  }

  public void setUserAgent(String userAgent) {
    _checkProxy();
    if (Objects.equals(this.userAgent, userAgent)) {
      return;
    }
    fieldChanged(_USERAGENT, this.userAgent, userAgent);
    this.userAgent = userAgent;
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

  public AuditLog getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((AuditLog) old);
  }

  public String displayName() {
    return this.getEntityType() + " - " + this.getAction().name();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof AuditLog && super.equals(a);
  }

  public AuditLog deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    AuditLog _obj = ((AuditLog) dbObj);
    _obj.setEntityType(entityType);
    _obj.setEntityId(entityId);
    _obj.setAction(action);
    _obj.setPerformedBy(performedBy);
    _obj.setPerformedAt(performedAt);
    _obj.setOldValues(oldValues);
    _obj.setNewValues(newValues);
    _obj.setIpAddress(ipAddress);
    _obj.setUserAgent(userAgent);
    _obj.setOrganization(organization);
  }

  public AuditLog cloneInstance(AuditLog cloneObj) {
    if (cloneObj == null) {
      cloneObj = new AuditLog();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setEntityType(this.getEntityType());
    cloneObj.setEntityId(this.getEntityId());
    cloneObj.setAction(this.getAction());
    cloneObj.setPerformedBy(this.getPerformedBy());
    cloneObj.setPerformedAt(this.getPerformedAt());
    cloneObj.setOldValues(this.getOldValues());
    cloneObj.setNewValues(this.getNewValues());
    cloneObj.setIpAddress(this.getIpAddress());
    cloneObj.setUserAgent(this.getUserAgent());
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public AuditLog createNewInstance() {
    return new AuditLog();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.performedBy);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
