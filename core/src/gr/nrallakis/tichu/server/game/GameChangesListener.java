package gr.nrallakis.tichu.server.game;

/**
 * GameChangesListener is responsible for communicating with the players of the game
 * informing them about all game state changes.
 */
public interface GameChangesListener {

    /**
     * Called when the player with turn passes.
     */
    void playerPassed();

    /**
     * Called at the first state of each round,
     * when all players have selected cards to be
     * exchanged.
     *
     * @param cards The cards to be received from
     *              the other players
     */
    void exchangedCardsReceived(Card[] cards);

    /**
     * Called when the player with the playerId
     * plays a bomb.
     */
    void playerBombed(String playerId, CardCombination bomb);

    /**
     * Called when the player with the playerId
     * calls Tichu.
     */
    void playerTichu(String playerId);

    /**
     * Called when the player with the playerId
     * calls Grand Tichu.
     */
    void playerGrandTichu(String playerId);

    /**
     * Called when the player with turn plays
     * cards.
     */
    void playerPlayedCards(CardCombination combination);

    /**
     * Called when the game round has ended.
     */
    void roundFinished(int[] teamsScores);

    /**
     * Called when a new game round starts.
     */
    void roundStarted();

    /**
     * Called when the game has ended.
     */
    void gameFinished(int[] teamsScores);

    /**
     * Called when the player with the playerId
     * leaves from the game.
     */
    void playerLeft(String playerId);

}
