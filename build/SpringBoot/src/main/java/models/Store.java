package models;

import classes.EntityStatus;
import classes.StoreType;
import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
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

@SolrDocument(collection = "Store")
@Entity
public class Store extends CreatableObject {
  public static final int _NAME = 0;
  public static final int _CODE = 1;
  public static final int _STORETYPE = 2;
  public static final int _ADDRESS = 3;
  public static final int _PHONE = 4;
  public static final int _EMAIL = 5;
  public static final int _MANAGER = 6;
  public static final int _DEFAULTWAREHOUSE = 7;
  public static final int _WAREHOUSES = 8;
  public static final int _STATUS = 9;
  public static final int _CREATEDAT = 10;
  public static final int _UPDATEDAT = 11;
  public static final int _ORGANIZATION = 12;
  @NotNull private String name;
  @NotNull private String code;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private StoreType storeType = StoreType.Supermarket;

  private String address;
  private String phone;
  private String email;

  @ManyToOne(fetch = FetchType.LAZY)
  private User manager;

  @ManyToOne(fetch = FetchType.LAZY)
  private Warehouse defaultWarehouse;

  @ManyToMany(mappedBy = "store")
  private List<Warehouse> warehouses = D3EPersistanceList.inverse(this, _WAREHOUSES);

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private EntityStatus status = EntityStatus.Active;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  private transient Store old;

  public Store() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.Store;
  }

  @Override
  public String _type() {
    return "Store";
  }

  @Override
  public int _fieldsCount() {
    return 13;
  }

  public void addToWarehouses(Warehouse val, long index) {
    if (index == -1) {
      this.warehouses.add(val);
    } else {
      this.warehouses.add(((int) index), val);
    }
  }

  public void removeFromWarehouses(Warehouse val) {
    this.warehouses.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
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

  public StoreType getStoreType() {
    _checkProxy();
    return this.storeType;
  }

  public void setStoreType(StoreType storeType) {
    _checkProxy();
    if (Objects.equals(this.storeType, storeType)) {
      return;
    }
    fieldChanged(_STORETYPE, this.storeType, storeType);
    this.storeType = storeType;
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

  public String getPhone() {
    _checkProxy();
    return this.phone;
  }

  public void setPhone(String phone) {
    _checkProxy();
    if (Objects.equals(this.phone, phone)) {
      return;
    }
    fieldChanged(_PHONE, this.phone, phone);
    this.phone = phone;
  }

  public String getEmail() {
    _checkProxy();
    return this.email;
  }

  public void setEmail(String email) {
    _checkProxy();
    if (Objects.equals(this.email, email)) {
      return;
    }
    fieldChanged(_EMAIL, this.email, email);
    this.email = email;
  }

  public User getManager() {
    _checkProxy();
    return this.manager;
  }

  public void setManager(User manager) {
    _checkProxy();
    if (Objects.equals(this.manager, manager)) {
      return;
    }
    fieldChanged(_MANAGER, this.manager, manager);
    this.manager = manager;
  }

  public Warehouse getDefaultWarehouse() {
    _checkProxy();
    return this.defaultWarehouse;
  }

  public void setDefaultWarehouse(Warehouse defaultWarehouse) {
    _checkProxy();
    if (Objects.equals(this.defaultWarehouse, defaultWarehouse)) {
      return;
    }
    fieldChanged(_DEFAULTWAREHOUSE, this.defaultWarehouse, defaultWarehouse);
    this.defaultWarehouse = defaultWarehouse;
  }

  public List<Warehouse> getWarehouses() {
    return this.warehouses;
  }

  public void setWarehouses(List<Warehouse> warehouses) {
    if (Objects.equals(this.warehouses, warehouses)) {
      return;
    }
    ((D3EPersistanceList<Warehouse>) this.warehouses).setAll(warehouses);
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

  public Store getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((Store) old);
  }

  public String displayName() {
    return this.getName();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof Store && super.equals(a);
  }

  public Store deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    Store _obj = ((Store) dbObj);
    _obj.setName(name);
    _obj.setCode(code);
    _obj.setStoreType(storeType);
    _obj.setAddress(address);
    _obj.setPhone(phone);
    _obj.setEmail(email);
    _obj.setManager(manager);
    _obj.setDefaultWarehouse(defaultWarehouse);
    _obj.setWarehouses(warehouses);
    _obj.setStatus(status);
    _obj.setCreatedAt(createdAt);
    _obj.setUpdatedAt(updatedAt);
    _obj.setOrganization(organization);
  }

  public Store cloneInstance(Store cloneObj) {
    if (cloneObj == null) {
      cloneObj = new Store();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setName(this.getName());
    cloneObj.setCode(this.getCode());
    cloneObj.setStoreType(this.getStoreType());
    cloneObj.setAddress(this.getAddress());
    cloneObj.setPhone(this.getPhone());
    cloneObj.setEmail(this.getEmail());
    cloneObj.setManager(this.getManager());
    cloneObj.setDefaultWarehouse(this.getDefaultWarehouse());
    cloneObj.setWarehouses(new ArrayList<>(this.getWarehouses()));
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

  public Store createNewInstance() {
    return new Store();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.manager);
    _refs.add(this.defaultWarehouse);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
