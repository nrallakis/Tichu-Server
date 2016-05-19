package gr.nrallakis.tichu.server.game;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * Represents a single card with a rank and a type
 * Immutable class
 */
public final class Card implements KryoSerializable {

    public static final int BLACK = 0, BLUE = 1, GREEN = 2, RED = 3;
    public static final int J = 11, Q = 12, K = 13, A = 14;
    public static final int DRAGON = 10, PHOENIX = 11, DOGS = 12, MAHJONG = 13;

    private int rank;
    private int type;

    /** Kryo requires a non-arg constructor to proceed to serialization */
    private Card() {}

    public Card(int type, int rank) {
        this.type = type;
        this.rank = rank;
    }

    public boolean isSpecial() {
        return rank == 15;
    }

    public int getSpecialType() {
        if (rank != 15) return 0;
        if (type == GREEN) return DRAGON;
        if (type == RED) return PHOENIX;
        if (type == BLUE) return DOGS;
        if (type == BLACK) return MAHJONG;
        return 0;
    }

    public int getType() {
        return type;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Card card = (Card) obj;
        return (this.rank == card.rank) && (this.type == card.type);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + rank;
        result = prime * result + type;
        return result;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeInt(rank);
        output.writeInt(type);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        this.rank = input.readInt();
        this.type = input.readInt();
    }

    public static boolean isPhoenix(Card card) {
        return card.type == PHOENIX;
    }

    public static boolean isDragon(Card card) {
        return card.type == DRAGON;
    }

    public static boolean isDogs(Card card) {
        return card.type == DOGS;
    }

    public static boolean isMahjong(Card card) {
        return card.type == MAHJONG;
    }

    public boolean isStrongerThan(Card card) {
        if (card == null) return true;
        return rank > card.getRank();
    }

    @Override
    public String toString() {
        if (isPhoenix(this)) return "Phoenix";
        if (isDogs(this)) return "Dogs";
        if (isDragon(this)) return "Dragon";
        if (isMahjong(this)) return "Mahjong";
        switch (type) {
            case GREEN:
                return "Green-" + rank;
            case RED:
                return "Red-" + rank;
            case BLUE:
                return "Blue-" + rank;
            case BLACK:
                return "Black-" + rank;
        }
        return "";
    }
}
