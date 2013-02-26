package servlet.poker.websocket.core;

import servlet.poker.websocket.core.PokerServerCore.UserInbound;
import servlet.poker.websocket.core.game.Card;

public class PokerUser {

	private UserInbound socket;
	
	private final String name;
	private final int	id;
	private int	currentRoomId = 0;

	private int	stack = 0;
	private PlayerAction	action = PlayerAction.out;
	private int	bet = 0;
	private int	position = 0;
	private Card	card1 = null;
	private Card	card2 = null;
	
	public enum PlayerAction {
		fold { public String toString() { return "Fold"; } },
		check { public String toString() { return "Check"; } },
		call { public String toString() { return "Call"; } },
		raise { public String toString() { return "Raise"; } },
		allin { public String toString() { return "All-In"; } },
		out { public String toString() { return "Out"; } },
		waiting { public String toString() { return "Waiting"; } },
		playing { public String toString() { return "Playing"; } },
		show { public String toString() { return "Show"; } },
		hide { public String toString() { return "Hide"; } },
		win { public String toString() { return "Win"; } },
		loose { public String toString() { return "Loose"; } },
		newPlayer { public String toString() { return "New"; } },
	}
	
	public PokerUser(String name, int id, UserInbound socket)
	{
		this.name = name;
		this.id = id;
		this.socket = socket;
	}

	public UserInbound getSocket() {
		return socket;
	}
	public String getName() {
		return name;
	}
	public int getId() {
		return id;
	}
	public void setSocket(UserInbound socket) {
		this.socket = socket;
	}

	public int getCurrentRoomId() {
		return currentRoomId;
	}

	public void setCurrentRoomId(int currentRoomId) {
		this.currentRoomId = currentRoomId;
	}

	public int getStack() {
		return stack;
	}

	public void setStack(int stack) {
		this.stack = stack;
	}

	public PlayerAction getAction() {
		return action;
	}

	public void setAction(PlayerAction action) {
		this.action = action;
	}

	public int getBet() {
		return bet;
	}

	public void setBet(int bet) {
		this.bet = bet;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Card getCard1() {
		return card1;
	}

	public void setCard1(Card card1) {
		this.card1 = card1;
	}

	public Card getCard2() {
		return card2;
	}

	public void setCard2(Card card2) {
		this.card2 = card2;
	}
	
}
