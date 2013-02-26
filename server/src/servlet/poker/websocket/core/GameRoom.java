package servlet.poker.websocket.core;

import servlet.poker.websocket.Events.CommandEvent;
import servlet.poker.websocket.Events.ConnectionEvent;
import servlet.poker.websocket.Events.ConnectionEvent.EConnectionCode;
import servlet.poker.websocket.Events.GameEvent;
import servlet.poker.websocket.Events.MessageEvent;
import servlet.poker.websocket.Events.MyEvent;
import servlet.poker.websocket.core.game.GameContext;

public class GameRoom extends Room {

	private final GameContext gameContext = new GameContext();
	
	public GameRoom(int roomId) {
		super(roomId);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

        System.out.println("Room_" + this.roomId + " started.");
		while (true)
		{
	        if (super._eventList.size() > 0)
	        {
	        	MyEvent tempEvent = super._eventList.iterator().next();
	        	String result = "";
	        	switch (tempEvent.getType()) {
		        	case connection : result = this.managePlayers(((ConnectionEvent)tempEvent)); break;
		        	case command : result = this.gameContext.updateGame((CommandEvent)tempEvent); break;
					case game : result = this.gameContext.updateGame((GameEvent)tempEvent); break;
					case message : this.sendToAllPlayers("<msg>" + tempEvent.getUser().getName() + " said : " + ((MessageEvent)tempEvent).getMessage()); break;
					default : result = "<srv>Unused Event.";
	        	}
	        	if (result != "") {
	        		tempEvent.getSocket().broadcastToMyself(result);
	        	}
		        super._eventList.remove(tempEvent);
	        }
	        else
				this.myWait();
		}
	}

	private String managePlayers(ConnectionEvent connectionEvent) {
		if (connectionEvent.getCode() == EConnectionCode.newPlayer) {
			if (this._playerList.size() >= 9)
				return "<srv>Room Full.";
			connectionEvent.getUser().setCurrentRoomId(this.roomId);
			this.addPlayer(connectionEvent.getUser());
			this.sendToAllPlayers("<srv>" + connectionEvent.getUser().getName() + " joined the room.");
			return "<srv>Welcome " + connectionEvent.getUser().getName() + ".";
		} else if (connectionEvent.getCode() == EConnectionCode.playerLeft) {
			this.sendToAllPlayers("<srv>" + connectionEvent.getUser().getName() + " left the room.");
			this.removePlayer(connectionEvent.getUser());
			return "";
		}
		return "<srv>Unknow Connection Event.";
	}
	
}
