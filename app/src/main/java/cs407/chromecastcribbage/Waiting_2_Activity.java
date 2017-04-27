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

        prev = getIntent().getExtras();
        //numPlayers = 3;//prev.getInt("players");

        //mCastConnectionManager = Welcome_Activity.getCastConnectionManager();

        waiting();
    }

    public void waiting() {

        boolean playersReady = true;
        //TODO: Get player1 username from Chromecast and set it
/*        String player1 = "temp";
        playerOne.setText("   Player 1: " + player1);

        if(numPlayers == 3){
            playerThree.setVisibility(View.VISIBLE);
        } else if(numPlayers == 4){
            playerThree.setVisibility(View.VISIBLE);
            playerFour.setVisibility(View.VISIBLE);

            playerOne.setText("   Team 1: " + player1);
            playerTwo.setText("   Team 1: (Waiting for Player)");
            playerThree.setText("   Team 2: (Waiting for Player)");
            playerFour.setText("   Team 2: (Waiting for Player)");
        }*/

        //TODO: Get Player UserIds from Chromecast and set text views to names
        //TODO: When all spots are filled give start button to Player 1?  All Players?



        if(playersReady){
            Button button = (Button) findViewById(R.id.startButton);
            button.setVisibility(View.VISIBLE);
        }
    }

    public void startGame(View view) {
        //sendPlayerPlayingRequest();

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
        if (message.has("ready")) {
            try {
                String player1 = message.getString("player1");
                String player2 = message.getString("player2");

                playerOne.setText(player1);
                if(!player2.equals("")){
                    playerTwo.setVisibility(View.VISIBLE);
                    playerTwo.setText(player2);
                }




            } catch (JSONException e) {
                Log.e("json", "onGameMessageReceived", e);
            }
        }
    }

/*    public void sendPlayerPlayingRequest() {
        final GameManagerClient gameManagerClient =
                mCastConnectionManager.getGameManagerClient();
        if (mCastConnectionManager.isConnectedToReceiver()) {
            PendingResult<GameManagerClient.GameManagerResult> result =
                    gameManagerClient.sendPlayerPlayingRequest(null);
            result.setResultCallback(
                    new ResultCallback<GameManagerClient.GameManagerResult>() {
                        @Override
                        public void onResult(final GameManagerClient.GameManagerResult
                                                     gameManagerResult) {
                            if (gameManagerResult.getStatus().isSuccess()) {
                               // ((MainActivity) getActivity()).setPlayerState(
                                 //       gameManagerClient.getCurrentState().getPlayer(
                                   //             gameManagerResult.getPlayerId())
                                     //           .getPlayerState());
                            } else {
                                //mCastConnectionManager.disconnectFromReceiver(false);
                                //Utils.showErrorDialog(getActivity(),
                                      //  gameManagerResult.getStatus().getStatusMessage());


                            }
                        }
                    });
        }
    }*/
}
