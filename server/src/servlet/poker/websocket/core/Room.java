package servlet.poker.websocket.core;

import java.lang.Thread;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import servlet.poker.websocket.Events.MyEvent;
import servlet.poker.websocket.core.game.PlayerList;
import servlet.poker.websocket.core.game.Table;

public class Room extends Thread {

	protected final int	roomId;
	protected final Set<MyEvent> _eventList = new CopyOnWriteArraySet<MyEvent>();

	protected final Set<PokerUser> _playerList = new CopyOnWriteArraySet<PokerUser>();
//	protected final Set<PokerUser> _userList = new CopyOnWriteArraySet<PokerUser>();
	protected int handNb;
	protected Table	table;
	protected PlayerList	playerList;
	

	
	
	public Room(int id) {
		this.roomId = id;
	}
	public void pushEvent(MyEvent event) {
		this._eventList.add(event);
        synchronized (this) {
        	this.notify();
        }
	}
	public void myWait() {
		try {
	        synchronized (this) {
		        // TODO : Use System.currentTimeMillis() before and after the wait to know if an event occurred or if we continue the wait (for sleep needed like for next turn)
	        	super.wait();
			}
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	/*public void addUser(PokerUser user)
	{
		this._userList.add(user);
	}*/
	public void addPlayer(PokerUser player)
	{
		this._playerList.add(player);
	}
	protected void sendToAllPlayers(String message) {
		for (PokerUser player : this._playerList) {
			player.getSocket().broadcastToMyself(message);
		}
	}
	public void removePlayer(PokerUser player) {
		player.setCurrentRoomId(0);
		this._playerList.remove(player);
	}
	/*public void removePlayer(int id) {
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
