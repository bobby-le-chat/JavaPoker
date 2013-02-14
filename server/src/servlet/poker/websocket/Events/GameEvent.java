package servlet.poker.websocket.Events;

@SuppressWarnings("serial")
public class GameEvent extends MyEvent {  // GameEvent is MyEvent type 3

	private final int action;
	public GameEvent(Object source, int action) {
		super(source, 3);
		this.action = action;
	}
	@Override
	public String	toString()
	{
		return null;
	}
	public int getAction() {
		return action;
	}
}
