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

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tech4lyf.cossaloon.Activities.AdminHomeActivity;
import com.tech4lyf.cossaloon.Context;
import com.tech4lyf.cossaloon.Models.Store;
import com.tech4lyf.cossaloon.R;
import com.tech4lyf.cossaloon.adapters.RecyclerViewAdapterStores;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoresFragment extends Fragment {

    RecyclerViewAdapterStores recyclerViewAdapterStores;
    RecyclerView recyclerView;
    View view;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceStores;
    private ArrayList<Store> storeList = new ArrayList<>();
    public StoresFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stores, container, false);
        AdminHomeActivity.objectType = Context.OBJECT_TYPE.STORE;
        AdminHomeActivity.level = 1;

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseApp.initializeApp(this.getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceStores = firebaseDatabase.getReference("Stores");

        recyclerView = view.findViewById(R.id.recycler_view_admin_stores);

        recyclerViewAdapterStores = new RecyclerViewAdapterStores(storeList);
        recyclerView.setAdapter(recyclerViewAdapterStores);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        fireBaseListener();


    }

    private void fireBaseListener() {


        databaseReferenceStores.orderByChild("name").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    Store store = dataSnapshot.getValue(Store.class);
                    if (store != null) {
                        synchronized (storeList) {
                            if (!storeList.contains(store)) {
                                storeList.add(store);
                                recyclerViewAdapterStores.setStoreList(storeList);
                                recyclerViewAdapterStores.notifyDataSetChanged();
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
                    Store store = dataSnapshot.getValue(Store.class);
                    if (store != null) {
                        synchronized (storeList) {
                            storeList.remove(store);
                            recyclerViewAdapterStores.setStoreList(storeList);
                            recyclerViewAdapterStores.notifyDataSetChanged();

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


}
