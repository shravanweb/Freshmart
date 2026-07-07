package helpers;

import classes.EntityStatus;
import classes.StoreType;
import models.Organization;
import models.SalesOrder;
import models.SalesReturn;
import models.Store;
import models.User;
import models.UserProfile;
import models.Warehouse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.SalesOrderRepository;
import repository.jpa.SalesReturnRepository;
import repository.jpa.StoreRepository;
import repository.jpa.UserProfileRepository;
import repository.jpa.WarehouseRepository;
import store.Database;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("Store")
public class StoreEntityHelper<T extends Store> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private StoreRepository storeRepository;
  @Autowired private SalesOrderRepository salesOrderRepository;
  @Autowired private SalesReturnRepository salesReturnRepository;
  @Autowired private UserProfileRepository userProfileRepository;
  @Autowired private WarehouseRepository warehouseRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public Store newInstance() {
    return new Store();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldName(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getName();
    if (it == null) {
      validationContext.addFieldError("name", "name is required.");
      return;
    }
  }

  public void validateFieldCode(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getCode();
    if (it == null) {
      validationContext.addFieldError("code", "code is required.");
      return;
    }
  }

  public void validateFieldStoreType(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    StoreType it = entity.getStoreType();
    if (it == null) {
      validationContext.addFieldError("storeType", "storeType is required.");
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
    validateFieldName(entity, validationContext, onCreate, onUpdate);
    validateFieldCode(entity, validationContext, onCreate, onUpdate);
    validateFieldStoreType(entity, validationContext, onCreate, onUpdate);
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
    return id == 0l ? null : ((T) storeRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultStatus(entity);
  }

  @Override
  public void compute(T entity) {}

  private void deleteStoreInSalesOrder(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.salesOrderRepository.getByStore(entity))) {
      deletionContext.addEntityError(
          "This Store cannot be deleted as it is being referred to by SalesOrder.");
    }
  }

  private void deleteStoreInSalesReturn(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.salesReturnRepository.getByStore(entity))) {
      deletionContext.addEntityError(
          "This Store cannot be deleted as it is being referred to by SalesReturn.");
    }
  }

  private void deleteAssignedStoresInUserProfile(
      T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.userProfileRepository.findByAssignedStores(entity))) {
      deletionContext.addEntityError(
          "This Store cannot be deleted as it is being referred to by UserProfile.");
    }
  }

  private void deleteStoreInWarehouse(T entity, EntityValidationContext deletionContext) {
    for (models.Warehouse warehouse : this.warehouseRepository.getByStore(entity)) {
      warehouse.setStore(null);
    }
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    this.deleteStoreInWarehouse(entity, deletionContext);
    return true;
  }

  public void validateOnDelete(T entity, EntityValidationContext deletionContext) {
    this.deleteStoreInSalesOrder(entity, deletionContext);
    this.deleteStoreInSalesReturn(entity, deletionContext);
    this.deleteAssignedStoresInUserProfile(entity, deletionContext);
  }

  @Override
  public Boolean onCreate(T entity, boolean internal, EntityValidationContext context) {
    syncManagerAssignment(entity, null);
    return true;
  }

  @Override
  public Boolean onUpdate(T entity, boolean internal, EntityValidationContext context) {
    User previousManager = null;
    if (entity.getId() > 0) {
      T oldEntity = getOld(entity.getId());
      if (oldEntity != null) {
        previousManager = oldEntity.getManager();
      }
    }
    syncManagerAssignment(entity, previousManager);
    return true;
  }

  private void syncManagerAssignment(T entity, User previousManager) {
    User newManager = entity.getManager();

    if (previousManager != null
        && (newManager == null || previousManager.getId() != newManager.getId())) {
      UserProfile previousProfile = userProfileRepository.getByUser(previousManager);
      if (previousProfile != null && previousProfile.getAssignedStores() != null) {
        previousProfile.getAssignedStores().remove(entity);
        Database.get().save(previousProfile);
      }
    }

    if (newManager == null) {
      return;
    }

    List<UserProfile> assignedProfiles = userProfileRepository.findByAssignedStores(entity);
    for (UserProfile profile : assignedProfiles) {
      if (profile.getUser() != null && profile.getUser().getId() == newManager.getId()) {
        continue;
      }
      profile.getAssignedStores().remove(entity);
      Database.get().save(profile);
    }

    UserProfile managerProfile = userProfileRepository.getByUser(newManager);
    if (managerProfile == null) {
      return;
    }

    List<Store> assignedStores = new ArrayList<>();
    assignedStores.add(entity);
    managerProfile.setAssignedStores(assignedStores);
    Database.get().save(managerProfile);
  }

  public T getOld(long id) {
    return ((T) getById(id).clone());
  }
}
