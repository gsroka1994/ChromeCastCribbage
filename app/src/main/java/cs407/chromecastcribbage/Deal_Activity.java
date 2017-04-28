package cs407.chromecastcribbage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.cast.games.GameManagerClient;
import com.google.android.gms.cast.games.GameManagerState;

import org.json.JSONException;
import org.json.JSONObject;

public class Deal_Activity extends AppCompatActivity implements GameManagerClient.Listener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ChromeCast Cribbage");
        setSupportActionBar(toolbar);

        Welcome_Activity.mCastConnectionManager.getGameManagerClient().setListener(this);

        JSONObject jsonMessage = new JSONObject();
        try {
            jsonMessage.put("getDealer", "dealer");
        } catch (JSONException e) {
            Log.e("json", "Error creating JSON message", e);
            return;
        }
        Welcome_Activity.mCastConnectionManager.getGameManagerClient().sendGameMessage(jsonMessage);

    }

    public void dealCards(View view) {
        JSONObject jsonMessage = new JSONObject();
        try {
            jsonMessage.put("deal", "deal");
        } catch (JSONException e) {
            Log.e("json", "Error creating JSON message", e);
            return;
        }
        Welcome_Activity.mCastConnectionManager.getGameManagerClient().sendGameMessage(jsonMessage);
        Intent intent = new Intent(this, Discard_Activity.class);
        startActivity(intent);
    }

    @Override
    public void onStateChanged(GameManagerState gameManagerState, GameManagerState gameManagerState1) {

    }

    @Override
    public void onGameMessageReceived(String playerId, JSONObject message) {
        if (message.has("dealer")) {
            try {
                String userName = message.getString("dealer");
                String playerName = message.getString("yourName");

                if(!playerName.equals(userName)){
                    Button button = (Button) findViewById(R.id.dealButton);
                    button.setVisibility(View.INVISIBLE);
                    TextView textView = (TextView) findViewById(R.id.dealText);
                    textView.setText("Waiting for " + userName + " to start dealing.");
                } else {
                    Button button = (Button) findViewById(R.id.dealButton);
                    button.setVisibility(View.VISIBLE);
                    TextView textView = (TextView) findViewById(R.id.dealText);
                    textView.setText("Its Your Deal.  Hit Deal when you are Ready");
                }

            } catch (JSONException e) {
                Log.e("json", "onGameMessageReceived", e);
            }
        }
    }
}
