package repository.solr;

@org.springframework.stereotype.Repository
public interface InventoryMovementSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<
        models.InventoryMovement, Long> {}
