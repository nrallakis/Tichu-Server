package gr.nrallakis.tichu.server.game;

import com.badlogic.gdx.utils.Array;

import gr.nrallakis.tichu.utils.CardSorter;

public class CardCombinationFinder {

    public static CardCombination findCombination(Array<Card> sCards) {
        final int scalar = 2;
        if (isPair(sCards)) {
            return new CardCombination(CardCombination.PAIR, sCards.first().getRank()*scalar, sCards);
        }
        if (isTriple(sCards)) {
            return new CardCombination(CardCombination.TRIPLE, sCards.first().getRank()*scalar, sCards);
        }
        if (isFullHouse(sCards)) {
            // We first assume that the triple is first
            int fullHouseValue = sCards.get(0).getRank();
            if (isTriple(sCards, 2)) {
                fullHouseValue = sCards.get(2).getRank();
            }
            return new CardCombination(CardCombination.FULLHOUSE, fullHouseValue*scalar, sCards);
        }
        if (isColorBomb(sCards)) {
            return new CardCombination(CardCombination.COLOR_BOMB, sCards.first().getRank()*scalar, sCards);
        }
        if (isStraightBomb(sCards)) {
            return new CardCombination(CardCombination.STRAIGHT_BOMB, sCards.first().getRank()*scalar, sCards);
        }
        if (areStraightPairs(sCards)) {
            return new CardCombination(CardCombination.STRAIGHT_PAIRS, sCards.first().getRank()*scalar, sCards);
        }
        if (isStraight(sCards)) {
            return new CardCombination(CardCombination.STRAIGHT, sCards.first().getRank()*scalar, sCards);
        }
        return null;
    }

    public static boolean isPair(Array<Card> sCards) {
        if (sCards.size !=2) return false;
        return sCards.get(0).getRank() == sCards.get(1).getRank();
    }

    public static boolean isTriple(Array<Card> sCards) {
        if (sCards.size != 3) return false;
        return sCards.get(0).getRank() == sCards.get(1).getRank()
                && sCards.get(1).getRank() == sCards.get(2).getRank();
    }

    public static boolean isTriple(Array<Card> sCards, int start) {
        if (start + 2 > sCards.size) return false;
        return sCards.get(start).getRank() == sCards.get(start+1).getRank()
                && sCards.get(start+1).getRank() == sCards.get(start+2).getRank();
    }

    public static boolean areStraightPairs(Array<Card> sCards) {
        if (sCards.size < 4) return false;
        if (sCards.size % 2 != 0) return false;

        CardSorter.sortByRank(sCards);

        /* Check if are all pairs */
        for (int i = 0; i < sCards.size; i+=2) {
            if (sCards.get(i).getRank() != sCards.get(i+1).getRank()) {
                return false;
            }
        }

        /* Check for increasing values */
        int nextRank = sCards.get(2).getRank();
        for (int i = 0; i < sCards.size; i+=2) {
            if (sCards.get(i).getRank() + 1 != nextRank) {
                return false;
            }
            nextRank++;
        }
        return true;
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

    public static boolean isColorBomb(Array<Card> sCards) {
        return haveDifferentTypesEachOther(sCards);
    }

    public static boolean isStraightBomb(Array<Card> sCards) {
        return isStraight(sCards) && haveSameType(sCards);
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
