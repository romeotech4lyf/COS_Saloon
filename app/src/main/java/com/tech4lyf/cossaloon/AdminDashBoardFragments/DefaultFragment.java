package com.tech4lyf.cossaloon.AdminDashBoardFragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.cossaloon.Activities.LoginActivity;
import com.tech4lyf.cossaloon.FormatData;
import com.tech4lyf.cossaloon.Listeners;
import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.R;

import net.cachapa.expandablelayout.ExpandableLayout;

public class DefaultFragment extends Fragment implements View.OnClickListener {

    private CardView stores;
    private CardView employees;
    private CardView manage;
    private CardView settings;
    private CardView signOut;
    private View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_admin_dash_board_default, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews();

        databaseReferenceIncomes = FirebaseDatabase.getInstance().getReference().child("Incomes");


        currentDate = FormatData.getCurrentDeviceDate();
        currentMonth = FormatData.getCurrentDeviceMonth();
        currentYear = FormatData.getCurrentDeviceYear();

        fireBaseListener();

        calendarListener();



    }

    private void initializeViews() {
        stores = root.findViewById(R.id.dashBoard_admin_stores_card);
        employees = root.findViewById(R.id.dashBoard_admin_employees_card);
        manage = root.findViewById(R.id.dashBoard_admin_manage_card);
        settings = root.findViewById(R.id.dashBoard_admin_areas_card);
        signOut = root.findViewById(R.id.dashBoard_admin_sign_out);
        calendar = root.findViewById(R.id.dashBoard_admin_calendar);
        incomeDaily = root.findViewById(R.id.dashBoard_admin_income_daily);
        incomeMonthly = root.findViewById(R.id.dashBoard_admin_income_monthly);
        signOut.setVisibility(View.INVISIBLE);

        stores.setOnClickListener(this);
        employees.setOnClickListener(this);
        manage.setOnClickListener(this);
        settings.setOnClickListener(this);
        signOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.dashBoard_admin_stores_card:
                Listeners.triggerOnClickDashBoardItemListener(R.string.stores);
                break;

            case R.id.dashBoard_admin_employees_card:
                Listeners.triggerOnClickDashBoardItemListener(R.string.employees);
                break;

            case R.id.dashBoard_admin_manage_card:
                Listeners.triggerOnClickDashBoardItemListener(R.string.services);
                break;

            case R.id.dashBoard_admin_areas_card:
                Listeners.triggerOnClickDashBoardItemListener(R.string.areas);
                break;

            case R.id.dashBoard_admin_sign_out:
                startActivity(new Intent(getContext(), LoginActivity.class).putExtra("isReturning",true));
                this.getActivity().finish();

            default:
                break;


        }

    }


    private void calendarListener() {
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {


                selectedDate = String.valueOf(dayOfMonth);
                selectedMonth = String.valueOf(month+1);
                selectedYear = String.valueOf(year);

                if(selectedDate.length()==1)
                    selectedDate="0"+selectedDate;
                if(selectedMonth.length()==1)
                    selectedMonth="0"+selectedMonth;



                Log.d("selected Date "+ selectedDate + selectedMonth + selectedYear,"currentDate "+currentDate+currentMonth+currentYear);



                databaseReferenceIncomes.child(selectedYear).child(selectedMonth).child(selectedDate).child("Combined").addValueEventListener(new ValueEventListener() {
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
                databaseReferenceIncomes.child(selectedYear).child(selectedMonth).child("Combined").addValueEventListener(new ValueEventListener() {
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
            }});
    }

    private void fireBaseListener() {

        databaseReferenceIncomes.child(currentYear).child(currentMonth).child(currentDate).child("Combined").addValueEventListener(new ValueEventListener() {
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
        databaseReferenceIncomes.child(currentYear).child(currentMonth).child("Combined").addValueEventListener(new ValueEventListener() {
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

    private String selectedDate;
    private String selectedMonth;
    private String selectedYear;
    private DatabaseReference databaseReferenceIncomes;
    private TextView employeeName;
    private TextView storeName;
    private RelativeLayout bills;
    private RelativeLayout info;
    private Employee employee;
    private CalendarView calendar;
    private String currentDate;
    private String currentMonth;
    private String currentYear;
    private Integer totalDaily = 0;
    private Integer totalMonthly = 0;
    private TextView incomeDaily;
    private TextView incomeMonthly;
}