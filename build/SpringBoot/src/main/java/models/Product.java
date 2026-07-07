package models;

import classes.ProductStatus;
import d3e.core.CloneContext;
import d3e.core.DFile;
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

@SolrDocument(collection = "Product")
@Entity
public class Product extends CreatableObject {
  public static final int _CATEGORY = 0;
  public static final int _SKU = 1;
  public static final int _NAME = 2;
  public static final int _DESCRIPTION = 3;
  public static final int _BARCODE = 4;
  public static final int _BASEUOM = 5;
  public static final int _PURCHASEPRICE = 6;
  public static final int _SELLINGPRICE = 7;
  public static final int _REORDERLEVEL = 8;
  public static final int _REORDERQUANTITY = 9;
  public static final int _TRACKBATCH = 10;
  public static final int _TRACKEXPIRY = 11;
  public static final int _SHELFLIFEDAYS = 12;
  public static final int _IMAGE = 13;
  public static final int _WAREHOUSESTOCKS = 14;
  public static final int _BATCHES = 15;
  public static final int _STATUS = 16;
  public static final int _CREATEDAT = 17;
  public static final int _UPDATEDAT = 18;
  public static final int _ORGANIZATION = 19;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ProductCategory category;

  @NotNull private String sku;
  @NotNull private String name;
  private String description;
  private String barcode;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private UnitOfMeasure baseUom;

  private double purchasePrice = 0.0d;
  private double sellingPrice = 0.0d;
  private double reorderLevel = 0.0d;
  private double reorderQuantity = 0.0d;
  private boolean trackBatch = false;
  private boolean trackExpiry = false;
  private long shelfLifeDays = 0l;

  @ManyToOne(fetch = FetchType.LAZY)
  private DFile image;

  @ManyToMany(mappedBy = "product")
  private List<WarehouseStock> warehouseStocks = D3EPersistanceList.inverse(this, _WAREHOUSESTOCKS);

  @ManyToMany(mappedBy = "product")
  private List<StockBatch> batches = D3EPersistanceList.inverse(this, _BATCHES);

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private ProductStatus status = ProductStatus.Active;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  private transient Product old;

