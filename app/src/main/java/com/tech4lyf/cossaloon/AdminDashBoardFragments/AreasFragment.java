package com.tech4lyf.cossaloon.AdminDashBoardFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.cossaloon.Activities.AdminHomeActivity;
import com.tech4lyf.cossaloon.Models.Area;
import com.tech4lyf.cossaloon.R;
import com.tech4lyf.cossaloon.adapters.RecyclerViewAdapterAreas;

import java.util.ArrayList;


public class AreasFragment extends Fragment {

    ArrayList<String> titleList = new ArrayList<>();
    ArrayList<String> subTitleList = new ArrayList<>();
    ArrayList<Integer> imageList = new ArrayList<>();
    ArrayList<Area> areaList = new ArrayList<>();
    RecyclerViewAdapterAreas recyclerViewAdapterAreas;
    private View view;
    private RecyclerView recyclerView;

    public AreasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_areas, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view_admin_areas);

        recyclerViewAdapterAreas = new RecyclerViewAdapterAreas(areaList);
        recyclerView.setAdapter(recyclerViewAdapterAreas);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        AdminHomeActivity.level = 1;
        DatabaseReference databaseReferenceAreas = FirebaseDatabase.getInstance().getReference().child("Areas");
        databaseReferenceAreas.orderByChild("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot area_ : dataSnapshot.getChildren()) {
                            if (area_ != null) {
                                Area area = area_.getValue(Area.class);
                                areaList.add(area);
                                recyclerViewAdapterAreas.setAreaList(areaList);
                                recyclerViewAdapterAreas.notifyDataSetChanged();
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
}
