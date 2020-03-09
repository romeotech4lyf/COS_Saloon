package com.tech4lyf.cossaloon.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.cossaloon.ChangeOfStyle;
import com.tech4lyf.cossaloon.FormatData;
import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.R;


public class EmployeeHomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Employee employee;
    private DatabaseReference databaseReferenceJobsToday;
    private DatabaseReference databaseReferenceJobsThisMonth;
    private Integer jobsTodayCount;
    private Integer jobsThisMonthCount;
    private TextView jobsToday;
    private TextView jobsThisMonth;
    private ImageView dashBoardDP;
    private String currentDate;
    private String currentMonth;
    private TextView dashBoardName;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        employee = (Employee) getIntent().getSerializableExtra("Employee");

        jobsThisMonth = findViewById(R.id.dashBoard_month_jobs);
        jobsToday = findViewById(R.id.dashBoard_day_jobs);
        bottomNavigationView = findViewById(R.id.dashBoard_employee_bottom_navigation_view);
        dashBoardDP = findViewById(R.id.employee_dashBoard_profile_image);
        dashBoardName = findViewById(R.id.employee_dashBoard_name);

        if (!getSupportFragmentManager().isDestroyed())
            getSupportFragmentManager().beginTransaction().replace(R.id.dashBoard_employee_fragment_container, new EmployeeDefaultFragment(employee), "EMPLOYEE BILL").commit();

        currentDate = FormatData.getCurrentDeviceDate();
        currentMonth = FormatData.getCurrentDeviceMonth();

        databaseReferenceJobsToday = FirebaseDatabase.getInstance().getReference().child("Jobs").child("Today" + currentDate);
        databaseReferenceJobsThisMonth = FirebaseDatabase.getInstance().getReference().child("Jobs").child("ThisMonth" + currentMonth);

        fireBaseListener();

        dashBoardName.setText(employee.getName());

        if (employee.getdPUriString() != null)
            Glide.with(ChangeOfStyle.getContext()).load(Uri.parse(employee.getdPUriString())).into(dashBoardDP);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }


    private void fireBaseListener() {
        databaseReferenceJobsToday.child(employee.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    jobsTodayCount = dataSnapshot.getValue(Integer.class);

                } else
                    jobsTodayCount = 0;

                jobsToday.setText(String.valueOf(jobsTodayCount));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReferenceJobsThisMonth.child(employee.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    jobsThisMonthCount = dataSnapshot.getValue(Integer.class);

                } else
                    jobsThisMonthCount = 0;
                jobsThisMonth.setText(String.valueOf(jobsThisMonthCount));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.employee_menu_info:
                if (!getSupportFragmentManager().isDestroyed())
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.dashBoard_employee_fragment_container, new EmployeeInfoFragment(employee), "EMPLOYEE INFO").commit();
                break;

            case R.id.employee_menu_home:
                if (!getSupportFragmentManager().isDestroyed())
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.dashBoard_employee_fragment_container, new EmployeeDefaultFragment(employee), "EMPLOYEE BILL").commit();

                break;

            default:
                break;
        }

        return true;
    }
}
