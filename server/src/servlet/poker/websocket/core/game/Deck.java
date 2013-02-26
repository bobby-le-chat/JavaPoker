package servlet.poker.websocket.core.game;

import java.util.HashSet;
import java.util.Set;

public class Deck {
	
	private final String[] ref = new String[] {
		"DA", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "DJ", "DQ", "DK",
		"HA", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "HJ", "HQ", "HK",
		"SA", "S2", "S3", "S4", "S5", "S6", "S7", "S8", "S9", "S10", "SJ", "SQ", "SK",
		"CA", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "CJ", "CQ", "CK",
		};

	private Set<Card> availableCards = new HashSet<Card>();
	private Set<Card> pickedCards = new HashSet<Card>();
	
	public Deck() {
		for (String str : ref) {
			this.availableCards.add(new Card(str));
		}
	}
	
	public void refillDeck() {
		for (Card card : this.pickedCards) {
			this.availableCards.add(card);
		}
		this.pickedCards.clear();
	}
	public Card pickCard() {
		int random = (int)(Math.random() * (this.availableCards.size() + 1));
		int i = 0;
		for (Card card : this.availableCards) {
			if (i == random) {
				this.pickedCards.add(card);
				this.availableCards.remove(card);
				return card;
			}
		}
		return null;
	}
	public void resetDeck() {
		this.pickedCards.clear();
		this.availableCards.clear();
		for (String str : ref) {
			this.availableCards.add(new Card(str));
		}
	}
}
