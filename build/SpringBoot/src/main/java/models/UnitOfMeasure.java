package models;

import classes.EntityStatus;
import classes.UomType;
import d3e.core.CloneContext;
import d3e.core.SchemaConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
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

@SolrDocument(collection = "UnitOfMeasure")
@Entity
public class UnitOfMeasure extends CreatableObject {
  public static final int _NAME = 0;
  public static final int _SYMBOL = 1;
  public static final int _UOMTYPE = 2;
  public static final int _BASEFACTOR = 3;
  public static final int _STATUS = 4;
  public static final int _ORGANIZATION = 5;
  @NotNull private String name;
  @NotNull private String symbol;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private UomType uomType = UomType.Base;

  private double baseFactor = 1.0d;

  @NotNull
  @Enumerated(jakarta.persistence.EnumType.STRING)
  private EntityStatus status = EntityStatus.Active;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  private transient UnitOfMeasure old;

  public UnitOfMeasure() {
    super();
  }

  @Override
  public int _typeIdx() {
    return SchemaConstants.UnitOfMeasure;
  }

  @Override
  public String _type() {
    return "UnitOfMeasure";
  }

  @Override
  public int _fieldsCount() {
    return 6;
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

  public String getSymbol() {
    _checkProxy();
    return this.symbol;
  }

  public void setSymbol(String symbol) {
    _checkProxy();
    if (Objects.equals(this.symbol, symbol)) {
      return;
    }
    fieldChanged(_SYMBOL, this.symbol, symbol);
    this.symbol = symbol;
  }

  public UomType getUomType() {
    _checkProxy();
    return this.uomType;
  }

  public void setUomType(UomType uomType) {
    _checkProxy();
    if (Objects.equals(this.uomType, uomType)) {
      return;
    }
    fieldChanged(_UOMTYPE, this.uomType, uomType);
    this.uomType = uomType;
  }

  public double getBaseFactor() {
    _checkProxy();
    return this.baseFactor;
  }

  public void setBaseFactor(double baseFactor) {
    _checkProxy();
    if (Objects.equals(this.baseFactor, baseFactor)) {
      return;
    }
    fieldChanged(_BASEFACTOR, this.baseFactor, baseFactor);
    this.baseFactor = baseFactor;
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

  public UnitOfMeasure getOld() {
    return this.old;
  }

  public void setOld(DatabaseObject old) {
    this.old = ((UnitOfMeasure) old);
  }

  public String displayName() {
    return this.getName();
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof UnitOfMeasure && super.equals(a);
  }

  public UnitOfMeasure deepClone(boolean clearId) {
    CloneContext ctx = new CloneContext(clearId);
    return ctx.startClone(this);
  }

  public void deepCloneIntoObj(ICloneable dbObj, CloneContext ctx) {
    super.deepCloneIntoObj(dbObj, ctx);
    UnitOfMeasure _obj = ((UnitOfMeasure) dbObj);
    _obj.setName(name);
    _obj.setSymbol(symbol);
    _obj.setUomType(uomType);
    _obj.setBaseFactor(baseFactor);
    _obj.setStatus(status);
    _obj.setOrganization(organization);
  }

  public UnitOfMeasure cloneInstance(UnitOfMeasure cloneObj) {
    if (cloneObj == null) {
      cloneObj = new UnitOfMeasure();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setName(this.getName());
    cloneObj.setSymbol(this.getSymbol());
    cloneObj.setUomType(this.getUomType());
    cloneObj.setBaseFactor(this.getBaseFactor());
    cloneObj.setStatus(this.getStatus());
    cloneObj.setOrganization(this.getOrganization());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }

  public UnitOfMeasure createNewInstance() {
    return new UnitOfMeasure();
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
