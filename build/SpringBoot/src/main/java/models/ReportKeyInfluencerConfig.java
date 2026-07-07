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

public class ReportKeyInfluencerConfig extends ReportBaseConfig {
  public static final int _ANALYZE = 0;
  public static final int _EEXPLAINBY = 1;
  public static final int _EXPANDBY = 2;
  private transient ReportField analyze;
  private transient List<ReportField> eexplainBy = D3EPersistanceList.child(this, _EEXPLAINBY);
  private transient List<ReportField> expandBy = D3EPersistanceList.child(this, _EXPANDBY);
  private transient ReportKeyInfluencerConfig old;

  public ReportKeyInfluencerConfig() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportKeyInfluencerConfig;
  }

  @Override
  public String _type() {
    return "ReportKeyInfluencerConfig";
  }

  @Override
  public int _fieldsCount() {
    return 3;
  }

  public void addToEexplainBy(ReportField val, long index) {
    if (index == -1) {
      this.eexplainBy.add(val);
    } else {
      this.eexplainBy.add(((int) index), val);
    }
  }

  public void removeFromEexplainBy(ReportField val) {
    val._clearChildIdx();
    this.eexplainBy.remove(val);
  }

  public void addToExpandBy(ReportField val, long index) {
    if (index == -1) {
      this.expandBy.add(val);
    } else {
      this.expandBy.add(((int) index), val);
    }
  }

  public void removeFromExpandBy(ReportField val) {
    val._clearChildIdx();
    this.expandBy.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
    if (analyze != null) {
      visitor.accept(analyze);
      analyze.setMasterReportKeyInfluencerConfig(this);
      analyze.updateMasters(visitor);
    }
    for (ReportField obj : this.getEexplainBy()) {
      visitor.accept(obj);
      obj.setMasterReportKeyInfluencerConfig(this);
      obj._setChildIdx(_EEXPLAINBY);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getExpandBy()) {
      visitor.accept(obj);
      obj.setMasterReportKeyInfluencerConfig(this);
      obj._setChildIdx(_EXPANDBY);
      obj.updateMasters(visitor);
    }
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
    if (analyze != null) {
      visitor.accept(analyze);
      analyze.visitChildren(visitor);
    }
    for (ReportField obj : this.getEexplainBy()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getExpandBy()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
  }

  public ReportField getAnalyze() {
    _checkProxy();
    return this.analyze;
  }

  public void setAnalyze(ReportField analyze) {
    _checkProxy();
    if (Objects.equals(this.analyze, analyze)) {
      if (this.analyze != null) {
        this.analyze._updateChanges();
      }
      return;
    }
    fieldChanged(_ANALYZE, this.analyze, analyze);
    this.analyze = analyze;
    if (this.analyze != null) {
      this.analyze.setMasterReportKeyInfluencerConfig(this);
      this.analyze._setChildIdx(_ANALYZE);
      this.analyze._updateChanges();
    }
  }

  public List<ReportField> getEexplainBy() {
    return this.eexplainBy;
  }

  public void setEexplainBy(List<ReportField> eexplainBy) {
    if (Objects.equals(this.eexplainBy, eexplainBy)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.eexplainBy).setAll(eexplainBy);
  }

  public List<ReportField> getExpandBy() {
    return this.expandBy;
  }

  public void setExpandBy(List<ReportField> expandBy) {
    if (Objects.equals(this.expandBy, expandBy)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.expandBy).setAll(expandBy);
  }

  public ReportKeyInfluencerConfig getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportKeyInfluencerConfig) old);
  }

  public void recordOld(CloneContext ctx) {
    this.setOld(ctx.getFromCache(this));
    if (this.getAnalyze() != null) {
      this.getAnalyze().recordOld(ctx);
    }
    this.getEexplainBy().forEach((one) -> one.recordOld(ctx));
    this.getExpandBy().forEach((one) -> one.recordOld(ctx));
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportKeyInfluencerConfig && super.equals(a);
  }

  public ReportKeyInfluencerConfig deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChild(analyze);
    ctx.collectChilds(eexplainBy);
    ctx.collectChilds(expandBy);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportKeyInfluencerConfig _obj = ((ReportKeyInfluencerConfig) dbObj);
    ctx.cloneChild(analyze, (v) -> _obj.setAnalyze(v));
    ctx.cloneChildList(eexplainBy, (v) -> _obj.setEexplainBy(v));
    ctx.cloneChildList(expandBy, (v) -> _obj.setExpandBy(v));
  }

  public ReportKeyInfluencerConfig cloneInstance(ReportKeyInfluencerConfig cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportKeyInfluencerConfig();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setAnalyze(this.getAnalyze() == null ? null : this.getAnalyze().cloneInstance(null));
    cloneObj.setEexplainBy(
        this.getEexplainBy().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setExpandBy(
        this.getExpandBy().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    return cloneObj;
  }

  public ReportKeyInfluencerConfig createNewInstance() {
    return new ReportKeyInfluencerConfig();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCreatableReferences(_refs, this.analyze);
    Database.collectCollctionCreatableReferences(_refs, this.eexplainBy);
    Database.collectCollctionCreatableReferences(_refs, this.expandBy);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }

  @Override
  protected void _handleChildChange(int _childIdx, boolean set) {
    switch (_childIdx) {
      case _ANALYZE:
        {
          this.childFieldChanged(_childIdx, set);
          break;
        }
      case _EEXPLAINBY:
        {
          this.childCollFieldChanged(_childIdx, set, this.eexplainBy);
          break;
        }
      case _EXPANDBY:
        {
          this.childCollFieldChanged(_childIdx, set, this.expandBy);
          break;
        }
      default:
        {
          super._handleChildChange(_childIdx, set);
        }
    }
  }
}
