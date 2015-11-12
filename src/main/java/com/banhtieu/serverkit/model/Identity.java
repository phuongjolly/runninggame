package com.banhtieu.serverkit.model;

import java.util.ArrayList;

import org.springframework.web.socket.WebSocketSession;

import com.banhtieu.serverkit.Room;

/**
 * The identity has indentify the user
 * @author banhtieu
 *
 */
public class Identity {

	/**
	 * The socket session
	 */
	private WebSocketSession session;

	/**
	 * all rooms that this user are in
	 */
	private ArrayList<Room> rooms;
	
	/**
	 * Get the socket session 
	 * @return
	 */
	public WebSocketSession getSession() {
		return session;
	}

	/**
	 * Set the socket session
	 * @param session
	 */
	public void setSession(WebSocketSession session) {
		this.session = session;
	}

	/**
	 * Get Room
	 * @param targetName
	 */
	public Room getRoom(String targetName) {
		Room result = null;
		
		for (Room room: rooms) {
			if (room.getName().equals(targetName)) {
				result = room;
				break;
			}
		}
		
		return result;
	}
}
