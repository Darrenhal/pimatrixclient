package de.pimatrix.gamecontroller.backend;

import android.os.AsyncTask;

public class NetworkingTask extends AsyncTask<Integer, Void, Void> {

    //Senden der Daten muss asynchron erfolgen, da Networking unter Android nicht in MainThread/UIThread laufen darf --> AsyncTask
    @Override
    protected Void doInBackground(Integer... interaction) {

        Integer data = interaction[0]; //zu übermittelnden Interaktionscode auslesen

        NetworkController.getInstance().send(data); //Über send() Methode des NetworkControllers senden

        return null; //keine Rückgabeparameter, da nur der Sendevorgang ausgeführt werden soll
    }
}