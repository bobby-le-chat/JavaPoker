package servlet.poker.websocket.core;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import servlet.poker.websocket.Events.CommandEvent;
import servlet.poker.websocket.Events.ConnectionEvent;
import servlet.poker.websocket.Events.GameEvent;
import servlet.poker.websocket.Events.MessageEvent;
import servlet.poker.websocket.Events.MyEvent;

public class CommandManager {
	
	private static GameEvent gameEvent(String message, Object source) {
		try {
			int i = Integer.parseInt(message);
			return new GameEvent(source, i);
		}
		catch (Exception e) { 
			return null;
		}
	}
	private static CommandEvent commandEvent(String cmd, String args, Object source) {
		// This regex find all the white spaces not into quotes, and allows \" in quotes
		String regex = "\\s+(?=((\\\\[\\\\\"]|[^\\\\\"])*\"(\\\\[\\\\\"]|[^\\\\\"])*\")*(\\\\[\\\\\"]|[^\\\\\"])*$)";
		List<String> argList = Arrays.asList(args.split(regex));
		return new CommandEvent(source, cmd.substring(1), argList); // substring 1 because of the slash
	}
	private static ConnectionEvent connectionEvent(String cmd, String args, Object source) {
		// This regex find all the white spaces not into quotes, and allows \" in quotes
		String regex = "\\s+(?=((\\\\[\\\\\"]|[^\\\\\"])*\"(\\\\[\\\\\"]|[^\\\\\"])*\")*(\\\\[\\\\\"]|[^\\\\\"])*$)";
		List<String> argList = Arrays.asList(args.split(regex));
		return new ConnectionEvent(source, cmd.substring(1), argList); // substring 1 because of the slash
	}
	private static MyEvent messageEvent(String message, Object source) {
		String regex = "^(/\\w+)"; // un message qui commence par /quelquechose est une commande
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(message);
		if (m.find() == true) // The message starts wish /, it's a command
		{
			if (m.group().equals("/joinRoom")) // This specific command is a connection Event
				return CommandManager.connectionEvent(m.group(), message.substring(m.group().length()), source);
			// else, every other command
			return CommandManager.commandEvent(m.group(), message.substring(m.group().length()), source);
		}
		// Not a command, just a simple text message
		return new MessageEvent(source, message);
	}
	private static MyEvent	determinateTypeOfEvent(String message, Object source)
	{
		String regex = "^(<\\w+>)"; // Il n'y a pas encore dans le protocole de balise fermante
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(message);
		if (m.find() == true)
			{
				String type = m.group();
				if (type.equals("<game>")) // écrit en dur, pas propre
					return CommandManager.gameEvent(message.substring(type.length()), source);
				else if (type.equals("<msg>"))
					return CommandManager.messageEvent(message.substring(type.length()), source);
			}
		return null;
	}
	public static MyEvent	parseMessage(String message, Object source) // Do not check validity of the event, the user of the event must check it before using it
	{
		MyEvent res = CommandManager.determinateTypeOfEvent(message, source);
		return res;
	}
}
