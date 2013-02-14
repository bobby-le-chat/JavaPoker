package servlet.poker.websocket.parser;

import java.util.List;

import servlet.poker.websocket.core.PokerUser;

public interface IParser {
	public String	getRoomDescription(List<PokerUser> visibility); // Only show cards of the players included in visibility
	public String	getRoomDescription(); // Show nothing
	public String	getFullRoomDescription(); // Show all cards
}
