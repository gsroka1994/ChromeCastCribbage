package cs407.chromecastcribbage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.MediaRouteActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.games.GameManagerClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;


public class Welcome_Activity extends AppCompatActivity implements Observer {

    private static final String TAG = "tag";
    private CastContext mCastContext;
    private MenuItem mediaRouteMenuItem;
    String userName;
    private static CastConnectionManager mCastConnectionManager;
    private int mPlayerState = GameManagerClient.PLAYER_STATE_UNKNOWN;
    private String mPlayerName;
    TextView statusMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ChromeCast Cribbage");
        setSupportActionBar(toolbar);

        Button chromeCastName = (Button) findViewById(R.id.chromeCastName);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String defaultValue = "";
        String userName = sharedPref.getString(getString(R.string.userName), defaultValue);

        EditText userNameET = (EditText) findViewById(R.id.userName);
        userNameET.setText(userName);

        statusMessage = (TextView) findViewById(R.id.statusMessage);

        //TODO: Call receiver to see if any games are waiting for players
        boolean connect = false;
        if(connect){
            String name = "Connect To Game";
            chromeCastName.setText(name);
        }

        mCastConnectionManager = new CastConnectionManager(this,
                "A7E2DC4A");

        //mCastContext = CastContext.getSharedInstance(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome_, menu);
        //mediaRouteMenuItem = CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), menu, R.id.media_route_menu_item);
        MenuItem mediaRouteMenuItem =
                menu.findItem(R.id.media_route_menu_item);
        MediaRouteActionProvider mediaRouteActionProvider =
                (MediaRouteActionProvider)

                        MenuItemCompat.getActionProvider(mediaRouteMenuItem);
        if (mediaRouteActionProvider == null) {
            Log.w(TAG, "mediaRouteActionProvider is null!");
            return false;
        }
        mediaRouteActionProvider.setRouteSelector(
                mCastConnectionManager.getMediaRouteSelector());
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

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.userName), userName);
        editor.commit();

        //TODO: Send UserName to Chromecast

        if(userName.length()==0){
            Toast.makeText(this, "Please Enter A Username", Toast.LENGTH_LONG).show();

        } else{
            Toast.makeText(this, "Username: "+userName, Toast.LENGTH_LONG).show();

            sendPlayerReadyRequest();

            Intent intent = new Intent(this, Setup_Activity.class);
            startActivity(intent);
        }

    }

    public static CastConnectionManager getCastConnectionManager() {
        return mCastConnectionManager;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCastConnectionManager.startScan();
        mCastConnectionManager.addObserver(this);
    }

    @Override
    protected void onPause() {
        mCastConnectionManager.stopScan();
        mCastConnectionManager.deleteObserver(this);
        super.onPause();
    }

    /**
     * Called when the cast connection changes.
     */
    @Override
    public void update(Observable object, Object data) {
        final GameManagerClient gameManagerClient = mCastConnectionManager.getGameManagerClient();
        if (mCastConnectionManager.isConnectedToReceiver()) {
            PendingResult<GameManagerClient.GameManagerResult> result =
                    gameManagerClient.sendPlayerAvailableRequest(null);
            result.setResultCallback(new ResultCallback<GameManagerClient.GameManagerResult>() {
                @Override
                public void onResult(final GameManagerClient.GameManagerResult gameManagerResult) {
                    if (gameManagerResult.getStatus().isSuccess()) {
                        Log.d(TAG, "Player ID: " + gameManagerResult.getPlayerId());
                        mPlayerState = gameManagerClient.getCurrentState().getPlayer(
                                gameManagerResult.getPlayerId()).getPlayerState();

                        statusMessage.setText("Great! Click Get Started to Continue");

                    } else {
                        mCastConnectionManager.disconnectFromReceiver(false);
                        statusMessage.setText("SHIT it didn't work");
                        //Utils.showErrorDialog(MainActivity.this,
                                //gameManagerResult.getStatus().getStatusMessage());
                    }
                   // updateFragments();
                }
            });
        }
       // updateFragments();
    }

    /**
     * Change the player state to PLAYER_STATE_READY.
     */
    public void sendPlayerReadyRequest() {
        final GameManagerClient gameManagerClient = mCastConnectionManager.getGameManagerClient();
        if (mCastConnectionManager.isConnectedToReceiver()) {
            // Send player name to the receiver
            JSONObject jsonMessage = new JSONObject();
            try {
                jsonMessage.put("name", userName);
            } catch (JSONException e) {
                Log.e(TAG, "Error creating JSON message", e);
                return;
            }
            PendingResult<GameManagerClient.GameManagerResult> result =
                    gameManagerClient.sendPlayerReadyRequest(jsonMessage);
            result.setResultCallback(new ResultCallback<GameManagerClient.GameManagerResult>() {
                @Override
                public void onResult(final GameManagerClient.GameManagerResult gameManagerResult) {
                    if (gameManagerResult.getStatus().isSuccess()) {
                                mPlayerState = gameManagerClient.getCurrentState().getPlayer(
                                        gameManagerResult.getPlayerId()).getPlayerState();
                    } else {
                        mCastConnectionManager.disconnectFromReceiver(false);
                       // Utils.showErrorDialog(getActivity(),
                         //       gameManagerResult.getStatus().getStatusMessage());
                    }
                }
            });
        }
    }
}
