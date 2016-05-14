package gr.nrallakis.tichu.server.game;

import com.badlogic.gdx.Gdx;

import gr.nrallakis.tichu.server.networking.GamePackets.*;

public class GamePlayerUpdater {

    private Player[] players;

    public GamePlayerUpdater(Player[] players) {
        this.players = players;
    }

    /**
     * Sends a playerPassed packet to each player
     */
    public void playerPassed() {
         broadcast(new PlayerPassed());
    }

    /**
     * Sends a playerBombed packet to each player
     */
    public void playerBombed(String playerId, CardCombination bomb) {
        PlayerBombed packet = new PlayerBombed();
        packet.playerId = playerId;
        packet.bomb = bomb;
        broadcast(packet);
    }

    /**
     * Sends a playerTichu packet to each player
     */
    public void playerTichu(String playerId) {
        PlayerTichu packet = new PlayerTichu();
        packet.playerId = playerId;
        broadcast(packet);
    }

    /**
     * Sends a playerGrandTichu packet to each player
     */
    public void playerGrandTichu(String playerId) {
        PlayerGrandTichu packet = new PlayerGrandTichu();
        packet.playerId = playerId;
        broadcast(packet);
    }

    /**
     * Sends a playerPlayedCards packet to each player
     */
    public void playerPlayedCards(CardCombination combination) {
        PlayerPlayed packet = new PlayerPlayed();
        packet.cardCombination = combination;
        broadcast(packet);
    }

    /**
     * Calls {@link Player#roundFinished(int[])} on each remote
     * game observer registered.
     */
    public void roundFinished(int[] teamsScores) {
        for (Player player : players) {
            player.roundFinished(teamsScores);
        }
    }

    /**
     * Calls {@link Player#roundStarted()} on each remote
     * game observer registered.
     */
    public void roundStarted() {
        for (Player player : players) {
            player.roundStarted();
        }
    }

    /**
     * Calls {@link Player#gameFinished(int[])} on each remote
     * game observer registered.
     */
    public void gameFinished(int[] teamsScores) {
        for (Player player : players) {
            player.gameFinished(teamsScores);
        }
    }

    /**
     * Sends a playerHandsSize packet to each player
     */
    public void playerHandSize(String playerId, int size) {
        PlayerDealtCards packet = new PlayerDealtCards();
        packet.playerId = playerId;
        broadcast(packet);
    }

    /**
     * Calls {@link Player#playerLeft(String)} on each remote
     * game observer registered.
     */
    public void playerLeft(String playerId) {
        for (Player player : players) {
            player.playerLeft(playerId);
        }
    }

    public void broadcast(Object packet) {
        for (Player Player : players) {
            Player.getConnection().sendTCP(packet);
        }
    }

    public void playerPlaysFirst(String playerId) {
        Gdx.app.log("SERVER", "SENDING WHO PLAYS FIRST PACKET: " + playerId);
        PlayerToPlayFirst packet = new PlayerToPlayFirst();
        packet.playerId = playerId;
        broadcast(packet);
    }
}
