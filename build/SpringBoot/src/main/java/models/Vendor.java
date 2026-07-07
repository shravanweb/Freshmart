package models;

import classes.EntityStatus;
import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.persistence.CascadeType;
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

@SolrDocument(collection = "Vendor")
@Entity
public class Vendor extends CreatableObject {
  public static final int _NAME = 0;
  public static final int _CODE = 1;
  public static final int _CONTACTPERSON = 2;
  public static final int _EMAIL = 3;
  public static final int _PHONE = 4;
  public static final int _ADDRESS = 5;
  public static final int _PAYMENTTERMS = 6;
  public static final int _TAXID = 7;
  public static final int _RATING = 8;
  public static final int _CONTACTS = 9;
  public static final int _STATUS = 10;
  public static final int _CREATEDAT = 11;
  public static final int _ORGANIZATION = 12;
  @NotNull private String name;
  @NotNull private String code;
  private String contactPerson;
  private String email;
  private String phone;
  private String address;
  private String paymentTerms;
  private String taxId;
  private long rating = 0l;

  @ManyToMany(mappedBy = "vendor", cascade = CascadeType.REMOVE)
  private List<SupplierContact> contacts = D3EPersistanceList.inverse(this, _CONTACTS);

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private EntityStatus status = EntityStatus.Active;

  private LocalDateTime createdAt;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  private transient Vendor old;

  public Vendor() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.Vendor;
  }

  @Override
  public String _type() {
    return "Vendor";
  }

  @Override
  public int _fieldsCount() {
    return 13;
  }

  public void addToContacts(SupplierContact val, long index) {
    if (index == -1) {
      this.contacts.add(val);
    } else {
      this.contacts.add(((int) index), val);
    }
  }

  public void removeFromContacts(SupplierContact val) {
    this.contacts.remove(val);
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

  public String getContactPerson() {
    _checkProxy();
    return this.contactPerson;
  }

  public void setContactPerson(String contactPerson) {
    _checkProxy();
    if (Objects.equals(this.contactPerson, contactPerson)) {
      return;
    }
    fieldChanged(_CONTACTPERSON, this.contactPerson, contactPerson);
    this.contactPerson = contactPerson;
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

  public String getPaymentTerms() {
    _checkProxy();
    return this.paymentTerms;
  }

  public void setPaymentTerms(String paymentTerms) {
    _checkProxy();
    if (Objects.equals(this.paymentTerms, paymentTerms)) {
      return;
    }
    fieldChanged(_PAYMENTTERMS, this.paymentTerms, paymentTerms);
    this.paymentTerms = paymentTerms;
  }

  public String getTaxId() {
    _checkProxy();
    return this.taxId;
  }

  public void setTaxId(String taxId) {
    _checkProxy();
    if (Objects.equals(this.taxId, taxId)) {
      return;
    }
    fieldChanged(_TAXID, this.taxId, taxId);
    this.taxId = taxId;
  }

  public long getRating() {
    _checkProxy();
    return this.rating;
  }

  public void setRating(long rating) {
    _checkProxy();
    if (Objects.equals(this.rating, rating)) {
      return;
    }
    fieldChanged(_RATING, this.rating, rating);
    this.rating = rating;
  }

  public List<SupplierContact> getContacts() {
    return this.contacts;
  }

  public void setContacts(List<SupplierContact> contacts) {
    if (Objects.equals(this.contacts, contacts)) {
      return;
    }
    ((D3EPersistanceList<SupplierContact>) this.contacts).setAll(contacts);
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

  public Vendor getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((Vendor) old);
  }

  public String displayName() {
    return this.getName();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof Vendor && super.equals(a);
  }

  public Vendor deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    Vendor _obj = ((Vendor) dbObj);
    _obj.setName(name);
    _obj.setCode(code);
    _obj.setContactPerson(contactPerson);
    _obj.setEmail(email);
    _obj.setPhone(phone);
    _obj.setAddress(address);
    _obj.setPaymentTerms(paymentTerms);
    _obj.setTaxId(taxId);
    _obj.setRating(rating);
    _obj.setContacts(contacts);
    _obj.setStatus(status);
    _obj.setCreatedAt(createdAt);
    _obj.setOrganization(organization);
  }

  public Vendor cloneInstance(Vendor cloneObj) {
    if (cloneObj == null) {
      cloneObj = new Vendor();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setName(this.getName());
    cloneObj.setCode(this.getCode());
    cloneObj.setContactPerson(this.getContactPerson());
    cloneObj.setEmail(this.getEmail());
    cloneObj.setPhone(this.getPhone());
    cloneObj.setAddress(this.getAddress());
    cloneObj.setPaymentTerms(this.getPaymentTerms());
    cloneObj.setTaxId(this.getTaxId());
    cloneObj.setRating(this.getRating());
    cloneObj.setContacts(new ArrayList<>(this.getContacts()));
    cloneObj.setStatus(this.getStatus());
    cloneObj.setCreatedAt(this.getCreatedAt());
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public Vendor createNewInstance() {
    return new Vendor();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.organization);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
