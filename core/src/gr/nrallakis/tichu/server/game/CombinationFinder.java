package gr.nrallakis.tichu.server.game;

import com.badlogic.gdx.utils.Array;

import gr.nrallakis.tichu.utils.CardSorter;

public class CombinationFinder {

    public Combination findCombination(Array<Card> sCards) {
        return null;
    }

    public static boolean arePairs(Array<Card> sCards) {
        if (sCards.size == 2)
            return sCards.get(0).getRank() == sCards.get(1).getRank();
        if (sCards.size % 2 == 0) {
            for (int i = 0; i < sCards.size / 2; i++) {
                if (sCards.get(i).getRank() != sCards.get(i + 1).getRank()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean hasBomb(Array<Card> hand) {
        if (hand.size < 4) return false;
        hand.sort();
        if (hand.size == 4) {
            return isBomb(hand);
        }
        //TODO: continue
        return false;
    }

    public static boolean isFullHouse(Array<Card> sCards) {
        if (sCards.size != 5) {
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

    public static boolean haveSameType(Array<Card> sCards) {
        CardSorter.sortByType(sCards);
        Card firstCard = sCards.get(0);
        Card lastCard = sCards.get(sCards.size-1);
        return firstCard.getType() == lastCard.getType();
    }

    /* Return whether the cards have each one a different type from the others
    * If the sCards size is bigger than 4 it is false*/
    public static boolean haveDifferentTypesEachOther(Array<Card> sCards) {
        if (sCards.size > 4) return false;
        int sum = 0;
        for (int i = 0; i < sCards.size; i++) {
            sum += sCards.get(i).getType();
        }
        return sum == 6;
    }

     /* There are two type of bombs:
    * i) 4 cards same rank with different types
    * ii) A straight of the same type */
    public static boolean isBomb(Array<Card> sCards) {
        boolean isFirstBomb = haveDifferentTypesEachOther(sCards);
        boolean isSecondBomb = isStraight(sCards) && haveSameType(sCards);
        return (isFirstBomb || isSecondBomb);
    }

    public static boolean isStraight(Array<Card> sCards) {
        if (sCards.size < 5) {
            return false;
        }
        CardSorter.sortByRank(sCards);

        /* Check for increasing values */
        int nextRank = sCards.get(0).getRank() + 1;
        for (int i = 1; i < sCards.size; i++) {
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
    public static int calculatePoints(Array<Card> cCards) {
        int points = 0;
        for (Card c : cCards) {
            if (c.getRank() == 5) points += 5;
            if (c.getRank() == 10) points += 10;
            if (c.getRank() == Card.K) points += 10;
            if (c.isSpecial()) {
                if (c.getSpecialType() == Card.DRAGON) points += 25;
                if (c.getSpecialType() == Card.PHOENIX) points -= 25;
            }
        }
        return points;
    }
}
