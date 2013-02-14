package servlet.poker.websocket.Events;

import java.util.EventObject;

@SuppressWarnings("serial")
public class MyEvent extends EventObject {

	private final int type; // 1 = Connection, 2 = Command, 3 = Game, 4 = Message
	public MyEvent(Object source, int type) {
		super(source);
		this.type = type;
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString()
	{
		return getClass().getName();
	}
	public int getType() {
		return type;
	}
}
