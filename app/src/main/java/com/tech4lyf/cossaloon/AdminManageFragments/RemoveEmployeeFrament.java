package com.tech4lyf.cossaloon.AdminManageFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tech4lyf.cossaloon.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RemoveEmployeeFrament extends Fragment {
View root;

    public RemoveEmployeeFrament() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_remove_employee_frament, container, false);
        return  root;
    }

}
