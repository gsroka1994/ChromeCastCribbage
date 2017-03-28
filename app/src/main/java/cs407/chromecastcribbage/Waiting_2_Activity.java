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

        if(prev.getInt("players") == 3){
            playerFour.setVisibility(View.INVISIBLE);
        } else if(prev.getInt("players") == 2){
            playerFour.setVisibility(View.INVISIBLE);
            playerThree.setVisibility(View.INVISIBLE);
        }

        playerOne.setText("Player One: "+prev.getString("userName"));

    }



    public void waiting() {
        Button button = (Button) findViewById(R.id.startButton);
        button.setVisibility(View.VISIBLE);
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, Find_Dealer_Activity.class);
        startActivity(intent);
    }
}
