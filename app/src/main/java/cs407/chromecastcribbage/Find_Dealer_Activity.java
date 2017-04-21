package cs407.chromecastcribbage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Find_Dealer_Activity extends AppCompatActivity {

    boolean selected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find__dealer_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ChromeCast Cribbage");
        setSupportActionBar(toolbar);

    }

    public void selectCard(View view) {

        if(!selected){
            selected = true;

            ImageView cardIV = (ImageView) findViewById(R.id.cardIV);
            cardIV.bringToFront();

            String card = "7C";

            Picasso.with(this).load("https://deckofcardsapi.com/static/img/"+card+".png").placeholder(R.drawable.back).error(R.drawable.error).into(cardIV);

            cardIV.setVisibility(View.VISIBLE);

            ImageView selectedCard = (ImageView) findViewById(view.getId());
            selectedCard.setVisibility(View.INVISIBLE);

            //TODO: Send selected card to Chromecast?  Maybe not if deck is implemented on receiver app
            //TODO: Wait until all players have selected a card
        }
    }

    public void moveNext(View view) {
        Intent intent = new Intent(this, Deal_Activity.class);
        startActivity(intent);
    }
}
