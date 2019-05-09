package de.pimatrix.gamecontroller.backend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.pimatrix.gamecontroller.MainActivity;
import de.pimatrix.gamecontroller.R;

public class Settings extends AppCompatActivity {

    private NetworkController connector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle("Settings");

        connector = (NetworkController) getIntent().getSerializableExtra("NetworkController");
    }

    @Override
    protected void onPause() {
        super.onPause();
        connector.send(0);
    }

    public void connect(View view) {
        TextView txtSetServerIP = findViewById(R.id.txtSetServerIP);
        TextView txtSetServerPort = findViewById(R.id.txtSetServerPort);

        Log.d("Test", "TextViews extracted");
        String serverIP = txtSetServerIP.getText().toString();
        int serverPort = Integer.parseInt(txtSetServerPort.getText().toString());

        connector.connect(serverIP, serverPort);
    }
}
