package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class AllUnitOfMeasuresRequest extends CreatableObject {
  public static final int _ORGANIZATION = 0;
  private Organization organization;

  public AllUnitOfMeasuresRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.AllUnitOfMeasuresRequest;
  }

  @Override
  public String _type() {
    return "AllUnitOfMeasuresRequest";
  }

  @Override
  public int _fieldsCount() {
    return 1;
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public Organization getOrganization() {
    _checkProxy();
    return this.organization;
  }

  public void setOrganization(Organization organization) {
    _checkProxy();
    if (Objects.equals(this.organization, organization)) {
      return;
    }
    fieldChanged(_ORGANIZATION, this.organization, organization);
    this.organization = organization;
  }

  public String displayName() {
    return "AllUnitOfMeasuresRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof AllUnitOfMeasuresRequest && super.equals(a);
  }

  public AllUnitOfMeasuresRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    AllUnitOfMeasuresRequest _obj = ((AllUnitOfMeasuresRequest) dbObj);
    _obj.setOrganization(organization);
  }

  public AllUnitOfMeasuresRequest cloneInstance(AllUnitOfMeasuresRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new AllUnitOfMeasuresRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public AllUnitOfMeasuresRequest createNewInstance() {
    return new AllUnitOfMeasuresRequest();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
