package models;

import classes.MovementDirection;
import classes.MovementReferenceType;
import classes.MovementType;
import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

@SolrDocument(collection = "InventoryMovement")
@Entity
public class InventoryMovement extends CreatableObject {
  public static final int _MOVEMENTNUMBER = 0;
  public static final int _WAREHOUSE = 1;
  public static final int _PRODUCT = 2;
  public static final int _MOVEMENTTYPE = 3;
  public static final int _QUANTITY = 4;
  public static final int _DIRECTION = 5;
  public static final int _REFERENCETYPE = 6;
  public static final int _REFERENCEID = 7;
  public static final int _BATCHNUMBER = 8;
  public static final int _UNITCOST = 9;
  public static final int _BALANCEAFTER = 10;
  public static final int _MOVEMENTDATE = 11;
  public static final int _PERFORMEDBY = 12;
  public static final int _NOTES = 13;
  public static final int _ORGANIZATION = 14;
  @NotNull private String movementNumber;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Warehouse warehouse;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Product product;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private MovementType movementType = MovementType.PurchaseReceipt;

  private double quantity = 0.0d;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private MovementDirection direction = MovementDirection.In;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private MovementReferenceType referenceType = MovementReferenceType.PurchaseOrder;

  @NotNull private String referenceId;
  private String batchNumber;
  private double unitCost = 0.0d;
  private double balanceAfter = 0.0d;
  @NotNull private LocalDateTime movementDate;

  @ManyToOne(fetch = FetchType.LAZY)
  private User performedBy;

  private String notes;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  private transient InventoryMovement old;

