package repository.solr;

@org.springframework.stereotype.Repository
public interface GoodsReceiptLineSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<
        models.GoodsReceiptLine, Long> {}
