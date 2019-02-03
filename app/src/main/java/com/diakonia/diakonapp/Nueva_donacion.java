package com.diakonia.diakonapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.diakonia.diakonapp.models.Donacion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Nueva_donacion extends AppCompatActivity {

    private DatabaseReference databaseReference, databaseReferenceUsers, mCurrentUser;
    Context contexto;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    Dialog thanksDialog;
    ImageView closeDialog, editFoto, camera;

    private ImageButton btnFoto;
    private Button btnDonar;
    private Bitmap donacionBitmap;
    private int puntos;
    private int cantidad_donaciones_previas;
    private int puntosXDonacion = 5;

    final FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
    String idDonacion ;
    private boolean foto = false;






    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_nueva_donacion);

            thanksDialog = new Dialog(this);

            contexto = this;

            if (foto == false){
                dispatchTakePictureIntent();
            }


            mCurrentUser = FirebaseDatabase.getInstance().getReference("usuarios").child(fbUser.getUid());


            mCurrentUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    puntos = Integer.parseInt(snapshot.child("puntos").getValue().toString());
                    cantidad_donaciones_previas = Integer.parseInt(snapshot.child("donaciones").getValue().toString());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



            final Intent intent = getIntent();

//            Log.d("prueba", "El firebaseusert es: " + fbUser.getDisplayName());

            final String beneficiariointent = intent.getExtras().getString("beneficiario");

            TextInputEditText beneficiario = findViewById(R.id.beneficiarios_id);
            beneficiario.setText(beneficiariointent);



            Spinner spinner = findViewById(R.id.spinner_unidades);
//            spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, unidad));

            btnDonar = (Button) findViewById(R.id.buttonDonar);
            btnDonar.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(this, R.drawable.ic_new_donate_icon), null, null, null);
            btnDonar.setCompoundDrawablePadding(10);

            btnFoto = (ImageButton)findViewById(R.id.btnFoto);

            camera = (ImageView)findViewById(R.id.imageIconCamera_id);
            editFoto = (ImageView)findViewById(R.id.editFoto);

            editFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dispatchTakePictureIntent();
                }
            });


            btnFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dispatchTakePictureIntent();
                }
            });


            btnDonar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TextInputEditText cantidad = (TextInputEditText) findViewById(R.id.cantidad_id);
                  //  TextInputEditText pesoPorUnidad = (TextInputEditText)findViewById(R.id.pesoPorUnidad_id);
                    Spinner producto = (Spinner)findViewById(R.id.producto_id);

                    Spinner unidad = (Spinner)findViewById(R.id.spinner_unidades);

//                    Date currentTime = Calendar.getInstance().getTime();
                    String ts = getCurrentTimeStamp();



                    databaseReference = FirebaseDatabase.getInstance().getReference("donaciones");
                    //databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("usuarios/"+ fbUser.getUid()+"/donaciones");

                    if(TextUtils.isEmpty(cantidad.getText()) ||  foto==false) {
                        Toast.makeText(contexto, "Verifique que todos los campos estén llenos y sean los correctos",Toast.LENGTH_SHORT).show();
                    }else {


                        String id = databaseReference.push().getKey();
                        idDonacion = id;

                        String foto = BitMapToString(donacionBitmap);

                        Donacion nuevadonacion = new Donacion( beneficiariointent,cantidad.getText().toString(),  fbUser.getEmail(), ts,
                                producto.getSelectedItem().toString(), Integer.toString(puntosXDonacion), fbUser.getUid(), unidad.getSelectedItem().toString(), foto);

                        databaseReference.child(id).setValue(nuevadonacion);
                        //databaseReferenceUsers.child(id).setValue(id);

                        mCurrentUser.child("puntos").setValue(puntos+puntosXDonacion);
                        mCurrentUser.child("donaciones").setValue(cantidad_donaciones_previas + 1);

                        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                        builder.setMessage(contexto.getString(R.string.shipment)+"."+ "\n \n"+contexto.getString(R.string.thanksForDonate)+"\n\n+"+Integer.toString(puntosXDonacion)+contexto.getString(R.string.user_profile_points_label))
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        ShowThanksPopUp();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();






                        //Toast.makeText(contexto,  contexto.getString(R.string.thanksForDonate) +"\n+"+Integer.toString(puntosXDonacion)+contexto.getString(R.string.user_profile_points_label), Toast.LENGTH_LONG).show();

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
            camera.setVisibility(View.GONE);
            editFoto.setVisibility(View.VISIBLE);

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

    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            String currentDateTime = dateFormat.format(new Date());

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public  void ShowThanksPopUp(){
        thanksDialog.setContentView(R.layout.popup_thks4donate);
        thanksDialog.setCanceledOnTouchOutside(false);
        thanksDialog.setCancelable(false);
        closeDialog = (ImageView) thanksDialog.findViewById(R.id.closepu);

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thanksDialog.dismiss();
                Intent intent = new Intent(contexto, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                contexto.startActivity(intent);
            }
        });

        thanksDialog.show();

    }

    }






