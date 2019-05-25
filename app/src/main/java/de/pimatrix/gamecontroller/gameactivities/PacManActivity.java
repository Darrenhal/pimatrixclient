package de.pimatrix.gamecontroller.gameactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import de.pimatrix.gamecontroller.R;
import de.pimatrix.gamecontroller.backend.NetworkController;
import de.pimatrix.gamecontroller.backend.NetworkingTask;

public class PacManActivity extends AppCompatActivity {

    private boolean backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pac_man);

        setTitle("Pac Man");

        printToServer(60);
    }

    @Override
    protected void onPause() {
        if (backPressed) {
            printToServer(65);
        } else if (NetworkController.isConnected()){
            printToServer(0);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (!backPressed) {
            NetworkController.getInstance().close();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        backPressed = true;
        super.onBackPressed();
    }

    public void moveLeft(View view) {
        printToServer(61);
    }

    public void moveRight(View view) {
        printToServer(62);
    }

    public void moveUp(View view) {
        printToServer(63);
    }

    public void moveDown(View view) {
        printToServer(64);
    }

    private void printToServer(int keyStroke) {
        new NetworkingTask().execute(new Integer[]{keyStroke});
    }
}