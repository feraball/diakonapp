package com.diakonia.diakonapp;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.diakonia.diakonapp.adapters.DonationsAdapter;
import com.diakonia.diakonapp.models.Donacion;
import com.diakonia.diakonapp.viewmodels.DonationsViewModel;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DonationHistoryFragment extends Fragment implements DonationsAdapter.OnCardListener {

     private Context mContext;
        private RecyclerView mRecyclerView;
        private DonationsAdapter    mAdapter;
        private DonationsViewModel mDonationViewModel;
        private DonationHistoryFragment.OnFragmentInteractionListener mListener;
    //
        private List<Donacion> donaciondList = new ArrayList<>() ;
        private DatabaseReference mDatabase;




    public DonationHistoryFragment() {
        // Required empty public constructor
    }

    public static DonationHistoryFragment newInstance() {
        DonationHistoryFragment fragment = new DonationHistoryFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_profile_tab, container, false);

        if(getActivity()!=null) mContext = getActivity();

        mRecyclerView = v.findViewById(R.id.recycler_user_profile_tab_id);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));





        mDonationViewModel = ViewModelProviders.of(this).get(DonationsViewModel.class);


        mDonationViewModel.getDonacion().observe(this, new Observer<List<Donacion>>() {
            @Override
            public void onChanged(@Nullable List<Donacion> donacion) {
                if (donacion != null) {

                    mRecyclerView.setAdapter(new DonationsAdapter(mContext, donacion, DonationHistoryFragment.this));
//
                }
            }
        });


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DonationHistoryFragment.OnFragmentInteractionListener) {
            mListener = (DonationHistoryFragment.OnFragmentInteractionListener) context;
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

    @Override
    public void onCardClick(int position) {

        Toast.makeText(mContext, "onCardClick: "+ position, Toast.LENGTH_SHORT).show();

    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

}
