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

    public NetworkController connector;
    public static TextView txtConnectionStatus;
    public static final int RETURN_TO_MAIN_MENU = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtConnectionStatus = findViewById(R.id.txtConnectionStatus);

        NetworkController connector = NetworkController.getInstance();
        new Thread(connector).start();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RETURN_TO_MAIN_MENU) {

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*
        @Override
        protected void onPause() {
            super.onPause();

            NetworkingTask sender = new NetworkingTask();
            sender.execute(new Integer[]{0});
        }
    */
    public void playSnake(View view) {
        Intent startSnake = new Intent(this, SnakeActivity.class);
        startActivityForResult(startSnake, RETURN_TO_MAIN_MENU);

    }

    public void playTetris(View view) {
        Intent startTetris = new Intent(this, TetrisActivity.class);
        startActivityForResult(startTetris, RETURN_TO_MAIN_MENU);
    }

    public void playTTT(View view) {
        Intent startTTT = new Intent(this, TicTacToeActivity.class);
        startActivityForResult(startTTT, RETURN_TO_MAIN_MENU);
    }

    public void playPacMan(View view) {
        Intent startPacMan = new Intent(this, PacManActivity.class);
        startActivityForResult(startPacMan, RETURN_TO_MAIN_MENU);
    }

    public void playPong(View view) {
        Intent startPong = new Intent(this, PacManActivity.class);
        startActivityForResult(startPong, RETURN_TO_MAIN_MENU);
    }

    public void launchSettings(View view) {
        Intent goToSettings = new Intent(this, Settings.class);
        startActivityForResult(goToSettings, RETURN_TO_MAIN_MENU);
    }

    public static void updateConnection(int classifier) {
        if (classifier == 0) {
            txtConnectionStatus.setText("Connection could not be established!");
        }

        if (classifier == 1) {
            txtConnectionStatus.setText("Connected To Pi Matrix!");
        }
    }
}
