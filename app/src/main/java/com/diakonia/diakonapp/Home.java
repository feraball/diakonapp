package com.diakonia.diakonapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;


public class Home extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, UserProfileFragment.OnFragmentInteractionListener, RewardsFragment.OnFragmentInteractionListener, DonationHistoryFragment.OnFragmentInteractionListener{

    private static final String TAG = "Home";
    BottomNavigationView mBNV;

    private final Fragment institutionsFragment = InstitutionsFragment.newInstance();
    private final Fragment userProfileFragment  = UserProfileFragment.newInstance();
    private final Fragment rewardsFragment      = RewardsFragment.newInstance();
    private       Fragment activeFragment       = institutionsFragment;

    private GoogleApiClient                 googleApiClient;
    private FirebaseAuth                    firebaseAuth;
    private FirebaseAuth.AuthStateListener  firebaseAuthListener;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    activeFragment = institutionsFragment;
//                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    break;
                case R.id.navigation_profile:
                    activeFragment = userProfileFragment;
                    break;
                case R.id.navigation_rewards:
                    activeFragment = rewardsFragment;
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container_id, activeFragment).commit();
            return true;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //BOTTOM NAVIGATION
        mBNV = findViewById(R.id.navigation);
        mBNV.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container_id, activeFragment).commit();



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,  this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser fbUser = firebaseAuth.getCurrentUser();
//                if (fbUser != null) {
//                    setUserData(fbUser);
//                } else {
//                    goLogInScreen();
//                }
//            }
//        };

//        new JsonTask().execute("https://diakoniapp.firebaseio.com/instituciones.json");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        MenuItem homeItem = mBNV.getMenu().getItem(0);

        if (activeFragment.equals(institutionsFragment)){
            this.finish();
        }else if (activeFragment.equals(userProfileFragment) || activeFragment.equals(rewardsFragment)){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container_id, institutionsFragment).commit();
            activeFragment = institutionsFragment;
            mBNV.setSelectedItemId(homeItem.getItemId());
        }
        //super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_logout:
                Toast.makeText(this, "LOGOUT", Toast.LENGTH_SHORT);
                logOut();
                return true;
            case R.id.action_about:
                Toast.makeText(this, "ABOUT", Toast.LENGTH_SHORT);
                return true;
            default:
                break;
        }

        return false;

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        firebaseAuth.addAuthStateListener(firebaseAuthListener);
//    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void logOut() {
        firebaseAuth.signOut();

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getApplicationContext(),"error al cerrar sesión", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        if (firebaseAuthListener != null) {
//            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
//        }
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
//                    InstitutionAdapter myAdapter = new InstitutionAdapter(contexto, lstInstituciones);
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
