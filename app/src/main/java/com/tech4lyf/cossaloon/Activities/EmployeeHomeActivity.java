package com.tech4lyf.cossaloon.Activities;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.R;


public class EmployeeHomeActivity extends AppCompatActivity {

    private Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        employee = (Employee) getIntent().getSerializableExtra("Employee");

        getSupportFragmentManager().beginTransaction().replace(R.id.dashBoard_employee_fragment_container, new EmployeeDefaultFragment(employee), "EMPLOYEE BILL").commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_home, menu);
        return true;
    }


}
