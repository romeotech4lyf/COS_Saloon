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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.cossaloon.ChangeOfStyle;
import com.tech4lyf.cossaloon.Models.Area;
import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.Models.Store;
import com.tech4lyf.cossaloon.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditEmployeeFragment extends Fragment implements View.OnClickListener {

    View root;
    String selectedStoreName = null;
    String selectedAreaName = null;
    String enteredName = null;
    String enteredPhoneNumber = null;
    String enteredPassword = null;
    CardView add;
    CardView cancel;
    private EditText enterName;
    private EditText enterPhoneNumber;
    private EditText enterPassword;
    private Spinner spinnerSelectStore;
    private Spinner spinnerSelectArea;
    private ArrayAdapter arrayAdapterStores;
    private ArrayAdapter arrayAdapterAreas;
    private ArrayList<String> areaNameList = new ArrayList<>();
    private ArrayList<String> areaIdList = new ArrayList<>();
    private ArrayList<String> storeNameList = new ArrayList<>();
    private ArrayList<String> storeIdList = new ArrayList<>();
    private DatabaseReference databaseReferenceStores;
    private DatabaseReference databaseReferenceAreas;
    private DatabaseReference databaseReferenceEmployees;
    private String key;
    private Employee employee;


    public EditEmployeeFragment() {
        // Required empty public constructor
    }

    public EditEmployeeFragment(Employee employee) {
        this.employee = employee;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.admin_edit_employee_ok:
                getInput();

                if (!(enteredPhoneNumber.length() < 10) && !(enteredPassword.length() < 8) && !(enteredName.length() < 1)
                        && !selectedAreaName.equals("Select Area") && !selectedStoreName.equals("Select Area First") && !selectedStoreName.equals("Select Store")) {

                    databaseReferenceEmployees.child(employee.getId()).child("name").setValue(enteredName);
                    databaseReferenceEmployees.child(employee.getId()).child("password").setValue(enteredPassword);
                    databaseReferenceEmployees.child(employee.getId()).child("storeId").setValue(storeIdList.get(storeNameList.indexOf(selectedStoreName)));
                    databaseReferenceEmployees.child(employee.getId()).child("storeName").setValue(selectedStoreName);
                    databaseReferenceEmployees.child(employee.getId()).child("areaId").setValue(areaIdList.get(areaNameList.indexOf(selectedAreaName)));
                    databaseReferenceEmployees.child(employee.getId()).child("areaName").setValue(selectedAreaName);
                    databaseReferenceEmployees.child(employee.getId()).child("phoneNumber").setValue(enteredPhoneNumber);

                    Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
                    if (!getParentFragmentManager().isDestroyed())
                        getParentFragmentManager().beginTransaction().remove(EditEmployeeFragment.this).commit();

                } else {
                    Toast.makeText(this.getContext(), "Enter Fields Properly!!", Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.admin_edit_employee_cancel:
                if (!getParentFragmentManager().isDestroyed())
                    getParentFragmentManager().beginTransaction().remove(EditEmployeeFragment.this).commit();

            default:
                break;
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_edit_employee, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        areaIdList.add(0, "Select Area");
        areaNameList.add(0, "Select Area");
        storeNameList.add(0, "Select Area First");
        storeIdList.add(0, "Select Area First");


        //
        enterName = root.findViewById(R.id.admin_edit_employee_enter_name);
        enterPassword = root.findViewById(R.id.admin_edit_employee_enter_password);
        enterPhoneNumber = root.findViewById(R.id.admin_edit_employee_enter_phone_number);
        spinnerSelectArea = root.findViewById(R.id.admin_edit_employee_select_area);
        spinnerSelectStore = root.findViewById(R.id.admin_edit_employee_select_store);
        add = root.findViewById(R.id.admin_edit_employee_ok);
        cancel = root.findViewById(R.id.admin_edit_employee_cancel);


        //
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceStores = firebaseDatabase.getReference("Stores");
        databaseReferenceAreas = firebaseDatabase.getReference("Areas");
        databaseReferenceEmployees = firebaseDatabase.getReference("Employees");


        //
        fireBaseAreaListener();


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
        arrayAdapterAreas = new ArrayAdapter(ChangeOfStyle.getContext(), android.R.layout.simple_spinner_item, areaNameList);
        arrayAdapterAreas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelectArea.setAdapter(arrayAdapterAreas);
        spinnerSelectArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedAreaName = parent.getItemAtPosition(position).toString();
                fireBaseStoreListener();
                Log.d("selected Area", selectedAreaName);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                selectedAreaName = parent.getItemAtPosition(0).toString();
                Log.d("selected Area", selectedAreaName);

            }
        });

        arrayAdapterStores = new ArrayAdapter(ChangeOfStyle.getContext(), android.R.layout.simple_spinner_item, storeNameList);
        arrayAdapterStores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelectStore.setAdapter(arrayAdapterStores);
        spinnerSelectStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedStoreName = parent.getItemAtPosition(position).toString();
                Log.d("selected Store", selectedStoreName);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                selectedStoreName = parent.getItemAtPosition(0).toString();

                Log.d("selected Store", selectedStoreName);

            }
        });


    }

    private void getInput() {
        enteredName = enterName.getText().toString();
        enteredPassword = enterPassword.getText().toString();
        enteredPhoneNumber = enterPhoneNumber.getText().toString();
        Log.d(enteredName + enteredPassword, enteredPhoneNumber);

    }

    private void fireBaseStoreListener() {

        if (!selectedAreaName.equals("Select Area")) {


            databaseReferenceStores.orderByChild("name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    storeIdList.clear();
                    storeNameList.clear();
                    storeNameList.add(0, "Select Store");
                    storeIdList.add(0, "Select Store");
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot store_ : dataSnapshot.getChildren()) {
                            Store store = store_.getValue(Store.class);
                            if (store != null) {
                                String storeId = store.getId();
                                String storeName = store.getName();
                                if (storeId != null && storeName != null) {
                                    if (areaIdList.size() > 0) {
                                        if (store.getAreaId().equals(areaIdList.get(areaNameList.indexOf(selectedAreaName)))) {
                                            if (!storeIdList.contains(storeId)) {
                                                storeIdList.add(storeId);
                                                storeNameList.add(storeName);
                                                arrayAdapterStores.notifyDataSetChanged();
                                            }
                                        }
                                        Log.d("lll", "kkk");
                                    }
                                }
                            }
                        }
                        Log.d(storeIdList.toString(), " storeIds");

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

}
