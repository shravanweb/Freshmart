package lists;

import classes.IdGenerator;
import classes.SupplierItem;
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
import models.SupplierItemRequest;
import models.User;
import models.Vendor;
import rest.ws.ClientSession;
import rest.ws.DataChangeTracker;
import store.StoreEventType;

public class SupplierItemChangeTracker implements Cancellable {
  private class SupplierItemInput {
    private TypeAndId vendorRef;
    private Vendor vendor;

    public SupplierItemInput(SupplierItemRequest _req) {
      this.vendorRef = TypeAndId.from(_req.getVendor());
      if (_req.getVendor() == null) {
        Log.info(
            "WARNING: Input \\\"vendor\\\" in \\\"SupplierItem\\\" is required, but got value null.");
      }
    }

    public Vendor getVendor() {
      return vendor;
    }

    public void load(DataChangeTracker tracker) {
      if (this.vendorRef != null) {
        this.vendor = tracker.fromTypeAndId(this.vendorRef);
      }
    }

    public void unload() {
      this.vendor = null;
    }
  }

  private SupplierItem root;
  private DataChangeTracker tracker;
  private ClientSession clientSession;
  private List<Disposable> disposables = ListExt.List();
  private SupplierItemInput inputs;
  private Field field;

  public SupplierItemChangeTracker(
      DataChangeTracker tracker, ClientSession clientSession, Field field) {
    this.tracker = tracker;
    this.clientSession = clientSession;
    this.field = field;
  }

  public void init(OutObject out, SupplierItem initialData, SupplierItemRequest inputs) {
    {
      BaseUser currentUser = CurrentUser.get();
      if (!(currentUser instanceof User)) {
        throw new RuntimeException(
            "Current user type does not have read permissions for this ObjectList.");
      }
    }
    initialData._clearChanges();
    this.inputs = new SupplierItemInput(inputs);
    storeInitialData(initialData);
    addSubscriptions();
    out.setId(root.getId());
    disposables.add(tracker.listen(out, field, clientSession));
  }

  @Override
  public void cancel() {
    disposables.forEach((d) -> d.dispose());
  }

  private void storeInitialData(SupplierItem initialData) {
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
            SchemaConstants.Vendor,
            null,
            (obj, old, type) -> {
              try {
                inputs.load(tracker);
                applyVendor(((Vendor) obj), ((Vendor) old), type);
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

  public void applyVendor(Vendor model, Vendor old, StoreEventType type) {
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

  private boolean applyWhere(Vendor model) {
    return Objects.equals(model, inputs.getVendor());
  }

  private void createInsertChange(Vendor model) {
    root.addToItemsRef(new TypeAndId(model._typeIdx(), model.getId()), -1);
    fire();
  }

  private void createDeleteChange(Vendor model) {
    long id = model.getId();
    TypeAndId existing = find(id);
    if (existing == null) {
      return;
    }
    root.removeFromItemsRef(existing);
    fire();
  }
}
