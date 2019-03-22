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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.diakonia.diakonapp.adapters.RewardsAdapter;
import com.diakonia.diakonapp.models.Reward;
import com.diakonia.diakonapp.viewmodels.RewardsViewModel;

import java.util.List;


public class RewardsFragment extends Fragment implements RewardsAdapter.OnCardListener{

    private Context           mContext;
    private RecyclerView      mRecyclerView;
    private RewardsAdapter    mAdapter;
    private RewardsViewModel  mRewardsViewModel;
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
        View v = inflater.inflate(R.layout.fragment_recycler, container, false);

        if(getActivity()!=null) mContext = getActivity();

        getActivity().setTitle(R.string.section_title_rewards);

        mRecyclerView = v.findViewById(R.id.recycler_view_id);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));


        mRewardsViewModel = ViewModelProviders.of(this).get(RewardsViewModel.class);


        mRewardsViewModel.getRewards().observe(this, new Observer<List<Reward>>() {
            @Override
            public void onChanged(@Nullable List<Reward> rewards) {
                if (rewards != null) {

                    //mAdapter.notifyDataSetChanged();

                    mRecyclerView.setAdapter(new RewardsAdapter(mContext, rewards, RewardsFragment.this));
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

        Toast.makeText(mContext, "Para reclamar premios, acércate al Banco de Alimentos.", Toast.LENGTH_SHORT).show();

    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
