package repository.solr;

@org.springframework.stereotype.Repository
public interface ProductCategorySolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<
        models.ProductCategory, Long> {}
