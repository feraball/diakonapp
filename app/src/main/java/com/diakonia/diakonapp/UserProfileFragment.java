package com.diakonia.diakonapp;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.diakonia.diakonapp.adapters.SectionsPagerAdapter;
import com.diakonia.diakonapp.models.User;
import com.diakonia.diakonapp.viewmodels.CurrentUserViewModel;
import com.diakonia.diakonapp.viewmodels.RewardsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


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

    UserProfileFragment contexto ;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    final FirebaseUser user = firebaseAuth.getCurrentUser();

    private CurrentUserViewModel mCUVM;


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



        //Profile INFO
        avatar          = v.findViewById(R.id.profile_user_picture_id);
        textoNombre     = v.findViewById(R.id.profile_user_name_id);
        textoPuntos     = v.findViewById(R.id.profile_user_points_id);
        textoDonaciones = v.findViewById(R.id.profile_user_donations_id);

        Glide.with(this).load(user.getPhotoUrl())
                        .into(avatar);


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


        mCUVM = ViewModelProviders.of(this).get(CurrentUserViewModel.class);

        mCUVM.getCurrentUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user != null){
                    textoNombre.setText(user.getNombre());
                    textoPuntos.setText(String.valueOf(user.getPuntos()));
                    textoDonaciones.setText(String.valueOf(user.getDonaciones()));
                }
            }
        });

        getActivity().setTitle(R.string.section_title_user_profile);

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


}
