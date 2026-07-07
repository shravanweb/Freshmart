package d3e.core;

import java.time.Duration;
import java.util.TimerTask;
import java.util.function.Consumer;

public class Timer extends TimerTask {

	private static java.util.Timer timer = new java.util.Timer("Timer");

	private int _tick;
	private boolean _active;
	private Runnable callback;

	public Timer(Duration duration, Runnable callback) {
		this.callback = callback;
		this._active = true;
		timer.schedule(this, duration.toMillis());
	}

	public static Timer periodic(Duration duration, Consumer<Timer> callback) {
		// TODO
		return null;
	}

	@Override
	public void run() {
		this._tick++;
		this.callback.run();
	}

	@Override
	public boolean cancel() {
		_active = false;
		return super.cancel();
	}

	public static void run(Runnable callback) {
		new Timer(Duration.ZERO, callback);
	}

	public int getTick() {
		return _tick;
	}

	public boolean isActive() {
		return _active;
	}
}
