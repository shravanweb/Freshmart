package repository.solr;

@org.springframework.stereotype.Repository
public interface PurchaseOrderSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<
        models.PurchaseOrder, Long> {}
