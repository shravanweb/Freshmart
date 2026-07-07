package lists;

import classes.IdGenerator;
import classes.SalesOrderItem;
import d3e.core.CurrentUser;
import d3e.core.ListExt;
import d3e.core.Log;
import d3e.core.SchemaConstants;
import gqltosql2.Field;
import gqltosql2.OutObject;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Cancellable;
import java.util.List;
import java.util.Objects;
import models.BaseUser;
import models.SalesOrder;
import models.SalesOrderItemRequest;
import models.User;
import rest.ws.ClientSession;
import rest.ws.DataChangeTracker;
import store.StoreEventType;

public class SalesOrderItemChangeTracker implements Cancellable {
  private class SalesOrderItemInput {
    private TypeAndId salesOrderRef;
    private SalesOrder salesOrder;

    public SalesOrderItemInput(SalesOrderItemRequest _req) {
      this.salesOrderRef = TypeAndId.from(_req.getSalesOrder());
      if (_req.getSalesOrder() == null) {
        Log.info(
            "WARNING: Input \\\"salesOrder\\\" in \\\"SalesOrderItem\\\" is required, but got value null.");
      }
    }

    public SalesOrder getSalesOrder() {
      return salesOrder;
    }

    public void load(DataChangeTracker tracker) {
      if (this.salesOrderRef != null) {
        this.salesOrder = tracker.fromTypeAndId(this.salesOrderRef);
      }
    }

    public void unload() {
      this.salesOrder = null;
    }
  }

  private SalesOrderItem root;
  private DataChangeTracker tracker;
  private ClientSession clientSession;
  private List<Disposable> disposables = ListExt.List();
  private SalesOrderItemInput inputs;
  private Field field;

  public SalesOrderItemChangeTracker(
      DataChangeTracker tracker, ClientSession clientSession, Field field) {
    this.tracker = tracker;
    this.clientSession = clientSession;
    this.field = field;
  }

  public void init(OutObject out, SalesOrderItem initialData, SalesOrderItemRequest inputs) {
    {
      BaseUser currentUser = CurrentUser.get();
      if (!(currentUser instanceof User)) {
        throw new RuntimeException(
            "Current user type does not have read permissions for this ObjectList.");
      }
    }
    initialData._clearChanges();
    this.inputs = new SalesOrderItemInput(inputs);
    storeInitialData(initialData);
    addSubscriptions();
    out.setId(root.getId());
    disposables.add(tracker.listen(out, field, clientSession));
  }

  @Override
  public void cancel() {
    disposables.forEach((d) -> d.dispose());
  }

  private void storeInitialData(SalesOrderItem initialData) {
    this.root = initialData;
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
            SchemaConstants.SalesOrder,
            null,
            (obj, old, type) -> {
              try {
                inputs.load(tracker);
                applySalesOrder(((SalesOrder) obj), ((SalesOrder) old), type);
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

  public void applySalesOrder(SalesOrder model, SalesOrder old, StoreEventType type) {
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

  private boolean applyWhere(SalesOrder model) {
    return Objects.equals(model, inputs.getSalesOrder());
  }

  private void createInsertChange(SalesOrder model) {
    root.addToItemsRef(new TypeAndId(model._typeIdx(), model.getId()), -1);
    fire();
  }

  private void createDeleteChange(SalesOrder model) {
    long id = model.getId();
    TypeAndId existing = find(id);
    if (existing == null) {
      return;
    }
    root.removeFromItemsRef(existing);
    fire();
  }
}
