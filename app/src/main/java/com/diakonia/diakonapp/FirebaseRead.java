package com.diakonia.diakonapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.introtoandroid.samplematerial.Instituciones;
import com.introtoandroid.samplematerial.ListaInstituciones;
import com.introtoandroid.samplematerial.R;

import java.util.ArrayList;
import java.util.List;

public class FirebaseRead extends AppCompatActivity {
    private DatabaseReference databaseReference;
    List<Instituciones> listaInstituciones;


// ...


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_read);

        databaseReference = FirebaseDatabase.getInstance().getReference("instituciones");

        listaInstituciones = new ArrayList<>();


    }


    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot institucionesSnapshot: dataSnapshot.getChildren()){

                   Instituciones  institucion = institucionesSnapshot.getValue(Instituciones.class);
                   // Log.d("prueba", String.valueOf(institucionesSnapshot.getValue()));
                    String a = institucion.getName();
                    Log.d("prueba", a);
                    listaInstituciones.add(institucion);


                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

