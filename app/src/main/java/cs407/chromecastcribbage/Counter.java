package cs407.chromecastcribbage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by sirinek on 3/19/2017.
 */

public class Counter {

    public static String count(Hand hand) {

        int fifteenCount = 0;
        int fifteen = 0;
        int pairCount = 0;
        int pair = 0;
        int threeOfaKind = 0;
        int threeOfaKindCount = 0;
        int fourOfaKind = 0;
        int fourOfaKindCount = 0;
        int suit = 0;
        boolean flush = true;
        int flushScore = 0;
        int totalScore = 0;
        int nobs = 0;
        int runOfFive = 0;
        int runOfFourCount = 0;
        int runOfFour = 0;
        int runOfThreeCount = 0;
        int runOfThree = 0;
        List<Integer> cards = new ArrayList();
        Set<Integer> cardSet;
        cardSet = new HashSet();
        int run = 0;
        boolean runFive = false;
        boolean runFour = false;
        int numberOfThreeRun = 0;
        boolean triRun = false;
        //String msg = "";

        /*msg = msg + hand.getCard(0).getIntSuit();
        msg = msg + hand.getCard(1).getIntSuit();
        msg = msg + hand.getCard(2).getIntSuit();
        msg = msg + hand.getCard(3).getIntSuit();
        msg = msg + hand.getCard(4).getIntSuit();*/

        //Pairs and Stuff
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            if (hashMap.containsKey(hand.getCard(i).getIntValue())) {
                hashMap.put(hand.getCard(i).getIntValue(), hashMap.get(hand.getCard(i).getIntValue()) + 1);
            } else {
                hashMap.put(hand.getCard(i).getIntValue(), 1);
            }
        }
        for (int count : hashMap.values()) {
            if (count == 4) {
                fourOfaKindCount += 1;
                fourOfaKind += 12;
            } else if (count == 3) {
                threeOfaKindCount += 1;
                threeOfaKind += 6;
            } else if (count == 2) {
                pairCount += 1;
                pair += 2;
            }
        }

        //Check for Nobs
        for (int i = 0; i < 4; i++) {
            if (hand.getCard(i).getIntValue() == 11 && hand.getCard(i).getSuit() == hand.getCard(4).getSuit()) {
                nobs = 1;
            }
        }

        //Check for flush
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                suit = hand.getCard(i).getIntSuit();
            } else {
                if (hand.getCard(i).getIntSuit() != suit) {
                    flush = false;
                }
            }
        }
        if (flush) {
            flushScore = 4;
            if (hand.getCard(4).getIntSuit() == suit) {
                flushScore = 5;
            }
        }

        //Make all face cards value equal to 10
        for (int i = 0; i < 5; i++) {
            if (hand.getCard(i).getIntValue() > 10) {
                cards.add(i, 10);
            } else {
                cards.add(i, hand.getCard(i).getIntValue());
            }
        }

        //Count 15's
        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {
                //2 card Fifteens
                if (cards.get(i) + cards.get(j) == 15) {
                    fifteenCount += 1;
                    fifteen += 2;
                }
                for (int k = j + 1; k < 5; k++) {
                    //3 card Fifteens
                    if (cards.get(i) + cards.get(j) + cards.get(k) == 15) {
                        fifteenCount += 1;
                        fifteen += 2;
                    }
                    for (int l = k + 1; l < 5; l++) {
                        //4 card Fifteens
                        if (cards.get(i) + cards.get(j) + cards.get(k) + cards.get(l) == 15) {
                            fifteenCount += 1;
                            fifteen += 2;
                        }
                        for (int m = l + 1; m < 5; m++) {
                            //5 card Fifteens
                            if (cards.get(i) + cards.get(j) + cards.get(k) + cards.get(l) + cards.get(m) == 15) {
                                fifteenCount += 1;
                                fifteen += 2;
                            }
                        }
                    }
                }
            }
        }

        //Sort cards low to high
        hand.sortByValueLowHigh();

        //Add values to set
        for (int i = 0; i < 5; i++) {
            cardSet.add(hand.getCard(i).getIntValue());
        }

        TreeSet treeSet = new TreeSet(cardSet);
        cards = new ArrayList<>();
        cards.addAll(treeSet);

        if (cardSet.size() == 5) {
            for (int i = 1; i < cards.size(); i++) {
                if ((cards.get(i - 1) + 1) == cards.get(i)) {
                    run += 1;
                    if (run == 4) {
                        runOfFive += 5;
                        runFive = true;
                    }
                } else {

                    run = 0;
                }

            }
        }
        run=0;
        if (cardSet.size() >= 4 && !runFive) {
            for (int i = 1; i < cards.size(); i++) {
                if ((cards.get(i - 1) + 1) == cards.get(i)) {
                    run += 1;
                    if (run >= 3) {
                        runOfFourCount += 1;
                        runOfFour += 4;
                        run = 0;
                        runFour = true;
                        for (int j = 1; j < hashMap.get(cards.get(i - 3)); j++) {
                            runOfFourCount += 1;
                            runOfFour += 4;
                        }
                        for (int j = 1; j < hashMap.get(cards.get(i - 2)); j++) {
                            runOfFourCount += 1;
                            runOfFour += 4;
                        }
                        for (int j = 1; j < hashMap.get(cards.get(i - 1)); j++) {
                            runOfFourCount += 1;
                            runOfFour += 4;
                        }
                        for (int j = 1; j < hashMap.get(cards.get(i)); j++) {
                            runOfFourCount += 1;
                            runOfFour += 4;
                        }
                    }
                } else {
                    run = 0;
                }
            }
        }
        run=0;
        if (cardSet.size() >= 3 && !runFour && !runFive) {
            for (int i = 1; i < cards.size(); i++) {
                if ((cards.get(i - 1) + 1) == cards.get(i)) {
                    run += 1;
                    if (run >= 2) {
                        if(hashMap.get(cards.get(i - 2))==3 || hashMap.get(cards.get(i - 1))==3 || hashMap.get(cards.get(i))==3){
                            triRun=true;
                        }
                        numberOfThreeRun = hashMap.get(cards.get(i - 2)) + hashMap.get(cards.get(i - 1)) + hashMap.get(cards.get(i));
                        if (numberOfThreeRun == 5 && !triRun) {
                            numberOfThreeRun = 4;
                        } else if(numberOfThreeRun == 5 && triRun){
                            numberOfThreeRun = 3;
                        }else {
                            numberOfThreeRun -= 2;
                        }
                        for (int j = 0; j < numberOfThreeRun; j++) {
                            runOfThreeCount += 1;
                            runOfThree += 3;
                        }
                        run = 0;
                    }
                } else {
                    run = 0;
                }
            }
        }

        totalScore = fifteen + pair + threeOfaKind + fourOfaKind + flushScore + runOfFive + runOfFour + runOfThree + nobs;
        return fifteenCount + " Fifteen for " + fifteen + "\n"
                + pairCount + " Pair for " + pair + "\n"
                + threeOfaKindCount + " Three of a kind for " + threeOfaKind + "\n"
                + fourOfaKindCount + " Four of a kind for " + fourOfaKind + "\n"
                + runOfFourCount + " Run of four for " + runOfFour + "\n"
                + runOfThreeCount + " Run of three for " + runOfThree + "\n"
                + "Run of five for " + runOfFive + "\n"
                + "Flush for " + flushScore + "\n"
                + "Nobs for " + nobs + "\n"// + msg +"\n"
                + "Total Score: " + totalScore;
    }

}
