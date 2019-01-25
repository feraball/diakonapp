package com.diakonia.diakonapp;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diakonia.diakonapp.models.Institution;


public class Perfil_Institucion extends AppCompatActivity {

    private ImageView imgPrincipal, imgTipoAsistencia;

    private TextView txtNombre, txtTipoAsistencia, txtDistancia, txtcantidadPersonas,
            txtDireccion, txtHorario, TxtTelefono, txtCorreo;

    private Button btnLlamar, btnMaps, btnDonar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institucion_perfil);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        imgPrincipal        = findViewById(R.id.imgprincipalinstitucion);
        imgTipoAsistencia   = findViewById(R.id.imgTipoAsistencia);

        txtNombre           = findViewById(R.id.txtNombre);
        txtTipoAsistencia   = findViewById(R.id.txtTipoAsistencia);
        //txtDistancia      = findViewById(R.id.);
        txtcantidadPersonas = findViewById(R.id.txtCantidadPersonas);
        txtDireccion        = findViewById(R.id.txtDireccion);
        txtHorario          = findViewById(R.id.txtHorario);
        TxtTelefono         = findViewById(R.id.txtTelefono);
        txtCorreo           = findViewById(R.id.txtCorreo);

        btnLlamar           = findViewById(R.id.btnLlamar);
        btnMaps             = findViewById(R.id.btnMaps);
        btnDonar            = findViewById(R.id.institution_detail_donation_button_id);


        //Intent intent = getIntent();

        final Institution passedInstitution = (Institution) getIntent().getParcelableExtra("institution_data");


//        String UrlFoto = intent.getExtras().getString("UrlFoto");
//        String tipoAsistencia = intent.getExtras().getString("tipoAsistencia");
//        String nombre = intent.getExtras().getString("nombre");
//        final String  direccion= intent.getExtras().getString("direccion");
//        final String  telefono= intent.getExtras().getString("telefono");
//        String  cantidadPersonas= intent.getExtras().getString("cantidadPersonas");
//        String horario = intent.getExtras().getString("horario");
//        String  correo= intent.getExtras().getString("correo");


        Glide
                .with(this)
                .load(passedInstitution.getUrlFoto())
                .into(imgPrincipal);

        switch (passedInstitution.getAsistencia()) {
            case"COMEDOR":
                imgTipoAsistencia.setImageResource(R.drawable.ic_restaurant_orange);
                break;
        }

        btnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Uri call = Uri.parse("tel:" + passedInstitution.getTelefono());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, call);
                startActivity(callIntent);


            }
        });

        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+passedInstitution.getDireccion());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                startActivity(mapIntent);
//                if (mapIntent.resolveActivity(mContext.getPackageManager()) != null) {
//                    startActivity(mapIntent);
//                }
            }
        });


        btnDonar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String beneficiario = passedInstitution.getNombre();
                Intent donarIntent = new Intent(Perfil_Institucion.this, Nueva_donacion.class);

                donarIntent.putExtra("beneficiario", beneficiario);

                startActivity(donarIntent);


            }
        });




        txtNombre.setText(passedInstitution.getNombre());
        txtTipoAsistencia.setText(passedInstitution.getAsistencia());
        txtcantidadPersonas.setText(passedInstitution.getCantidadPersonasAtendidas());
        txtDireccion.setText(passedInstitution.getDireccion());
        txtHorario.setText(passedInstitution.getHorarioAtencion());
        TxtTelefono.setText(passedInstitution.getTelefono());
        txtCorreo.setText(passedInstitution.getCorreo());


//        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle("");
        }


    }
}
