package rest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import classes.ReportAggregateType;
import classes.ReportFieldFromType;
import classes.ReportFilterValue;
import classes.ReportInput;
import d3e.core.AggregateExpression;
import d3e.core.Criteria;
import d3e.core.Criterion;
import d3e.core.ListExt;
import d3e.core.MapExt;
import d3e.core.MultiExpression;
import d3e.core.PropertyExpression;
import d3e.core.RawResult;
import d3e.core.Restrictions;
import gqltosql.schema.DField;
import gqltosql.schema.DModel;
import gqltosql.schema.FieldPrimitiveType;
import gqltosql.schema.FieldType;
import gqltosql.schema.IModelSchema;
import jakarta.annotation.PostConstruct;
import models.Report;
import models.ReportBarChartConfig;
import models.ReportBaseConfig;
import models.ReportCardConfig;
import models.ReportData;
import models.ReportDataRow;
import models.ReportDataSection;
import models.ReportField;
import models.ReportFilter;
import models.ReportFunnelChartConfig;
import models.ReportGuageConfig;
import models.ReportKPIConfig;
import models.ReportKeyInfluencerConfig;
import models.ReportLineAndAreaChartConfig;
import models.ReportLineAndColumnChartConfig;
import models.ReportMapConfig;
import models.ReportMatrixConfig;
import models.ReportModel;
import models.ReportMultiRowCardConfig;
import models.ReportNamedConditionFilter;
import models.ReportPieChartConfig;
import models.ReportProperty;
import models.ReportPropertyFilter;
import models.ReportRule;
import models.ReportRuleSet;
import models.ReportScatterChartConfig;
import models.ReportSingleRule;
import models.ReportSlicerConfig;
import models.ReportTableConfig;
import models.ReportWaterfallChartConfig;
import store.CustomFieldService;
import store.DatabaseObject;
import store.ICustomFieldProcessor;

@Service
public class DynamicReportService {

	private static DynamicReportService INS;

	private DateTimeFormatter d3eDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private DateTimeFormatter d3eTimeFormatter = DateTimeFormatter.ofPattern("HH:mm a");
	private DateTimeFormatter d3eDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a");
	@Autowired
	private IModelSchema schema;

	public static DynamicReportService get() {
		return INS;
	}

	@PostConstruct
	public void init() {
		INS = this;
	}

	public ReportData execute(Report report, List<ReportInput> inputs, String tenantPath, DatabaseObject tenant) {
		ReportData data = new ReportData();
		if (report == null || report.getConfig() == null) {
			ReportDataSection sec = new ReportDataSection();
			sec.addToColumns("Select or drag fields to populate this visual", -1);
			data.addToSections(sec, -1);
			return data;
		}

		Criteria c = Criteria.on(report.getModel());
		ReportSummary summary = new ReportSummary();
		applyReportConfiguration(report.getConfig(), summary);

		if (report.getCriteria() != null) {
			Map<String, ReportFilter> filters = MapExt.Map();
			for (ReportFilter filter : report.getFilters()) {
				if (filter instanceof ReportPropertyFilter) {
					filters.put(((ReportPropertyFilter) filter).getName(), filter);
				} else {
					filters.put(((ReportNamedConditionFilter) filter).getName(), filter);
				}
			}
			Map<String, ReportInput> filterValues = inputs.stream().collect(Collectors.toMap(f -> f.getName(), f -> f));
			Criterion where = createCriterion(c.model, report.getCriteria(), filters, filterValues);
			if (where != null) {
				c.addCriterion(where);
			}
		}

		addTenantCondition(c, tenantPath, tenant);

		List<Function<RawResult, String>> readers = new ArrayList<>();
		for (ReportField s : summary.selections) {
			c.addSelection(createFieldColumn(c.model, s, readers));
		}

		for (ReportField g : summary.groupBys) {
			PropertyExpression cri = createFieldExpression(c.model, g.getField());
			c.addGrouopBy(cri);
		}

		for (ReportField o : summary.orderBys) {
			PropertyExpression cri = createFieldExpression(c.model, o.getField());
			c.addOrderBy(cri);
		}
		List<List<String>> rows = new ArrayList<>();
		if (!summary.selections.isEmpty()) {
			List<RawResult> listRaw = c.listRaw();
			if (listRaw.size() >= 10000) {
				ReportDataSection sec = new ReportDataSection();
				sec.addToColumns("There are more rows found, Please add more filters/criteria", -1);
				data.addToSections(sec, -1);
				return data;
			}
			for (RawResult row : listRaw) {
				List<String> values = new ArrayList<>();
				readers.forEach(r -> values.add(r.apply(row)));
				rows.add(values);
			}
		}

		ReportDataSection primary = new ReportDataSection();
		if (summary.primary.header != null) {
			primary.setHeader(getFieldName(c.model, summary.primary.header));
		}
		primary.setColumns(getFieldNames(c.model, summary.primary.columns));
		data.addToSections(primary, -1);
		if (summary.legend == -1) {
			ReportDataSection ds = new ReportDataSection();
			ds.setColumns(getFieldNames(c.model, summary.columns));
			data.addToSections(ds, -1);
			for (List<String> row : rows) {
				ReportDataRow dr = new ReportDataRow();
				data.addToRows(dr, -1);
				dr.setRow(row);
			}
		} else {
			prepareMatrixTable(summary, data, c.model, rows);
		}
		List<ReportDataSection> sections = new ArrayList<>(data.getSections());
		for (ReportDataSection sec : sections) {
			if (sec.getColumns().isEmpty()) {
				data.removeFromSections(sec);
			}
		}
		if (data.getSections().isEmpty()) {
			ReportDataSection sec = new ReportDataSection();
			sec.addToColumns("Select or drag fields to populate this visual", -1);
			data.addToSections(sec, -1);
		}
		return data;
	}

