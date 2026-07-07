package repository.solr;

@org.springframework.stereotype.Repository
public interface SalesReturnLineSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<
        models.SalesReturnLine, Long> {}
