package repository.solr;

@org.springframework.stereotype.Repository
public interface SupplierContactSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<
        models.SupplierContact, Long> {}