	private void addTenantCondition(Criteria c, String tenantPath, DatabaseObject tenant) {
		if (tenantPath != null) {
			ReportSingleRule rule = new ReportSingleRule();
			rule.setField(tenantPath);
			rule.setValue1(tenant == null ? "NO-0" : tenant.getIdent());
			rule.setFieldFrom(ReportFieldFromType.Value);
			Criterion tr = createCriterion(c.model, rule, null, null);
			c.addCriterion(tr);
		}
	}

	private String getFieldName(DModel<?> model, ReportField field) {
		if (field.getAggregate() == ReportAggregateType.None) {
			return field.getName();
		}
		return field.getAggregate().name() + " of " + field.getName();
	}

	private List<String> getFieldNames(DModel<?> model, List<ReportField> columns) {
		return ListExt.map(columns, s -> getFieldName(model, s));
	}

	private void prepareMatrixTable(ReportSummary summary, ReportData data, DModel<?> model, List<List<String>> rows) {
		List<String> legends = new ArrayList<>();
		Map<String, Section> sections = new HashMap<>();
		int legendColumns = summary.columns.size();
		int primaryColumns = summary.primary.columns.size();
		for (List<String> row : rows) {
			String legend = row.get(summary.legend);
			if (!legends.contains(legend)) {
				ReportDataSection ds = new ReportDataSection();
				ds.setHeader(legend);
				ds.setColumns(getFieldNames(model, summary.columns));
				int columnIndex = primaryColumns + legends.size() * legendColumns;
				data.addToSections(ds, -1);
				legends.add(legend);
				Section sec = new Section(legend, primaryColumns, columnIndex, legendColumns);
				sections.put(legend, sec);
			}
		}
		int columnsCount = primaryColumns + legends.size() * legendColumns;

		Segment all = new Segment(data, sections, columnsCount, primaryColumns, 0);
		for (List<String> row : rows) {
			all.apply(row);
		}
	}

	private Criterion createCriterion(DModel<?> model, ReportRule rule, Map<String, ReportFilter> filters,
			Map<String, ReportInput> values) {
		if (rule instanceof ReportRuleSet) {
			return createCriterion(model, (ReportRuleSet) rule, filters, values);
		} else if (rule instanceof ReportSingleRule) {
			return createCriterion(model, (ReportSingleRule) rule, filters, values);
		} else {
			throw new RuntimeException("Invalid Rule");
		}
	}

	private Criterion createCriterion(DModel<?> model, ReportRuleSet criteria, Map<String, ReportFilter> filters,
			Map<String, ReportInput> values) {
		List<Criterion> criterions = new ArrayList<>();
		for (ReportRule rule : criteria.getRules()) {
			Criterion criterion = createCriterion(model, rule, filters, values);
			if (criterion != null) {
				criterions.add(criterion);
			}
		}
		if (criterions.isEmpty()) {
			return null;
		}
		return new MultiExpression(criterions, criteria.isAll());
	}

