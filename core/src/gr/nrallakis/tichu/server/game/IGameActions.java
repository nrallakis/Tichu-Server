package gr.nrallakis.tichu.server.game;

/**
 * IGameActions is responsible for communicating with the players of the game.
 * Note: All objects are passed as json strings
 */
public interface IGameActions {

    /**
     * The cards played by the player with turn
     */
    void cardsPlayed(String cards);

    void pass(String playerId);

    void gameFinished();

    void roundFinished();

    void nextRound();

}
