package servlet.poker.websocket.parser;

import java.util.List;
import org.jdom2.Element;
import org.jdom2.Attribute;

import servlet.poker.websocket.core.PokerUser;

public class XmlParser implements IParser {

	private Element racine = new Element("game");
	private Element hand = new Element("hand");
	private Element action = new Element("action");
	private Element table = new Element("table");
	private Element playerList = new Element("playerList");
	
	private Element serverList = new Element("serverList"):
	
	public XmlParser()
	{
		this.racine.addContent(this.hand);
		this.racine.setAttribute(new Attribute("id", String.valueOf(356)));
		
		this.hand.addContent(this.action);
		this.hand.setAttribute(new Attribute("nb", String.valueOf(1)));
		
		this.action.addContent(this.table);
		this.action.addContent(this.playerList);
		

        System.out.println(this.racine.getText());
	}
	@Override
	public String getRoomDescription(List<PokerUser> visibility) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRoomDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFullRoomDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getServerList() {
		// TODO Auto-generated method stub
		return null;
	}

}
