package de.pimatrix.gamecontroller.gameactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import de.pimatrix.gamecontroller.R;
import de.pimatrix.gamecontroller.backend.NetworkingTask;

public class TicTacToeActivity extends AppCompatActivity {

    private NetworkingTask connector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        setTitle("Tic Tac Toe");

        connector = (NetworkingTask) getIntent().getSerializableExtra("NetworkController");
        connector.execute(15);
    }

    public void topLeft(View view) {
        connector.execute(16);
    }

    public void topMiddle(View view) {
        connector.execute(17);
    }

    public void topRight(View view) {
        connector.execute(18);
    }

    public void middleLeft(View view) {
        connector.execute(19);
    }

    public void middleMiddle(View view) {
        connector.execute(20);
    }

    public void middleRight(View view) {
        connector.execute(21);
    }

    public void bottomLeft(View view) {
        connector.execute(22);
    }

    public void bottomMiddle(View view) {
        connector.execute(23);
    }

    public void bottomRight(View view) {
        connector.execute(24);
    }

    @Override
    protected void onPause() {
        connector.execute(0);
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        connector.execute(25);
        super.onDestroy();
    }
}
