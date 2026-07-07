package models;

import classes.ReportMapType;
import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import store.D3EPersistanceList;
import store.DBObject;
import store.Database;
import store.DatabaseObject;
import store.ICloneable;

public class ReportMapConfig extends ReportBaseConfig {
  public static final int _TYPE = 0;
  public static final int _LOCATION = 1;
  public static final int _LEGEND = 2;
  public static final int _LATITUDE = 3;
  public static final int _LONGITUDE = 4;
  public static final int _SIZE = 5;
  public static final int _TOOLTIPS = 6;
  @NotNull private ReportMapType type = ReportMapType.Map;
  private transient List<ReportField> location = D3EPersistanceList.child(this, _LOCATION);
  private transient List<ReportField> legend = D3EPersistanceList.child(this, _LEGEND);
  private transient ReportField latitude;
  private transient ReportField longitude;
  private transient List<ReportField> size = D3EPersistanceList.child(this, _SIZE);
  private transient List<ReportField> tooltips = D3EPersistanceList.child(this, _TOOLTIPS);
  private transient ReportMapConfig old;

  public ReportMapConfig() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportMapConfig;
  }

  @Override
  public String _type() {
    return "ReportMapConfig";
  }

  @Override
  public int _fieldsCount() {
    return 7;
  }

  public void addToLocation(ReportField val, long index) {
    if (index == -1) {
      this.location.add(val);
    } else {
      this.location.add(((int) index), val);
    }
  }

  public void removeFromLocation(ReportField val) {
    val._clearChildIdx();
    this.location.remove(val);
  }

  public void addToLegend(ReportField val, long index) {
    if (index == -1) {
      this.legend.add(val);
    } else {
      this.legend.add(((int) index), val);
    }
  }

  public void removeFromLegend(ReportField val) {
    val._clearChildIdx();
    this.legend.remove(val);
  }

  public void addToSize(ReportField val, long index) {
    if (index == -1) {
      this.size.add(val);
    } else {
      this.size.add(((int) index), val);
    }
  }

  public void removeFromSize(ReportField val) {
    val._clearChildIdx();
    this.size.remove(val);
  }

  public void addToTooltips(ReportField val, long index) {
    if (index == -1) {
      this.tooltips.add(val);
    } else {
      this.tooltips.add(((int) index), val);
    }
  }

  public void removeFromTooltips(ReportField val) {
    val._clearChildIdx();
    this.tooltips.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
    for (ReportField obj : this.getLocation()) {
      visitor.accept(obj);
      obj.setMasterReportMapConfig(this);
      obj._setChildIdx(_LOCATION);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getLegend()) {
      visitor.accept(obj);
      obj.setMasterReportMapConfig(this);
      obj._setChildIdx(_LEGEND);
      obj.updateMasters(visitor);
    }
    if (latitude != null) {
      visitor.accept(latitude);
      latitude.setMasterReportMapConfig(this);
      latitude.updateMasters(visitor);
    }
    if (longitude != null) {
      visitor.accept(longitude);
      longitude.setMasterReportMapConfig(this);
      longitude.updateMasters(visitor);
    }
    for (ReportField obj : this.getSize()) {
      visitor.accept(obj);
      obj.setMasterReportMapConfig(this);
      obj._setChildIdx(_SIZE);
      obj.updateMasters(visitor);
    }
    for (ReportField obj : this.getTooltips()) {
      visitor.accept(obj);
      obj.setMasterReportMapConfig(this);
      obj._setChildIdx(_TOOLTIPS);
      obj.updateMasters(visitor);
    }
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
    for (ReportField obj : this.getLocation()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getLegend()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    if (latitude != null) {
      visitor.accept(latitude);
      latitude.visitChildren(visitor);
    }
    if (longitude != null) {
      visitor.accept(longitude);
      longitude.visitChildren(visitor);
    }
    for (ReportField obj : this.getSize()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
    for (ReportField obj : this.getTooltips()) {
      visitor.accept(obj);
      obj.visitChildren(visitor);
    }
  }

  public ReportMapType getType() {
    _checkProxy();
    return this.type;
  }

  public void setType(ReportMapType type) {
    _checkProxy();
    if (Objects.equals(this.type, type)) {
      return;
    }
    fieldChanged(_TYPE, this.type, type);
    this.type = type;
  }

  public List<ReportField> getLocation() {
    return this.location;
  }

  public void setLocation(List<ReportField> location) {
    if (Objects.equals(this.location, location)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.location).setAll(location);
  }

  public List<ReportField> getLegend() {
    return this.legend;
  }

  public void setLegend(List<ReportField> legend) {
    if (Objects.equals(this.legend, legend)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.legend).setAll(legend);
  }

  public ReportField getLatitude() {
    _checkProxy();
    return this.latitude;
  }

  public void setLatitude(ReportField latitude) {
    _checkProxy();
    if (Objects.equals(this.latitude, latitude)) {
      if (this.latitude != null) {
        this.latitude._updateChanges();
      }
      return;
    }
    fieldChanged(_LATITUDE, this.latitude, latitude);
    this.latitude = latitude;
    if (this.latitude != null) {
      this.latitude.setMasterReportMapConfig(this);
      this.latitude._setChildIdx(_LATITUDE);
      this.latitude._updateChanges();
    }
  }

  public ReportField getLongitude() {
    _checkProxy();
    return this.longitude;
  }

  public void setLongitude(ReportField longitude) {
    _checkProxy();
    if (Objects.equals(this.longitude, longitude)) {
      if (this.longitude != null) {
        this.longitude._updateChanges();
      }
      return;
    }
    fieldChanged(_LONGITUDE, this.longitude, longitude);
    this.longitude = longitude;
    if (this.longitude != null) {
      this.longitude.setMasterReportMapConfig(this);
      this.longitude._setChildIdx(_LONGITUDE);
      this.longitude._updateChanges();
    }
  }

  public List<ReportField> getSize() {
    return this.size;
  }

  public void setSize(List<ReportField> size) {
    if (Objects.equals(this.size, size)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.size).setAll(size);
  }

  public List<ReportField> getTooltips() {
    return this.tooltips;
  }

  public void setTooltips(List<ReportField> tooltips) {
    if (Objects.equals(this.tooltips, tooltips)) {
      return;
    }
    ((D3EPersistanceList<ReportField>) this.tooltips).setAll(tooltips);
  }

  public ReportMapConfig getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportMapConfig) old);
  }

  public void recordOld(CloneContext ctx) {
    this.setOld(ctx.getFromCache(this));
    this.getLocation().forEach((one) -> one.recordOld(ctx));
    this.getLegend().forEach((one) -> one.recordOld(ctx));
    if (this.getLatitude() != null) {
      this.getLatitude().recordOld(ctx);
    }
    if (this.getLongitude() != null) {
      this.getLongitude().recordOld(ctx);
    }
    this.getSize().forEach((one) -> one.recordOld(ctx));
    this.getTooltips().forEach((one) -> one.recordOld(ctx));
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportMapConfig && super.equals(a);
  }

  public ReportMapConfig deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void collectChildValues(CloneContext ctx) {
    super.collectChildValues(ctx);
    ctx.collectChilds(location);
    ctx.collectChilds(legend);
    ctx.collectChild(latitude);
    ctx.collectChild(longitude);
    ctx.collectChilds(size);
    ctx.collectChilds(tooltips);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportMapConfig _obj = ((ReportMapConfig) dbObj);
    _obj.setType(type);
    ctx.cloneChildList(location, (v) -> _obj.setLocation(v));
    ctx.cloneChildList(legend, (v) -> _obj.setLegend(v));
    ctx.cloneChild(latitude, (v) -> _obj.setLatitude(v));
    ctx.cloneChild(longitude, (v) -> _obj.setLongitude(v));
    ctx.cloneChildList(size, (v) -> _obj.setSize(v));
    ctx.cloneChildList(tooltips, (v) -> _obj.setTooltips(v));
  }

  public ReportMapConfig cloneInstance(ReportMapConfig cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportMapConfig();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setType(this.getType());
    cloneObj.setLocation(
        this.getLocation().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setLegend(
        this.getLegend().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setLatitude(
        this.getLatitude() == null ? null : this.getLatitude().cloneInstance(null));
    cloneObj.setLongitude(
        this.getLongitude() == null ? null : this.getLongitude().cloneInstance(null));
    cloneObj.setSize(
        this.getSize().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    cloneObj.setTooltips(
        this.getTooltips().stream()
            .map((ReportField colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    return cloneObj;
  }

  public ReportMapConfig createNewInstance() {
    return new ReportMapConfig();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    Database.collectCollctionCreatableReferences(_refs, this.location);
    Database.collectCollctionCreatableReferences(_refs, this.legend);
    Database.collectCreatableReferences(_refs, this.latitude);
    Database.collectCreatableReferences(_refs, this.longitude);
    Database.collectCollctionCreatableReferences(_refs, this.size);
    Database.collectCollctionCreatableReferences(_refs, this.tooltips);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }

  @Override
  protected void _handleChildChange(int _childIdx, boolean set) {
    switch (_childIdx) {
      case _LOCATION:
        {
          this.childCollFieldChanged(_childIdx, set, this.location);
          break;
        }
      case _LEGEND:
        {
          this.childCollFieldChanged(_childIdx, set, this.legend);
          break;
        }
      case _LATITUDE:
        {
          this.childFieldChanged(_childIdx, set);
          break;
        }
      case _LONGITUDE:
        {
          this.childFieldChanged(_childIdx, set);
          break;
        }
      case _SIZE:
        {
          this.childCollFieldChanged(_childIdx, set, this.size);
          break;
        }
      case _TOOLTIPS:
        {
          this.childCollFieldChanged(_childIdx, set, this.tooltips);
          break;
        }
      default:
        {
          super._handleChildChange(_childIdx, set);
        }
    }
  }
}