	private Criterion createCriterion(DModel<?> model, ReportSingleRule rule, Map<String, ReportFilter> filters,
			Map<String, ReportInput> values) {
		PropertyExpression field = createFieldExpression(model, rule.getField());
		switch (rule.getOperator()) {
		case Equal:
			if (rule.getFieldFrom() == ReportFieldFromType.Value) {
				return Restrictions.eq(field, parseValue(field, rule.getValue1()));
			} else if (rule.getFieldFrom() == ReportFieldFromType.Filter) {
				String name = "";
				if (rule.getFilter() instanceof ReportPropertyFilter) {
					ReportPropertyFilter pf = (ReportPropertyFilter) rule.getFilter();
					name = pf.getName();
				} else {
					ReportNamedConditionFilter pf = (ReportNamedConditionFilter) rule.getFilter();
					name = pf.getName();
				}
				ReportInput input = values.get(name);
				if (input == null) {
					return null;
				}
				return Restrictions.eq(field, parseValue(field, input.getValue()));
			} else {
				return Restrictions.eq(field, parseValue(field, rule.getFieldValue1()));
			}
		case Between:
			return Restrictions.between(field, parseValue(field, rule.getValue1()),
					parseValue(field, rule.getValue2()));
		case NotEqual:
			if (rule.getFieldFrom() == ReportFieldFromType.Value) {
				return Restrictions.notEq(field, parseValue(field, rule.getValue1()));
			} else if (rule.getFieldFrom() == ReportFieldFromType.Filter) {
				String name = "";
				if (rule.getFilter() instanceof ReportPropertyFilter) {
					ReportPropertyFilter pf = (ReportPropertyFilter) rule.getFilter();
					name = pf.getName();
				} else {
					ReportNamedConditionFilter pf = (ReportNamedConditionFilter) rule.getFilter();
					name = pf.getName();
				}
				ReportInput input = values.get(name);
				return Restrictions.notEq(field, parseValue(field, input.getValue()));
			} else {
				return Restrictions.notEq(field, parseValue(field, rule.getFieldValue1()));
			}
		case GreaterThan:
			if (rule.getFieldFrom() == ReportFieldFromType.Value) {
				return Restrictions.gt(field, parseValue(field, rule.getValue1()));
			} else if (rule.getFieldFrom() == ReportFieldFromType.Filter) {
				String name = "";
				if (rule.getFilter() instanceof ReportPropertyFilter) {
					ReportPropertyFilter pf = (ReportPropertyFilter) rule.getFilter();
					name = pf.getName();
				} else {
					ReportNamedConditionFilter pf = (ReportNamedConditionFilter) rule.getFilter();
					name = pf.getName();
				}
				ReportInput input = values.get(name);
				return Restrictions.gt(field, parseValue(field, input.getValue()));
			} else {
				return Restrictions.gt(field, parseValue(field, rule.getFieldValue1()));
			}
		case GreaterThanorEqual:
			if (rule.getFieldFrom() == ReportFieldFromType.Value) {
				return Restrictions.ge(field, parseValue(field, rule.getValue1()));
			} else if (rule.getFieldFrom() == ReportFieldFromType.Filter) {
				String name = "";
				if (rule.getFilter() instanceof ReportPropertyFilter) {
					ReportPropertyFilter pf = (ReportPropertyFilter) rule.getFilter();
					name = pf.getName();
				} else {
					ReportNamedConditionFilter pf = (ReportNamedConditionFilter) rule.getFilter();
					name = pf.getName();
				}
				ReportInput input = values.get(name);
				return Restrictions.ge(field, parseValue(field, input.getValue()));
			} else {
				return Restrictions.ge(field, parseValue(field, rule.getFieldValue1()));
			}
		case LessThan:
			if (rule.getFieldFrom() == ReportFieldFromType.Value) {
				return Restrictions.lt(field, parseValue(field, rule.getValue1()));
			} else if (rule.getFieldFrom() == ReportFieldFromType.Filter) {
				String name = "";
				if (rule.getFilter() instanceof ReportPropertyFilter) {
					ReportPropertyFilter pf = (ReportPropertyFilter) rule.getFilter();
					name = pf.getName();
				} else {
					ReportNamedConditionFilter pf = (ReportNamedConditionFilter) rule.getFilter();
					name = pf.getName();
				}
				ReportInput input = values.get(name);
				return Restrictions.lt(field, parseValue(field, input.getValue()));
			} else {
				return Restrictions.lt(field, parseValue(field, rule.getFieldValue1()));
			}
		case LessThanorEqual:
			if (rule.getFieldFrom() == ReportFieldFromType.Value) {
				return Restrictions.le(field, parseValue(field, rule.getValue1()));
			} else if (rule.getFieldFrom() == ReportFieldFromType.Filter) {
				String name = "";
				if (rule.getFilter() instanceof ReportPropertyFilter) {
					ReportPropertyFilter pf = (ReportPropertyFilter) rule.getFilter();
					name = pf.getName();
				} else {
					ReportNamedConditionFilter pf = (ReportNamedConditionFilter) rule.getFilter();
					name = pf.getName();
				}
				ReportInput input = values.get(name);
				return Restrictions.le(field, parseValue(field, input.getValue()));
			} else {
				return Restrictions.le(field, parseValue(field, rule.getFieldValue1()));
			}
		case IsIn:
			// if(rule.getFieldFrom() == ReportFieldFromType.Value) {
			// return Restrictions.in(field, rule.getValues());
			// }else if(rule.getFieldFrom() == ReportFieldFromType.Filter) {
			// ReportInput input = values.get(rule.getFilter().getName());
			// return Restrictions.in(field, filterValue.getValues());
			// }else {
			// return Restrictions.in(field, rule.getFieldValues());
			// }
		case IsNotIn:
			// if(rule.getFieldFrom() == ReportFieldFromType.Value) {
			// return Restrictions.notIn(field, rule.getValues());
			// }else if(rule.getFieldFrom() == ReportFieldFromType.Filter) {
			// ReportInput input = values.get(rule.getFilter().getName());
			// return Restrictions.notIn(field, filterValue.getValues());
			// }else {
			// return Restrictions.notIn(field, rule.getFieldValues());
			// }
		case Contains:
			if (rule.getFieldFrom() == ReportFieldFromType.Value) {
				return Restrictions.contains(field, rule.getValue1());
			} else if (rule.getFieldFrom() == ReportFieldFromType.Filter) {
				String name = "";
				if (rule.getFilter() instanceof ReportPropertyFilter) {
					ReportPropertyFilter pf = (ReportPropertyFilter) rule.getFilter();
					name = pf.getName();
				} else {
					ReportNamedConditionFilter pf = (ReportNamedConditionFilter) rule.getFilter();
					name = pf.getName();
				}
				ReportInput input = values.get(name);
				return Restrictions.contains(field, input.getValue());
			} else {
				return Restrictions.contains(field, rule.getFieldValue1());
			}
		case NotContains:
			if (rule.getFieldFrom() == ReportFieldFromType.Value) {
				return Restrictions.notContains(field, rule.getValue1());
			} else if (rule.getFieldFrom() == ReportFieldFromType.Filter) {
				String name = "";
				if (rule.getFilter() instanceof ReportPropertyFilter) {
					ReportPropertyFilter pf = (ReportPropertyFilter) rule.getFilter();
					name = pf.getName();
				} else {
					ReportNamedConditionFilter pf = (ReportNamedConditionFilter) rule.getFilter();
					name = pf.getName();
				}
				ReportInput input = values.get(name);
				return Restrictions.notContains(field, input.getValue());
			} else {
				return Restrictions.notContains(field, rule.getFieldValue1());
			}
		case StartsWith:
			if (rule.getFieldFrom() == ReportFieldFromType.Value) {
				return Restrictions.startsWith(field, rule.getValue1());
			} else if (rule.getFieldFrom() == ReportFieldFromType.Filter) {
				String name = "";
				if (rule.getFilter() instanceof ReportPropertyFilter) {
					ReportPropertyFilter pf = (ReportPropertyFilter) rule.getFilter();
					name = pf.getName();
				} else {
					ReportNamedConditionFilter pf = (ReportNamedConditionFilter) rule.getFilter();
					name = pf.getName();
				}
				ReportInput input = values.get(name);
				return Restrictions.startsWith(field, input.getValue());
			} else {
				return Restrictions.startsWith(field, rule.getFieldValue1());
			}
		case EndsWith:
			if (rule.getFieldFrom() == ReportFieldFromType.Value) {
				return Restrictions.endsWith(field, rule.getValue1());
			} else if (rule.getFieldFrom() == ReportFieldFromType.Filter) {
				String name = "";
				if (rule.getFilter() instanceof ReportPropertyFilter) {
					ReportPropertyFilter pf = (ReportPropertyFilter) rule.getFilter();
					name = pf.getName();
				} else {
					ReportNamedConditionFilter pf = (ReportNamedConditionFilter) rule.getFilter();
					name = pf.getName();
				}
				ReportInput input = values.get(name);
				return Restrictions.endsWith(field, input.getValue());
			} else {
				return Restrictions.endsWith(field, rule.getFieldValue1());
			}
		case IsSet:
			// if(rule.getFieldFrom() == ReportFieldFromType.Value) {
			// return Restrictions.set(field, rule.getValue1());
			// }else if(rule.getFieldFrom() == ReportFieldFromType.Filter) {
			// ReportInput input = values.get(rule.getFilter().getName());
			// return Restrictions.set(field, filterValue.getValue());
			// }else {
			// return Restrictions.set(field, rule.getFieldValue1());
			// }
		case Match:
			// return Restrictions.match(field);
		case Is:
			if (rule.getFieldFrom() == ReportFieldFromType.Value) {
				return Restrictions.is(field, parseValue(field, rule.getValue1()));
			} else if (rule.getFieldFrom() == ReportFieldFromType.Filter) {
				String name = "";
				if (rule.getFilter() instanceof ReportPropertyFilter) {
					ReportPropertyFilter pf = (ReportPropertyFilter) rule.getFilter();
					name = pf.getName();
				} else {
					ReportNamedConditionFilter pf = (ReportNamedConditionFilter) rule.getFilter();
					name = pf.getName();
				}
				ReportInput input = values.get(name);
				return Restrictions.is(field, parseValue(field, input.getValue()));
			} else {
				return Restrictions.is(field, parseValue(field, rule.getFieldValue1()));
			}
		case IsNot:
			if (rule.getFieldFrom() == ReportFieldFromType.Value) {
				return Restrictions.isNot(field, parseValue(field, rule.getValue1()));
			} else if (rule.getFieldFrom() == ReportFieldFromType.Filter) {
				String name = "";
				if (rule.getFilter() instanceof ReportPropertyFilter) {
					ReportPropertyFilter pf = (ReportPropertyFilter) rule.getFilter();
					name = pf.getName();
				} else {
					ReportNamedConditionFilter pf = (ReportNamedConditionFilter) rule.getFilter();
					name = pf.getName();
				}
				ReportInput input = values.get(name);
				return Restrictions.isNot(field, parseValue(field, input.getValue()));
			} else {
				return Restrictions.isNot(field, parseValue(field, rule.getFieldValue1()));
			}
		default:
			throw new RuntimeException("Invalid Rule Operator");

		}
	}

