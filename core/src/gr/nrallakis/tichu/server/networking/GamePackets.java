package gr.nrallakis.tichu.server.networking;

import gr.nrallakis.tichu.server.game.Card;
import gr.nrallakis.tichu.server.game.CardCombination;

public class GamePackets {

    public static class PlayerGrandTichu {
        public String playerId;
    }

    public static class PlayerTichu {
        public String playerId;
    }

    public static class PlayerPassed {}

    public static class PlayerPlayed {
        public CardCombination cardCombination;
    }

    public static class GiveCards {
        public Card[] cards;
    }

    public static class PlayerBombed {
        public String playerId;
    }

    public static class PlayerToPlayFirst {
        public String playerId;
    }

}
