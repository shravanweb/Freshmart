package repository.solr;

@org.springframework.stereotype.Repository
public interface StockBatchSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<models.StockBatch, Long> {}
