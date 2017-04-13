package cs407.chromecastcribbage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Setup_Activity extends AppCompatActivity {

    Bundle prev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ChromeCast Cribbage");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onClick(View view) {

        Intent intent = new Intent(this, Waiting_2_Activity.class);

        switch (view.getId()) {
            case R.id.twoPlayerButton:
                intent.putExtra("players",2);
                startActivity(intent);
                break;
            case R.id.threePlayerButton:
                intent.putExtra("players",3);
                startActivity(intent);
                break;
            case R.id.fourPlayerButton:
                intent.putExtra("players",4);
                startActivity(intent);
                break;
        }

    }
}
