package cs407.chromecastcribbage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Welcome_Activity extends AppCompatActivity {

    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ChromeCast Cribbage");
        setSupportActionBar(toolbar);

        Button chromeCastName = (Button) findViewById(R.id.chromeCastName);


        //TODO: Find names of available chromecasts
        String name = "Enter Chromecast Here";
        chromeCastName.setText(name);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    Move to the next screen
    1. If not setup go to setupScreen
    2. If setup go to waiting screen
     */
    public void welcomeMove(View view) {
        EditText et = (EditText) findViewById(R.id.userName);
        userName = et.getText().toString();
        if(userName.length()==0){
            Toast.makeText(this, "Please Enter A Username", Toast.LENGTH_LONG).show();

        } else{
            Toast.makeText(this, "Username: "+userName, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Setup_Activity.class);
            intent.putExtra("userName",userName);
            startActivity(intent);
        }

    }
}
