package servlet.poker.websocket.core.game.state;

import servlet.poker.websocket.Events.GameEvent;
import servlet.poker.websocket.core.game.GameContext;

public class PreflopState extends GameState {

	public PreflopState(GameContext context, boolean newGame) {
		context.clean();
		if (newGame == true)
			context.initGame();
		context.newHand();
	}
	
	@Override
	public String updateState(GameContext context, GameEvent event) {
		if (event.getUser() == context.getCurrentPlayer())
			return "<game>";
		return "<srv>Incorrect Action";
	}


}
