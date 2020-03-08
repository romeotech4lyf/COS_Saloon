package com.tech4lyf.cossaloon.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.tech4lyf.cossaloon.AdminDashBoardFragments.AreasFragment;
import com.tech4lyf.cossaloon.AdminDashBoardFragments.DefaultFragment;
import com.tech4lyf.cossaloon.AdminDashBoardFragments.EmployeesFragment;
import com.tech4lyf.cossaloon.AdminDashBoardFragments.ServicesFragment;
import com.tech4lyf.cossaloon.AdminDashBoardFragments.StoresFragment;
import com.tech4lyf.cossaloon.AdminDetailsFragments.AreaDetailsFragment;
import com.tech4lyf.cossaloon.AdminDetailsFragments.EmployeeDetailsFragment;
import com.tech4lyf.cossaloon.AdminDetailsFragments.StoreDetailsFragment;
import com.tech4lyf.cossaloon.AdminManageFragments.AddEmployeeFragment;
import com.tech4lyf.cossaloon.AdminManageFragments.AddStoreFragment;
import com.tech4lyf.cossaloon.Context;
import com.tech4lyf.cossaloon.FormatData;
import com.tech4lyf.cossaloon.Listeners;
import com.tech4lyf.cossaloon.Models.Area;
import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.Models.Store;
import com.tech4lyf.cossaloon.R;
import com.tech4lyf.cossaloon.adapters.RecyclerViewAdapterEmployees;

