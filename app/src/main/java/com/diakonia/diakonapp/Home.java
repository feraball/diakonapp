package com.diakonia.diakonapp;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.diakonia.diakonapp.adapters.SectionsPagerAdapter;


public class Home extends AppCompatActivity {

    private static final String TAG = "Home";

    private TabLayout    mTabLayout;
    private AppBarLayout mAppBarLayout;
    private ViewPager    mViewPager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Toast.makeText(Home.this, "HOME", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_profile:
                    Toast.makeText(Home.this, "PROFILE", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_rewards:
                    Toast.makeText(Home.this, "REWARD", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home button
//            case android.R.id.home:
//                Log.d("pilaaaaa", "onOptionsItemSelected: " + item.getItemId());
//                NavUtils.navigateUpFromSameTask(this);
//
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d("pilaaaaa", "HOME onCreate: ");

        //TABS
        mTabLayout    = findViewById(R.id.tabs);
        mAppBarLayout = findViewById(R.id.appbar);
        mViewPager    = findViewById(R.id.container);
        SectionsPagerAdapter mAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        //Adding Fragments
        mAdapter.AddFragment(new Category1Fragment(), "Category 1");
//        mAdapter.AddFragment(new Category1Fragment(), "Category 2");
//        mAdapter.AddFragment(new Category1Fragment(), "Category 3");

        //Adapter Set-up
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        //BOTTOM NAVIGATION
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


//        new JsonTask().execute("https://diakoniapp.firebaseio.com/instituciones.json");
    }


//    private class JsonTask extends AsyncTask<String, String, String> {
//
//        private JSONArray jsonArray;
//
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            pd = new ProgressDialog(Home.this);
//            pd.setMessage("Cargando Datos");
//            pd.setCancelable(false);
//            pd.show();
//        }
//
//        protected String doInBackground(String... params) {
//
//            HttpURLConnection connection = null;
//            BufferedReader reader = null;
//
//            try {
//                URL url = new URL(params[0]);
//                connection = (HttpURLConnection) url.openConnection();
//                connection.connect();
//
//
//                InputStream stream = connection.getInputStream();
//
//                reader = new BufferedReader(new InputStreamReader(stream));
//
//                StringBuffer buffer = new StringBuffer();
//                String line = "";
//
//                while ((line = reader.readLine()) != null) {
//                    buffer.append(line + "\n");
//                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
//
//                }
//
//                return buffer.toString();
//
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (connection != null) {
//                    connection.disconnect();
//                }
//                try {
//                    if (reader != null) {
//                        reader.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
////            Log.d("mequierodormir",result);
////
////            Gson gson = new Gson();
////            String jsonOutput = result;
////            Type listType = new TypeToken<List<Institution>>(){}.getType();
////            List<Institution> institutions = gson.fromJson(jsonOutput, listType);
////
////            for (Institution item:institutions) {
////                Log.d("mequierodormir",item.getName());
////                lstInstituciones.add(item);
////            }
//
//
//            try {
//                jsonArray = new JSONArray(result);
//
//
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//
//
//                    JSONObject instituciones = jsonArray.getJSONObject(i);
//
//                    String nombre = null;
//                    try {
//                        nombre = instituciones.getString("NOMBRE DE LA INSTITUCION ");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    String asistencia = null;
//                    try {
//                        asistencia = instituciones.getString("ASISTENCIA");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    String cantidad = null;
//                    try {
//                        cantidad = instituciones.getString("CANTIDAD");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    String poblacionAtendida = null;
//                    try {
//                        poblacionAtendida = instituciones.getString("POBLACION ATENDIDA");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    String direccion = null;
//                    try {
//                        direccion = instituciones.getString("DIRECCION ");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    String telefono = null;
//                    try {
//                        telefono = instituciones.getString("TELEFONO");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    String atencion = null;
//                    try {
//                        atencion = instituciones.getString("ATENCION");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    String horarioDeAtencion = null;
//                    try {
//                        horarioDeAtencion = instituciones.getString("HORARIO DE ATENCION");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    String correo = null;
//                    try {
//                        correo = instituciones.getString("CORREOS");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    String servicioBrindado = null;
//                    try {
//                        servicioBrindado = instituciones.getString("SERVICIO QUE BRINDAN");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//                    String longitud = null;
//                    try {
//                        longitud = instituciones.getString("LONGITUD");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//                    String latitud = null;
//                    try {
//                        longitud = instituciones.getString("LATITUD");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    String urlFoto = null;
//                    try {
//                        urlFoto = instituciones.getString("FOTO");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    Log.d("prueba", nombre);
//
//
//
//                    lstInstituciones.add(new Institution(nombre, asistencia, telefono, longitud, latitud, cantidad, direccion, horarioDeAtencion,correo, urlFoto));
//
//                    RecyclerView myrv = (RecyclerView) findViewById(R.id.recycler_view);
//                    RecyclerAdapter myAdapter = new RecyclerAdapter(contexto, lstInstituciones);
//                    myrv.setLayoutManager(new GridLayoutManager(contexto,1));
//                    myrv.setAdapter(myAdapter);
//
//
//
//
//
//
//            Log.d("prueba", "nombre:"+nombre+";"+asistencia+";"+cantidad+";"+poblacionAtendida+";"+direccion+";"+telefono+";"+atencion+";"+horarioDeAtencion+";"+correo+";"+servicioBrindado+"\n\n");
//
//
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            if (pd.isShowing()) {
//                pd.dismiss();
//            }
//
//
//        }
//
//
//    }


}
