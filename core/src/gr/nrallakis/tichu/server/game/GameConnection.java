package gr.nrallakis.tichu.server.game;

/**
 * GameConnection is responsible for communicating with the game hosted on the server.
 * Note: All objects are passed using kryo serialization
 */
public interface GameConnection {

    void playCards(String playerId, CardCombination combination);

    void pass(String playerId);

    void callTichu(String playerId);

    void callGrandTichu(String playerId);

    void exchangeThreeCards(String playerId, Card left, Card top, Card right);

    void bomb(String playerId, CardCombination combination);

    void dealCardsLeft(String playerId);

    void receivedCards(String playerId);
}
