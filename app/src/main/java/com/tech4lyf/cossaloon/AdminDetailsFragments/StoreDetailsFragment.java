package com.tech4lyf.cossaloon.AdminDetailsFragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.cossaloon.Activities.AdminHomeActivity;
import com.tech4lyf.cossaloon.AdminDetailBillsFragment.StoreDetailBillsFragment;
import com.tech4lyf.cossaloon.ChangeOfStyle;
import com.tech4lyf.cossaloon.Context;
import com.tech4lyf.cossaloon.FormatData;
import com.tech4lyf.cossaloon.Listeners;
import com.tech4lyf.cossaloon.Models.Store;
import com.tech4lyf.cossaloon.R;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreDetailsFragment extends Fragment implements View.OnClickListener {

    ArrayList<String> listItems = new ArrayList<>();
    ArrayList<Integer> listItemPrices = new ArrayList<>();
    private String selectedDate;
    private String selectedMonth;
    private String selectedYear;
    private View root;
    private DatabaseReference databaseReferenceStores;
    private DatabaseReference databaseReferenceIncomes;
    private TextView storeName;
    private TextView jobsToday;
    private TextView jobsThisMonth;
    private RelativeLayout bills;
    private RelativeLayout info;
    private Store store;
    private CalendarView calendar;
    private String currentDate;
    private String currentMonth;
    private String currentYear;
    private ExpandableLayout expandableLayoutInfo;
    private ExpandableLayout expandableLayoutBills;
    private Integer totalDaily = 0;
    private Integer totalMonthly = 0;
    private TextView incomeDaily;
    private TextView incomeMonthly;
    private CircleImageView dP;
    private DatabaseReference databaseReferenceJobsToday;
    private DatabaseReference databaseReferenceJobsThisMonth;
    private CardView delete;
    private Integer jobsTodayCount;
    private Integer jobsThisMonthCount;
    private EditText editName;
    private TextView editNameEnter;

    public StoreDetailsFragment() {
        // Required empty public constructor
    }

    public StoreDetailsFragment(Store store) {
        this.store = store;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_store_details, container, false);
        AdminHomeActivity.objectType = Context.OBJECT_TYPE.STORE;
        AdminHomeActivity.level = 2;


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        storeName = root.findViewById(R.id.admin_store_details_title);
        info = root.findViewById(R.id.admin_store_details_info);
        delete = root.findViewById(R.id.admin_store_details_delete);
        bills = root.findViewById(R.id.admin_store_details_bills);
        expandableLayoutInfo = root.findViewById(R.id.admin_store_details_info_expandable_layout);
        expandableLayoutBills = root.findViewById(R.id.admin_store_details_bills_expandable_layout);
        calendar = root.findViewById(R.id.admin_store_details_calendar);
        incomeDaily = root.findViewById(R.id.admin_store_details_income_daily);
        incomeMonthly = root.findViewById(R.id.admin_store_details_income_monthly);
        dP = root.findViewById(R.id.admin_store_details_image);
        jobsToday = root.findViewById(R.id.admin_store_details_jobs_today);
        jobsThisMonth = root.findViewById(R.id.admin_store_details_jobs_this_month);
        editName = root.findViewById(R.id.admin_store_details_edit_name);
        editNameEnter = root.findViewById(R.id.admin_store_details_edit_name_enter);

        databaseReferenceIncomes = FirebaseDatabase.getInstance().getReference().child("Incomes");
        databaseReferenceStores = FirebaseDatabase.getInstance().getReference().child("Stores");


        currentDate = FormatData.getCurrentDeviceDate();
        currentMonth = FormatData.getCurrentDeviceMonth();
        currentYear = FormatData.getCurrentDeviceYear();
        databaseReferenceJobsToday = FirebaseDatabase.getInstance().getReference().child("Jobs").child("Today" + currentDate);
        databaseReferenceJobsThisMonth = FirebaseDatabase.getInstance().getReference().child("Jobs").child("ThisMonth" + currentMonth);

        fireBaseListener();

        calendarListener();

        editName.setText(store.getName());


        info.setOnClickListener(this);
        bills.setOnClickListener(this);
        delete.setOnClickListener(this);
        editNameEnter.setOnClickListener(this);


        storeName.setText(store.getName());

        jobsThisMonth.setText(FormatData.setJobsCountThisMonth(jobsThisMonthCount));
        jobsToday.setText(FormatData.setJobsCountToday(jobsTodayCount));
        // test();


    }


    private void calendarListener() {
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {


                selectedDate = String.valueOf(dayOfMonth);
                selectedMonth = String.valueOf(month + 1);
                selectedYear = String.valueOf(year);

                if (selectedDate.length() == 1)
                    selectedDate = "0" + selectedDate;
                if (selectedMonth.length() == 1)
                    selectedMonth = "0" + selectedMonth;


                Log.d("selected Date " + selectedDate + selectedMonth + selectedYear, "currentDate " + currentDate + currentMonth + currentYear);


                databaseReferenceIncomes.child(selectedYear).child(selectedMonth).child(selectedDate).child(store.getId()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            totalDaily = dataSnapshot.getValue(Integer.class);
                        } else
                            totalDaily = 0;
                        incomeDaily.setText(String.valueOf(totalDaily));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                databaseReferenceIncomes.child(selectedYear).child(selectedMonth).child(store.getId()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            totalMonthly = dataSnapshot.getValue(Integer.class);
                        } else
                            totalMonthly = 0;
                        incomeMonthly.setText(String.valueOf(totalMonthly));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void fireBaseListener() {

        databaseReferenceJobsToday.child(store.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    jobsTodayCount = dataSnapshot.getValue(Integer.class);

                } else
                    jobsTodayCount = 0;
                jobsToday.setText(FormatData.setJobsCountToday(jobsTodayCount));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReferenceJobsThisMonth.child(store.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    jobsThisMonthCount = dataSnapshot.getValue(Integer.class);

                } else
                    jobsThisMonthCount = 0;
                jobsThisMonth.setText(FormatData.setJobsCountThisMonth(jobsThisMonthCount));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReferenceIncomes.child(currentYear).child(currentMonth).child(currentDate).child(store.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    totalDaily = dataSnapshot.getValue(Integer.class);
                } else
                    totalDaily = 0;
                incomeDaily.setText(String.valueOf(totalDaily));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReferenceIncomes.child(currentYear).child(currentMonth).child(store.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    totalMonthly = dataSnapshot.getValue(Integer.class);
                } else
                    totalMonthly = 0;
                incomeMonthly.setText(String.valueOf(totalMonthly));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.admin_store_details_bills:
                if (expandableLayoutBills.isExpanded()) {
                    expandableLayoutBills.collapse(true);
                } else {
                    getChildFragmentManager().beginTransaction().replace(R.id.admin_store_details_bills_fragment_container, new StoreDetailBillsFragment(store.getId()), "BILLS").commit();
                    expandableLayoutBills.expand(true);
                }
                break;

            case R.id.admin_store_details_info:
                if (expandableLayoutInfo.isExpanded()) {
                    expandableLayoutInfo.collapse(true);
                } else {
                    expandableLayoutInfo.expand(true);
                }
                break;

            case R.id.admin_store_details_edit_name_enter:
                String enteredName = editName.getText().toString();
                if (!(enteredName.length() < 1)) {
                    databaseReferenceStores.child(store.getId()).child("name").setValue(enteredName);
                    storeName.setText(enteredName);
                }
                expandableLayoutInfo.collapse(true);
                break;

            case R.id.admin_store_details_delete:
                deleteStore();
                break;

            default:
                break;
        }

    }

    private void deleteStore() {
        databaseReferenceStores.child(store.getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ChangeOfStyle.getContext(),"Store Removed Successfully",Toast.LENGTH_SHORT).show();
                Listeners.triggerOnClickDashBoardItemListener(R.string.stores);

            }
        });


    }
}
