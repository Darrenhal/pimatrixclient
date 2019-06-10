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
        setContentView(R.layout.activity_settings);

        setTitle("Settings");

        invokedByOnCreate = true;
        connector = NetworkController.getInstance();
    }

    @Override
    protected void onPause() {
        if (!backPressed && NetworkController.isConnected()) {
            new NetworkingTask().execute(new Integer[]{0});
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        backPressed = true;
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        backPressed = false;
        if (!invokedByOnCreate) {
            NetworkController.resetNetworkController();
            new Thread(NetworkController.getInstance()).start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        invokedByOnCreate = false;
        super.onResume();
    }

    public void resetServer(View view) {
        new NetworkingTask().execute(101);
    }

    public void connect(View view) {
        TextView txtSetServerIP = findViewById(R.id.txtSetServerIP);
        TextView txtSetServerPort = findViewById(R.id.txtSetServerPort);

        String serverIP = txtSetServerIP.getText().toString();
        int serverPort = Integer.parseInt(txtSetServerPort.getText().toString());

        connector.setServerIP(serverIP);
        connector.setServerPort(serverPort);

        new Thread(connector).start();
        onBackPressed();
        finish();
    }
}