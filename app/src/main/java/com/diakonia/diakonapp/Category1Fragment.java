package com.diakonia.diakonapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
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

import com.diakonia.diakonapp.adapters.RecyclerAdapter;
import com.diakonia.diakonapp.models.Institution;
import com.diakonia.diakonapp.viewmodels.Category1ViewModel;
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

public class Category1Fragment extends Fragment {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private Category1ViewModel mCategory1ViewModel;
    private String url = "https://diakoniapp.firebaseio.com/instituciones.json";

    public static Category1Fragment newInstance() {
        return new Category1Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.d("mequierodormir","Fragment On CreateView");
//
//        if(getActivity()!=null) mContext = getActivity();
//
//
//        mRecyclerView = getActivity().findViewById(R.id.recycler_view);
//
//
//        mCategory1ViewModel = ViewModelProviders.of(this).get(Category1ViewModel.class);
//        mCategory1ViewModel.init();
//
//
//        Log.d("mequierodormir",mCategory1ViewModel.getInstitutions().getValue().toString());


//        mAdapter = new RecyclerAdapter();
//        mCategory1ViewModel.getInstitutions().observe(this, new Observer<List<Institution>>() {
//            @Override
//            public void onChanged(@Nullable List<Institution> institutions) {
//                mAdapter.notifyDataSetChanged();
//            }
//        });
//
//        initRecyclerView();
        return inflater.inflate(R.layout.category1_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("mequierodormir","Fragment On ActivityCreated");
//        mCategory1ViewModel = ViewModelProviders.of(this).get(Category1ViewModel.class);
        // TODO: Use the ViewModel
    }

    public void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d("mequierodormir","Fragment On Create");
    }

    private void initRecyclerView(){
        mAdapter = new RecyclerAdapter(mContext, mCategory1ViewModel.getInstitutions().getValue());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);

    }





}
