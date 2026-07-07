package repository.solr;

@org.springframework.stereotype.Repository
public interface SalesOrderLineSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<
        models.SalesOrderLine, Long> {}
