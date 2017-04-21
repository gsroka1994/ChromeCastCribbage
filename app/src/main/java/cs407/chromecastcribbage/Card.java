package cs407.chromecastcribbage;

public class Card {

    private final String suit;
    private final String value;

    public Card(){
        suit="";
        value="";
    }

    public Card(String theValue, String theSuit) {
        value = theValue;
        suit = theSuit;
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public int getIntSuit() {
        switch (suit) {
            case "S":
                return 1;
            case "H":
                return 2;
            case "D":
                return 3;
            default:
                return 4;
        }
    }

    public int getIntValue() {

        String stringValue = value;
        switch (stringValue) {
            case "A":
                stringValue = "1";
                break;
            case "J":
                stringValue = "11";
                break;
            case "Q":
                stringValue = "12";
                break;
            case "K":
                stringValue = "13";
                break;
        }
        return Integer.valueOf(stringValue);
    }

    public String getFileName() {
        return value + suit;
    }
}