	private Object parseValue(PropertyExpression field, String val) {
		FieldType ft = field.getType();
		switch (ft) {
		case InverseCollection:
		case ReferenceCollection:
		case Reference:
			String[] split = val.split("-");
			return Long.valueOf(split[1]);
		case Primitive:
		case PrimitiveCollection:
			FieldPrimitiveType pt = field.getPrimitiveType();
			switch (pt) {
			case Boolean:
				return Boolean.valueOf(val);
			case Date:
				return val;
			case Time:
				return val;
			case DateTime:
				return val;
			case Double:
				return Double.valueOf(val);
			case Integer:
				return Long.valueOf(val);
			default:
				return val;
			}
		}
		return val;
	}

	private PropertyExpression createFieldExpression(DModel<?> model, String path) {
		String[] fields = path.split("\\.");
		PropertyExpression prop = null;
		for (String f : fields) {
			if (prop == null) {
				prop = prop(model, f);
			} else {
				prop = prop.prop(f);
			}
		}
		return prop;
	}

	public PropertyExpression prop(DModel<?> model, String field) {
		DField<?, ?> df = model.getField(field);
		if (df == null) {
			ICustomFieldProcessor<?> processor = CustomFieldService.get().getProcessor(model.getType());
			if (processor != null) {
				df = processor.getDField(field);
			}
		}
		return new PropertyExpression(null, df);
	}

