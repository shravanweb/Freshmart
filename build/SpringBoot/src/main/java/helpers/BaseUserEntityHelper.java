package helpers;

import d3e.core.QueryProvider;
import java.util.List;
import models.BaseUser;
import models.OneTimePassword;
import models.PushNotification;
import models.UserDevice;
import models.UserDevicesRequest;
import models.UserLoginRecord;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.BaseUserRepository;
import repository.jpa.OneTimePasswordRepository;
import repository.jpa.PushNotificationRepository;
import repository.jpa.UserDeviceRepository;
import repository.jpa.UserLoginRecordRepository;
import store.Database;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("BaseUser")
public class BaseUserEntityHelper<T extends BaseUser> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private BaseUserRepository baseUserRepository;
  @Autowired private OneTimePasswordRepository oneTimePasswordRepository;
  @Autowired private PushNotificationRepository pushNotificationRepository;
  @Autowired private UserDeviceRepository userDeviceRepository;
  @Autowired private UserLoginRecordRepository userLoginRecordRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
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
    return id == 0l ? null : ((T) baseUserRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {}

  @Override
  public void compute(T entity) {}

  private void deleteUserInOneTimePassword(T entity, EntityValidationContext deletionContext) {
    for (OneTimePassword obj : this.oneTimePasswordRepository.getByUser(entity)) {
      this.mutator.delete(obj, true);
    }
  }

  private void deleteUsersInPushNotification(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.pushNotificationRepository.findByUsers(entity))) {
      deletionContext.addEntityError(
          "This BaseUser cannot be deleted as it is being referred to by PushNotification.");
    }
  }

  private void deleteUserInUserDevice(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.userDeviceRepository.getByUser(entity))) {
      deletionContext.addEntityError(
          "This BaseUser cannot be deleted as it is being referred to by UserDevice.");
    }
  }

  private void deleteUserInUserLoginRecord(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.userLoginRecordRepository.getByUser(entity))) {
      deletionContext.addEntityError(
          "This BaseUser cannot be deleted as it is being referred to by UserLoginRecord.");
    }
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    this.deleteUserInOneTimePassword(entity, deletionContext);
    return true;
  }

  public void validateOnDelete(T entity, EntityValidationContext deletionContext) {
    this.deleteUsersInPushNotification(entity, deletionContext);
    this.deleteUserInUserDevice(entity, deletionContext);
    this.deleteUserInUserLoginRecord(entity, deletionContext);
  }

  public void performAction_CreateDevice(BaseUser entity) {
    {
      if (entity.getDeviceToken() != null) {
        UserDevicesRequest userDevicesRequest$ = new UserDevicesRequest();
        {
          userDevicesRequest$.setUser(entity);
          userDevicesRequest$.setToken(entity.getDeviceToken());
        }
        List<UserDevice> devices =
            QueryProvider.get().getUserDevices(userDevicesRequest$).getItems();
        if (devices.isEmpty()) {
          UserDevice userDevice$ = new UserDevice();
          {
            userDevice$.setUser(entity);
            userDevice$.setDeviceToken(entity.getDeviceToken());
          }
          UserDevice device = userDevice$;
          Database.get().save(device);
        }
      }
    }
  }

  public void performOnCreateActions(BaseUser entity) {
    performOnCreateAndUpdateActions(entity);
  }

  public void performOnUpdateActions(BaseUser entity) {
    performOnCreateAndUpdateActions(entity);
  }

  public void performOnCreateAndUpdateActions(BaseUser entity) {
    performAction_CreateDevice(entity);
  }

  @Override
  public Boolean onCreate(T entity, boolean internal, EntityValidationContext context) {
    performOnCreateActions(entity);
    return true;
  }

  @Override
  public Boolean onUpdate(T entity, boolean internal, EntityValidationContext context) {
    performOnUpdateActions(entity);
    return true;
  }

  public T getOld(long id) {
    return ((T) getById(id).clone());
  }
}
