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
        setContentView(R.layout.activity_snake); //Laden des Layouts für die SnakeActivity

        setTitle("Snake"); //Titel der AppLeiste setzen

        backPressed = false; //Bei Rufen von onCreate kann der BackButton noch nicht gedrückt worden sein --> false
        invokedByOnCreate = true; //relevant für onResume Methode; identifiziert ob onResume() durch Starten oder durch Fortsetzen der Activity gerufen wird
        printToServer(1); //Server mitteilen, dass Snake gestartet wurde
    }

    //Methoden um durch Klicken auf per onClick zugewiesenen Button entsprechenden Interaktionscode an Server zu senden
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

    public void restart(View view) {
        printToServer(6);
    }

    @Override
    protected void onPause() {
        if (backPressed) {
            printToServer(7); //Wenn Back Button gedrückt wurde --> Interaktionscode 7: Snake beenden
        } else if (NetworkController.isConnected()) {
            printToServer(0); //Ansonsten bei Server abmelden (Interaktionscode 0), wenn Verbindung besteht
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (!backPressed) {
            NetworkController.getInstance().close(); //Wenn Activity nicht durch Back Button beendet wurde --> Socket schließen
        }
        super.onDestroy();
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
            printToServer(1); //Server mitteilen, dass Snake wieder ausgeführt wird
        }
        invokedByOnCreate = false; //Bei nächstem Aufruf der Methode Activity nicht neu erstellt sondern fortgesetzt --> nicht von onCreate gerufen --> false
        super.onResume();
    }

    private void printToServer(int keyStroke) {
        new NetworkingTask().execute(new Integer[]{keyStroke}); //Senden des übergebenen Interaktionscodes an den Server
    }

}
