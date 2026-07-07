package models;

import classes.NotificationType;
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

@SolrDocument(collection = "InAppNotification")
@Entity
public class InAppNotification extends CreatableObject {
  public static final int _RECIPIENT = 0;
  public static final int _TITLE = 1;
  public static final int _MESSAGE = 2;
  public static final int _NOTIFICATIONTYPE = 3;
  public static final int _REFERENCETYPE = 4;
  public static final int _REFERENCEID = 5;
  public static final int _ISREAD = 6;
  public static final int _CREATEDAT = 7;
  public static final int _ORGANIZATION = 8;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private User recipient;

  @NotNull private String title;
  @NotNull private String message;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private NotificationType notificationType = NotificationType.LowStock;

  private String referenceType;
  private String referenceId;
  private boolean isRead = false;
  private LocalDateTime createdAt;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  private transient InAppNotification old;

  public InAppNotification() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.InAppNotification;
  }

  @Override
  public String _type() {
    return "InAppNotification";
  }

  @Override
  public int _fieldsCount() {
    return 9;
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public User getRecipient() {
    _checkProxy();
    return this.recipient;
  }

  public void setRecipient(User recipient) {
    _checkProxy();
    if (Objects.equals(this.recipient, recipient)) {
      return;
    }
    fieldChanged(_RECIPIENT, this.recipient, recipient);
    this.recipient = recipient;
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

  public String getMessage() {
    _checkProxy();
    return this.message;
  }

  public void setMessage(String message) {
    _checkProxy();
    if (Objects.equals(this.message, message)) {
      return;
    }
    fieldChanged(_MESSAGE, this.message, message);
    this.message = message;
  }

  public NotificationType getNotificationType() {
    _checkProxy();
    return this.notificationType;
  }

  public void setNotificationType(NotificationType notificationType) {
    _checkProxy();
    if (Objects.equals(this.notificationType, notificationType)) {
      return;
    }
    fieldChanged(_NOTIFICATIONTYPE, this.notificationType, notificationType);
    this.notificationType = notificationType;
  }

  public String getReferenceType() {
    _checkProxy();
    return this.referenceType;
  }

  public void setReferenceType(String referenceType) {
    _checkProxy();
    if (Objects.equals(this.referenceType, referenceType)) {
      return;
    }
    fieldChanged(_REFERENCETYPE, this.referenceType, referenceType);
    this.referenceType = referenceType;
  }

  public String getReferenceId() {
    _checkProxy();
    return this.referenceId;
  }

  public void setReferenceId(String referenceId) {
    _checkProxy();
    if (Objects.equals(this.referenceId, referenceId)) {
      return;
    }
    fieldChanged(_REFERENCEID, this.referenceId, referenceId);
    this.referenceId = referenceId;
  }

  public boolean isIsRead() {
    _checkProxy();
    return this.isRead;
  }

  public void setIsRead(boolean isRead) {
    _checkProxy();
    if (Objects.equals(this.isRead, isRead)) {
      return;
    }
    fieldChanged(_ISREAD, this.isRead, isRead);
    this.isRead = isRead;
  }

  public LocalDateTime getCreatedAt() {
    _checkProxy();
    return this.createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    _checkProxy();
    if (Objects.equals(this.createdAt, createdAt)) {
      return;
    }
    fieldChanged(_CREATEDAT, this.createdAt, createdAt);
    this.createdAt = createdAt;
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

  public InAppNotification getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((InAppNotification) old);
  }

  public String displayName() {
    return this.getTitle();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof InAppNotification && super.equals(a);
  }

  public InAppNotification deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    InAppNotification _obj = ((InAppNotification) dbObj);
    _obj.setRecipient(recipient);
    _obj.setTitle(title);
    _obj.setMessage(message);
    _obj.setNotificationType(notificationType);
    _obj.setReferenceType(referenceType);
    _obj.setReferenceId(referenceId);
    _obj.setIsRead(isRead);
    _obj.setCreatedAt(createdAt);
    _obj.setOrganization(organization);
  }

  public InAppNotification cloneInstance(InAppNotification cloneObj) {
    if (cloneObj == null) {
      cloneObj = new InAppNotification();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setRecipient(this.getRecipient());
    cloneObj.setTitle(this.getTitle());
    cloneObj.setMessage(this.getMessage());
    cloneObj.setNotificationType(this.getNotificationType());
    cloneObj.setReferenceType(this.getReferenceType());
    cloneObj.setReferenceId(this.getReferenceId());
    cloneObj.setIsRead(this.isIsRead());
    cloneObj.setCreatedAt(this.getCreatedAt());
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public InAppNotification createNewInstance() {
    return new InAppNotification();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.recipient);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
