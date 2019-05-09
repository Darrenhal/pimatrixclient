package de.pimatrix.gamecontroller.gameactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.pimatrix.gamecontroller.R;
import de.pimatrix.gamecontroller.backend.NetworkController;

public class TicTacToeActivity extends AppCompatActivity {

    private NetworkController connector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        setTitle("Tic Tac Toe");

        connector = (NetworkController) getIntent().getSerializableExtra("NetworkController");
    }
}
