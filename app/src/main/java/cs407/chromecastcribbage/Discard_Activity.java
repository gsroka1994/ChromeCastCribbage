package cs407.chromecastcribbage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.cast.games.GameManagerClient;
import com.google.android.gms.cast.games.GameManagerState;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Discard_Activity extends AppCompatActivity implements GameManagerClient.Listener{

    Hand hand;
    Hand crib;
    Card card1;
    Card card2;
    Card card3;
    Card card4;
    Card card5;
    Card card6;
    int cribSize = 0;
    Boolean selected1 = false;
    Boolean selected2 = false;
    Boolean selected3 = false;
    Boolean selected4 = false;
    Boolean selected5 = false;
    Boolean selected6 = false;
    String dealer = null;
    int margin;

    ImageView card1IV;
    ImageView card2IV;
    ImageView card3IV;
    ImageView card4IV;
    ImageView card5IV;
    ImageView card6IV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discard_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ChromeCast Cribbage");
        setSupportActionBar(toolbar);

        Welcome_Activity.mCastConnectionManager.getGameManagerClient().setListener(this);

        hand = new Hand();

        //TODO: Get Cards codes from the chromecast

        // Get the dealer
        JSONObject jsonMessage = new JSONObject();
        try {
            jsonMessage.put("getDealer", "dealer");
        } catch (JSONException e) {
            Log.e("json", "Error creating JSON message", e);
            return;
        }
        Welcome_Activity.mCastConnectionManager.getGameManagerClient().sendGameMessage(jsonMessage);



        int dpValue = 20; // margin in dips
        float d = this.getResources().getDisplayMetrics().density;
        margin = (int)(dpValue * d);

        crib = new Hand();
    }

    public void discardCards(View view) {

        if (cribSize < 2) {
            Toast.makeText(this, "You need to select 2 cards for the crib", Toast.LENGTH_LONG).show();

        } else {

            //TODO: Send Crib Cards to the Chromecast

            JSONObject jsonMessage = new JSONObject();
            try {
                jsonMessage.put("cribSet", "Yes");
                jsonMessage.put("crib1", crib.getCard(0).getFileName());
                jsonMessage.put("crib2", crib.getCard(1).getFileName());
            } catch (JSONException e) {
                Log.e("json", "Error creating JSON message", e);
                return;
            }
            Welcome_Activity.mCastConnectionManager.getGameManagerClient().sendGameMessage(jsonMessage);

            Intent intent = new Intent(this, Pegging_Activity.class);
            intent.putExtra("card1", hand.getCard(0).getFileName());
            intent.putExtra("card2", hand.getCard(1).getFileName());
            intent.putExtra("card3", hand.getCard(2).getFileName());
            intent.putExtra("card4", hand.getCard(3).getFileName());
            startActivity(intent);

        }
    }

    public void addCardToCrib(View view) {

        LinearLayout.LayoutParams rel_btn;

        switch (view.getId()) {
            case R.id.cardOneIV:
                rel_btn = (LinearLayout.LayoutParams) card1IV.getLayoutParams();
                if (!selected1 && cribSize < 2) {
                    selected1 =true;
                    crib.addCard(card1);
                    cribSize++;
                    hand.removeCard(card1);
                    rel_btn.topMargin = 0;
                } else if (selected1) {
                    selected1 =false;
                    crib.removeCard(card1);
                    hand.addCard(card1);
                    cribSize--;
                    rel_btn.topMargin = margin;
                }
                card1IV.setLayoutParams(rel_btn);
                break;
            case R.id.cardTwoIV:
                rel_btn = (LinearLayout.LayoutParams) card2IV.getLayoutParams();
                if (!selected2 && cribSize < 2) {
                    selected2 =true;
                    crib.addCard(card2);
                    cribSize++;
                    hand.removeCard(card2);
                    rel_btn.topMargin = 0;
                    card2IV.setLayoutParams(rel_btn);
                } else if (selected2) {
                    selected2 =false;
                    crib.removeCard(card2);
                    hand.addCard(card2);
                    cribSize--;
                    rel_btn.topMargin = margin;
                    card2IV.setLayoutParams(rel_btn);
                }
                break;
            case R.id.cardThreeIV:
                rel_btn = (LinearLayout.LayoutParams) card3IV.getLayoutParams();
                if (!selected3 && cribSize < 2) {
                    selected3 =true;
                    crib.addCard(card3);
                    cribSize++;
                    hand.removeCard(card3);
                    rel_btn.topMargin = 0;
                    card3IV.setLayoutParams(rel_btn);
                } else if (selected3) {
                    selected3 =false;
                    crib.removeCard(card3);
                    hand.addCard(card3);
                    cribSize--;
                    rel_btn.topMargin = margin;
                    card3IV.setLayoutParams(rel_btn);
                }
                break;
            case R.id.cardFourIV:
                rel_btn = (LinearLayout.LayoutParams) card4IV.getLayoutParams();
                if (!selected4 && cribSize < 2) {
                    selected4 =true;
                    crib.addCard(card4);
                    cribSize++;
                    hand.removeCard(card4);
                    rel_btn.topMargin = 0;
                    card4IV.setLayoutParams(rel_btn);
                } else if (selected4) {
                    selected4 =false;
                    crib.removeCard(card4);
                    hand.addCard(card4);
                    cribSize--;
                    rel_btn.topMargin = margin;
                    card4IV.setLayoutParams(rel_btn);
                }
                break;
            case R.id.cardFiveIV:
                rel_btn = (LinearLayout.LayoutParams) card5IV.getLayoutParams();
                if (!selected5 && cribSize < 2) {
                    selected5 =true;
                    crib.addCard(card5);
                    cribSize++;
                    hand.removeCard(card5);
                    rel_btn.topMargin = 0;
                    card5IV.setLayoutParams(rel_btn);
                } else if (selected5) {
                    selected5 =false;
                    crib.removeCard(card5);
                    hand.addCard(card5);
                    cribSize--;
                    rel_btn.topMargin = margin;
                    card5IV.setLayoutParams(rel_btn);
                }
                break;
            case R.id.cardSixIV:
                rel_btn = (LinearLayout.LayoutParams) card6IV.getLayoutParams();
                if (!selected6 && cribSize < 2) {
                    selected6 =true;
                    crib.addCard(card6);
                    cribSize++;
                    hand.removeCard(card6);
                    rel_btn.topMargin = 0;
                    card6IV.setLayoutParams(rel_btn);
                } else if (selected6) {
                    selected6 =false;
                    crib.removeCard(card6);
                    hand.addCard(card6);
                    cribSize--;
                    rel_btn.topMargin = margin;
                    card6IV.setLayoutParams(rel_btn);
                }
                break;
        }

    }

    @Override
    public void onStateChanged(GameManagerState gameManagerState, GameManagerState gameManagerState1) {

    }

    @Override
    public void onGameMessageReceived(String playerId, JSONObject message) {

        if(message.has("dealer")){
            try {
                dealer = message.getString("dealer");
                TextView msg = (TextView) findViewById(R.id.discardMsg);
                msg.setText("Select two cards for " + dealer + "'s crib");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Get the hand
            JSONObject jsonMessage = new JSONObject();
            try {
                jsonMessage.put("getHand", "getHand");
            } catch (JSONException e) {
                Log.e("json", "Error creating JSON message", e);
                return;
            }
            Welcome_Activity.mCastConnectionManager.getGameManagerClient().sendGameMessage(jsonMessage);

        } else if (message.has("card1")) {
            try {
                String code1 = message.getString("card1");
                String code2 = message.getString("card2");
                String code3 = message.getString("card3");
                String code4 = message.getString("card4");
                String code5 = message.getString("card5");
                String code6 = message.getString("card6");

                hand.addCard(new Card(String.valueOf(code1.charAt(0)),String.valueOf(code1.charAt(1))));
                hand.addCard(new Card(String.valueOf(code2.charAt(0)),String.valueOf(code2.charAt(1))));
                hand.addCard(new Card(String.valueOf(code3.charAt(0)),String.valueOf(code3.charAt(1))));
                hand.addCard(new Card(String.valueOf(code4.charAt(0)),String.valueOf(code4.charAt(1))));
                hand.addCard(new Card(String.valueOf(code5.charAt(0)),String.valueOf(code5.charAt(1))));
                hand.addCard(new Card(String.valueOf(code6.charAt(0)),String.valueOf(code6.charAt(1))));
                hand.sortByValueLowHigh();

                card1IV = (ImageView) findViewById(R.id.cardOneIV);
                card2IV = (ImageView) findViewById(R.id.cardTwoIV);
                card3IV = (ImageView) findViewById(R.id.cardThreeIV);
                card4IV = (ImageView) findViewById(R.id.cardFourIV);
                card5IV = (ImageView) findViewById(R.id.cardFiveIV);
                card6IV = (ImageView) findViewById(R.id.cardSixIV);

                card1 = hand.getCard(0);
                card2 = hand.getCard(1);
                card3 = hand.getCard(2);
                card4 = hand.getCard(3);
                card5 = hand.getCard(4);
                card6 = hand.getCard(5);

                Picasso.with(this).load("https://deckofcardsapi.com/static/img/"+card1.getFileName()+".png").placeholder(R.drawable.back).error(R.drawable.error).into(card1IV);
                Picasso.with(this).load("https://deckofcardsapi.com/static/img/"+card2.getFileName()+".png").placeholder(R.drawable.back).error(R.drawable.error).into(card2IV);
                Picasso.with(this).load("https://deckofcardsapi.com/static/img/"+card3.getFileName()+".png").placeholder(R.drawable.back).error(R.drawable.error).into(card3IV);
                Picasso.with(this).load("https://deckofcardsapi.com/static/img/"+card4.getFileName()+".png").placeholder(R.drawable.back).error(R.drawable.error).into(card4IV);
                Picasso.with(this).load("https://deckofcardsapi.com/static/img/"+card5.getFileName()+".png").placeholder(R.drawable.back).error(R.drawable.error).into(card5IV);
                Picasso.with(this).load("https://deckofcardsapi.com/static/img/"+card6.getFileName()+".png").placeholder(R.drawable.back).error(R.drawable.error).into(card6IV);

            } catch (JSONException e) {
                Log.e("json", "onGameMessageReceived", e);
            }
        }  else if((message.has("sendP2Hand"))){
            // Get the hand
            JSONObject jsonMessage = new JSONObject();
            try {
                jsonMessage.put("getP2Hand", "getHand");
            } catch (JSONException e) {
                Log.e("json", "Error creating JSON message", e);
                return;
            }
            Welcome_Activity.mCastConnectionManager.getGameManagerClient().sendGameMessage(jsonMessage);
        }
    }

}
