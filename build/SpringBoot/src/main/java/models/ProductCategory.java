package models;

import classes.EntityStatus;
import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.D3EPersistanceList;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

@SolrDocument(collection = "ProductCategory")
@Entity
public class ProductCategory extends CreatableObject {
  public static final int _PARENTCATEGORY = 0;
  public static final int _CHILDCATEGORIES = 1;
  public static final int _PRODUCTS = 2;
  public static final int _NAME = 3;
  public static final int _CODE = 4;
  public static final int _DESCRIPTION = 5;
  public static final int _STATUS = 6;
  public static final int _ORGANIZATION = 7;

  @ManyToOne(fetch = FetchType.LAZY)
  private ProductCategory parentCategory;

  @ManyToMany(mappedBy = "parentCategory")
  private List<ProductCategory> childCategories =
      D3EPersistanceList.inverse(this, _CHILDCATEGORIES);

  @ManyToMany(mappedBy = "category")
  private List<Product> products = D3EPersistanceList.inverse(this, _PRODUCTS);

  @NotNull private String name;
  @NotNull private String code;
  private String description;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private EntityStatus status = EntityStatus.Active;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  private transient ProductCategory old;

  public ProductCategory() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.ProductCategory;
  }

  @Override
  public String _type() {
    return "ProductCategory";
  }

  @Override
  public int _fieldsCount() {
    return 8;
  }

  public void addToChildCategories(ProductCategory val, long index) {
    if (index == -1) {
      this.childCategories.add(val);
    } else {
      this.childCategories.add(((int) index), val);
    }
  }

  public void removeFromChildCategories(ProductCategory val) {
    this.childCategories.remove(val);
  }

  public void addToProducts(Product val, long index) {
    if (index == -1) {
      this.products.add(val);
    } else {
      this.products.add(((int) index), val);
    }
  }

  public void removeFromProducts(Product val) {
    this.products.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void visitChildren(Consumer<DBObject> visitor) {
    super.visitChildren(visitor);
  }

  public ProductCategory getParentCategory() {
    _checkProxy();
    return this.parentCategory;
  }

  public void setParentCategory(ProductCategory parentCategory) {
    _checkProxy();
    if (Objects.equals(this.parentCategory, parentCategory)) {
      return;
    }
    fieldChanged(_PARENTCATEGORY, this.parentCategory, parentCategory);
    if (!(isOld) && this.parentCategory != null) {
      this.parentCategory.removeFromChildCategories(this);
    }
    this.parentCategory = parentCategory;
    if (!(isOld)
        && parentCategory != null
        && !(parentCategory.getChildCategories().contains(this))) {
      parentCategory.addToChildCategories(this, -1);
    }
  }

  public List<ProductCategory> getChildCategories() {
    return this.childCategories;
  }

  public void setChildCategories(List<ProductCategory> childCategories) {
    if (Objects.equals(this.childCategories, childCategories)) {
      return;
    }
    ((D3EPersistanceList<ProductCategory>) this.childCategories).setAll(childCategories);
  }

  public List<Product> getProducts() {
    return this.products;
  }

  public void setProducts(List<Product> products) {
    if (Objects.equals(this.products, products)) {
      return;
    }
    ((D3EPersistanceList<Product>) this.products).setAll(products);
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

  public ProductCategory getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((ProductCategory) old);
  }

  public String displayName() {
    return this.getName();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ProductCategory && super.equals(a);
  }

  public ProductCategory deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    ProductCategory _obj = ((ProductCategory) dbObj);
    _obj.setParentCategory(parentCategory);
    _obj.setChildCategories(childCategories);
    _obj.setProducts(products);
    _obj.setName(name);
    _obj.setCode(code);
    _obj.setDescription(description);
    _obj.setStatus(status);
    _obj.setOrganization(organization);
  }

  public ProductCategory cloneInstance(ProductCategory cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ProductCategory();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setParentCategory(this.getParentCategory());
    cloneObj.setChildCategories(new ArrayList<>(this.getChildCategories()));
    cloneObj.setProducts(new ArrayList<>(this.getProducts()));
    cloneObj.setName(this.getName());
    cloneObj.setCode(this.getCode());
    cloneObj.setDescription(this.getDescription());
    cloneObj.setStatus(this.getStatus());
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public ProductCategory createNewInstance() {
    return new ProductCategory();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.parentCategory);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
