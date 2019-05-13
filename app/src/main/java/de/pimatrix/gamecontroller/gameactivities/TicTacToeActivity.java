package de.pimatrix.gamecontroller.gameactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import de.pimatrix.gamecontroller.R;
import de.pimatrix.gamecontroller.backend.NetworkController;
import de.pimatrix.gamecontroller.backend.NetworkingTask;

public class TicTacToeActivity extends AppCompatActivity {

    private boolean backPressed;
    private boolean invokedByOnCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        setTitle("Tic Tac Toe");

        invokedByOnCreate = true;
        printToServer(15);
    }

    public void topLeft(View view) {
        printToServer(16);
    }

    public void topMiddle(View view) {
        printToServer(17);
    }

    public void topRight(View view) {
        printToServer(18);
    }

    public void middleLeft(View view) {
        printToServer(19);
    }

    public void middleMiddle(View view) {
        printToServer(20);
    }

    public void middleRight(View view) {
        printToServer(21);
    }

    public void bottomLeft(View view) {
        printToServer(22);
    }

    public void bottomMiddle(View view) {
        printToServer(23);
    }

    public void bottomRight(View view) {
        printToServer(24);
    }

    @Override
    protected void onPause() {
        if (backPressed) {
            printToServer(25);
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
            printToServer(15);
        }
        invokedByOnCreate = false;
        super.onResume();
    }

    private void printToServer(int keyStroke) {
        NetworkingTask sender = new NetworkingTask();
        sender.execute(new Integer[]{keyStroke});
    }
}
