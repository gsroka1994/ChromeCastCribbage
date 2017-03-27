package cs407.chromecastcribbage;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void quadRun_isCorrect() throws Exception {

        Card card1 = new Card(9,1);
        Card card2 = new Card(9,2);
        Card card3 = new Card(10,3);
        Card card4 = new Card(10,2);
        Card card5 = new Card(11,1);

        Hand hand = new Hand();
        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);
        hand.addCard(card4);
        hand.addCard(card5);

        String countString = Counter.count(hand);
        String[] count = countString.split("Total Score: ");
        System.out.println(count[0]);

        int finalCount = Integer.parseInt(count[1]);

        assertEquals(16, finalCount);
    }

    @Test
    public void triRun_isCorrect() throws Exception {

        Card card1 = new Card(9,1);
        Card card2 = new Card(9,2);
        Card card3 = new Card(10,3);
        Card card4 = new Card(9,2);
        Card card5 = new Card(11,1);

        Hand hand = new Hand();
        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);
        hand.addCard(card4);
        hand.addCard(card5);

        String countString = Counter.count(hand);
        String[] count = countString.split("Total Score: ");
        System.out.println(count[0]);

        int finalCount = Integer.parseInt(count[1]);

        assertEquals(15, finalCount);
    }

    @Test
    public void dubRun_isCorrect() throws Exception {

        Card card1 = new Card(9,1);
        Card card2 = new Card(9,2);
        Card card3 = new Card(10,3);
        Card card4 = new Card(1,2);
        Card card5 = new Card(11,1);

        Hand hand = new Hand();
        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);
        hand.addCard(card4);
        hand.addCard(card5);

        String countString = Counter.count(hand);
        String[] count = countString.split("Total Score: ");
        System.out.println(count[0]);

        int finalCount = Integer.parseInt(count[1]);

        assertEquals(8, finalCount);
    }

    @Test
    public void oneRun_isCorrect() throws Exception {

        Card card1 = new Card(9,1);
        Card card2 = new Card(4,2);
        Card card3 = new Card(10,3);
        Card card4 = new Card(3,2);
        Card card5 = new Card(11,1);

        Hand hand = new Hand();
        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);
        hand.addCard(card4);
        hand.addCard(card5);

        String countString = Counter.count(hand);
        String[] count = countString.split("Total Score: ");
        System.out.println(count[0]);

        int finalCount = Integer.parseInt(count[1]);

        assertEquals(3, finalCount);
    }

    @Test
    public void runOfFour_isCorrect() throws Exception {

        Card card1 = new Card(9,1);
        Card card2 = new Card(8,2);
        Card card3 = new Card(10,3);
        Card card4 = new Card(3,2);
        Card card5 = new Card(11,1);

        Hand hand = new Hand();
        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);
        hand.addCard(card4);
        hand.addCard(card5);

        String countString = Counter.count(hand);
        String[] count = countString.split("Total Score: ");
        System.out.println(count[0]);

        int finalCount = Integer.parseInt(count[1]);

        assertEquals(4, finalCount);
    }

    public void runOfFive_isCorrect() throws Exception {

        Card card1 = new Card(9,1);
        Card card2 = new Card(8,2);
        Card card3 = new Card(10,3);
        Card card4 = new Card(12,2);
        Card card5 = new Card(11,1);

        Hand hand = new Hand();
        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);
        hand.addCard(card4);
        hand.addCard(card5);

        String countString = Counter.count(hand);
        String[] count = countString.split("Total Score: ");
        System.out.println(count[0]);

        int finalCount = Integer.parseInt(count[1]);

        assertEquals(5, finalCount);
    }
}