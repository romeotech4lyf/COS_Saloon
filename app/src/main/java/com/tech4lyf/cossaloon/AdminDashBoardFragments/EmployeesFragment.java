package com.tech4lyf.cossaloon.AdminDashBoardFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.cossaloon.Activities.AdminHomeActivity;
import com.tech4lyf.cossaloon.AdminManageFragments.AddEmployeeFragment;
import com.tech4lyf.cossaloon.Context;
import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.R;
import com.tech4lyf.cossaloon.adapters.RecyclerViewAdapterEmployees;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeesFragment extends Fragment implements View.OnClickListener {

    RecyclerViewAdapterEmployees recyclerViewAdapterEmployees;
    RecyclerView recyclerView;
    View view;
    FloatingActionButton addEmployee;


    public EmployeesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_employees, container, false);
        AdminHomeActivity.objectType = Context.OBJECT_TYPE.EMPLOYEE;
        AdminHomeActivity.level = 1;

        return view;

    }


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceEmployees;
    private ArrayList<Employee> employeeList = new ArrayList<>();


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseApp.initializeApp(this.getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceEmployees = firebaseDatabase.getReference("Employees");

        recyclerView = view.findViewById(R.id.recycler_view_admin_employees);
        addEmployee = view.findViewById(R.id.recycler_view_admin_add_employee);

        recyclerViewAdapterEmployees = new RecyclerViewAdapterEmployees(employeeList);
        recyclerView.setAdapter(recyclerViewAdapterEmployees);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));


        addEmployee.setOnClickListener(this);

        fireBaseListener();




    }

    private void fireBaseListener() {


        databaseReferenceEmployees.orderByChild("name").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    Employee employee = dataSnapshot.getValue(Employee.class);
                    if (employee != null) {
                        synchronized (employeeList){
                            if (!employeeList.contains(employee))  {
                                employeeList.add(employee);
                                recyclerViewAdapterEmployees.setEmployeeList(employeeList);
                                recyclerViewAdapterEmployees.notifyDataSetChanged();
                            }


                        }
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Employee employee = dataSnapshot.getValue(Employee.class);
                    if (employee != null) {
                        synchronized (employeeList){
                            employeeList.remove(employee);
                            recyclerViewAdapterEmployees.setEmployeeList(employeeList);
                            recyclerViewAdapterEmployees.notifyDataSetChanged();

                        }
                    }

                }
            }


            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recycler_view_admin_add_employee:
                getChildFragmentManager().beginTransaction().replace(R.id.admin_add_employees_fragment_container, new AddEmployeeFragment()).commit();
                break;
            default:
                break;
        }
    }
}
