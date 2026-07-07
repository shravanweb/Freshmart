package repository.solr;

@org.springframework.stereotype.Repository
public interface StoreSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<models.Store, Long> {}
