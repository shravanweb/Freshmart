package d3e.core;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;

public class Completer<T> {

	private SingleEmitter<T> emitter;
	private boolean completed;
	private boolean success;
	private T value;
	private Throwable error;

	public Completer() {
	}

	public void sync() {
		// TODO
		// return null;
	}

	public Single<T> getFuture() {
		Single<T> create = Single.create(e -> {
			this.emitter = e;
			checkComplete();
		});
		return create;
	}

	public void complete(T value) {
		this.value = value;
		this.completed = true;
		this.success = true;
		checkComplete();
	}

	private void checkComplete() {
		if(this.emitter == null) {
			return;
		}
		if (completed) {
			if (success) {
				this.emitter.onSuccess(value);
			} else {
				this.emitter.onError(error);
			}
		}
	}

	public void completeError(Throwable error) {
		this.error = error;
		this.completed = true;
		this.success = false;
		checkComplete();
	}

	public boolean getIsCompleted() {
		return completed;
	}

}
