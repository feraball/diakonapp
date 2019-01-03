package com.introtoandroid.samplematerial;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class ListaInstituciones extends AppCompatActivity {
    private static final String DEBUG_TAG = "AppCompatActivity";

    public static final String EXTRA_UPDATE = "update";
    public static final String EXTRA_DELETE = "delete";
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_COLOR = "color";
    public static final String EXTRA_INITIAL = "initial";

    public static final String TRANSITION_FAB = "fab_transition";
    public static final String TRANSITION_INITIAL = "initial_transition";
    public static final String TRANSITION_NAME = "name_transition";
    public static final String TRANSITION_DELETE_BUTTON = "delete_button_transition";




    private RecyclerView recyclerView;
    private ReciclerAdapter adapter;
    private ArrayList<Card> cardsList = new ArrayList<>();
    private int[] colors;
    private String[] names;

    private String[] asistencia;
    private String [] cantidadPersonasAtendidas;
    private String[] poblacionAtendida;
    private String[] sector;
    private String[] zona;
    private String[] direccion;
    private String[] telefono;
    private String[] correo;
    private String[] servicioBrindado;
    private int idActual;

    private String url = "http://diakonia.esy.es/diakonia.json";

    private RequestQueue mQueue;

    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_instituciones);



        names = getResources().getStringArray(R.array.instituciones);
        colors = getResources().getIntArray(R.array.initial_colors);

        mQueue = Volley.newRequestQueue(this);
        showImage();










        asistencia= getResources().getStringArray(R.array.asistencia);
        cantidadPersonasAtendidas= getResources().getStringArray(R.array.cantidadPersonasAtendidas);
        poblacionAtendida= getResources().getStringArray(R.array.poblacionAtendida);
        sector= getResources().getStringArray(R.array.sector);
        zona= getResources().getStringArray(R.array.zona);
        direccion= getResources().getStringArray(R.array.direccion);
        telefono= getResources().getStringArray(R.array.telefono);
        correo= getResources().getStringArray(R.array.correo);
        servicioBrindado= getResources().getStringArray(R.array.servicioBrindado);



        if(isNetworkAvailable()) {
        Log.d("prueba", "está conectado"); jsonParse();initCards();
        } else { Log.d("prueba","no está conectado") ; initCards();}



        if (adapter == null) {
            adapter = new ReciclerAdapter(this, cardsList);
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Pair<View, String> pair = Pair.create(v.findViewById(R.id.fab), TRANSITION_FAB);
//
//                ActivityOptionsCompat options;
//                Activity act = ListaInstituciones.this;
//                options = ActivityOptionsCompat.makeSceneTransitionAnimation(act, pair);
//
//                Intent transitionIntent = new Intent(act, TransitionAddActivity.class);
//                act.startActivityForResult(transitionIntent, adapter.getItemCount(), options.toBundle());
//            }
//        });






    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        idActual=requestCode;

        Log.d(DEBUG_TAG, "requestCode is " + requestCode);
        // if adapter.getItemCount() is request code, that means we are adding a new position
        // anything less than adapter.getItemCount() means we are editing a particular position
        if (requestCode == adapter.getItemCount()) {
            if (resultCode == RESULT_OK) {
                // Make sure the Add request was successful
                // if add name, insert name in list
                String name = data.getStringExtra(EXTRA_NAME);
                int color = data.getIntExtra(EXTRA_COLOR, 0);
                adapter.addCard(name, color);
            }
        } else {
            // Anything other than adapter.getItemCount() means editing a particular list item
            // the requestCode is the list item position
            if (resultCode == RESULT_OK) {
                // Make sure the Update request was successful
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(requestCode);
                if (data.getExtras().getBoolean(EXTRA_DELETE, false)) {
                    // if delete user delete
                    // The user deleted a contact
                    adapter.deleteCard(viewHolder.itemView, requestCode);
                } else if (data.getExtras().getBoolean(EXTRA_UPDATE)) {
                    // if name changed, update user
                    String name = data.getStringExtra(EXTRA_NAME);
                    viewHolder.itemView.setVisibility(View.INVISIBLE);
                    adapter.updateCard(name, requestCode);
                }
            }
        }
    }


    public void doSmoothScroll(int position) {
        recyclerView.smoothScrollToPosition(position);
    }

    private void initCards() {
        String[] agencias = getResources().getStringArray(R.array.instituciones);
        Log.d(DEBUG_TAG, "agenias"+agencias.length);
        for (int i = 0; i < agencias.length ; i++) {
            Card card = new Card();
            card.setId((long) i);
            card.setName(names[i]);
            card.setColorResource(colors[i]);
            card.setAsis(asistencia[i]);
            card.setCantidadPersonasAtendidas(cantidadPersonasAtendidas[i]);
            card.setCorreo(correo[i]);
            card.setDireccion(direccion[i]);
            card.setPoblacionAtendida(poblacionAtendida[i]);
            card.setSector(sector[i]);
            card.setServicioBrindado(servicioBrindado[i]);
            card.setTelefono(telefono[i]);
            Log.d(DEBUG_TAG, "Card created with id " + card.getId() + ", name " + card.getName() + ", color " + card.getColorResource());
            cardsList.add(card);
        }


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void jsonParse() {



        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jsonArray = response.getJSONArray("instituciones");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject instituciones = null;
                                try {
                                    instituciones = jsonArray.getJSONObject(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

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


                                Log.d("prueba", "nombre:"+nombre+";"+asistencia+";"+cantidad+";"+poblacionAtendida+";"+direccion+";"+telefono+";"+atencion+";"+horarioDeAtencion+";"+correo+";"+servicioBrindado+"\n\n");


                                Card card = new Card();
                                card.setId((long) i);
                                card.setName(nombre);
                                card.setColorResource(colors[i]);
                                card.setAsis(asistencia);
                                card.setCantidadPersonasAtendidas(cantidad);
                                card.setCorreo(correo);
                                card.setDireccion(direccion);
                                card.setPoblacionAtendida(poblacionAtendida);

                                card.setServicioBrindado(servicioBrindado);
                                card.setTelefono(telefono);


                                cardsList.add(card);
                                Log.d(DEBUG_TAG, "Card created with iddd " + card.getId() + ", name " + card.getName() + ", color " + card.getColorResource());


                            }



                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);

    }
    public void showImage() {
        Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(this);

        Uri imageUri = Uri.parse("android.resource://" + getPackageName() + "/drawable/" + "img");

        imageView.setImageURI(imageUri);
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }



}
