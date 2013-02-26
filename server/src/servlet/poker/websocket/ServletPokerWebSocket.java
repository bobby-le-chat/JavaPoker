package servlet.poker.websocket;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

import servlet.poker.websocket.core.PokerServerCore;


/**
 * Servlet implementation class ServletPokerWebSocket
 */
@WebServlet(description = "WebSocket part", urlPatterns = { "/PokerServer" })
public class ServletPokerWebSocket extends WebSocketServlet {
	private static final long serialVersionUID = 1L;

    private static final String GUEST_PREFIX = "User_";

    private final AtomicInteger connectionIds = new AtomicInteger(0);
    private final PokerServerCore Core = new PokerServerCore();

    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol,
            HttpServletRequest request) {
    	
    	// New connection
    	// TODO : check if session exists, otherwise login and/or create session
    	if (request.getSession().getAttribute("name") == null)
    	{
    		// temp manual session creation
    		request.getSession().setAttribute("name", GUEST_PREFIX + this.connectionIds.incrementAndGet());
    		// rights : 0 = visitor, 1 = user, 2 = admin
    		request.getSession().setAttribute("right", 0);
    	}
    	// TODO : Create WS user (from database)
    	
        return this.Core.addUser(request.getSession());
    }

    

}
