package repository.solr;

@org.springframework.stereotype.Repository
public interface UserProfileSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<models.UserProfile, Long> {}
