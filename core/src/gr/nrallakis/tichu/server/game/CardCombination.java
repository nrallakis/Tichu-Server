package gr.nrallakis.tichu.server.game;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CardCombination implements KryoSerializable {

    public static int PAIR = 0;
    public static int FULL_HOUSE = 1;
    public static int STRAIGHT_PAIRS = 2;
    public static int STRAIGHT = 3;
    public static int TRIPLE = 4;
    public static int STRAIGHT_BOMB = 5;
    public static int COLOR_BOMB = 6;

    private List<Card> cards;
    private int type;

    /** The value of the combination.
     * Used to compare to same type combination
     * The value uses a *2 scalar to avoid float
     * usage with phoenix +0.5 rank */
    private int value;

    private CardCombination() {}

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

    public int getLength() {
        return cards.size();
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
}
