package repository.solr;

@org.springframework.stereotype.Repository
public interface AuditLogSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<models.AuditLog, Long> {}
