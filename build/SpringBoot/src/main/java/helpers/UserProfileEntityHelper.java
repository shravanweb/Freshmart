package helpers;

import classes.AppUserRole;
import classes.EntityStatus;
import models.Organization;
import models.User;
import models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.DFileRepository;
import repository.jpa.UserProfileRepository;
import store.Database;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;
import java.util.Objects;

@org.springframework.stereotype.Service("UserProfile")
public class UserProfileEntityHelper<T extends UserProfile> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private UserProfileRepository userProfileRepository;
  @Autowired private DFileRepository dFileRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public UserProfile newInstance() {
    return new UserProfile();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldUser(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    User it = entity.getUser();
    if (it == null) {
      validationContext.addFieldError("user", "user is required.");
      return;
    }
  }

  public void validateFieldDisplayName(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getDisplayName();
    if (it == null) {
      validationContext.addFieldError("displayName", "displayName is required.");
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
    validateFieldUser(entity, validationContext, onCreate, onUpdate);
    validateFieldDisplayName(entity, validationContext, onCreate, onUpdate);
    validateFieldAppRole(entity, validationContext, onCreate, onUpdate);
    validateFieldStatus(entity, validationContext, onCreate, onUpdate);
    validateFieldOrganization(entity, validationContext, onCreate, onUpdate);
    validateFieldUserUnique(entity, validationContext);
  }

  public void validateOnCreate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, true, false);
  }

  public void validateOnUpdate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, false, true);
  }

  public void validateFieldUserUnique(T entity, EntityValidationContext validationContext) {
    if (entity.getOrganization() != null
        && !(userProfileRepository.checkUserProfileUserUniqueInOrganization(
            entity.getOrganization().getId(), entity.getId(), entity.getUser()))) {
      validationContext.addFieldError("user", "Given user already exists in the same Organization");
    }
  }

  public void setDefaultStatus(T entity) {
    if (entity.getStatus() != EntityStatus.Active) {
      return;
    }
    entity.setStatus(EntityStatus.Active);
  }

  public void setDefaultAppRole(T entity) {
    User user = entity.getUser();
    if (user != null && user.getRole() != null) {
      entity.setAppRole(user.getRole());
      return;
    }
    if (entity.getAppRole() == AppUserRole.OrganizationAdmin) {
      entity.setAppRole(AppUserRole.Viewer);
    }
  }

  private void syncUserRole(T entity) {
    User user = entity.getUser();
    if (user == null || entity.getAppRole() == null) {
      return;
    }
    if (!Objects.equals(user.getRole(), entity.getAppRole())) {
      user.setRole(entity.getAppRole());
      Database.get().save(user);
    }
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) userProfileRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultStatus(entity);
    this.setDefaultAppRole(entity);
  }

  @Override
  public void compute(T entity) {}

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    return true;
  }

  @Override
  public Boolean onCreate(T entity, boolean internal, EntityValidationContext context) {
    syncUserRole(entity);
    return true;
  }

  @Override
  public Boolean onUpdate(T entity, boolean internal, EntityValidationContext context) {
    syncUserRole(entity);
    return true;
  }

  public T getOld(long id) {
    return ((T) getById(id).clone());
  }
}
