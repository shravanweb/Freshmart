package helpers;

import classes.ProductStatus;
import models.GoodsReceiptLine;
import models.InventoryAdjustmentLine;
import models.InventoryMovement;
import models.Organization;
import models.Product;
import models.ProductCategory;
import models.PurchaseOrderLine;
import models.SalesOrderLine;
import models.SalesReturnLine;
import models.StockAlert;
import models.StockBatch;
import models.StockTransferLine;
import models.UnitOfMeasure;
import models.WarehouseStock;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.DFileRepository;
import repository.jpa.GoodsReceiptLineRepository;
import repository.jpa.InventoryAdjustmentLineRepository;
import repository.jpa.InventoryMovementRepository;
import repository.jpa.ProductCategoryRepository;
import repository.jpa.ProductRepository;
import repository.jpa.PurchaseOrderLineRepository;
import repository.jpa.SalesOrderLineRepository;
import repository.jpa.SalesReturnLineRepository;
import repository.jpa.StockAlertRepository;
import repository.jpa.StockBatchRepository;
import repository.jpa.StockTransferLineRepository;
import repository.jpa.WarehouseStockRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;

@org.springframework.stereotype.Service("Product")
public class ProductEntityHelper<T extends Product> implements EntityHelper<T> {
  @Autowired protected EntityMutator mutator;
  @Autowired private ProductRepository productRepository;
  @Autowired private GoodsReceiptLineRepository goodsReceiptLineRepository;
  @Autowired private InventoryAdjustmentLineRepository inventoryAdjustmentLineRepository;
  @Autowired private InventoryMovementRepository inventoryMovementRepository;
  @Autowired private ProductCategoryRepository productCategoryRepository;
  @Autowired private PurchaseOrderLineRepository purchaseOrderLineRepository;
  @Autowired private SalesOrderLineRepository salesOrderLineRepository;
  @Autowired private SalesReturnLineRepository salesReturnLineRepository;
  @Autowired private StockAlertRepository stockAlertRepository;
  @Autowired private StockBatchRepository stockBatchRepository;
  @Autowired private StockTransferLineRepository stockTransferLineRepository;
  @Autowired private WarehouseStockRepository warehouseStockRepository;
  @Autowired private DFileRepository dFileRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public Product newInstance() {
    return new Product();
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validateFieldCategory(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    ProductCategory it = entity.getCategory();
    if (it == null) {
      validationContext.addFieldError("category", "category is required.");
      return;
    }
  }

  public void validateFieldSku(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getSku();
    if (it == null) {
      validationContext.addFieldError("sku", "sku is required.");
      return;
    }
  }

  public void validateFieldName(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    String it = entity.getName();
    if (it == null) {
      validationContext.addFieldError("name", "name is required.");
      return;
    }
  }

  public void validateFieldBaseUom(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    UnitOfMeasure it = entity.getBaseUom();
    if (it == null) {
      validationContext.addFieldError("baseUom", "baseUom is required.");
      return;
    }
  }

  public void validateFieldStatus(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    ProductStatus it = entity.getStatus();
    if (it == null) {
      validationContext.addFieldError("status", "status is required.");
      return;
    }
  }

  public void validateFieldOrganization(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    Organization it = entity.getOrganization();
    if (it == null) {
      validationContext.addFieldError("organization", "organization is required.");
      return;
    }
  }

  public void validateInternal(
      T entity, EntityValidationContext validationContext, boolean onCreate, boolean onUpdate) {
    validateFieldCategory(entity, validationContext, onCreate, onUpdate);
    validateFieldSku(entity, validationContext, onCreate, onUpdate);
    validateFieldName(entity, validationContext, onCreate, onUpdate);
    validateFieldBaseUom(entity, validationContext, onCreate, onUpdate);
    validateFieldStatus(entity, validationContext, onCreate, onUpdate);
    validateFieldOrganization(entity, validationContext, onCreate, onUpdate);
  }

  public void validateOnCreate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, true, false);
  }

  public void validateOnUpdate(T entity, EntityValidationContext validationContext) {
    validateInternal(entity, validationContext, false, true);
  }

  public void setDefaultPurchasePrice(T entity) {
    if (entity.getPurchasePrice() != 0.0d) {
      return;
    }
    entity.setPurchasePrice(0.0d);
  }

  public void setDefaultSellingPrice(T entity) {
    if (entity.getSellingPrice() != 0.0d) {
      return;
    }
    entity.setSellingPrice(0.0d);
  }

  public void setDefaultReorderLevel(T entity) {
    if (entity.getReorderLevel() != 0.0d) {
      return;
    }
    entity.setReorderLevel(0.0d);
  }

  public void setDefaultReorderQuantity(T entity) {
    if (entity.getReorderQuantity() != 0.0d) {
      return;
    }
    entity.setReorderQuantity(0.0d);
  }

  public void setDefaultStatus(T entity) {
    if (entity.getStatus() != ProductStatus.Active) {
      return;
    }
    entity.setStatus(ProductStatus.Active);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) productRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultPurchasePrice(entity);
    this.setDefaultSellingPrice(entity);
    this.setDefaultReorderLevel(entity);
    this.setDefaultReorderQuantity(entity);
    this.setDefaultStatus(entity);
  }

  @Override
  public void compute(T entity) {}

  private void deleteProductInGoodsReceiptLine(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.goodsReceiptLineRepository.getByProduct(entity))) {
      deletionContext.addEntityError(
          "This Product cannot be deleted as it is being referred to by GoodsReceiptLine.");
    }
  }

  private void deleteProductInInventoryAdjustmentLine(
      T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.inventoryAdjustmentLineRepository.getByProduct(entity))) {
      deletionContext.addEntityError(
          "This Product cannot be deleted as it is being referred to by InventoryAdjustmentLine.");
    }
  }

  private void deleteProductInInventoryMovement(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.inventoryMovementRepository.getByProduct(entity))) {
      deletionContext.addEntityError(
          "This Product cannot be deleted as it is being referred to by InventoryMovement.");
    }
  }

  private void deleteProductInPurchaseOrderLine(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.purchaseOrderLineRepository.getByProduct(entity))) {
      deletionContext.addEntityError(
          "This Product cannot be deleted as it is being referred to by PurchaseOrderLine.");
    }
  }

  private void deleteProductInSalesOrderLine(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.salesOrderLineRepository.getByProduct(entity))) {
      deletionContext.addEntityError(
          "This Product cannot be deleted as it is being referred to by SalesOrderLine.");
    }
  }

  private void deleteProductInSalesReturnLine(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.salesReturnLineRepository.getByProduct(entity))) {
      deletionContext.addEntityError(
          "This Product cannot be deleted as it is being referred to by SalesReturnLine.");
    }
  }

  private void deleteProductInStockAlert(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.stockAlertRepository.getByProduct(entity))) {
      deletionContext.addEntityError(
          "This Product cannot be deleted as it is being referred to by StockAlert.");
    }
  }

  private void deleteProductInStockBatch(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.stockBatchRepository.getByProduct(entity))) {
      deletionContext.addEntityError(
          "This Product cannot be deleted as it is being referred to by StockBatch.");
    }
  }

  private void deleteProductInStockTransferLine(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.stockTransferLineRepository.getByProduct(entity))) {
      deletionContext.addEntityError(
          "This Product cannot be deleted as it is being referred to by StockTransferLine.");
    }
  }

  private void deleteProductInWarehouseStock(T entity, EntityValidationContext deletionContext) {
    if (EntityHelper.haveUnDeleted(this.warehouseStockRepository.getByProduct(entity))) {
      deletionContext.addEntityError(
          "This Product cannot be deleted as it is being referred to by WarehouseStock.");
    }
  }

  public Boolean onDelete(T entity, boolean internal, EntityValidationContext deletionContext) {
    if (entity.getCategory() != null) {
      entity.getCategory().removeFromProducts(entity);
    }
    return true;
  }

  public void validateOnDelete(T entity, EntityValidationContext deletionContext) {
    this.deleteProductInGoodsReceiptLine(entity, deletionContext);
    this.deleteProductInInventoryAdjustmentLine(entity, deletionContext);
    this.deleteProductInInventoryMovement(entity, deletionContext);
    this.deleteProductInPurchaseOrderLine(entity, deletionContext);
    this.deleteProductInSalesOrderLine(entity, deletionContext);
    this.deleteProductInSalesReturnLine(entity, deletionContext);
    this.deleteProductInStockAlert(entity, deletionContext);
    this.deleteProductInStockBatch(entity, deletionContext);
    this.deleteProductInStockTransferLine(entity, deletionContext);
    this.deleteProductInWarehouseStock(entity, deletionContext);
  }

  @Override
  public Boolean onCreate(T entity, boolean internal, EntityValidationContext context) {
    return true;
  }

  @Override
  public Boolean onUpdate(T entity, boolean internal, EntityValidationContext context) {
    return true;
  }

  public T getOld(long id) {
    return ((T) getById(id).clone());
  }
}
