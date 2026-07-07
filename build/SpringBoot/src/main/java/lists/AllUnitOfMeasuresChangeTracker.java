package lists;

import classes.AllUnitOfMeasures;
import classes.IdGenerator;
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
import models.AllUnitOfMeasuresRequest;
import models.BaseUser;
import models.Organization;
import models.UnitOfMeasure;
import models.User;
import rest.ws.ClientSession;
import rest.ws.DataChangeTracker;
import store.StoreEventType;

public class AllUnitOfMeasuresChangeTracker implements Cancellable {
  private class OrderBy {
    String OrderByValue0;
    long id;

    public OrderBy(String OrderByValue0, long id) {
      this.OrderByValue0 = OrderByValue0;
      this.id = id;
    }

    public boolean fallsBefore(String OrderByValue0) {
      if (ObjectUtils.isGreaterThan(this.OrderByValue0, OrderByValue0)) {
        return false;
      }
      return true;
    }

    public void update(String OrderByValue0) {
      this.OrderByValue0 = OrderByValue0;
    }
  }

  private class AllUnitOfMeasuresInput {
    private TypeAndId organizationRef;
    private Organization organization;

    public AllUnitOfMeasuresInput(AllUnitOfMeasuresRequest _req) {
      this.organizationRef = TypeAndId.from(_req.getOrganization());
      if (_req.getOrganization() == null) {
        Log.info(
            "WARNING: Input \\\"organization\\\" in \\\"AllUnitOfMeasures\\\" is required, but got value null.");
      }
    }

    public Organization getOrganization() {
      return organization;
    }

    public void load(DataChangeTracker tracker) {
      if (this.organizationRef != null) {
        this.organization = tracker.fromTypeAndId(this.organizationRef);
      }
    }

    public void unload() {
      this.organization = null;
    }
  }

  private AllUnitOfMeasures root;
  private List<OrderBy> orderBy = ListExt.List();
  private DataChangeTracker tracker;
  private ClientSession clientSession;
  private List<Disposable> disposables = ListExt.List();
  private AllUnitOfMeasuresInput inputs;
  private Field field;

  public AllUnitOfMeasuresChangeTracker(
      DataChangeTracker tracker, ClientSession clientSession, Field field) {
    this.tracker = tracker;
    this.clientSession = clientSession;
    this.field = field;
  }

  public void init(OutObject out, AllUnitOfMeasures initialData, AllUnitOfMeasuresRequest inputs) {
    {
      BaseUser currentUser = CurrentUser.get();
      if (!(currentUser instanceof User)) {
        throw new RuntimeException(
            "Current user type does not have read permissions for this ObjectList.");
      }
    }
    initialData._clearChanges();
    this.inputs = new AllUnitOfMeasuresInput(inputs);
    storeInitialData(initialData);
    addSubscriptions();
    out.setId(root.getId());
    disposables.add(tracker.listen(out, field, clientSession));
  }

  @Override
  public void cancel() {
    disposables.forEach((d) -> d.dispose());
  }

  private void storeInitialData(AllUnitOfMeasures initialData) {
    this.root = initialData;
    this.orderBy =
        initialData.getItems().stream()
            .map((x) -> new OrderBy(x.getName(), x.getId()))
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
            SchemaConstants.UnitOfMeasure,
            null,
            (obj, old, type) -> {
              try {
                inputs.load(tracker);
                applyUnitOfMeasure(((UnitOfMeasure) obj), ((UnitOfMeasure) old), type);
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

  public void applyUnitOfMeasure(UnitOfMeasure model, UnitOfMeasure old, StoreEventType type) {
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

  private boolean applyWhere(UnitOfMeasure model) {
    return Objects.equals(model.getOrganization(), inputs.getOrganization());
  }

  private void createInsertChange(UnitOfMeasure model) {
    long id = model.getId();
    String OrderByValue0 = model.getName();
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

  private void createDeleteChange(UnitOfMeasure model) {
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

  private boolean createPathChangeChange(UnitOfMeasure model, UnitOfMeasure old) {
    String oldValue0 = old.getName();
    String modelValue0 = model.getName();
    boolean changed = ObjectUtils.compare(oldValue0, modelValue0) != 0;
    if (!(changed)) {
      return false;
    }
    long id = model.getId();
    TypeAndId existing = find(id);
    if (existing == null) {
      return false;
    }
    String OrderByValue0 = model.getName();
    moveItems(model, existing, OrderByValue0);
    return true;
  }

  private void moveItems(UnitOfMeasure model, TypeAndId data, String OrderByValue0) {
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
