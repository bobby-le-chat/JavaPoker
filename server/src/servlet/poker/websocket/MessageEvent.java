package servlet.poker.websocket;

import java.util.EventObject;

import org.apache.catalina.websocket.MessageInbound;

public class MessageEvent extends EventObject {

	public String	_message;
	public MessageInbound	_sender;
	public MessageEvent(MessageInbound source, String message) {
		super(source);
		this._message = message;
		this._sender = source;
	}

	@Override
	public String	toString()
	{
		return this._sender.toString() + this._message;
	}
}
