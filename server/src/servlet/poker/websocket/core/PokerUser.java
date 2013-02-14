package servlet.poker.websocket.core;

import org.apache.catalina.websocket.MessageInbound;

public class PokerUser {

	private MessageInbound socket;
	
	private final String name;
	private final int	id;
	private int	currentRoomId = 0;
	
	public PokerUser(String name, int id, MessageInbound socket)
	{
		this.name = name;
		this.id = id;
		this.socket = socket;
	}

	public MessageInbound getSocket() {
		return socket;
	}
	public String getName() {
		return name;
	}
	public int getId() {
		return id;
	}
	public void setSocket(MessageInbound socket) {
		this.socket = socket;
	}

	public int getCurrentRoomId() {
		return currentRoomId;
	}

	public void setCurrentRoomId(int currentRoomId) {
		this.currentRoomId = currentRoomId;
	}
	
}
