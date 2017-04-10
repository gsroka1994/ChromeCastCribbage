package cs407.chromecastcribbage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

public class Discard_Activity extends AppCompatActivity {

    Button card1Button;
    Button card2Button;
    Button card3Button;
    Button card4Button;
    Button card5Button;
    Button card6Button;
    Hand hand;
    Hand crib;
    Card card1;
    Card card2;
    Card card3;
    Card card4;
    Card card5;
    Card card6;
    Card turnCard;
    int cribSize = 0;
    Boolean selcted1 = false;
    Boolean selcted2 = false;
    Boolean selcted3 = false;
    Boolean selcted4 = false;
    Boolean selcted5 = false;
    Boolean selcted6 = false;
    int margin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discard_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ChromeCast Cribbage");
        setSupportActionBar(toolbar);

        Deck deck = new Deck();
        hand = new Hand();

        deck.shuffle();

        hand.addCard(deck.dealCard());
        hand.addCard(deck.dealCard());
        hand.addCard(deck.dealCard());
        hand.addCard(deck.dealCard());
        hand.addCard(deck.dealCard());
        hand.addCard(deck.dealCard());
        hand.sortByValue();

        turnCard = deck.dealCard();

        //ImageButton card1 = (ImageButton) findViewById(R.id.cardOneButton);
        card1Button = (Button) findViewById(R.id.cardOneButton);
        card2Button = (Button) findViewById(R.id.cardTwoButton);
        card3Button = (Button) findViewById(R.id.cardThreeButton);
        card4Button = (Button) findViewById(R.id.cardFourButton);
        card5Button = (Button) findViewById(R.id.cardFiveButton);
        card6Button = (Button) findViewById(R.id.cardSixButton);

        card1 = hand.getCard(0);
        card2 = hand.getCard(1);
        card3 = hand.getCard(2);
        card4 = hand.getCard(3);
        card5 = hand.getCard(4);
        card6 = hand.getCard(5);

        //card1.setImageResource(R.drawable.ace_of_clubs);
        //card1Button.setText(card1.toString());
        int id;
        Context context = card1Button.getContext();
        id = context.getResources().getIdentifier(card1.getFileName(), "drawable", context.getPackageName());
        card1Button.setBackgroundResource(id);
        id = context.getResources().getIdentifier(card2.getFileName(), "drawable", context.getPackageName());
        card2Button.setBackgroundResource(id);
        id = context.getResources().getIdentifier(card3.getFileName(), "drawable", context.getPackageName());
        card3Button.setBackgroundResource(id);
        id = context.getResources().getIdentifier(card4.getFileName(), "drawable", context.getPackageName());
        card4Button.setBackgroundResource(id);
        id = context.getResources().getIdentifier(card5.getFileName(), "drawable", context.getPackageName());
        card5Button.setBackgroundResource(id);
        id = context.getResources().getIdentifier(card6.getFileName(), "drawable", context.getPackageName());
        card6Button.setBackgroundResource(id);

        /*card2Button.setText(card2.toString());
        card3Button.setText(card3.toString());
        card4Button.setText(card4.toString());
        card5Button.setText(card5.toString());
        card6Button.setText(card6.toString());*/

