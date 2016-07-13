package gr.nrallakis.tichu.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import gr.nrallakis.tichu.server.game.Card;
import gr.nrallakis.tichu.server.game.CardCombination;
import gr.nrallakis.tichu.server.game.Trick;

import static org.junit.Assert.assertEquals;


public class TrickTest {

    Trick trick;
    ArrayList<Trick> tricks;

    @Before
    public void setUp() {
        trick = new Trick();
        tricks = new ArrayList<>();
    }

    @After
    public void cleanUp() {
        trick = null;
        tricks = null;
    }

    @Test
    public void testValueOfTrick() {
        Card[] cards = new Card[] { new Card(0, 5), new Card(0, 6), new Card(0, 8), new Card(0, 10), new Card(0, Card.K) }; //Value = 25
        CardCombination combination = new CardCombination(0, 0, Arrays.asList(cards));
        trick.addCombination(combination);
        assertEquals(25, trick.value());
    }

    @Test
    public void testValueOfNoValueTrick() {
        Card[] cards = new Card[] {  new Card(0, 6), new Card(0, 8) }; //Value = 0
        CardCombination combination = new CardCombination(0, 0, Arrays.asList(cards));
        trick.addCombination(combination);
        assertEquals(0, trick.value());
    }

    @Test
    public void testValueOfMultipleTricks() {
        Card[] cards = new Card[] { new Card(0, 5), new Card(0, 6), new Card(0, 8), new Card(0, 10), new Card(0, Card.K) }; //Value = 25
        Card[] cards2 = new Card[] {  new Card(0, 6), new Card(0, 8) }; //Value = 0
        Card[] cards3 = new Card[] { new Card(0, 5), new Card(0, 6), new Card(0, 7), new Card(Card.PHOENIX, 8), new Card(Card.DRAGON, 25) }; //Value = 25
    }
}
