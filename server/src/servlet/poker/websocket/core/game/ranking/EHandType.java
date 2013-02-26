package servlet.poker.websocket.core.game.ranking;

public enum EHandType {
	HighCard(1),
	Pair(2),
	TwoPair(3),
	ThreeofaKind(4),
	Straight(5),
	Flush(6),
	FullHouse(7),
	FourofaKind(8),
	StraightFlush(9),
	RoyalFlush(10),
	;
	

    private final int id;
	EHandType(int id) { this.id = id; }
    public int getValue() { return id; }
	public static void changeIfHigher(EHandType comparedValue, EHandType compareValue) {
		if (compareValue.getValue() > comparedValue.getValue())
			comparedValue = compareValue;
	}
}