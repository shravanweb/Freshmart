package repository.jpa;

import d3e.core.SchemaConstants;
import models.Report;
import org.springframework.stereotype.Service;

@Service
public class ReportRepository extends AbstractD3ERepository<Report> {
  public int getTypeIndex() {
    return SchemaConstants.Report;
  }
}
