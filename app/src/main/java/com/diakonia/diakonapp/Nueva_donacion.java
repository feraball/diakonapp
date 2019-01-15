package com.diakonia.diakonapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.diakonia.diakonapp.models.Donacion;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;


public class Nueva_donacion extends AppCompatActivity {





        private DatabaseReference databaseReference;
        Context contexto;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageButton btnFoto;
    private Bitmap donacionBitmap;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_nueva_donacion);

            contexto = this;

            final Intent intent = getIntent();

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
                    databaseReference = FirebaseDatabase.getInstance().getReference("donaciones");


                    TextInputEditText cantidad = (TextInputEditText) findViewById(R.id.cantidad_id);
                    TextInputEditText pesoPorUnidad = (TextInputEditText)findViewById(R.id.pesoPorUnidad_id);
                    TextInputEditText producto = (TextInputEditText)findViewById(R.id.producto_id);
                    Spinner unidad = (Spinner)findViewById(R.id.spinner_unidades);

                    String id = databaseReference.push().getKey();

                    String foto = BitMapToString(donacionBitmap);

                    Donacion nuevadonacion = new Donacion( beneficiariointent,cantidad.getText().toString(),  "email", "fechaDonacion", pesoPorUnidad.getText().toString(),
                            producto.getText().toString(), "4", "uid", unidad.getSelectedItem().toString(), foto);

                    databaseReference.child(id).setValue(nuevadonacion);
                    Toast.makeText(contexto,  "Gracias por donar +5 puntos!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(contexto, Home.class);
                    contexto.startActivity(intent);






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






