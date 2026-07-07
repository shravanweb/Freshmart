package repository.solr;

@org.springframework.stereotype.Repository
public interface InAppNotificationSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<
        models.InAppNotification, Long> {}
