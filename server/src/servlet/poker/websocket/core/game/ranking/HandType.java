package servlet.poker.websocket.core.game.ranking;

import java.util.LinkedHashSet;
import java.util.Set;
import servlet.poker.websocket.core.game.Card;

public class HandType {
	
	private  Set<Card> cards;
	private  EHandType hand;

	/*public HandType(Set<Card> cards, EHandType hand) {
		this.cards = cards;
		this.hand = hand;
	}*/
	public HandType() {
		this.hand = EHandType.HighCard;
		this.cards = new LinkedHashSet<Card>();
	}

	public void setHand(EHandType hand) {
		this.hand = hand;
	}
	public EHandType getHand() {
		return this.hand;
	}
	

	public Set<Card> getCards() {
		return cards;
	}
	public void setCards(Set<Card> cards) {
		this.cards = cards;
	}
	public void addCard(Card card) {
		this.cards.add(card);
	}
	public void addCards(Set<Card> cardsbyValue) {
		this.cards.addAll(cardsbyValue);
	}
	public void cleanCards() {
		this.cards = new LinkedHashSet<Card>();
	}
	public void cleanCards(int nb) {
		int nbKeep = this.cards.size() - nb;
		int i = 0;
		Set<Card> newSet = new LinkedHashSet<Card>();
		for (Card card : this.cards) {
			if (++i > nbKeep)
				break;
			newSet.add(card);
		}
		this.cards = newSet;
		System.out.println(this.cards);
	}

	@Override
	public String toString() {
		String resToString = "";
		resToString =  this.hand.toString() + " with (";
		int count = 0;
		for (Card card : this.cards) {
			resToString += card.toString() + ", ";
			count++;
		}
		if (count > 0)
			resToString = resToString.substring(0, resToString.length() - 2); // we cut the last ", "
		resToString += ")";
		return resToString;
	}
}
