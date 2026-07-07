package models;

import classes.ReportCellAllign;
import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class ReportCellStyle extends DatabaseObject {
  public static final int _WIDTH = 0;
  public static final int _FONT = 1;
  public static final int _FONTSIZE = 2;
  public static final int _TEXTCOLOR = 3;
  public static final int _BGCOLOR = 4;
  public static final int _VALLIGN = 5;
  public static final int _HALLIGN = 6;
  private long width = 0l;
  private String font;
  private long fontSize = 0l;
  private String textColor;
  private String bgColor;
  private ReportCellAllign vAllign = ReportCellAllign.Center;
  private ReportCellAllign hAllign = ReportCellAllign.Center;
  private ReportCell masterReportCell;
  private transient ReportCellStyle old;

  public ReportCellStyle() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ReportCellStyle;
  }

  @Override
  public String _type() {
    return "ReportCellStyle";
  }

  @Override
  public int _fieldsCount() {
    return 7;
  }

  public DatabaseObject _masterObject() {
    DatabaseObject master = super._masterObject();
    if (master != null) {
      return master;
    }
    if (masterReportCell != null) {
      return masterReportCell;
    }
    return null;
  }

  public void _setMasterObject(DBObject master) {
    super._setMasterObject(master);
    if (master instanceof ReportCell) {
      masterReportCell = ((ReportCell) master);
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
    if (masterReportCell != null) {
      masterReportCell.updateFlat(obj);
    }
  }

  public long getWidth() {
    _checkProxy();
    return this.width;
  }

  public void setWidth(long width) {
    _checkProxy();
    if (Objects.equals(this.width, width)) {
      return;
    }
    fieldChanged(_WIDTH, this.width, width);
    this.width = width;
  }

  public String getFont() {
    _checkProxy();
    return this.font;
  }

  public void setFont(String font) {
    _checkProxy();
    if (Objects.equals(this.font, font)) {
      return;
    }
    fieldChanged(_FONT, this.font, font);
    this.font = font;
  }

  public long getFontSize() {
    _checkProxy();
    return this.fontSize;
  }

  public void setFontSize(long fontSize) {
    _checkProxy();
    if (Objects.equals(this.fontSize, fontSize)) {
      return;
    }
    fieldChanged(_FONTSIZE, this.fontSize, fontSize);
    this.fontSize = fontSize;
  }

  public String getTextColor() {
    _checkProxy();
    return this.textColor;
  }

  public void setTextColor(String textColor) {
    _checkProxy();
    if (Objects.equals(this.textColor, textColor)) {
      return;
    }
    fieldChanged(_TEXTCOLOR, this.textColor, textColor);
    this.textColor = textColor;
  }

  public String getBgColor() {
    _checkProxy();
    return this.bgColor;
  }

  public void setBgColor(String bgColor) {
    _checkProxy();
    if (Objects.equals(this.bgColor, bgColor)) {
      return;
    }
    fieldChanged(_BGCOLOR, this.bgColor, bgColor);
    this.bgColor = bgColor;
  }

  public ReportCellAllign getVAllign() {
    _checkProxy();
    return this.vAllign;
  }

  public void setVAllign(ReportCellAllign vAllign) {
    _checkProxy();
    if (Objects.equals(this.vAllign, vAllign)) {
      return;
    }
    fieldChanged(_VALLIGN, this.vAllign, vAllign);
    this.vAllign = vAllign;
  }

  public ReportCellAllign getHAllign() {
    _checkProxy();
    return this.hAllign;
  }

  public void setHAllign(ReportCellAllign hAllign) {
    _checkProxy();
    if (Objects.equals(this.hAllign, hAllign)) {
      return;
    }
    fieldChanged(_HALLIGN, this.hAllign, hAllign);
    this.hAllign = hAllign;
  }

  public ReportCell getMasterReportCell() {
    return this.masterReportCell;
  }

  public void setMasterReportCell(ReportCell masterReportCell) {
    this.masterReportCell = masterReportCell;
  }

  public ReportCellStyle getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ReportCellStyle) old);
  }

  public String displayName() {
    return "ReportCellStyle";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportCellStyle && super.equals(a);
  }

  public ReportCellStyle deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ReportCellStyle _obj = ((ReportCellStyle) dbObj);
    _obj.setWidth(width);
    _obj.setFont(font);
    _obj.setFontSize(fontSize);
    _obj.setTextColor(textColor);
    _obj.setBgColor(bgColor);
    _obj.setVAllign(vAllign);
    _obj.setHAllign(hAllign);
  }

  public ReportCellStyle cloneInstance(ReportCellStyle cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportCellStyle();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setWidth(this.getWidth());
    cloneObj.setFont(this.getFont());
    cloneObj.setFontSize(this.getFontSize());
    cloneObj.setTextColor(this.getTextColor());
    cloneObj.setBgColor(this.getBgColor());
    cloneObj.setVAllign(this.getVAllign());
    cloneObj.setHAllign(this.getHAllign());
    return cloneObj;
  }

  public ReportCellStyle createNewInstance() {
    return new ReportCellStyle();
  }

  public boolean needOldObject() {
    return true;
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
