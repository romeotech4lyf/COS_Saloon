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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.R;

import static android.app.Activity.RESULT_OK;


public class EmployeeDetailInfoFragment extends Fragment implements View.OnClickListener {

    View root;
    TextView name;
    TextView storeName;
    TextView areaName;
    ImageView kyc;
    ImageView dp;
    Employee employee;
    CardView dpEdit;
    ImageView kycEdit;
    FirebaseStorage storageDp;
    private int getImage = 1423;

    private StorageReference storageReferenceDp;


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


        name = root.findViewById(R.id.admin_employee_details_info_name);
        storeName = root.findViewById(R.id.admin_employee_details_info_store_name);
        areaName = root.findViewById(R.id.admin_employee_details_info_area_name);
        kyc = root.findViewById(R.id.admin_employee_details_info_kyc);
        dp = root.findViewById(R.id.admin_employee_details_info_dp);
        dpEdit = root.findViewById(R.id.admin_employee_details_info_dp_edit);
        //  kycEdit = root.findViewById(R.id.admin_employee_details_info_kyc_edit);
        storageDp = FirebaseStorage.getInstance();


        //
        name.setText(employee.getName());
        storeName.setText(employee.getStoreName());
        areaName.setText(employee.getAreaName());


        //
        // dpEdit.setOnClickListener(this);
        // kycEdit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.admin_employee_details_info_dp_edit: {
                startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), getImage);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == getImage) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Uri uriImage = data.getData();
                    storageReferenceDp = storageDp.getReference().child(employee.getId());
                    storageReferenceDp.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();


                        }
                    });
                }
            }
        }
    }


}
