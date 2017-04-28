package cs407.chromecastcribbage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.cast.games.GameManagerClient;
import com.google.android.gms.cast.games.GameManagerState;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Pegging_Activity extends AppCompatActivity implements GameManagerClient.Listener {

    Hand hand;
    Boolean yourTurn = false;
    TextView turnText;
    int count = 4;
    String cardName1;
    String cardName2;
    String cardName3;
    String cardName4;

    ImageView card1IV;
    ImageView card2IV;
    ImageView card3IV;
    ImageView card4IV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegging_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Chromecast Cribbage");
        setSupportActionBar(toolbar);

        Welcome_Activity.mCastConnectionManager.getGameManagerClient().setListener(this);

        hand = new Hand();

        Bundle prev = getIntent().getExtras();
        cardName1 = prev.getString("card1");
        cardName2 = prev.getString("card2");
        cardName3 = prev.getString("card3");
        cardName4 = prev.getString("card4");


        hand.addCard(new Card(String.valueOf(cardName1.charAt(0)), String.valueOf(cardName1.charAt(1))));
        hand.addCard(new Card(String.valueOf(cardName2.charAt(0)), String.valueOf(cardName2.charAt(1))));
        hand.addCard(new Card(String.valueOf(cardName3.charAt(0)), String.valueOf(cardName3.charAt(1))));
        hand.addCard(new Card(String.valueOf(cardName4.charAt(0)), String.valueOf(cardName4.charAt(1))));
        hand.sortByValue();


        card1IV = (ImageView) findViewById(R.id.cardOneIV);
        card2IV = (ImageView) findViewById(R.id.cardTwoIV);
        card3IV = (ImageView) findViewById(R.id.cardThreeIV);
        card4IV = (ImageView) findViewById(R.id.cardFourIV);

        Picasso.with(this).load("https://deckofcardsapi.com/static/img/" + cardName1 + ".png").placeholder(R.drawable.back).error(R.drawable.error).into(card1IV);
        Picasso.with(this).load("https://deckofcardsapi.com/static/img/" + cardName2 + ".png").placeholder(R.drawable.back).error(R.drawable.error).into(card2IV);
        Picasso.with(this).load("https://deckofcardsapi.com/static/img/" + cardName3 + ".png").placeholder(R.drawable.back).error(R.drawable.error).into(card3IV);
        Picasso.with(this).load("https://deckofcardsapi.com/static/img/" + cardName4 + ".png").placeholder(R.drawable.back).error(R.drawable.error).into(card4IV);

        turnText = (TextView) findViewById(R.id.turnText);

        // Get the currentTurn
        JSONObject jsonMessage = new JSONObject();
        try {
            jsonMessage.put("getTurn", "turn");
        } catch (JSONException e) {
            Log.e("json", "Error creating JSON message", e);
            return;
        }
        Welcome_Activity.mCastConnectionManager.getGameManagerClient().sendGameMessage(jsonMessage);
        turnText.setText("Waiting for Everyone to Finish Discarding");


    }

    public void waiting() {
        //TODO: Get whos turn it is to peg
        String userName = "temp";

    }

    public void playCard(View view) {
        Card playCard = new Card();

        if (yourTurn) {

            switch (view.getId()) {
                case R.id.cardOneIV:
                    playCard = hand.getCard(0);
                    count--;
                    card1IV.setVisibility(View.INVISIBLE);
                    break;
                case R.id.cardTwoIV:
                    playCard = hand.getCard(1);
                    count--;
                    card2IV.setVisibility(View.INVISIBLE);
                    break;
                case R.id.cardThreeIV:
                    playCard = hand.getCard(2);
                    count--;
                    card3IV.setVisibility(View.INVISIBLE);
                    break;
                case R.id.cardFourIV:
                    playCard = hand.getCard(3);
                    count--;
                    card4IV.setVisibility(View.INVISIBLE);
                    break;
            }

            JSONObject jsonMessage = new JSONObject();
            try {
                jsonMessage.put("pegging", "Yes");
                jsonMessage.put("pegCard", playCard.getIntValue());
                jsonMessage.put("pegCode", playCard.getFileName());
            } catch (JSONException e) {
                Log.e("json", "Error creating JSON message", e);
                return;
            }
            Welcome_Activity.mCastConnectionManager.getGameManagerClient().sendGameMessage(jsonMessage);

            yourTurn = false;
        } else {
            Toast.makeText(this, "Its not your turn", Toast.LENGTH_LONG).show();
        }

        if (count == 0) {

        }
    }

    @Override
    public void onStateChanged(GameManagerState gameManagerState, GameManagerState gameManagerState1) {

    }

    @Override
    public void onGameMessageReceived(String playerId, JSONObject message) {
        if (message.has("turn")) {
            try {
                String turnUserName = message.getString("turn");
                String playerUserName = message.getString("player");

                if (turnUserName.equals(playerUserName)) {
                    turnText.setText("Its your turn to select a card for pegging");
                    yourTurn = true;
                } else {
                    turnText.setText("Its " + turnUserName + " turn to select a card for pegging");
                    yourTurn = false;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (message.has("toCountScreen")) {
            //TODO: Get turn card from Chromecast and add it to hand
            try {
                String turnCardCode = message.getString("toCountScreen");

                hand.addCard(new Card(String.valueOf(turnCardCode.charAt(0)), String.valueOf(turnCardCode.charAt(1))));
                hand.sortByValueLowHigh();

                String countString = Counter.count(hand);
                String[] score = countString.split("Total Score: ");

                JSONObject jsonMessage = new JSONObject();
                try {
                    jsonMessage.put("count", "Yes");
                    jsonMessage.put("handCountString", score[0]);
                    jsonMessage.put("handCount", score[1]);
                } catch (JSONException e) {
                    Log.e("json", "Error creating JSON message", e);
                    return;
                }
                Welcome_Activity.mCastConnectionManager.getGameManagerClient().sendGameMessage(jsonMessage);

                Intent intent = new Intent(this, Count_Screen_Activity.class);
                intent.putExtra("card1", cardName1);
                intent.putExtra("card2", cardName2);
                intent.putExtra("card3", cardName3);
                intent.putExtra("card4", cardName4);
                intent.putExtra("turnCard", turnCardCode);
                intent.putExtra("countString", countString);
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
