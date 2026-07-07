package repository.solr;

@org.springframework.stereotype.Repository
public interface UserInvitationSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<
        models.UserInvitation, Long> {}
