package de.pimatrix.gamecontroller.gameactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.logging.Logger;

import de.pimatrix.gamecontroller.R;
import de.pimatrix.gamecontroller.backend.NetworkingTask;

public class PongActivity extends AppCompatActivity {

    private int player1Position;
    private int player1Transform;
    private int player2Position;
    private int player2Transform;
    private TextView txtLogger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pong);

        final double fixedUpdateLength = 10.01;

        txtLogger = findViewById(R.id.txtLogger);

        SeekBar player1 = findViewById(R.id.skBarPlayer1);
        SeekBar player2 = findViewById(R.id.skBarPlayer2);

        player1Position = 9;
        player2Position = 9;

        player1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                player1Transform = (int) (progress / fixedUpdateLength);
                Log.d("PongTest", "Transform: " + player1Transform);
                int transformations = 0;
                if (player1Transform < player1Position) {
                    while (player1Transform < player1Position) {
                        //printToServer(82);
                        player1Transform++;
                        transformations++;
                        txtLogger.setText("Pos: " + player1Position);
                        sleep();
                    }

                    player1Position = player1Transform - transformations;
                } else if (player1Transform > player1Position) {
                    while (player1Transform > player1Position) {
                        //printToServer(81);
                        player1Transform--;
                        transformations--;
                        sleep();
                    }
                    Log.d("PongTest", "Pos: " + player1Transform);
                    player1Position = player1Transform + transformations;
                    txtLogger.setText("Pos: " + player1Position);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        player2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                player2Transform = (int) (progress / fixedUpdateLength);
                txtLogger.setText("P2: " + player2Transform);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }


    private void printToServer(int keyStroke) {
        new NetworkingTask().execute(new Integer[]{keyStroke});
    }

    private void sleep() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
