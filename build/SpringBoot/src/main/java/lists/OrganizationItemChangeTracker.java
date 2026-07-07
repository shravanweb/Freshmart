package lists;

import classes.IdGenerator;
import classes.OrganizationItem;
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
import models.Organization;
import models.OrganizationItemRequest;
import models.User;
import rest.ws.ClientSession;
import rest.ws.DataChangeTracker;
import store.StoreEventType;

public class OrganizationItemChangeTracker implements Cancellable {
  private class OrganizationItemInput {
    private TypeAndId organizationRef;
    private Organization organization;

    public OrganizationItemInput(OrganizationItemRequest _req) {
      this.organizationRef = TypeAndId.from(_req.getOrganization());
      if (_req.getOrganization() == null) {
        Log.info(
            "WARNING: Input \\\"organization\\\" in \\\"OrganizationItem\\\" is required, but got value null.");
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

  private OrganizationItem root;
  private DataChangeTracker tracker;
  private ClientSession clientSession;
  private List<Disposable> disposables = ListExt.List();
  private OrganizationItemInput inputs;
  private Field field;

  public OrganizationItemChangeTracker(
      DataChangeTracker tracker, ClientSession clientSession, Field field) {
    this.tracker = tracker;
    this.clientSession = clientSession;
    this.field = field;
  }

  public void init(OutObject out, OrganizationItem initialData, OrganizationItemRequest inputs) {
    {
      BaseUser currentUser = CurrentUser.get();
      if (!(currentUser instanceof User)) {
        throw new RuntimeException(
            "Current user type does not have read permissions for this ObjectList.");
      }
    }
    initialData._clearChanges();
    this.inputs = new OrganizationItemInput(inputs);
    storeInitialData(initialData);
    addSubscriptions();
    out.setId(root.getId());
    disposables.add(tracker.listen(out, field, clientSession));
  }

  @Override
  public void cancel() {
    disposables.forEach((d) -> d.dispose());
  }

  private void storeInitialData(OrganizationItem initialData) {
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
            SchemaConstants.Organization,
            null,
            (obj, old, type) -> {
              try {
                inputs.load(tracker);
                applyOrganization(((Organization) obj), ((Organization) old), type);
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

  public void applyOrganization(Organization model, Organization old, StoreEventType type) {
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

  private boolean applyWhere(Organization model) {
    return Objects.equals(model, inputs.getOrganization());
  }

  private void createInsertChange(Organization model) {
    root.addToItemsRef(new TypeAndId(model._typeIdx(), model.getId()), -1);
    fire();
  }

  private void createDeleteChange(Organization model) {
    long id = model.getId();
    TypeAndId existing = find(id);
    if (existing == null) {
      return;
    }
    root.removeFromItemsRef(existing);
    fire();
  }
}
