package gr.nrallakis.tichu.tests;

import com.badlogic.gdx.utils.Array;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gr.nrallakis.tichu.server.game.CombinationFinder;
import gr.nrallakis.tichu.server.game.Card;

public class TestCombinationFinder {

    private Array<Card> hand;

    @Before
    public void setUp() {
        hand = new Array<Card>();
    }

    @After
    public void cleanUp() {
        hand = null;
    }

    @Test
    public void testCalculatePoints() {
        hand.add(new Card(Card.DRAGON));        //+25
        hand.add(new Card(Card.BLUE, 6));
        hand.add(new Card(Card.PHOENIX));       //-25
        hand.add(new Card(Card.MAHJONG));
        hand.add(new Card(Card.BLUE, 5));       //+5
        hand.add(new Card(Card.BLUE, 10));      //+10
        hand.add(new Card(Card.RED, 2));
        hand.add(new Card(Card.GREEN, Card.K)); //+10
        assertEquals(25, CombinationFinder.calculatePoints(hand));

        hand.removeIndex(5); //-10
        assertEquals(15, CombinationFinder.calculatePoints(hand));
    }

//    @Test
//    public void testHasBomb() {
//        hand.add(new Card(Card.BLUE, 4));
//        hand.add(new Card(Card.GREEN, 4));
//        hand.add(new Card(Card.RED, 4));
//        hand.add(new Card(Card.BLACK, 4));
//        assert CombinationFinder.hasBomb(hand) == true;
//    }

    @Test
    public void testIsPair() {
        hand.add(new Card(Card.BLUE, 2));
        hand.add(new Card(Card.RED, 2));
        assertEquals(true, CombinationFinder.isPair(hand));
    }

    @Test
    public void testIsPair2() {
        hand.add(new Card(Card.BLUE, 2));
        hand.add(new Card(Card.RED, 3));
        assertEquals(false, CombinationFinder.isPair(hand));
    }

    @Test
    public void testIsPair3() {
        hand.add(new Card(Card.BLUE, 2));
        hand.add(new Card(Card.RED, 2));
        hand.add(new Card(Card.BLACK, 2));
        assertEquals(false, CombinationFinder.isPair(hand));
    }

    @Test
    public void testArePairs() {
        hand.add(new Card(Card.BLUE, 2));
        hand.add(new Card(Card.RED, 2));
        hand.add(new Card(Card.BLUE, 3));
        hand.add(new Card(Card.RED, 3));
        assertEquals(true, CombinationFinder.arePairs(hand));
    }

    @Test
    public void testArePairs2() {
        hand.add(new Card(Card.BLUE, 2));
        hand.add(new Card(Card.RED, 2));
        hand.add(new Card(Card.BLUE, 3));
        hand.add(new Card(Card.RED, 3));
        hand.add(new Card(Card.BLUE, 4));
        hand.add(new Card(Card.RED, 4));
        hand.add(new Card(Card.BLUE, 5));
        hand.add(new Card(Card.RED, 5));
        assertEquals(true, CombinationFinder.arePairs(hand));
    }

    @Test
    public void testArePair3() {
        hand.add(new Card(Card.BLUE, 2));
        hand.add(new Card(Card.RED, 2));
        hand.add(new Card(Card.BLUE, 3));
        hand.add(new Card(Card.RED, 5));
        hand.add(new Card(Card.BLUE, 1));
        hand.add(new Card(Card.RED, 7));
        assertEquals(false, CombinationFinder.arePairs(hand));
    }

    @Test
    public void testArePair4() {
        hand.add(new Card(Card.BLUE, 2));
        hand.add(new Card(Card.RED, 2));
        hand.add(new Card(Card.BLUE, 4));
        hand.add(new Card(Card.RED, 4));
        hand.add(new Card(Card.BLUE, 5));
        hand.add(new Card(Card.RED, 5));
        assertEquals(false, CombinationFinder.arePairs(hand));
    }

    @Test
    public void testIsTriple() {
        hand.add(new Card(Card.BLACK, 4));
        hand.add(new Card(Card.RED, 4));
        hand.add(new Card(Card.GREEN, 4));
        assertEquals(true, CombinationFinder.isTriple(hand));
    }

    @Test
    public void testIsTriple2() {
        hand.add(new Card(Card.BLACK, 2));
        hand.add(new Card(Card.RED, 4));
        hand.add(new Card(Card.GREEN, 1));
        assertEquals(false, CombinationFinder.isTriple(hand));
    }
    @Test
    public void testIsTriple3() {
        hand.add(new Card(Card.BLACK, 4));
        hand.add(new Card(Card.RED, 4));
        hand.add(new Card(Card.GREEN, 4));
        hand.add(new Card(Card.BLUE, 4));
        assertEquals(false, CombinationFinder.isTriple(hand));
    }

    @Test
    public void testIsFullHouse() {
        hand.add(new Card(Card.GREEN, 3));
        hand.add(new Card(Card.BLACK, 3));
        hand.add(new Card(Card.RED, 3));
        hand.add(new Card(Card.BLACK, 2));
        hand.add(new Card(Card.BLUE, 2));
        assertEquals(true, CombinationFinder.isFullHouse(hand));
    }

    @Test
    public void testIsFullHouse2() {
        hand.add(new Card(Card.GREEN, 6));
        hand.add(new Card(Card.GREEN, 1));
        hand.add(new Card(Card.RED, 3));
        hand.add(new Card(Card.BLACK, 2));
        hand.add(new Card(Card.BLUE, 7));
        assertEquals(false, CombinationFinder.isFullHouse(hand));
    }

