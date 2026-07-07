package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.D3EPersistanceList;
import store.DBObject;
import store.Database;
import store.DatabaseObject;
import store.ICloneable;

@SolrDocument(collection = "Report")
@Entity
public class Report extends CreatableObject {
  public static final int _MODEL = 0;
  public static final int _NAME = 1;
  public static final int _CELLS = 2;
  public static final int _CONFIG = 3;
  public static final int _FILTERS = 4;
  public static final int _CRITERIA = 5;
  public static final int _FLATREPORTRULE = 6;
  @NotNull private String model;
  private String name;
  private transient ReportCell cells;
  private transient ReportBaseConfig config;
  private transient List<ReportFilter> filters = D3EPersistanceList.child(this, _FILTERS);
  private transient ReportRuleSet criteria;
  private transient List<ReportRule> flatReportRule = new ArrayList<>();

  public Report() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.Report;
  }

  @Override
  public String _type() {
    return "Report";
  }

  @Override
  public int _fieldsCount() {
    return 7;
  }

  public void addToFilters(ReportFilter val, long index) {
    if (index == -1) {
      this.filters.add(val);
    } else {
      this.filters.add(((int) index), val);
    }
  }

  public void removeFromFilters(ReportFilter val) {
    val._clearChildIdx();
    this.filters.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    flatReportRule.clear();
    super.updateMasters(visitor);
    if (cells != null) {
      visitor.accept(cells);
      cells.setMasterReport(this);
      cells.updateMasters(visitor);
    }
    if (config != null) {
      visitor.accept(config);
      config.setMasterReport(this);
      config.updateMasters(visitor);
    }
    for (ReportFilter obj : this.getFilters()) {
      visitor.accept(obj);
      obj.setMasterReport(this);
      obj._setChildIdx(_FILTERS);
      obj.updateMasters(visitor);
    }
    if (criteria != null) {
      visitor.accept(criteria);
      criteria.setMasterReport(this);
      criteria.updateMasters(visitor);
    }
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
    if (cells != null) {
      visitor.accept(cells);
      cells.visitChildren(visitor);
    }
    if (config != null) {
      visitor.accept(config);
      config.visitChildren(visitor);
    }
    for (ReportFilter obj : this.getFilters()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    if (criteria != null) {
      visitor.accept(criteria);
      criteria.visitChildren(visitor);
    }
  }

  public void updateFlat(DatabaseObject obj) {
    super.updateFlat(obj);
    if (obj instanceof ReportRule) {
      flatReportRule.add(((ReportRule) obj));
    }
  }

  public String getModel() {
    _checkProxy();
    return this.model;
  }

  public void setModel(String model) {
    _checkProxy();
    if (Objects.equals(this.model, model)) {
      return;
    }
    fieldChanged(_MODEL, this.model, model);
    this.model = model;
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

  public ReportCell getCells() {
    _checkProxy();
    return this.cells;
  }

  public void setCells(ReportCell cells) {
    _checkProxy();
    if (Objects.equals(this.cells, cells)) {
      if (this.cells != null) {
        this.cells._updateChanges();
      }
      return;
    }
    fieldChanged(_CELLS, this.cells, cells);
    this.cells = cells;
    if (this.cells != null) {
      this.cells.setMasterReport(this);
      this.cells._setChildIdx(_CELLS);
      this.cells._updateChanges();
    }
  }

  public ReportBaseConfig getConfig() {
    _checkProxy();
    return this.config;
  }

  public void setConfig(ReportBaseConfig config) {
    _checkProxy();
    if (Objects.equals(this.config, config)) {
      if (this.config != null) {
        this.config._updateChanges();
      }
      return;
    }
    fieldChanged(_CONFIG, this.config, config);
    this.config = config;
    if (this.config != null) {
      this.config.setMasterReport(this);
      this.config._setChildIdx(_CONFIG);
      this.config._updateChanges();
    }
  }

  public List<ReportFilter> getFilters() {
    return this.filters;
  }

  public void setFilters(List<ReportFilter> filters) {
    if (Objects.equals(this.filters, filters)) {
      return;
    }
    ((D3EPersistanceList<ReportFilter>) this.filters).setAll(filters);
  }

  public ReportRuleSet getCriteria() {
    _checkProxy();
    return this.criteria;
  }

  public void setCriteria(ReportRuleSet criteria) {
    _checkProxy();
    if (Objects.equals(this.criteria, criteria)) {
      if (this.criteria != null) {
        this.criteria._updateChanges();
      }
      return;
    }
    fieldChanged(_CRITERIA, this.criteria, criteria);
    this.criteria = criteria;
    if (this.criteria != null) {
      this.criteria.setMasterReport(this);
      this.criteria._setChildIdx(_CRITERIA);
      this.criteria._updateChanges();
    }
  }

  public List<ReportRule> getFlatReportRule() {
    return this.flatReportRule;
  }

  public String displayName() {
    return "Report";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof Report && super.equals(a);
  }

  public Report deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChild(cells);
    ctx.collectChild(config);
    ctx.collectChilds(filters);
    ctx.collectChild(criteria);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    Report _obj = ((Report) dbObj);
    _obj.setModel(model);
    _obj.setName(name);
    ctx.cloneChild(cells, (v) -> _obj.setCells(v));
    ctx.cloneChild(config, (v) -> _obj.setConfig(v));
    ctx.cloneChildList(filters, (v) -> _obj.setFilters(v));
    ctx.cloneChild(criteria, (v) -> _obj.setCriteria(v));
  }

  public Report cloneInstance(Report cloneObj) {
    if (cloneObj == null) {
      cloneObj = new Report();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setModel(this.getModel());
    cloneObj.setName(this.getName());
    cloneObj.setCells(this.getCells() == null ? null : this.getCells().cloneInstance(null));
    cloneObj.setConfig(this.getConfig() == null ? null : this.getConfig().cloneInstance(null));
    cloneObj.setFilters(
        this.getFilters().stream()
            .map((ReportFilter colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setCriteria(
        this.getCriteria() == null ? null : this.getCriteria().cloneInstance(null));
    return cloneObj;
  }

  public Report createNewInstance() {
    return new Report();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCreatableReferences(_refs, this.cells);
    Database.collectCreatableReferences(_refs, this.config);
    Database.collectCollctionCreatableReferences(_refs, this.filters);
    Database.collectCreatableReferences(_refs, this.criteria);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }

  @Override
  protected void _handleChildChange(int _childIdx, boolean set) {
    switch (_childIdx) {
      case _CELLS:
        {
          this.childFieldChanged(_childIdx, set);
          break;
        }
      case _CONFIG:
        {
          this.childFieldChanged(_childIdx, set);
          break;
        }
      case _FILTERS:
        {
          this.childCollFieldChanged(_childIdx, set, this.filters);
          break;
        }
      case _CRITERIA:
        {
          this.childFieldChanged(_childIdx, set);
          break;
        }
    }
  }
}
