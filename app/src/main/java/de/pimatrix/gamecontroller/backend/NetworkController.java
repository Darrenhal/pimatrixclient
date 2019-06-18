package de.pimatrix.gamecontroller.backend;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

import de.pimatrix.gamecontroller.MainActivity;

public class NetworkController implements Runnable, Serializable {

    private static String serverIP = "192.168.178.39"; //Default Server IP Adresse
    private static int serverPort = 35000; //Default Server Port
    private static Socket socket;
    private static NetworkController nc;
    private static boolean connected = false;

    private static NetworkController instance;

    //Beim Starten des NetworkController Threads versuchen Verbindung mit Server herzustellen
    @Override
    public void run() {
        connect(serverIP, serverPort);
    }

    //Singleton Konstruktor
    private NetworkController() {
        nc = this;
    }

    private void connect(String serverIP, int serverPort) {
        if (socket != null) { //Wenn socket nicht NULL ist
            send(0); //... beim Server abmelden --> Verhindern, dass durch drücken auf Reconnect der Server zwei (oder mehr) Clients vom selben Handy bedient (vorhergehende Verbindungen bestehen dann tot auf dem Server fort und verbrauchen unnötigerweise Ressourcen)
        }
        socket = null; //Danach socket auf NULL setzen
        try {
            socket = new Socket(serverIP, serverPort); //Socket neu erstellen
            connected = true;
            //new Thread(new KeepAlive(serverIP)).start();
            MainActivity.updateConnection(1); //Verbindungsstatus im Hauptmenü anzeigen (verbunden)
        } catch (IOException e) {
            MainActivity.updateConnection(0); //Verbindungsstatus im Hauptmenü anzeigen (nicht verbunden)
            connected = false;
            e.printStackTrace();
        }
    }

    //Senden der Interaktionscodes abhängig vom übergebenen Parameter (--> wird festgelegt durch gedrückte Taste)
    public void send(int keyStroke) {
        if (connected) { //Daten werden gesendet, sofern Verbindung zum Server vorhanden ist
            try {
                OutputStream out = socket.getOutputStream(); //OutputStream vom Socket holen
                out.write(keyStroke); //Daten auf Datenstrom schreiben
            } catch (IOException e) {
            }
        }
    }

    //Zugriff auf Instanz der Klasse (siehe Singleton)
    public static NetworkController getInstance() {
        if (instance == null) {
            instance = new NetworkController();
        }
        return instance;
    }

    //Setzen der ServerIP Adresse (z.B. durch veränderte Verbindungseinstellungen in den Settings)
    protected void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    //Setzen des Server Ports (z.B. durch veränderte Verbindungseinstellungen in den Settings)
    protected void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    //gibt aktuellen Verbindungsstatus zurück
    public static boolean isConnected() {
        return connected;
    }


    public static void setConnection(boolean connectionUpdate) {
        connected = connectionUpdate;
    }

    //NetworkController auf NULL setzen --> Freigeben des Speichers durch GC
    public static void resetNetworkController() {
        instance = null;
    }

    //Schließen des Sockets
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
        }
    }
}