package de.pimatrix.gamecontroller.gameactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import de.pimatrix.gamecontroller.R;
import de.pimatrix.gamecontroller.backend.NetworkController;
import de.pimatrix.gamecontroller.backend.NetworkingTask;

public class PacManActivity extends AppCompatActivity {

    private NetworkController connector;
    private boolean backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pac_man);

        setTitle("Pac Man");

        connector = (NetworkController) getIntent().getSerializableExtra("NetworkController");
    }

    @Override
    protected void onPause() {
        if (backPressed) {
            printToServer(6);
        } else if (NetworkController.isConnected()){
            printToServer(0);
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        backPressed = true;
        super.onBackPressed();
    }

    public void mtrx11(View view) {
        connector.send(16);
    }

    public void mtrx21(View view) {
        connector.send(17);
    }

    public void mtrx31(View view) {
        connector.send(18);
    }

    public void mtrx12(View view) {
        connector.send(19);
    }

    public void mtrx22(View view) {
        connector.send(20);
    }

    public void mtrx32(View view) {
        connector.send(21);
    }

    public void mtrx13(View view) {
        connector.send(22);
    }

    public void mtrx23(View view) {
        connector.send(23);
    }

    public void mtrx33(View view) {
        connector.send(24);
    }

    private void printToServer(int keyStroke) {
        new NetworkingTask().execute(new Integer[]{keyStroke});
    }
}