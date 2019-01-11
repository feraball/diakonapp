package com.diakonia.diakonapp;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class Institucion_Activity extends AppCompatActivity {

    private ImageView imgPrincipal, imgTipoAsistencia;

    private TextView txtNombre, txtTipoAsistencia, txtDistancia, txtcantidadPersonas,
            txtDireccion, txtHorario, TxtTelefono, txtCorreo;

    private ImageButton btnLlamar, btnMaps, btnDonar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        if (Build.VERSION.SDK_INT < 16)
//        {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }
//        else
//        {
//            View decorView = getWindow().getDecorView();
//            // Hide the status bar.
//            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(uiOptions);
//        }
//
//        getSupportActionBar().hide();

        setContentView(R.layout.activity_institucion);

        imgPrincipal = (ImageView) findViewById(R.id.imgprincipalinstitucion);
        imgTipoAsistencia = (ImageView) findViewById(R.id.imgTipoAsistencia);

        txtNombre = (TextView)findViewById(R.id.txtNombre);
        txtTipoAsistencia = (TextView)findViewById(R.id.txtTipoAsistencia);
        //txtDistancia = (TextView)findViewById(R.id.);
        txtcantidadPersonas = (TextView)findViewById(R.id.txtCantidadPersonas);
        txtDireccion = (TextView)findViewById(R.id.txtDireccion);
        txtHorario = (TextView)findViewById(R.id.txtHorario);
        TxtTelefono = (TextView)findViewById(R.id.txtTelefono);
        txtCorreo = (TextView)findViewById(R.id.txtCorreo);

        btnLlamar =(ImageButton)findViewById(R.id.btnLlamar);
        btnMaps =(ImageButton)findViewById(R.id.btnMaps);
        btnDonar =(ImageButton)findViewById(R.id.btnDonar);







        Intent intent = getIntent();

        String UrlFoto = intent.getExtras().getString("UrlFoto");
        String tipoAsistencia = intent.getExtras().getString("tipoAsistencia");
        String nombre = intent.getExtras().getString("nombre");
        final String  direccion= intent.getExtras().getString("direccion");
        final String  telefono= intent.getExtras().getString("telefono");
        String  cantidadPersonas= intent.getExtras().getString("cantidadPersonas");
        String horario = intent.getExtras().getString("horario");
        String  correo= intent.getExtras().getString("correo");


        Glide
                .with(this)
                .load(UrlFoto)
                .into(imgPrincipal);

        switch (tipoAsistencia) {

            case"COMEDOR":
                imgTipoAsistencia.setImageResource(R.drawable.ic_restaurant_orange);


                break;
        }

        btnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Uri call = Uri.parse("tel:"+telefono);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, call);
                startActivity(callIntent);


            }
        });

        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+direccion);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                startActivity(mapIntent);
//                if (mapIntent.resolveActivity(mContext.getPackageManager()) != null) {
//                    startActivity(mapIntent);
//                }
            }
        });


        txtNombre.setText(nombre);

        txtTipoAsistencia.setText(tipoAsistencia);

        txtcantidadPersonas.setText(cantidadPersonas);
        txtDireccion.setText(direccion);
        txtHorario.setText(horario);
        TxtTelefono.setText(telefono);
        txtCorreo.setText(correo);










    }
}
