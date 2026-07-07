package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class GoodsReceiptItemRequest extends CreatableObject {
  public static final int _GOODSRECEIPT = 0;
  private GoodsReceipt goodsReceipt;

  public GoodsReceiptItemRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.GoodsReceiptItemRequest;
  }

  @Override
  public String _type() {
    return "GoodsReceiptItemRequest";
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

  public GoodsReceipt getGoodsReceipt() {
    _checkProxy();
    return this.goodsReceipt;
  }

  public void setGoodsReceipt(GoodsReceipt goodsReceipt) {
    _checkProxy();
    if (Objects.equals(this.goodsReceipt, goodsReceipt)) {
      return;
    }
    fieldChanged(_GOODSRECEIPT, this.goodsReceipt, goodsReceipt);
    this.goodsReceipt = goodsReceipt;
  }

  public String displayName() {
    return "GoodsReceiptItemRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof GoodsReceiptItemRequest && super.equals(a);
  }

  public GoodsReceiptItemRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    GoodsReceiptItemRequest _obj = ((GoodsReceiptItemRequest) dbObj);
    _obj.setGoodsReceipt(goodsReceipt);
  }

  public GoodsReceiptItemRequest cloneInstance(GoodsReceiptItemRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new GoodsReceiptItemRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setGoodsReceipt(this.getGoodsReceipt());
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public GoodsReceiptItemRequest createNewInstance() {
    return new GoodsReceiptItemRequest();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.goodsReceipt);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
