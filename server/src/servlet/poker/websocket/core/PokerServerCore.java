package servlet.poker.websocket.core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WsOutbound;

import servlet.poker.websocket.Events.ConnectionEvent;
import servlet.poker.websocket.Events.MyEvent;
import servlet.poker.websocket.Events.ConnectionEvent.EConnectionCode;
import servlet.poker.websocket.Events.MyEvent.EEventType;


public class PokerServerCore {


    private final Set<UserInbound> connectedPeople =
            new CopyOnWriteArraySet<UserInbound>();
    // private final Set<Room> roomList =
    //        new CopyOnWriteArraySet<Room>();
    //private final CommandManager myConnections =
    //        new CommandManager();

    protected final AtomicInteger connectionIds = new AtomicInteger(0);
    protected final AtomicInteger roomIds = new AtomicInteger(0);
    
    // Threaded Rooms and event
	//private CopyOnWriteArraySet<EventObject> _eventList = 
    //        new CopyOnWriteArraySet<EventObject>();
    private CopyOnWriteArraySet<Room> _roomList = 
            new CopyOnWriteArraySet<Room>();
    
    
    public PokerServerCore() {
    	this.addRoom(1);
    	this.addRoom(2);
    	this.addRoom(6);
    }
    
    private void addRoom(int roomId) {
    	GameRoom tempRoom = new GameRoom(roomId);
    	tempRoom.start();
    	this._roomList.add(tempRoom);
    }
    
    private Room findRoom(String argument) {
    	try {
    		int roomId = Integer.parseInt(argument);
        	for (Room room : this._roomList)
        	{
        		if (room.getRoomId() == roomId)
        			return room;
        	}
    	} catch (NumberFormatException exception) {
    		return null;
    	}
		return null; // TODO : use throw instead of null return value
	}
    private Room findRoom(int roomId) {
    	for (Room room : this._roomList)
        	{
        		if (room.getRoomId() == roomId)
        			return room;
        	}
		return null; // TODO : use throw instead of null return value
	}
    private void distributeEvent(MyEvent event)
    {
    	int senderRoom = event.getUser().getCurrentRoomId();
    	if (event.getType() == EEventType.connection && ((ConnectionEvent)event).getCode() == EConnectionCode.newPlayer) // Connection Event
    	{
    		ConnectionEvent connectionEvent = (ConnectionEvent)event;
    		Room concernedRoom = this.findRoom(senderRoom);
			if (senderRoom != 0 && concernedRoom != null) {
				concernedRoom.pushEvent(new ConnectionEvent(event.getSocket(), EConnectionCode.playerLeft, new ArrayList<String>()));
			}
			concernedRoom = this.findRoom(connectionEvent.getArgument(0));
			if (concernedRoom != null)
				concernedRoom.pushEvent(connectionEvent);
			else
				event.getSocket().broadcastToMyself("<srv>Connection impossible.");
			// TODO : change direct message text by a answer manager
			// Possibly a static class working with code message and enable to manage foreign languages
			return;
    	}
    	if (senderRoom != 0) {
    		Room concernedRoom = this.findRoom(senderRoom);
			if (concernedRoom != null)
				concernedRoom.pushEvent(event);
    	}
    	else
			event.getSocket().broadcastToMyself("<srv>Please connect to a room.");
    }
    
    

	public final class UserInbound extends MessageInbound {
        private final HttpSession _session;
        private final PokerUser	pokerUser;

        public PokerUser getPokerUser() {
			return pokerUser;
		}
		public UserInbound(HttpSession session) {
        	this._session = session;
        	this.pokerUser = new PokerUser("User_" + connectionIds.incrementAndGet(), connectionIds.get(),  this);
        }
        @Override
        protected void onOpen(WsOutbound outbound) {
        	connectedPeople.add(this);
        }

        @Override
        protected void onClose(int status) {
        	MyEvent event = new ConnectionEvent(this, EConnectionCode.playerLeft, new ArrayList<String>());
        	distributeEvent(event);
        	connectedPeople.remove(this);
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
            if (event == null)
            	this.broadcastToMyself("<srv>Incorrect message.");
            else {
            	distributeEvent(event);
            }
            
            /*
            String filteredMessage = String.format("%s: %s",
            		this.pokerUser.getName(), HTMLFilter.filter(message.toString()));
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
            	int roomId = this.pokerUser.getCurrentRoomId();
            	_roomList.iterator().next().pushEvent(event);
                broadcastToAll("new Room");
            }*/
        }
        public void broadcastToMyself(String message) {
            try {
                CharBuffer buffer = CharBuffer.wrap(message);
                this.getWsOutbound().writeTextMessage(buffer);
            } catch (IOException ignore) {
                // Ignore
            }
        }
        /*private void broadcastToAll(String message) {
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
        }*/
		public HttpSession get_session() {
			return _session;
		}
    }

	public StreamInbound addUser(HttpSession httpSession) {
		return new UserInbound(httpSession);
	}
}
