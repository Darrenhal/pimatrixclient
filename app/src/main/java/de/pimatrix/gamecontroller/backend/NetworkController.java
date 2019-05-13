package de.pimatrix.gamecontroller.backend;

import android.app.Activity;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

import de.pimatrix.gamecontroller.MainActivity;

public class NetworkController implements Runnable, Serializable {

    private String serverIP = "192.168.178.39";
    private int serverPort = 35000;
    private static Socket socket;
    private static NetworkController nc;

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
            MainActivity.updateConnection(1);
        } catch (IOException e) {
            MainActivity.updateConnection(0);
            e.printStackTrace();
        }
    }

    public void send(int keyStroke) {
        try {
            OutputStream out = socket.getOutputStream();
            out.write(keyStroke);
        } catch (IOException e) {}
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

    public static void resetNetworkController() {
        instance = null;
    }
}