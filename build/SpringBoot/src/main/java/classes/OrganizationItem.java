package classes;

import d3e.core.SchemaConstants;
import java.util.List;
import lists.TypeAndId;
import models.Organization;
import store.D3EPersistanceList;
import store.DBObject;

public class OrganizationItem extends DBObject {
  public static final int _STATUS = 0;
  public static final int _ERRORS = 1;
  public static final int _ITEMS = 2;
  private long id;
  private ResultStatus status;
  private List<String> errors = new D3EPersistanceList<>(this, _ERRORS);
  private List<Organization> items = new D3EPersistanceList<>(this, _ITEMS);
  private List<TypeAndId> itemsRef;

  public OrganizationItem() {}

  public OrganizationItem(List<String> errors, List<Organization> items, ResultStatus status) {
    this.errors.addAll(errors);
    this.items.addAll(items);
    this.status = status;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public ResultStatus getStatus() {
    return status;
  }

  public void setStatus(ResultStatus status) {
    fieldChanged(_STATUS, this.status, status);
    this.status = status;
  }

  public List<String> getErrors() {
    return errors;
  }

  public void setErrors(List<String> errors) {
    ((D3EPersistanceList<String>) this.errors).setAll(errors);
  }

  public void addToErrors(String val, long index) {
    if (index == -1) {
      this.errors.add(val);
    } else {
      this.errors.add(((int) index), val);
    }
  }

  public void removeFromErrors(String val) {
    this.errors.remove(val);
  }

  public List<Organization> getItems() {
    return items;
  }

  public List<TypeAndId> getItemsRef() {
    return itemsRef;
  }

  public void setItems(List<Organization> items) {
    ((D3EPersistanceList<Organization>) this.items).setAll(items);
  }

  public void setItemsRef(List<TypeAndId> itemsRef) {
    if (this.itemsRef != null) {
      ((D3EPersistanceList<TypeAndId>) this.itemsRef).setAll(itemsRef);
    } else {
      this.itemsRef = itemsRef;
    }
  }

  public void addToItems(Organization val, long index) {
    if (index == -1) {
      this.items.add(val);
    } else {
      this.items.add(((int) index), val);
    }
  }

  public void addToItemsRef(TypeAndId val, long index) {
    if (index == -1) {
      this.itemsRef.add(val);
    } else {
      this.itemsRef.add(((int) index), val);
    }
  }

  public void removeFromItems(Organization val) {
    this.items.remove(val);
  }

  public void removeFromItemsRef(TypeAndId val) {
    this.itemsRef.remove(val);
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.OrganizationItem;
  }

  @Override
  public String _type() {
    return "OrganizationItem";
  }

  @Override
  public int _fieldsCount() {
    return 3;
  }

  public void _convertToObjectRef() {
    this.itemsRef = TypeAndId.fromList(this.items, _ITEMS, this);
    this.items.clear();
  }
}
