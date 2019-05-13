package de.pimatrix.gamecontroller.gameactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import de.pimatrix.gamecontroller.R;
import de.pimatrix.gamecontroller.backend.NetworkController;
import de.pimatrix.gamecontroller.backend.NetworkingTask;

public class TetrisActivity extends AppCompatActivity {

    private boolean backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);

        setTitle("Tetris");

        printToServer(7);
    }

    public void rotateLeft(View view) {
        printToServer(10);
    }

    public void rotateRight(View view) {
        printToServer(11);
    }

    public void left(View view) {
        printToServer(8);
    }

    public void right(View view) {
        printToServer(9);
    }

    public void boost(View view) {
        printToServer(12);
    }

    public void pause(View view) {
        printToServer(13);
    }

    @Override
    protected void onPause() {
        if (backPressed) {
            printToServer(6);
        } else {
            printToServer(0);
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        backPressed = true;
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        backPressed = false;
        NetworkController.resetNetworkController();
        new Thread(NetworkController.getInstance()).start();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        printToServer(14);
        super.onDestroy();
    }

    private void printToServer(int keyStroke) {
        NetworkingTask sender = new NetworkingTask();
        sender.execute(new Integer[]{keyStroke});
    }
}
