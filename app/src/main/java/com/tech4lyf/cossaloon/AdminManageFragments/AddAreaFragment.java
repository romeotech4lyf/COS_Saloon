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
import com.tech4lyf.cossaloon.FormatData;
import com.tech4lyf.cossaloon.Models.Area;
import com.tech4lyf.cossaloon.R;

public class AddAreaFragment extends Fragment implements View.OnClickListener {


    private View root;
    private String enteredName = null;
    private CardView add;
    private CardView cancel;
    private EditText enterName;
    private DatabaseReference databaseReferenceAreas;
    private String key;


    public AddAreaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_add_area, container, false);
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReferenceAreas = firebaseDatabase.getReference("Areas");


        //
        enterName = root.findViewById(R.id.admin_add_area_enter_name);

        add = root.findViewById(R.id.admin_add_area_ok);
        cancel = root.findViewById(R.id.admin_add_area_cancel);


        AdminHomeActivity.level = 2;


        //

        //
        add.setOnClickListener(this);
        cancel.setOnClickListener(this);


    }


    private void getInput() {
        enteredName = enterName.getText().toString();


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.admin_add_area_ok:
                getInput();
                key = databaseReferenceAreas.push().getKey();
                if (!(enteredName.length() < 1)) {

                    databaseReferenceAreas.child(key).
                            setValue(new Area(key, enteredName, FormatData.getCurrentDeviceFullDate())).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "Area Added Successfully", Toast.LENGTH_SHORT).show();

                            if (!getParentFragmentManager().isDestroyed()) {
                                AdminHomeActivity.level = 1;
                                getParentFragmentManager().beginTransaction().remove(AddAreaFragment.this).commit();
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


            case R.id.admin_add_area_cancel:
                if (!getParentFragmentManager().isDestroyed()) {
                    AdminHomeActivity.level = 1;
                    getParentFragmentManager().beginTransaction().remove(AddAreaFragment.this).commit();
                }
        }
    }


}
