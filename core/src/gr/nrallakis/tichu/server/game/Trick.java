package gr.nrallakis.tichu.server.game;

import java.util.ArrayList;
import java.util.List;

import gr.nrallakis.tichu.server.utils.CardCombinationUtils;

public class Trick {

    private List<Card> cards;

    public Trick() {
        cards = new ArrayList<>();
    }

    public void addCombination(CardCombination combination) {
        cards.addAll(combination.getCards());
    }

    public int value() {
        return CardCombinationUtils.calculatePoints(cards);
    }
}
