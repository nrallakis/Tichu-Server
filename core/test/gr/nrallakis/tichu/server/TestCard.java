package gr.nrallakis.tichu.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gr.nrallakis.tichu.server.game.Card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestCard {

    private Card card;
    private Card card2;
    private Card card3;

    @Before
    public void setUp() {
        card = new Card(Card.BLACK, 3);
        card2 = new Card(Card.BLACK, 3);
        card3 = new Card(Card.RED, 3);
    }

    @After
    public void cleanUp() {
        card = null;
        card2 = null;
        card3 = null;
    }

    @Test
    public void testEqualsAssertive() {
        assertTrue(card.equals(card));
    }

    @Test
    public void testEqualsSymmetric() {
        assertTrue(card.equals(card2));
        assertTrue(card2.equals(card));
    }

    @Test
    public void testEquals() {
        assertFalse(card.equals(card3));
        assertFalse(card2.equals(card3));
    }

    @Test
    public void testNamingGreen() {
        card = new Card(Card.GREEN, 5);
        assertEquals("Green-5", card.toString());
    }

    @Test
    public void testNamingRed() {
        card = new Card(Card.RED, 7);
        assertEquals("Red-7", card.toString());
    }

    @Test
    public void testNamingBlue() {
        card = new Card(Card.BLUE, 3);
        assertEquals("Blue-3", card.toString());
    }

    @Test
    public void testNamingBlack() {
        card = new Card(Card.BLACK, 2);
        assertEquals("Black-2", card.toString());
    }
}
