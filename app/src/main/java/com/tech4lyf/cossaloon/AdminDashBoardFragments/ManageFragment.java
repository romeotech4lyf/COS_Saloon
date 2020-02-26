package com.tech4lyf.cossaloon.AdminDashBoardFragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.cossaloon.Activities.AdminHomeActivity;
import com.tech4lyf.cossaloon.AdminManageFragments.AddAreaFragment;
import com.tech4lyf.cossaloon.AdminManageFragments.AddEmployeeFragment;
import com.tech4lyf.cossaloon.AdminManageFragments.AddStoreFragment;
import com.tech4lyf.cossaloon.AdminManageFragments.RemoveAreaFragment;
import com.tech4lyf.cossaloon.AdminManageFragments.RemoveEmployeeFrament;
import com.tech4lyf.cossaloon.AdminManageFragments.RemoveStoreFragment;
import com.tech4lyf.cossaloon.AdminManageFragments.SetPricesFragment;
import com.tech4lyf.cossaloon.ChangeOfStyle;
import com.tech4lyf.cossaloon.Models.Area;
import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.Models.Store;
import com.tech4lyf.cossaloon.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageFragment extends Fragment implements View.OnClickListener {

    private Spinner spinnerSelectStore;
    private Spinner spinnerSelectArea;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceStores;
    private DatabaseReference databaseReferenceAreas;
    private DatabaseReference databaseReferenceEmployees;
    private ArrayAdapter arrayAdapterStores;
    private ArrayAdapter arrayAdapterAreas;
    private String selectedAreaName;
    private CardView addStore;
    private CardView addArea;
    private CardView addEmployee;
    private CardView removeStore;
    private CardView removeEmployee;
    private CardView removeArea;
    private CardView setPrices;
    private View root;
    private ArrayList<String> employeeStoreIdList = new ArrayList<>();
    private ArrayList<String> storeAreaIdList = new ArrayList<>();
    private ArrayList<String> storeAreaNameList = new ArrayList<>();
    private ArrayList<String> employeeStoreNameList = new ArrayList<>();
    private ArrayList<String> areaIdList = new ArrayList<>();
    private ArrayList<String> areaNameList = new ArrayList<>();
    private boolean isStoreIdSynchronized = false;
    private String selectedStoreName;
    private int x = 4;
    private int y = 0;
    private int z = 0;
    private String enterAreaName = "My area";
    private String enterEmployeeName = "My Employee";
    private String enterStoreName = " th Street";


    public ManageFragment() {
        AdminHomeActivity.level++;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_manage, container, false);
        return root;
    }

    //    ArrayList<String> stores = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseApp.initializeApp(this.getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceStores = firebaseDatabase.getReference("Store");
        databaseReferenceAreas = firebaseDatabase.getReference("Areas");
        databaseReferenceEmployees = firebaseDatabase.getReference("Employees");

        //
        addStore = root.findViewById(R.id.admin_manage_add_store);
        addEmployee = root.findViewById(R.id.admin_manage_add_employee);
        addArea = root.findViewById(R.id.admin_manage_add_area);
        removeArea = root.findViewById(R.id.admin_manage_remove_area);
        removeEmployee = root.findViewById(R.id.admin_manage_remove_employee);
        removeStore = root.findViewById(R.id.admin_manage_remove_store);
        setPrices = root.findViewById(R.id.admin_manage_set_prices);

        //
        spinnerSelectStore = root.findViewById(R.id.admin_manage_select_Store);
        arrayAdapterStores = new ArrayAdapter(ChangeOfStyle.getContext(), android.R.layout.simple_spinner_item, employeeStoreNameList);
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


        //
        spinnerSelectArea = root.findViewById(R.id.admin_manage_select_area);
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


        addStore.setOnClickListener(this);
        addEmployee.setOnClickListener(this);
        addArea.setOnClickListener(this);
        removeStore.setOnClickListener(this);
        removeEmployee.setOnClickListener(this);
        removeArea.setOnClickListener(this);
        removeStore.setOnClickListener(this);
        setPrices.setOnClickListener(this);


        fireBaseListener();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.admin_manage_add_store:
                addStore();
                break;
            case R.id.admin_manage_add_employee:
                addEmployee();
                break;
            case R.id.admin_manage_add_area:
                addArea();
                break;
            case R.id.admin_manage_remove_area:
                removeArea();
                break;
            case R.id.admin_manage_remove_employee:
                removeEmployee();
                break;
            case R.id.admin_manage_remove_store:
                removeStore();
                break;
            case R.id.admin_manage_set_prices:
                setPrices();
                break;
            default:
                break;
        }
    }

    private void setPrices() {
        getChildFragmentManager().beginTransaction().replace(R.id.admin_manage_fragment_container, new SetPricesFragment()).commit();

    }

    private void removeArea() {
        getChildFragmentManager().beginTransaction().replace(R.id.admin_manage_fragment_container, new RemoveAreaFragment()).commit();

    }

    private void removeEmployee() {
        getChildFragmentManager().beginTransaction().replace(R.id.admin_manage_fragment_container, new RemoveEmployeeFrament()).commit();


    }

    private void removeStore() {

        getChildFragmentManager().beginTransaction().replace(R.id.admin_manage_fragment_container, new RemoveStoreFragment()).commit();


    }

    void fireBaseListener() {


        databaseReferenceStores.orderByChild("storeName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot store_ : dataSnapshot.getChildren()) {
                            Store store = store_.getValue(Store.class);

                            if (store != null) {
                                String storeId = store.getId();
                                String storeName = store.getName();
                                String storeAreaName = store.getAreaName();
                                if (storeId != null && storeName != null && storeAreaName != null) {
                                    employeeStoreIdList.add(storeId);
                                    employeeStoreNameList.add(storeName);
                                    storeAreaIdList.add(storeName);
                                    arrayAdapterStores.notifyDataSetChanged();
                                    isStoreIdSynchronized = true;
                                }
                                //    employeeStoreNameList.add(store_.getstoreName);
                                Log.d(employeeStoreIdList.toString(), " storeIds");


                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReferenceStores.orderByChild("StoreName").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                fireBaseStoreListener(dataSnapshot);
            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                fireBaseStoreListener(dataSnapshot);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                fireBaseStoreListener(dataSnapshot);


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                fireBaseStoreListener(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReferenceAreas.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                fireBaseAreaListener(dataSnapshot);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                fireBaseAreaListener(dataSnapshot);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                fireBaseAreaListener(dataSnapshot);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                fireBaseAreaListener(dataSnapshot);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReferenceAreas.orderByChild("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot area_ : dataSnapshot.getChildren()) {
                            Area area = area_.getValue(Area.class);

                            if (area != null) {
                                String areaId = area.getId();
                                String areaName = area.getName();
                                if (areaId != null && areaName != null) {
                                    areaIdList.add(areaId);
                                    areaNameList.add(areaName);
                                    arrayAdapterAreas.notifyDataSetChanged();
                                    isStoreIdSynchronized = true;
                                }
                                Log.d(areaIdList.toString(), " areaIds");

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

    private void fireBaseStoreListener(DataSnapshot dataSnapshot) {
        if (dataSnapshot != null) {
            if (dataSnapshot.exists()) {
                Store store = dataSnapshot.getValue(Store.class);

                if (store != null) {
                    String storeId = store.getId();
                    String storeName = store.getName();
                    if (storeId != null && storeName != null) {
                        if (areaIdList.size() > 0 && areaNameList.contains(selectedAreaName)) {
                            if (store.getAreaId() == areaIdList.get(areaNameList.indexOf(selectedAreaName))) {
                                employeeStoreIdList.add(storeId);
                                employeeStoreNameList.add(storeName);
                                arrayAdapterStores.notifyDataSetChanged();
                                isStoreIdSynchronized = true;
                            }
                        }
                    }
                    Log.d(employeeStoreIdList.toString(), " storeIds");


                }
            }
        }


    }

    private void fireBaseAreaListener(DataSnapshot dataSnapshot) {
        if (dataSnapshot != null) {
            if (dataSnapshot.exists()) {
                Area area = dataSnapshot.getValue(Area.class);
                if (area != null) {
                    String areaId = area.getId();
                    String areaName = area.getName();
                    if (areaId != null && areaName != null) {
                        areaIdList.add(areaId);
                        areaNameList.add(areaName);
                        arrayAdapterAreas.notifyDataSetChanged();
                        isStoreIdSynchronized = true;
                    }
                    Log.d(areaIdList.toString(), " areaIds");


                }
            }
        }

    }

    private void addArea() {

        getChildFragmentManager().beginTransaction().replace(R.id.admin_manage_fragment_container, new AddAreaFragment()).commit();


        Toast.makeText(getActivity().getBaseContext(), "AreaAdded", Toast.LENGTH_SHORT).show();
        String key = databaseReferenceAreas.push().getKey();
        databaseReferenceAreas.child(key).setValue(new Area(key, enterAreaName + ++z));


    }

    private void addEmployee() {

        getChildFragmentManager().beginTransaction().replace(R.id.admin_manage_fragment_container, new AddEmployeeFragment()).commit();


        if (isStoreIdSynchronized) {
            Toast.makeText(getActivity().getBaseContext(), "EmployeeAdded", Toast.LENGTH_SHORT).show();
            String key = databaseReferenceEmployees.push().getKey();
            databaseReferenceEmployees.child(key).setValue(new Employee(key, enterEmployeeName + ++y, employeeStoreIdList.get(employeeStoreNameList.indexOf(selectedStoreName)), selectedStoreName + " - " + areaNameList.get(employeeStoreNameList.indexOf(selectedStoreName)), areaIdList.get(areaNameList.indexOf(selectedAreaName)), "areaName", "9191919191", "18-1-2020"));
        }
    }

    private void addStore() {

        getChildFragmentManager().beginTransaction().replace(R.id.admin_manage_fragment_container, new AddStoreFragment()).commit();

        if (isStoreIdSynchronized) {
            Toast.makeText(getActivity().getBaseContext(), "storeAdded", Toast.LENGTH_SHORT).show();
            String key = databaseReferenceStores.push().getKey();
            databaseReferenceStores.child(key).setValue(new Store(key, ++x + enterStoreName, areaIdList.get(areaNameList.indexOf(selectedAreaName)), selectedAreaName, "address", 400, 20000));

        }

    }


}
