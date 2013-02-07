package servlet.poker.websocket;

import java.util.EventObject;
import java.util.concurrent.CopyOnWriteArraySet;

public class GameRoom extends IRoom {

	private CopyOnWriteArraySet<EventObject> _eventList;
	private CopyOnWriteArraySet<EventObject> _playerList;
	private CopyOnWriteArraySet<PokerUser> _userList;
	
	GameRoom(CopyOnWriteArraySet<EventObject> eventList)
	{
		this._eventList = eventList;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true)
		{
	        System.out.println("Check Event : ");
	        if (this._eventList.size() > 0)
	        {
		        System.out.println(" Event trouvé : ");
	        	EventObject tempEvent = this._eventList.iterator().next();
		        System.out.println("  " + tempEvent.toString());
		        this._eventList.remove(tempEvent);
	        }
	        else
				try {
				        System.out.println(" Pas d'event trouvé, go to WAIT");
				        synchronized (this) {
					        System.out.println(" wait : " + this.hashCode());
					        // TODO : Use System.currentTimeMillis() before and after the wait to know if an event occurred or if we continue the wait (for sleep needed like for next turn)
				        	this.wait();
						}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
}
