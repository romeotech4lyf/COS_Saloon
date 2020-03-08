package com.tech4lyf.cossaloon.AdminStoreDetailsFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tech4lyf.cossaloon.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreDetailBillsFragment extends Fragment {
    private View root;
    private String storeId;


    public StoreDetailBillsFragment() {
        // Required empty public constructor
    }
public StoreDetailBillsFragment(String storeId) {
        this.storeId = storeId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root =inflater.inflate(R.layout.fragment_blank, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
