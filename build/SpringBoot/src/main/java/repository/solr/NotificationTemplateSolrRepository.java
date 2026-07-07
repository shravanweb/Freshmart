package repository.solr;

@org.springframework.stereotype.Repository
public interface NotificationTemplateSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<
        models.NotificationTemplate, Long> {}
