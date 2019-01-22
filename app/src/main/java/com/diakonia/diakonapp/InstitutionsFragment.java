package com.diakonia.diakonapp;


import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diakonia.diakonapp.adapters.InstitutionAdapter;
import com.diakonia.diakonapp.models.Institution;
import com.diakonia.diakonapp.viewmodels.InstitutionsViewModel;

import java.util.List;

public class InstitutionsFragment extends Fragment implements InstitutionAdapter.OnCardListener{

    private static final String TAG = "InstitutionsFragment";

    private Context            mContext;
    private RecyclerView       mRecyclerView;
    private InstitutionAdapter mAdapter;
    private InstitutionsViewModel mInstitutionsViewModel;
    private ProgressDialog     pd;
    private String url = "https://diakoniapp.firebaseio.com/instituciones.json";


//    public static InstitutionsFragment newInstance() {
//        return new InstitutionsFragment();
//    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_recycler, container, false);

        if(getActivity()!=null) mContext = getActivity();

        getActivity().setTitle(R.string.app_name);

        mRecyclerView = v.findViewById(R.id.recycler_view_id);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

//        mProgressBar  = v.findViewById(R.id.progress_circular_id);

//        pd = new ProgressDialog(mContext);
//        pd.setMessage("Cargando Datos");
//        pd.setCancelable(false);
        //pd.show();


        mInstitutionsViewModel = ViewModelProviders.of(this).get(InstitutionsViewModel.class);


        mInstitutionsViewModel.getInstitutions().observe(this, new Observer<List<Institution>>() {
            @Override
            public void onChanged(@Nullable List<Institution> institutions) {
                if (institutions != null) {
                      //mAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(new InstitutionAdapter(mContext, institutions, InstitutionsFragment.this));
                }
            }
        });


//        mInstitutionsViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
//            @Override
//            public void onChanged(@Nullable Boolean aBoolean) {
//                if (aBoolean){
//                    pd.show();
//
//                }else{
//                    //mAdapter.notifyDataSetChanged();
//                    pd.hide();
//
//                }
//            }
//        });


        return v;
    }



    @Override
    public void onCardClick(int position) {
        Log.d("probandoclicks", "onCardClick: "+ position);
        Institution institutionToGo = mInstitutionsViewModel.getInstitutions().getValue().get(position);
        Intent intent = new Intent(mContext, Perfil_Institucion.class);
        intent.putExtra("institution_data", institutionToGo);
        startActivity(intent);
    }
}
