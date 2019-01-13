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


        dataSet.add(new Institution("Comedor 1","COMEDOR","+593666666666","","","100","Av. Quito y Machala","Lunes a Sabados","lol@gmail.com", "https://www.diakonia-ec.org/img/banco_1.jpg"));
        dataSet.add(new Institution("Comedor 2","COMEDOR","+593666666666","","","40","Av. Quito y Machala","Lunes a Domingo","lowdwdwd3@gmail.com", "https://www.diakonia-ec.org/img/banco_1.jpg"));
        dataSet.add(new Institution("Comedor 3","COMEDOR","+593666666666","","","25","Av. Quito y Machala","Lunes a Viernes","qwfqfqfqfd@gmail.com", "https://www.diakonia-ec.org/img/banco_1.jpg"));
        dataSet.add(new Institution("Comedor 4","COMEDOR","+593666666666","","","80","Av. Quito y Machala","Lunes a Sabados","235fvwc@gmail.com", "https://www.diakonia-ec.org/img/banco_1.jpg"));
        dataSet.add(new Institution("Comedor 5","COMEDOR","+593666666666","","","150","Av. Quito y Machala","Lunes a Viernes","f14tgwegwq@gmail.com", "https://www.diakonia-ec.org/img/banco_1.jpg"));

        Log.d("pilaaaaa", "setInstitutions: CREATED 5");
    }

}
