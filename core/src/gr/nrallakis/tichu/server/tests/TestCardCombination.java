package gr.nrallakis.tichu.server.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import gr.nrallakis.tichu.server.game.Card;
import gr.nrallakis.tichu.server.game.CardCombination;

import static org.junit.Assert.assertEquals;

public class TestCardCombination {

    ArrayList<Card> cards;
    ArrayList<Card> cards2;
    CardCombination one;
    CardCombination two;

    @Before
    public void setUp() {
        cards = new ArrayList<>();
        cards2 = new ArrayList<>();
    }

    @After
    public void cleanUp() {
        cards = null;
        cards2 = null;
        one = null;
        two = null;
    }

    @Test
    public void testIsStrongerWithSameValueCombinations() {
        cards.add(new Card(Card.BLACK, 2));
        cards.add(new Card(Card.RED, 2));

        cards2.add(new Card(Card.GREEN, 2));
        cards2.add(new Card(Card.BLUE, 2));

        one = new CardCombination(CardCombination.PAIR, cards.get(0).getRank(), cards);
        two = new CardCombination(CardCombination.PAIR, cards2.get(0).getRank(), cards2);

        assertEquals(false, one.isStrongerThan(two));
        assertEquals(false, two.isStrongerThan(one));
    }

    @Test
    public void testIsStrongerCombinationFullHouse() {
        cards.add(new Card(Card.BLACK, 2));
        cards.add(new Card(Card.RED, 2));
        cards.add(new Card(Card.RED, 4));
        cards.add(new Card(Card.BLACK, 4));
        cards.add(new Card(Card.BLUE, 4));

        cards2.add(new Card(Card.GREEN, 2));
        cards2.add(new Card(Card.BLUE, 2));
        cards2.add(new Card(Card.RED, 2));
        cards2.add(new Card(Card.BLACK, 4));
        cards2.add(new Card(Card.BLUE, 4));

        one = new CardCombination(CardCombination.PAIR, cards.get(2).getRank(), cards);
        two = new CardCombination(CardCombination.PAIR, cards2.get(0).getRank(), cards2);

        assertEquals(true, one.isStrongerThan(two));
        assertEquals(false, two.isStrongerThan(one));
    }

    @Test
    public void testIsStrongerCombinationColorBombSameType() {
        cards.add(new Card(Card.BLACK, 3));
        cards.add(new Card(Card.RED, 3));
        cards.add(new Card(Card.BLUE, 3));
        cards.add(new Card(Card.GREEN, 3));

        cards2.add(new Card(Card.BLACK, 4));
        cards2.add(new Card(Card.RED, 4));
        cards2.add(new Card(Card.BLUE, 4));
        cards2.add(new Card(Card.GREEN, 4));

        one = new CardCombination(CardCombination.COLOR_BOMB, cards.get(0).getRank(), cards);
        two = new CardCombination(CardCombination.COLOR_BOMB, cards2.get(0).getRank(), cards2);

        assertEquals(false, one.isStrongerThan(two));
        assertEquals(true, two.isStrongerThan(one));
    }

    @Test
    public void testIsStrongerCombinationStraightBombSameType() {
        cards.add(new Card(Card.BLACK, 3));
        cards.add(new Card(Card.BLACK, 4));
        cards.add(new Card(Card.BLACK, 5));
        cards.add(new Card(Card.BLACK, 6));
        cards.add(new Card(Card.BLACK, 7));

        cards2.add(new Card(Card.RED, 4));
        cards2.add(new Card(Card.RED, 5));
        cards2.add(new Card(Card.RED, 6));
        cards2.add(new Card(Card.RED, 7));
        cards2.add(new Card(Card.RED, 8));

        one = new CardCombination(CardCombination.STRAIGHT_BOMB, cards.get(0).getRank(), cards);
        two = new CardCombination(CardCombination.STRAIGHT_BOMB, cards2.get(0).getRank(), cards2);

        assertEquals(false, one.isStrongerThan(two));
        assertEquals(true, two.isStrongerThan(one));
    }

    @Test
    public void testIsStrongerCombinationBombDifferentType() {
        cards.add(new Card(Card.BLACK, 3));
        cards.add(new Card(Card.BLACK, 4));
        cards.add(new Card(Card.BLACK, 5));
        cards.add(new Card(Card.BLACK, 6));
        cards.add(new Card(Card.BLACK, 7));

        cards2.add(new Card(Card.GREEN, 8));
        cards2.add(new Card(Card.BLUE, 8));
        cards2.add(new Card(Card.RED, 8));
        cards2.add(new Card(Card.BLACK, 8));

        one = new CardCombination(CardCombination.STRAIGHT_BOMB, cards.get(0).getRank(), cards);
        two = new CardCombination(CardCombination.COLOR_BOMB, cards2.get(0).getRank(), cards2);

        assertEquals(true, one.isStrongerThan(two));
        assertEquals(false, two.isStrongerThan(one));
    }

    @Test
    public void testIsStrongerCombinationStraightPairs() {
        cards.add(new Card(Card.BLACK, 2));
        cards.add(new Card(Card.RED, 2));
        cards.add(new Card(Card.BLACK, 3));
        cards.add(new Card(Card.RED, 3));
        cards.add(new Card(Card.RED, 4));
        cards.add(new Card(Card.BLACK, 4));

        cards2.add(new Card(Card.BLACK, 3));
        cards2.add(new Card(Card.RED, 3));
        cards2.add(new Card(Card.RED, 4));
        cards2.add(new Card(Card.BLACK, 4));
        cards2.add(new Card(Card.RED, 5));
        cards2.add(new Card(Card.BLACK, 5));

        one = new CardCombination(CardCombination.STRAIGHT_PAIRS, cards.get(0).getRank(), cards);
        two = new CardCombination(CardCombination.STRAIGHT_PAIRS, cards2.get(0).getRank(), cards2);

        assertEquals(false, one.isStrongerThan(two));
        assertEquals(true, two.isStrongerThan(one));
    }
}