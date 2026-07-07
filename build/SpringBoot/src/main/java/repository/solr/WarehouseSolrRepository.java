package repository.solr;

@org.springframework.stereotype.Repository
public interface WarehouseSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<models.Warehouse, Long> {}
