package de.pimatrix.gamecontroller.backend;

import android.os.AsyncTask;

public class NetworkingTask extends AsyncTask<Integer, Void, Void> {

    @Override
    protected Void doInBackground(Integer... interaction) {

        Integer data = interaction[0];

        NetworkController.getInstance().send(data);

        return null;
    }
}