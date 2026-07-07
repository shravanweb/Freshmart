package models;

import classes.StockTransferStatus;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.D3EPersistanceList;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

@SolrDocument(collection = "StockTransfer")
@Entity
public class StockTransfer extends CreatableObject {
  public static final int _TRANSFERNUMBER = 0;
  public static final int _SOURCEWAREHOUSE = 1;
  public static final int _DESTINATIONWAREHOUSE = 2;
  public static final int _TRANSFERDATE = 3;
  public static final int _STATUS = 4;
  public static final int _NOTES = 5;
  public static final int _REQUESTEDBY = 6;
  public static final int _APPROVEDBY = 7;
  public static final int _SHIPPEDAT = 8;
  public static final int _RECEIVEDAT = 9;
  public static final int _LINES = 10;
  public static final int _ORGANIZATION = 11;
  @NotNull private String transferNumber;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Warehouse sourceWarehouse;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Warehouse destinationWarehouse;

  @NotNull private LocalDate transferDate;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private StockTransferStatus status = StockTransferStatus.Draft;

  private String notes;

  @ManyToOne(fetch = FetchType.LAZY)
  private User requestedBy;

  @ManyToOne(fetch = FetchType.LAZY)
  private User approvedBy;

  private LocalDateTime shippedAt;
  private LocalDateTime receivedAt;

  @ManyToMany(mappedBy = "stockTransfer", cascade = CascadeType.REMOVE)
  private List<StockTransferLine> lines = D3EPersistanceList.inverse(this, _LINES);

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  private transient StockTransfer old;

  public StockTransfer() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.StockTransfer;
  }

  @Override
  public String _type() {
    return "StockTransfer";
  }

  @Override
  public int _fieldsCount() {
    return 12;
  }

  public void addToLines(StockTransferLine val, long index) {
    if (index == -1) {
      this.lines.add(val);
    } else {
      this.lines.add(((int) index), val);
    }
  }

  public void removeFromLines(StockTransferLine val) {
    this.lines.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public String getTransferNumber() {
    _checkProxy();
    return this.transferNumber;
  }

  public void setTransferNumber(String transferNumber) {
    _checkProxy();
    if (Objects.equals(this.transferNumber, transferNumber)) {
      return;
    }
    fieldChanged(_TRANSFERNUMBER, this.transferNumber, transferNumber);
    this.transferNumber = transferNumber;
  }

  public Warehouse getSourceWarehouse() {
    _checkProxy();
    return this.sourceWarehouse;
  }

  public void setSourceWarehouse(Warehouse sourceWarehouse) {
    _checkProxy();
    if (Objects.equals(this.sourceWarehouse, sourceWarehouse)) {
      return;
    }
    fieldChanged(_SOURCEWAREHOUSE, this.sourceWarehouse, sourceWarehouse);
    this.sourceWarehouse = sourceWarehouse;
  }

  public Warehouse getDestinationWarehouse() {
    _checkProxy();
    return this.destinationWarehouse;
  }

  public void setDestinationWarehouse(Warehouse destinationWarehouse) {
    _checkProxy();
    if (Objects.equals(this.destinationWarehouse, destinationWarehouse)) {
      return;
    }
    fieldChanged(_DESTINATIONWAREHOUSE, this.destinationWarehouse, destinationWarehouse);
    this.destinationWarehouse = destinationWarehouse;
  }

  public LocalDate getTransferDate() {
    _checkProxy();
    return this.transferDate;
  }

  public void setTransferDate(LocalDate transferDate) {
    _checkProxy();
    if (Objects.equals(this.transferDate, transferDate)) {
      return;
    }
    fieldChanged(_TRANSFERDATE, this.transferDate, transferDate);
    this.transferDate = transferDate;
  }

  public StockTransferStatus getStatus() {
    _checkProxy();
    return this.status;
  }

  public void setStatus(StockTransferStatus status) {
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

  public User getRequestedBy() {
    _checkProxy();
    return this.requestedBy;
  }

  public void setRequestedBy(User requestedBy) {
    _checkProxy();
    if (Objects.equals(this.requestedBy, requestedBy)) {
      return;
    }
    fieldChanged(_REQUESTEDBY, this.requestedBy, requestedBy);
    this.requestedBy = requestedBy;
  }

  public User getApprovedBy() {
    _checkProxy();
    return this.approvedBy;
  }

  public void setApprovedBy(User approvedBy) {
    _checkProxy();
    if (Objects.equals(this.approvedBy, approvedBy)) {
      return;
    }
    fieldChanged(_APPROVEDBY, this.approvedBy, approvedBy);
    this.approvedBy = approvedBy;
  }

  public LocalDateTime getShippedAt() {
    _checkProxy();
    return this.shippedAt;
  }

  public void setShippedAt(LocalDateTime shippedAt) {
    _checkProxy();
    if (Objects.equals(this.shippedAt, shippedAt)) {
      return;
    }
    fieldChanged(_SHIPPEDAT, this.shippedAt, shippedAt);
    this.shippedAt = shippedAt;
  }

  public LocalDateTime getReceivedAt() {
    _checkProxy();
    return this.receivedAt;
  }

  public void setReceivedAt(LocalDateTime receivedAt) {
    _checkProxy();
    if (Objects.equals(this.receivedAt, receivedAt)) {
      return;
    }
    fieldChanged(_RECEIVEDAT, this.receivedAt, receivedAt);
    this.receivedAt = receivedAt;
  }

  public List<StockTransferLine> getLines() {
    return this.lines;
  }

  public void setLines(List<StockTransferLine> lines) {
    if (Objects.equals(this.lines, lines)) {
      return;
    }
    ((D3EPersistanceList<StockTransferLine>) this.lines).setAll(lines);
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

  public StockTransfer getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((StockTransfer) old);
  }

  public String displayName() {
    return this.getTransferNumber();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof StockTransfer && super.equals(a);
  }

  public StockTransfer deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    StockTransfer _obj = ((StockTransfer) dbObj);
    _obj.setTransferNumber(transferNumber);
    _obj.setSourceWarehouse(sourceWarehouse);
    _obj.setDestinationWarehouse(destinationWarehouse);
    _obj.setTransferDate(transferDate);
    _obj.setStatus(status);
    _obj.setNotes(notes);
    _obj.setRequestedBy(requestedBy);
    _obj.setApprovedBy(approvedBy);
    _obj.setShippedAt(shippedAt);
    _obj.setReceivedAt(receivedAt);
    _obj.setLines(lines);
    _obj.setOrganization(organization);
  }

  public StockTransfer cloneInstance(StockTransfer cloneObj) {
    if (cloneObj == null) {
      cloneObj = new StockTransfer();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setTransferNumber(this.getTransferNumber());
    cloneObj.setSourceWarehouse(this.getSourceWarehouse());
    cloneObj.setDestinationWarehouse(this.getDestinationWarehouse());
    cloneObj.setTransferDate(this.getTransferDate());
    cloneObj.setStatus(this.getStatus());
    cloneObj.setNotes(this.getNotes());
    cloneObj.setRequestedBy(this.getRequestedBy());
    cloneObj.setApprovedBy(this.getApprovedBy());
    cloneObj.setShippedAt(this.getShippedAt());
    cloneObj.setReceivedAt(this.getReceivedAt());
    cloneObj.setLines(new ArrayList<>(this.getLines()));
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public StockTransfer createNewInstance() {
    return new StockTransfer();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.sourceWarehouse);
    _refs.add(this.destinationWarehouse);
    _refs.add(this.requestedBy);
    _refs.add(this.approvedBy);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
