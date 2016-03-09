package gr.nrallakis.tichu.server.game;

public class GamePlayerUpdater {

    private GamePlayer[] gamePlayers;

    public GamePlayerUpdater(GamePlayer[] gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    /**
     * Calls {@link GamePlayer#playerPassed()} on each remote
     * game observer registered.
     */
    public void playerPassed() {
        for (GamePlayer player : gamePlayers) {
            player.playerPassed();
        }
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
        for (GamePlayer player : gamePlayers) {
            player.playerBombed(playerId, bomb);
        }
    }

    /**
     * Calls {@link GamePlayer#playerTichu(String)} on each remote
     * game observer registered.
     */
    public void playerTichu(String playerId) {
        for (GamePlayer player : gamePlayers) {
            player.playerTichu(playerId);
        }
    }

    /**
     * Calls {@link GamePlayer#playerGrandTichu(String)} on each remote
     * game observer registered.
     */
    public void playerGrandTichu(String playerId) {
        for (GamePlayer player : gamePlayers) {
            player.playerGrandTichu(playerId);
        }
    }

    /**
     * Calls {@link GamePlayer#playerPlayedCards(CardCombination)} on each remote
     * game observer registered.
     */
    public void playerPlayedCards(CardCombination combination) {
        for (GamePlayer player : gamePlayers) {
            player.playerPlayedCards(combination);
        }
    }

    /**
     * Calls {@link GamePlayer#roundFinished(int[])} on each remote
     * game observer registered.
     */
    public void roundFinished(int[] teamsScores) {
        for (GamePlayer player : gamePlayers) {
            player.roundFinished(teamsScores);
        }
    }

    /**
     * Calls {@link GamePlayer#roundStarted()} on each remote
     * game observer registered.
     */
    public void roundStarted() {
        for (GamePlayer player : gamePlayers) {
            player.roundStarted();
        }
    }

    /**
     * Calls {@link GamePlayer#gameFinished(int[])} on each remote
     * game observer registered.
     */
    public void gameFinished(int[] teamsScores) {
        for (GamePlayer player : gamePlayers) {
            player.gameFinished(teamsScores);
        }
    }

    /**
     * Calls {@link GamePlayer#playerLeft(String)} on each remote
     * game observer registered.
     */
    public void playerLeft(String playerId) {
        for (GamePlayer player : gamePlayers) {
            player.playerLeft(playerId);
        }
    }

    public void broadcast(Object packet) {
        for (GamePlayer gamePlayer : gamePlayers) {
            gamePlayer.getConnection().sendTCP(packet);
        }
    }

}
