package repository.solr;

@org.springframework.stereotype.Repository
public interface InventoryAdjustmentSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<
        models.InventoryAdjustment, Long> {}
