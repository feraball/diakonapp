package com.diakonia.diakonapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.diakonia.diakonapp.models.Institution;
import com.diakonia.diakonapp.repositories.InstitutionRepository;

import java.util.List;

public class Category1ViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<List<Institution>> mInstitutions;
    private InstitutionRepository mRepo;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();

    public void init(){
        if(mInstitutions != null){
            return;
        }
        mRepo = InstitutionRepository.getInstance();
        mInstitutions = mRepo.getInstitutions();
    }

    public void addNewValue(final Institution institution){
        mIsUpdating.setValue(true);
        new updateTask().execute(institution);
    }

    public LiveData<List<Institution>> getInstitutions(){
        return mInstitutions;
    }

    public LiveData<Boolean> getIsUpdating(){
        return mIsUpdating;
    }



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
            List<Institution> currentInstitutions = mInstitutions.getValue();
            currentInstitutions.add(institution);
            mInstitutions.postValue(currentInstitutions);
            mIsUpdating.postValue(false);
        }

    }

}
