package models;

import classes.AdjustmentReason;
import classes.AdjustmentStatus;
import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.D3EPersistanceList;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

@SolrDocument(collection = "InventoryAdjustment")
@Entity
public class InventoryAdjustment extends CreatableObject {
  public static final int _ADJUSTMENTNUMBER = 0;
  public static final int _WAREHOUSE = 1;
  public static final int _ADJUSTMENTDATE = 2;
  public static final int _REASON = 3;
  public static final int _STATUS = 4;
  public static final int _NOTES = 5;
  public static final int _ADJUSTEDBY = 6;
  public static final int _LINES = 7;
  public static final int _ORGANIZATION = 8;
  @NotNull private String adjustmentNumber;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Warehouse warehouse;

  @NotNull private LocalDate adjustmentDate;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private AdjustmentReason reason = AdjustmentReason.PhysicalCount;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private AdjustmentStatus status = AdjustmentStatus.Draft;

  private String notes;

  @ManyToOne(fetch = FetchType.LAZY)
  private User adjustedBy;

  @ManyToMany(mappedBy = "inventoryAdjustment", cascade = CascadeType.REMOVE)
  private List<InventoryAdjustmentLine> lines = D3EPersistanceList.inverse(this, _LINES);

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  private transient InventoryAdjustment old;

  public InventoryAdjustment() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.InventoryAdjustment;
  }

  @Override
  public String _type() {
    return "InventoryAdjustment";
  }

  @Override
  public int _fieldsCount() {
    return 9;
  }

  public void addToLines(InventoryAdjustmentLine val, long index) {
    if (index == -1) {
      this.lines.add(val);
    } else {
      this.lines.add(((int) index), val);
    }
  }

  public void removeFromLines(InventoryAdjustmentLine val) {
    this.lines.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public String getAdjustmentNumber() {
    _checkProxy();
    return this.adjustmentNumber;
  }

  public void setAdjustmentNumber(String adjustmentNumber) {
    _checkProxy();
    if (Objects.equals(this.adjustmentNumber, adjustmentNumber)) {
      return;
    }
    fieldChanged(_ADJUSTMENTNUMBER, this.adjustmentNumber, adjustmentNumber);
    this.adjustmentNumber = adjustmentNumber;
  }

  public Warehouse getWarehouse() {
    _checkProxy();
    return this.warehouse;
  }

  public void setWarehouse(Warehouse warehouse) {
    _checkProxy();
    if (Objects.equals(this.warehouse, warehouse)) {
      return;
    }
    fieldChanged(_WAREHOUSE, this.warehouse, warehouse);
    this.warehouse = warehouse;
  }

  public LocalDate getAdjustmentDate() {
    _checkProxy();
    return this.adjustmentDate;
  }

  public void setAdjustmentDate(LocalDate adjustmentDate) {
    _checkProxy();
    if (Objects.equals(this.adjustmentDate, adjustmentDate)) {
      return;
    }
    fieldChanged(_ADJUSTMENTDATE, this.adjustmentDate, adjustmentDate);
    this.adjustmentDate = adjustmentDate;
  }

  public AdjustmentReason getReason() {
    _checkProxy();
    return this.reason;
  }

  public void setReason(AdjustmentReason reason) {
    _checkProxy();
    if (Objects.equals(this.reason, reason)) {
      return;
    }
    fieldChanged(_REASON, this.reason, reason);
    this.reason = reason;
  }

  public AdjustmentStatus getStatus() {
    _checkProxy();
    return this.status;
  }

  public void setStatus(AdjustmentStatus status) {
    _checkProxy();
    if (Objects.equals(this.status, status)) {
      return;
    }
    fieldChanged(_STATUS, this.status, status);
    this.status = status;
  }

  public String getNotes() {
    _checkProxy();
    return this.notes;
  }

  public void setNotes(String notes) {
    _checkProxy();
    if (Objects.equals(this.notes, notes)) {
      return;
    }
    fieldChanged(_NOTES, this.notes, notes);
    this.notes = notes;
  }

  public User getAdjustedBy() {
    _checkProxy();
    return this.adjustedBy;
  }

  public void setAdjustedBy(User adjustedBy) {
    _checkProxy();
    if (Objects.equals(this.adjustedBy, adjustedBy)) {
      return;
    }
    fieldChanged(_ADJUSTEDBY, this.adjustedBy, adjustedBy);
    this.adjustedBy = adjustedBy;
  }

  public List<InventoryAdjustmentLine> getLines() {
    return this.lines;
  }

  public void setLines(List<InventoryAdjustmentLine> lines) {
    if (Objects.equals(this.lines, lines)) {
      return;
    }
    ((D3EPersistanceList<InventoryAdjustmentLine>) this.lines).setAll(lines);
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

  public InventoryAdjustment getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((InventoryAdjustment) old);
  }

  public String displayName() {
    return this.getAdjustmentNumber();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof InventoryAdjustment && super.equals(a);
  }

  public InventoryAdjustment deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    InventoryAdjustment _obj = ((InventoryAdjustment) dbObj);
    _obj.setAdjustmentNumber(adjustmentNumber);
    _obj.setWarehouse(warehouse);
    _obj.setAdjustmentDate(adjustmentDate);
    _obj.setReason(reason);
    _obj.setStatus(status);
    _obj.setNotes(notes);
    _obj.setAdjustedBy(adjustedBy);
    _obj.setLines(lines);
    _obj.setOrganization(organization);
  }

  public InventoryAdjustment cloneInstance(InventoryAdjustment cloneObj) {
    if (cloneObj == null) {
      cloneObj = new InventoryAdjustment();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setAdjustmentNumber(this.getAdjustmentNumber());
    cloneObj.setWarehouse(this.getWarehouse());
    cloneObj.setAdjustmentDate(this.getAdjustmentDate());
    cloneObj.setReason(this.getReason());
    cloneObj.setStatus(this.getStatus());
    cloneObj.setNotes(this.getNotes());
    cloneObj.setAdjustedBy(this.getAdjustedBy());
    cloneObj.setLines(new ArrayList<>(this.getLines()));
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public InventoryAdjustment createNewInstance() {
    return new InventoryAdjustment();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.warehouse);
    _refs.add(this.adjustedBy);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
