package com.diakonia.diakonapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diakonia.diakonapp.adapters.DonationsAdapter;
import com.diakonia.diakonapp.adapters.SectionsPagerAdapter;
import com.diakonia.diakonapp.models.Donacion;
import com.diakonia.diakonapp.models.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import java.util.ArrayList;


public class UserProfileFragment extends Fragment {


    private Context                 mContext;
    private TabLayout               mTabLayout;
    private ViewPager               mViewPager;
    private SectionsPagerAdapter    mSPAdapter;


    //Views
    ImageView avatar;

    TextView textoNombre;
    TextView textoPuntos;
    TextView textoDonaciones;

    ProgressDialog pd;
    private JSONArray jsonArray;
    UserProfileFragment contexto ;
    private  View vista;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    final FirebaseUser user = firebaseAuth.getCurrentUser();
    private DatabaseReference mDatabase, dDatabase;
    private ArrayList<Usuario> dataSet = new ArrayList<>();

    private RecyclerView      mRecyclerView;
    private DonationsAdapter mAdapter;

    private ArrayList<Donacion> lstDonacion = new ArrayList<>();

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
        if(getActivity()!=null) mContext = getActivity();



        //Toolbar
//        Toolbar toolbar = v.findViewById(R.id.profile_user_toolbar_id);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        getActivity().setTitle(getResources().getString(R.string.section_title_user_profile));

        Log.d("titulo", "activity title: "+ getActivity().getTitle());


        //Profile INFO
        avatar          = v.findViewById(R.id.profile_user_picture_id);
        textoNombre     = v.findViewById(R.id.profile_user_name_id);
        textoPuntos     = v.findViewById(R.id.profile_user_points_id);
        textoDonaciones = v.findViewById(R.id.profile_user_donations_id);

        Glide.with(this).load(user.getPhotoUrl()).into(avatar);


        //TABS
        mTabLayout    = v.findViewById(R.id.user_profile_tablayout_id);
        mViewPager    = v.findViewById(R.id.user_profile_tab_fragment_container_id);
        mSPAdapter    = new SectionsPagerAdapter(getChildFragmentManager());

        //ADDING FRAGMENTS
        mSPAdapter.AddFragment(new DonationHistoryFragment(), getResources().getString(R.string.user_profile_donations_label));
        mSPAdapter.AddFragment(new InstitutionsFragment(),  getResources().getString(R.string.user_profile_tab_favorites_label));

        //Adapter Set-up
        mViewPager.setAdapter(mSPAdapter);
        mTabLayout.setupWithViewPager(mViewPager);



        // Inflate the layout for this fragment


//        vista=v;
//
//        mRecyclerView = v.findViewById(R.id.recycler_donations);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//
//
            loadFromFirebase();
//
//            mAdapter=new DonationsAdapter(mContext, lstDonacion);
//            mRecyclerView.setAdapter(mAdapter);

///oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooojo

   //ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooojo
//        myrv.setLayoutManager(new GridLayoutManager(mContext,1));








        //new JsonTask().execute(url);



        return v;
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


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void loadFromFirebase() {


        mDatabase= FirebaseDatabase.getInstance().getReference("usuarios").child(user.getUid());
        mDatabase.keepSynced(true);

        dDatabase = FirebaseDatabase.getInstance().getReference("donaciones");
        dDatabase.keepSynced(true);





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
                            //String producto, String beneficiario, String fechaDonacion, String puntos, String foto)
//                            lstDonacion.add(new Donacion(dataSnapshot1.child(snap.getValue().toString()).child("producto").getValue().toString(),
//                                    dataSnapshot1.child(snap.getValue().toString()).child("beneficiario").getValue().toString(),
//                                    dataSnapshot1.child(snap.getValue().toString()).child("fechaDonacion").getValue().toString(),
//                                    dataSnapshot1.child(snap.getValue().toString()).child("puntos").getValue().toString(),
//                                    dataSnapshot1.child(snap.getValue().toString()).child("foto").getValue().toString()));


//                            Log.d("prueba producto", dataSnapshot1.child(snap.getValue().toString()).child("producto").getValue().toString());
//                            Log.d("prueba beneficiario", dataSnapshot1.child(snap.getValue().toString()).child("beneficiario").getValue().toString());
//                            Log.d("prueba fecha", dataSnapshot1.child(snap.getValue().toString()).child("fechaDonacion").getValue().toString());
//                            Log.d("prueba puntos", dataSnapshot1.child(snap.getValue().toString()).child("puntos").getValue().toString());
//                            Log.d("prueba puntos", dataSnapshot1.child(snap.getValue().toString()).child("foto").getValue().toString());

                           // Bitmap fotoDecodificada = StringToBitMap(dataSnapshot1.child(snap.getValue().toString()).child("foto").getValue().toString());
                         //   ImageView a = (ImageView)vista.findViewById(R.id.j);
                           // a.setImageBitmap(fotoDecodificada);



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
}
