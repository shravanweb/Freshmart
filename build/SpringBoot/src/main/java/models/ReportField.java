package models;

import classes.ReportAggregateType;
import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class ReportField extends DatabaseObject {
  public static final int _NAME = 0;
  public static final int _FIELD = 1;
  public static final int _AGGREGATE = 2;
  private String name;
  private String field;
  private ReportAggregateType aggregate = ReportAggregateType.None;
  private ReportBarChartConfig masterReportBarChartConfig;
  private ReportCardConfig masterReportCardConfig;
  private ReportFunnelChartConfig masterReportFunnelChartConfig;
  private ReportGuageConfig masterReportGuageConfig;
  private ReportKPIConfig masterReportKPIConfig;
  private ReportKeyInfluencerConfig masterReportKeyInfluencerConfig;
  private ReportLineAndAreaChartConfig masterReportLineAndAreaChartConfig;
  private ReportLineAndColumnChartConfig masterReportLineAndColumnChartConfig;
  private ReportMapConfig masterReportMapConfig;
  private ReportMatrixConfig masterReportMatrixConfig;
  private ReportMultiRowCardConfig masterReportMultiRowCardConfig;
  private ReportPieChartConfig masterReportPieChartConfig;
  private ReportScatterChartConfig masterReportScatterChartConfig;
  private ReportSlicerConfig masterReportSlicerConfig;
  private ReportTableConfig masterReportTableConfig;
  private ReportWaterfallChartConfig masterReportWaterfallChartConfig;
  private transient ReportField old;

  public ReportField() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportField;
  }

  @Override
  public String _type() {
    return "ReportField";
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
    if (masterReportBarChartConfig != null) {
      return masterReportBarChartConfig;
    }
    if (masterReportCardConfig != null) {
      return masterReportCardConfig;
    }
    if (masterReportFunnelChartConfig != null) {
      return masterReportFunnelChartConfig;
    }
    if (masterReportGuageConfig != null) {
      return masterReportGuageConfig;
    }
    if (masterReportKPIConfig != null) {
      return masterReportKPIConfig;
    }
    if (masterReportKeyInfluencerConfig != null) {
      return masterReportKeyInfluencerConfig;
    }
    if (masterReportLineAndAreaChartConfig != null) {
      return masterReportLineAndAreaChartConfig;
    }
    if (masterReportLineAndColumnChartConfig != null) {
      return masterReportLineAndColumnChartConfig;
    }
    if (masterReportMapConfig != null) {
      return masterReportMapConfig;
    }
    if (masterReportMatrixConfig != null) {
      return masterReportMatrixConfig;
    }
    if (masterReportMultiRowCardConfig != null) {
      return masterReportMultiRowCardConfig;
    }
    if (masterReportPieChartConfig != null) {
      return masterReportPieChartConfig;
    }
    if (masterReportScatterChartConfig != null) {
      return masterReportScatterChartConfig;
    }
    if (masterReportSlicerConfig != null) {
      return masterReportSlicerConfig;
    }
    if (masterReportTableConfig != null) {
      return masterReportTableConfig;
    }
    if (masterReportWaterfallChartConfig != null) {
      return masterReportWaterfallChartConfig;
    }
    return null;
  }

  public void _setMasterObject(DBObject master) {
    super._setMasterObject(master);
    if (master instanceof ReportBarChartConfig) {
      masterReportBarChartConfig = ((ReportBarChartConfig) master);
    }
    if (master instanceof ReportCardConfig) {
      masterReportCardConfig = ((ReportCardConfig) master);
    }
    if (master instanceof ReportFunnelChartConfig) {
      masterReportFunnelChartConfig = ((ReportFunnelChartConfig) master);
    }
    if (master instanceof ReportGuageConfig) {
      masterReportGuageConfig = ((ReportGuageConfig) master);
    }
    if (master instanceof ReportKPIConfig) {
      masterReportKPIConfig = ((ReportKPIConfig) master);
    }
    if (master instanceof ReportKeyInfluencerConfig) {
      masterReportKeyInfluencerConfig = ((ReportKeyInfluencerConfig) master);
    }
    if (master instanceof ReportLineAndAreaChartConfig) {
      masterReportLineAndAreaChartConfig = ((ReportLineAndAreaChartConfig) master);
    }
    if (master instanceof ReportLineAndColumnChartConfig) {
      masterReportLineAndColumnChartConfig = ((ReportLineAndColumnChartConfig) master);
    }
    if (master instanceof ReportMapConfig) {
      masterReportMapConfig = ((ReportMapConfig) master);
    }
    if (master instanceof ReportMatrixConfig) {
      masterReportMatrixConfig = ((ReportMatrixConfig) master);
    }
    if (master instanceof ReportMultiRowCardConfig) {
      masterReportMultiRowCardConfig = ((ReportMultiRowCardConfig) master);
    }
    if (master instanceof ReportPieChartConfig) {
      masterReportPieChartConfig = ((ReportPieChartConfig) master);
    }
    if (master instanceof ReportScatterChartConfig) {
      masterReportScatterChartConfig = ((ReportScatterChartConfig) master);
    }
    if (master instanceof ReportSlicerConfig) {
      masterReportSlicerConfig = ((ReportSlicerConfig) master);
    }
    if (master instanceof ReportTableConfig) {
      masterReportTableConfig = ((ReportTableConfig) master);
    }
    if (master instanceof ReportWaterfallChartConfig) {
      masterReportWaterfallChartConfig = ((ReportWaterfallChartConfig) master);
    }
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public void updateFlat(DatabaseObject obj) {
    super.updateFlat(obj);
    if (masterReportBarChartConfig != null) {
      masterReportBarChartConfig.updateFlat(obj);
    }
    if (masterReportCardConfig != null) {
      masterReportCardConfig.updateFlat(obj);
    }
    if (masterReportFunnelChartConfig != null) {
      masterReportFunnelChartConfig.updateFlat(obj);
    }
    if (masterReportGuageConfig != null) {
      masterReportGuageConfig.updateFlat(obj);
    }
    if (masterReportKPIConfig != null) {
      masterReportKPIConfig.updateFlat(obj);
    }
    if (masterReportKeyInfluencerConfig != null) {
      masterReportKeyInfluencerConfig.updateFlat(obj);
    }
    if (masterReportLineAndAreaChartConfig != null) {
      masterReportLineAndAreaChartConfig.updateFlat(obj);
    }
    if (masterReportLineAndColumnChartConfig != null) {
      masterReportLineAndColumnChartConfig.updateFlat(obj);
    }
    if (masterReportMapConfig != null) {
      masterReportMapConfig.updateFlat(obj);
    }
    if (masterReportMatrixConfig != null) {
      masterReportMatrixConfig.updateFlat(obj);
    }
    if (masterReportMultiRowCardConfig != null) {
      masterReportMultiRowCardConfig.updateFlat(obj);
    }
    if (masterReportPieChartConfig != null) {
      masterReportPieChartConfig.updateFlat(obj);
    }
    if (masterReportScatterChartConfig != null) {
      masterReportScatterChartConfig.updateFlat(obj);
    }
    if (masterReportSlicerConfig != null) {
      masterReportSlicerConfig.updateFlat(obj);
    }
    if (masterReportTableConfig != null) {
      masterReportTableConfig.updateFlat(obj);
    }
    if (masterReportWaterfallChartConfig != null) {
      masterReportWaterfallChartConfig.updateFlat(obj);
    }
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

  public String getField() {
    _checkProxy();
    return this.field;
  }

  public void setField(String field) {
    _checkProxy();
    if (Objects.equals(this.field, field)) {
      return;
    }
    fieldChanged(_FIELD, this.field, field);
    this.field = field;
  }

  public ReportAggregateType getAggregate() {
    _checkProxy();
    return this.aggregate;
  }

  public void setAggregate(ReportAggregateType aggregate) {
    _checkProxy();
    if (Objects.equals(this.aggregate, aggregate)) {
      return;
    }
    fieldChanged(_AGGREGATE, this.aggregate, aggregate);
    this.aggregate = aggregate;
  }

  public ReportBarChartConfig getMasterReportBarChartConfig() {
    return this.masterReportBarChartConfig;
  }

  public void setMasterReportBarChartConfig(ReportBarChartConfig masterReportBarChartConfig) {
    this.masterReportBarChartConfig = masterReportBarChartConfig;
  }

  public ReportCardConfig getMasterReportCardConfig() {
    return this.masterReportCardConfig;
  }

  public void setMasterReportCardConfig(ReportCardConfig masterReportCardConfig) {
    this.masterReportCardConfig = masterReportCardConfig;
  }

  public ReportFunnelChartConfig getMasterReportFunnelChartConfig() {
    return this.masterReportFunnelChartConfig;
  }

  public void setMasterReportFunnelChartConfig(
      ReportFunnelChartConfig masterReportFunnelChartConfig) {
    this.masterReportFunnelChartConfig = masterReportFunnelChartConfig;
  }

  public ReportGuageConfig getMasterReportGuageConfig() {
    return this.masterReportGuageConfig;
  }

  public void setMasterReportGuageConfig(ReportGuageConfig masterReportGuageConfig) {
    this.masterReportGuageConfig = masterReportGuageConfig;
  }

  public ReportKPIConfig getMasterReportKPIConfig() {
    return this.masterReportKPIConfig;
  }

  public void setMasterReportKPIConfig(ReportKPIConfig masterReportKPIConfig) {
    this.masterReportKPIConfig = masterReportKPIConfig;
  }

  public ReportKeyInfluencerConfig getMasterReportKeyInfluencerConfig() {
    return this.masterReportKeyInfluencerConfig;
  }

  public void setMasterReportKeyInfluencerConfig(
      ReportKeyInfluencerConfig masterReportKeyInfluencerConfig) {
    this.masterReportKeyInfluencerConfig = masterReportKeyInfluencerConfig;
  }

  public ReportLineAndAreaChartConfig getMasterReportLineAndAreaChartConfig() {
    return this.masterReportLineAndAreaChartConfig;
  }

  public void setMasterReportLineAndAreaChartConfig(
      ReportLineAndAreaChartConfig masterReportLineAndAreaChartConfig) {
    this.masterReportLineAndAreaChartConfig = masterReportLineAndAreaChartConfig;
  }

  public ReportLineAndColumnChartConfig getMasterReportLineAndColumnChartConfig() {
    return this.masterReportLineAndColumnChartConfig;
  }

  public void setMasterReportLineAndColumnChartConfig(
      ReportLineAndColumnChartConfig masterReportLineAndColumnChartConfig) {
    this.masterReportLineAndColumnChartConfig = masterReportLineAndColumnChartConfig;
  }

  public ReportMapConfig getMasterReportMapConfig() {
    return this.masterReportMapConfig;
  }

  public void setMasterReportMapConfig(ReportMapConfig masterReportMapConfig) {
    this.masterReportMapConfig = masterReportMapConfig;
  }

  public ReportMatrixConfig getMasterReportMatrixConfig() {
    return this.masterReportMatrixConfig;
  }

  public void setMasterReportMatrixConfig(ReportMatrixConfig masterReportMatrixConfig) {
    this.masterReportMatrixConfig = masterReportMatrixConfig;
  }

  public ReportMultiRowCardConfig getMasterReportMultiRowCardConfig() {
    return this.masterReportMultiRowCardConfig;
  }

  public void setMasterReportMultiRowCardConfig(
      ReportMultiRowCardConfig masterReportMultiRowCardConfig) {
    this.masterReportMultiRowCardConfig = masterReportMultiRowCardConfig;
  }

  public ReportPieChartConfig getMasterReportPieChartConfig() {
    return this.masterReportPieChartConfig;
  }

  public void setMasterReportPieChartConfig(ReportPieChartConfig masterReportPieChartConfig) {
    this.masterReportPieChartConfig = masterReportPieChartConfig;
  }

  public ReportScatterChartConfig getMasterReportScatterChartConfig() {
    return this.masterReportScatterChartConfig;
  }

  public void setMasterReportScatterChartConfig(
      ReportScatterChartConfig masterReportScatterChartConfig) {
    this.masterReportScatterChartConfig = masterReportScatterChartConfig;
  }

  public ReportSlicerConfig getMasterReportSlicerConfig() {
    return this.masterReportSlicerConfig;
  }

  public void setMasterReportSlicerConfig(ReportSlicerConfig masterReportSlicerConfig) {
    this.masterReportSlicerConfig = masterReportSlicerConfig;
  }

  public ReportTableConfig getMasterReportTableConfig() {
    return this.masterReportTableConfig;
  }

  public void setMasterReportTableConfig(ReportTableConfig masterReportTableConfig) {
    this.masterReportTableConfig = masterReportTableConfig;
  }

  public ReportWaterfallChartConfig getMasterReportWaterfallChartConfig() {
    return this.masterReportWaterfallChartConfig;
  }

  public void setMasterReportWaterfallChartConfig(
      ReportWaterfallChartConfig masterReportWaterfallChartConfig) {
    this.masterReportWaterfallChartConfig = masterReportWaterfallChartConfig;
  }

  public ReportField getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportField) old);
  }

  public String displayName() {
    return "ReportField";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportField && super.equals(a);
  }

  public ReportField deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportField _obj = ((ReportField) dbObj);
    _obj.setName(name);
    _obj.setField(field);
    _obj.setAggregate(aggregate);
  }

  public ReportField cloneInstance(ReportField cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportField();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setName(this.getName());
    cloneObj.setField(this.getField());
    cloneObj.setAggregate(this.getAggregate());
    return cloneObj;
  }

  public ReportField createNewInstance() {
    return new ReportField();
  }

  public boolean needOldObject() {
    return true;
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
