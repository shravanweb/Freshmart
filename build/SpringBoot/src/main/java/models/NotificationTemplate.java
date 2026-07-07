package models;

import classes.EntityStatus;
import classes.NotificationChannel;
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

@SolrDocument(collection = "NotificationTemplate")
@Entity
public class NotificationTemplate extends CreatableObject {
  public static final int _TEMPLATECODE = 0;
  public static final int _CHANNEL = 1;
  public static final int _SUBJECT = 2;
  public static final int _BODYTEMPLATE = 3;
  public static final int _STATUS = 4;
  public static final int _ORGANIZATION = 5;
  @NotNull private String templateCode;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private NotificationChannel channel = NotificationChannel.Email;

  private String subject;
  @NotNull private String bodyTemplate;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private EntityStatus status = EntityStatus.Active;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  public NotificationTemplate() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.NotificationTemplate;
  }

  @Override
  public String _type() {
    return "NotificationTemplate";
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

  public String getTemplateCode() {
    _checkProxy();
    return this.templateCode;
  }

  public void setTemplateCode(String templateCode) {
    _checkProxy();
    if (Objects.equals(this.templateCode, templateCode)) {
      return;
    }
    fieldChanged(_TEMPLATECODE, this.templateCode, templateCode);
    this.templateCode = templateCode;
  }

  public NotificationChannel getChannel() {
    _checkProxy();
    return this.channel;
  }

  public void setChannel(NotificationChannel channel) {
    _checkProxy();
    if (Objects.equals(this.channel, channel)) {
      return;
    }
    fieldChanged(_CHANNEL, this.channel, channel);
    this.channel = channel;
  }

  public String getSubject() {
    _checkProxy();
    return this.subject;
  }

  public void setSubject(String subject) {
    _checkProxy();
    if (Objects.equals(this.subject, subject)) {
      return;
    }
    fieldChanged(_SUBJECT, this.subject, subject);
    this.subject = subject;
  }

  public String getBodyTemplate() {
    _checkProxy();
    return this.bodyTemplate;
  }

  public void setBodyTemplate(String bodyTemplate) {
    _checkProxy();
    if (Objects.equals(this.bodyTemplate, bodyTemplate)) {
      return;
    }
    fieldChanged(_BODYTEMPLATE, this.bodyTemplate, bodyTemplate);
    this.bodyTemplate = bodyTemplate;
  }

  public EntityStatus getStatus() {
    _checkProxy();
    return this.status;
  }

  public void setStatus(EntityStatus status) {
    _checkProxy();
    if (Objects.equals(this.status, status)) {
      return;
    }
    fieldChanged(_STATUS, this.status, status);
    this.status = status;
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
    return this.getTemplateCode();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof NotificationTemplate && super.equals(a);
  }

  public NotificationTemplate deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    NotificationTemplate _obj = ((NotificationTemplate) dbObj);
    _obj.setTemplateCode(templateCode);
    _obj.setChannel(channel);
    _obj.setSubject(subject);
    _obj.setBodyTemplate(bodyTemplate);
    _obj.setStatus(status);
    _obj.setOrganization(organization);
  }

  public NotificationTemplate cloneInstance(NotificationTemplate cloneObj) {
    if (cloneObj == null) {
      cloneObj = new NotificationTemplate();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setTemplateCode(this.getTemplateCode());
    cloneObj.setChannel(this.getChannel());
    cloneObj.setSubject(this.getSubject());
    cloneObj.setBodyTemplate(this.getBodyTemplate());
    cloneObj.setStatus(this.getStatus());
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public NotificationTemplate createNewInstance() {
    return new NotificationTemplate();
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
