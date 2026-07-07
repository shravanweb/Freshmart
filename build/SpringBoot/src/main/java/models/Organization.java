package models;

import classes.OrganizationStatus;
import d3e.core.CloneContext;
import d3e.core.DFile;
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

@SolrDocument(collection = "Organization")
@Entity
public class Organization extends CreatableObject {
  public static final int _NAME = 0;
  public static final int _CODE = 1;
  public static final int _LEGALNAME = 2;
  public static final int _TAXID = 3;
  public static final int _EMAIL = 4;
  public static final int _PHONE = 5;
  public static final int _ADDRESS = 6;
  public static final int _LOGO = 7;
  public static final int _CURRENCY = 8;
  public static final int _TIMEZONE = 9;
  public static final int _STATUS = 10;
  public static final int _CREATEDAT = 11;
  public static final int _UPDATEDAT = 12;
  public static final int _CREATEDBY = 13;
  @NotNull private String name;
  @NotNull private String code;
  private String legalName;
  private String taxId;
  private String email;
  private String phone;
  private String address;

  @ManyToOne(fetch = FetchType.LAZY)
  private DFile logo;

  @NotNull private String currency = "USD";
  private String timezone = "UTC";

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private OrganizationStatus status = OrganizationStatus.Active;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  private User createdBy;

  private transient Organization old;

  public Organization() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.Organization;
  }

  @Override
  public String _type() {
    return "Organization";
  }

  @Override
  public int _fieldsCount() {
    return 14;
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

  public String getLegalName() {
    _checkProxy();
    return this.legalName;
  }

  public void setLegalName(String legalName) {
    _checkProxy();
    if (Objects.equals(this.legalName, legalName)) {
      return;
    }
    fieldChanged(_LEGALNAME, this.legalName, legalName);
    this.legalName = legalName;
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

  public DFile getLogo() {
    _checkProxy();
    return this.logo;
  }

  public void setLogo(DFile logo) {
    _checkProxy();
    if (Objects.equals(this.logo, logo)) {
      return;
    }
    fieldChanged(_LOGO, this.logo, logo);
    this.logo = logo;
  }

  public String getCurrency() {
    _checkProxy();
    return this.currency;
  }

  public void setCurrency(String currency) {
    _checkProxy();
    if (Objects.equals(this.currency, currency)) {
      return;
    }
    fieldChanged(_CURRENCY, this.currency, currency);
    this.currency = currency;
  }

  public String getTimezone() {
    _checkProxy();
    return this.timezone;
  }

  public void setTimezone(String timezone) {
    _checkProxy();
    if (Objects.equals(this.timezone, timezone)) {
      return;
    }
    fieldChanged(_TIMEZONE, this.timezone, timezone);
    this.timezone = timezone;
  }

  public OrganizationStatus getStatus() {
    _checkProxy();
    return this.status;
  }

  public void setStatus(OrganizationStatus status) {
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

  public User getCreatedBy() {
    _checkProxy();
    return this.createdBy;
  }

  public void setCreatedBy(User createdBy) {
    _checkProxy();
    if (Objects.equals(this.createdBy, createdBy)) {
      return;
    }
    fieldChanged(_CREATEDBY, this.createdBy, createdBy);
    this.createdBy = createdBy;
  }

  public Organization getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((Organization) old);
  }

  public String displayName() {
    return this.getName();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof Organization && super.equals(a);
  }

  public Organization deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    Organization _obj = ((Organization) dbObj);
    _obj.setName(name);
    _obj.setCode(code);
    _obj.setLegalName(legalName);
    _obj.setTaxId(taxId);
    _obj.setEmail(email);
    _obj.setPhone(phone);
    _obj.setAddress(address);
    _obj.setLogo(logo);
    _obj.setCurrency(currency);
    _obj.setTimezone(timezone);
    _obj.setStatus(status);
    _obj.setCreatedAt(createdAt);
    _obj.setUpdatedAt(updatedAt);
    _obj.setCreatedBy(createdBy);
  }

  public Organization cloneInstance(Organization cloneObj) {
    if (cloneObj == null) {
      cloneObj = new Organization();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setName(this.getName());
    cloneObj.setCode(this.getCode());
    cloneObj.setLegalName(this.getLegalName());
    cloneObj.setTaxId(this.getTaxId());
    cloneObj.setEmail(this.getEmail());
    cloneObj.setPhone(this.getPhone());
    cloneObj.setAddress(this.getAddress());
    cloneObj.setLogo(this.getLogo());
    cloneObj.setCurrency(this.getCurrency());
    cloneObj.setTimezone(this.getTimezone());
    cloneObj.setStatus(this.getStatus());
    cloneObj.setCreatedAt(this.getCreatedAt());
    cloneObj.setUpdatedAt(this.getUpdatedAt());
    cloneObj.setCreatedBy(this.getCreatedBy());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public Organization createNewInstance() {
    return new Organization();
  }

  public boolean needOldObject() {
    return true;
  }

  public void collectCreatableReferences(List<Object> _refs) {
    super.collectCreatableReferences(_refs);
    _refs.add(this.logo);
    _refs.add(this.createdBy);
  }

  @Override
  public boolean _isEntity() {
    return true;
  }
}
