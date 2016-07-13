package gr.nrallakis.tichu.server.utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import gr.nrallakis.tichu.server.game.Card;
import gr.nrallakis.tichu.server.game.CardCombination;
import gr.nrallakis.tichu.server.game.InvalidCardCombinationException;

import static org.junit.Assert.assertEquals;

public class CombinationUtilsTest {

    private ArrayList<Card> hand;

    @Before
    public void setUp() {
        hand = new ArrayList<>();
    }

    @After
    public void cleanUp() {
        hand = null;
    }

    @Test
    public void testCalculatePoints() {
        hand.add(new Card(Card.DRAGON, 25));        //+25
        hand.add(new Card(Card.BLUE, 6));
        hand.add(new Card(Card.PHOENIX, -1));       //-25
        hand.add(new Card(Card.MAHJONG, 1));
        hand.add(new Card(Card.BLUE, 5));       //+5
        hand.add(new Card(Card.BLUE, 10));      //+10
        hand.add(new Card(Card.RED, 2));
        hand.add(new Card(Card.GREEN, Card.K)); //+10
        Assert.assertEquals(25, CardCombinationUtils.calculatePoints(hand));

        hand.remove(5); //-10
        assertEquals(15, CardCombinationUtils.calculatePoints(hand));
    }

    @Test
    public void testFindCombination0() throws InvalidCardCombinationException {
        hand.add(new Card(Card.BLUE, 2));
        assertEquals(CardCombination.SINGLE, CardCombinationUtils.findCombination(hand).getType());
    }

    @Test
    public void testFindCombination1() throws InvalidCardCombinationException {
        hand.add(new Card(Card.BLUE, 2));
        hand.add(new Card(Card.RED, 2));
        assertEquals(CardCombination.PAIR, CardCombinationUtils.findCombination(hand).getType());
    }

    @Test
    public void testFindCombination2() throws InvalidCardCombinationException {
        hand.add(new Card(Card.BLACK, 4));
        hand.add(new Card(Card.RED, 4));
        hand.add(new Card(Card.GREEN, 4));
        assertEquals(CardCombination.TRIPLE, CardCombinationUtils.findCombination(hand).getType());
    }

    @Test
    public void testFindCombination3() throws InvalidCardCombinationException {
        hand.add(new Card(Card.GREEN, 3));
        hand.add(new Card(Card.BLACK, 3));
        hand.add(new Card(Card.RED, 3));
        hand.add(new Card(Card.BLACK, 2));
        hand.add(new Card(Card.BLUE, 2));
        assertEquals(CardCombination.FULL_HOUSE, CardCombinationUtils.findCombination(hand).getType());
    }

    @Test
    public void testFindCombination4() throws InvalidCardCombinationException {
        hand.add(new Card(Card.BLUE, 2));
        hand.add(new Card(Card.RED, 2));
        hand.add(new Card(Card.BLUE, 3));
        hand.add(new Card(Card.RED, 3));
        hand.add(new Card(Card.BLUE, 4));
        hand.add(new Card(Card.RED, 4));
        hand.add(new Card(Card.BLUE, 5));
        hand.add(new Card(Card.RED, 5));
        assertEquals(CardCombination.STRAIGHT_PAIRS, CardCombinationUtils.findCombination(hand).getType());
    }

    @Test
    public void testFindCombination5() throws InvalidCardCombinationException {
        hand.add(new Card(Card.BLUE, 4));
        hand.add(new Card(Card.GREEN, 4));
        hand.add(new Card(Card.RED, 4));
        hand.add(new Card(Card.BLACK, 4));
        assertEquals(CardCombination.COLOR_BOMB, CardCombinationUtils.findCombination(hand).getType());
    }

    @Test
    public void testIsPair() {
        hand.add(new Card(Card.BLUE, 2));
        hand.add(new Card(Card.RED, 2));
        assertEquals(true, CardCombinationUtils.isPair(hand));
    }

    @Test
    public void testIsPair2() {
        hand.add(new Card(Card.BLUE, 2));
        hand.add(new Card(Card.RED, 3));
        assertEquals(false, CardCombinationUtils.isPair(hand));
    }

    @Test
    public void testIsPair3() {
        hand.add(new Card(Card.BLUE, 2));
        hand.add(new Card(Card.RED, 2));
        hand.add(new Card(Card.BLACK, 2));
        assertEquals(false, CardCombinationUtils.isPair(hand));
    }

    @Test
    public void testAreStraightPairs() {
        hand.add(new Card(Card.BLUE, 2));
        hand.add(new Card(Card.RED, 2));
        hand.add(new Card(Card.BLUE, 3));
        hand.add(new Card(Card.RED, 3));
        assertEquals(true, CardCombinationUtils.areStraightPairs(hand));
    }

