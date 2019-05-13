package de.pimatrix.gamecontroller.backend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import de.pimatrix.gamecontroller.R;

public class Settings extends AppCompatActivity {

    private NetworkController connector;
    private boolean backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle("Settings");

        connector = NetworkController.getInstance();
    }

    @Override
    protected void onPause() {
        if (!backPressed) {
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
        NetworkController.resetNetworkController();
        new Thread(NetworkController.getInstance()).start();
        super.onResume();
    }
    public void connect(View view) {
        TextView txtSetServerIP = findViewById(R.id.txtSetServerIP);
        TextView txtSetServerPort = findViewById(R.id.txtSetServerPort);

        String serverIP = txtSetServerIP.getText().toString();
        int serverPort = Integer.parseInt(txtSetServerPort.getText().toString());

        connector.setServerIP(serverIP);
        connector.setServerPort(serverPort);

        new Thread(connector).start();
        finish();
    }
}