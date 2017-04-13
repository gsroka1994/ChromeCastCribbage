package cs407.chromecastcribbage;

import android.content.Context;
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
    TextView countView1;
    TextView countView2;
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
        countView1 = (TextView) findViewById(R.id.countString1);
        countView2 = (TextView) findViewById(R.id.countString2);
        totalScore = (TextView) findViewById(R.id.totalScore1);

        int id;
        Context context = card1.getContext();
        id = context.getResources().getIdentifier(prev.getString("card1"), "drawable", context.getPackageName());
        card1.setBackgroundResource(id);
        id = context.getResources().getIdentifier(prev.getString("card2"), "drawable", context.getPackageName());
        card2.setBackgroundResource(id);
        id = context.getResources().getIdentifier(prev.getString("card3"), "drawable", context.getPackageName());
        card3.setBackgroundResource(id);
        id = context.getResources().getIdentifier(prev.getString("card4"), "drawable", context.getPackageName());
        card4.setBackgroundResource(id);
        id = context.getResources().getIdentifier(prev.getString("turnCard"), "drawable", context.getPackageName());
        turnCardButton.setBackgroundResource(id);

        //card1.setImageResource(R.drawable.ace_of_clubs);
        /*card1.setText(prev.getString("card1"));
        card2.setText(prev.getString("card2"));
        card3.setText(prev.getString("card3"));
        card4.setText(prev.getString("card4"));
        turnCardButton.setText(prev.getString("turnCard"));*/
        String countString = prev.getString("countString");
        String[] score = countString.split("\n");
        String breakdown1 = "";
        String breakdown2 = "";
        int counter = 0;
        for(String line : score){
            if(!line.endsWith("0") && !line.startsWith("Total Score: ")){
                if(counter < 5){
                    breakdown1 = breakdown1 + line + "\n";
                } else{
                    breakdown2 = breakdown2 + line + "\n";
                }

            }
        }
        countView1.setText(breakdown1);
        countView2.setText(breakdown2);
        String[] count = score[9].split("Total Score: ");
        totalScore.setText(count[1]);

        //TODO: When the Chromecast is ready move to the deal screen

    }

}
