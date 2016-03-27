package gr.nrallakis.tichu.server.game;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.esotericsoftware.kryonet.Connection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gr.nrallakis.tichu.server.core.AccountManager;
import gr.nrallakis.tichu.server.networking.GamePackets;

public class Player implements GamePlayer {

    private Connection connection;
    private GamePlayerUpdate lastGameUpdate;

    private boolean isReady;
    private List<Card> hand;
    private Bet bet;

    public Player(Connection connection) {
        this.connection = connection;
        this.bet = Bet.NONE;
        this.hand = new ArrayList<>(14);
    }

    public String toJson() {
        Json json = new Json();
        json.setSerializer(Player.class, new Json.Serializer<Player>() {
            @Override
            public void write(Json json, Player player, @SuppressWarnings("rawtypes") Class knownType) {
                json.writeObjectStart();
                json.writeValue("name", player.getName());
                json.writeValue("points", player.getPoints());
                json.writeValue("id", player.getId());
                json.writeObjectEnd();
            }

            @Override
            public Player read(Json json, JsonValue jsonData, @SuppressWarnings("rawtypes") Class type) {
                return null;
            }
        });
        return json.toJson(this);
    }

    public String getId() {
        return connection.toString();
    }

    public int getPoints() {
        return AccountManager.getInstance().getAccountRankPoints(getId());
    }

    public String getName() {
        return AccountManager.getInstance().getAccountName(getId());
    }

    public List<Card> getHand() {
        return hand;
    }

    public boolean hasMahjong() {
        return hand.contains(new Card(Card.MAHJONG, 1));
    }

    public void sendPacket(Object packet) {
        connection.sendTCP(packet);
    }

    public boolean hasCards(List<Card> cards) {
        return hand.containsAll(cards);
    }

    @Override
    public void playerPassed() {
        lastGameUpdate = GamePlayerUpdate.PLAYER_PASSED;
        sendPacket(new GamePackets.PlayerPassed());
    }

    @Override
    public void exchangedCardsReceived(Card[] cards) {
        lastGameUpdate = GamePlayerUpdate.EXCHANGED_CARD_RECEIVED;
//        connection.se
    }

    @Override
    public void playerBombed(String playerId, CardCombination bomb) {
        lastGameUpdate = GamePlayerUpdate.PLAYER_BOMBED;
        GamePackets.PlayerBombed playerBombed = new GamePackets.PlayerBombed();
    }

    @Override
    public void playerTichu(String playerId) {
        lastGameUpdate = GamePlayerUpdate.PLAYER_TICHU;
        if (playerId.equals(getId())) {
            setBet(Bet.TICHU);
        }
        GamePackets.PlayerTichu tichu = new GamePackets.PlayerTichu();
        tichu.playerId = playerId;
        sendPacket(tichu);
    }

    @Override
    public void playerGrandTichu(String playerId) {
        lastGameUpdate = GamePlayerUpdate.PLAYER_GRAND_TICHU;
        if (playerId.equals(getId())) {
            setBet(Bet.GRAND_TICHU);
        }
        GamePackets.PlayerGrandTichu grandTichu = new GamePackets.PlayerGrandTichu();
        grandTichu.playerId = playerId;
        sendPacket(grandTichu);
    }

    @Override
    public void playerPlayedCards(CardCombination combination) {
        lastGameUpdate = GamePlayerUpdate.PLAYER_PLAYED_CARDS;
        GamePackets.PlayerPlayed cardsPlayed = new GamePackets.PlayerPlayed();
        cardsPlayed.cardCombination = combination;
        sendPacket(cardsPlayed);
    }

    @Override
    public void roundFinished(int[] teamsScores) {
        lastGameUpdate = GamePlayerUpdate.ROUND_FINISHED;
    }

    @Override
    public void roundStarted() {
        lastGameUpdate = GamePlayerUpdate.ROUND_STARTED;
    }

    @Override
    public void gameFinished(int[] teamsScores) {
        lastGameUpdate = GamePlayerUpdate.GAME_FINISHED;
    }

    @Override
    public void playerLeft(String playerId) {}

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    public Connection getConnection() {
        return connection;
    }

    public GamePlayerUpdate getLastGameUpdate() {
        return lastGameUpdate;
    }

    public boolean isNotReady() {
        return !isReady;
    }

    public void setReady(boolean isReady) {
        this.isReady = isReady;
    }

    public void addCards(Card...theCards) {
        hand.addAll(Arrays.asList(theCards));
    }

    public void removeCards(List<Card> cards) {
        hand.removeAll(cards);
    }
}
