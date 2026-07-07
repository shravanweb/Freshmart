package repository.solr;

@org.springframework.stereotype.Repository
public interface SalesReturnSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<models.SalesReturn, Long> {}
