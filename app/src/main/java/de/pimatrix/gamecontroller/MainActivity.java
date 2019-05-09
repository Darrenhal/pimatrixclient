package de.pimatrix.gamecontroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import de.pimatrix.gamecontroller.backend.NetworkController;
import de.pimatrix.gamecontroller.backend.Settings;
import de.pimatrix.gamecontroller.gameactivities.PacManActivity;
import de.pimatrix.gamecontroller.gameactivities.SnakeActivity;
import de.pimatrix.gamecontroller.gameactivities.TetrisActivity;
import de.pimatrix.gamecontroller.gameactivities.TicTacToeActivity;

public class MainActivity extends AppCompatActivity {

    public NetworkController connector;
    public static TextView txtConnectionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtConnectionStatus = findViewById(R.id.txtConnectionStatus);
    }

    @Override
    protected void onResume() {
        super.onResume();

        NetworkController connector = new NetworkController();
        new Thread(connector).start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        connector.send(0);
    }

    public void playSnake(View view) {
        Intent startSnake = new Intent(this, SnakeActivity.class);
        startSnake.putExtra("NetworkController", connector);
        startActivity(startSnake);

    }

    public void playTetris(View view) {
        Intent startTetris = new Intent(this, TetrisActivity.class);
        startTetris.putExtra("NetworkController", connector);
        startActivity(startTetris);
    }

    public void playTTT(View view) {
        Intent startTTT = new Intent(this, TicTacToeActivity.class);
        startTTT.putExtra("NetworkController", connector);
        startActivity(startTTT);
    }

    public void playPacMan(View view) {
        Intent startPacMan = new Intent(this, PacManActivity.class);
        startPacMan.putExtra("NetworkController", connector);
        startActivity(startPacMan);
    }

    public void playPong(View view) {
        Intent startPong = new Intent(this, PacManActivity.class);
        startPong.putExtra("NetworkController", connector);
        startActivity(startPong);
    }

    public void launchSettings(View view) {
        Intent goToSettings = new Intent(this, Settings.class);
        goToSettings.putExtra("NetworkController", connector);
        startActivity(goToSettings);
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
