package cs407.chromecastcribbage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Winner_Activity extends AppCompatActivity {

    TextView winnerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        winnerText = (TextView) findViewById(R.id.winnerText);
        Bundle prev = getIntent().getExtras();
        winnerText.setText(prev.getString("winner") + " is the Winner!");

    }

    public void newGame(View view) {
        Intent intent = new Intent(this, Waiting_2_Activity.class);
        startActivity(intent);
    }
}
