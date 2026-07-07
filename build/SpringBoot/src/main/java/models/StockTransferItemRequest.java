package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class StockTransferItemRequest extends CreatableObject {
  public static final int _STOCKTRANSFER = 0;
  private StockTransfer stockTransfer;

  public StockTransferItemRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.StockTransferItemRequest;
  }

  @Override
  public String _type() {
    return "StockTransferItemRequest";
  }

  @Override
  public int _fieldsCount() {
    return 1;
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public StockTransfer getStockTransfer() {
    _checkProxy();
    return this.stockTransfer;
  }

  public void setStockTransfer(StockTransfer stockTransfer) {
    _checkProxy();
    if (Objects.equals(this.stockTransfer, stockTransfer)) {
      return;
    }
    fieldChanged(_STOCKTRANSFER, this.stockTransfer, stockTransfer);
    this.stockTransfer = stockTransfer;
  }

  public String displayName() {
    return "StockTransferItemRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof StockTransferItemRequest && super.equals(a);
  }

  public StockTransferItemRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    StockTransferItemRequest _obj = ((StockTransferItemRequest) dbObj);
    _obj.setStockTransfer(stockTransfer);
  }

  public StockTransferItemRequest cloneInstance(StockTransferItemRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new StockTransferItemRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setStockTransfer(this.getStockTransfer());
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public StockTransferItemRequest createNewInstance() {
    return new StockTransferItemRequest();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.stockTransfer);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
