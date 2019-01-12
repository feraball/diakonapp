package com.diakonia.diakonapp.repositories;

import android.app.ProgressDialog;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.diakonia.diakonapp.Home;
import com.diakonia.diakonapp.R;
import com.diakonia.diakonapp.adapters.RecyclerAdapter;
import com.diakonia.diakonapp.models.Institution;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class InstitutionRepository {

    private static InstitutionRepository instance;
    private ArrayList<Institution> dataSet = new ArrayList<>();
    private String url = "https://diakoniapp.firebaseio.com/instituciones.json";
    ProgressDialog pd;

    public static InstitutionRepository getInstance(){
        if(instance == null){
            instance = new InstitutionRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Institution>> getInstitutions(){
        setInstitutions();

        MutableLiveData<List<Institution>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setInstitutions(){
        Log.d("JSON","SETInstituciones");

        dataSet.add(new Institution("SANjkajagafayufy"));
        dataSet.add(new Institution("SANjkajagafayufy"));
        dataSet.add(new Institution("SANjkajagafayufy"));
        dataSet.add(new Institution("SANjkajagafayufy"));
        dataSet.add(new Institution("SANjkajagafayufy"));


    }




}