  public InventoryMovement() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.InventoryMovement;
  }

  @Override
  public String _type() {
    return "InventoryMovement";
  }

  @Override
  public int _fieldsCount() {
    return 15;
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public String getMovementNumber() {
    _checkProxy();
    return this.movementNumber;
  }

  public void setMovementNumber(String movementNumber) {
    _checkProxy();
    if (Objects.equals(this.movementNumber, movementNumber)) {
      return;
    }
    fieldChanged(_MOVEMENTNUMBER, this.movementNumber, movementNumber);
    this.movementNumber = movementNumber;
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

  public Product getProduct() {
    _checkProxy();
    return this.product;
  }

  public void setProduct(Product product) {
    _checkProxy();
    if (Objects.equals(this.product, product)) {
      return;
    }
    fieldChanged(_PRODUCT, this.product, product);
    this.product = product;
  }

  public MovementType getMovementType() {
    _checkProxy();
    return this.movementType;
  }

  public void setMovementType(MovementType movementType) {
    _checkProxy();
    if (Objects.equals(this.movementType, movementType)) {
      return;
    }
    fieldChanged(_MOVEMENTTYPE, this.movementType, movementType);
    this.movementType = movementType;
  }

  public double getQuantity() {
    _checkProxy();
    return this.quantity;
  }

  public void setQuantity(double quantity) {
    _checkProxy();
    if (Objects.equals(this.quantity, quantity)) {
      return;
    }
    fieldChanged(_QUANTITY, this.quantity, quantity);
    this.quantity = quantity;
  }

  public MovementDirection getDirection() {
    _checkProxy();
    return this.direction;
  }

  public void setDirection(MovementDirection direction) {
    _checkProxy();
    if (Objects.equals(this.direction, direction)) {
      return;
    }
    fieldChanged(_DIRECTION, this.direction, direction);
    this.direction = direction;
  }

  public MovementReferenceType getReferenceType() {
    _checkProxy();
    return this.referenceType;
  }

  public void setReferenceType(MovementReferenceType referenceType) {
    _checkProxy();
    if (Objects.equals(this.referenceType, referenceType)) {
      return;
    }
    fieldChanged(_REFERENCETYPE, this.referenceType, referenceType);
    this.referenceType = referenceType;
  }

  public String getReferenceId() {
    _checkProxy();
    return this.referenceId;
  }

  public void setReferenceId(String referenceId) {
    _checkProxy();
    if (Objects.equals(this.referenceId, referenceId)) {
      return;
    }
    fieldChanged(_REFERENCEID, this.referenceId, referenceId);
    this.referenceId = referenceId;
  }

  public String getBatchNumber() {
    _checkProxy();
    return this.batchNumber;
  }

  public void setBatchNumber(String batchNumber) {
    _checkProxy();
    if (Objects.equals(this.batchNumber, batchNumber)) {
      return;
    }
    fieldChanged(_BATCHNUMBER, this.batchNumber, batchNumber);
    this.batchNumber = batchNumber;
  }

  public double getUnitCost() {
    _checkProxy();
    return this.unitCost;
  }

  public void setUnitCost(double unitCost) {
    _checkProxy();
    if (Objects.equals(this.unitCost, unitCost)) {
      return;
    }
    fieldChanged(_UNITCOST, this.unitCost, unitCost);
    this.unitCost = unitCost;
  }

  public double getBalanceAfter() {
    _checkProxy();
    return this.balanceAfter;
  }

  public void setBalanceAfter(double balanceAfter) {
    _checkProxy();
    if (Objects.equals(this.balanceAfter, balanceAfter)) {
      return;
    }
    fieldChanged(_BALANCEAFTER, this.balanceAfter, balanceAfter);
    this.balanceAfter = balanceAfter;
  }

  public LocalDateTime getMovementDate() {
    _checkProxy();
    return this.movementDate;
  }

  public void setMovementDate(LocalDateTime movementDate) {
    _checkProxy();
    if (Objects.equals(this.movementDate, movementDate)) {
      return;
    }
    fieldChanged(_MOVEMENTDATE, this.movementDate, movementDate);
    this.movementDate = movementDate;
  }

  public User getPerformedBy() {
    _checkProxy();
    return this.performedBy;
  }

  public void setPerformedBy(User performedBy) {
    _checkProxy();
    if (Objects.equals(this.performedBy, performedBy)) {
      return;
    }
    fieldChanged(_PERFORMEDBY, this.performedBy, performedBy);
    this.performedBy = performedBy;
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

  public InventoryMovement getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((InventoryMovement) old);
  }

  public String displayName() {
    return this.getMovementNumber();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof InventoryMovement && super.equals(a);
  }

  public InventoryMovement deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    InventoryMovement _obj = ((InventoryMovement) dbObj);
    _obj.setMovementNumber(movementNumber);
    _obj.setWarehouse(warehouse);
    _obj.setProduct(product);
    _obj.setMovementType(movementType);
    _obj.setQuantity(quantity);
    _obj.setDirection(direction);
    _obj.setReferenceType(referenceType);
    _obj.setReferenceId(referenceId);
    _obj.setBatchNumber(batchNumber);
    _obj.setUnitCost(unitCost);
    _obj.setBalanceAfter(balanceAfter);
    _obj.setMovementDate(movementDate);
    _obj.setPerformedBy(performedBy);
    _obj.setNotes(notes);
    _obj.setOrganization(organization);
  }

  public InventoryMovement cloneInstance(InventoryMovement cloneObj) {
    if (cloneObj == null) {
      cloneObj = new InventoryMovement();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setMovementNumber(this.getMovementNumber());
    cloneObj.setWarehouse(this.getWarehouse());
    cloneObj.setProduct(this.getProduct());
    cloneObj.setMovementType(this.getMovementType());
    cloneObj.setQuantity(this.getQuantity());
    cloneObj.setDirection(this.getDirection());
    cloneObj.setReferenceType(this.getReferenceType());
    cloneObj.setReferenceId(this.getReferenceId());
    cloneObj.setBatchNumber(this.getBatchNumber());
    cloneObj.setUnitCost(this.getUnitCost());
    cloneObj.setBalanceAfter(this.getBalanceAfter());
    cloneObj.setMovementDate(this.getMovementDate());
    cloneObj.setPerformedBy(this.getPerformedBy());
    cloneObj.setNotes(this.getNotes());
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public InventoryMovement createNewInstance() {
    return new InventoryMovement();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.warehouse);
    _refs.add(this.product);
    _refs.add(this.performedBy);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
