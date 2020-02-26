package com.tech4lyf.cossaloon.AdminEmployeeDetailsFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.R;


public class EmployeeDetailInfoFragment extends Fragment {

    View root;
    TextView name;
    TextView storeName;
    TextView areaName;
    ImageView kyc;
    ImageView dp;
    Employee employee;


    public EmployeeDetailInfoFragment(Employee employee) {
        this.employee = employee;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_employee_detail_info_fragments, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = root.findViewById(R.id.admin_employee_details_info_name);
        storeName = root.findViewById(R.id.admin_employee_details_info_store_name);
        areaName = root.findViewById(R.id.admin_employee_details_info_area_name);
        kyc = root.findViewById(R.id.admin_employee_details_info_kyc);
        dp = root.findViewById(R.id.admin_employee_details_info_dp);


        //
        name.setText(employee.getName());
        storeName.setText(employee.getStoreName());
        areaName.setText(employee.getAreaName());

    }
}
