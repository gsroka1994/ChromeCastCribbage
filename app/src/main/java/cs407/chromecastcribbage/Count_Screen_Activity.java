package cs407.chromecastcribbage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Count_Screen_Activity extends AppCompatActivity {

    ImageView card1;
    ImageView card2;
    ImageView card3;
    ImageView card4;
    ImageView turnCard;
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

        card1 = (ImageView) findViewById(R.id.cardOneIV);
        card2 = (ImageView) findViewById(R.id.cardTwoIV);
        card3 = (ImageView) findViewById(R.id.cardThreeIV);
        card4 = (ImageView) findViewById(R.id.cardFourIV);
        turnCard = (ImageView) findViewById(R.id.turnCardIV);
        countView1 = (TextView) findViewById(R.id.countString1);
        countView2 = (TextView) findViewById(R.id.countString2);
        totalScore = (TextView) findViewById(R.id.totalScore1);

        Picasso.with(this).load("https://deckofcardsapi.com/static/img/"+prev.getString("card1")+".png").placeholder(R.drawable.back).error(R.drawable.error).into(card1);
        Picasso.with(this).load("https://deckofcardsapi.com/static/img/"+prev.getString("card2")+".png").placeholder(R.drawable.back).error(R.drawable.error).into(card2);
        Picasso.with(this).load("https://deckofcardsapi.com/static/img/"+prev.getString("card3")+".png").placeholder(R.drawable.back).error(R.drawable.error).into(card3);
        Picasso.with(this).load("https://deckofcardsapi.com/static/img/"+prev.getString("card4")+".png").placeholder(R.drawable.back).error(R.drawable.error).into(card4);
        Picasso.with(this).load("https://deckofcardsapi.com/static/img/"+prev.getString("turnCard")+".png").placeholder(R.drawable.back).error(R.drawable.error).into(turnCard);

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
