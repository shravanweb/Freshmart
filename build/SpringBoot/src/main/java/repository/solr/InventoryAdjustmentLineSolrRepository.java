package repository.solr;

@org.springframework.stereotype.Repository
public interface InventoryAdjustmentLineSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<
        models.InventoryAdjustmentLine, Long> {}
