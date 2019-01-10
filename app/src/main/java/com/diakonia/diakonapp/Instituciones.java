package com.diakonia.diakonapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import com.introtoandroid.samplematerial.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Instituciones extends AppCompatActivity {

    private JSONArray jsonArray;

    Context contexto;
    ProgressDialog pd;
    List<ClaseInstituciones> lstInstituciones = new ArrayList<>();

        private String url = "https://diakoniapp.firebaseio.com/instituciones.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instituciones);


        //lstInstituciones = new ArrayList<>();

        new JsonTask().execute(url);
        contexto= this;

//        lstInstituciones.add(new ClaseInstituciones("aaaa"));
//        lstInstituciones.add(new ClaseInstituciones("bbbb"));
//
//        RecyclerView myrv = (RecyclerView) findViewById(R.id.recycler_view);
//        AdaptadorInstituciones myAdapter = new AdaptadorInstituciones(contexto, lstInstituciones);
//        myrv.setLayoutManager(new GridLayoutManager(contexto,2));
//        myrv.setAdapter(myAdapter);



        Log.d("prueba", "hola mundo"+lstInstituciones.toString());


    }




    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(Instituciones.this);
            pd.setMessage("Cargando Datos");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);





            try {
                jsonArray = new JSONArray(result);


                for (int i = 0; i < jsonArray.length(); i++) {


                    JSONObject instituciones = jsonArray.getJSONObject(i);


                    String nombre = null;
                    try {
                        nombre = instituciones.getString("NOMBRE DE LA INSTITUCION ");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String asistencia = null;
                    try {
                        asistencia = instituciones.getString("ASISTENCIA");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String cantidad = null;
                    try {
                        cantidad = instituciones.getString("CANTIDAD");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String poblacionAtendida = null;
                    try {
                        poblacionAtendida = instituciones.getString("POBLACION ATENDIDA");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String direccion = null;
                    try {
                        direccion = instituciones.getString("DIRECCION ");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String telefono = null;
                    try {
                        telefono = instituciones.getString("TELEFONO");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String atencion = null;
                    try {
                        atencion = instituciones.getString("ATENCION");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String horarioDeAtencion = null;
                    try {
                        horarioDeAtencion = instituciones.getString("HORARIO DE ATENCION");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String correo = null;
                    try {
                        correo = instituciones.getString("CORREOS");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String servicioBrindado = null;
                    try {
                        servicioBrindado = instituciones.getString("SERVICIO QUE BRINDAN");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    String longitud = null;
                    try {
                        longitud = instituciones.getString("LONGITUD");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    String latitud = null;
                    try {
                        longitud = instituciones.getString("LATITUD");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String urlFoto = null;
                    try {
                        urlFoto = instituciones.getString("FOTO");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("prueba", nombre);



        lstInstituciones.add(new ClaseInstituciones(nombre, asistencia, telefono, longitud, latitud, cantidad, direccion, horarioDeAtencion,correo, urlFoto));

        RecyclerView myrv = (RecyclerView) findViewById(R.id.recycler_view);
        AdaptadorInstituciones myAdapter = new AdaptadorInstituciones(contexto, lstInstituciones);
        myrv.setLayoutManager(new GridLayoutManager(contexto,1));
        myrv.setAdapter(myAdapter);






                    //Log.d("prueba", "nombre:"+nombre+";"+asistencia+";"+cantidad+";"+poblacionAtendida+";"+direccion+";"+telefono+";"+atencion+";"+horarioDeAtencion+";"+correo+";"+servicioBrindado+"\n\n");


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (pd.isShowing()) {
                pd.dismiss();
            }


        }


    }
}
