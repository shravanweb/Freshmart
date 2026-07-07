package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import store.D3EPersistanceList;
import store.DBObject;
import store.Database;
import store.DatabaseObject;
import store.ICloneable;

public class ReportRuleSet extends ReportRule {
  public static final int _ALL = 1;
  public static final int _RULES = 2;
  private boolean all = false;
  private transient List<ReportRule> rules = D3EPersistanceList.child(this, _RULES);
  private Report masterReport;
  private transient ReportRuleSet old;

  public ReportRuleSet() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportRuleSet;
  }

  @Override
  public String _type() {
    return "ReportRuleSet";
  }

  @Override
  public int _fieldsCount() {
    return 3;
  }

  public DatabaseObject _masterObject() {
    DatabaseObject master = super._masterObject();
    if (master != null) {
      return master;
    }
    if (masterReport != null) {
      return masterReport;
    }
    return null;
  }

  public void _setMasterObject(DBObject master) {
    super._setMasterObject(master);
    if (master instanceof Report) {
      masterReport = ((Report) master);
    }
  }

  public void addToRules(ReportRule val, long index) {
    if (index == -1) {
      this.rules.add(val);
    } else {
      this.rules.add(((int) index), val);
    }
  }

  public void removeFromRules(ReportRule val) {
    val._clearChildIdx();
    this.rules.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
    for (ReportRule obj : this.getRules()) {
      visitor.accept(obj);
      obj.setMasterReportRuleSet(this);
      obj._setChildIdx(_RULES);
      obj.updateMasters(visitor);
    }
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
    for (ReportRule obj : this.getRules()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
  }

  public void updateFlat(DatabaseObject obj) {
    super.updateFlat(obj);
    if (masterReport != null) {
      masterReport.updateFlat(obj);
    }
  }

  public boolean isAll() {
    _checkProxy();
    return this.all;
  }

  public void setAll(boolean all) {
    _checkProxy();
    if (Objects.equals(this.all, all)) {
      return;
    }
    fieldChanged(_ALL, this.all, all);
    this.all = all;
  }

  public List<ReportRule> getRules() {
    return this.rules;
  }

  public void setRules(List<ReportRule> rules) {
    if (Objects.equals(this.rules, rules)) {
      return;
    }
    ((D3EPersistanceList<ReportRule>) this.rules).setAll(rules);
  }

  public Report getMasterReport() {
    return this.masterReport;
  }

  public void setMasterReport(Report masterReport) {
    this.masterReport = masterReport;
  }

  public ReportRuleSet getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportRuleSet) old);
  }

  public void recordOld(CloneContext ctx) {
    this.setOld(ctx.getFromCache(this));
    this.getRules().forEach((one) -> one.recordOld(ctx));
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportRuleSet && super.equals(a);
  }

  public ReportRuleSet deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChilds(rules);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportRuleSet _obj = ((ReportRuleSet) dbObj);
    _obj.setAll(all);
    ctx.cloneChildList(rules, (v) -> _obj.setRules(v));
  }

  public ReportRuleSet cloneInstance(ReportRuleSet cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportRuleSet();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setAll(this.isAll());
    cloneObj.setRules(
        this.getRules().stream()
            .map((ReportRule colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    return cloneObj;
  }

  public ReportRuleSet createNewInstance() {
    return new ReportRuleSet();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCollctionCreatableReferences(_refs, this.rules);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }

  @Override
  protected void _handleChildChange(int _childIdx, boolean set) {
    switch (_childIdx) {
      case _RULES:
        {
          this.childCollFieldChanged(_childIdx, set, this.rules);
          break;
        }
      default:
        {
          super._handleChildChange(_childIdx, set);
        }
    }
  }
}
