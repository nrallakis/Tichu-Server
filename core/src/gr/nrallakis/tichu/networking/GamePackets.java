package gr.nrallakis.tichu.networking;

import gr.nrallakis.tichu.server.game.CardCombination;

public class GamePackets {

    public static class PlayerGrandTichu {

    }

    public static class PlayerTichu {

    }

    public static class PlayerPassed {

    }

    public static class PlayerPlayed {
        CardCombination cardCombination;
    }
}
