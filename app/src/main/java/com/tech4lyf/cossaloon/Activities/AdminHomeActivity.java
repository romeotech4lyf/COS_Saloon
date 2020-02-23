package com.tech4lyf.cossaloon.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tech4lyf.cossaloon.AdminDashBoardFragments.DefaultFragment;
import com.tech4lyf.cossaloon.AdminDashBoardFragments.EmployeeDetailsFragment;
import com.tech4lyf.cossaloon.AdminDashBoardFragments.EmployeesFragment;
import com.tech4lyf.cossaloon.AdminDashBoardFragments.ManageFragment;
import com.tech4lyf.cossaloon.AdminDashBoardFragments.StoreDetailsFragment;
import com.tech4lyf.cossaloon.AdminDashBoardFragments.StoresFragment;
import com.tech4lyf.cossaloon.Context;
import com.tech4lyf.cossaloon.Listeners;
import com.tech4lyf.cossaloon.R;
import com.tech4lyf.cossaloon.adapters.RecyclerViewAdapterEmployees;

public class AdminHomeActivity extends AppCompatActivity implements Listeners.OnClickDashBoardItemListener, Listeners.OnClickRecyclerItemListener,Listeners.OnBackPressedListener {

    public static int level = 0;
    private AppBarConfiguration mAppBarConfiguration;
    private RecyclerViewAdapterEmployees recyclerViewAdapterEmployees;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private TextView textView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Toolbar toolbar = findViewById(R.id.toolbar_admin);
        textView = findViewById(R.id.text_home);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new DefaultFragment(), "DEFAULT").commit();
        textView.setText(getResources().getString(R.string.dashBoard));
        FirebaseApp.initializeApp(this);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Stores");
        setSupportActionBar(toolbar);
        Listeners.setOnClickDashBoardItemListener(this);
        Listeners.setOnClickRecyclerItemListener(this);
        Listeners.setOnBackPressedListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_home, menu);
        return true;
    }

    @Override
    public void onClick(final int stringId) {

        switch (stringId) {
            case R.string.stores:
                getSupportFragmentManager().beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new StoresFragment(), "STORES").commit();
                break;

            case R.string.administration:
                getSupportFragmentManager().beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new ManageFragment(), "MANAGE").commit();
                break;


            case R.string.employee:
                getSupportFragmentManager().beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new EmployeesFragment(), "EMPLOYEE").commit();
                break;


            default:
                break;
        }

    }
    private String id;
    private Context.OBJECT_TYPE objectType;


    @Override
    public void onBackPressed() {

        AdminHomeActivity.level -= 1;
        Log.d(String.valueOf(AdminHomeActivity.level), objectType.toString());

        if (AdminHomeActivity.level < 0)
            super.onBackPressed();
        else if ((AdminHomeActivity.level == 0)) {
            fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new DefaultFragment(), "DEFAULT").commit();
        } else if (AdminHomeActivity.level == 1) {
            Listeners.triggerOnBackPressedListener(objectType);
        }

    }

    @Override
    public void onClick(String id, Context.OBJECT_TYPE objectType) {
        this.id = id;
        this.objectType = objectType;
        Log.d(id,objectType.toString());

        switch (objectType) {
            case EMPLOYEE:
                fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new EmployeeDetailsFragment(id), "EMPLOYEE DETAILS").commit();
                break;
            case STORE:
                fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new StoreDetailsFragment(id), "STORE DETAILS").commit();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed (Context.OBJECT_TYPE objectType) {

        switch (objectType) {
            case EMPLOYEE:
                fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new EmployeesFragment(), "EMPLOYEES").commit();
                break;
            case STORE:
                fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new StoresFragment(), "STORES").commit();
                break;
            default:
                break;
        }


    }
}
