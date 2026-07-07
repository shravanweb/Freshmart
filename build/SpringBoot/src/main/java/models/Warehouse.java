package models;

import classes.EntityStatus;
import classes.WarehouseType;
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

@SolrDocument(collection = "Warehouse")
@Entity
public class Warehouse extends CreatableObject {
  public static final int _STORE = 0;
  public static final int _NAME = 1;
  public static final int _CODE = 2;
  public static final int _WAREHOUSETYPE = 3;
  public static final int _ADDRESS = 4;
  public static final int _ISDEFAULT = 5;
  public static final int _STATUS = 6;
  public static final int _CREATEDAT = 7;
  public static final int _UPDATEDAT = 8;
  public static final int _ORGANIZATION = 9;

  @ManyToOne(fetch = FetchType.LAZY)
  private Store store;

  @NotNull private String name;
  @NotNull private String code;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private WarehouseType warehouseType = WarehouseType.Main;

  private String address;
  private boolean isDefault = false;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private EntityStatus status = EntityStatus.Active;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  private transient Warehouse old;

  public Warehouse() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.Warehouse;
  }

  @Override
  public String _type() {
    return "Warehouse";
  }

  @Override
  public int _fieldsCount() {
    return 10;
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public Store getStore() {
    _checkProxy();
    return this.store;
  }

  public void setStore(Store store) {
    _checkProxy();
    if (Objects.equals(this.store, store)) {
      return;
    }
    fieldChanged(_STORE, this.store, store);
    if (!(isOld) && this.store != null) {
      this.store.removeFromWarehouses(this);
    }
    this.store = store;
    if (!(isOld) && store != null && !(store.getWarehouses().contains(this))) {
      store.addToWarehouses(this, -1);
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

  public String getCode() {
    _checkProxy();
    return this.code;
  }

  public void setCode(String code) {
    _checkProxy();
    if (Objects.equals(this.code, code)) {
      return;
    }
    fieldChanged(_CODE, this.code, code);
    this.code = code;
  }

  public WarehouseType getWarehouseType() {
    _checkProxy();
    return this.warehouseType;
  }

  public void setWarehouseType(WarehouseType warehouseType) {
    _checkProxy();
    if (Objects.equals(this.warehouseType, warehouseType)) {
      return;
    }
    fieldChanged(_WAREHOUSETYPE, this.warehouseType, warehouseType);
    this.warehouseType = warehouseType;
  }

  public String getAddress() {
    _checkProxy();
    return this.address;
  }

  public void setAddress(String address) {
    _checkProxy();
    if (Objects.equals(this.address, address)) {
      return;
    }
    fieldChanged(_ADDRESS, this.address, address);
    this.address = address;
  }

  public boolean isIsDefault() {
    _checkProxy();
    return this.isDefault;
  }

  public void setIsDefault(boolean isDefault) {
    _checkProxy();
    if (Objects.equals(this.isDefault, isDefault)) {
      return;
    }
    fieldChanged(_ISDEFAULT, this.isDefault, isDefault);
    this.isDefault = isDefault;
  }

  public EntityStatus getStatus() {
    _checkProxy();
    return this.status;
  }

  public void setStatus(EntityStatus status) {
    _checkProxy();
    if (Objects.equals(this.status, status)) {
      return;
    }
    fieldChanged(_STATUS, this.status, status);
    this.status = status;
  }

  public LocalDateTime getCreatedAt() {
    _checkProxy();
    return this.createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    _checkProxy();
    if (Objects.equals(this.createdAt, createdAt)) {
      return;
    }
    fieldChanged(_CREATEDAT, this.createdAt, createdAt);
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    _checkProxy();
    return this.updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    _checkProxy();
    if (Objects.equals(this.updatedAt, updatedAt)) {
      return;
    }
    fieldChanged(_UPDATEDAT, this.updatedAt, updatedAt);
    this.updatedAt = updatedAt;
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

  public Warehouse getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((Warehouse) old);
  }

  public String displayName() {
    return this.getName();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof Warehouse && super.equals(a);
  }

  public Warehouse deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    Warehouse _obj = ((Warehouse) dbObj);
    _obj.setStore(store);
    _obj.setName(name);
    _obj.setCode(code);
    _obj.setWarehouseType(warehouseType);
    _obj.setAddress(address);
    _obj.setIsDefault(isDefault);
    _obj.setStatus(status);
    _obj.setCreatedAt(createdAt);
    _obj.setUpdatedAt(updatedAt);
    _obj.setOrganization(organization);
  }

  public Warehouse cloneInstance(Warehouse cloneObj) {
    if (cloneObj == null) {
      cloneObj = new Warehouse();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setStore(this.getStore());
    cloneObj.setName(this.getName());
    cloneObj.setCode(this.getCode());
    cloneObj.setWarehouseType(this.getWarehouseType());
    cloneObj.setAddress(this.getAddress());
    cloneObj.setIsDefault(this.isIsDefault());
    cloneObj.setStatus(this.getStatus());
    cloneObj.setCreatedAt(this.getCreatedAt());
    cloneObj.setUpdatedAt(this.getUpdatedAt());
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public Warehouse createNewInstance() {
    return new Warehouse();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.store);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
