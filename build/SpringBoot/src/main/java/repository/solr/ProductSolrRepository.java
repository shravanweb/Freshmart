package repository.solr;

@org.springframework.stereotype.Repository
public interface ProductSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<models.Product, Long> {}