	private Criterion createFieldColumn(DModel<?> model, ReportField column,
			List<Function<RawResult, String>> readers) {
		PropertyExpression field = createFieldExpression(model, column.getField());
		if (column.getAggregate() != ReportAggregateType.None) {
			switch (column.getAggregate()) {
			case Array:
				break;
			case Count:
				readers.add(a -> String.valueOf(a.readInteger()));
				break;
			case Average:
			case Max:
			case Min:
			case Sum:
			case Percent:
				readers.add(a -> doubleToStringNoE(a.readDouble()));
				break;
			default:
				throw new RuntimeException("Unsupported Aggregator");
			}
			return new AggregateExpression(column.getAggregate(), field);
		}
		FieldType ft = field.getType();
		switch (ft) {
		case InverseCollection:
		case ReferenceCollection:
		case Reference:
			readers.add(a -> String.valueOf(a.reaObject()));
			break;
		case Primitive:
		case PrimitiveCollection:
			FieldPrimitiveType pt = field.getPrimitiveType();
			switch (pt) {
			case Boolean:
				readers.add(a -> String.valueOf(a.readBoolean()));
				break;
			case DFile:
				readers.add(a -> a.readString());
				break;
			case Date:
				DateTimeFormatter f1 = d3eDateFormatter;
				readers.add(a -> {
					LocalDate d = a.readDate();
					return d == null ? "" : f1.format(d);
				});
				break;
			case Time:
				DateTimeFormatter f2 = d3eTimeFormatter;
				readers.add(a -> {
					LocalTime d = a.readTime();
					return d == null ? "" : f2.format(d);
				});
				break;
			case DateTime:
				DateTimeFormatter f3 = d3eDateTimeFormatter;
				readers.add(a -> {
					LocalDateTime d = a.readDateTime();
					return d == null ? "" : f3.format(d);
				});
				break;
			case Double:
				readers.add(a -> doubleToStringNoE(a.readDouble()));
				break;
			case Duration:
				readers.add(a -> String.valueOf(a.readDuration()));
				break;
			case Enum:
				readers.add(a -> a.readString());
				break;
			case Integer:
				readers.add(a -> String.valueOf(a.readInteger()));
				break;
			case String:
				readers.add(a -> a.readString());
				break;
			default:
				readers.add(a -> String.valueOf(a.readAny()));
				break;
			}
			break;
		}
		return field;
	}

