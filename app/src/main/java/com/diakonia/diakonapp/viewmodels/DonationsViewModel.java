package com.diakonia.diakonapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.diakonia.diakonapp.models.Donacion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DonationsViewModel extends ViewModel {

    private  static final  String  TAG = "DonationsViewModel";

    private MutableLiveData<List<Donacion>> mDonation = new MutableLiveData<>();


    public DonationsViewModel(){
        super();
        loadDataFromFireBase();
    }


    public LiveData<List<Donacion>> getDonacion(){
        return mDonation;
    }


    private void loadDataFromFireBase() {

        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference mDataRef = FirebaseDatabase.getInstance().getReference("donaciones");

        mDataRef.keepSynced(true);
        mDataRef.orderByChild("uId").equalTo(uId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Donacion> dataSet = new ArrayList<>();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snap : dataSnapshot.getChildren()){
                                dataSet.add(0, snap.getValue(Donacion.class));
                            }
                        }
                        mDonation.setValue(dataSet);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

    }

}
