package gr.nrallakis.tichu.server.game;

import com.badlogic.gdx.utils.Array;

public class Deck {

    private Array<Card> cards;

    public Deck() {
        cards = new Array<Card>(56);

        for (int type = 0; type < 3; type++) {
            for (int rank = 2; rank < 14; rank++) {
                cards.add(new Card(type, rank));
            }
        }
        //Add the special cards
        cards.add(new Card(Card.DOGS));
        cards.add(new Card(Card.MAHJONG));
        cards.add(new Card(Card.PHOENIX));
        cards.add(new Card(Card.DRAGON));

        cards.shuffle();
    }

    public Card drawCard() {
        return cards.removeIndex(0);
    }
}
