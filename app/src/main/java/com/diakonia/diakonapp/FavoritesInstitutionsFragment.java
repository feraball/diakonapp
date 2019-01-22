package com.diakonia.diakonapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FavoritesInstitutionsFragment extends Fragment {

    public FavoritesInstitutionsFragment() {
        // Required empty public constructor
    }


    public static FavoritesInstitutionsFragment newInstance() {
        FavoritesInstitutionsFragment fragment = new FavoritesInstitutionsFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recycler, container, false);

        //TextView texto = v.findViewById(R.id.prueba_profile_tabs_text_id);
        //texto.setText("FAVORITOS");

        return v;
    }
}
