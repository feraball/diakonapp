package com.diakonia.diakonapp;


import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.diakonia.diakonapp.adapters.RecyclerAdapter;
import com.diakonia.diakonapp.models.Institution;
import com.diakonia.diakonapp.viewmodels.Category1ViewModel;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class Category1Fragment extends Fragment implements RecyclerAdapter.OnCardListener{

    private static final String TAG = "Category1Fragment";

    private Context            mContext;
    private RecyclerView       mRecyclerView;
    private RecyclerAdapter    mAdapter;
    private Category1ViewModel mCategory1ViewModel;
    private ProgressDialog     pd;
    private String url = "https://diakoniapp.firebaseio.com/instituciones.json";


//    public static Category1Fragment newInstance() {
//        return new Category1Fragment();
//    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.category1_fragment, container, false);

        if(getActivity()!=null) mContext = getActivity();

        mRecyclerView = v.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

//        mProgressBar  = v.findViewById(R.id.progress_circular_id);

//        pd = new ProgressDialog(mContext);
//        pd.setMessage("Cargando Datos");
//        pd.setCancelable(false);
        //pd.show();


        mCategory1ViewModel = ViewModelProviders.of(this).get(Category1ViewModel.class);

        //mCategory1ViewModel.init();


        //mAdapter = new RecyclerAdapter(mContext, mCategory1ViewModel.getInstitutions().getValue(), this);

        //mCategory1ViewModel.getInstitutions().observe(this, institutionsObserver);


        mCategory1ViewModel.getInstitutions().observe(this, new Observer<List<Institution>>() {
            @Override
            public void onChanged(@Nullable List<Institution> institutions) {
                if (institutions != null) {
                    Log.d("TEST VIEWMODEL", "INSTITUTIONS NOT NULL - HUBO CAMBIO");
                    Log.d("TEST VIEWMODEL", "INSTITUTIONS SIZE: " + institutions.size());

                    //mAdapter.notifyDataSetChanged();

                    mRecyclerView.setAdapter(new RecyclerAdapter(mContext, institutions, Category1Fragment.this));
//                }else{
//                    Log.d("TEST VIEWMODEL", "INSTITUTIONS NULL");
                }
            }
        });


//        mCategory1ViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
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

        //initRecyclerView();

        return v;
    }


//    private void initRecyclerView(){
//        mAdapter = new RecyclerAdapter(mContext, mCategory1ViewModel.getInstitutions().getValue(), this);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        mRecyclerView.setAdapter(mAdapter);
//    }


    @Override
    public void onCardClick(int position) {
        Log.d("probandoclicks", "onCardClick: "+ position);

        Institution institutionToGo = mCategory1ViewModel.getInstitutions().getValue().get(position);
        Intent intent = new Intent(mContext, Perfil_Institucion.class);
        intent.putExtra("institution_data", institutionToGo);
        startActivity(intent);
    }
}
