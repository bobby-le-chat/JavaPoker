package servlet.poker.websocket.Events;


@SuppressWarnings("serial")
public class MessageEvent extends MyEvent { // MessageEvent is a MyEvent type 4

	private final String message;

	public MessageEvent(Object source, String message) {
		super(source, 4);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
