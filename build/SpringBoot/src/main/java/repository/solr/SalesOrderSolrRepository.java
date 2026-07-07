package repository.solr;

@org.springframework.stereotype.Repository
public interface SalesOrderSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<models.SalesOrder, Long> {}
