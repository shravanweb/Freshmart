package helpers;

import classes.EntityStatus;
import classes.NotificationChannel;
import models.NotificationTemplate;
import models.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.NotificationTemplateRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("NotificationTemplate")
public class NotificationTemplateEntityHelper<T extends NotificationTemplate>
    implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private NotificationTemplateRepository notificationTemplateRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public NotificationTemplate newInstance() {
    return new NotificationTemplate();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldTemplateCode(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getTemplateCode();
    if (it == null) {
      validationContext.addFieldError("templateCode", "templateCode is required.");
      return;
    }
  }

  public void validateFieldChannel(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    NotificationChannel it = entity.getChannel();
    if (it == null) {
      validationContext.addFieldError("channel", "channel is required.");
      return;
    }
  }

  public void validateFieldBodyTemplate(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getBodyTemplate();
    if (it == null) {
      validationContext.addFieldError("bodyTemplate", "bodyTemplate is required.");
      return;
    }
  }

  public void validateFieldStatus(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    EntityStatus it = entity.getStatus();
    if (it == null) {
      validationContext.addFieldError("status", "status is required.");
      return;
    }
  }

  public void validateFieldOrganization(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    Organization it = entity.getOrganization();
    if (it == null) {
      validationContext.addFieldError("organization", "organization is required.");
      return;
    }
  }

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    validateFieldTemplateCode(entity, validationContext, onCreate, onUpdate);
    validateFieldChannel(entity, validationContext, onCreate, onUpdate);
    validateFieldBodyTemplate(entity, validationContext, onCreate, onUpdate);
    validateFieldStatus(entity, validationContext, onCreate, onUpdate);
    validateFieldOrganization(entity, validationContext, onCreate, onUpdate);
  }

  public void validateOnCreate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, true, false);
  }

  public void validateOnUpdate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, false, true);
  }

  public void setDefaultStatus(T entity) {
    if (entity.getStatus() != EntityStatus.Active) {
      return;
    }
    entity.setStatus(EntityStatus.Active);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) notificationTemplateRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultStatus(entity);
  }

  @Override
  public void compute(T entity) {}

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    return true;
  }

  @Override
  public Boolean onCreate(T entity, boolean internal, EntityValidationContext context) {
    return true;
  }

  @Override
  public Boolean onUpdate(T entity, boolean internal, EntityValidationContext context) {
    return true;
  }

  public T getOld(long id) {
    return ((T) getById(id).clone());
  }
}