        int dpValue = 20; // margin in dips
        float d = this.getResources().getDisplayMetrics().density;
        margin = (int)(dpValue * d);


    }

    public void discardCards(View view) {

        if (cribSize < 2) {
            Toast.makeText(this, "You need to select 2 cards for the crib", Toast.LENGTH_LONG).show();

        } else {
            Intent intent = new Intent(this, Pegging_Activity.class);
            intent.putExtra("card1Suit", hand.getCard(0).getSuit());
            intent.putExtra("card1Val", hand.getCard(0).getValue());
            intent.putExtra("card2Suit", hand.getCard(1).getSuit());
            intent.putExtra("card2Val", hand.getCard(1).getValue());
            intent.putExtra("card3Suit", hand.getCard(2).getSuit());
            intent.putExtra("card3Val", hand.getCard(2).getValue());
            intent.putExtra("card4Suit", hand.getCard(3).getSuit());
            intent.putExtra("card4Val", hand.getCard(3).getValue());
            // Turn card logic
            intent.putExtra("turnCardSuit", turnCard.getSuit());
            intent.putExtra("turnCardVal", turnCard.getValue());
            startActivity(intent);
        }
    }

    public void addCardToCrib(View view) {
        crib = new Hand();
        Card card = new Card();

        if (cribSize >= 2) {
            Toast.makeText(this, "You have already selected 2 cards for the crib\n You need to deselect one before selecting a new one", Toast.LENGTH_LONG).show();
        }

        LinearLayout.LayoutParams rel_btn;


        switch (view.getId()) {
            case R.id.cardOneButton:
                rel_btn = (LinearLayout.LayoutParams) card1Button.getLayoutParams();
                if (!selcted1 && cribSize < 2) {
                    selcted1=true;
                    crib.addCard(card1);
                    cribSize++;
                    hand.removeCard(card1);
                    rel_btn.topMargin = 0;
                } else if (selcted1) {
                    selcted1=false;
                    crib.removeCard(card1);
                    hand.addCard(card1);
                    cribSize--;
                    rel_btn.topMargin = margin;
                }
                card1Button.setLayoutParams(rel_btn);
                break;
            case R.id.cardTwoButton:
                rel_btn = (LinearLayout.LayoutParams) card2Button.getLayoutParams();
                if (!selcted2 && cribSize < 2) {
                    selcted2=true;
                    crib.addCard(card2);
                    cribSize++;
                    hand.removeCard(card2);
                    rel_btn.topMargin = 0;
                    card2Button.setLayoutParams(rel_btn);
                } else if (selcted2) {
                    selcted2=false;
                    crib.removeCard(card2);
                    hand.addCard(card2);
                    cribSize--;
                    rel_btn.topMargin = margin;
                    card2Button.setLayoutParams(rel_btn);
                }
                break;
            case R.id.cardThreeButton:
                rel_btn = (LinearLayout.LayoutParams) card3Button.getLayoutParams();
                if (!selcted3 && cribSize < 2) {
                    selcted3=true;
                    crib.addCard(card3);
                    cribSize++;
                    hand.removeCard(card3);
                    rel_btn.topMargin = 0;
                    card3Button.setLayoutParams(rel_btn);
                } else if (selcted3) {
                    selcted3=false;
                    crib.removeCard(card3);
                    hand.addCard(card3);
                    cribSize--;
                    rel_btn.topMargin = margin;
                    card3Button.setLayoutParams(rel_btn);
                }
                break;
            case R.id.cardFourButton:
                rel_btn = (LinearLayout.LayoutParams) card4Button.getLayoutParams();
                if (!selcted4 && cribSize < 2) {
                    selcted4=true;
                    crib.addCard(card4);
                    cribSize++;
                    hand.removeCard(card4);
                    rel_btn.topMargin = 0;
                    card4Button.setLayoutParams(rel_btn);
                } else if (selcted4) {
                    selcted4=false;
                    crib.removeCard(card4);
                    hand.addCard(card4);
                    cribSize--;
                    rel_btn.topMargin = margin;
                    card4Button.setLayoutParams(rel_btn);
                }
                break;
            case R.id.cardFiveButton:
                rel_btn = (LinearLayout.LayoutParams) card5Button.getLayoutParams();
                if (!selcted5 && cribSize < 2) {
                    selcted5=true;
                    crib.addCard(card5);
                    cribSize++;
                    hand.removeCard(card5);
                    rel_btn.topMargin = 0;
                    card5Button.setLayoutParams(rel_btn);
                } else if (selcted5) {
                    selcted5=false;
                    crib.removeCard(card5);
                    hand.addCard(card5);
                    cribSize--;
                    rel_btn.topMargin = margin;
                    card5Button.setLayoutParams(rel_btn);
                }
                break;
            case R.id.cardSixButton:
                rel_btn = (LinearLayout.LayoutParams) card6Button.getLayoutParams();
                if (!selcted6 && cribSize < 2) {
                    selcted6=true;
                    crib.addCard(card6);
                    cribSize++;
                    hand.removeCard(card6);
                    rel_btn.topMargin = 0;
                    card6Button.setLayoutParams(rel_btn);
                } else if (selcted6) {
                    selcted6=false;
                    crib.removeCard(card6);
                    hand.addCard(card6);
                    cribSize--;
                    rel_btn.topMargin = margin;
                    card6Button.setLayoutParams(rel_btn);
                }
                break;
        }

    }
}
