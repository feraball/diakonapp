package com.diakonia.diakonapp;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.ncapdevi.fragnav.FragNavController;

import java.util.ArrayList;
import java.util.List;


public class Home extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, UserProfileFragment.OnFragmentInteractionListener, RewardsFragment.OnFragmentInteractionListener{

    private static final String TAG = "Home";
//    private FragNavController.Builder builder;
//
//
//    private final int INDEX_HOME = FragNavController.TAB1;
//    private final int INDEX_USERPROFILE = FragNavController.TAB2;
//    private final int INDEX_REWARDS = FragNavController.TAB3;
//    private final int INDEX_FRIENDS = FragNavController.TAB4;
//    private final int INDEX_FOOD = FragNavController.TAB5;


    //private FragNavController mNavController;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();

//                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    //Toast.makeText(Home.this, "HOME", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigation_profile:
                    selectedFragment = new UserProfileFragment();
                    //Toast.makeText(Home.this, "PROFILE", Toast.LENGTH_SHORT).show();
                    //
                    break;
                case R.id.navigation_rewards:
                    selectedFragment = new RewardsFragment();
                    //Toast.makeText(Home.this, "REWARDS", Toast.LENGTH_SHORT).show();
                    break;
            }

                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container_id, selectedFragment).addToBackStack(null).commit();

            return true;
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



        //BOTTOM NAVIGATION
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //builder = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.main_fragment_container_id);

//        List<Fragment> fragments = new ArrayList<>(3);
//
//        fragments.add(HomeFragment.newInstance());
//        fragments.add(UserProfileFragment.newInstance());
//        fragments.add(RewardsFragment.newInstance());
//
//
//        builder.rootFragments(fragments);
//
//        mFragNavController = builder.build();
//
//        mFragNavController.switchTab(NavController.TAB1);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container_id, new HomeFragment()).commit();


//        new JsonTask().execute("https://diakoniapp.firebaseio.com/instituciones.json");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        if (mFragNavController != null) {
//            mFragNavController.onSaveInstanceState(outState);
//        }
//    }

//    @Override
//    public Fragment getRootFragment(int i) {
//        switch (index) {
//            case INDEX_RECENTS:
//                return RecentsFragment.newInstance(0);
//            case INDEX_FAVORITES:
//                return FavoritesFragment.newInstance(0);
//            case INDEX_NEARBY:
//                return NearbyFragment.newInstance(0);
//            case INDEX_FRIENDS:
//                return FriendsFragment.newInstance(0);
//            case INDEX_FOOD:
//                return FoodFragment.newInstance(0);
//        }
//        throw new IllegalStateException("Need to send an index that we know");
//        return null;
//    }


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
////                Log.d("mequierodormir",item.getNombre());
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
