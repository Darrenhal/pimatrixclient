package de.pimatrix.gamecontroller.gameactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import de.pimatrix.gamecontroller.R;
import de.pimatrix.gamecontroller.backend.NetworkController;
import de.pimatrix.gamecontroller.backend.NetworkingTask;

public class TetrisActivity extends AppCompatActivity {

    private boolean backPressed;
    private boolean invokedByOnCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);

        setTitle("Tetris");

        invokedByOnCreate = true;
        printToServer(20);
    }

    public void left(View view) {
        printToServer(21);
    }

    public void right(View view) {
        printToServer(22);
    }

    public void rotateLeft(View view) {
        printToServer(23);
    }

    public void rotateRight(View view) {
        printToServer(24);
    }

    public void boost(View view) {
        printToServer(25);
    }

    public void pauseGame(View view) {
        TextView txtTetrisStatus = findViewById(R.id.txtTetrisStatus);
        Button btnPauseTetris = findViewById(R.id.btnPauseTetris);

        if (txtTetrisStatus.getText().toString().equals("Spiel läuft")) {
            btnPauseTetris.setText("Fortsetzen");
            txtTetrisStatus.setText("Spiel pausiert");
            printToServer(13);
        } else {
            btnPauseTetris.setText("Pause");
            txtTetrisStatus.setText("Spiel läuft");
        }
        printToServer(26);
    }

    @Override
    protected void onPause() {
        if (backPressed) {
            printToServer(27);
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
            printToServer(7);
        }
        invokedByOnCreate = false;
        super.onResume();
    }

    private void printToServer(int keyStroke) {
        NetworkingTask sender = new NetworkingTask();
        sender.execute(new Integer[]{keyStroke});
    }
}
