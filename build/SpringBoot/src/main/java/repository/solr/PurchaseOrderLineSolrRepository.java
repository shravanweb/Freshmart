package repository.solr;

@org.springframework.stereotype.Repository
public interface PurchaseOrderLineSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<
        models.PurchaseOrderLine, Long> {}
