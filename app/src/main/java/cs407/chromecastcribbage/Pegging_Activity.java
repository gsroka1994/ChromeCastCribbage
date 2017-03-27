package cs407.chromecastcribbage;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Pegging_Activity extends AppCompatActivity {

    Button card1;
    Button card2;
    Button card3;
    Button card4;
    Button turnCardButton;
    Hand hand;
    Card turnCard;
    Boolean yourTurn = true;
    TextView turnText;
    int count = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegging_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Chromecast Cribbage");
        setSupportActionBar(toolbar);

        hand = new Hand();

        Bundle prev = getIntent().getExtras();
        hand.addCard(new Card(prev.getInt("card1Val"), prev.getInt("card1Suit")));
        hand.addCard(new Card(prev.getInt("card2Val"), prev.getInt("card2Suit")));
        hand.addCard(new Card(prev.getInt("card3Val"), prev.getInt("card3Suit")));
        hand.addCard(new Card(prev.getInt("card4Val"), prev.getInt("card4Suit")));
        // TurnCard stuff
        turnCard = new Card(prev.getInt("turnCardVal"), prev.getInt("turnCardSuit"));
        hand.sortByValue();

        card1 = (Button) findViewById(R.id.cardOneButton);
        card2 = (Button) findViewById(R.id.cardTwoButton);
        card3 = (Button) findViewById(R.id.cardThreeButton);
        card4 = (Button) findViewById(R.id.cardFourButton);
        turnCardButton = (Button) findViewById(R.id.turnCard);

        //card1.setImageResource(R.drawable.ace_of_clubs);
        card1.setText(hand.getCard(0).toString());
        card2.setText(hand.getCard(1).toString());
        card3.setText(hand.getCard(2).toString());
        card4.setText(hand.getCard(3).toString());
        turnCardButton.setText(turnCard.toString());

        turnText = (TextView) findViewById(R.id.turnText);
        if(yourTurn){
            turnText.setText("Its your turn to select a card for pegging");
        }

    }


    public void playCard(View view) {
        Card playCard = new Card();


        if (yourTurn) {

            switch (view.getId()) {
                case R.id.cardOneButton:
                    playCard = hand.getCard(0);
                    count--;
                    card1.setVisibility(View.INVISIBLE);
                    break;
                case R.id.cardTwoButton:
                    playCard = hand.getCard(1);
                    count--;
                    card2.setVisibility(View.INVISIBLE);
                    break;
                case R.id.cardThreeButton:
                    playCard = hand.getCard(2);
                    count--;
                    card3.setVisibility(View.INVISIBLE);
                    break;
                case R.id.cardFourButton:
                    playCard = hand.getCard(3);
                    count--;
                    card4.setVisibility(View.INVISIBLE);
                    break;
            }

            //TODO: Send playCard to Chromecast
            Toast.makeText(this, "You have played " + playCard, Toast.LENGTH_LONG).show();
            turnText.setText("Its USERNAME's turn to select a card for pegging");
            //yourTurn = false;

        }
        else{
            Toast.makeText(this, "Its not your turn", Toast.LENGTH_LONG).show();
        }

        if(count == 0){
            //TODO: Get turn card from Chromecast and add it to hand
            hand.addCard(turnCard);
            String countString = Counter.count(hand);
            Toast.makeText(this,countString, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, Count_Screen_Activity.class);
            intent.putExtra("card1", hand.getCard(0).toString());
            intent.putExtra("card2", hand.getCard(1).toString());
            intent.putExtra("card3", hand.getCard(2).toString());
            intent.putExtra("card4", hand.getCard(3).toString());
            intent.putExtra("turnCard", hand.getCard(4).toString());
            intent.putExtra("countString", countString);
            startActivity(intent);
        }


    }
}
