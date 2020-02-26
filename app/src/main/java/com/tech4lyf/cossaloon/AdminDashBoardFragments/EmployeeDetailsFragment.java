package com.tech4lyf.cossaloon.AdminDashBoardFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tech4lyf.cossaloon.Activities.AdminHomeActivity;
import com.tech4lyf.cossaloon.AdminDetailsFragments.EmployeeDetailBillsFragment;
import com.tech4lyf.cossaloon.AdminDetailsFragments.EmployeeDetailInfoFragment;
import com.tech4lyf.cossaloon.Models.Bill;
import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.R;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeDetailsFragment extends Fragment implements View.OnClickListener {

    ArrayList<String> listItems = new ArrayList<>();
    ArrayList<Integer> listItemPrices = new ArrayList<>();
    private View root;
    private DatabaseReference databaseReferenceEmployees;
    private TextView employeeName;
    private TextView storeName;
    private DatabaseReference databaseReferenceIncomes;
    private CardView bills;
    private CardView info;
    private Employee employee;
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
        storeName = root.findViewById(R.id.admin_employee_details_sub_title);
        info = root.findViewById(R.id.admin_employee_details_info);
        bills = root.findViewById(R.id.admin_employee_details_bills);
        expandableLayoutInfo = root.findViewById(R.id.admin_employee_details_info_expandable_layout);
        expandableLayoutBills = root.findViewById(R.id.admin_employee_details_bills_expandable_layout);


        info.setOnClickListener(this);
        bills.setOnClickListener(this);


        employeeName.setText(employee.getName());
        storeName.setText(employee.getStoreName());
        test();

    }

    private void test() {
        databaseReferenceIncomes = FirebaseDatabase.getInstance().getReference().child("Incomes");

        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH));
        String date = String.valueOf(calendar.get(Calendar.DATE));
        String time = null;
        listItems.add("cutting");
        listItems.add("shaving");
        listItemPrices.add(120);
        listItemPrices.add(40);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH - MM - SS");

            time = calendar.getTime().toString();
        }
        String key = databaseReferenceIncomes.push().getKey();

        databaseReferenceIncomes.child(year).child(month).child(date).child(key).setValue(new Bill(key, employee.getAreaId(), employee.getStoreId(), "storeName", employee.getId(), "employeeName", date, time, listItems, listItemPrices, 160));

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
