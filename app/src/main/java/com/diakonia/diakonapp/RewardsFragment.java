package com.diakonia.diakonapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.diakonia.diakonapp.adapters.RewardsAdapter;
import com.diakonia.diakonapp.models.Reward;

import java.util.ArrayList;
import java.util.List;


public class RewardsFragment extends Fragment implements RewardsAdapter.OnCardListener{

    private Context           mContext;
    private RecyclerView      mRecyclerView;
    private RewardsAdapter    mAdapter;
    private OnFragmentInteractionListener mListener;

    public RewardsFragment() {
        // Required empty public constructor
    }


    public static RewardsFragment newInstance() {
        RewardsFragment fragment = new RewardsFragment();

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
        View v = inflater.inflate(R.layout.fragment_rewards, container, false);

        if(getActivity()!=null) mContext = getActivity();

        mRecyclerView = v.findViewById(R.id.recycler_rewards);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));


        List<Reward> rewardList = new ArrayList<>() ;

        rewardList.add(new Reward("Pulcera", "Banco", "03 de Abril 2019", 50,"https://firebasestorage.googleapis.com/v0/b/diakoniapp.appspot.com/o/app_images%2Fpulceras.JPG?alt=media&token=32d26ade-871f-4613-86d5-2cadec9e1e93"));
        rewardList.add(new Reward("Taza", "Banco", "17 de Marzo 2019", 100,"https://firebasestorage.googleapis.com/v0/b/diakoniapp.appspot.com/o/app_images%2Fjarro.JPG?alt=media&token=fc831520-133b-4082-8d71-74bf73a90d21"));


        mAdapter = new RewardsAdapter(mContext, rewardList, this);
        mRecyclerView.setAdapter(mAdapter);



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

    @Override
    public void onCardClick(int position) {

        Toast.makeText(mContext, "onCardClick: "+ position, Toast.LENGTH_SHORT).show();

    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