    @Test
    public void testAreStraightPairs2() {
        hand.add(new Card(Card.BLUE, 2));
        hand.add(new Card(Card.RED, 2));
        hand.add(new Card(Card.BLUE, 3));
        hand.add(new Card(Card.RED, 3));
        hand.add(new Card(Card.BLUE, 4));
        hand.add(new Card(Card.RED, 4));
        hand.add(new Card(Card.BLUE, 5));
        hand.add(new Card(Card.RED, 5));
        assertEquals(true, CardCombinationUtils.areStraightPairs(hand));
    }

    @Test
    public void testAreStraightPairs3() {
        hand.add(new Card(Card.BLUE, 2));
        hand.add(new Card(Card.RED, 2));
        hand.add(new Card(Card.BLUE, 3));
        hand.add(new Card(Card.RED, 5));
        hand.add(new Card(Card.BLUE, 1));
        hand.add(new Card(Card.RED, 7));
        assertEquals(false, CardCombinationUtils.areStraightPairs(hand));
    }

    @Test
    public void testAreStraightPairs4() {
        hand.add(new Card(Card.BLUE, 2));
        hand.add(new Card(Card.RED, 2));
        hand.add(new Card(Card.BLUE, 4));
        hand.add(new Card(Card.RED, 4));
        hand.add(new Card(Card.BLUE, 5));
        hand.add(new Card(Card.RED, 5));
        assertEquals(false, CardCombinationUtils.areStraightPairs(hand));
    }

    @Test
    public void testIsTriple() {
        hand.add(new Card(Card.BLACK, 4));
        hand.add(new Card(Card.RED, 4));
        hand.add(new Card(Card.GREEN, 4));
        assertEquals(true, CardCombinationUtils.isTriple(hand));
    }

    @Test
    public void testIsTriple2() {
        hand.add(new Card(Card.BLACK, 2));
        hand.add(new Card(Card.RED, 4));
        hand.add(new Card(Card.GREEN, 1));
        assertEquals(false, CardCombinationUtils.isTriple(hand));
    }
    @Test
    public void testIsTriple3() {
        hand.add(new Card(Card.BLACK, 4));
        hand.add(new Card(Card.RED, 4));
        hand.add(new Card(Card.GREEN, 4));
        hand.add(new Card(Card.BLUE, 4));
        assertEquals(false, CardCombinationUtils.isTriple(hand));
    }

    @Test
    public void testIsFullHouse() {
        hand.add(new Card(Card.GREEN, 3));
        hand.add(new Card(Card.BLACK, 3));
        hand.add(new Card(Card.RED, 3));
        hand.add(new Card(Card.BLACK, 2));
        hand.add(new Card(Card.BLUE, 2));
        assertEquals(true, CardCombinationUtils.isFullHouse(hand));
    }

    @Test
    public void testIsFullHouse2() {
        hand.add(new Card(Card.GREEN, 6));
        hand.add(new Card(Card.GREEN, 1));
        hand.add(new Card(Card.RED, 3));
        hand.add(new Card(Card.BLACK, 2));
        hand.add(new Card(Card.BLUE, 7));
        assertEquals(false, CardCombinationUtils.isFullHouse(hand));
    }

    @Test
    public void testIsFullHouse3() {
        hand.add(new Card(Card.GREEN, 6));
        hand.add(new Card(Card.BLACK, 6));
        hand.add(new Card(Card.RED, Card.A));
        hand.add(new Card(Card.BLACK, Card.A));
        hand.add(new Card(Card.BLUE, Card.A));
        assertEquals(true, CardCombinationUtils.isFullHouse(hand));
    }

    @Test
    public void testIsStraight() {
        hand.add(new Card(Card.GREEN, 3));
        hand.add(new Card(Card.BLACK, 4));
        hand.add(new Card(Card.GREEN, 5));
        hand.add(new Card(Card.GREEN, 6));
        hand.add(new Card(Card.GREEN, 7));
        assertEquals(true, CardCombinationUtils.isStraight(hand));
    }

    @Test
    public void testIsStraight2() {
        hand.add(new Card(Card.GREEN, 9));
        hand.add(new Card(Card.BLACK, 10));
        hand.add(new Card(Card.GREEN, 11));
        hand.add(new Card(Card.RED, 12));
        hand.add(new Card(Card.GREEN, 13));
        hand.add(new Card(Card.GREEN, 14));
        assertEquals(true, CardCombinationUtils.isStraight(hand));
    }

