package com.diakonia.diakonapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.diakonia.diakonapp.models.Reward;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RewardsViewModel extends ViewModel {

    private static final String TAG = "RewardsViewModel";

    private MutableLiveData<List<Reward>> mRewards = new MutableLiveData<>();

    public RewardsViewModel() {
        super();

        loadDataFromFireBase();

    }

    public LiveData<List<Reward>> getRewards(){

        Log.d("TEST VIEWMODEL", "GET Rewards()");

        return mRewards;
    }


    private void loadDataFromFireBase() {

        FirebaseDatabase.getInstance()
                .getReference("rewards")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Reward> dataSet = new ArrayList<>();
                        if (dataSnapshot.exists()) {
//                            myLiveData.postValue((List<Institution>) dataSnapshot);

                            for (DataSnapshot snap : dataSnapshot.getChildren()){
                                dataSet.add(snap.getValue(Reward.class));
                            }
                            Log.d("TEST VIEWMODEL", "DATASET Rewards LLENADO");
                        }
                        mRewards.setValue(dataSet);

                        //mIsUpdating.setValue(false);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }


}
