package cs407.chromecastcribbage;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Count_Screen_Activity extends AppCompatActivity {

    Button card1;
    Button card2;
    Button card3;
    Button card4;
    Button turnCardButton;
    TextView countView;
    TextView totalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count__screen_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ChromeCast Cribbage");
        setSupportActionBar(toolbar);

        Bundle prev = getIntent().getExtras();

        card1 = (Button) findViewById(R.id.cardOneButton);
        card2 = (Button) findViewById(R.id.cardTwoButton);
        card3 = (Button) findViewById(R.id.cardThreeButton);
        card4 = (Button) findViewById(R.id.cardFourButton);
        turnCardButton = (Button) findViewById(R.id.turnCardButton);
        countView = (TextView) findViewById(R.id.countString);
        totalScore = (TextView) findViewById(R.id.totalScore);

        //card1.setImageResource(R.drawable.ace_of_clubs);
        card1.setText(prev.getString("card1"));
        card2.setText(prev.getString("card2"));
        card3.setText(prev.getString("card3"));
        card4.setText(prev.getString("card4"));
        turnCardButton.setText(prev.getString("turnCard"));
        String countString = prev.getString("countString");
        String[] count = countString.split("Total Score:");
        countView.setText(count[0]);
        totalScore.setText("TOTAL\n " + count[1]);

    }

}
