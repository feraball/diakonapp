package com.diakonia.diakonapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DonationHistoryFragment extends Fragment {


    public DonationHistoryFragment() {
        // Required empty public constructor
    }

    public static DonationHistoryFragment newInstance() {
        DonationHistoryFragment fragment = new DonationHistoryFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_profile_tab, container, false);

        TextView texto = v.findViewById(R.id.prueba_profile_tabs_text_id);
        texto.setText("HISTORIAL DONACIONES");

        return v;
    }

}
