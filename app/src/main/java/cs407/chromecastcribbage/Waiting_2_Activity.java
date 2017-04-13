package cs407.chromecastcribbage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Waiting_2_Activity extends AppCompatActivity {

    TextView playerOne;
    TextView playerTwo;
    TextView playerThree;
    TextView playerFour;
    Bundle prev;
    int numPlayers;
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
        numPlayers = prev.getInt("players");

        waiting();
    }

    public void waiting() {

        boolean playersReady = true;
        //TODO: Get player1 username from Chromecast and set it
        String player1 = "temp";
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
        }

        //TODO: Get Player UserIds from Chromecast and set text views to names
        //TODO: When all spots are filled give start button to Player 1?  All Players?



        if(playersReady){
            Button button = (Button) findViewById(R.id.startButton);
            button.setVisibility(View.VISIBLE);
        }
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, Find_Dealer_Activity.class);
        startActivity(intent);
    }
}
