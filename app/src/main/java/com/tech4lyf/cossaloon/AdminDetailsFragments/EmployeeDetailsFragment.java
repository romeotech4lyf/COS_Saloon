package com.tech4lyf.cossaloon.AdminDetailsFragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tech4lyf.cossaloon.Activities.AdminHomeActivity;
import com.tech4lyf.cossaloon.AdminEmployeeDetailsFragments.EmployeeDetailBillsFragment;
import com.tech4lyf.cossaloon.AdminEmployeeDetailsFragments.EmployeeDetailInfoFragment;
import com.tech4lyf.cossaloon.FormatData;
import com.tech4lyf.cossaloon.Models.Bill;
import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.R;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeDetailsFragment extends Fragment implements View.OnClickListener {

    ArrayList<String> listItems = new ArrayList<>();
    ArrayList<Integer> listItemPrices = new ArrayList<>();
    String selectedDate;
    String selectedMonth;
    String selectedYear;
    private View root;
    private DatabaseReference databaseReferenceEmployees;
    private TextView employeeName;
    private TextView storeName;
    private CardView bills;
    private CardView info;
    private Employee employee;
    private CalendarView calendar;
    private ExpandableLayout expandableLayoutInfo;
    private ExpandableLayout expandableLayoutBills;

    public EmployeeDetailsFragment() {
        // Required empty public constructor
    }

    public EmployeeDetailsFragment(Employee employee) {
        this.employee = employee;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_employee_details, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AdminHomeActivity.level = 2;

        employeeName = root.findViewById(R.id.admin_employee_details_title);
        info = root.findViewById(R.id.admin_employee_details_info);
        bills = root.findViewById(R.id.admin_employee_details_bills);
        expandableLayoutInfo = root.findViewById(R.id.admin_employee_details_info_expandable_layout);
        expandableLayoutBills = root.findViewById(R.id.admin_employee_details_bills_expandable_layout);
        calendar = root.findViewById(R.id.admin_employee_details_calendar);

        selectedDate = new SimpleDateFormat("dd").format(new Date(calendar.getDate()));
        selectedMonth = new SimpleDateFormat("mm").format(new Date(calendar.getDate()));
        selectedYear = new SimpleDateFormat("yyyy").format(new Date(calendar.getDate()));

        Log.d(selectedDate, "date");


        info.setOnClickListener(this);
        bills.setOnClickListener(this);


        employeeName.setText(employee.getName());
        test();

    }

    private void test() {
        DatabaseReference databaseReferenceIncomes = FirebaseDatabase.getInstance().getReference().child("Incomes");


        listItems.add("cutting");
        listItems.add("shaving");
        listItemPrices.add(120);
        listItemPrices.add(40);

        String date = new SimpleDateFormat("dd").format(new Date().getTime());
        String month = new SimpleDateFormat("mm").format(new Date().getTime());
        String year = new SimpleDateFormat("yyyy").format(new Date().getTime());
        String time = new SimpleDateFormat("hh:mm:ss").format(new Date().getTime());

        Integer total = FormatData.getTotal(listItemPrices);


        String key = databaseReferenceIncomes.push().getKey();

        databaseReferenceIncomes.child(year).child(month).child(date).child(key).setValue(new Bill(key, employee.getAreaId(), employee.getAreaName(), employee.getStoreId(), employee.getStoreName(), employee.getId(), employee.getName(), date, time, listItems, listItemPrices, total));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.admin_employee_details_bills:
                if (expandableLayoutBills.isExpanded()) {
                    expandableLayoutBills.collapse(true);
                } else {
                    getChildFragmentManager().beginTransaction().replace(R.id.admin_employee_details_bills_fragment_container, new EmployeeDetailBillsFragment(employee.getId()), "BILLS").commit();
                    expandableLayoutBills.expand(true);
                }
                break;

            case R.id.admin_employee_details_info:
                if (expandableLayoutInfo.isExpanded()) {
                    expandableLayoutInfo.collapse(true);
                } else {
                    getChildFragmentManager().beginTransaction().replace(R.id.admin_employee_details_info_fragment_container, new EmployeeDetailInfoFragment(employee), "INFO").commit();
                    expandableLayoutInfo.expand(true);
                }
            default:
                break;
        }

    }
}
