package com.tech4lyf.cossaloon.AdminDashBoardFragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tech4lyf.cossaloon.Activities.AdminHomeActivity;
import com.tech4lyf.cossaloon.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeDetailsFragment extends Fragment {

    private View root;
    String id;
    public EmployeeDetailsFragment() {
        // Required empty public constructor
    }
    public EmployeeDetailsFragment(String id){
        this.id = id;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_employee_details, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AdminHomeActivity.level = 2;


    }
}
