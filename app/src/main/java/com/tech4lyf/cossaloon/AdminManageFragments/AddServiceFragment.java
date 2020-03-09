package com.tech4lyf.cossaloon.AdminManageFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tech4lyf.cossaloon.Activities.AdminHomeActivity;
import com.tech4lyf.cossaloon.Models.Service;
import com.tech4lyf.cossaloon.R;

public class AddServiceFragment extends Fragment implements View.OnClickListener {


    private View root;
    private String enteredName = null;
    private String enteredPrice = null;
    private CardView add;
    private CardView cancel;
    private EditText enterName;
    private EditText enterPrice;
    private DatabaseReference databaseReferenceServices;
    private String key;


    public AddServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_add_service_frgment, container, false);
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReferenceServices = firebaseDatabase.getReference("Services");


        //
        enterName = root.findViewById(R.id.admin_add_service_enter_name);
        enterPrice = root.findViewById(R.id.admin_add_service_enter_price);

        add = root.findViewById(R.id.admin_add_service_ok);
        cancel = root.findViewById(R.id.admin_add_service_cancel);


        AdminHomeActivity.level = 2;


        //

        //
        add.setOnClickListener(this);
        cancel.setOnClickListener(this);


    }


    private void getInput() {
        enteredName = enterName.getText().toString();
        enteredPrice = enterPrice.getText().toString();


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.admin_add_service_ok:
                getInput();
                key = databaseReferenceServices.push().getKey();
                if (!(enteredName.length() < 1) && !(enteredPrice.length() < 1)) {

                    databaseReferenceServices.child(key).
                            setValue(new Service(key, enteredName, Integer.valueOf(enteredPrice))).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "Service Added Successfully", Toast.LENGTH_SHORT).show();
                            if (!getParentFragmentManager().isDestroyed()) {
                                AdminHomeActivity.level = 1;
                                getParentFragmentManager().beginTransaction().remove(AddServiceFragment.this).commit();
                            }

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


            case R.id.admin_add_service_cancel:
                if (!getParentFragmentManager().isDestroyed()) {
                    AdminHomeActivity.level = 1;
                    getParentFragmentManager().beginTransaction().remove(AddServiceFragment.this).commit();
                }

        }
    }


}
