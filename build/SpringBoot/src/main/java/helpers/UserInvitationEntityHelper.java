package helpers;

import classes.AppUserRole;
import classes.InvitationStatus;
import java.time.LocalDateTime;
import models.Organization;
import models.User;
import models.UserInvitation;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.UserInvitationRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("UserInvitation")
public class UserInvitationEntityHelper<T extends UserInvitation> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private UserInvitationRepository userInvitationRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public UserInvitation newInstance() {
    return new UserInvitation();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldEmail(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getEmail();
    if (it == null) {
      validationContext.addFieldError("email", "email is required.");
      return;
    }
  }

  public void validateFieldAppRole(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    AppUserRole it = entity.getAppRole();
    if (it == null) {
      validationContext.addFieldError("appRole", "appRole is required.");
      return;
    }
  }

  public void validateFieldInvitedBy(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    User it = entity.getInvitedBy();
    if (it == null) {
      validationContext.addFieldError("invitedBy", "invitedBy is required.");
      return;
    }
  }

  public void validateFieldToken(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getToken();
    if (it == null) {
      validationContext.addFieldError("token", "token is required.");
      return;
    }
  }

  public void validateFieldStatus(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    InvitationStatus it = entity.getStatus();
    if (it == null) {
      validationContext.addFieldError("status", "status is required.");
      return;
    }
  }

  public void validateFieldExpiresAt(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    LocalDateTime it = entity.getExpiresAt();
    if (it == null) {
      validationContext.addFieldError("expiresAt", "expiresAt is required.");
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
    validateFieldEmail(entity, validationContext, onCreate, onUpdate);
    validateFieldAppRole(entity, validationContext, onCreate, onUpdate);
    validateFieldInvitedBy(entity, validationContext, onCreate, onUpdate);
    validateFieldToken(entity, validationContext, onCreate, onUpdate);
    validateFieldStatus(entity, validationContext, onCreate, onUpdate);
    validateFieldExpiresAt(entity, validationContext, onCreate, onUpdate);
    validateFieldOrganization(entity, validationContext, onCreate, onUpdate);
    validateFieldTokenUnique(entity, validationContext);
  }

  public void validateOnCreate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, true, false);
  }

  public void validateOnUpdate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, false, true);
  }

  public void validateFieldTokenUnique(T entity, EntityValidationContext validationContext) {
    if (entity.getOrganization() != null
        && !(userInvitationRepository.checkUserInvitationTokenUniqueInOrganization(
            entity.getOrganization().getId(), entity.getId(), entity.getToken()))) {
      validationContext.addFieldError(
          "token", "Given token already exists in the same Organization");
    }
  }

  public void setDefaultStatus(T entity) {
    if (entity.getStatus() != InvitationStatus.Pending) {
      return;
    }
    entity.setStatus(InvitationStatus.Pending);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) userInvitationRepository.getOne(id));
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
