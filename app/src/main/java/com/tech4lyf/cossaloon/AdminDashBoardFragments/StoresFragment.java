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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.cossaloon.Activities.AdminHomeActivity;
import com.tech4lyf.cossaloon.Context;
import com.tech4lyf.cossaloon.Models.Stores;
import com.tech4lyf.cossaloon.R;
import com.tech4lyf.cossaloon.adapters.RecyclerViewAdapterEmployees;

import java.util.ArrayList;


public class StoresFragment extends Fragment  {

    private View view;
    private RecyclerView recyclerView;



    public StoresFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stores, container, false);
        return view;
    }

    ArrayList<String> titleList = new ArrayList<>();
    ArrayList<String> subTitleList = new ArrayList<>();
    ArrayList<Integer> imageList = new ArrayList<>();
    RecyclerViewAdapterEmployees recyclerViewAdapterEmployees;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view_admin_stores);

               recyclerViewAdapterEmployees = new RecyclerViewAdapterEmployees(new ArrayList<String>(),titleList,subTitleList,imageList, Context.OBJECT_TYPE.STORE);
        recyclerView.setAdapter(recyclerViewAdapterEmployees);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        AdminHomeActivity.level = 1;
        DatabaseReference databaseReferenceStores = FirebaseDatabase.getInstance().getReference().child("Stores");

        databaseReferenceStores.orderByChild("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot store_ : dataSnapshot.getChildren()){
                    Stores stores = store_.getValue(Stores.class);
                    titleList.add(stores.getName());
                    subTitleList.add(stores.getAreaName());
                    recyclerViewAdapterEmployees.notifyDataSetChanged();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
