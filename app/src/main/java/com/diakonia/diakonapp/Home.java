package com.diakonia.diakonapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.diakonia.diakonapp.adapters.RecyclerAdapter;
import com.diakonia.diakonapp.adapters.SectionsPagerAdapter;
import com.diakonia.diakonapp.models.Institution;
import com.google.gson.Gson;
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

public class Home extends AppCompatActivity {

    Context contexto;

    private TabLayout    mTabLayout;
    private AppBarLayout mAppBarLayout;
    private ViewPager    mViewPager;

    List<Institution> lstInstituciones = new ArrayList<>();
//    private SectionsPagerAdapter mSectionsPagerAdapter;

    ProgressDialog pd;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Toast.makeText(contexto, "HOME", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_profile:
                    Toast.makeText(contexto, "PROFILE", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_rewards:
                    Toast.makeText(contexto, "REWARD", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d("mequierodormir","Home Activity On Create");


        //TABS
        mTabLayout    = findViewById(R.id.tabs);
        mAppBarLayout = findViewById(R.id.appbar);
        mViewPager   = findViewById(R.id.container);
        SectionsPagerAdapter mAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        //Adding Fragments
        mAdapter.AddFragment(new Category1Fragment(), "Category 1");
//        mAdapter.AddFragment(new Category1Fragment(), "Category 2");
//        mAdapter.AddFragment(new Category1Fragment(), "Category 3");

        //Adapter Set-up
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        //BOTTOM NAVIGATION
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        contexto = this;
        new JsonTask().execute("https://diakoniapp.firebaseio.com/instituciones.json");
    }

//
//    public static class PlaceholderFragment extends Fragment {
//
//        private static final String ARG_SECTION_NUMBER = "section_number";
//
//        public PlaceholderFragment() {
//
//        }
//
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.category1_fragment, container, false);
//
//            Log.d("mequierodormir","INFLATER?");
////            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
////            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//            return rootView;
//        }
//    }




    private class JsonTask extends AsyncTask<String, String, String> {

        private JSONArray jsonArray;

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(Home.this);
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

//            Log.d("mequierodormir",result);
//
//            Gson gson = new Gson();
//            String jsonOutput = result;
//            Type listType = new TypeToken<List<Institution>>(){}.getType();
//            List<Institution> institutions = gson.fromJson(jsonOutput, listType);
//
//            for (Institution item:institutions) {
//                Log.d("mequierodormir",item.getName());
//                lstInstituciones.add(item);
//            }


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



                    lstInstituciones.add(new Institution(nombre, asistencia, telefono, longitud, latitud, cantidad, direccion, horarioDeAtencion,correo, urlFoto));

                    RecyclerView myrv = (RecyclerView) findViewById(R.id.recycler_view);
                    RecyclerAdapter myAdapter = new RecyclerAdapter(contexto, lstInstituciones);
                    myrv.setLayoutManager(new GridLayoutManager(contexto,1));
                    myrv.setAdapter(myAdapter);






            Log.d("prueba", "nombre:"+nombre+";"+asistencia+";"+cantidad+";"+poblacionAtendida+";"+direccion+";"+telefono+";"+atencion+";"+horarioDeAtencion+";"+correo+";"+servicioBrindado+"\n\n");


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
