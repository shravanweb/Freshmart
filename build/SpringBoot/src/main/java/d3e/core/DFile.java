package d3e.core;

import classes.Env;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import store.Database;

@Entity
public class DFile {

	private String name;
	@Id
	private String id;
	private long size;
	private transient boolean proxy;
	private transient String repo;

	private String mimeType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getSize() {
		_checkProxy();
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getName() {
		_checkProxy();
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMimeType() {
		_checkProxy();
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public void _markProxy(String repo) {
		this.proxy = true;
		this.repo = repo;
	}

	public void _checkProxy() {
		if (this.proxy) {
			Database.get().unproxyDFile(this, repo);
			this.proxy = false;
		}
	}

	public String getDownloadUrl() {
		return Env.get().getBaseHttpUrl() + "/api/download/" + this.id;
	}
}
