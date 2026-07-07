package repository.solr;

@org.springframework.stereotype.Repository
public interface StockTransferLineSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<
        models.StockTransferLine, Long> {}
