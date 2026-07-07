package repository.solr;

@org.springframework.stereotype.Repository
public interface UserLoginRecordSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<
        models.UserLoginRecord, Long> {}
