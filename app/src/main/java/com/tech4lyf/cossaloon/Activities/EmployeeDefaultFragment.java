package com.tech4lyf.cossaloon.Activities;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tech4lyf.cossaloon.Models.Service;
import com.tech4lyf.cossaloon.R;
import com.tech4lyf.cossaloon.adapters.RecyclerViewAdapterBillItems;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeDefaultFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerViewAdapterBillItems recyclerViewAdapterBillItems;
    ArrayList<Service> serviceList = new ArrayList<>();
    private View root;

    public EmployeeDefaultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_employee_default, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = root.findViewById(R.id.employee_put_bill_recycler_view);
        serviceList.add(new Service("cutting", 120));
        serviceList.add(new Service("shaving", 40));
        serviceList.add(new Service("shaving", 40));
        serviceList.add(new Service("shaving", 40));

        recyclerViewAdapterBillItems = new RecyclerViewAdapterBillItems(serviceList);
        recyclerView.setAdapter(recyclerViewAdapterBillItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));

    }

}