	public static String doubleToStringNoE(double val) {
		String str = String.format("%.6f", val).replace(",", "");
		if (str.contains(".")) {
			str = str.replaceAll("0*$", ""); // Remove trailing zeros
			str = str.replaceAll("\\.$", ""); // Remove trailing dot if no digits after it
		}
		return str;
	}

	private void applyReportConfiguration(ReportBaseConfig config, ReportSummary summary) {
		if (config instanceof ReportBarChartConfig) {
			applyBarChartConfig((ReportBarChartConfig) config, summary);
		} else if (config instanceof ReportLineAndAreaChartConfig) {
			applyLineAndAreaChartConfig((ReportLineAndAreaChartConfig) config, summary);
		} else if (config instanceof ReportLineAndColumnChartConfig) {
			applyLineAndColumnChartConfig((ReportLineAndColumnChartConfig) config, summary);
		} else if (config instanceof ReportWaterfallChartConfig) {
			applyWaterfallChartConfig((ReportWaterfallChartConfig) config, summary);
		} else if (config instanceof ReportFunnelChartConfig) {
			applyFunnelChartConfig((ReportFunnelChartConfig) config, summary);
		} else if (config instanceof ReportScatterChartConfig) {
			applyScatterChartConfig((ReportScatterChartConfig) config, summary);
		} else if (config instanceof ReportPieChartConfig) {
			applyPieChartConfig((ReportPieChartConfig) config, summary);
		} else if (config instanceof ReportMapConfig) {
			applyMapConfig((ReportMapConfig) config, summary);
		} else if (config instanceof ReportGuageConfig) {
			applyGuageConfig((ReportGuageConfig) config, summary);
		} else if (config instanceof ReportCardConfig) {
			applyCardConfig((ReportCardConfig) config, summary);
		} else if (config instanceof ReportMultiRowCardConfig) {
			applyMultiRowCardConfig((ReportMultiRowCardConfig) config, summary);
		} else if (config instanceof ReportKPIConfig) {
			applyKPIConfig((ReportKPIConfig) config, summary);
		} else if (config instanceof ReportSlicerConfig) {
			applySlicerConfig((ReportSlicerConfig) config, summary);
		} else if (config instanceof ReportTableConfig) {
			applyTableConfig((ReportTableConfig) config, summary);
		} else if (config instanceof ReportMatrixConfig) {
			applyMatrixConfig((ReportMatrixConfig) config, summary);
		} else if (config instanceof ReportKeyInfluencerConfig) {
			applyKeyInfluencerConfig((ReportKeyInfluencerConfig) config, summary);
		} else {
			throw new RuntimeException("Invalid config type");
		}
	}

	private void applyBarChartConfig(ReportBarChartConfig config, ReportSummary summary) {
		List<ReportField> xAxes;
		List<ReportField> yAxes;
		switch (config.getType()) {
		case Stacked:
		case Clustered:
		case HundredPercentStacked:
			xAxes = config.getYAxes();
			yAxes = config.getXAxes();
			break;
		case StackedColumn:
		case ClusteredColumn:
		case HundredPercentStackedColumn:
		case HundredPercentStackedArea:
		case StackedArea:
		case RibbonChart:
			xAxes = config.getXAxes();
			yAxes = config.getYAxes();
			break;
		default:
			throw new RuntimeException("Invalid type");
		}
		if (xAxes.size() > 1) {
			xAxes = ListExt.asList(xAxes.get(0));
		}
		if (config.getLegend() != null && yAxes.size() > 1) {
			yAxes = ListExt.asList(yAxes.get(0));
		}
		List<ReportField> tooltips = config.getTooltips();
		summary.groupBys.addAll(xAxes);
		summary.selections.addAll(xAxes);
		summary.orderBys.addAll(xAxes);
		if (config.getLegend() != null) {
			summary.groupBys.add(config.getLegend());
			summary.selections.add(config.getLegend());
			summary.orderBys.add(config.getLegend());
			summary.primary.header = config.getLegend();
			summary.legend = xAxes.size();
		}
		summary.selections.addAll(config.getSmallMultiples());
		summary.orderBys.addAll(config.getSmallMultiples());

		summary.selections.addAll(yAxes);
		summary.selections.addAll(tooltips);

		summary.primary.columns.addAll(xAxes);
		summary.columns.addAll(yAxes);
		summary.columns.addAll(tooltips);
		summary.smallMultiples.addAll(config.getSmallMultiples());
	}

	private List<ReportField> addGregates(List<ReportField> yAxes) {
		return ListExt.map(yAxes, f -> {
			if (f.getAggregate() == ReportAggregateType.None) {
				ReportField rf = new ReportField();
				rf.setField(f.getField());
				rf.setAggregate(ReportAggregateType.Sum);
				return rf;
			}
			return f;
		});
	}

