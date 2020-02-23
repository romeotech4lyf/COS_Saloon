package com.tech4lyf.cossaloon.AdminManageFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tech4lyf.cossaloon.R;


public class RemoveStoreFragment extends Fragment {

    View root;


    public RemoveStoreFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_remove_store, container, false);
        return root;
    }




}
