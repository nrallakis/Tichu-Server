package gr.nrallakis.tichu.server.game;

import com.badlogic.gdx.utils.Array;

public final class CardCombination {

    public static int PAIR = 0,
                       FULLHOUSE = 1,
                       STRAIGHT_PAIRS = 2,
                       STRAIGHT = 3,
                       TRIPLE = 4,
                       BOMB = 5;

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

    public boolean isStrongerThan(CardCombination comb) {
        if (this.type != comb.getType()) return false;
        return this.value > comb.getValue();
    }
}
