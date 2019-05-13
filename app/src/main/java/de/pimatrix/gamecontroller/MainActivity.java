package de.pimatrix.gamecontroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import de.pimatrix.gamecontroller.backend.NetworkController;
import de.pimatrix.gamecontroller.backend.NetworkingTask;
import de.pimatrix.gamecontroller.backend.Settings;
import de.pimatrix.gamecontroller.gameactivities.PacManActivity;
import de.pimatrix.gamecontroller.gameactivities.SnakeActivity;
import de.pimatrix.gamecontroller.gameactivities.TetrisActivity;
import de.pimatrix.gamecontroller.gameactivities.TicTacToeActivity;

public class MainActivity extends AppCompatActivity {

    public static TextView txtConnectionStatus;
    public static final int RETURN_TO_MAIN_MENU = 1;
    public static final int REQEUST_RECONNECT = 2;
    private static MainActivity main;
    private boolean callingOtherActivity = false;
    private boolean connectionReset = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main = this;

        connectionReset = true;
        txtConnectionStatus = findViewById(R.id.txtConnectionStatus);

//        NetworkController connector = NetworkController.getInstance();
//        new Thread(connector).start();
    }

    @Override
    protected void onResume() {
        callingOtherActivity = false;
        if (connectionReset) {
            NetworkController.resetNetworkController();
            new Thread(NetworkController.getInstance()).start();
        }
        connectionReset = false;
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RETURN_TO_MAIN_MENU) {
            onResume();
        } else if (requestCode == REQEUST_RECONNECT) {}
        callingOtherActivity = false;
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        if (!callingOtherActivity){
            new NetworkingTask().execute(new Integer[]{0});
            connectionReset = true;
        }
        super.onPause();
    }

    public void playSnake(View view) {
        callingOtherActivity = true;
        Intent startSnake = new Intent(this, SnakeActivity.class);
        startActivityForResult(startSnake, RETURN_TO_MAIN_MENU);

    }

    public void playTetris(View view) {
        callingOtherActivity = true;
        Intent startTetris = new Intent(this, TetrisActivity.class);
        startActivityForResult(startTetris, RETURN_TO_MAIN_MENU);
    }

    public void playTTT(View view) {
        callingOtherActivity = true;
        Intent startTTT = new Intent(this, TicTacToeActivity.class);
        startActivityForResult(startTTT, RETURN_TO_MAIN_MENU);
    }

    public void playPacMan(View view) {
        callingOtherActivity = true;
        Intent startPacMan = new Intent(this, PacManActivity.class);
        startActivityForResult(startPacMan, RETURN_TO_MAIN_MENU);
    }

    public void playPong(View view) {
        callingOtherActivity = true;
        Intent startPong = new Intent(this, PacManActivity.class);
        startActivityForResult(startPong, RETURN_TO_MAIN_MENU);
    }

    public void launchSettings(View view) {
        callingOtherActivity = true;
        Intent goToSettings = new Intent(this, Settings.class);
        startActivityForResult(goToSettings, REQEUST_RECONNECT);
    }

    public static void updateConnection(int classifier) {
        if (classifier == 0) {
            main.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtConnectionStatus.setText("Connection could not be established!");
                }
            });
        }

        if (classifier == 1) {
            main.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtConnectionStatus.setText("Connected To Pi Matrix!");
                }
            });
        }
    }
}
