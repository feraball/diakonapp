package com.diakonia.diakonapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.diakonia.diakonapp.models.Donacion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
//        loadFromFirebase();

        Log.d("usereeee", FirebaseAuth.getInstance().getCurrentUser().getUid());
        loadDataFromFireBase();

//        load();
    }





    public LiveData<List<Donacion>> getDonacion(){
        return mDonation;
    }


    private void loadDataFromFireBase() {

        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference mDataRef = FirebaseDatabase.getInstance().getReference("donaciones");
        mDataRef.orderByChild("uId").equalTo(uId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Donacion> dataSet = new ArrayList<>();
                        if (dataSnapshot.exists()) {
//                            myLiveData.postValue((List<Institution>) dataSnapshot);
                            Log.d("prueba", "existe");

                            for (DataSnapshot snap : dataSnapshot.getChildren()){
                                dataSet.add(0, snap.getValue(Donacion.class));
                                Log.d("prueba", snap.child("beneficiario").toString());
                            }

                        }else{
                            Log.d("prueba", "datasnap no existe");
                        }
                        mDonation.setValue(dataSet);

                        //mIsUpdating.setValue(false);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }




    public void load() {

        FirebaseDatabase.getInstance().getReference("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {



                Log.d("prueba", snapshot.child("nombre").getValue().toString());

                for (final DataSnapshot snap : snapshot.child("donaciones").getChildren()){

                    FirebaseDatabase.getInstance().getReference("donaciones").addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            ArrayList<Donacion> dataSet = new ArrayList<>();
                            if (dataSnapshot1.exists()) {
//                            myLiveData.postValue((List<Institution>) dataSnapshot);

                                for (DataSnapshot snap : dataSnapshot1.getChildren()) {

                                    dataSet.add(snap.getValue(Donacion.class));

                                }

                                Log.d("prueba producto", dataSnapshot1.child(snap.getValue().toString()).child("producto").getValue().toString());
                                Log.d("prueba beneficiario", dataSnapshot1.child(snap.getValue().toString()).child("beneficiario").getValue().toString());
                                Log.d("prueba fecha", dataSnapshot1.child(snap.getValue().toString()).child("fechaDonacion").getValue().toString());
                                Log.d("prueba puntos", dataSnapshot1.child(snap.getValue().toString()).child("puntos").getValue().toString());
                                Log.d("prueba puntos", dataSnapshot1.child(snap.getValue().toString()).child("foto").getValue().toString());


                            }
                            mDonation.setValue(dataSet);
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
