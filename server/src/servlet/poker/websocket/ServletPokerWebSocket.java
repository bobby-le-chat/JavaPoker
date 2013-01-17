package servlet.poker.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

import utils.HTMLFilter;

/**
 * Servlet implementation class ServletPokerWebSocket
 */
@WebServlet(description = "WebSocket part", urlPatterns = { "/PokerServer" })
public class ServletPokerWebSocket extends WebSocketServlet {
	private static final long serialVersionUID = 1L;

    private static final String GUEST_PREFIX = "Baltringue_";

    private final AtomicInteger connectionIds = new AtomicInteger(0);
    private final Set<ChatMessageInbound> connections =
            new CopyOnWriteArraySet<ChatMessageInbound>();

    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol,
            HttpServletRequest request) {
        return new ChatMessageInbound(connectionIds.incrementAndGet());
    }

    private final class ChatMessageInbound extends MessageInbound {

        private final String _nickname;
        private final int _id;

        private ChatMessageInbound(int id) {
            this._nickname = GUEST_PREFIX + id;
            this._id = id;
        }
        
        protected int	getId()
        {
        	return this._id;
        }
        @Override
        protected void onOpen(WsOutbound outbound) {
            connections.add(this);
            String message = String.format("* %s %s",
            		this._nickname, " est dans la place.");
            broadcastToAll(message);
        }

        @Override
        protected void onClose(int status) {
            connections.remove(this);
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
            broadcastToAll(filteredMessage);
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
            for (ChatMessageInbound connection : connections) {
                try {
                    CharBuffer buffer = CharBuffer.wrap(message);
                    connection.getWsOutbound().writeTextMessage(buffer);
                } catch (IOException ignore) {
                    // Ignore
                }
            }
        }
        private void broadcastToSpecific(String message, ArrayList<Integer> idList, Boolean inclusive) {
            for (ChatMessageInbound connection : connections)
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
    }

}
