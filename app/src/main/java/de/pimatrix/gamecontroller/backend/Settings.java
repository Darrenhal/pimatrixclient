package de.pimatrix.gamecontroller.backend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import de.pimatrix.gamecontroller.R;

public class Settings extends AppCompatActivity {

    private NetworkController connector;
    private boolean backPressed;
    private boolean invokedByOnCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings); //Laden des Layouts für das Settings Menü

        setTitle("Settings"); //Titel der AppLeiste setzen

        invokedByOnCreate = true; //relevant für onResume Methode; identifiziert ob onResume() durch Starten oder durch Fortsetzen der Activity gerufen wird
        connector = NetworkController.getInstance(); //Holen der NetworkController Instanz
    }

    @Override
    protected void onPause() {
        if (!backPressed && NetworkController.isConnected()) { //Wenn Verbindung zum Server besteht, Activity pausiert wird und nicht BackButton geklickt wurde (App wird pausiert) ...
            new NetworkingTask().execute(new Integer[]{0}); //... beim Server abmelden mit Interaktionscode 0
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
        }
        invokedByOnCreate = false; //Bei nächstem Aufruf der Methode Activity nicht neu erstellt sondern fortgesetzt --> nicht von onCreate gerufen --> false
        super.onResume();
    }

    //Befehl zum Zurücksetzen der seriellen Verbindung (Pi <--> Arduino) an Server senden
    public void resetServer(View view) {
        new NetworkingTask().execute(101);
    }

    //Methode zum erneuten Verbindungsaufbau mit dem Server
    public void connect(View view) {
        TextView txtSetServerIP = findViewById(R.id.txtSetServerIP);
        TextView txtSetServerPort = findViewById(R.id.txtSetServerPort);

        String serverIP = txtSetServerIP.getText().toString(); //Einlesen der im Menü eingetragenen IP Adresse
        int serverPort = Integer.parseInt(txtSetServerPort.getText().toString()); //Einlesen des im Menü eingetragenen Server Ports

        connector.setServerIP(serverIP); //Setzen der neuen Server IP
        connector.setServerPort(serverPort); //Setzen des neuen Server Ports

        new Thread(connector).start(); //NetworkController Thread mit vorhandener Instanz erneut starten --> erneuter Verbindungsversuch
        onBackPressed(); //interner Aufruf der Logik, für gedrückten BackButton --> Verbindung wird in onPause nicht wieder getrennt
        finish(); //Beenden der Activity und Zurückkehren zum Hauptmenü
    }
}