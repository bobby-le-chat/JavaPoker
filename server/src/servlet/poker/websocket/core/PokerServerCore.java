package servlet.poker.websocket.core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WsOutbound;

import servlet.poker.websocket.Events.ConnectionEvent;
import servlet.poker.websocket.Events.MyEvent;
import utils.HTMLFilter;


public class PokerServerCore {


    private final Set<UserInbound> connectedPeople =
            new CopyOnWriteArraySet<UserInbound>();
    private final Set<Room> roomList =
            new CopyOnWriteArraySet<Room>();
    private final CommandManager myconnections =
            new CommandManager();

    protected final AtomicInteger connectionIds = new AtomicInteger(0);
    protected final AtomicInteger roomIds = new AtomicInteger(0);
    
    // Threaded Rooms and event
	private CopyOnWriteArraySet<EventObject> _eventList = 
            new CopyOnWriteArraySet<EventObject>();
    private CopyOnWriteArraySet<Room> _roomList = 
            new CopyOnWriteArraySet<Room>();
	
    private void distributeEvent(MyEvent event)
    {
    	if (event.getType() == 1) // Connection Event
    	{
    		ConnectionEvent 
    		if (event.)
    		this.findRoom();
    	}
    }
    
    public final class UserInbound extends MessageInbound {
        private final HttpSession _session;
        private final PokerUser	_pokerUser;

        public UserInbound(HttpSession session) {
        	this._session = session;
        	this._pokerUser = new PokerUser("User_" + connectionIds.incrementAndGet(), connectionIds.get(),  this);
        }
        @Override
        protected void onOpen(WsOutbound outbound) {
        	connectedPeople.add(this);
            String message = String.format("* %s %s",
            		this._pokerUser.getName(), " est dans la place.");
            broadcastToAll(message);
        }

        @Override
        protected void onClose(int status) {
        	connectedPeople.remove(this);
            String message = String.format("* %s %s",
            		this._pokerUser.getName(), " se casse en douce.");
            broadcastToAll(message);
        }

        @Override
        protected void onBinaryMessage(ByteBuffer message) throws IOException {
            throw new UnsupportedOperationException(
                    "Binary message not supported.");
        }

        @Override
        protected void onTextMessage(CharBuffer message) throws IOException {
            // Never trust the client
            String stringMessage = message.toString();
            MyEvent event = CommandManager.parseMessage(stringMessage, this);
            
            
            
            String filteredMessage = String.format("%s: %s",
            		this._pokerUser.getName(), HTMLFilter.filter(message.toString()));
            String messageOnly = message.toString();
    		System.out.println("Is Content '" + messageOnly + "'");
            if (messageOnly.equals("/newRoom"))
            {
            	GameRoom tempRoom = new GameRoom(_eventList, roomIds.incrementAndGet());
            	tempRoom.start();
            	_roomList.add(tempRoom);
                broadcastToAll("new Room");
            }
            else if (messageOnly.equals("/joinroom"))
            {
            	int roomId = this._pokerUser.getCurrentRoomId();
            	_roomList.iterator().next().pushEvent(event);
                broadcastToAll("new Room");
            }
        }
        private void broadcastToMyself(String message) {
            try {
                CharBuffer buffer = CharBuffer.wrap(message);
                this.getWsOutbound().writeTextMessage(buffer);
            } catch (IOException ignore) {
                // Ignore
            }
        }
        private void broadcastToAll(String message) {
            for (UserInbound connection : connectedPeople) {
                try {
                    CharBuffer buffer = CharBuffer.wrap(message);
                    connection.getWsOutbound().writeTextMessage(buffer);
                } catch (IOException ignore) {
                    // Ignore
                }
            }
        }
        private void broadcastToSpecific(String message, ArrayList<Integer> idList, Boolean inclusive) {
            for (UserInbound connection : connectedPeople)
            {
            	Boolean isOnTheList = idList.contains(connection.getId());
            	if ((isOnTheList && inclusive) || (!isOnTheList && !inclusive))
            	{
                    try {
                        CharBuffer buffer = CharBuffer.wrap(message);
                        connection.getWsOutbound().writeTextMessage(buffer);
                    } catch (IOException ignore) {
                    	System.out.print("Exception catch : broadcastToSpecific."); 
                    }
            		
            	}
            }
        }

    	@Override
    	public String	toString()
    	{
    		return this._nickname;
    	}
    }

	public StreamInbound addUser() {
		return new UserInbound();
	}
}
