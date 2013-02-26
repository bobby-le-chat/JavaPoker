package servlet.poker.websocket.Events;

import java.util.EventObject;

import servlet.poker.websocket.core.PokerServerCore.UserInbound;
import servlet.poker.websocket.core.PokerUser;

@SuppressWarnings("serial")
public class MyEvent extends EventObject {
	
	public enum EEventType {
		connection,
		command,
		game,
		message,
	}
	
	private final EEventType type; // 1 = Connection, 2 = Command, 3 = Game, 4 = Message
	private final UserInbound socket;
	
	public MyEvent(Object source, EEventType type) {
		super(source);
		this.socket = (UserInbound)source;
		this.type = type;
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString()
	{
		return getClass().getName();
	}
	public EEventType getType() {
		return type;
	}
	public UserInbound getSocket() {
		return socket;
	}
	public PokerUser getUser() {
		return socket.getPokerUser();
	}
}
