package servlet.poker.websocket.core.game.state;

import servlet.poker.websocket.Events.CommandEvent;
import servlet.poker.websocket.Events.GameEvent;
import servlet.poker.websocket.core.game.GameContext;

public abstract class GameState {
	public abstract String	updateState(GameContext context, GameEvent event);
	
	public String updateState(GameContext context, CommandEvent event) {
		if (event.getCommandName().equals("stop")) {
			context.setCurrentState(new NoGameState(context));
			return "<srv>Game stopped.";
		}
		return "<srv>Incorrect Command";
	}
}
