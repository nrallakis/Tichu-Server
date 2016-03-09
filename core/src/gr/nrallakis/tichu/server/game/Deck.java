package gr.nrallakis.tichu.server.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>(56);
        addNormalCards();
        addSpecialCards();
        shuffle();
    }

    private void addNormalCards() {
        for (int type = 0; type < 3; type++) {
            for (int rank = 2; rank < 14; rank++) {
                cards.add(new Card(type, rank));
            }
        }
    }

    private void addSpecialCards() {
        cards.add(new Card(Card.DOGS, 0));
        cards.add(new Card(Card.MAHJONG, 1));
        cards.add(new Card(Card.PHOENIX, -1));
        cards.add(new Card(Card.DRAGON, 25));
    }

    private void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.remove(0);
    }

    public Card[] drawSixCards() {
        Card[] sixCards = new Card[6];
        for (int i = 0; i < sixCards.length; i++) {
            sixCards[i] = drawCard();
        }
        return sixCards;
    }
}
