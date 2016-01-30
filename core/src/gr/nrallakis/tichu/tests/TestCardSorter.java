package gr.nrallakis.tichu.tests;

import com.badlogic.gdx.utils.Array;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gr.nrallakis.tichu.server.game.Card;
import gr.nrallakis.tichu.utils.CardSorter;

import static org.junit.Assert.assertEquals;

public class TestCardSorter {

    private Array<Card> cards;
    private Array<Card> sorted;

    @Before
    public void setUp() {
        cards = new Array<>();
        sorted = new Array<>();
    }

    @After
    public void cleanUp() {
        cards = null;
        sorted = null;
    }

    @Test
    public void testSortByRankGivenUnsortedCards() {
        cards.add(new Card(Card.BLUE, 7));
        cards.add(new Card(Card.BLUE, 4));
        cards.add(new Card(Card.BLUE, 6));
        cards.add(new Card(Card.BLUE, 5));
        cards.add(new Card(Card.BLUE, 8));

        CardSorter.sortByRank(cards);

        sorted.add(new Card(Card.BLUE, 4));
        sorted.add(new Card(Card.BLUE, 5));
        sorted.add(new Card(Card.BLUE, 6));
        sorted.add(new Card(Card.BLUE, 7));
        sorted.add(new Card(Card.BLUE, 8));

        assertEquals(sorted, cards);
    }

    @Test
    public void testSortByRankGivenPresortedCards() {
        cards.add(new Card(Card.BLUE, 4));
        cards.add(new Card(Card.BLUE, 5));
        cards.add(new Card(Card.BLUE, 6));
        cards.add(new Card(Card.BLUE, 7));
        cards.add(new Card(Card.BLUE, 8));

        sorted = cards;
        CardSorter.sortByRank(cards);

        assertEquals(sorted, cards);
    }

    @Test
    public void testSortByTypeGivenUnsortedCards() {
        cards.add(new Card(Card.BLACK, 5));
        cards.add(new Card(Card.BLUE, 7));
        cards.add(new Card(Card.RED, 8));

        CardSorter.sortByType(cards);

        sorted.add(new Card(Card.RED, 8));
        sorted.add(new Card(Card.BLUE, 7));
        sorted.add(new Card(Card.BLACK, 5));

        assertEquals(sorted, cards);
    }

    @Test
    public void testSortByTypeGivenSortedCards() {
        cards.add(new Card(Card.RED, 8));
        cards.add(new Card(Card.BLUE, 7));
        cards.add(new Card(Card.BLACK, 5));

        CardSorter.sortByType(cards);

        sorted.add(new Card(Card.RED, 8));
        sorted.add(new Card(Card.BLUE, 7));
        sorted.add(new Card(Card.BLACK, 5));

        assertEquals(sorted, cards);
    }

}
