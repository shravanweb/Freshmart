package models;

import classes.ProductStatus;
import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

public class ProductSearchRequest extends CreatableObject {
  public static final int _ORGANIZATION = 0;
  public static final int _SEARCHTERM = 1;
  public static final int _CATEGORY = 2;
  public static final int _STATUS = 3;
  private Organization organization;
  private String searchTerm;
  private ProductCategory category;
  private ProductStatus status = ProductStatus.Active;

  public ProductSearchRequest() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ProductSearchRequest;
  }

  @Override
  public String _type() {
    return "ProductSearchRequest";
  }

  @Override
  public int _fieldsCount() {
    return 4;
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
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

  public String getSearchTerm() {
    _checkProxy();
    return this.searchTerm;
  }

  public void setSearchTerm(String searchTerm) {
    _checkProxy();
    if (Objects.equals(this.searchTerm, searchTerm)) {
      return;
    }
    fieldChanged(_SEARCHTERM, this.searchTerm, searchTerm);
    this.searchTerm = searchTerm;
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
    this.category = category;
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

  public String displayName() {
    return "ProductSearchRequest";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ProductSearchRequest && super.equals(a);
  }

  public ProductSearchRequest deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ProductSearchRequest _obj = ((ProductSearchRequest) dbObj);
    _obj.setOrganization(organization);
    _obj.setSearchTerm(searchTerm);
    _obj.setCategory(category);
    _obj.setStatus(status);
  }

  public ProductSearchRequest cloneInstance(ProductSearchRequest cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ProductSearchRequest();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setOrganization(this.getOrganization());
    cloneObj.setSearchTerm(this.getSearchTerm());
    cloneObj.setCategory(this.getCategory());
    cloneObj.setStatus(this.getStatus());
    return cloneObj;
  }

  public boolean transientModel() {
    return true;
  }

  public ProductSearchRequest createNewInstance() {
    return new ProductSearchRequest();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.organization);
    _refs.add(this.category);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
