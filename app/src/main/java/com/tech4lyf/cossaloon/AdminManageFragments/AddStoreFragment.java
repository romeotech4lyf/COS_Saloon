package com.tech4lyf.cossaloon.AdminManageFragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.tech4lyf.cossaloon.Activities.AdminHomeActivity;
import com.tech4lyf.cossaloon.ChangeOfStyle;
import com.tech4lyf.cossaloon.FormatData;
import com.tech4lyf.cossaloon.Models.Area;
import com.tech4lyf.cossaloon.Models.Store;
import com.tech4lyf.cossaloon.R;

import java.util.ArrayList;

public class AddStoreFragment extends Fragment implements View.OnClickListener {


    private View root;
    private String selectedAreaName = null;
    private String enteredName = null;
    private CardView add;
    private CardView cancel;

    private EditText enterName;

    private Spinner spinnerSelectArea;
    private ArrayAdapter arrayAdapterAreas;
    private ArrayList<String> areaNameList = new ArrayList<>();
    private ArrayList<String> areaIdList = new ArrayList<>();
    private DatabaseReference databaseReferenceStores;
    private DatabaseReference databaseReferenceAreas;
    private String key;


    public AddStoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_add_stores, container, false);
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReferenceStores = firebaseDatabase.getReference("Stores");
        databaseReferenceAreas = firebaseDatabase.getReference("Areas");


        areaIdList.add(0, "Select Area");
        areaNameList.add(0, "Select Area");

        fireBaseAreaListener();

        //
        enterName = root.findViewById(R.id.admin_add_store_enter_name);

        spinnerSelectArea = root.findViewById(R.id.admin_add_store_select_area);
        add = root.findViewById(R.id.admin_add_store_ok);
        cancel = root.findViewById(R.id.admin_add_store_cancel);


        AdminHomeActivity.level = 2;


        //
        fillSpinners();

        //
        add.setOnClickListener(this);
        cancel.setOnClickListener(this);



    }

    void fireBaseAreaListener() {

        Log.d("here", "here");

        databaseReferenceAreas.orderByChild("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot area_ : dataSnapshot.getChildren()) {
                        Area area = area_.getValue(Area.class);
                        if (area != null) {
                            String areaId = area.getId();
                            String areaName = area.getName();
                            if (areaId != null && areaName != null) {
                                if (!areaIdList.contains(areaId)) {
                                    areaIdList.add(areaId);
                                    areaNameList.add(areaName);
                                    arrayAdapterAreas.notifyDataSetChanged();
                                }
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

    private void fillSpinners() {


        //
        spinnerSelectArea = root.findViewById(R.id.admin_add_store_select_area);
        arrayAdapterAreas = new ArrayAdapter(ChangeOfStyle.getContext(), android.R.layout.simple_spinner_item, areaNameList);
        arrayAdapterAreas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelectArea.setAdapter(arrayAdapterAreas);
        spinnerSelectArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedAreaName = parent.getItemAtPosition(position).toString();
                Log.d("selected Area", selectedAreaName);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                selectedAreaName = parent.getItemAtPosition(0).toString();
                Log.d("selected Area", selectedAreaName);

            }
        });


    }

    private void getInput() {
        enteredName = enterName.getText().toString();


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.admin_add_store_ok:
                getInput();
                key = databaseReferenceStores.push().getKey();
                if (!(enteredName.length() < 1) && !selectedAreaName.equals("Select Area")) {

                    databaseReferenceStores.child(key).
                            setValue(new Store(key, enteredName,  areaIdList.get(areaNameList.indexOf(selectedAreaName)), selectedAreaName,
                                    FormatData.getCurrentDeviceFullDate())).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "Store Added Successfully", Toast.LENGTH_SHORT).show();
                            AddStoreFragment.this.getParentFragmentManager().beginTransaction().remove(AddStoreFragment.this).commit();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getContext(), "Task Failed!!", Toast.LENGTH_SHORT).show();

                        }
                    });

                } else {
                    Toast.makeText(this.getContext(), "Enter Fields Properly!!", Toast.LENGTH_SHORT).show();

                }
                break;


            case R.id.admin_add_store_cancel:
                AdminHomeActivity.level = 1;
                getParentFragmentManager().beginTransaction().remove(AddStoreFragment.this).commit();

        }
    }


}