    @Test
    public void testIsFullHouse3() {
        hand.add(new Card(Card.GREEN, 6));
        hand.add(new Card(Card.BLACK, 6));
        hand.add(new Card(Card.RED, Card.A));
        hand.add(new Card(Card.BLACK, Card.A));
        hand.add(new Card(Card.BLUE, Card.A));
        assertEquals(true, CombinationFinder.isFullHouse(hand));
    }

    @Test
    public void testIsStraight() {
        hand.add(new Card(Card.GREEN, 3));
        hand.add(new Card(Card.BLACK, 4));
        hand.add(new Card(Card.GREEN, 5));
        hand.add(new Card(Card.GREEN, 6));
        hand.add(new Card(Card.GREEN, 7));
        assertEquals(true, CombinationFinder.isStraight(hand));
    }

    @Test
    public void testIsStraight2() {
        hand.add(new Card(Card.GREEN, 9));
        hand.add(new Card(Card.BLACK, 10));
        hand.add(new Card(Card.GREEN, 11));
        hand.add(new Card(Card.RED, 12));
        hand.add(new Card(Card.GREEN, 13));
        hand.add(new Card(Card.GREEN, 14));
        assertEquals(true, CombinationFinder.isStraight(hand));
    }

    @Test
    public void testIsStraight3() {
        hand.add(new Card(Card.GREEN, 6));
        hand.add(new Card(Card.BLACK, 8));
        hand.add(new Card(Card.GREEN, 11));
        hand.add(new Card(Card.RED, 12));
        hand.add(new Card(Card.GREEN, 13));
        hand.add(new Card(Card.GREEN, 14));
        assertEquals(false, CombinationFinder.isStraight(hand));
    }

    @Test
    public void testIsStraight4() {
        hand.add(new Card(Card.GREEN, 5));
        hand.add(new Card(Card.BLACK, 3));
        hand.add(new Card(Card.GREEN, 4));
        hand.add(new Card(Card.RED, 5));
        hand.add(new Card(Card.GREEN, 6));
        assertEquals(false, CombinationFinder.isStraight(hand));
    }

    @Test
    public void testHaveSameType() {
        hand.add(new Card(Card.BLACK, 2));
        hand.add(new Card(Card.BLACK, 3));
        hand.add(new Card(Card.BLACK, 1));
        hand.add(new Card(Card.BLACK, 12));
        assertEquals(true, CombinationFinder.haveSameType(hand));
    }

    @Test
    public void testHaveSameType2() {
        hand.add(new Card(Card.BLACK, 2));
        hand.add(new Card(Card.GREEN, 3));
        hand.add(new Card(Card.BLACK, 1));
        hand.add(new Card(Card.BLACK, 12));
        assertEquals(false, CombinationFinder.haveSameType(hand));
    }

    @Test
    public void testHaveDifferentType() {
        hand.add(new Card(Card.BLACK, 2));
        hand.add(new Card(Card.GREEN, 3));
        hand.add(new Card(Card.RED, 1));
        hand.add(new Card(Card.BLUE, 12));
        assertEquals(true, CombinationFinder.haveDifferentTypesEachOther(hand));
    }

    @Test
    public void testHaveDifferentType2() {
        hand.add(new Card(Card.BLACK, 2));
        hand.add(new Card(Card.BLACK, 3));
        hand.add(new Card(Card.RED, 1));
        hand.add(new Card(Card.BLUE, 12));
        assertEquals(false, CombinationFinder.haveDifferentTypesEachOther(hand));
    }

    @Test
    public void testHaveDifferentType3() {
        hand.add(new Card(Card.GREEN, 2));
        hand.add(new Card(Card.GREEN, 3));
        hand.add(new Card(Card.RED, 1));
        hand.add(new Card(Card.GREEN, 12));
        assertEquals(false, CombinationFinder.haveDifferentTypesEachOther(hand));
    }

    @Test
    public void testIsBomb() {
        hand.add(new Card(Card.BLUE, 4));
        hand.add(new Card(Card.GREEN, 4));
        hand.add(new Card(Card.RED, 4));
        hand.add(new Card(Card.BLACK, 4));
        assertEquals(true, CombinationFinder.isBomb(hand));
    }

    @Test
    public void testIsBomb2() {
        hand.add(new Card(Card.BLUE, 3));
        hand.add(new Card(Card.BLUE, 4));
        hand.add(new Card(Card.BLUE, 5));
        hand.add(new Card(Card.BLUE, 6));
        hand.add(new Card(Card.BLUE, 7));
        assertEquals(true, CombinationFinder.isBomb(hand));
    }

    @Test
    public void testIsBomb3() {
        hand.add(new Card(Card.RED, 3));
        hand.add(new Card(Card.RED, 4));
        hand.add(new Card(Card.RED, 5));
        hand.add(new Card(Card.RED, 6));
        hand.add(new Card(Card.RED, 7));
        hand.add(new Card(Card.BLACK, 8));
        assertEquals(false, CombinationFinder.isBomb(hand));
    }

    @Test
    public void testIsBomb4() {
        hand.add(new Card(Card.RED, 3));
        hand.add(new Card(Card.BLACK, 3));
        hand.add(new Card(Card.GREEN, 3));
        hand.add(new Card(Card.BLUE, 3));
        hand.add(new Card(Card.RED, 4));
        assertEquals(false, CombinationFinder.isBomb(hand));
    }

    @Test
    public void testIsBomb5() {
        hand.add(new Card(Card.RED, 3));
        hand.add(new Card(Card.RED, 4));
        hand.add(new Card(Card.RED, 5));
        hand.add(new Card(Card.RED, 6));
        hand.add(new Card(Card.RED, 7));
        hand.add(new Card(Card.RED, 8));
        hand.add(new Card(Card.RED, 9));
        assertEquals(true, CombinationFinder.isBomb(hand));
    }

}
