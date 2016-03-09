package gr.nrallakis.tichu.server.game;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.esotericsoftware.kryonet.Connection;

import java.util.ArrayList;
import java.util.List;

import gr.nrallakis.tichu.core.AccountManager;
import gr.nrallakis.tichu.networking.GamePackets.PlayerBombed;
import gr.nrallakis.tichu.networking.GamePackets.PlayerGrandTichu;
import gr.nrallakis.tichu.networking.GamePackets.PlayerPassed;
import gr.nrallakis.tichu.networking.GamePackets.PlayerPlayed;
import gr.nrallakis.tichu.networking.GamePackets.PlayerTichu;

public class Player implements GamePlayer {

    private Connection connection;
    private String id;
    private GamePlayerUpdate lastGameUpdate;

    private boolean isReady;
    private List<Card> hand;
    private Bet bet;

    public Player(Connection connection) {
        this.connection = connection;
        this.id = connection.toString();
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
        return id;
    }

    public int getPoints() {
        return AccountManager.getInstance().getAccountRankPoints(id);
    }

    public String getName() {
        return AccountManager.getInstance().getAccountName(id);
    }

    public List<Card> getHand() {
        return hand;
    }

    @Override
    public void playerPassed() {
        lastGameUpdate = GamePlayerUpdate.PLAYER_PASSED;
        sendPacket(new PlayerPassed());
    }

    @Override
    public void exchangedCardsReceived(Card[] cards) {
        lastGameUpdate = GamePlayerUpdate.EXCHANGED_CARD_RECEIVED;
//        connection.se
    }

    @Override
    public void playerBombed(String playerId, CardCombination bomb) {
        lastGameUpdate = GamePlayerUpdate.PLAYER_BOMBED;
        PlayerBombed playerBombed = new PlayerBombed();
    }

    @Override
    public void playerTichu(String playerId) {
        lastGameUpdate = GamePlayerUpdate.PLAYER_TICHU;
        if (playerId.equals(this.id)) {
            setBet(Bet.TICHU);
        }
        PlayerTichu tichu = new PlayerTichu();
        tichu.playerId = playerId;
        sendPacket(tichu);
    }

    @Override
    public void playerGrandTichu(String playerId) {
        lastGameUpdate = GamePlayerUpdate.PLAYER_GRAND_TICHU;
        if (playerId.equals(this.id)) {
            setBet(Bet.GRAND_TICHU);
        }
        PlayerGrandTichu grandTichu = new PlayerGrandTichu();
        grandTichu.playerId = playerId;
        sendPacket(grandTichu);
    }

    @Override
    public void playerPlayedCards(CardCombination combination) {
        lastGameUpdate = GamePlayerUpdate.PLAYER_PLAYED_CARDS;
        PlayerPlayed cardsPlayed = new PlayerPlayed();
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
    public void playerLeft(String playerId) {
    }

    public boolean isReady() {
        return isReady;
    }

    public Bet getBet() {
        return bet;
    }

    public void setReady(boolean isReady) {
        this.isReady = isReady;
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

    public void sendPacket(Object packet) {
        connection.sendTCP(packet);
    }

    @Override
    public String toString() {
        return id;
    }
}
