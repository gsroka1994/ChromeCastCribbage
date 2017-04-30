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
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Waiting_2_Activity extends AppCompatActivity implements GameManagerClient.Listener {

    TextView playerOne;
    TextView playerTwo;
    TextView playerThree;
    TextView playerFour;
    Button startButton;
    Bundle prev;
    int numPlayers;
    //private static CastConnectionManager mCastConnectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_2_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ChromeCast Cribbage");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        playerOne = (TextView) findViewById(R.id.playerOne);
        playerTwo = (TextView) findViewById(R.id.playerTwo);
        playerThree = (TextView) findViewById(R.id.playerThree);
        playerFour = (TextView) findViewById(R.id.playerFour);

        //prev = getIntent().getExtras();
        //numPlayers = 3;//prev.getInt("players");

        //mCastConnectionManager = Welcome_Activity.getCastConnectionManager();
        startButton = (Button) findViewById(R.id.startButton);

        Welcome_Activity.mCastConnectionManager.getGameManagerClient().setListener(this);

        JSONObject jsonMessage = new JSONObject();
        try {
            jsonMessage.put("getPlayers", "yes");
        } catch (JSONException e) {
            Log.e("json", "Error creating JSON message", e);
            return;
        }
        Welcome_Activity.mCastConnectionManager.getGameManagerClient().sendGameMessage(jsonMessage);

    }

    public void startGame(View view) {

        JSONObject jsonMessage = new JSONObject();
        try {
            jsonMessage.put("startGame", "start");
        } catch (JSONException e) {
            Log.e("json", "Error creating JSON message", e);
            return;
        }
        Welcome_Activity.mCastConnectionManager.getGameManagerClient().sendGameMessage(jsonMessage);
        Intent intent = new Intent(this, Find_Dealer_Activity.class);
        startActivity(intent);
    }

    @Override
    public void onStateChanged(GameManagerState gameManagerState, GameManagerState gameManagerState1) {

    }

    @Override
    public void onGameMessageReceived(String playerId, JSONObject message) {
        if (message.has("player1")) {
            try {
                String player1 = message.getString("player1");
                String player2 = message.getString("player2");
                playerOne.setVisibility(View.VISIBLE);
                playerOne.setText("Player 1: " + player1);
                if(!player2.equals("")){
                    playerTwo.setVisibility(View.VISIBLE);
                    playerTwo.setText("Player 2: " + player2);
                    startButton.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                Log.e("json", "onGameMessageReceived", e);
            }
        } else if(message.has("startGame")){
            Intent intent = new Intent(this, Find_Dealer_Activity.class);
            startActivity(intent);
        }
    }

}
