package servlet.poker.websocket.core.game.state;

import servlet.poker.websocket.Events.CommandEvent;
import servlet.poker.websocket.Events.GameEvent;
import servlet.poker.websocket.core.game.GameContext;

public class NoGameState extends GameState {

	public NoGameState() {
	}
	public NoGameState(GameContext context) {
		context.stopGame();
	}

	@Override
	public String updateState(GameContext context, GameEvent event) {
		return "<srv>Incorrect Action";
	}

	@Override
	public String updateState(GameContext context, CommandEvent event) {
		if (event.getCommandName().equals("start")) {
			context.setCurrentState(new PreflopState(context, true));
			return "<srv>New Game";
		}
		return super.updateState(context, event);
	}

}
