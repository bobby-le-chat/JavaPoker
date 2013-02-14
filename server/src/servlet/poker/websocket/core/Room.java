package servlet.poker.websocket.core;

import java.lang.Thread;
import java.util.EventObject;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class Room extends Thread {

	private final int	roomId;
	protected final Set<EventObject> _eventList;
	protected final Set<PokerUser> _playerList = new CopyOnWriteArraySet<PokerUser>();
	protected final Set<PokerUser> _userList = new CopyOnWriteArraySet<PokerUser>();

	public Room(Set<EventObject> eventList, int id) {
		this._eventList = eventList;
		this.roomId = id;
	}
	public void pushEvent(EventObject event)
	{
		this._eventList.add(event);
	}
	/*public void addUser(PokerUser user)
	{
		this._userList.add(user);
	}
	public void addPlayer(PokerUser player)
	{
		this._userList.add(player);
	}
	public void removePlayer(PokerUser player) {
		this._playerList.remove(player);
	}
	public void removePlayer(int id) {
		for (PokerUser itPokerUser : this._playerList)
			if  (itPokerUser.getId() == id)
				this._playerList.remove(itPokerUser);
	}
	public void removeUser(PokerUser user) {
		this._userList.remove(user);
	}
	public void removeUser(int id) {
		for (PokerUser itPokerUser : this._userList)
			if  (itPokerUser.getId() == id)
				this._userList.remove(itPokerUser);
	}
	*/
	public int getRoomId() {
		return this.roomId;
	}
}
