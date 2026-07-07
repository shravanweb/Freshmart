package repository.solr;

@org.springframework.stereotype.Repository
public interface OrganizationSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<
        models.Organization, Long> {}
