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
        printToServer(40);
    }

    public void topLeft(View view) {
        printToServer(41);
    }

    public void topMiddle(View view) {
        printToServer(42);
    }

    public void topRight(View view) {
        printToServer(43);
    }

    public void middleLeft(View view) {
        printToServer(44);
    }

    public void middleMiddle(View view) {
        printToServer(45);
    }

    public void middleRight(View view) {
        printToServer(46);
    }

    public void bottomLeft(View view) {
        printToServer(47);
    }

    public void bottomMiddle(View view) {
        printToServer(48);
    }

    public void bottomRight(View view) {
        printToServer(49);
    }

    public void resetGame(View view) {
        printToServer(50);
    }

    @Override
    protected void onPause() {
        if (backPressed) {
            printToServer(51);
        } else if(NetworkController.isConnected()){
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
            printToServer(40);
        }
        invokedByOnCreate = false;
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (!backPressed) {
            NetworkController.getInstance().close();
        }
        super.onDestroy();
    }

    private void printToServer(int keyStroke) {
        NetworkingTask sender = new NetworkingTask();
        sender.execute(new Integer[]{keyStroke});
    }
}
