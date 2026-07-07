package classes;

import d3e.core.SchemaConstants;
import java.util.List;
import lists.TypeAndId;
import models.InventoryMovement;
import store.D3EPersistanceList;
import store.DBObject;

public class MovementReportRows extends DBObject {
  public static final int _STATUS = 0;
  public static final int _ERRORS = 1;
  public static final int _COUNT = 2;
  public static final int _ITEMS = 3;
  private long id;
  private ResultStatus status;
  private List<String> errors = new D3EPersistanceList<>(this, _ERRORS);
  private long count;
  private List<InventoryMovement> items = new D3EPersistanceList<>(this, _ITEMS);
  private List<TypeAndId> itemsRef;

  public MovementReportRows() {}

  public MovementReportRows(
      long count, List<String> errors, List<InventoryMovement> items, ResultStatus status) {
    this.count = count;
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

  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    fieldChanged(_COUNT, this.count, count);
    this.count = count;
  }

  public List<InventoryMovement> getItems() {
    return items;
  }

  public List<TypeAndId> getItemsRef() {
    return itemsRef;
  }

  public void setItems(List<InventoryMovement> items) {
    ((D3EPersistanceList<InventoryMovement>) this.items).setAll(items);
  }

  public void setItemsRef(List<TypeAndId> itemsRef) {
    if (this.itemsRef != null) {
      ((D3EPersistanceList<TypeAndId>) this.itemsRef).setAll(itemsRef);
    } else {
      this.itemsRef = itemsRef;
    }
  }

  public void addToItems(InventoryMovement val, long index) {
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

  public void removeFromItems(InventoryMovement val) {
    this.items.remove(val);
  }

  public void removeFromItemsRef(TypeAndId val) {
    this.itemsRef.remove(val);
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.MovementReportRows;
  }

  @Override
  public String _type() {
    return "MovementReportRows";
  }

  @Override
  public int _fieldsCount() {
    return 4;
  }

  public void _convertToObjectRef() {
    this.itemsRef = TypeAndId.fromList(this.items, _ITEMS, this);
    this.items.clear();
  }
}
