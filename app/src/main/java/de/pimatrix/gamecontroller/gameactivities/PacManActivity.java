package de.pimatrix.gamecontroller.gameactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import de.pimatrix.gamecontroller.R;
import de.pimatrix.gamecontroller.backend.NetworkController;
import de.pimatrix.gamecontroller.backend.NetworkingTask;

public class PacManActivity extends AppCompatActivity {

    private boolean backPressed;
    private boolean invokedByOnCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pac_man);

        setTitle("Pac Man"); //Titel der AppLeiste setzen

        invokedByOnCreate = true; //Bei Rufen von onCreate kann der BackButton noch nicht gedrückt worden sein --> false
        printToServer(60); //Server mitteilen, dass PacMan gestartet wurde
    }

    @Override
    protected void onPause() {
        if (backPressed) {
            printToServer(65); //Wenn Back Button gedrückt wurde --> Interaktionscode 65: PacMan beenden
        } else if (NetworkController.isConnected()){
            printToServer(0); //Ansonsten bei Server abmelden (Interaktionscode 0), wenn Verbindung besteht
        }
        super.onPause();
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
            printToServer(60); //Server mitteilen, dass PacMan wieder ausgeführt wird
        }
        invokedByOnCreate = false; //Bei nächstem Aufruf der Methode Activity nicht neu erstellt sondern fortgesetzt --> nicht von onCreate gerufen --> false
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (!backPressed) { //Wenn Activity nicht durch Back Button beendet wurde --> Socket schließen
            NetworkController.getInstance().close();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        backPressed = true; //Klicken des Back Buttons ruft onBackPressed --> setzen von backPressed auf true
        super.onBackPressed();
    }

    //Methoden um durch Klicken auf per onClick zugewiesenen Button entsprechenden Interaktionscode an Server zu senden
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
        new NetworkingTask().execute(new Integer[]{keyStroke}); //Senden des übergebenen Interaktionscodes an den Server
    }
}