package rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import d3e.core.DFile;
import gqltosql.schema.IModelSchema;
import graphql.language.BooleanValue;
import graphql.language.EnumValue;
import graphql.language.FloatValue;
import graphql.language.IntValue;
import graphql.language.NullValue;
import graphql.language.ObjectField;
import graphql.language.ObjectValue;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.language.VariableReference;
import store.EntityHelperService;

public class ObjectValueInputContext extends ArgumentInputContext {

	private Map<String, Object> value;

	public ObjectValueInputContext(ObjectValue value, EntityHelperService helperService,
			Map<Long, Object> inputObjectCache, Map<String, DFile> files, JSONObject variables, IModelSchema schema) {
		super(null, helperService, inputObjectCache, files, variables, schema);
		List<ObjectField> fields = value.getObjectFields();
		Map<String, Object> obj = new HashMap<>();
		fields.forEach(o -> obj.put(o.getName(), o.getValue()));
		this.value = obj;
	}

	@Override
	Object readAny(String field) {
		Object v = value.get(field);
		if (v instanceof VariableReference) {
			try {
				return variables.get(((VariableReference) v).getName());
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		}
		return v;
	}

	@Override
	public boolean has(String field) {
		return value.containsKey(field);
	}

	@Override
	protected <T> T readObject(String type, boolean readFully) {
		JSONObject json = toJSONObject(value, variables);
		JSONInputContext ctx = new JSONInputContext(json, helperService, inputObjectCache, files, schema);
		return ctx.readObject(type, readFully);
	}

	static JSONObject toJSONObject(Map<String, Object> fields, JSONObject variables) {
		JSONObject json = new JSONObject();
		if (fields == null) {
			return json;
		}
		for (Map.Entry<String, Object> entry : fields.entrySet()) {
			putJsonValue(json, entry.getKey(), entry.getValue(), variables);
		}
		return json;
	}

	private static void putJsonValue(JSONObject json, String key, Object val, JSONObject variables) {
		try {
			if (val == null || val instanceof NullValue) {
				json.put(key, JSONObject.NULL);
				return;
			}
			if (val instanceof VariableReference) {
				Object resolved = variables != null ? variables.get(((VariableReference) val).getName()) : null;
				if (resolved instanceof JSONObject) {
					json.put(key, resolved);
				} else if (resolved instanceof JSONArray) {
					json.put(key, resolved);
				} else {
					json.put(key, resolved);
				}
				return;
			}
			if (val instanceof StringValue) {
				json.put(key, ((StringValue) val).getValue());
			} else if (val instanceof EnumValue) {
				json.put(key, ((EnumValue) val).getName());
			} else if (val instanceof IntValue) {
				json.put(key, ((IntValue) val).getValue());
			} else if (val instanceof FloatValue) {
				json.put(key, ((FloatValue) val).getValue());
			} else if (val instanceof BooleanValue) {
				json.put(key, ((BooleanValue) val).isValue());
			} else if (val instanceof ObjectValue) {
				Map<String, Object> nested = new HashMap<>();
				((ObjectValue) val).getObjectFields().forEach(f -> nested.put(f.getName(), f.getValue()));
				json.put(key, toJSONObject(nested, variables));
			} else if (val instanceof JSONObject) {
				json.put(key, val);
			} else {
				json.put(key, val);
			}
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
