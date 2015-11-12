package com.banhtieu.serverkit;

import java.lang.reflect.Method;
import java.util.Hashtable;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.banhtieu.serverkit.model.Identity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * A default lobby
 * @author banhtieu
 *
 */
public class Lobby extends TextWebSocketHandler {

	/**
	 * Include all identities
	 */
	private Hashtable<WebSocketSession, Identity> identities;
	
	/**
	 * Construct the Lobby
	 */
	public Lobby() {
		identities = new Hashtable<WebSocketSession, Identity>();
	}
	
	/**
	 * Handle the text message
	 */
	@Override
	protected synchronized void handleTextMessage(WebSocketSession session,
			TextMessage message) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(message.getPayload());
		
		// verify structure
		if (node.has("data") && node.has("action")) {
			JsonNode dataNode = node.get("data");
			String actionName = node.get("action").asText();
			
			// get identity
			Identity identity = null;
			Object targetObject = null;
			
			if (identities.containsKey(session)) {
				identity = identities.get(session);
			} else {
				identity = resolveIdentity(node);
				identity.setSession(session);
				identities.put(session, identity);
			}
			
			// if node has target the resolve the target
			if (node.has("target")) {
				String targetName = node.get("target").asText();
				targetObject = identity.getRoom(targetName);
			} else {
				targetObject = this;
			}
			
			// if the target is not null
			if (targetObject != null) {
				Method actionMethod = getMethod(targetObject, actionName);
				
				// there are two parameters
				// and the first parameter is assignable from identity
				if ((actionMethod.getParameterCount() == 2)
					&& (actionMethod.getParameters()[0]
							.getClass().isAssignableFrom(identity.getClass())))
				{
					// invoke the action
					ActionRunner runner = new ActionRunner(
													targetObject, 
													actionMethod, 
													identity, 
													dataNode);
					Thread thread = new Thread(runner);
					thread.start();
				}
			}
		}
		
	}
	
	/**
	 * Get method of an object
	 * @param targetObject
	 * @param actionName
	 * @return
	 */
	private Method getMethod(Object targetObject, String actionName) {
		Method methods[] = targetObject.getClass().getMethods();
		Method result = null;
		
		for (Method method: methods) {
			if (method.getName().equals(actionName)) {
				result = method;
				break;
			}
		}
		
		return result;
	}

	/**
	 * Resolve Identity
	 * Can put another field in JSON data like accessToken to 
	 * resolve the identity 
	 * @return
	 */
	protected Identity resolveIdentity(JsonNode messageData) {
		Identity identity = new Identity();
		return identity;
	}
	
}
