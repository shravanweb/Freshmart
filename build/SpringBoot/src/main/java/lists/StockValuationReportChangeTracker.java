package lists;

import classes.IdGenerator;
import classes.StockValuationReport;
import d3e.core.CurrentUser;
import d3e.core.ListExt;
import d3e.core.Log;
import d3e.core.ObjectUtils;
import d3e.core.SchemaConstants;
import gqltosql2.Field;
import gqltosql2.OutObject;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Cancellable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import models.BaseUser;
import models.Organization;
import models.StockValuationReportRequest;
import models.User;
import models.Warehouse;
import models.WarehouseStock;
import rest.ws.ClientSession;
import rest.ws.DataChangeTracker;
import store.StoreEventType;

public class StockValuationReportChangeTracker implements Cancellable {
  private class OrderBy {
    double OrderByValue0;
    long id;

    public OrderBy(double OrderByValue0, long id) {
      this.OrderByValue0 = OrderByValue0;
      this.id = id;
    }

    public boolean fallsBefore(double OrderByValue0) {
      if (ObjectUtils.isLessThan(this.OrderByValue0, OrderByValue0)) {
        return false;
      }
      return true;
    }

    public void update(double OrderByValue0) {
      this.OrderByValue0 = OrderByValue0;
    }
  }

  private class StockValuationReportInput {
    private TypeAndId organizationRef;
    private Organization organization;
    private TypeAndId warehouseRef;
    private Warehouse warehouse;

    public StockValuationReportInput(StockValuationReportRequest _req) {
      this.organizationRef = TypeAndId.from(_req.getOrganization());
      if (_req.getOrganization() == null) {
        Log.info(
            "WARNING: Input \\\"organization\\\" in \\\"StockValuationReport\\\" is required, but got value null.");
      }
      this.warehouseRef = TypeAndId.from(_req.getWarehouse());
    }

    public Organization getOrganization() {
      return organization;
    }

    public Warehouse getWarehouse() {
      return warehouse;
    }

    public void load(DataChangeTracker tracker) {
      if (this.organizationRef != null) {
        this.organization = tracker.fromTypeAndId(this.organizationRef);
      }
      if (this.warehouseRef != null) {
        this.warehouse = tracker.fromTypeAndId(this.warehouseRef);
      }
    }

    public void unload() {
      this.organization = null;
      this.warehouse = null;
    }
  }

  private StockValuationReport root;
  private List<OrderBy> orderBy = ListExt.List();
  private DataChangeTracker tracker;
  private ClientSession clientSession;
  private List<Disposable> disposables = ListExt.List();
  private StockValuationReportInput inputs;
  private Field field;

  public StockValuationReportChangeTracker(
      DataChangeTracker tracker, ClientSession clientSession, Field field) {
    this.tracker = tracker;
    this.clientSession = clientSession;
    this.field = field;
  }

  public void init(
      OutObject out, StockValuationReport initialData, StockValuationReportRequest inputs) {
    {
      BaseUser currentUser = CurrentUser.get();
      if (!(currentUser instanceof User)) {
        throw new RuntimeException(
            "Current user type does not have read permissions for this ObjectList.");
      }
    }
    initialData._clearChanges();
    this.inputs = new StockValuationReportInput(inputs);
    storeInitialData(initialData);
    addSubscriptions();
    out.setId(root.getId());
    disposables.add(tracker.listen(out, field, clientSession));
  }

  @Override
  public void cancel() {
    disposables.forEach((d) -> d.dispose());
  }

  private void storeInitialData(StockValuationReport initialData) {
    this.root = initialData;
    this.orderBy =
        initialData.getItems().stream()
            .map((x) -> new OrderBy(x.getStockValue(), x.getId()))
            .collect(Collectors.toList());
    long id = IdGenerator.getNext();
    this.root.setId(id);
    initialData._convertToObjectRef();
  }

  private void addSubscriptions() {
    // This method will register listeners on each reference that is referred to in the DataQuery
    // expression.
    // A listener is added by default on the Table from which we pull the data, since any change in
    // that must trigger a subscription change.
    Disposable baseSubscribe =
        tracker.listen(
            SchemaConstants.WarehouseStock,
            null,
            (obj, old, type) -> {
              try {
                inputs.load(tracker);
                applyWarehouseStock(((WarehouseStock) obj), ((WarehouseStock) old), type);
              } finally {
                inputs.unload();
              }
            });
    disposables.add(baseSubscribe);
  }

