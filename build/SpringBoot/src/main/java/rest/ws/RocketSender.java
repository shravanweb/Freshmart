package rest.ws;

import java.io.IOException;

import org.springframework.web.socket.BinaryMessage;

public interface RocketSender {

	void sendMessage(BinaryMessage msg, int msgId, boolean system) throws IOException;

	String getToken();

	Template getTemplate();

	String getSessionId();
}
