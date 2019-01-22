package com.diakonia.diakonapp.viewmodels;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.diakonia.diakonapp.Category1Fragment;
import com.diakonia.diakonapp.models.Institution;
import com.diakonia.diakonapp.repositories.InstitutionRepository;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Category1ViewModel extends ViewModel {

    private static final String TAG = "Category1ViewModel";

//    private static final DatabaseReference INSTITUTIONS_REF = FirebaseDatabase.getInstance().getReference().child("instituciones");
//
//    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(INSTITUTIONS_REF);
//    //private final LiveData<List<Institution>> intitutionsLiveData = Transformations.map(liveData, new Deserializer());
//    private MutableLiveData<List<Institution>> mInstitutions = new MutableLiveData<>();



//    private class Deserializer implements Function<DataSnapshot, List<Institution>> {
//        @Override
//        public List<Institution> apply(DataSnapshot dataSnapshot) {
//            return dataSnapshot.getValue(List<Institution>.class);
//        }
//    }

//    @NonNull
//    public LiveData<List<Institution>> getInstitutionLiveData() {
//
//        DataSnapshot dataSnapshot = liveData.getValue();
//
//        for (DataSnapshot snap: dataSnapshot.getChildren()){
//            mInstitutions.getValue().add(snap.getValue(Institution.class));
//        }
//
//
//        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Institution>().setQuery(INSTITUTIONS_REF, Institution.class).build();
//        FirebaseRecyclerAdapter adapter;
//
//        FirebaseDatabase.getInstance()
//                .getReference("instituciones")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()) {
//                            mInstitutions.postValue((List<Institution>) dataSnapshot);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//
//
//        //mInstitutions = (List<Institution>) values;
//        return mInstitutions;
//    }


    private MutableLiveData<List<Institution>> mInstitutions = new MutableLiveData<>();
    //private InstitutionRepository mRepo;
    //private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();


    public Category1ViewModel() {
        super();

        loadDataFromFireBase();
        //mInstitutions = InstitutionRepository.getInstance().getInstitutions();
    }

//    public void init(){
//        Log.d("TEST VIEWMODEL", "ViewModel init:");
//        if(mInstitutions != null){
//            Log.d("TEST VIEWMODEL", "mInstitutions is NOT null");
//            return;
//        }
//        Log.d("TEST VIEWMODEL", "mInstitutions is null");
//        mRepo = InstitutionRepository.getInstance();
//
//        mInstitutions = mRepo.getInstitutions();
//    }

//    public void addNewValue(final Institution institution){
//        mIsUpdating.setValue(true);
//        new updateTask().execute(institution);
//    }
//
    public LiveData<List<Institution>> getInstitutions(){
        //mIsUpdating.setValue(true);
        Log.d("TEST VIEWMODEL", "GET Institutions()");

//        if (mInstitutions == null) {
//            mInstitutions = new MutableLiveData<List<Institution>>();
//
//        }
        return mInstitutions;
    }
//
    public void loadDataFromFireBase() {

        //mIsUpdating.setValue(true);

        FirebaseDatabase.getInstance()
                .getReference("instituciones").keepSynced(true);

        FirebaseDatabase.getInstance()
                .getReference("instituciones")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Institution> dataSet = new ArrayList<>();
                        if (dataSnapshot.exists()) {
//                            myLiveData.postValue((List<Institution>) dataSnapshot);

                            for (DataSnapshot snap : dataSnapshot.getChildren()){
                                dataSet.add(snap.getValue(Institution.class));
                            }
                            Log.d("TEST VIEWMODEL", "DATASET LLENADO");
                        }
                        mInstitutions.setValue(dataSet);

                        //mIsUpdating.setValue(false);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
//
//    public LiveData<Boolean> getIsUpdating(){
//        return mIsUpdating;
//    }



    private class updateTask extends AsyncTask<Institution, Void, Institution>{

        @Override
        protected Institution doInBackground(Institution... institutions) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return institutions[0];
        }

        @Override
        protected void onPostExecute(Institution institution) {
            super.onPostExecute(institution);
//            List<Institution> currentInstitutions = mInstitutions.getValue();
//            currentInstitutions.add(institution);
//            mInstitutions.postValue(currentInstitutions);
//            mIsUpdating.postValue(false);
        }

    }

}
