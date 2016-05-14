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
        for (int type = 0; type < 4; type++) {
            for (int rank = 2; rank < 15; rank++) {
                cards.add(new Card(type, rank));
            }
        }
    }

    private void addSpecialCards() {
        cards.add(new Card(Card.DOGS, 1));
        cards.add(new Card(Card.MAHJONG, 1));
        cards.add(new Card(Card.PHOENIX, 0));
        cards.add(new Card(Card.DRAGON, 25));
    }

    private void shuffle() {
        Collections.shuffle(cards);
    }

    public Card[] drawSixCards() {
        return drawCards(6);
    }

    public Card[] drawEightCards() {
        return drawCards(8);
    }

    private Card[] drawCards(int count) {
        Card[] cards = new Card[count];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = drawCard();
        }
        return cards;
    }

    public Card drawCard() {
        return cards.remove(0);
    }
}
