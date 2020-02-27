package com.tech4lyf.cossaloon.AdminEmployeeDetailsFragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.cossaloon.Models.Bill;
import com.tech4lyf.cossaloon.R;
import com.tech4lyf.cossaloon.adapters.RecyclerViewAdapterBills;

import java.util.ArrayList;
import java.util.Calendar;


public class EmployeeDetailBillsFragment extends Fragment {
    DatabaseReference databaseReferenceBills;
    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewAdapterBills recyclerViewAdapterBills;
    private ArrayList<Bill> billList = new ArrayList<>();
    private String employeeId;


    public EmployeeDetailBillsFragment(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_employee_detail_bills, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH));
        String date = String.valueOf(calendar.get(Calendar.DATE));
        recyclerView = view.findViewById(R.id.bills_recyclerView);
        recyclerViewAdapterBills = new RecyclerViewAdapterBills(billList);
        recyclerView.setAdapter(recyclerViewAdapterBills);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        databaseReferenceBills = FirebaseDatabase.getInstance().getReference("Incomes").child(year).child(month).child(date);


        databaseReferenceBills.orderByChild("employeeName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot bill_ : dataSnapshot.getChildren()) {
                        Bill bill = bill_.getValue(Bill.class);
                        if (bill != null) {
                            Log.d(employeeId, bill.getEmployeeId());
                            if (employeeId.equals(bill.getEmployeeId())) {
                                billList.add(bill);
                                recyclerViewAdapterBills.setBillList(billList);
                                recyclerViewAdapterBills.notifyDataSetChanged();
                            }
                        }


                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}