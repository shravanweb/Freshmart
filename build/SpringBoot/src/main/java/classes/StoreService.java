package classes;

import java.time.LocalDateTime;
import models.Store;
import store.Database;

public class StoreService {
  public StoreService() {}

  public static Store createStore(Store store) {
    if (store == null) {
      throw new RuntimeException("Store cannot be null");
    }
    store.setCreatedAt(LocalDateTime.now());
    store.setUpdatedAt(LocalDateTime.now());
    Database.get().save(store);
    return store;
  }

  public static Store updateStore(Store store) {
    if (store == null) {
      throw new RuntimeException("Store cannot be null");
    }
    store.setUpdatedAt(LocalDateTime.now());
    Database.get().save(store);
    return store;
  }

  public static void deleteStore(Store store) {
    if (store == null) {
      return;
    }
    Database.get().delete(store);
  }
}
