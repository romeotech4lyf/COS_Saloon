package com.tech4lyf.cossaloon.AdminStoreDetailsFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.tech4lyf.cossaloon.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AreaDetailBillsFragment extends Fragment {
    private View root;
    private String areaId;

    public AreaDetailBillsFragment() {
        // Required empty public constructor
    }

    public AreaDetailBillsFragment(String areaId) {
this.areaId = areaId   ; }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_area_detail_bills, container, false);
    }

}
