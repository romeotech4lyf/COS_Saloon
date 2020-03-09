package com.tech4lyf.cossaloon.AdminEmployeeDetailsFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tech4lyf.cossaloon.AdminManageFragments.EditEmployeeFragment;
import com.tech4lyf.cossaloon.ChangeOfStyle;
import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.R;

import static android.app.Activity.RESULT_OK;


public class EmployeeDetailInfoFragment extends Fragment implements View.OnClickListener {

    private final int getDP = 1236;
    private final int getKYC = 1326;
    View root;
    TextView name;
    TextView storeName;
    TextView areaName;
    TextView joiningDate;
    TextView phoneNumber;
    Employee employee;
    CardView dpEdit;
    CardView kycEdit;
    CardView edit;
    FirebaseStorage storageDp;
    DatabaseReference databaseReferenceEmployees;
    private FirebaseStorage storage;
    private StorageReference storageReferenceDP;
    private StorageReference storageReferenceKYC;
    private ImageView dP;
    private ImageView kYC;
    private Uri dPUri;
    private Uri kYCUri;

    public EmployeeDetailInfoFragment(Employee employee) {
        this.employee = employee;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_employee_detail_info_fragments, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        storage = FirebaseStorage.getInstance();
        String key = employee.getId();
        databaseReferenceEmployees = FirebaseDatabase.getInstance().getReference().child("Employees");


        storageReferenceDP = storage.getReference().child(key + "-dp");
        storageReferenceKYC = storage.getReference().child(key + "-kyc");


        name = root.findViewById(R.id.admin_employee_details_info_name);
        storeName = root.findViewById(R.id.admin_employee_details_info_store_name);
        areaName = root.findViewById(R.id.admin_employee_details_info_area_name);
        kYC = root.findViewById(R.id.admin_employee_details_info_kyc);
        dP = root.findViewById(R.id.admin_employee_details_info_dp);
        dpEdit = root.findViewById(R.id.admin_employee_details_info_dp_edit);
        kycEdit = root.findViewById(R.id.admin_employee_details_info_kyc_edit);
        edit = root.findViewById(R.id.admin_employee_details_info_edit);
        joiningDate = root.findViewById(R.id.admin_employee_details_info_joining_date);
        phoneNumber = root.findViewById(R.id.admin_employee_details_info_phone_number);
        storageDp = FirebaseStorage.getInstance();


        //
        name.setText(employee.getName());
        storeName.setText(employee.getStoreName());
        areaName.setText(employee.getAreaName());
        joiningDate.setText(employee.getJoiningDate());
        phoneNumber.setText(employee.getPhoneNumber());
        if (employee.getdPUriString() != null)
            Glide.with(ChangeOfStyle.getContext()).load(Uri.parse(employee.getdPUriString())).into(dP);
        if (employee.getkYCUriString() != null)
            Glide.with(ChangeOfStyle.getContext()).load(Uri.parse(employee.getkYCUriString())).into(kYC);

        //
        dpEdit.setOnClickListener(this);
        kycEdit.setOnClickListener(this);
        edit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.admin_employee_details_info_dp_edit:
                startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), getDP);
                break;

            case R.id.admin_employee_details_info_kyc_edit:
                startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), getKYC);
                break;
            case R.id.admin_employee_details_info_edit:
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.admin_employee_details_info_edit_fragment_container, new EditEmployeeFragment(employee), "EDIT EMPLOYEE")
                        .addToBackStack("EDIT EMPLOYEE").commit();

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
                                        if (uri != null) {
                                            dPUri = uri;
                                            databaseReferenceEmployees.child(employee.getId()).child("dPUriString").setValue(dPUri.toString());
                                            Glide.with(ChangeOfStyle.getContext()).load(dPUri).into(dP);
                                        }

                                    }
                                });

                                Toast.makeText(ChangeOfStyle.getContext(), "DP Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ChangeOfStyle.getContext(), "DP Uploading Failed", Toast.LENGTH_SHORT).show();


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
                                    Toast.makeText(ChangeOfStyle.getContext(), "KYC Uploaded", Toast.LENGTH_SHORT).show();
                                    storageReferenceKYC.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            if (uri != null) {
                                                kYCUri = uri;
                                                databaseReferenceEmployees.child(employee.getId()).child("kYCUriString").setValue(kYCUri.toString());
                                                Glide.with(ChangeOfStyle.getContext()).load(kYCUri).into(kYC);
                                            }
                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ChangeOfStyle.getContext(), "KYC Uploading Failed", Toast.LENGTH_SHORT).show();


                                }
                            });
                        }
                    }


                }
        }
    }

}