	private void applyLineAndAreaChartConfig(ReportLineAndAreaChartConfig config, ReportSummary summary) {
		summary.groupBys.add(config.getXAxes().get(0));
		summary.selections.addAll(config.getTooltips());
		summary.selections.addAll(config.getSmallMultiples());
		summary.selections.addAll(config.getYAxes());
		summary.selections.addAll(config.getSecondaryYAxes());
	}

	private void applyLineAndColumnChartConfig(ReportLineAndColumnChartConfig config, ReportSummary summary) {
		summary.groupBys.add(config.getXAxes().get(0));
		summary.selections.addAll(config.getTooltips());
		summary.selections.addAll(config.getSmallMultiples());
		summary.selections.addAll(config.getColumnLegend());
		summary.selections.addAll(config.getLineYAxes());
		summary.selections.addAll(config.getColumnYAxes());
	}

	private void applyWaterfallChartConfig(ReportWaterfallChartConfig config, ReportSummary summary) {
		summary.groupBys.add(config.getCategory().get(0));
		summary.groupBys.addAll(config.getBreakdownFields());
		summary.selections.addAll(config.getTooltips());
		summary.selections.addAll(config.getYAxes());
	}

	private void applyFunnelChartConfig(ReportFunnelChartConfig config, ReportSummary summary) {
		summary.groupBys.addAll(config.getCategoryFields());
		summary.selections.addAll(config.getTooltips());
		summary.selections.addAll(config.getValues());
	}

	private void applyScatterChartConfig(ReportScatterChartConfig config, ReportSummary summary) {
		summary.groupBys.addAll(config.getXAxes());
		summary.groupBys.addAll(config.getYAxes());
		summary.groupBys.addAll(config.getSize());
		summary.groupBys.addAll(config.getLegends());
		summary.groupBys.addAll(config.getPlayAxis());
		summary.selections.addAll(config.getTooltips());
		summary.selections.addAll(config.getValues());
	}

	private void applyPieChartConfig(ReportPieChartConfig config, ReportSummary summary) {
		summary.groupBys.addAll(config.getLegend());
		summary.selections.addAll(config.getLegend());
		summary.selections.addAll(config.getValues());
		summary.selections.addAll(config.getTooltips());
		summary.selections.addAll(config.getDetails());
		summary.primary.columns.addAll(config.getLegend());
		summary.primary.columns.addAll(config.getValues());
		summary.primary.columns.addAll(config.getTooltips());
		summary.primary.columns.addAll(config.getDetails());
	}

	private void applyMapConfig(ReportMapConfig config, ReportSummary summary) {
		summary.groupBys.addAll(config.getLocation());
		summary.groupBys.addAll(config.getLegend());
		summary.selections.add(config.getLatitude());
		summary.selections.add(config.getLongitude());
		summary.selections.addAll(config.getSize());
		summary.selections.addAll(config.getTooltips());
	}

	private void applyGuageConfig(ReportGuageConfig config, ReportSummary summary) {
		summary.selections.addAll(config.getValue());
		summary.selections.addAll(config.getMin());
		summary.selections.addAll(config.getMax());
		summary.selections.addAll(config.getTarget());
		summary.selections.addAll(config.getTooltips());
	}

	private void applyCardConfig(ReportCardConfig config, ReportSummary summary) {
		summary.selections.add(config.getValue());
	}

	private void applyMultiRowCardConfig(ReportMultiRowCardConfig config, ReportSummary summary) {
		summary.selections.addAll(config.getValues());
	}

	private void applyKPIConfig(ReportKPIConfig config, ReportSummary summary) {
		summary.selections.add(config.getValue());
		summary.selections.addAll(config.getTarget());
		summary.selections.add(config.getTrend());
	}

	private void applySlicerConfig(ReportSlicerConfig config, ReportSummary summary) {
		summary.selections.addAll(config.getFields());
	}

	private void applyTableConfig(ReportTableConfig config, ReportSummary summary) {
		summary.primary.columns.addAll(config.getColumns());
		summary.selections.addAll(config.getColumns());
	}

	private void applyMatrixConfig(ReportMatrixConfig config, ReportSummary summary) {
		summary.selections.addAll(config.getColumns());
		summary.selections.addAll(config.getRows());
		summary.selections.addAll(config.getValues());
	}

	private void applyKeyInfluencerConfig(ReportKeyInfluencerConfig config, ReportSummary summary) {
		summary.groupBys.add(config.getAnalyze());
		summary.groupBys.addAll(config.getEexplainBy());
		summary.groupBys.addAll(config.getExpandBy());
	}