  public Product() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.Product;
  }

  @Override
  public String _type() {
    return "Product";
  }

  @Override
  public int _fieldsCount() {
    return 20;
  }

  public void addToWarehouseStocks(WarehouseStock val, long index) {
    if (index == -1) {
      this.warehouseStocks.add(val);
    } else {
      this.warehouseStocks.add(((int) index), val);
    }
  }

  public void removeFromWarehouseStocks(WarehouseStock val) {
    this.warehouseStocks.remove(val);
  }

  public void addToBatches(StockBatch val, long index) {
    if (index == -1) {
      this.batches.add(val);
    } else {
      this.batches.add(((int) index), val);
    }
  }

  public void removeFromBatches(StockBatch val) {
    this.batches.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public ProductCategory getCategory() {
    _checkProxy();
    return this.category;
  }

  public void setCategory(ProductCategory category) {
    _checkProxy();
    if (Objects.equals(this.category, category)) {
      return;
    }
    fieldChanged(_CATEGORY, this.category, category);
    if (!(isOld) && this.category != null) {
      this.category.removeFromProducts(this);
    }
    this.category = category;
    if (!(isOld) && category != null && !(category.getProducts().contains(this))) {
      category.addToProducts(this, -1);
    }
  }

  public String getSku() {
    _checkProxy();
    return this.sku;
  }

  public void setSku(String sku) {
    _checkProxy();
    if (Objects.equals(this.sku, sku)) {
      return;
    }
    fieldChanged(_SKU, this.sku, sku);
    this.sku = sku;
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

  public String getDescription() {
    _checkProxy();
    return this.description;
  }

  public void setDescription(String description) {
    _checkProxy();
    if (Objects.equals(this.description, description)) {
      return;
    }
    fieldChanged(_DESCRIPTION, this.description, description);
    this.description = description;
  }

  public String getBarcode() {
    _checkProxy();
    return this.barcode;
  }

  public void setBarcode(String barcode) {
    _checkProxy();
    if (Objects.equals(this.barcode, barcode)) {
      return;
    }
    fieldChanged(_BARCODE, this.barcode, barcode);
    this.barcode = barcode;
  }

  public UnitOfMeasure getBaseUom() {
    _checkProxy();
    return this.baseUom;
  }

  public void setBaseUom(UnitOfMeasure baseUom) {
    _checkProxy();
    if (Objects.equals(this.baseUom, baseUom)) {
      return;
    }
    fieldChanged(_BASEUOM, this.baseUom, baseUom);
    this.baseUom = baseUom;
  }

  public double getPurchasePrice() {
    _checkProxy();
    return this.purchasePrice;
  }

  public void setPurchasePrice(double purchasePrice) {
    _checkProxy();
    if (Objects.equals(this.purchasePrice, purchasePrice)) {
      return;
    }
    fieldChanged(_PURCHASEPRICE, this.purchasePrice, purchasePrice);
    this.purchasePrice = purchasePrice;
  }

  public double getSellingPrice() {
    _checkProxy();
    return this.sellingPrice;
  }

  public void setSellingPrice(double sellingPrice) {
    _checkProxy();
    if (Objects.equals(this.sellingPrice, sellingPrice)) {
      return;
    }
    fieldChanged(_SELLINGPRICE, this.sellingPrice, sellingPrice);
    this.sellingPrice = sellingPrice;
  }

  public double getReorderLevel() {
    _checkProxy();
    return this.reorderLevel;
  }

  public void setReorderLevel(double reorderLevel) {
    _checkProxy();
    if (Objects.equals(this.reorderLevel, reorderLevel)) {
      return;
    }
    fieldChanged(_REORDERLEVEL, this.reorderLevel, reorderLevel);
    this.reorderLevel = reorderLevel;
  }

  public double getReorderQuantity() {
    _checkProxy();
    return this.reorderQuantity;
  }

  public void setReorderQuantity(double reorderQuantity) {
    _checkProxy();
    if (Objects.equals(this.reorderQuantity, reorderQuantity)) {
      return;
    }
    fieldChanged(_REORDERQUANTITY, this.reorderQuantity, reorderQuantity);
    this.reorderQuantity = reorderQuantity;
  }

  public boolean isTrackBatch() {
    _checkProxy();
    return this.trackBatch;
  }

  public void setTrackBatch(boolean trackBatch) {
    _checkProxy();
    if (Objects.equals(this.trackBatch, trackBatch)) {
      return;
    }
    fieldChanged(_TRACKBATCH, this.trackBatch, trackBatch);
    this.trackBatch = trackBatch;
  }

  public boolean isTrackExpiry() {
    _checkProxy();
    return this.trackExpiry;
  }

  public void setTrackExpiry(boolean trackExpiry) {
    _checkProxy();
    if (Objects.equals(this.trackExpiry, trackExpiry)) {
      return;
    }
    fieldChanged(_TRACKEXPIRY, this.trackExpiry, trackExpiry);
    this.trackExpiry = trackExpiry;
  }

  public long getShelfLifeDays() {
    _checkProxy();
    return this.shelfLifeDays;
  }

  public void setShelfLifeDays(long shelfLifeDays) {
    _checkProxy();
    if (Objects.equals(this.shelfLifeDays, shelfLifeDays)) {
      return;
    }
    fieldChanged(_SHELFLIFEDAYS, this.shelfLifeDays, shelfLifeDays);
    this.shelfLifeDays = shelfLifeDays;
  }

  public DFile getImage() {
    _checkProxy();
    return this.image;
  }

  public void setImage(DFile image) {
    _checkProxy();
    if (Objects.equals(this.image, image)) {
      return;
    }
    fieldChanged(_IMAGE, this.image, image);
    this.image = image;
  }

  public List<WarehouseStock> getWarehouseStocks() {
    return this.warehouseStocks;
  }

  public void setWarehouseStocks(List<WarehouseStock> warehouseStocks) {
    if (Objects.equals(this.warehouseStocks, warehouseStocks)) {
      return;
    }
    ((D3EPersistanceList<WarehouseStock>) this.warehouseStocks).setAll(warehouseStocks);
  }

  public List<StockBatch> getBatches() {
    return this.batches;
  }

  public void setBatches(List<StockBatch> batches) {
    if (Objects.equals(this.batches, batches)) {
      return;
    }
    ((D3EPersistanceList<StockBatch>) this.batches).setAll(batches);
  }

  public ProductStatus getStatus() {
    _checkProxy();
    return this.status;
  }

  public void setStatus(ProductStatus status) {
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

  public Product getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((Product) old);
  }

  public String displayName() {
    return this.getName();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof Product && super.equals(a);
  }

  public Product deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    Product _obj = ((Product) dbObj);
    _obj.setCategory(category);
    _obj.setSku(sku);
    _obj.setName(name);
    _obj.setDescription(description);
    _obj.setBarcode(barcode);
    _obj.setBaseUom(baseUom);
    _obj.setPurchasePrice(purchasePrice);
    _obj.setSellingPrice(sellingPrice);
    _obj.setReorderLevel(reorderLevel);
    _obj.setReorderQuantity(reorderQuantity);
    _obj.setTrackBatch(trackBatch);
    _obj.setTrackExpiry(trackExpiry);
    _obj.setShelfLifeDays(shelfLifeDays);
    _obj.setImage(image);
    _obj.setWarehouseStocks(warehouseStocks);
    _obj.setBatches(batches);
    _obj.setStatus(status);
    _obj.setCreatedAt(createdAt);
    _obj.setUpdatedAt(updatedAt);
    _obj.setOrganization(organization);
  }

  public Product cloneInstance(Product cloneObj) {
    if (cloneObj == null) {
      cloneObj = new Product();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setCategory(this.getCategory());
    cloneObj.setSku(this.getSku());
    cloneObj.setName(this.getName());
    cloneObj.setDescription(this.getDescription());
    cloneObj.setBarcode(this.getBarcode());
    cloneObj.setBaseUom(this.getBaseUom());
    cloneObj.setPurchasePrice(this.getPurchasePrice());
    cloneObj.setSellingPrice(this.getSellingPrice());
    cloneObj.setReorderLevel(this.getReorderLevel());
    cloneObj.setReorderQuantity(this.getReorderQuantity());
    cloneObj.setTrackBatch(this.isTrackBatch());
    cloneObj.setTrackExpiry(this.isTrackExpiry());
    cloneObj.setShelfLifeDays(this.getShelfLifeDays());
    cloneObj.setImage(this.getImage());
    cloneObj.setWarehouseStocks(new ArrayList<>(this.getWarehouseStocks()));
    cloneObj.setBatches(new ArrayList<>(this.getBatches()));
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

  public Product createNewInstance() {
    return new Product();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.category);
    _refs.add(this.baseUom);
    _refs.add(this.image);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