    @Test
    public void testIsStraight3() {
        hand.add(new Card(Card.GREEN, 6));
        hand.add(new Card(Card.BLACK, 8));
        hand.add(new Card(Card.GREEN, 11));
        hand.add(new Card(Card.RED, 12));
        hand.add(new Card(Card.GREEN, 13));
        hand.add(new Card(Card.GREEN, 14));
        assertEquals(false, CardCombinationUtils.isStraight(hand));
    }

    @Test
    public void testIsStraight4() {
        hand.add(new Card(Card.GREEN, 5));
        hand.add(new Card(Card.BLACK, 3));
        hand.add(new Card(Card.GREEN, 4));
        hand.add(new Card(Card.RED, 5));
        hand.add(new Card(Card.GREEN, 6));
        assertEquals(false, CardCombinationUtils.isStraight(hand));
    }

    @Test
    public void testHaveSameType() {
        hand.add(new Card(Card.BLACK, 2));
        hand.add(new Card(Card.BLACK, 3));
        hand.add(new Card(Card.BLACK, 1));
        hand.add(new Card(Card.BLACK, 12));
        assertEquals(true, CardCombinationUtils.haveSameType(hand));
    }

    @Test
    public void testHaveSameType2() {
        hand.add(new Card(Card.BLACK, 2));
        hand.add(new Card(Card.GREEN, 3));
        hand.add(new Card(Card.BLACK, 1));
        hand.add(new Card(Card.BLACK, 12));
        assertEquals(false, CardCombinationUtils.haveSameType(hand));
    }

    @Test
    public void testHaveDifferentType() {
        hand.add(new Card(Card.BLACK, 2));
        hand.add(new Card(Card.GREEN, 3));
        hand.add(new Card(Card.RED, 1));
        hand.add(new Card(Card.BLUE, 12));
        assertEquals(true, CardCombinationUtils.haveDifferentTypesEachOther(hand));
    }

    @Test
    public void testHaveDifferentType2() {
        hand.add(new Card(Card.BLACK, 2));
        hand.add(new Card(Card.BLACK, 3));
        hand.add(new Card(Card.RED, 1));
        hand.add(new Card(Card.BLUE, 12));
        assertEquals(false, CardCombinationUtils.haveDifferentTypesEachOther(hand));
    }

    @Test
    public void testHaveDifferentType3() {
        hand.add(new Card(Card.GREEN, 2));
        hand.add(new Card(Card.GREEN, 3));
        hand.add(new Card(Card.RED, 1));
        hand.add(new Card(Card.GREEN, 12));
        assertEquals(false, CardCombinationUtils.haveDifferentTypesEachOther(hand));
    }

    @Test
    public void testIsBomb() {
        hand.add(new Card(Card.BLUE, 4));
        hand.add(new Card(Card.GREEN, 4));
        hand.add(new Card(Card.RED, 4));
        hand.add(new Card(Card.BLACK, 4));
        assertEquals(true, CardCombinationUtils.isColorBomb(hand));
    }

    @Test
    public void testIsBomb2() {
        hand.add(new Card(Card.BLUE, 3));
        hand.add(new Card(Card.BLUE, 2));
        hand.add(new Card(Card.BLUE, 5));
        hand.add(new Card(Card.BLUE, 6));
        hand.add(new Card(Card.BLUE, 7));
        assertEquals(false, CardCombinationUtils.isStraightBomb(hand));
    }

    @Test
    public void testIsBomb3() {
        hand.add(new Card(Card.RED, 3));
        hand.add(new Card(Card.RED, 4));
        hand.add(new Card(Card.RED, 5));
        hand.add(new Card(Card.RED, 6));
        hand.add(new Card(Card.RED, 7));
        hand.add(new Card(Card.BLACK, 8));
        assertEquals(false, CardCombinationUtils.isStraightBomb(hand));
    }

    @Test
    public void testIsBomb4() {
        hand.add(new Card(Card.RED, 3));
        hand.add(new Card(Card.BLACK, 3));
        hand.add(new Card(Card.GREEN, 3));
        hand.add(new Card(Card.BLUE, 3));
        hand.add(new Card(Card.RED, 4));
        assertEquals(false, CardCombinationUtils.isStraightBomb(hand));
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
        assertEquals(true, CardCombinationUtils.isStraightBomb(hand));
    }

    @Test
    public void testIsBomb6() {
        hand.add(new Card(Card.RED, 3));
        hand.add(new Card(Card.RED, 4));
        hand.add(new Card(Card.BLACK, 5));
        hand.add(new Card(Card.RED, 6));
        hand.add(new Card(Card.RED, 7));
        assertEquals(false, CardCombinationUtils.isStraightBomb(hand));
    }

}
