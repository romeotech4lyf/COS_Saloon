package com.tech4lyf.cossaloon.ui.user.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.cossaloon.AdminHome;
import com.tech4lyf.cossaloon.Listeners;
import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements Listeners.OnClickRecyclerItemListener, Listeners.OnBackPressedListener {

    private RecyclerViewAdapter recyclerViewAdapter;
    private DatabaseReference databaseReference;
    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private View root;
    private TextView textView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = root.findViewById(R.id.text_home);
        Listeners.setOnClickRecyclerItemListener(this);
        Listeners.setOnBackPressedListener(this);
     /*   homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        textView.setText(getResources().getString(R.string.dashBoard));
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Employee");


        initializeRecyclerView();


    }

    private void initializeRecyclerView() {
        final ArrayList<String> titles = new ArrayList<>();
        ArrayList<Integer> images = new ArrayList<>();
        textView.setText(getResources().getString(R.string.dashBoard)+"->");


        databaseReference.orderByChild("Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    if (dataSnapshot.exists()) {
                        // dataSnapshot is the "issue" node with all children with id 0
                        //*for (DataSnapshot user : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        Employee employee = dataSnapshot.getValue(Employee.class);
                        if (employee != null) {
                            titles.add(employee.getName());


                        }
                        //}
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        titles.add(getResources().getString(R.string.stores));
        titles.add(getResources().getString(R.string.employee));
        titles.add(getResources().getString(R.string.administration));
        titles.add(getResources().getString(R.string.settings));

        images.add(R.mipmap.stores);
        images.add(R.mipmap.employees);
        images.add(R.mipmap.statistics);
        images.add(R.mipmap.settings);


        recyclerView = root.findViewById(R.id.recycler_view_dashBoard);
        recyclerViewAdapter = new RecyclerViewAdapter(titles, images);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

    }

    @Override
    public void onClick(String title) {
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<Integer> images = new ArrayList<>();
        textView.setText(title+"->");
        if (title.equals(getResources().getString(R.string.stores))) {

            int storeCount = 6;
            for (int i = 0; i < storeCount; i++) {
                titles.add("Store Name");
                images.add(R.drawable.ic_store_black_24dp);
            }

            recyclerViewAdapter.setImageList(images);
            recyclerViewAdapter.setTitleList(titles);
            recyclerViewAdapter.notifyDataSetChanged();
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        }
        if (title.equals(getResources().getString(R.string.employee))) {
            int employeeCount = 12;

            for (int i = 0; i < employeeCount; i++) {
                titles.add("EmployeeName");
                images.add(R.drawable.ic_person_black_24dp);

            }


            recyclerViewAdapter.setImageList(images);
            recyclerViewAdapter.setTitleList(titles);
            recyclerViewAdapter.notifyDataSetChanged();
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        }
        if (title.equals(getResources().getString(R.string.administration))) {

            titles.add("ADD STORE");
            images.add(R.drawable.ic_store_black_24dp);

            recyclerViewAdapter.setImageList(images);
            recyclerViewAdapter.setTitleList(titles);
            recyclerViewAdapter.notifyDataSetChanged();
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        }
    }

    @Override
    public void onBackPressed() {
        AdminHome.isReturn = true;
        initializeRecyclerView();
    }
}