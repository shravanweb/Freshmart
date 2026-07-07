package helpers;

import classes.NotificationType;
import models.InAppNotification;
import models.Organization;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.InAppNotificationRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("InAppNotification")
public class InAppNotificationEntityHelper<T extends InAppNotification> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private InAppNotificationRepository inAppNotificationRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public InAppNotification newInstance() {
    return new InAppNotification();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldRecipient(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    User it = entity.getRecipient();
    if (it == null) {
      validationContext.addFieldError("recipient", "recipient is required.");
      return;
    }
  }

  public void validateFieldTitle(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getTitle();
    if (it == null) {
      validationContext.addFieldError("title", "title is required.");
      return;
    }
  }

  public void validateFieldMessage(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getMessage();
    if (it == null) {
      validationContext.addFieldError("message", "message is required.");
      return;
    }
  }

  public void validateFieldNotificationType(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    NotificationType it = entity.getNotificationType();
    if (it == null) {
      validationContext.addFieldError("notificationType", "notificationType is required.");
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
    validateFieldRecipient(entity, validationContext, onCreate, onUpdate);
    validateFieldTitle(entity, validationContext, onCreate, onUpdate);
    validateFieldMessage(entity, validationContext, onCreate, onUpdate);
    validateFieldNotificationType(entity, validationContext, onCreate, onUpdate);
    validateFieldOrganization(entity, validationContext, onCreate, onUpdate);
  }

  public void validateOnCreate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, true, false);
  }

  public void validateOnUpdate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, false, true);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) inAppNotificationRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {}

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
