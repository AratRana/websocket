package org.example.websocket;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;

import org.example.model.Link;

@ApplicationScoped
public class SessionHandler {
	private int linkId = 0;
	private final Set<Session> sessions = new HashSet<>();
	private final Set<Link> links = new HashSet<>();

	public void addSession(Session session) {
		sessions.add(session);
		for (Link link : links) {
			JsonObject addMessage = createAddMessage(link);
			sendToSession(session, addMessage);
		}
	}

	private void sendToSession(Session session, JsonObject message) {
		try {
			session.getBasicRemote().sendText(message.toString());
		} catch (IOException ex) {
			sessions.remove(session);
			Logger.getLogger(SessionHandler.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public void removeSession(Session session) {
		sessions.remove(session);
	}

	private JsonObject createAddMessage(Link link) {
		JsonProvider provider = JsonProvider.provider();
		JsonObject addMessage = provider.createObjectBuilder().add("action", "add").add("id", link.getId())
				.add("link", link.getLink()).build();
		return addMessage;
	}

	public void addLink(Link link) {
		link.setId(linkId);
		linkId++;
		JsonObject addMessage = createAddMessage(link);
		sendToAllConnectedSessions(addMessage);

	}

	private void sendToAllConnectedSessions(JsonObject message) {
		for (Session session : sessions) {
			sendToSession(session, message);
		}

	}
}
