package repository.solr;

@org.springframework.stereotype.Repository
public interface StockAlertSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<models.StockAlert, Long> {}
