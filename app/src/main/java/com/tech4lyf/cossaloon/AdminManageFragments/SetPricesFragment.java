package com.tech4lyf.cossaloon.AdminManageFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.tech4lyf.cossaloon.R;


public class SetPricesFragment extends Fragment {

    View root;


    public SetPricesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_set_prices, container, false);
        return root;
    }

}