import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity implements Listeners.OnClickDashBoardItemListener, Listeners.OnClickStoreListListener, Listeners.OnClickEmployeeListListener, Listeners.OnBackPressedListener, View.OnClickListener, Listeners.OnClickAreaListListener {

    private static final String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_MEDIA_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static int level = 0;
    public static Context.OBJECT_TYPE objectType = Context.OBJECT_TYPE.NULL;
    private TextView jobsToday;
    private TextView jobsThisMonth;
    private AppBarConfiguration mAppBarConfiguration;
    private RecyclerViewAdapterEmployees recyclerViewAdapterEmployees;

    private FragmentManager fragmentManager;
    private FloatingActionButton floatingActionButton;
    private String id;
    private Integer jobsTodayCount = 0;
    private Integer jobsThisMonthCount = 0;
    private DatabaseReference databaseReferenceJobsToday;
    private DatabaseReference databaseReferenceJobsThisMonth;
    private String currentDate;
    private String currentMonth;

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(final int stringId) {

        switch (stringId) {
            case R.string.stores:
                getSupportFragmentManager().beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new StoresFragment(), "STORES").commit();
                floatingActionButton.setImageResource(R.drawable.ic_add_black_24dp);
                floatingActionButton.setVisibility(View.VISIBLE);
                objectType = Context.OBJECT_TYPE.STORE;

                break;

            case R.string.services:
                getSupportFragmentManager().beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new ServicesFragment(), "SERVICE").commit();
                floatingActionButton.setImageResource(R.drawable.ic_add_black_24dp);
                floatingActionButton.setVisibility(View.VISIBLE);
                objectType = Context.OBJECT_TYPE.SERVICE;

                break;

            case R.string.employees:
                getSupportFragmentManager().beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new EmployeesFragment(), "EMPLOYEE").commit();
                floatingActionButton.setImageResource(R.drawable.ic_add_black_24dp);
                floatingActionButton.setVisibility(View.VISIBLE);
                objectType = Context.OBJECT_TYPE.EMPLOYEE;
                break;

            case R.string.areas:
                getSupportFragmentManager().beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new AreasFragment(), "AREA").commit();
                floatingActionButton.setImageResource(R.drawable.ic_add_black_24dp);
                floatingActionButton.setVisibility(View.VISIBLE);
                objectType = Context.OBJECT_TYPE.AREA;

            default:
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();
        setContentView(R.layout.activity_admin_home);
        Toolbar toolbar = findViewById(R.id.toolbar_admin);
        floatingActionButton = findViewById(R.id.admin_add_fab);
        jobsThisMonth = findViewById(R.id.dashBoard_month_jobs);
        jobsToday = findViewById(R.id.dashBoard_day_jobs);
        fragmentManager = getSupportFragmentManager();
        objectType = Context.OBJECT_TYPE.NULL;
        fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new DefaultFragment(), "DEFAULT").commit();
        FirebaseApp.initializeApp(this);
        setSupportActionBar(toolbar);
        floatingActionButton.setImageResource(R.mipmap.logout);


        currentDate = FormatData.getCurrentDeviceDate();
        currentMonth = FormatData.getCurrentDeviceMonth();

        databaseReferenceJobsToday = FirebaseDatabase.getInstance().getReference().child("Jobs").child("Today" + currentDate);
        databaseReferenceJobsThisMonth = FirebaseDatabase.getInstance().getReference().child("Jobs").child("ThisMonth" + currentMonth);

        fireBaseListener();


        floatingActionButton.setOnClickListener(this);
        Listeners.setOnClickDashBoardItemListener(this);
        Listeners.setOnClickEmployeeListListener(this);
        Listeners.setOnClickStoreListListener(this);
        Listeners.setOnClickAreaListListener(this);
        Listeners.setOnBackPressedListener(this);


    }

    private void fireBaseListener() {
        databaseReferenceJobsToday.child("Combined").addValueEventListener(new ValueEventListener() {
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
        databaseReferenceJobsThisMonth.child("Combined").addValueEventListener(new ValueEventListener() {
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

    void checkPermissions() {
        final TedPermission tedPermission = new TedPermission(getBaseContext());
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                tedPermission.setDeniedMessage("Permissions Needed");

            }
        };
        tedPermission.setPermissionListener(permissionListener).setDeniedMessage("Must Accept Permissions").setPermissions(permissions).check();

    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {

        AdminHomeActivity.level -= 1;

        if (AdminHomeActivity.level < 0)
            super.onBackPressed();
            //android.os.Process.killProcess(android.os.Process.myPid());
        else if ((AdminHomeActivity.level == 0)) {
            destroyFragments();
            fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new DefaultFragment(), "DEFAULT").commit();
            floatingActionButton.setImageResource(R.mipmap.logout);
            objectType = Context.OBJECT_TYPE.NULL;
        } else if (AdminHomeActivity.level == 1) {
            if (objectType != null) {
                floatingActionButton.setVisibility(View.VISIBLE);
                Listeners.triggerOnBackPressedListener(objectType);
            }
        }


    }


    void destroyFragments() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }


    @Override
    public void onBackPressed(Context.OBJECT_TYPE objectType) {
        destroyFragments();



        switch (objectType) {
            case EMPLOYEE:
                fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new EmployeesFragment(), "EMPLOYEES").commit();
                break;
            case STORE:
                fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new StoresFragment(), "STORES").commit();
                break;
            case AREA:
                fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new AreasFragment(), "AREAS").commit();
                break;
            case SERVICE:
                fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new ServicesFragment(), "SERVICES").commit();
                break;
            default:
                break;
        }

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(Store store) {
        objectType = Context.OBJECT_TYPE.STORE;
        fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new StoreDetailsFragment(store), "STORE DETAILS").commit();
        floatingActionButton.setVisibility(View.INVISIBLE);

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(Employee employee) {

        objectType = Context.OBJECT_TYPE.EMPLOYEE;
        fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new EmployeeDetailsFragment(employee), "EMPLOYEE DETAILS").commit();
        floatingActionButton.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.admin_add_fab:

                switch (level) {

                    case 1:
                        switch (objectType) {
                            case EMPLOYEE:
                                getSupportFragmentManager().beginTransaction().replace(R.id.admin_add_employees_fragment_container, new AddEmployeeFragment()).commit();
                                break;
                            case AREA:
                                getSupportFragmentManager().beginTransaction().replace(R.id.admin_add_employees_fragment_container, new AddEmployeeFragment()).commit();
                                break;
                            case STORE:
                                getSupportFragmentManager().beginTransaction().replace(R.id.admin_add_stores_fragment_container, new AddStoreFragment()).commit();
                                break;
                            case NULL:
                                startActivity(new Intent(AdminHomeActivity.this, LoginActivity.class).putExtra("isReturning", true));
                                this.finish();
                            default:
                                break;
                        }
                        break;

                    case 0:
                        startActivity(new Intent(AdminHomeActivity.this, LoginActivity.class).putExtra("isReturning", true));
                        this.finish();
                        break;

                    default:
                        break;


                }

            default:
                break;
        }

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(Area area) {
        objectType = Context.OBJECT_TYPE.AREA;
        fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new AreaDetailsFragment(area), "AREA DETAILS").commit();
        floatingActionButton.setVisibility(View.INVISIBLE);

    }
}

