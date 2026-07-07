package classes;

import java.time.LocalDateTime;
import models.Organization;
import store.Database;

public class OrganizationService {
  public OrganizationService() {}

  public static Organization createOrganization(Organization organization) {
    if (organization == null) {
      throw new RuntimeException("Organization cannot be null");
    }
    organization.setCreatedAt(LocalDateTime.now());
    organization.setUpdatedAt(LocalDateTime.now());
    Database.get().save(organization);
    return organization;
  }

  public static Organization updateOrganization(Organization organization) {
    if (organization == null) {
      throw new RuntimeException("Organization cannot be null");
    }
    organization.setUpdatedAt(LocalDateTime.now());
    Database.get().save(organization);
    return organization;
  }

  public static void deleteOrganization(Organization organization) {
    if (organization == null) {
      return;
    }
    Database.get().delete(organization);
  }
}
