package de.pimatrix.gamecontroller.gameactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.pimatrix.gamecontroller.R;
import de.pimatrix.gamecontroller.backend.NetworkController;

public class TetrisActivity extends AppCompatActivity {

    private NetworkController connector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);

        setTitle("Tetris");

        connector = (NetworkController) getIntent().getSerializableExtra("NetworkController");
    }
}