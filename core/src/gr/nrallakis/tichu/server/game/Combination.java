package gr.nrallakis.tichu.server.game;

import com.badlogic.gdx.utils.Array;

public final class Combination {

    private static int PAIR = 0,
                       FULLHOUSE = 1,
                       STEPS = 2,
                       QAEDA = 3,
                       TRIPLE = 4,
                       BOMB = 5;

    private Array<Card> cards;
    private int type;
    private int value;

    /* The value of the Combination
    * A combination with a greater value
    * is a greater combination. */

    public Combination(int type, int value, Array<Card> sCards) {
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

    public boolean isStrongerThan(Combination comb) {
        if (this.type != comb.getType()) return false;
        return this.value > comb.getValue();
    }
}
