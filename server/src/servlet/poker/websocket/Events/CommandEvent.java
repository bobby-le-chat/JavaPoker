package servlet.poker.websocket.Events;

import java.util.List;

@SuppressWarnings("serial")
public class CommandEvent extends MyEvent { // CommandEvent is MyEvent type 2

	final private String commandName;
	final private List<String> arguments;

	public CommandEvent(Object source, String cmd, List<String> args) {
		super(source, 2);
		this.commandName = cmd;
		this.arguments = args;
	}

	public String getCommandName() {
		return commandName;
	}

	public List<String> getArguments() {
		return arguments;
	}

}
