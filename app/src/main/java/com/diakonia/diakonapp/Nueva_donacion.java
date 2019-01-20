package com.diakonia.diakonapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.diakonia.diakonapp.models.Donacion;
import com.diakonia.diakonapp.models.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Nueva_donacion extends AppCompatActivity {





        private DatabaseReference databaseReference, databaseReferenceUsers, mDatabase;
        Context contexto;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageButton btnFoto;
    private Bitmap donacionBitmap;
    private  int puntos;
    private  int puntosXDonacion =5;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    final FirebaseUser user = firebaseAuth.getCurrentUser();
    String idDonacion ;
    private  boolean foto =false;






    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_nueva_donacion);

            contexto = this;

        mDatabase= FirebaseDatabase.getInstance().getReference("usuarios").child(user.getUid());


        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                puntos= Integer.parseInt(snapshot.child("puntos").getValue().toString());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



            final Intent intent = getIntent();

        Log.d("prueba", "El firebaseusert es: " +user.getDisplayName());

            final String beneficiariointent = intent.getExtras().getString("beneficiario");

            TextInputEditText beneficiario = findViewById(R.id.beneficiarios_id);
            beneficiario.setText(beneficiariointent);



            Spinner spinner = (Spinner) findViewById(R.id.spinner_unidades);
//            String[] unidad = {"Gramos", "KiloGramos", "Libras", "Onzas", "Mililitros"};
//            spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, unidad));

            Button botonDonar = (Button) findViewById(R.id.buttonDonar);
             btnFoto = (ImageButton)findViewById(R.id.btnFoto);







            btnFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dispatchTakePictureIntent();






                }
            });


            botonDonar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TextInputEditText cantidad = (TextInputEditText) findViewById(R.id.cantidad_id);
                    TextInputEditText pesoPorUnidad = (TextInputEditText)findViewById(R.id.pesoPorUnidad_id);
                    TextInputEditText producto = (TextInputEditText)findViewById(R.id.producto_id);
                    Spinner unidad = (Spinner)findViewById(R.id.spinner_unidades);

                    Date currentTime = Calendar.getInstance().getTime();
                    String ts = currentTime.toString();
                    databaseReference = FirebaseDatabase.getInstance().getReference("donaciones");
                    databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("usuarios/"+user.getUid()+"/donaciones");

                    if(cantidad.getText().equals("") || pesoPorUnidad.getText().equals("") || producto.getText().equals("") || foto==false) {
                        Toast.makeText(contexto, "Verifique que todos los campos estén llenos y sean los correctos",Toast.LENGTH_SHORT).show();
                    }else {


                        String id = databaseReference.push().getKey();
                        idDonacion=id;

                        String foto = BitMapToString(donacionBitmap);

                        Donacion nuevadonacion = new Donacion( beneficiariointent,cantidad.getText().toString(),  user.getEmail(), ts, pesoPorUnidad.getText().toString(),
                                producto.getText().toString(), Integer.toString(puntosXDonacion), user.getUid(), unidad.getSelectedItem().toString(), foto);

                        databaseReference.child(id).setValue(nuevadonacion);
                        databaseReferenceUsers.child(id).setValue(id);
                        mDatabase.child("puntos").setValue(puntos+puntosXDonacion);











                        Toast.makeText(contexto,  "Gracias por donar \n+"+Integer.toString(puntosXDonacion)+" puntos!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(contexto, Home.class);
                        contexto.startActivity(intent);

                    }




















                }
            });


        }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            donacionBitmap = (Bitmap) extras.get("data");

            btnFoto.setImageBitmap(donacionBitmap);
            foto=true;
        }
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    }






