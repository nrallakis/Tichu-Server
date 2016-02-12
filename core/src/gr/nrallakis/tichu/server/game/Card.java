package gr.nrallakis.tichu.server.game;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * Represents a single card with a rank and a type
 * Immutable class
 */

public final class Card implements Comparable<Card>, KryoSerializable {

    public static final int GREEN = 0, RED = 1, BLUE = 2, BLACK = 3;
    public static final int J = 11, Q = 12, K = 13, A = 14;
    public static final int DRAGON = 10, PHOENIX = 11, DOGS = 12, MAHJONG = 13;

    private int rank;
    private int type;

    private Card() {}

    public Card(int special) {
        this(special, 15);
    }

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
    public int compareTo(Card c) {
        if (this.rank > c.getRank()) return 1;
        if (this.rank < c.getRank()) return -1;
        if (this.rank == c.getRank() && this.type < c.getType()) return -1;
        if (this.rank == c.getRank() && this.type > c.getType()) return 1;
        return 0;
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
}