	public List<ReportModel> prepareSchema(List<List<String>> models) {
		List<ReportModel> result = new ArrayList<ReportModel>();
		models.forEach(m -> {
			String name = m.remove(0);
			DModel<?> type = this.schema.getType(name);
			if (type != null) {
				ReportModel model = new ReportModel();
				model.setName(name);
				m.forEach(p -> {
					DField<?, ?> field = type.getField(p);
					if (field != null) {
						ReportProperty prop = new ReportProperty();
						prop.setName(toReadableString(field.getName()));
						prop.setProperty(field.getName());
						prop.setType(field.getTypeName());
						prop.setCollection(field.isCollection());
						prop.setIsEnum(field.isEnum());
						prop.setIsReference(field.isReference());
						model.addToProperties(prop, -1);
					}
				});
				result.add(model);
			}
		});
		return result;
	}

	private static String toReadableString(String camelCaseString) {
		StringBuilder sb = new StringBuilder();
		boolean capitalizeNext = true;
		for (int i = 0; i < camelCaseString.length(); i++) {
			char c = camelCaseString.charAt(i);
			if (Character.isUpperCase(c)) {
				if (capitalizeNext) {
					sb.append(c);
					capitalizeNext = false;
				} else {
					sb.append(' ');
					sb.append(c);
				}
			} else {
				if (capitalizeNext) {
					sb.append(Character.toUpperCase(c));
					capitalizeNext = false;
				} else {
					sb.append(c);
				}
			}
		}
		return sb.toString();
	}

	private class ReportHeader {
		private ReportField header;
		private List<ReportField> columns = new ArrayList<>();
	}

	private class ReportSummary {
		private ReportHeader primary = new ReportHeader();
		private List<ReportField> columns = new ArrayList<>();
		private List<ReportField> smallMultiples = new ArrayList<>();
		private int legend = -1;
		private List<ReportField> selections = new ArrayList<>();
		private List<ReportField> groupBys = new ArrayList<>();
		private List<ReportField> orderBys = new ArrayList<>();
	}

	private class Segment {
		private Map<String, Segment> segments = new HashMap<>();
		private ReportData data;
		private Map<String, Section> sections;
		private int index;
		private int segmentsCount;
		private int totalColumns;
		private ReportDataRow row;

		public Segment(ReportData data, Map<String, Section> sections, int totalColumns, int segments, int index) {
			this.data = data;
			this.totalColumns = totalColumns;
			this.sections = sections;
			this.segmentsCount = segments;
			this.index = index;
		}

		public void apply(List<String> row) {
			String val = row.get(index);
			if (this.segmentsCount == this.index) {
				Section sec = sections.get(val);
				if (this.row == null) {
					ReportDataRow dr = new ReportDataRow();
					this.data.addToRows(dr, -1);
					for (int i = 0; i < segmentsCount; i++) {
						dr.addToRow(row.get(i), -1);
					}
					for (int i = index; i < totalColumns; i++) {
						dr.addToRow("", -1);
					}
					this.row = dr;
				}
				sec.apply(row, this.row.getRow());
			} else {
				Segment sec = segments.get(val);
				if (sec == null) {
					sec = new Segment(data, sections, totalColumns, segmentsCount, index + 1);
					segments.put(val, sec);
				}
				sec.apply(row);
			}
		}
	}

	private class Section {
		private int index;
		private int size;
		private int valueIndex;
		private String name;

		public Section(String name, int segments, int index, int size) {
			this.name = name;
			this.valueIndex = segments + 1;
			this.index = index;
			this.size = size;
		}

		public void apply(List<String> row, List<String> data) {
			for (int i = 0; i < size; i++) {
				data.set(index + i, row.get(valueIndex + i));
			}
		}

		@Override
		public String toString() {
			return name + " @" + valueIndex + ":" + index;
		}
	}

	private static ReportField field(String name, boolean agg) {
		ReportField field = new ReportField();
		field.setField(name);
		if (agg) {
			field.setAggregate(ReportAggregateType.Sum);
		}
		return field;
	}

	public List<ReportFilterValue> getFilterValues(String model, String property, String tenantPath,
			DatabaseObject tenant) {
		Criteria c = Criteria.on(model);
		ReportField field = field(property, false);
		Function<RawResult, DatabaseObject> reader = (a -> a.reaObject());
		c.addSelection(createFieldExpression(c.model, field.getField()));
		addTenantCondition(c, tenantPath, tenant);
		List<RawResult> listRaw = c.listRaw();
		List<ReportFilterValue> values = new ArrayList<>();
		for (RawResult row : listRaw) {
			ReportFilterValue value = new ReportFilterValue();
			DatabaseObject ob = reader.apply(row);
			value.setName(ob.toString());
			value.setValue(ob.getIdent());
			values.add(value);
		}
		return values;
	}

}
