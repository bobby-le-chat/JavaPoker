package servlet.poker.websocket.core.game;

import java.util.ArrayList;
import java.util.List;

public class Card implements Comparable<Card>{
	
	public enum Suit {
		diamond,
		heart,
		spade,
		club,
	}
	
	final private int value;
	final private Suit suit;
	final private List<Integer> privateScore = new ArrayList<Integer>();
	
	public Card(int value, Suit suit) {
		this.value = value;
		this.suit = suit;
	}
	public Card(String card) {
		String value, stringSuit;
		value = card.substring(1);
		stringSuit = card.substring(0, 1);
		System.out.println(stringSuit + " " + value);
		this.value = this.valueStringToInt(value);
		this.suit = this.suitStringToEnum(stringSuit);
	}
	public Card(String value, Suit suit) {
		this.value = this.valueStringToInt(value);
		this.suit = suit;
	}

	private int valueStringToInt(String value) {
		switch (value) {
			case "J"	: return 11;
			case "Q"	: return 12;
			case "K"	: return 13;
			case "A"	: return 14;
			default		: return Integer.parseInt(value);
		}
	}
	private Suit suitStringToEnum(String stringSuit) {
		Suit enumSuit = null;
		switch (stringSuit) {
			case "D"	: enumSuit = Suit.diamond; break;
			case "H"	: enumSuit = Suit.heart; break;
			case "S"	: enumSuit = Suit.spade; break;
			case "C"	: enumSuit = Suit.club; break;
		}
		return enumSuit;
	}
	
	public int getValue() {
		return value;
	}
	public String getStringValue() {
		switch (value) {
		case 11 : return "J";
		case 12 : return "Q";
		case 13 : return "K";
		case 14 : return "A";
		}
		return String.valueOf(value);
	}
	public Suit getSuit() {
		return suit;
	}
	public String getStringSuit() {
		switch (suit) {
		case diamond : return "D";
		case heart : return "H";
		case spade : return "S";
		case club : return "C";
		}
		return null;
	}
	@Override
	public String toString() {
		return this.getStringSuit() + this.getStringValue();
	}
	@Override
	public int compareTo(Card comparedCard) {
		if (comparedCard.getValue() < this.value)
			return -1;
		else if (comparedCard.getValue() == this.value)
			return 0;
		else
			return 1;
	}
}
