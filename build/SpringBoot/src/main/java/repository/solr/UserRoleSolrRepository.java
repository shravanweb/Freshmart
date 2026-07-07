package repository.solr;

@org.springframework.stereotype.Repository
public interface UserRoleSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<models.UserRole, Long> {}
