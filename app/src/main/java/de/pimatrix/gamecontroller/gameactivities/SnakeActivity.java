package de.pimatrix.gamecontroller.gameactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import de.pimatrix.gamecontroller.MainActivity;
import de.pimatrix.gamecontroller.R;
import de.pimatrix.gamecontroller.backend.NetworkController;
import de.pimatrix.gamecontroller.backend.NetworkingTask;

public class SnakeActivity extends AppCompatActivity {

    private boolean backPressed = false;
    private boolean invokedByOnCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);

        setTitle("Snake");

        backPressed = false;
        invokedByOnCreate = true;
        printToServer(1);
    }

    public void left(View view) {
        printToServer(2);
    }

    public void right(View view) {
        printToServer(3);
    }

    public void up(View view) {
        printToServer(4);
    }

    public void down(View view) {
        printToServer(5);
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
        if (!invokedByOnCreate) {
            NetworkController.resetNetworkController();
            new Thread(NetworkController.getInstance()).start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printToServer(1);
        }
        invokedByOnCreate = false;
        super.onResume();
    }

    private void printToServer(int keyStroke) {
        new NetworkingTask().execute(new Integer[]{keyStroke});
    }

}
