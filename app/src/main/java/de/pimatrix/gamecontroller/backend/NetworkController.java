package de.pimatrix.gamecontroller.backend;

import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

import de.pimatrix.gamecontroller.MainActivity;

public class NetworkController implements Runnable, Serializable {

    private String serverIP = "141.72.138.229";
    private int serverPort = 35000;
    private static Socket socket;

    @Override
    public void run() {
        connect(serverIP, serverPort);
    }

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
}
