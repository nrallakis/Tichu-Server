package gr.nrallakis.tichu.server.game;

import com.badlogic.gdx.utils.Array;

public final class CardCombination {

    public static int PAIR = 0;
    public static int FULLHOUSE = 1;
    public static int STRAIGHT_PAIRS = 2;
    public static int STRAIGHT = 3;
    public static int TRIPLE = 4;
    public static int STRAIGHT_BOMB = 5;
    public static int COLOR_BOMB = 6;

    private Array<Card> cards;
    private int type;

    /** The value of the combination.
     * Used to compare to same type combination
     * The value uses a *2 scalar to avoid float
     * usage with phoenix +0.5 rank */
    private int value;

    public CardCombination(int type, int value, Array<Card> sCards) {
        this.type = type;
        this.value = value;
        this.cards = sCards;
    }

    public int getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public Array<Card> getCards() {
        return cards;
    }

    public int getLength() {
        return cards.size;
    }

    public boolean isStrongerThan(CardCombination comb) {
        if (this.type == STRAIGHT_BOMB && comb.getType() == COLOR_BOMB) {
            return true;
        }
        if (this.type != comb.getType()) {
            return false;
        }
        return this.value > comb.getValue();
    }
}
