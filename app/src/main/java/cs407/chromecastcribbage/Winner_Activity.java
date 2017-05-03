package cs407.chromecastcribbage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.cast.games.GameManagerClient;
import com.google.android.gms.cast.games.GameManagerState;

import org.json.JSONException;
import org.json.JSONObject;

public class Winner_Activity extends AppCompatActivity implements GameManagerClient.Listener{

    TextView winnerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ChromeCast Cribbage");
        setSupportActionBar(toolbar);

        winnerText = (TextView) findViewById(R.id.winnerText);
        Bundle prev = getIntent().getExtras();
        winnerText.setText(prev.getString("winner") + " is the Winner!");

        Welcome_Activity.mCastConnectionManager.getGameManagerClient().setListener(this);

        JSONObject jsonMessage = new JSONObject();
        try {
            jsonMessage.put("displayChange", "displayChange");
        } catch (JSONException e) {
            Log.e("json", "Error creating JSON message", e);
            return;
        }
        Welcome_Activity.mCastConnectionManager.getGameManagerClient().sendGameMessage(jsonMessage);

    }

    public void newGame(View view) {
        JSONObject jsonMessage = new JSONObject();
        try {
            jsonMessage.put("newGame", "newGame");
        } catch (JSONException e) {
            Log.e("json", "Error creating JSON message", e);
            return;
        }
        Welcome_Activity.mCastConnectionManager.getGameManagerClient().sendGameMessage(jsonMessage);
        Intent intent = new Intent(this, Find_Dealer_Activity.class);
        startActivity(intent);
    }

    @Override
    public void onGameMessageReceived(String playerId, JSONObject message) {
        if (message.has("startAgain")) {
            Intent intent = new Intent(this, Find_Dealer_Activity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onStateChanged(GameManagerState gameManagerState, GameManagerState gameManagerState1) {

    }

}
