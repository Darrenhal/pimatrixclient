package de.pimatrix.gamecontroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import de.pimatrix.gamecontroller.backend.NetworkController;
import de.pimatrix.gamecontroller.backend.NetworkingTask;
import de.pimatrix.gamecontroller.backend.Settings;
import de.pimatrix.gamecontroller.gameactivities.PacManActivity;
import de.pimatrix.gamecontroller.gameactivities.PongActivity;
import de.pimatrix.gamecontroller.gameactivities.SnakeActivity;
import de.pimatrix.gamecontroller.gameactivities.TetrisActivity;
import de.pimatrix.gamecontroller.gameactivities.TicTacToeActivity;

public class MainActivity extends AppCompatActivity {

    public static TextView txtConnectionStatus;
    public static final int RETURN_TO_MAIN_MENU = 1;
    public static final int REQEUST_RECONNECT = 2;
    private static MainActivity main; //zugänlich machen des Objekts, um in runOnUiThread darauf zugreifen zu können
    private boolean callingOtherActivity = false;
    private boolean connectionReset = false;
    private boolean backPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Layout für Hauptmenü laden

        main = this;

        connectionReset = true; //Zu Beginn der Anwendung muss Verbindungsreset ausgeführt werden (um neuen Verbindungsversuch zu ermöglichen und saubere Connect/Disconnect Funktionalität zu ermöglichen)
        txtConnectionStatus = findViewById(R.id.txtConnectionStatus); //Textfeld zum Anzeigen des Verbindungsstatus

//        NetworkController connector = NetworkController.getInstance();
//        new Thread(connector).start();
    }

    @Override
    protected void onResume() {
        callingOtherActivity = false;
        if (connectionReset) { //Resetten der Verbindung, wenn Activity fortgesetzt wird (gemäß Activity Lifecycle also auch zu Beginn der App), sofern ein erneutes Verbinden vorgesehen ist (siehe boolean)
            NetworkController.resetNetworkController(); //Rufen der Reset-Methode des NetworkControllers
            new Thread(NetworkController.getInstance()).start(); //Starten des NetworkController Threads
        }
        connectionReset = false; //Reset-Bedingung auf false setzen --> Reset wurde gerade bereits durchgeführt
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //Wenn aufgerufene Activity Result zurückgibt anhand des Results ...
        if (requestCode == RETURN_TO_MAIN_MENU) {
            onResume(); //... onResume ausführen
        } else if (requestCode == REQEUST_RECONNECT) {
            onResume(); //... auch onResume ausführen
        }
        callingOtherActivity = false; //es wird keine andere Activity gestartet --> false
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        if (!callingOtherActivity && NetworkController.isConnected()) { //Wenn Activity pausiert wird, während eine Verbindung zum Server besteht und ohne, dass eine andere Activity gestartet wird ...
            new NetworkingTask().execute(new Integer[]{0}); //... abmelden vom Server
            connectionReset = true; //Connection Reset auf true setzen --> bei Fortsetzen der App (onResume) wird Verbindung erneut aufgebaut
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        backPressed = true; //Klicken des Back Buttons ruft onBackPressed --> setzen von backPressed auf true
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (!backPressed) {
            NetworkController.getInstance().close(); //Schließen des NetworkControllers, wenn die Activity zerstört wird, weil der Back Button geklickt wurde
        }
        super.onDestroy();
    }

    public void playSnake(View view) { //Wenn der Button, dem die Methode als onClick zugewiesen ist angeklickt wird ...
        callingOtherActivity = true; //... andere Activity wird gerufen --> true
        Intent startSnake = new Intent(this, SnakeActivity.class); //neuen Intent erstellen, mit dem die SnakeActivity gestartet wird
        startActivityForResult(startSnake, RETURN_TO_MAIN_MENU); //Activity starten --> springt nach Beendigung in onActivityResult

    }

    public void playTetris(View view) { //Wenn der Button, dem die Methode als onClick zugewiesen ist angeklickt wird ...
        callingOtherActivity = true; //... andere Activity wird gerufen --> true
        Intent startTetris = new Intent(this, TetrisActivity.class); //neuen Intent erstellen, mit dem die TetrisActivity gestartet wird
        startActivityForResult(startTetris, RETURN_TO_MAIN_MENU); //Activity starten --> springt nach Beendigung in onActivityResult
    }

    public void playTTT(View view) { //Wenn der Button, dem die Methode als onClick zugewiesen ist angeklickt wird ...
        callingOtherActivity = true; //... andere Activity wird gerufen --> true
        Intent startTTT = new Intent(this, TicTacToeActivity.class); //neuen Intent erstellen, mit dem die TicTacToeActivity gestartet wird
        startActivityForResult(startTTT, RETURN_TO_MAIN_MENU); //Activity starten --> springt nach Beendigung in onActivityResult
    }

    public void playPacMan(View view) { //Wenn der Button, dem die Methode als onClick zugewiesen ist angeklickt wird ...
        callingOtherActivity = true; //... andere Activity wird gerufen --> true
        Intent startPacMan = new Intent(this, PacManActivity.class); //neuen Intent erstellen, mit dem die PacManActivity gestartet wird
        startActivityForResult(startPacMan, RETURN_TO_MAIN_MENU); //Activity starten --> springt nach Beendigung in onActivityResult
    }

    public void playPong(View view) { //Wenn der Button, dem die Methode als onClick zugewiesen ist angeklickt wird ...
        callingOtherActivity = true; //... andere Activity wird gerufen --> true
        Intent startPong = new Intent(this, PongActivity.class); //neuen Intent erstellen, mit dem die PongActivity gestartet wird
        startActivityForResult(startPong, RETURN_TO_MAIN_MENU); //Activity starten --> springt nach Beendigung in onActivityResult
    }

    public void launchSettings(View view) { //Wenn der Button, dem die Methode als onClick zugewiesen ist angeklickt wird ...
        callingOtherActivity = true; //... andere Activity wird gerufen --> true
        Intent goToSettings = new Intent(this, Settings.class); //neuen Intent erstellen, mit dem das Settings Menü gestartet wird
        startActivityForResult(goToSettings, REQEUST_RECONNECT); //Activity starten --> springt nach Beendigung in onActivityResult
    }

    public static void updateConnection(int classifier) {
        if (classifier == 0) { //Überprüfen, welchen Wert der NetworkController beim Verbindungsversuch zurückliefert (0 = nicht verbunden, 1 = verbunden)
            main.runOnUiThread(new Runnable() {
                @Override
                public void run() { //benötigt, um von anderer Klasse Komponente eines UI Threads zu verändern
                    txtConnectionStatus.setText("Connection could not be established!"); //Verbindungsstatus anzeigen
                }
            });
        }

        if (classifier == 1) { //Überprüfen, welchen Wert der NetworkController beim Verbindungsversuch zurückliefert (0 = nicht verbunden, 1 = verbunden)
            main.runOnUiThread(new Runnable() {
                @Override
                public void run() { //benötigt, um von anderer Klasse Komponente eines UI Threads zu verändern
                    txtConnectionStatus.setText("Connected To Pi Matrix!"); //Verbindungsstatus anzeigen
                }
            });
        }
    }
}
