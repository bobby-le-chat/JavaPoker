package javapoker.client.command;

import java.net.URI;

import com.kaazing.gateway.client.html5.WebSocket;
import com.kaazing.gateway.client.html5.WebSocketAdapter;
import com.kaazing.gateway.client.html5.WebSocketEvent;

public class AuthCommand {

	public static void main(String[] args) throws Exception {
		
		final WebSocket ws = new WebSocket();
		
		ws.addWebSocketListener(new WebSocketAdapter() {
			@Override
			public void onMessage(WebSocketEvent messageEvent) {
				System.out.println("Received Event Data: "
						+ messageEvent.getData());
				// let's close the open connection...
				try {
					ws.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			
			public void onOpen(WebSocketEvent openEvent) {
				System.out.println("Connection to Server is up!");
				// we are able to talk to the WebSocket gateway
				try {
					ws.send("Hey, server!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		ws.connect(new URI("ws://echo.websocket.org:80"));
	}
}
