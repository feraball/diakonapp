package com.diakonia.diakonapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.diakonia.diakonapp.models.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;

    private SignInButton signInButton;

    public static final int SIGN_IN_CODE = 777;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private ProgressBar progressBar;

    private DatabaseReference databaseReference;
    Context contexto;

    Dialog startPopUp;
    ImageView closeStartImg, imgStart;
    Button btnBefore, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        contexto=this;

        startPopUp = new Dialog(this);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton = (SignInButton) findViewById(R.id.signInButton);

        signInButton.setSize(SignInButton.SIZE_WIDE);

        signInButton.setColorScheme(SignInButton.COLOR_DARK);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    goMainScreen();



                    databaseReference = FirebaseDatabase.getInstance().getReference("usuarios");

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (!snapshot.hasChild(user.getUid())) {

                                User nuevoUser = new User(user.getDisplayName(), user.getEmail(), user.getUid(), user.getPhoneNumber());
                                databaseReference.child(user.getUid()).setValue(nuevoUser);
                            }

                            Intent intent = new Intent(contexto, Home.class);
                            contexto.startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }else {
                    ShowStartPopUp();
                }
            }
        };

        progressBar = (ProgressBar) findViewById(R.id.progressBar);


    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("TEEEEEEST", "handleSignInResult: "+ result.isSuccess());
        if (result.isSuccess()) {

            firebaseAuthWithGoogle(result.getSignInAccount());
        } else {
            Toast.makeText(this, "error al logear", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount signInAccount) {

        progressBar.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.GONE);

        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);
                signInButton.setVisibility(View.VISIBLE);

                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "no tiene autorización", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    public  void ShowStartPopUp(){

        startPopUp.setContentView(R.layout.popup_start);
        startPopUp.setCanceledOnTouchOutside(false);
        startPopUp.setCancelable(false);
        closeStartImg = (ImageView) startPopUp.findViewById(R.id.closeStartPop);
        imgStart=(ImageView)startPopUp.findViewById(R.id.startPopImg);
        btnNext=(Button)startPopUp.findViewById(R.id.siguiente);
        btnBefore=(Button)startPopUp.findViewById(R.id.anterior);

        imgStart.setTag("start1");




        closeStartImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPopUp.dismiss();

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String imageName = (String) imgStart.getTag();
                Log.d("prueba", imageName);

                switch (imageName){
                    case"start1":
                        imgStart.setImageResource(R.drawable.start2);
                        imgStart.setTag("start2");
                        break;

                    case"start2":
                        imgStart.setImageResource(R.drawable.start3);
                        imgStart.setTag("start3");
                        break;

                    case"start3":
                        imgStart.setImageResource(R.drawable.start1);
                        imgStart.setTag("start1");
                        break;




                }




            }
        });

        btnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String imageName = (String) imgStart.getTag();

                switch (imageName){
                    case"start1":
                        imgStart.setImageResource(R.drawable.start3);
                        imgStart.setTag("start3");
                        break;

                    case"start2":
                        imgStart.setImageResource(R.drawable.start1);
                        imgStart.setTag("start1");
                        break;

                    case"start3":
                        imgStart.setImageResource(R.drawable.start2);
                        imgStart.setTag("start2");
                        break;




                }




            }
        });

        startPopUp.show();

    }


}
