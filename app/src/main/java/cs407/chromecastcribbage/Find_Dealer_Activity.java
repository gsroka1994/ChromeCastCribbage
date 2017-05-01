package cs407.chromecastcribbage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.cast.games.GameManagerClient;
import com.google.android.gms.cast.games.GameManagerState;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.Thread.sleep;

public class Find_Dealer_Activity extends AppCompatActivity implements GameManagerClient.Listener{

    boolean selected = false;
    String card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find__dealer_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ChromeCast Cribbage");
        setSupportActionBar(toolbar);

        Welcome_Activity.mCastConnectionManager.getGameManagerClient().setListener(this);
        JSONObject jsonMessage = new JSONObject();
        try {
            jsonMessage.put("getDealerCard", "card");
        } catch (JSONException e) {
            Log.e("json", "Error creating JSON message", e);
            return;
        }
        Welcome_Activity.mCastConnectionManager.getGameManagerClient().sendGameMessage(jsonMessage);


    }

    public void selectCard(View view) {

        if(!selected){
            selected = true;

            ImageView cardIV = (ImageView) findViewById(R.id.cardIV);
            cardIV.bringToFront();

            Picasso.with(this).load("https://deckofcardsapi.com/static/img/"+card+".png").placeholder(R.drawable.back).error(R.drawable.error).into(cardIV);

            cardIV.setVisibility(View.VISIBLE);

            ImageView selectedCard = (ImageView) findViewById(view.getId());
            selectedCard.setVisibility(View.INVISIBLE);

            JSONObject jsonMessage = new JSONObject();
            try {
                jsonMessage.put("toDealScreen", "toDealScreen");
            } catch (JSONException e) {
                Log.e("json", "Error creating JSON message", e);
                return;
            }
            Welcome_Activity.mCastConnectionManager.getGameManagerClient().sendGameMessage(jsonMessage);
        }
    }

    @Override
    public void onGameMessageReceived(String playerId, JSONObject message) {
        if (message.has("code")) {
            try {
                card = message.getString("code");
            } catch (JSONException e) {
                Log.e("json", "onGameMessageReceived", e);
            }
        } else if(message.has("toDealScreen")) {
            Intent intent = new Intent(this, Deal_Activity.class);
            startActivity(intent);
        } else if (message.has("sameHand")){
            Intent intent = new Intent(this, Find_Dealer_Activity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onStateChanged(GameManagerState gameManagerState, GameManagerState gameManagerState1) {

    }

}
