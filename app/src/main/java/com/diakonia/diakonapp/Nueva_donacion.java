package com.diakonia.diakonapp;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.diakonia.diakonapp.models.Donacion;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Nueva_donacion extends AppCompatActivity {





        private DatabaseReference databaseReference;
        Context contexto;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_nueva_donacion);

            contexto = this;

            Intent intent = getIntent();

            final String beneficiariointent = intent.getExtras().getString("beneficiario");

            TextView beneficiario = (TextView) findViewById(R.id.beneficiarios_id);
            beneficiario.setText(beneficiariointent);



            Spinner spinner = (Spinner) findViewById(R.id.spinner_unidades);
            String[] unidad = {"Gramos", "KiloGramos", "Libras", "Onzas", "Mililitros"};
            spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, unidad));

            Button botonDonar = (Button) findViewById(R.id.buttonDonar);


            botonDonar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseReference = FirebaseDatabase.getInstance().getReference("donaciones");


                    TextInputEditText cantidad = (TextInputEditText) findViewById(R.id.cantidad_id);
                    TextInputEditText pesoPorUnidad = (TextInputEditText)findViewById(R.id.pesoPorUnidad_id);
                    TextInputEditText producto = (TextInputEditText)findViewById(R.id.producto_id);
                    Spinner unidad = (Spinner)findViewById(R.id.spinner_unidades);

                    String id = databaseReference.push().getKey();

                    Donacion nuevadonacion = new Donacion( beneficiariointent,cantidad.getText().toString(),  "email", "fechaDonacion", pesoPorUnidad.getText().toString(),
                            producto.getText().toString(), "4", "uid", unidad.getSelectedItem().toString());

                    databaseReference.child(id).setValue(nuevadonacion);






                }
            });


        }

    }






