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
        setContentView(R.layout.activity_tic_tac_toe); //Laden des Layouts für TicTacToe

        setTitle("Tic Tac Toe"); //Titel der AppLeiste setzen

        invokedByOnCreate = true; //Bei Rufen von onCreate kann der BackButton noch nicht gedrückt worden sein --> false
        printToServer(40); //Server mitteilen, dass TicTacToe gestartet wurde
    }

    //Methoden um durch Klicken auf per onClick zugewiesenen Button entsprechenden Interaktionscode an Server zu senden
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
            printToServer(51); //Wenn Back Button gedrückt wurde --> Interaktionscode 51: TicTacToe beenden
        } else if (NetworkController.isConnected()) {
            printToServer(0); //Ansonsten bei Server abmelden (Interaktionscode 0), wenn Verbindung besteht
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        backPressed = true; //Klicken des Back Buttons ruft onBackPressed --> setzen von backPressed auf true
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        backPressed = false; //Bei Rufen von onResume kann der BackButton noch nicht gedrückt worden sein --> false
        if (!invokedByOnCreate) { //Wenn Methode gerufen wurde durch Fortsetzen der Activity (weil vorher App pausiert/im Hintergrund) ...
            NetworkController.resetNetworkController(); //... NetworkController resetten
            new Thread(NetworkController.getInstance()).start(); //... neuen NetworkController Threade starten --> Verbindung neu herstellen
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printToServer(40); //Server mitteilen, dass TicTacToe wieder ausgeführt wird
        }
        invokedByOnCreate = false; //Bei nächstem Aufruf der Methode Activity nicht neu erstellt sondern fortgesetzt --> nicht von onCreate gerufen --> false
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (!backPressed) {
            NetworkController.getInstance().close(); //Wenn Activity nicht durch Back Button beendet wurde --> Socket schließen
        }
        super.onDestroy();
    }

    private void printToServer(int keyStroke) {
        NetworkingTask sender = new NetworkingTask();
        sender.execute(new Integer[]{keyStroke}); //Senden des übergebenen Interaktionscodes an den Server
    }
}