  private TypeAndId find(long id) {
    // TODO: Maybe remove
    return this.root.getItemsRef().stream().filter((x) -> x.id == id).findFirst().orElse(null);
  }

  private boolean has(long id) {
    return this.root.getItemsRef().stream().anyMatch((x) -> x.id == id);
  }

  private void fire() {
    tracker.fire(root, StoreEventType.Update);
  }

  public void applyWarehouseStock(WarehouseStock model, WarehouseStock old, StoreEventType type) {
    if (type == StoreEventType.Insert) {
      // New data is inserted, So we just insert the new data depending on the clauses.
      if (applyWhere(model)) {
        createInsertChange(model);
      }
    } else if (type == StoreEventType.Delete) {
      // Existing data is deleted
      createDeleteChange(model);
    } else {
      // Existing data is updated
      boolean currentMatch = applyWhere(model);
      boolean oldMatch = has(old.getId());
      if (currentMatch == oldMatch) {
        if (!(currentMatch) && !(oldMatch)) {
          return;
        }
        createPathChangeChange(model, old);
      } else {
        if (oldMatch) {
          createDeleteChange(model);
        }
        if (currentMatch) {
          createInsertChange(model);
        }
      }
    }
  }

  private boolean applyWhere(WarehouseStock model) {
    return Objects.equals(model.getOrganization(), inputs.getOrganization())
        && (inputs.getWarehouse() == null
            || Objects.equals(model.getWarehouse(), inputs.getWarehouse()));
  }

  private void createInsertChange(WarehouseStock model) {
    long id = model.getId();
    double OrderByValue0 = model.getStockValue();
    // Find index at which to add incoming object
    long index = 0;
    int orderBySize = this.orderBy.size();
    while (index < orderBySize && this.orderBy.get(((int) index)).fallsBefore(OrderByValue0)) {
      index++;
    }
    root.addToItemsRef(new TypeAndId(model._typeIdx(), model.getId()), index);
    this.orderBy.add(((int) index), new OrderBy(OrderByValue0, id));
    root.setCount(root.getCount() + 1);
    fire();
  }

  private void createDeleteChange(WarehouseStock model) {
    long id = model.getId();
    TypeAndId existing = find(id);
    if (existing == null) {
      return;
    }
    root.removeFromItemsRef(existing);
    ListExt.removeWhere(this.orderBy, (x) -> x.id == id);
    root.setCount(root.getCount() - 1);
    fire();
  }

  private boolean createPathChangeChange(WarehouseStock model, WarehouseStock old) {
    double oldValue0 = old.getStockValue();
    double modelValue0 = model.getStockValue();
    boolean changed = oldValue0 != modelValue0;
    if (!(changed)) {
      return false;
    }
    long id = model.getId();
    TypeAndId existing = find(id);
    if (existing == null) {
      return false;
    }
    double OrderByValue0 = model.getStockValue();
    moveItems(model, existing, OrderByValue0);
    return true;
  }

  private void moveItems(WarehouseStock model, TypeAndId data, double OrderByValue0) {
    // Find old index
    int index = this.root.getItemsRef().indexOf(data);
    // Remove from old index
    this.root.removeFromItemsRef(data);
    OrderBy toMove = this.orderBy.remove(index);
    // At this point, ref list and orderBy list are balanced
    // Find index at which to add incoming object
    long newIndex = 0;
    int orderBySize = this.orderBy.size();
    while (newIndex < orderBySize
        && this.orderBy.get(((int) newIndex)).fallsBefore(OrderByValue0)) {
      newIndex++;
    }
    boolean append = newIndex == orderBySize;
    if (append) {
      // Adding at the end
      newIndex = -1;
    }
    this.root.addToItemsRef(data, newIndex);
    if (append) {
      this.orderBy.add(toMove);
    } else {
      this.orderBy.add(((int) newIndex), toMove);
    }
    // Update the moved OrderBy
    toMove.update(OrderByValue0);
  }
}
