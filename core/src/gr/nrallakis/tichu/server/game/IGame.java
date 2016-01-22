package gr.nrallakis.tichu.server.game;

/**
 * IGame is responsible for communicating with the game hosted on the server.
 * Note: All objects are passed as json strings
 */
public interface IGame {

    void pass(String playerId);

    /**
     * Gives cards to the players, the first card
     * is given to the left player, the second to the top
     * player and the third to the right player.
     * Called at the start of every round.
     */
    void giveCards(String threeCards);

    void playCards(String playerId, String comb);

    /**
     * Called only when the player exits from the game and not the app
     */
    void leave(String playerId);

}
