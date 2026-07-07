package models;

import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.DBObject;
import store.DatabaseObject;
import store.ICloneable;

@SolrDocument(collection = "SupplierContact")
@Entity
public class SupplierContact extends CreatableObject {
  public static final int _NAME = 0;
  public static final int _EMAIL = 1;
  public static final int _PHONE = 2;
  public static final int _ISPRIMARY = 3;
  public static final int _VENDOR = 4;
  @NotNull private String name;
  private String email;
  private String phone;
  private boolean isPrimary = false;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Vendor vendor;

  public SupplierContact() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.SupplierContact;
  }

  @Override
  public String _type() {
    return "SupplierContact";
  }

  @Override
  public int _fieldsCount() {
    return 5;
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

  public boolean isIsPrimary() {
    _checkProxy();
    return this.isPrimary;
  }

  public void setIsPrimary(boolean isPrimary) {
    _checkProxy();
    if (Objects.equals(this.isPrimary, isPrimary)) {
      return;
    }
    fieldChanged(_ISPRIMARY, this.isPrimary, isPrimary);
    this.isPrimary = isPrimary;
  }

  public Vendor getVendor() {
    _checkProxy();
    return this.vendor;
  }

  public void setVendor(Vendor vendor) {
    _checkProxy();
    if (Objects.equals(this.vendor, vendor)) {
      return;
    }
    fieldChanged(_VENDOR, this.vendor, vendor);
    if (!(isOld) && this.vendor != null) {
      this.vendor.removeFromContacts(this);
    }
    this.vendor = vendor;
    if (!(isOld) && vendor != null && !(vendor.getContacts().contains(this))) {
      vendor.addToContacts(this, -1);
    }
  }

  public String displayName() {
    return this.getName();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof SupplierContact && super.equals(a);
  }

  public SupplierContact deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    SupplierContact _obj = ((SupplierContact) dbObj);
    _obj.setName(name);
    _obj.setEmail(email);
    _obj.setPhone(phone);
    _obj.setIsPrimary(isPrimary);
    _obj.setVendor(vendor);
  }

  public SupplierContact cloneInstance(SupplierContact cloneObj) {
    if (cloneObj == null) {
      cloneObj = new SupplierContact();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setName(this.getName());
    cloneObj.setEmail(this.getEmail());
    cloneObj.setPhone(this.getPhone());
    cloneObj.setIsPrimary(this.isIsPrimary());
    cloneObj.setVendor(this.getVendor());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public SupplierContact createNewInstance() {
    return new SupplierContact();
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.vendor);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
