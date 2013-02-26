package servlet.poker.websocket.core.game.ranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import servlet.poker.websocket.core.game.Card;

public class CardRanking {


	private static void addKicker(HandType res, Set<Card> cardSet) throws Exception {
		List<Card> cardList = new ArrayList<Card>(cardSet);
		Collections.sort(cardList);
		Set<Card> currentSet = res.getCards();
		Iterator<Card> it = cardList.listIterator();
		while (currentSet.size() < 5) {
			if (!it.hasNext())
				throw new Exception("Troubles with cards and kicker");
			Card card = it.next();
			currentSet.add(card);
		}
		res.addCards(currentSet);
	}
	private static Set<Card> getCardsbyValue(Set<Card> refSet, int valueRef) {
		Set<Card> resSet = new HashSet<Card>();
		for (Card card : refSet){
			if (card.getValue() == valueRef)
				resSet.add(card);
		}
		return resSet;
	}
	
	private static HandType isDouble(Set<Card> cardSet) {
		Map<Integer, Integer> nbCard = new TreeMap<Integer, Integer>(Collections.reverseOrder());
		for (Card card : cardSet) {
			if (nbCard.get(card.getValue()) == null)
				nbCard.put(card.getValue(), 1); // Collection[valueOfCard] += 1;
			else
				nbCard.put(card.getValue(), nbCard.get(card.getValue()) + 1); // Collection[valueOfCard] += 1;
		}
		HandType res = new HandType();
		for (Entry<Integer, Integer> i : nbCard.entrySet()) {
			switch (i.getValue()) {
				case 4 : {
					res.setHand(EHandType.FourofaKind);
					res.cleanCards();
					res.addCards(CardRanking.getCardsbyValue(cardSet, i.getKey()));
				} break;
				case 3 : {
					if (res.getHand() == EHandType.Pair) {
						res.setHand(EHandType.FullHouse);
						Set<Card> tempSet = res.getCards();
						res.cleanCards();
						res.addCards(CardRanking.getCardsbyValue(cardSet, i.getKey()));
						res.addCards(tempSet);
					}
					else if (res.getHand() == EHandType.TwoPair) {
						res.setHand(EHandType.FullHouse);
						res.cleanCards(2);
						Set<Card> tempSet = res.getCards();
						res.cleanCards();
						res.addCards(CardRanking.getCardsbyValue(cardSet, i.getKey()));
						res.addCards(tempSet);
					}
					else if (res.getHand() != EHandType.ThreeofaKind) {
						res.setHand(EHandType.ThreeofaKind);
						res.addCards(CardRanking.getCardsbyValue(cardSet, i.getKey()));
					}
				} break;
				case 2 : {
					if (res.getHand() == EHandType.ThreeofaKind) {
						res.setHand(EHandType.FullHouse);
						res.addCards(CardRanking.getCardsbyValue(cardSet, i.getKey()));
					}
					else if (res.getHand() == EHandType.Pair) {
						res.setHand(EHandType.TwoPair);
						res.addCards(CardRanking.getCardsbyValue(cardSet, i.getKey()));
					}
					else if (res.getHand() == EHandType.HighCard) {
						res.addCards(CardRanking.getCardsbyValue(cardSet, i.getKey()));						
						res.setHand(EHandType.Pair);
					}
				} break;
			}
		}
		try {
			CardRanking.addKicker(res, cardSet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	private static HandType isFlush(Set<Card> cardSet) {
		TreeMap<Card.Suit, Integer> nbCard = new TreeMap<Card.Suit, Integer>(Collections.reverseOrder());
		HandType res = new HandType();
		for (Card card : cardSet) {
			if (nbCard.get(card.getSuit()) != null)
				nbCard.put(card.getSuit(), nbCard.get(card.getSuit()) + 1);
			else
				nbCard.put(card.getSuit(), 1);
		}
		Card.Suit flush = nbCard.firstKey();
		if (nbCard.get(nbCard.firstKey()) >= 5) {
			Iterator<Card> it = cardSet.iterator();
			while (it.hasNext()) {
				Card card = it.next(); // must be called before you can call i.remove()
			   // Do something
				if (card.getSuit() != flush)
					it.remove();
			}
			res.setHand(EHandType.Flush);
			try {
				CardRanking.addKicker(res, cardSet);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return res;
	}
	private static HandType isStraight(Set<Card> cardSet) {
		List<Card> cardList = new ArrayList<Card>(cardSet);
		Collections.sort(cardList);
		int value = 0;
		Card ace = null;
		HandType res = new HandType();
		for (Card card : cardList) {
			System.out.println(card.toString() + " " + value + " " + res.getCards().toString());
			if (card.getValue() == 14)
				ace = card;
			if (value != card.getValue()) {
				if ((card.getValue() + 1) != value)
					res.cleanCards();
				res.addCard(card);
				if (res.getCards().size() >= 5) {
					res.setHand(EHandType.Straight);
					return res;
				}
				value = card.getValue();
			}
		}
		if (res.getCards().size() == 4 && value == 2 && ace != null) {
			res.addCard(ace);
			res.setHand(EHandType.Straight);
			return res;
		}
		if (res.getCards().size() < 5) {
			res.cleanCards();
			System.out.println(res.toString() + " " + cardSet.toString());
			try {
				CardRanking.addKicker(res, cardSet);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		return res;
	}
	
	public static HandType createHandType(Set<Card> cardSet) {
		HandType combi =  CardRanking.isDouble(cardSet);
		HandType flush = CardRanking.isFlush(cardSet);
		HandType straight = CardRanking.isStraight(cardSet);
		if (flush.getHand().getValue() > combi.getHand().getValue())
			return flush;
		else if (straight.getHand().getValue() > combi.getHand().getValue())
			return straight;
		else
			return combi;
	}
	public static HandType createHandType(Set<Card> cardSet, Set<Card> personnalCardSet) {
		cardSet.addAll(personnalCardSet);
		return createHandType(cardSet);
	}
}
