package classes;

import d3e.core.ListExt;
import java.util.List;
import models.Report;
import models.ReportData;
import models.ReportModel;
import rest.DynamicReportService;

public class ServerReportUtils {
  private static List<ReportModel> schema;

  public static String getTenantRule(String model) {
    return null;
  }

  public static List<ReportModel> getSupportedModelSchema() {
    if (schema != null) return schema;
    List<List<String>> reportSchema = ListExt.List();
    schema = DynamicReportService.get().prepareSchema(reportSchema);
    return schema;
  }

  public static ReportData execute(Report report, List<ReportInput> asList) {
    return DynamicReportService.get()
        .execute(report, asList, getTenantRule(report.getModel()), null);
  }

  public static List<ReportFilterValue> getFilterValues(String model, String property) {
    return DynamicReportService.get().getFilterValues(model, property, getTenantRule(model), null);
  }
}
