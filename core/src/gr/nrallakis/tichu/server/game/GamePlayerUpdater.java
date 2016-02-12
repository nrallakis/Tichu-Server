package gr.nrallakis.tichu.server.game;

import com.badlogic.gdx.utils.Array;

public class GamePlayerUpdater {

    private Array<GamePlayer> gamePlayers;

    public GamePlayerUpdater(Array<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public void dealEightCards() {
    }

    /**
     * Calls {@link GamePlayer#playerPassed()} on each remote
     * game observer registered.
     */
    public void playerPassed() {
    }

    /**
     * Calls {@link GamePlayer#exchangedCardsReceived(Card[])} on each remote
     * game observer registered.
     */
    public void exchangedCardsReceived(Card[] cards) {
    }

    /**
     * Calls {@link GamePlayer#playerBombed(String, CardCombination)} on each remote
     * game observer registered.
     */
    public void playerBombed(String playerId, CardCombination bomb) {
    }

    /**
     * Calls {@link GamePlayer#playerTichu(String)} on each remote
     * game observer registered.
     */
    public void playerTichu(String playerId) {
    }

    /**
     * Calls {@link GamePlayer#playerGrandTichu(String)} on each remote
     * game observer registered.
     */
    public void playerGrandTichu(String playerId) {
    }

    /**
     * Calls {@link GamePlayer#playerPlayedCards(CardCombination)} on each remote
     * game observer registered.
     */
    public void playerPlayedCards(CardCombination combination) {
    }

    /**
     * Calls {@link GamePlayer#roundFinished(int[])} on each remote
     * game observer registered.
     */
    public void roundFinished(int[] teamsScores) {
    }

    /**
     * Calls {@link GamePlayer#roundStarted()} on each remote
     * game observer registered.
     */
    public void roundStarted() {
    }

    /**
     * Calls {@link GamePlayer#gameFinished(int[])} on each remote
     * game observer registered.
     */
    public void gameFinished(int[] teamsScores) {
    }

    /**
     * Calls {@link GamePlayer#playerLeft(String)} on each remote
     * game observer registered.
     */
    public void playerLeft(String playerId) {
    }

    public void broadcast(Object packet) {
        for (GamePlayer gamePlayer : gamePlayers) {
            gamePlayer.getConnection().sendTCP(packet);
        }
    }

}
