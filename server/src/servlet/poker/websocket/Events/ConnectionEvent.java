package servlet.poker.websocket.Events;

import java.util.Iterator;
import java.util.List;

@SuppressWarnings("serial")
public class ConnectionEvent extends MyEvent { // Connection event is MyEvent type 1
	
	// TODO : use enum for the codeNb
	// 1 = user joins the room
	// 2 = user leaves the room
	
	private final int code;
	private final List<String> arguments;

	public ConnectionEvent(Object User, int code, List<String> argList) {
		super(User, 1);
		this.code = code;
		this.arguments = argList;
	}
	public ConnectionEvent(Object User, String stringCode, List<String> argList) {
		super(User, 1);
		if (stringCode.equals("joinRoom"))
			this.code = 1;
		else if (stringCode.equals("leftRoom"))
			this.code = 2;
		else
			this.code = -1;
		this.arguments = argList;
	}

	public int getCode() {
		return code;
	}
	public List<String> getArguments() {
		return arguments;
	}
	public String getArgument(int id) { // beware ! first argument is id = 0 
		int i = 0;
		Iterator<String> it = this.arguments.iterator();
		String s;
		while (it.hasNext()) {
			s = it.next();
			if (i == id)
				return s;
			i++;
		}
		return null;
	}
	
}
