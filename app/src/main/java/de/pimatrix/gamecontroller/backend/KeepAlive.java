package de.pimatrix.gamecontroller.backend;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class KeepAlive implements Runnable{

    private InputStream in;
    private OutputStream out;

    KeepAlive(String serverIP) {
        int keepAlivePort = 42000;
        Log.d("KeepAlive-Test", "KeepAlive created");
        try {
            Socket keepAliveSocket = new Socket(serverIP, keepAlivePort);
            out = keepAliveSocket.getOutputStream();
            in = keepAliveSocket.getInputStream();
        } catch (IOException e) {}
    }

    @Override
    public void run() {
        boolean waitingForResponse = true;
        int keepAlive_waitCycles = 3;
        int keepAlive_waitedCycles = 0;

        Log.d("KeepAlive-Test", "KeepAlive running");

        while(NetworkController.isConnected()) {
            Log.d("KeepAlive-Test", "while-loop");
            sleep();
            try {
                while (waitingForResponse && keepAlive_waitedCycles < keepAlive_waitCycles) {
                    if (in.available() > 0) {
                        in.read();
                        waitingForResponse = false;
                    } else {
                        keepAlive_waitedCycles++;
                        sleep();
                    }

                    Log.d("KeepAlive-Test", "receiving - cycle: " + keepAlive_waitedCycles);
                }

                if (waitingForResponse && keepAlive_waitedCycles == 3) {
                    NetworkController.setConnection(false);
                } else {
                    keepAlive_waitedCycles = 0;
                }

            } catch (IOException e) {}

            if (NetworkController.isConnected()) {
                sleep();

                try {
                    out.write(1);
                    Log.d("KeepAlive-Test", "sending");
                } catch (IOException e) {}
            }
        }
    }

    private void sleep() {
        int keepAlive_timeout = 3000;

        try {
            Thread.sleep(keepAlive_timeout);
        } catch (InterruptedException e) {}
    }
}