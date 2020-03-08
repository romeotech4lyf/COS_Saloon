package com.tech4lyf.cossaloon.AdminDashBoardFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.cossaloon.Activities.AdminHomeActivity;
import com.tech4lyf.cossaloon.Context;
import com.tech4lyf.cossaloon.Listeners;
import com.tech4lyf.cossaloon.Models.Service;
import com.tech4lyf.cossaloon.R;
import com.tech4lyf.cossaloon.adapters.RecyclerViewAdapterServices;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServicesFragment extends Fragment implements Listeners.OnClickServiceListListener {

    private View root;
    private RecyclerView recyclerView;
    private ArrayList<Service> serviceList = new ArrayList<>();
    private DatabaseReference databaseReferenceServices;
    private RecyclerViewAdapterServices recyclerViewAdapterServices;


    public ServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_services, container, false);
        AdminHomeActivity.level = 1;
        AdminHomeActivity.objectType = Context.OBJECT_TYPE.SERVICE;
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        removeDuplicate();
        recyclerView = root.findViewById(R.id.admin_services_recycler_view);
        databaseReferenceServices = FirebaseDatabase.getInstance().getReference().child("Services");
        recyclerViewAdapterServices = new RecyclerViewAdapterServices(serviceList);
        recyclerView.setAdapter(recyclerViewAdapterServices);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fireBaseListener();


        Listeners.setOnClickServiceListListener(this);


    }

    void removeDuplicate() {
        Service prevService = null;
        for (Service service : serviceList) {
            if (prevService != null) {
                if (service.getId().equals(prevService.getId())) {
                    serviceList.remove(service);
                }
            }
            prevService = service;
        }
    }

    private void fireBaseListener() {
       // String key = databaseReferenceServices.push().getKey();

        //   databaseReferenceServices.child(key).setValue(new Service(key, "Shaving", 40));


        databaseReferenceServices.orderByChild("name").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    removeDuplicate();
                    Service service = dataSnapshot.getValue(Service.class);
                    if (service != null) {
                        synchronized (serviceList){
                        if (!serviceList.contains(service))  {
                            serviceList.add(service);
                            recyclerViewAdapterServices.setServiceList(serviceList);
                            recyclerViewAdapterServices.notifyDataSetChanged();
                        }
                        removeDuplicate();


                    }
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                removeDuplicate();
                if (dataSnapshot.exists()) {
                    Service service = dataSnapshot.getValue(Service.class);
                    if (service != null) {
                        synchronized (serviceList){
                        serviceList.remove(service);
                        recyclerViewAdapterServices.setServiceList(serviceList);
                        recyclerViewAdapterServices.notifyDataSetChanged();
                            removeDuplicate();

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

    private void fireBaseChildEventListener(DataSnapshot dataSnapshot) {


    }


    @Override
    public void onClick(final Service service, String itemName, String itemPrice, Context.FLAG flag) {

        switch (flag) {


            case UPDATE:
                serviceList.remove(service);
                recyclerViewAdapterServices.setServiceList(serviceList);
                recyclerViewAdapterServices.notifyDataSetChanged();
                databaseReferenceServices.child(service.getId()).setValue(new Service(service.getId(), itemName, Integer.valueOf(itemPrice)))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(getContext(), "Service Updated Successfully!", Toast.LENGTH_SHORT).show();


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        recyclerViewAdapterServices.setServiceList(serviceList);
                        recyclerViewAdapterServices.notifyDataSetChanged();

                        Toast.makeText(getContext(), "Updating Service Failed!", Toast.LENGTH_SHORT).show();


                    }
                });
                break;

            case CANCEL:
                break;

            case DELETE:
                databaseReferenceServices.child(service.getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        serviceList.remove(service);
                        recyclerViewAdapterServices.setServiceList(serviceList);
                        recyclerViewAdapterServices.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Service Removed Successfully!", Toast.LENGTH_SHORT).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getContext(), "Removing Service Failed!", Toast.LENGTH_SHORT).show();

                    }
                });
                break;

            default:
                break;


        }


    }
}
