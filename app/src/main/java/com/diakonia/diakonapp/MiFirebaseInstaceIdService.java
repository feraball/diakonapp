package com.diakonia.diakonapp;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MiFirebaseInstaceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("prueba", token);
    }
}
