package de.pimatrix.gamecontroller.backend;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

import de.pimatrix.gamecontroller.MainActivity;

public class NetworkController implements Runnable {

    private String serverIP = "192.168.178.24";
    private int serverPort = 35000;
    private static Socket socket;

    private static NetworkController instance;

    @Override
    public void run() {
        connect(serverIP, serverPort);
    }

    private NetworkController() {}

    public static void connect(String serverIP, int serverPort) {
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
        } catch (IOException e) {

        }
    }

    public static NetworkController getInstance() {
        if (instance == null) {
            instance = new NetworkController();
        }
        return instance;
    }
}