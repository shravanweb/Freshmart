package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.D3EPersistanceList;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class ReportNamedConditionFilter extends ReportFilter {
  public static final int _NAME = 0;
  public static final int _CONDITIONS = 1;
  private String name;
  private transient List<ReportNamedCondition> conditions =
      D3EPersistanceList.reference(this, _CONDITIONS);
  private transient ReportNamedConditionFilter old;

  public ReportNamedConditionFilter() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportNamedConditionFilter;
  }

  @Override
  public String _type() {
    return "ReportNamedConditionFilter";
  }

  @Override
  public int _fieldsCount() {
    return 2;
  }

  public void addToConditions(ReportNamedCondition val, long index) {
    if (index == -1) {
      this.conditions.add(val);
    } else {
      this.conditions.add(((int) index), val);
    }
  }

  public void removeFromConditions(ReportNamedCondition val) {
    this.conditions.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public String getName() {
    _checkProxy();
    return this.name;
  }

  public void setName(String name) {
    _checkProxy();
    if (Objects.equals(this.name, name)) {
      return;
    }
    fieldChanged(_NAME, this.name, name);
    this.name = name;
  }

  public List<ReportNamedCondition> getConditions() {
    return this.conditions;
  }

  public void setConditions(List<ReportNamedCondition> conditions) {
    if (Objects.equals(this.conditions, conditions)) {
      return;
    }
    ((D3EPersistanceList<ReportNamedCondition>) this.conditions).setAll(conditions);
  }

  public ReportNamedConditionFilter getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportNamedConditionFilter) old);
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportNamedConditionFilter && super.equals(a);
  }

  public ReportNamedConditionFilter deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportNamedConditionFilter _obj = ((ReportNamedConditionFilter) dbObj);
    _obj.setName(name);
    _obj.setConditions(ctx.cloneRefList(conditions));
  }

  public ReportNamedConditionFilter cloneInstance(ReportNamedConditionFilter cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportNamedConditionFilter();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setName(this.getName());
    cloneObj.setConditions(new ArrayList<>(this.getConditions()));
    return cloneObj;
  }

  public ReportNamedConditionFilter createNewInstance() {
    return new ReportNamedConditionFilter();
  }

  public boolean needOldObject() {
    return true;
  }

  @Override
  public boolean _isEntity() {
    return true;
  }

  @Override
  protected void _handleChildChange(int _childIdx, boolean set) {
    super._handleChildChange(_childIdx, set);
  }
}
