package servlet.poker.websocket.core.game;

import servlet.poker.websocket.Events.CommandEvent;
import servlet.poker.websocket.Events.GameEvent;
import servlet.poker.websocket.core.game.state.GameState;
import servlet.poker.websocket.core.game.state.NoGameState;
import servlet.poker.websocket.core.PokerUser;

public class GameContext {

	private GameState	currentState; // new, preFlop, flop, turn, river, end
	private PokerUser currentPlayer;
	
	public GameContext() {
		this.currentState = new NoGameState();
		this.currentPlayer = null;
	}
	
	public String updateGame(GameEvent event) {
		return this.currentState.updateState(this, event);
	}	
	public String updateGame(CommandEvent event) {
		return this.currentState.updateState(this, event);
	}
	
	public GameState getCurrentState() {
		return currentState;
	}
	public void setCurrentState(GameState currentState) {
		this.currentState = currentState;
	}
	public PokerUser getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(PokerUser currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public void clean() {
		// TODO Auto-generated method stub
		
	}

	public void initGame() {
		// TODO Auto-generated method stub
		
	}

	public void newHand() {
		// TODO Auto-generated method stub
		
	}

	public void stopGame() {
		// TODO Auto-generated method stub
		
	}
	
}
