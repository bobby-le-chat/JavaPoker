package servlet.poker.websocket.Events;

import java.util.List;

@SuppressWarnings("serial")
public class ConnectionEvent extends MyEvent { // Connection event is MyEvent type 1
	
	public enum EConnectionCode {
		newPlayer,
		playerLeft,
		unknow,
	}
	
	private final EConnectionCode code;
	private final List<String> arguments;

	public ConnectionEvent(Object User, EConnectionCode code, List<String> argList) {
		super(User, EEventType.connection);
		this.code = code;
		this.arguments = argList;
	}
	public ConnectionEvent(Object User, String stringCode, List<String> argList) {
		super(User, EEventType.connection);
		if (stringCode.equals("joinRoom"))
			this.code = EConnectionCode.newPlayer;
		else if (stringCode.equals("leftRoom"))
			this.code = EConnectionCode.playerLeft;
		else
			this.code = EConnectionCode.unknow;
		this.arguments = argList;
	}

	public EConnectionCode getCode() {
		return code;
	}
	public List<String> getArguments() {
		return arguments;
	}
	public String getArgument(int id) { // beware ! first argument is id = 0 and it's not the command's name
		int i = 0;
		for (String arg : this.arguments) {
			if (i == id)
				return arg;
			i++;
		}
		return "";
	}
	public String toString() {
		String res = "Connection Event code " + code + " args : [";
		for (String arg : this.arguments) {
			res += " '" + arg + "' ";
		}
		res += "]";
		return res;
	}
}
