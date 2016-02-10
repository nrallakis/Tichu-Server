package gr.nrallakis.tichu.server.game;

import com.badlogic.gdx.utils.Array;

public class RemoteGameObserverUpdater {

    Array<RemoteGameObserver> gameObservers;

    public RemoteGameObserverUpdater(Array<RemoteGameObserver> gameObservers) {
        this.gameObservers = gameObservers;
    }

    /**
     * Calls {@link RemoteGameObserver#playerPassed()} on each remote
     * game observer registered.
     */
    public void playerPassed() {
        for (RemoteGameObserver rgb : gameObservers) {
            rgb.playerPassed();
        }
    }

    /**
     * Calls {@link RemoteGameObserver#exchangedCardsReceived(Card[])} on each remote
     * game observer registered.
     */
    public void exchangedCardsReceived(Card[] cards) {
        for (RemoteGameObserver rgb : gameObservers) {
            rgb.exchangedCardsReceived(cards);
        }
    }

    /**
     * Calls {@link RemoteGameObserver#playerBombed(String, CardCombination)} on each remote
     * game observer registered.
     */
    public void playerBombed(String playerId, CardCombination bomb) {
        for (RemoteGameObserver rgb : gameObservers) {
            rgb.playerBombed(playerId, bomb);
        }
    }

    /**
     * Calls {@link RemoteGameObserver#playerTichu(String)} on each remote
     * game observer registered.
     */
    public void playerTichu(String playerId) {
        for (RemoteGameObserver rgb : gameObservers) {
            rgb.playerTichu(playerId);
        }
    }

    /**
     * Calls {@link RemoteGameObserver#playerGrandTichu(String)} on each remote
     * game observer registered.
     */
    public void playerGrandTichu(String playerId) {
        for (RemoteGameObserver rgb : gameObservers) {
            rgb.playerGrandTichu(playerId);
        }
    }

    /**
     * Calls {@link RemoteGameObserver#playerPlayedCards(CardCombination)} on each remote
     * game observer registered.
     */
    public void playerPlayedCards(CardCombination combination) {
        for (RemoteGameObserver rgb : gameObservers) {
            rgb.playerPlayedCards(combination);
        }
    }

    /**
     * Calls {@link RemoteGameObserver#roundFinished(int[])} on each remote
     * game observer registered.
     */
    public void roundFinished(int[] teamsScores) {
        for (RemoteGameObserver rgb : gameObservers) {
            rgb.roundFinished(teamsScores);
        }
    }

    /**
     * Calls {@link RemoteGameObserver#roundStarted()} on each remote
     * game observer registered.
     */
    public void roundStarted() {
        for (RemoteGameObserver rgb : gameObservers) {
            rgb.roundStarted();
        }
    }

    /**
     * Calls {@link RemoteGameObserver#gameFinished(int[])} on each remote
     * game observer registered.
     */
    public void gameFinished(int[] teamsScores) {
        for (RemoteGameObserver rgb : gameObservers) {
            rgb.gameFinished(teamsScores);
        }
    }

    /**
     * Calls {@link RemoteGameObserver#playerLeft(String)} on each remote
     * game observer registered.
     */
    public void playerLeft(String playerId) {
        for (RemoteGameObserver rgb : gameObservers) {
            rgb.playerLeft(playerId);
        }
    }

}
