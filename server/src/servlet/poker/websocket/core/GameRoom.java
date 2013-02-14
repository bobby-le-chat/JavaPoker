package servlet.poker.websocket.core;

import java.util.EventObject;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class GameRoom extends Room {

	
	public GameRoom(Set<EventObject> eventList, int roomId) {
		super(eventList, roomId);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true)
		{
	        System.out.println("Check Event : ");
	        if (super._eventList.size() > 0)
	        {
		        System.out.println(" Event trouvé : ");
	        	EventObject tempEvent = super._eventList.iterator().next();
		        System.out.println("  " + tempEvent.toString());
		        super._eventList.remove(tempEvent);
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
