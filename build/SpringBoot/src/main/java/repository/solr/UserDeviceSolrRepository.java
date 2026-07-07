package repository.solr;

@org.springframework.stereotype.Repository
public interface UserDeviceSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<models.UserDevice, Long> {}
