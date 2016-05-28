package gr.nrallakis.tichu.server.game;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a combination of cards.
 */
public final class CardCombination implements KryoSerializable {

    public static int SINGLE = 0;
    public static int PAIR = 1;
    public static int FULL_HOUSE = 2;
    public static int STRAIGHT_PAIRS = 3;
    public static int STRAIGHT = 4;
    public static int TRIPLE = 5;
    public static int STRAIGHT_BOMB = 6;
    public static int COLOR_BOMB = 7;

    private List<Card> cards;
    private int type;

    /**
     * The value of the combination.
     * Used to compare combinations of the same type
     * The value uses a *2 scalar to avoid float
     * usage with phoenix +0.5 rank
     */
    private int value;

    /**
     * Private constructor used during kryo serialization
     */
    private CardCombination() {
    }

    public CardCombination(int type, int value, List<Card> sCards) {
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

    public List<Card> getCards() {
        return cards;
    }

    /**
     * Compares this combination of cards with the specified
     *
     * @param comb the combination to compare to
     * @return Returns whether it is stronger or not
     */
    public boolean isStrongerThan(CardCombination comb) {
        /** Every combination is stronger than empty combination*/
        if (comb == null) {
            return true;
        }

        /** Straight bombs are stronger than color bombs*/
        if (this.type == STRAIGHT_BOMB && comb.getType() == COLOR_BOMB) {
            return true;
        }

        /** Having dealt with bombs, every different type isn't stronger */
        if (this.type != comb.getType()) {
            return false;
        }

        /** If the combination is a single card, compare the single cards */
        if (comb.getCards().size() == 1) {
            Card card = cards.get(0);
            Card other = comb.getCards().get(0);
            return card.isStrongerThan(other);
        }
        return this.value > comb.getValue();
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeInt(type);
        output.writeInt(value);
        kryo.writeClassAndObject(output, cards.toArray(new Card[cards.size()]));
    }

    @Override
    public void read(Kryo kryo, Input input) {
        this.type = input.readInt();
        this.value = input.readInt();
        this.cards = new ArrayList<>(Arrays.asList((Card[]) kryo.readClassAndObject(input)));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Card card : cards) {
            builder.append(card);
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CardCombination)) return false;
        CardCombination other = (CardCombination) obj;
        return value == other.getValue()
                && type == other.getType()
                && cards.equals(other.getCards());
    }
}
