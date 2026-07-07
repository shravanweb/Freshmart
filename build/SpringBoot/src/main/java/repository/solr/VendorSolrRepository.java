package repository.solr;

@org.springframework.stereotype.Repository
public interface VendorSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<models.Vendor, Long> {}
