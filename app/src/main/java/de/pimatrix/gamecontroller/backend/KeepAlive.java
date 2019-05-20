package de.pimatrix.gamecontroller.backend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class KeepAlive implements Runnable{

    private InputStream in;
    private OutputStream out;

    KeepAlive(String serverIP) {
        int keepAlivePort = 42000;
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

        while(NetworkController.isConnected()) {
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
                }

                if (waitingForResponse && keepAlive_waitedCycles == 3) {
                    NetworkController.setConnection(false);
                }

            } catch (IOException e) {}

            if (NetworkController.isConnected()) {
                sleep();

                try {
                    out.write(1);
                } catch (IOException e) {}
            }
        }
    }

    private void sleep() {
        int keepAlive_timeout = 200;

        try {
            Thread.sleep(keepAlive_timeout);
        } catch (InterruptedException e) {}
    }
}