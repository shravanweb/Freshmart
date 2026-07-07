package helpers;

import classes.ReportMapType;
import d3e.core.SchemaConstants;
import models.ReportField;
import models.ReportMapConfig;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("ReportMapConfig")
public class ReportMapConfigEntityHelper<T extends ReportMapConfig>
    extends ReportBaseConfigEntityHelper<T> {
  public ReportMapConfig newInstance() {
    return new ReportMapConfig();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldType(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    ReportMapType it = entity.getType();
    if (it == null) {
      validationContext.addFieldError("type", "type is required.");
      return;
    }
  }

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    super.validateInternal(entity, validationContext, onCreate, onUpdate);
    validateFieldType(entity, validationContext, onCreate, onUpdate);
    long locationIndex = 0l;
    for (ReportField obj : entity.getLocation()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("location", null, locationIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("location", null, locationIndex++));
      }
    }
    long legendIndex = 0l;
    for (ReportField obj : entity.getLegend()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("legend", null, legendIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("legend", null, legendIndex++));
      }
    }
    if (entity.getLatitude() != null) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(entity.getLatitude());
      if (onCreate) {
        helper.validateOnCreate(
            entity.getLatitude(), validationContext.child("latitude", null, -1l));
      } else {
        helper.validateOnUpdate(
            entity.getLatitude(), validationContext.child("latitude", null, -1l));
      }
    }
    if (entity.getLongitude() != null) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(entity.getLongitude());
      if (onCreate) {
        helper.validateOnCreate(
            entity.getLongitude(), validationContext.child("longitude", null, -1l));
      } else {
        helper.validateOnUpdate(
            entity.getLongitude(), validationContext.child("longitude", null, -1l));
      }
    }
    long sizeIndex = 0l;
    for (ReportField obj : entity.getSize()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("size", null, sizeIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("size", null, sizeIndex++));
      }
    }
    long tooltipsIndex = 0l;
    for (ReportField obj : entity.getTooltips()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      if (onCreate) {
        helper.validateOnCreate(obj, validationContext.child("tooltips", null, tooltipsIndex++));
      } else {
        helper.validateOnUpdate(obj, validationContext.child("tooltips", null, tooltipsIndex++));
      }
    }
  }

  public void validateOnCreate(T entity, EntityValidationContext validationContext) {
    super.validateOnCreate(entity, validationContext);
  }

  public void validateOnUpdate(T entity, EntityValidationContext validationContext) {
    super.validateOnUpdate(entity, validationContext);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return ((T) provider.get().find(SchemaConstants.ReportMapConfig, id));
  }

  @Override
  public void setDefaults(T entity) {
    for (ReportField obj : entity.getLocation()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    for (ReportField obj : entity.getLegend()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    if (entity.getLatitude() != null) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(entity.getLatitude());
      helper.setDefaults(entity.getLatitude());
    }
    if (entity.getLongitude() != null) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(entity.getLongitude());
      helper.setDefaults(entity.getLongitude());
    }
    for (ReportField obj : entity.getSize()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
    for (ReportField obj : entity.getTooltips()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
  }

  @Override
  public void compute(T entity) {
    for (ReportField obj : entity.getLocation()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
    for (ReportField obj : entity.getLegend()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
    if (entity.getLatitude() != null) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(entity.getLatitude());
      helper.compute(entity.getLatitude());
    }
    if (entity.getLongitude() != null) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(entity.getLongitude());
      helper.compute(entity.getLongitude());
    }
    for (ReportField obj : entity.getSize()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
    for (ReportField obj : entity.getTooltips()) {
      ReportFieldEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    super.onDelete(entity, internal, deletionContext);
    return true;
  }

  @Override
  public Boolean onCreate(T entity, boolean internal, EntityValidationContext context) {
    super.onCreate(entity, internal, context);
    return true;
  }

  @Override
  public Boolean onUpdate(T entity, boolean internal, EntityValidationContext context) {
    super.onUpdate(entity, internal, context);
    return true;
  }
}
