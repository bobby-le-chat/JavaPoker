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

import javax.servlet.http.HttpSession;

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
    private final PokerServerCore Core = new PokerServerCore();

    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol,
            HttpServletRequest request) {
    	
    	// New connection
    	// TODO : check if session exists, otherwise login and/or create session
    	if (request.getSession().getAttribute("name") == null)
    	{
    		System.out.println("New Connection not logged");
    		// temp manual session creation
    		request.getSession().setAttribute("name", GUEST_PREFIX + this.connectionIds.incrementAndGet());
    		// rights : 0 = visitor, 1 = user, 2 = admin
    		request.getSession().setAttribute("right", 0);
    		System.out.println(request.getSession().getAttribute("referer"));
    	}
    	// TODO : Create WS user (from database)
    	
        return this.Core.addUser();
    }

    

}
