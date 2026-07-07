package repository.solr;

@org.springframework.stereotype.Repository
public interface GoodsReceiptSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<
        models.GoodsReceipt, Long> {}
