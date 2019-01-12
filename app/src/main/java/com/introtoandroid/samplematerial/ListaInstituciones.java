//package com.introtoandroid.samplematerial;
//
//
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.drawable.ColorDrawable;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//
//import com.diakonia.diakonapp.models.Institution;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//
//
//
//public class ListaInstituciones extends AppCompatActivity {
//    private static final String DEBUG_TAG = "AppCompatActivity";
//
//    public static final String EXTRA_UPDATE = "update";
//    public static final String EXTRA_DELETE = "delete";
//    public static final String EXTRA_NAME = "name";
//    public static final String EXTRA_COLOR = "color";
//    public static final String EXTRA_INITIAL = "initial";
//
//    public static final String TRANSITION_FAB = "fab_transition";
//    public static final String TRANSITION_INITIAL = "initial_transition";
//    public static final String TRANSITION_NAME = "name_transition";
//    public static final String TRANSITION_DELETE_BUTTON = "delete_button_transition";
//    ProgressDialog pd;
//
//
//  Institution card = new Institution();
//                    card.setId((long) i);
//                    card.setName(nombre);
//                    card.setColorResource(colors[i]);
//                    //card.setAsis(asistencia);
//                    //  card.setCantidadPersonasAtendidas(cantidad);
//                    card.setDireccion(direccion);
////                    card.setPoblacionAtendida(poblacionAtendida);
//
////                    card.setServicioBrindado(servicioBrindado);
//                    card.setTelefono(telefono);
//
//
//    private RecyclerView recyclerView;
////    private ReciclerAdapter adapter;
//    private ArrayList<Institution> cardsList = new ArrayList<>();
//    private int[] colors;
//
//    private int idActual;
//
//    private String url = "https://diakoniapp.firebaseio.com/instituciones.json";
//
//
//
//    private JSONArray jsonArray;
//
//
////    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
////    DatabaseReference mensajeRef = ref.child("instituciones");
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.AppTheme);
//        super.onCreate(savedInstanceState);
//       // setContentView(R.layout.lista_instituciones);
//
//        new JsonTask().execute(url);
//
//
//
//
//        colors = getResources().getIntArray(R.array.initial_colors);
//
//
//        //showImage();
//
//
//
//
//
//
//
//
////        if (adapter == null) {
////            adapter = new ReciclerAdapter(this, cardsList);
////        }
////        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
////        recyclerView.setAdapter(adapter);
////        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
////        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
////        fab.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Pair<View, String> pair = Pair.create(v.findViewById(R.id.fab), TRANSITION_FAB);
////
////                ActivityOptionsCompat options;
////                Activity act = ListaInstituciones.this;
////                options = ActivityOptionsCompat.makeSceneTransitionAnimation(act, pair);
////
////                Intent transitionIntent = new Intent(act, TransitionAddActivity.class);
////                act.startActivityForResult(transitionIntent, adapter.getItemCount(), options.toBundle());
////            }
////        });
//
//
//
//
//
//
//    }
//
//
////    @Override
////    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        idActual=requestCode;
////
////        Log.d(DEBUG_TAG, "requestCode is " + requestCode);
////        // if adapter.getItemCount() is request code, that means we are adding a new position
////        // anything less than adapter.getItemCount() means we are editing a particular position
////        if (requestCode == adapter.getItemCount()) {
////            if (resultCode == RESULT_OK) {
////                // Make sure the Add request was successful
////                // if add name, insert name in list
////                String name = data.getStringExtra(EXTRA_NAME);
////                int color = data.getIntExtra(EXTRA_COLOR, 0);
////                adapter.addCard(name, color);
////            }
////        } else {
////            // Anything other than adapter.getItemCount() means editing a particular list item
////            // the requestCode is the list item position
////            if (resultCode == RESULT_OK) {
////                // Make sure the Update request was successful
////                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(requestCode);
////                if (data.getExtras().getBoolean(EXTRA_DELETE, false)) {
////                    // if delete user delete
////                    // The user deleted a contact
////                    adapter.deleteCard(viewHolder.itemView, requestCode);
////                } else if (data.getExtras().getBoolean(EXTRA_UPDATE)) {
////                    // if name changed, update user
////                    String name = data.getStringExtra(EXTRA_NAME);
////                    viewHolder.itemView.setVisibility(View.INVISIBLE);
////                    adapter.updateCard(name, requestCode);
////                }
////            }
////        }
////    }
//
//
//    public void doSmoothScroll(int position) {
//        recyclerView.smoothScrollToPosition(position);
//    }
//
//
//
//    private boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }
//
//
//    public void showImage() {
//        Dialog builder = new Dialog(this);
//        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        builder.getWindow().setBackgroundDrawable(
//                new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialogInterface) {
//                //nothing;
//            }
//        });
//
//        ImageView imageView = new ImageView(this);
//
//        Uri imageUri = Uri.parse("android.resource://" + getPackageName() + "/drawable/" + "img");
//
//        imageView.setImageURI(imageUri);
//        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
//        builder.show();
//    }
//
//
//
//
//    private class JsonTask extends AsyncTask<String, String, String> {
//
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            pd = new ProgressDialog(ListaInstituciones.this);
//            pd.setMessage("Cargando Datos");
//            pd.setCancelable(false);
//            pd.show();
//        }
//
//        protected String doInBackground(String... params) {
//
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
//                    buffer.append(line+"\n");
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
//
//            try {
//                 jsonArray = new JSONArray(result);
//
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//
//
//                    JSONObject   instituciones = jsonArray.getJSONObject(i);
//
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
//                    //Log.d("prueba", "nombre:"+nombre+";"+asistencia+";"+cantidad+";"+poblacionAtendida+";"+direccion+";"+telefono+";"+atencion+";"+horarioDeAtencion+";"+correo+";"+servicioBrindado+"\n\n");
//
//
//                    Institution card = new Institution();
//                    card.setId((long) i);
//                    card.setName(nombre);
//                    card.setColorResource(colors[i]);
//                    //card.setAsis(asistencia);
//                  //  card.setCantidadPersonasAtendidas(cantidad);
//                    card.setDireccion(direccion);
////                    card.setPoblacionAtendida(poblacionAtendida);
//
////                    card.setServicioBrindado(servicioBrindado);
//                    card.setTelefono(telefono);
//
//
//                    cardsList.add(card);
//                    Log.d(DEBUG_TAG, "Institution created with iddd " + card.getId() + ", name " + card.getName() + ", color " + card.getColorResource());
//
//
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            if (pd.isShowing()){
//                pd.dismiss();
//            }
//
//        }
//    }
//
//
//
//}
