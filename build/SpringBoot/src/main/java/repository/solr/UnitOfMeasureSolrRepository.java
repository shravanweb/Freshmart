package repository.solr;

@org.springframework.stereotype.Repository
public interface UnitOfMeasureSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<
        models.UnitOfMeasure, Long> {}
