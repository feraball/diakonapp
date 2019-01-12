package com.diakonia.diakonapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.diakonia.diakonapp.models.Institution;
import com.diakonia.diakonapp.repositories.InstitutionRepository;

import java.util.List;

public class Category1ViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<List<Institution>> mInstitutions;
    private InstitutionRepository mRepo;

    public void init(){
        if(mInstitutions != null){
            return;
        }
        mRepo = InstitutionRepository.getInstance();
        mInstitutions = mRepo.getInstitutions();
    }

    public LiveData<List<Institution>> getInstitutions(){
        return mInstitutions;
    }

}
