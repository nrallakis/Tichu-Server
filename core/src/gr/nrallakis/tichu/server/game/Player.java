package gr.nrallakis.tichu.server.game;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryonet.Connection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gr.nrallakis.tichu.server.core.AccountManager;
import gr.nrallakis.tichu.server.lobby.Room;
import gr.nrallakis.tichu.server.utils.CardSorter;

public class Player implements KryoSerializable {

    private Connection connection;

    private Card[] exchangeCardsReceived;
    private List<Card> hand;
    private Bet bet;

    private boolean isReady;
    private boolean hasExchanged;
    private boolean hasReceivedCards;

    private Room roomJoinedTo;

    public Player(Connection connection) {
        this.connection = connection;
        this.bet = Bet.NONE;
        this.hand = new ArrayList<>(14);
        this.exchangeCardsReceived = new Card[3];
    }

    public String getId() {
        return connection.toString();
    }

    /** Warning this method does a query */
    public int getPoints() {
        return AccountManager.getInstance().getAccountRankPoints(getId());
    }

    /** Warning this method does a query */
    public String getName() {
        return AccountManager.getInstance().getAccountName(getId());
    }

    public List<Card> getHand() {
        return hand;
    }

    public void removeCards(Card...cards) {
        for (Card card : cards) {
            hand.remove(card);
        }
        CardSorter.sortByRank(hand);
    }

    public void joinRoom(Room room) {
        roomJoinedTo = room;
    }

    public boolean onRoom() {
        return roomJoinedTo == null;
    }

    public Room getRoomJoinedTo() {
        return roomJoinedTo;
    }

    public void leftFromRoom() {
        roomJoinedTo = null;
    }

    public boolean hasMahjong() {
        return hand.contains(new Card(Card.MAHJONG, 1));
    }

    public void sendPacket(Object packet) {
        if (connection == null) return;
        connection.sendTCP(packet);
    }

    public boolean hasCards(List<Card> cards) {
        return hand.containsAll(cards);
    }

    public void receivedExchangeCard(Card card, int index) {
        exchangeCardsReceived[index] = card;
        addCards(card);
    }

    public Card[] getExchangeCardsReceived() {
        return exchangeCardsReceived;
    }

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean isReady) {
        this.isReady = isReady;
    }

    public void addCards(Card...theCards) {
        hand.addAll(Arrays.asList(theCards));
        CardSorter.sortByRank(hand);
    }

    public void removeCards(List<Card> cards) {
        hand.removeAll(cards);
    }

    public void setHasExchanged(boolean hasExchanged) {
        this.hasExchanged = hasExchanged;
    }

    public boolean hasExchanged() {
        return hasExchanged;
    }

    public void setHasReceivedCards(boolean hasReceivedCards) {
        this.hasReceivedCards = hasReceivedCards;
    }

    public boolean hasReceivedCards() {
        return hasReceivedCards;
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeString(getName());
        output.writeString(getId());
        output.writeInt(getPoints());
    }

    @Override
    public void read(Kryo kryo, Input input) {}

    public String printCards() {
        StringBuilder string = new StringBuilder();
        for (Card card : hand) {
            string.append(card.toString());
            string.append(" , ");
        }
        System.out.println("Player: " + getId() + " has cards: " + string);
        return string.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Player)) return false;
        if (!(other.getClass().equals(this.getClass()))) return false;
        Player otherPlayer = (Player) other;
        return getId().equals(otherPlayer.getId());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + getId().length();
        return result;
    }
}
