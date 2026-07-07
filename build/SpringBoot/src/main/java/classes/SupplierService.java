package classes;

import java.time.LocalDateTime;
import models.SupplierContact;
import models.Vendor;
import store.Database;

public class SupplierService {
  public SupplierService() {}

  public static Vendor createSupplier(Vendor supplier) {
    if (supplier == null) {
      throw new RuntimeException("Vendor cannot be null");
    }
    supplier.setCreatedAt(LocalDateTime.now());
    Database.get().save(supplier);
    return supplier;
  }

  public static Vendor updateSupplier(Vendor supplier) {
    if (supplier == null) {
      throw new RuntimeException("Vendor cannot be null");
    }
    Database.get().save(supplier);
    return supplier;
  }

  public static void deleteSupplier(Vendor supplier) {
    if (supplier == null) {
      return;
    }
    Database.get().delete(supplier);
  }

  public static SupplierContact createSupplierContact(SupplierContact supplierContact) {
    if (supplierContact == null) {
      throw new RuntimeException("SupplierContact cannot be null");
    }
    Database.get().save(supplierContact);
    return supplierContact;
  }

  public static SupplierContact updateSupplierContact(SupplierContact supplierContact) {
    if (supplierContact == null) {
      throw new RuntimeException("SupplierContact cannot be null");
    }
    Database.get().save(supplierContact);
    return supplierContact;
  }

  public static void deleteSupplierContact(SupplierContact supplierContact) {
    if (supplierContact == null) {
      return;
    }
    Database.get().delete(supplierContact);
  }
}
