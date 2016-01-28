package gr.nrallakis.tichu.utils;

import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

import gr.nrallakis.tichu.server.game.Card;

public class CardSorter {

    public static Comparator<Card> byType;
    public static Comparator<Card> byRank;

    static {
        byType = (card, card2) -> {
            if (card.getType() > card2.getType()) return 1;
            if (card.getType() < card2.getType()) return -1;
            return 0;
        };

        byRank = (card, card2) -> {
            if (card.getRank() > card2.getRank()) return 1;
            if (card.getRank() < card2.getRank()) return -1;
            return 0;
        };
    }

    public static void sortByType(Array<Card> cards) {
        cards.sort(byType);
    }

    public static void sortByRank(Array<Card> cards) {
        cards.sort(byRank);
    }

}
