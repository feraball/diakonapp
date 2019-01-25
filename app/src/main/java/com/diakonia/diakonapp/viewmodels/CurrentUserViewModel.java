package com.diakonia.diakonapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.diakonia.diakonapp.models.Reward;
import com.diakonia.diakonapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CurrentUserViewModel extends ViewModel {

    private MutableLiveData<User> mCUser = new MutableLiveData<>();
    final FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

    public CurrentUserViewModel() {
        super();

        loadDataFromFireBase();

    }

    public LiveData<User> getCurrentUser(){

        return mCUser;
    }


    private void loadDataFromFireBase() {

        DatabaseReference mReference = FirebaseDatabase.getInstance()
                .getReference("usuarios").child(fbUser.getUid());

        mReference.keepSynced(true);

        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            Log.d("CURRENT_USER", dataSnapshot.getValue().toString());
                            mCUser.setValue(dataSnapshot.getValue(User.class));
//                            for (DataSnapshot snap : dataSnapshot.getChildren()){
//                                mCUser.setValue(snap.getValue(User.class));
//                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }


}
