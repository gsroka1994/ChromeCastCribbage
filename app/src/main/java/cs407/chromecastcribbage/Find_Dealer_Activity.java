package cs407.chromecastcribbage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

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
            Button button = (Button) findViewById(R.id.button18);
            Deck deck = new Deck();
            deck.shuffle();
            String card = deck.dealCard().getFileName();

            Context context = button.getContext();
            int id = context.getResources().getIdentifier(card, "drawable", context.getPackageName());

            button.setBackgroundResource(id);
            button.setVisibility(View.VISIBLE);

            button = (Button) findViewById(view.getId());
            button.setVisibility(View.INVISIBLE);

            //TODO: Send selected card to Chromecast?  Maybe not if deck is implemented on receiver app
            //TODO: Wait until all players have selected a card
        }
    }

    public void moveNext(View view) {
        Intent intent = new Intent(this, Deal_Activity.class);
        startActivity(intent);
    }
}
