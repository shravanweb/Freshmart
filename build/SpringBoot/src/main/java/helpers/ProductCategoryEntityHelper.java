package helpers;

import classes.EntityStatus;
import models.Organization;
import models.Product;
import models.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.ProductCategoryRepository;
import repository.jpa.ProductRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("ProductCategory")
public class ProductCategoryEntityHelper<T extends ProductCategory> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private ProductCategoryRepository productCategoryRepository;
  @Autowired private ProductRepository productRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public ProductCategory newInstance() {
    return new ProductCategory();
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
    return id == 0l ? null : ((T) productCategoryRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultStatus(entity);
  }

  @Override
  public void compute(T entity) {}

  private void deleteCategoryInProduct(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.productRepository.getByCategory(entity))) {
      deletionContext.addEntityError(
          "This ProductCategory cannot be deleted as it is being referred to by Product.");
    }
  }

  private void deleteParentCategoryInProductCategory(
      T entity, EntityValidationContext deletionContext) {
    for (models.ProductCategory productCategory :
        this.productCategoryRepository.getByParentCategory(entity)) {
      productCategory.setParentCategory(null);
    }
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    this.deleteParentCategoryInProductCategory(entity, deletionContext);
    if (entity.getParentCategory() != null) {
      entity.getParentCategory().removeFromChildCategories(entity);
    }
    return true;
  }

  public void validateOnDelete(T entity, EntityValidationContext deletionContext) {
    this.deleteCategoryInProduct(entity, deletionContext);
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
