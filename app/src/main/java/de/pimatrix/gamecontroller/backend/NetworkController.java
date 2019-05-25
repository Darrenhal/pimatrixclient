package de.pimatrix.gamecontroller.backend;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

import de.pimatrix.gamecontroller.MainActivity;

public class NetworkController implements Runnable, Serializable {

    private static String serverIP = "192.168.178.39";
    private static int serverPort = 35000;
    private static Socket socket;
    private static NetworkController nc;
    private static boolean connected = false;

    private static NetworkController instance;

    @Override
    public void run() {
        connect(serverIP, serverPort);
    }

    private NetworkController() {
        nc = this;
    }

    private void connect(String serverIP, int serverPort) {
        if (socket != null) {
            send(0);
        }
        socket = null;
        try {
            socket = new Socket(serverIP, serverPort);
            connected = true;
            //new Thread(new KeepAlive(serverIP)).start();
            MainActivity.updateConnection(1);
        } catch (IOException e) {
            MainActivity.updateConnection(0);
            connected = false;
            e.printStackTrace();
        }
    }

    public void send(int keyStroke) {
        if (connected) {
            try {
                OutputStream out = socket.getOutputStream();
                out.write(keyStroke);
            } catch (IOException e) {}
        }
    }

    public static NetworkController getInstance() {
        if (instance == null) {
            instance = new NetworkController();
        }
        return instance;
    }

    protected void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    protected void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public static boolean isConnected() {
        return connected;
    }

    public static void setConnection(boolean connectionUpdate) {
        connected = connectionUpdate;
    }

    public static void resetNetworkController() {
        instance = null;
    }
}