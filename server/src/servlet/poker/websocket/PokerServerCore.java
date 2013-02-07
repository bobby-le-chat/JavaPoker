package servlet.poker.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.HttpSession;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WsOutbound;

import utils.HTMLFilter;


public class PokerServerCore {


    private final Set<UserInbound> connectedPeople =
            new CopyOnWriteArraySet<UserInbound>();
    private final Set<IRoom> roomList =
            new CopyOnWriteArraySet<IRoom>();
    private final CommandManager myconnections =
            new CommandManager();
    
    // Threaded Rooms and event
	private CopyOnWriteArraySet<EventObject> _eventList = 
            new CopyOnWriteArraySet<EventObject>();
    private CopyOnWriteArraySet<IRoom> _roomList = 
            new CopyOnWriteArraySet<IRoom>();
	
    public final class UserInbound extends MessageInbound {

        private final String _nickname;
        private final HttpSession _session;
        private final int _id;

        public UserInbound(int id, HttpSession session) {
        	this._session = session;
            if (this._session.getAttribute("name") == null) {
            	this._session.setAttribute("name", "Guest_" + id);
            }
            if (this._session.getAttribute("id") == null) {
            	this._session.setAttribute("id", id);
                this._id = id;
            }
            else
            	this._id = Integer.valueOf(this._session.getAttribute("id").toString());
            this._nickname = this._session.getAttribute("name").toString();
        }
        public UserInbound() {
        	this._session = null;
        	this._nickname = "Guest_unknow";
        	this._id = 0;
        }
        protected int	getId()
        {
        	return this._id;
        }
        protected String	getNickname()
        {
        	return this._nickname;
        }
        @Override
        protected void onOpen(WsOutbound outbound) {
        	connectedPeople.add(this);
            String message = String.format("* %s %s",
            		this._nickname, " est dans la place.");
            broadcastToAll(message);
        }

        @Override
        protected void onClose(int status) {
        	connectedPeople.remove(this);
            String message = String.format("* %s %s",
            		this._nickname, " se casse en douce.");
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
            String filteredMessage = String.format("%s: %s",
            		this._nickname, HTMLFilter.filter(message.toString()));
            String messageOnly = message.toString();
    		System.out.println("Is Content '" + messageOnly + "'");
            if (messageOnly.equals("/newRoom"))
            {
            	GameRoom tempRoom = new GameRoom(_eventList);
            	tempRoom.start();
            	_roomList.add(tempRoom);
                broadcastToAll("new Room");
            }
            else
            {
            	_eventList.add(new MessageEvent(this, filteredMessage));
            	broadcastToAll(filteredMessage);
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
