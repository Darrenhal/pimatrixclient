package de.pimatrix.gamecontroller.gameactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import de.pimatrix.gamecontroller.R;
import de.pimatrix.gamecontroller.backend.NetworkingTask;

public class SnakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);

        setTitle("Snake");

        NetworkingTask sender = new NetworkingTask();
        sender.execute(new Integer[]{1});
    }

    public void left(View view) {
        NetworkingTask sender = new NetworkingTask();
        sender.execute(new Integer[]{2});
    }

    public void right(View view) {
        NetworkingTask sender = new NetworkingTask();
        sender.execute(new Integer[]{3});
    }

    public void up(View view) {
        NetworkingTask sender = new NetworkingTask();
        sender.execute(new Integer[]{4});
    }

    public void down(View view) {
        NetworkingTask sender = new NetworkingTask();
        sender.execute(new Integer[]{5});
    }



/*
    @Override
    protected void onPause() {
        super.onPause();
        NetworkingTask sender = new NetworkingTask();
        sender.execute(new Integer[]{0});
    }
*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkingTask sender = new NetworkingTask();
        sender.execute(new Integer[]{6});
    }

}
