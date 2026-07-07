package rest.ws;

import java.util.ArrayList;
import java.util.List;

import d3e.core.Log;
import d3e.core.MD5Util;
import gqltosql.schema.DField;
import gqltosql.schema.DModel;

public class TemplateType {
	private DModel<?> model;
	private String unknownName;
	private int clientParent;
	private DField<?, ?>[] fields;
	private int[] mapping;
	private String hash;
	private int parentServerCount;
	private int parentClientCount;
	private TemplateType parentType;
	public boolean valid = true;
	private boolean parentFieldCountDone = false; // Flag to avoid computing multiple times for a type

	public TemplateType(DModel<?> model, int length) {
		this.model = model;
		this.fields = new DField<?, ?>[length];
		if (model != null) {
			this.mapping = new int[model.getFields().length];
			for (int x = 0; x < this.mapping.length; x++) {
				this.mapping[x] = -1;
			}
			this.parentServerCount = model.getParentCount();
		}
	}

	public void setUnknownName(String unknownName) {
		this.unknownName = unknownName;
	}

	public String getUnknownName() {
		return unknownName;
	}

	public void setClientParent(int clientParent) {
		this.clientParent = clientParent;
	}

	public int getClientParent() {
		return clientParent;
	}

	public void setParentClientCount(int parentClientCount) {
		this.parentClientCount = parentClientCount;
	}

	public int getParentClientCount() {
		return parentClientCount;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getHash() {
		return hash;
	}

	public void addField(int idx, DField<?, ?> field) {
		fields[idx] = field;
		if (valid) {
			mapping[field.getIndex() - parentServerCount] = idx;
		}
	}

	public DModel<?> getModel() {
		return model;
	}

	public DField<?, ?>[] getFields() {
		return fields;
	}

	public DField<?, ?> getField(int idx) {
		if (idx < parentClientCount) {
			return parentType.getField(idx);
		}
		int index = idx - parentClientCount;
		if (index >= fields.length) {
			Log.info("Invalid field index in " + this.model.getType() + " index: " + idx);
		}
		return fields[index];
	}

	public int toClientIdx(int serverIdx) {
		if (serverIdx < parentServerCount) {
			return parentType.toClientIdx(serverIdx);
		}
		return mapping[serverIdx - parentServerCount] + parentClientCount;
	}

	public void setParent(TemplateType parentType) {
		this.parentType = parentType;
		this.valid = parentType.valid;
	}

	public void computeParentFieldsCount() {
		if (parentType == null) {
			return;
		}
		parentType.computeParentFieldsCount();
		if (parentFieldCountDone) {
			return;
		}
		this.parentClientCount = parentType.fields.length + parentType.parentClientCount;
		parentFieldCountDone = true;
	}

	public TemplateType getParentType() {
		return parentType;
	}

	@Override
	public String toString() {
		return model.toString();
	}

	public void computeHash() {
		if (hash != null) {
			return;
		}
		List<String> md5 = new ArrayList<>();
		TemplateType parent = getParentType();
		if (parent != null) {
			parent.computeHash();
			md5.add(parent.getHash());
		}
		if (valid) {
			md5.add(getModel().getType());
		} else {
			md5.add(unknownName);
		}
		for (DField<?, ?> f : fields) {
			md5.add(f.getName());
		}
		String hash = MD5Util.md5(md5);
		setHash(hash);
	}
}
