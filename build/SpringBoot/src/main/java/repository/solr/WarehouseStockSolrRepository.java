package repository.solr;

@org.springframework.stereotype.Repository
public interface WarehouseStockSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<
        models.WarehouseStock, Long> {}
