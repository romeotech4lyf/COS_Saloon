package com.tech4lyf.cossaloon.AdminDetailBillsFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tech4lyf.cossaloon.FormatData;
import com.tech4lyf.cossaloon.Models.Bill;
import com.tech4lyf.cossaloon.R;
import com.tech4lyf.cossaloon.adapters.RecyclerViewAdapterBills;

import java.util.ArrayList;
import java.util.Calendar;



public class StoreDetailBillsFragment extends Fragment {
    private DatabaseReference databaseReferenceBills;
    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewAdapterBills recyclerViewAdapterBills;
    private ArrayList<Bill> billList = new ArrayList<>();
    private String storeId;


    public StoreDetailBillsFragment(String storeId) {
        this.storeId = storeId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_store_detail_bills, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Calendar calendar = Calendar.getInstance();
        String currentYear = FormatData.getCurrentDeviceYear();
        String currentMonth = FormatData.getCurrentDeviceMonth();
        String currentDate = FormatData.getCurrentDeviceDate();
        recyclerView = view.findViewById(R.id.bills_recyclerView);
        recyclerViewAdapterBills = new RecyclerViewAdapterBills(billList);
        recyclerView.setAdapter(recyclerViewAdapterBills);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        databaseReferenceBills = FirebaseDatabase.getInstance().getReference().child("Bills").child(currentYear).child(currentMonth).child(currentDate);


        databaseReferenceBills.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    Bill bill = dataSnapshot.getValue(Bill.class);
                    if(bill!=null){
                        if(bill.getStoreId().equals(storeId)){

                            billList.add(bill);
                            recyclerViewAdapterBills.setBillList(billList);
                            recyclerViewAdapterBills.notifyDataSetChanged();
                        }}}


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
