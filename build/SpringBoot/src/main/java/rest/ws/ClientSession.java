package rest.ws;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;

import org.slf4j.MDC;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import d3e.core.Log;
import d3e.core.MapExt;

public class ClientSession extends Thread implements RocketSender {

	private WebSocketSession session;
	ByteArrayOutputStream stream = new ByteArrayOutputStream();
	Template template;
	long userId;
	String userType;
	String token;
	String deviceToken;
	private ReentrantLock lock = new ReentrantLock();
	Map<String, AbstractClientProxy> proxies = MapExt.Map();
	List<BinaryMessage> queue = new ArrayList<>();
	LinkedBlockingQueue<RocketMessage> inputMessages = new LinkedBlockingQueue<>();
	private long timeout;
	private BiConsumer<ClientSession, RocketMessage> executer;

	public ClientSession(WebSocketSession session, BiConsumer<ClientSession, RocketMessage> executer) {
		super("Client-" + session.getId());
		this.session = session;
		this.executer = executer;
		start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				RocketMessage take = inputMessages.take();
				MDC.put("userId", String.valueOf(userId));
				MDC.put("http", getSessionId());
				MDC.put("reqid", String.valueOf(take.getReqId()));
				long t = take.time;
				take.time = System.currentTimeMillis();
				Log.info("Message was queued (" + (take.time - t) + "ms). Started executing");
				this.executer.accept(this, take);
				t = take.time;
				take.time = System.currentTimeMillis();
				Log.info("Execution done (" + (take.time - t) + "ms). Queue size: " + inputMessages.size());
			} catch (Exception e) {
				Log.printStackTrace(e);
			}
		}
	}

	@Override
	public Template getTemplate() {
		return template;
	}

	public String getSessionId() {
		return session.getId();
	}

	public void lock() {
		lock.lock();
	}

	public boolean isLocked() {
		return lock.isLocked() && lock.isHeldByCurrentThread();
	}

	public void unlock() {
		lock.unlock();
	}

	@Override
	public String getToken() {
		return token;
	}

	@Override
	public void sendMessage(BinaryMessage msg, int msgId, boolean system) throws IOException {
		if (msgId == 0) {
			lock.lock();
		}
		try {
			if (session == null) {
				queue.add(msg);
			} else {
				session.sendMessage(msg);
			}
		} finally {
			if (msg.isLast()) {
				lock.unlock();
			}
		}
	}

	public WebSocketSession getSession() {
		return session;
	}

	public void setSession(WebSocketSession session) throws IOException {
		this.session = session;
		if (session != null) {
			for (BinaryMessage msg : queue) {
				session.sendMessage(msg);
			}
			queue.clear();
		}
	}

	public void setTimeOut(long timeout) {
		this.timeout = timeout;
	}

	public long getTimeout() {
		return timeout;
	}

	public void logout() {
		this.userId = 0;
		this.userType = null;
		this.token = null;
	}

	public void addMessageToQueue() {
		RocketMessage msg = new RocketMessage(this.stream.toByteArray(), this, false);
		msg.setReqId(msg.readInt());
		msg.time = System.currentTimeMillis();
		MDC.put("reqid", String.valueOf(msg.getReqId()));
		Log.info("Client Message added. size:" + this.inputMessages.size());
		this.inputMessages.add(msg);
		this.stream = new ByteArrayOutputStream();
	}

	public RocketMessage createMessage() {
		return new RocketMessage(new byte[] {}, this, false);
	}

	public String getClientAddress() {
		String clientIpAddress = session.getRemoteAddress() != null 
		        ? session.getRemoteAddress().getAddress().getHostAddress() 
		        : "Unknown";
		return clientIpAddress;
	}
}
