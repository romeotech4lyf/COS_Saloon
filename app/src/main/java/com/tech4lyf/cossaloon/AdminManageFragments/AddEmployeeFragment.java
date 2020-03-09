package com.tech4lyf.cossaloon.AdminManageFragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tech4lyf.cossaloon.Activities.AdminHomeActivity;
import com.tech4lyf.cossaloon.ChangeOfStyle;
import com.tech4lyf.cossaloon.FormatData;
import com.tech4lyf.cossaloon.Models.Area;
import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.Models.Store;
import com.tech4lyf.cossaloon.R;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class AddEmployeeFragment extends Fragment implements View.OnClickListener {

    private final int getKYC = 123;
    private final int getDP = 122;
    View root;
    String selectedStoreName = null;
    String selectedAreaName = null;
    String enteredName = null;
    String enteredPhoneNumber = null;
    String enteredPassword = null;
    CardView add;
    CardView cancel;
    CardView uploadDP;
    CardView uploadKYC;
    File localFile = null;
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
    private FirebaseStorage storage;
    private StorageReference storageReferenceDP;
    private StorageReference storageReferenceKYC;
    private ImageView dP;
    private ImageView kYC;
    private Uri dPUri;
    private Uri kYCUri;

    public AddEmployeeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_add_employee, container, false);
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        databaseReferenceStores = firebaseDatabase.getReference("Stores");
        databaseReferenceAreas = firebaseDatabase.getReference("Areas");
        databaseReferenceEmployees = firebaseDatabase.getReference("Employees");
        key = databaseReferenceEmployees.push().getKey();
        storageReferenceDP = storage.getReference().child(key + "-dp");
        storageReferenceKYC = storage.getReference().child(key + "-kyc");


        areaIdList.add(0, "Select Area");
        areaNameList.add(0, "Select Area");
        storeNameList.add(0, "Select Area First");
        storeIdList.add(0, "Select Area First");

        fireBaseAreaListener();

        //
        enterName = root.findViewById(R.id.admin_add_employee_enter_name);
        enterPassword = root.findViewById(R.id.admin_add_employee_enter_password);
        enterPhoneNumber = root.findViewById(R.id.admin_add_employee_enter_phone_number);
        spinnerSelectArea = root.findViewById(R.id.admin_add_employee_select_area);
        spinnerSelectStore = root.findViewById(R.id.admin_add_employee_select_store);
        add = root.findViewById(R.id.admin_add_employee_ok);
        cancel = root.findViewById(R.id.admin_add_employee_cancel);
        uploadDP = root.findViewById(R.id.admin_add_employee_upload_dp);
        uploadKYC = root.findViewById(R.id.admin_add_employee_upload_kyc);
        dP = root.findViewById(R.id.admin_add_employee_dp);
        kYC = root.findViewById(R.id.admin_add_employee_kyc);
        AdminHomeActivity.level = 2;


        //
        fillSpinners();

        //
        add.setOnClickListener(this);
        cancel.setOnClickListener(this);
        uploadKYC.setOnClickListener(this);
        uploadDP.setOnClickListener(this);


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
        spinnerSelectArea = root.findViewById(R.id.admin_add_employee_select_area);
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

        spinnerSelectStore = root.findViewById(R.id.admin_add_employee_select_store);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.admin_add_employee_ok:
                getInput();
                if (!(enteredPhoneNumber.length() < 10) && !(enteredPassword.length() < 8) && !(enteredName.length() < 1)
                        && !selectedAreaName.equals("Select Area") && !selectedStoreName.equals("Select Area First") && !selectedStoreName.equals("Select Store")) {
                    String dpURIString = dPUri == null ? null : dPUri.toString();
                    String kYCURIString = kYCUri == null ? null : kYCUri.toString();
                    databaseReferenceEmployees.child(key).setValue(new Employee(key, enteredName, enteredPassword, storeIdList.get(storeNameList.indexOf(selectedStoreName)), selectedStoreName, areaIdList.get(areaNameList.indexOf(selectedAreaName)), selectedAreaName, enteredPhoneNumber, FormatData.getCurrentDeviceFullDate(), dpURIString, kYCURIString)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "Employee Added Successfully", Toast.LENGTH_SHORT).show();
                            getParentFragmentManager().beginTransaction().remove(AddEmployeeFragment.this).commit();

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

            case R.id.admin_add_employee_upload_dp:
                startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), getDP);
                break;
            case R.id.admin_add_employee_upload_kyc:
                startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), getKYC);
                break;

            case R.id.admin_add_employee_cancel:
                AdminHomeActivity.level=1;
                getParentFragmentManager().beginTransaction().remove(AddEmployeeFragment.this).commit();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case getDP:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Uri uriImage = data.getData();
                        storageReferenceDP.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReferenceDP.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        dPUri = uri;
                                        Glide.with(getContext()).load(uri).into(dP);

                                    }
                                });

                                Toast.makeText(getContext(), "DP Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "DP Uploading Failed", Toast.LENGTH_SHORT).show();


                            }
                        });
                    }
                }
                break;

            case getKYC:
                if (resultCode == RESULT_OK) {

                    if (data != null) {
                        Uri uriImage = data.getData();
                        if (uriImage != null) {
                            storageReferenceKYC.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(getContext(), "KYC Uploaded", Toast.LENGTH_SHORT).show();
                                    storageReferenceKYC.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            kYCUri = uri;
                                            Glide.with(getContext()).load(uri).into(kYC);

                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "KYC Uploading Failed", Toast.LENGTH_SHORT).show();


                                }
                            });
                        }
                    }


                }
        }
    }


}
