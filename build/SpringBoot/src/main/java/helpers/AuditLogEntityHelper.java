package helpers;

import classes.AuditAction;
import java.time.LocalDateTime;
import models.AuditLog;
import models.Organization;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.AuditLogRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("AuditLog")
public class AuditLogEntityHelper<T extends AuditLog> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private AuditLogRepository auditLogRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public AuditLog newInstance() {
    return new AuditLog();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldEntityType(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getEntityType();
    if (it == null) {
      validationContext.addFieldError("entityType", "entityType is required.");
      return;
    }
  }

  public void validateFieldEntityId(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getEntityId();
    if (it == null) {
      validationContext.addFieldError("entityId", "entityId is required.");
      return;
    }
  }

  public void validateFieldAction(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    AuditAction it = entity.getAction();
    if (it == null) {
      validationContext.addFieldError("action", "action is required.");
      return;
    }
  }

  public void validateFieldPerformedBy(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    User it = entity.getPerformedBy();
    if (it == null) {
      validationContext.addFieldError("performedBy", "performedBy is required.");
      return;
    }
  }

  public void validateFieldPerformedAt(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    LocalDateTime it = entity.getPerformedAt();
    if (it == null) {
      validationContext.addFieldError("performedAt", "performedAt is required.");
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
    validateFieldEntityType(entity, validationContext, onCreate, onUpdate);
    validateFieldEntityId(entity, validationContext, onCreate, onUpdate);
    validateFieldAction(entity, validationContext, onCreate, onUpdate);
    validateFieldPerformedBy(entity, validationContext, onCreate, onUpdate);
    validateFieldPerformedAt(entity, validationContext, onCreate, onUpdate);
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
    return id == 0l ? null : ((T) auditLogRepository.getOne(id));
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
