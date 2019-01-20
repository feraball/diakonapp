package com.diakonia.diakonapp;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Diakonapp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
