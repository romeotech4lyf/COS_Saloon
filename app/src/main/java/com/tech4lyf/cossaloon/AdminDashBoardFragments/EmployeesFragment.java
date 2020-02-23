package com.tech4lyf.cossaloon.AdminDashBoardFragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.cossaloon.Activities.AdminHomeActivity;
import com.tech4lyf.cossaloon.Context;
import com.tech4lyf.cossaloon.Listeners;
import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.R;
import com.tech4lyf.cossaloon.adapters.RecyclerViewAdapterEmployees;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeesFragment extends Fragment  {

    RecyclerViewAdapterEmployees recyclerViewAdapterEmployees;
    RecyclerView recyclerView;
    View view;


    public EmployeesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_employees, container, false);
        return view;

    }

    ArrayList<String> titleList = new ArrayList<>();
    ArrayList<String> subTitleList = new ArrayList<>();
    ArrayList<String> idList = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceEmployees;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseApp.initializeApp(this.getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceEmployees = firebaseDatabase.getReference("Employees");

        recyclerView = view.findViewById(R.id.recycler_view_admin_employees);
        ArrayList<Integer> imageList = new ArrayList<>();


        recyclerViewAdapterEmployees = new RecyclerViewAdapterEmployees(idList,titleList,subTitleList,imageList, Context.OBJECT_TYPE.EMPLOYEE);
        recyclerView.setAdapter(recyclerViewAdapterEmployees);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        AdminHomeActivity.level = 1;


        databaseReferenceEmployees.orderByChild("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot employee_ : dataSnapshot.getChildren()){
                    Employee employee = employee_.getValue(Employee.class);
                    if (employee != null) {

                        titleList.add(employee.getName());
                        subTitleList.add(employee.getStoreName());
                        idList.add(employee.getId());
                        recyclerViewAdapterEmployees.notifyDataSetChanged();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }



}
