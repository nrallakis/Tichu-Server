package gr.nrallakis.tichu.server.game;

public enum Bet {
    NONE(0),
    TICHU(100),
    GRAND_TICHU(200);

    Bet(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }
}