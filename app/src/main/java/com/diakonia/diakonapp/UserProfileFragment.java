package com.diakonia.diakonapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.diakonia.diakonapp.models.Institution;
import com.diakonia.diakonapp.models.Reward;
import com.diakonia.diakonapp.models.Usuario;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;

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


public class UserProfileFragment extends Fragment {

    ProgressDialog pd;
    private JSONArray jsonArray;
    UserProfileFragment contexto ;
    private  View vista;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    final FirebaseUser user = firebaseAuth.getCurrentUser();
    private DatabaseReference mDatabase, dDatabase;
    private ArrayList<Usuario> dataSet = new ArrayList<>();

    String url = "https://diakoniapp.firebaseio.com/usuarios/"+user.getUid()+".json";

    private OnFragmentInteractionListener mListener;


    public UserProfileFragment() {

        // Required empty public constructor
    }


    public static UserProfileFragment newInstance() {
        UserProfileFragment fragment = new UserProfileFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contexto=this;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);

        //Toolbar
        Toolbar toolbar = v.findViewById(R.id.profile_user_toolbar_id);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).setTitle("Profile");

        ImageView avatar = (ImageView) v.findViewById(R.id.profile_user_picture_id);

        Glide.with(this).load(user.getPhotoUrl()).into(avatar);


        // Inflate the layout for this fragment


        vista=v;


            loadFromFirebase();






        //new JsonTask().execute(url);



        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void loadFromFirebase() {


        final TextView textoNombre = (TextView)vista.findViewById(R.id.profile_user_name_id);
        final TextView textoPuntos=(TextView)vista.findViewById(R.id.profile_user_points_id);
        final TextView textoDonaciones = (TextView)vista.findViewById(R.id.profile_user_donations_id);

        mDatabase= FirebaseDatabase.getInstance().getReference("usuarios").child(user.getUid());
        mDatabase.keepSynced(true);

        dDatabase = FirebaseDatabase.getInstance().getReference("donaciones");





        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Log.d("prueba", snapshot.child("nombre").getValue().toString());

                textoNombre.setText(snapshot.child("nombre").getValue().toString());
                textoPuntos.setText(snapshot.child("puntos").getValue().toString());
                textoDonaciones.setText(Long.toString(snapshot.child("donaciones").getChildrenCount()));


                for (final DataSnapshot snap : snapshot.child("donaciones").getChildren()){

                    dDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {

                            Log.d("prueba", dataSnapshot1.child(snap.getValue().toString()).child("beneficiario").getValue().toString());




                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




                }










            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


//    private class JsonTask extends AsyncTask<String, String, String> {
//
//        protected void onPreExecute() {
//            super.onPreExecute();
////
////            pd = new ProgressDialog(contexto);
////            pd.setMessage("Cargando Datos");
////            pd.setCancelable(false);
////            pd.show();
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
//            Log.d("prueba", result);
//
//
//
//
//
//            if(result!=null){
//
//                try {
//                    JSONObject user = new JSONObject(result);
//                    String nombre = null;
//                    try {
//                        nombre = user.getString("nombre");
//                        TextView textoNombre = (TextView)vista.findViewById(R.id.profile_user_name_id);
//                        textoNombre.setText(nombre);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
////                if (pd.isShowing()){
////                    pd.dismiss();
////                }
//
//            }
//            else
//            {
//                Log.d("prueba", "No hay conección");
//            }
//
//
//
//
//        }
//    }
}
