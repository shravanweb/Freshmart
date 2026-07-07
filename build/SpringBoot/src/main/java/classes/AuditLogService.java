package classes;

import java.time.LocalDateTime;
import models.AuditLog;
import store.Database;

public class AuditLogService {
  public AuditLogService() {}

  public static AuditLog createAuditLog(AuditLog auditLog) {
    if (auditLog == null) {
      throw new RuntimeException("AuditLog cannot be null");
    }
    if (auditLog.getPerformedAt() == null) {
      auditLog.setPerformedAt(LocalDateTime.now());
    }
    Database.get().save(auditLog);
    return auditLog;
  }

  public static AuditLog updateAuditLog(AuditLog auditLog) {
    if (auditLog == null) {
      throw new RuntimeException("AuditLog cannot be null");
    }
    Database.get().save(auditLog);
    return auditLog;
  }

  public static void deleteAuditLog(AuditLog auditLog) {
    if (auditLog == null) {
      return;
    }
    Database.get().delete(auditLog);
  }
}
