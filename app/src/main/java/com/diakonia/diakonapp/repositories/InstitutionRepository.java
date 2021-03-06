package com.diakonia.diakonapp.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.diakonia.diakonapp.models.Institution;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InstitutionRepository {

    private static final String TAG = "InstitutionRepository";
    private DatabaseReference mDatabase;


    //private static final DatabaseReference INSTITUTIONS_REF = FirebaseDatabase.getInstance().getReference().child("instituciones");


    private static InstitutionRepository instance;
    private ArrayList<Institution> dataSet = new ArrayList<>();
    //private MutableLiveData<List<Institution>> myLiveData = new MutableLiveData<>();

//    private String url = "https://diakoniapp.firebaseio.com/instituciones.json";
//    ProgressDialog pd;

    public static InstitutionRepository getInstance() {
        if (instance == null) {
            instance = new InstitutionRepository();
//            synchronized (InstitutionRepository.class) {
//                if (instance == null) {
//                    instance = new InstitutionRepository();
//                }
//            }

        }
        return instance;
    }

    public MutableLiveData<List<Institution>> getInstitutions() {
        Log.d("TEST VIEWMODEL", "TRY TO GET INSTITUTIONS IN REPOSITORY");

        if (dataSet.size() == 0){
            loadFromFirebase();
        }

//
        MutableLiveData<List<Institution>> data = new MutableLiveData<>();
        data.setValue(dataSet);

        Log.d("TEST VIEWMODEL", "data SIZZE: "+data.getValue().size());
        return data;
    }

    public void loadFromFirebase() {
//        final MutableLiveData<List<Institution>> data = new MutableLiveData<>();
        //Log.d("TEST VIEWMODEL", "dataSetSIze before: "+myLiveData.getValue().size());

        mDatabase= FirebaseDatabase.getInstance().getReference("instituciones");
        mDatabase.keepSynced(true);

        mDatabase
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
//                            myLiveData.postValue((List<Institution>) dataSnapshot);

                                for (DataSnapshot snap : dataSnapshot.getChildren()){
                                    dataSet.add(snap.getValue(Institution.class));
                                }
                            Log.d("TEST VIEWMODEL", "DATASET LLENADO");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    //Log.d("TEST VIEWMODEL", "dataSetSIze after: "+myLiveData.getValue().size());

    //return data;

//        dataSet.add(new Institution("Comedor 1","COMEDOR","+593666666666","","","100","Av. Quito y Machala","Lunes a Sabados","lol@gmail.com", "https://www.diakonia-ec.org/img/banco_1.jpg"));
//        dataSet.add(new Institution("Comedor 2","COMEDOR","+593666666666","","","40","Av. Quito y Machala","Lunes a Domingo","lowdwdwd3@gmail.com", "https://www.diakonia-ec.org/img/banco_1.jpg"));
//        dataSet.add(new Institution("Comedor 3","COMEDOR","+593666666666","","","25","Av. Quito y Machala","Lunes a Viernes","qwfqfqfqfd@gmail.com", "https://www.diakonia-ec.org/img/banco_1.jpg"));
//        dataSet.add(new Institution("Comedor 4","COMEDOR","+593666666666","","","80","Av. Quito y Machala","Lunes a Sabados","235fvwc@gmail.com", "https://www.diakonia-ec.org/img/banco_1.jpg"));
//        dataSet.add(new Institution("Comedor 5","COMEDOR","+593666666666","","","150","Av. Quito y Machala","Lunes a Viernes","f14tgwegwq@gmail.com", "https://www.diakonia-ec.org/img/banco_1.jpg"));

    //Log.d("pilaaaaa", "setInstitutions: CREATED 5");

    }

}
