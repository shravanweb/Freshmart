package helpers;

import models.PushNotification;
import models.UserDevice;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.BaseUserRepository;
import repository.jpa.PushNotificationRepository;
import repository.jpa.UserDeviceRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("UserDevice")
public class UserDeviceEntityHelper<T extends UserDevice> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private UserDeviceRepository userDeviceRepository;
  @Autowired private BaseUserRepository baseUserRepository;
  @Autowired private PushNotificationRepository pushNotificationRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public UserDevice newInstance() {
    return new UserDevice();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {}

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
    return id == 0l ? null : ((T) userDeviceRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {}

  @Override
  public void compute(T entity) {}

  private void deleteFailedDevicesInPushNotification(
      T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.pushNotificationRepository.findByFailedDevices(entity))) {
      deletionContext.addEntityError(
          "This UserDevice cannot be deleted as it is being referred to by PushNotification.");
    }
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    if (entity.getUser() != null) {
      entity.getUser().setDevices(null);
    }
    return true;
  }

  public void validateOnDelete(T entity, EntityValidationContext deletionContext) {
    this.deleteFailedDevicesInPushNotification(entity, deletionContext);
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
