package gr.nrallakis.tichu.server.utils;

import java.util.List;

import gr.nrallakis.tichu.server.game.Card;
import gr.nrallakis.tichu.server.game.CardCombination;
import gr.nrallakis.tichu.server.game.InvalidCardCombinationException;

/** Utility class for card combinations. Includes many card combination operations*/
public class CardCombinationUtils {

    /**
     * Given an Array of cards, finds and returns the appropriate CardCombination object.
     * If the cards do not form any type of combination, an InvalidCardCombinationException is thrown
     *
     * @see CardCombination
     */
    public static CardCombination findCombination(List<Card> sCards) throws InvalidCardCombinationException {
        final int scalar = 2;
        if (sCards.size() == 1) {
            return new CardCombination(CardCombination.SINGLE, sCards.get(0).getRank()*scalar, sCards);
        }
        if (isPair(sCards)) {
            return new CardCombination(CardCombination.PAIR, sCards.get(0).getRank()*scalar, sCards);
        }
        if (isTriple(sCards)) {
            return new CardCombination(CardCombination.TRIPLE, sCards.get(0).getRank()*scalar, sCards);
        }
        if (isFullHouse(sCards)) {
            // We first assume that the triple is first
            int fullHouseValue = sCards.get(0).getRank();
            if (isTriple(sCards, 2)) {
                fullHouseValue = sCards.get(2).getRank();
            }
            return new CardCombination(CardCombination.FULL_HOUSE, fullHouseValue*scalar, sCards);
        }
        if (isColorBomb(sCards)) {
            return new CardCombination(CardCombination.COLOR_BOMB, sCards.get(0).getRank()*scalar, sCards);
        }
        if (isStraightBomb(sCards)) {
            return new CardCombination(CardCombination.STRAIGHT_BOMB, sCards.get(0).getRank()*scalar, sCards);
        }
        if (areStraightPairs(sCards)) {
            return new CardCombination(CardCombination.STRAIGHT_PAIRS, sCards.get(0).getRank()*scalar, sCards);
        }
        if (isStraight(sCards)) {
            return new CardCombination(CardCombination.STRAIGHT, sCards.get(0).getRank()*scalar, sCards);
        }
        throw new InvalidCardCombinationException();
    }

    public static boolean isPair(List<Card> sCards) {
        if (sCards.size() != 2) return false;
        return sCards.get(0).getRank() == sCards.get(1).getRank();
    }

    public static boolean isTriple(List<Card> sCards) {
        if (sCards.size() != 3) return false;
        return sCards.get(0).getRank() == sCards.get(1).getRank()
                && sCards.get(1).getRank() == sCards.get(2).getRank();
    }

    public static boolean isTriple(List<Card> sCards, int start) {
        if (start + 2 > sCards.size()) return false;
        return sCards.get(start).getRank() == sCards.get(start+1).getRank()
                && sCards.get(start+1).getRank() == sCards.get(start+2).getRank();
    }

    public static boolean areStraightPairs(List<Card> sCards) {
        if (sCards.size() < 4) return false;
        if (sCards.size() % 2 != 0) return false;

        CardSorter.sortByRank(sCards);

        /* Check if are all pairs */
        for (int i = 0; i < sCards.size(); i+=2) {
            if (sCards.get(i).getRank() != sCards.get(i+1).getRank()) {
                return false;
            }
        }

        /* Check for increasing values */
        int nextRank = sCards.get(2).getRank();
        for (int i = 0; i < sCards.size(); i+=2) {
            if (sCards.get(i).getRank() + 1 != nextRank) {
                return false;
            }
            nextRank++;
        }
        return true;
    }

    public static boolean isFullHouse(List<Card> sCards) {
        if (sCards.size() != 5) {
            return false;
        }
        // Full House has one of the following formats:
        // xxx-yy and xx-yyy
        boolean twoAndThree;
        boolean threeAndTwo;

        twoAndThree = sCards.get(0).getRank() == sCards.get(1).getRank()
                   && sCards.get(2).getRank() == sCards.get(3).getRank()
                   && sCards.get(3).getRank() == sCards.get(4).getRank();

        threeAndTwo = sCards.get(0).getRank() == sCards.get(1).getRank()
                   && sCards.get(1).getRank() == sCards.get(2).getRank()
                   && sCards.get(3).getRank() == sCards.get(4).getRank();

        return (twoAndThree || threeAndTwo);
    }

    public static boolean haveSameType(List<Card> sCards) {
        CardSorter.sortByType(sCards);
        Card firstCard = sCards.get(0);
        Card lastCard = sCards.get(sCards.size()-1);
        return firstCard.getType() == lastCard.getType();
    }

    /* Return whether the cards have each one a different type from the others
    * If the sCards size is bigger than 4 it is false*/
    public static boolean haveDifferentTypesEachOther(List<Card> sCards) {
        if (sCards.size() > 4) return false;
        int sum = 0;
        for (int i = 0; i < sCards.size(); i++) {
            sum += sCards.get(i).getType();
        }
        return sum == 6;
    }

    public static boolean isColorBomb(List<Card> sCards) {
        return haveDifferentTypesEachOther(sCards);
    }

    public static boolean isStraightBomb(List<Card> sCards) {
        return isStraight(sCards) && haveSameType(sCards);
    }

    public static boolean isStraight(List<Card> sCards) {
        if (sCards.size() < 5) {
            return false;
        }
        CardSorter.sortByRank(sCards);

        /* Check for increasing values */
        int nextRank = sCards.get(0).getRank() + 1;
        for (int i = 1; i < sCards.size(); i++) {
            if (sCards.get(i).getRank() != nextRank) {
                return false;
            }
            nextRank++;
        }
        return true;
    }

    /**
     * @return the points for a card trick
     */
    public static int calculatePoints(List<Card> cards) {
        int points = 0;
        for (Card card : cards) {
            if (card.isSpecial()) {
                if (card.getSpecialType() == Card.DRAGON) points += 25;
                if (card.getSpecialType() == Card.PHOENIX) points -= 25;
            } else {
                if (card.getRank() == 5) points += 5;
                if (card.getRank() == 10) points += 10;
                if (card.getRank() == Card.K) points += 10;
            }
        }
        return points;
    }
}
