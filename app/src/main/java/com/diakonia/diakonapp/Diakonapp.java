package com.diakonia.diakonapp;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.google.firebase.database.FirebaseDatabase;

public class Diakonapp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
