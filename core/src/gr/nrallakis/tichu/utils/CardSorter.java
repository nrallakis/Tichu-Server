package gr.nrallakis.tichu.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import gr.nrallakis.tichu.server.game.Card;

public class CardSorter {

    public static Comparator<Card> byType;
    public static Comparator<Card> byRank;

    static {
        byType = new Comparator<Card>() {
            @Override
            public int compare(Card card, Card card2) {
                if (card.getType() > card2.getType()) return 1;
                if (card.getType() < card2.getType()) return -1;
                return 0;
            }
        };

        byRank = new Comparator<Card>() {
            @Override
            public int compare(Card card, Card card2) {
                if (card.getRank() > card2.getRank()) return 1;
                if (card.getRank() < card2.getRank()) return -1;
                return 0;
            }
        };
    }

    public static void sortByType(List<Card> cards) {
        Collections.sort(cards, byType);
    }

    public static void sortByRank(List<Card> cards) {
        Collections.sort(cards, byRank);
    }

}
