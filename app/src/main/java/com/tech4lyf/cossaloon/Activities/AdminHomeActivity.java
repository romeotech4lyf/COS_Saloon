package com.tech4lyf.cossaloon.Activities;

import android.os.Bundle;
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
import com.tech4lyf.cossaloon.AdminDashBoardFragments.AreasFragment;
import com.tech4lyf.cossaloon.AdminDashBoardFragments.DefaultFragment;
import com.tech4lyf.cossaloon.AdminDashBoardFragments.EmployeesFragment;
import com.tech4lyf.cossaloon.AdminDashBoardFragments.ManageFragment;
import com.tech4lyf.cossaloon.AdminDashBoardFragments.StoresFragment;
import com.tech4lyf.cossaloon.AdminDetailsFragments.EmployeeDetailsFragment;
import com.tech4lyf.cossaloon.AdminDetailsFragments.StoreDetailsFragment;
import com.tech4lyf.cossaloon.Context;
import com.tech4lyf.cossaloon.Listeners;
import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.Models.Store;
import com.tech4lyf.cossaloon.R;
import com.tech4lyf.cossaloon.adapters.RecyclerViewAdapterEmployees;

public class AdminHomeActivity extends AppCompatActivity implements Listeners.OnClickDashBoardItemListener, Listeners.OnClickStoreListListener, Listeners.OnClickEmployeeListListener, Listeners.OnBackPressedListener {

    public static int level = 0;
    private AppBarConfiguration mAppBarConfiguration;
    private RecyclerViewAdapterEmployees recyclerViewAdapterEmployees;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private TextView textView;
    private FragmentManager fragmentManager;
    private String id;


    /*  databaseReference.orderByChild("date").addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              if (dataSnapshot != null) {
                  if (dataSnapshot.exists()) {
                      for (DataSnapshot bill_ : dataSnapshot.getChildren()) {
                          if (bill_ != null) {
                              Bill bill = bill_.getValue(Bill.class);
                              if (bill != null) {

                              }

                          }
                      }
                  }
              }

          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });*/
    private Context.OBJECT_TYPE objectType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Toolbar toolbar = findViewById(R.id.toolbar_admin);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new DefaultFragment(), "DEFAULT").commit();
        FirebaseApp.initializeApp(this);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Bills");
        setSupportActionBar(toolbar);
        Listeners.setOnClickDashBoardItemListener(this);
        Listeners.setOnClickEmployeeListListener(this);
        Listeners.setOnClickStoreListListener(this);
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

            case R.string.services:
                getSupportFragmentManager().beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new ManageFragment(), "MANAGE").commit();
                break;


            case R.string.employees:
                getSupportFragmentManager().beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new EmployeesFragment(), "EMPLOYEE").commit();
                break;

            case R.string.areas:
                getSupportFragmentManager().beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new AreasFragment(), "EMPLOYEE").commit();




            default:
                break;
        }

    }

    @Override
    public void onBackPressed() {

        AdminHomeActivity.level -= 1;

        if (AdminHomeActivity.level < 0)
            super.onBackPressed();
        else if ((AdminHomeActivity.level == 0)) {
            fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new DefaultFragment(), "DEFAULT").commit();
        } else if (AdminHomeActivity.level == 1) {
            if (objectType != null) {
                Listeners.triggerOnBackPressedListener(objectType);
            }
        }

    }


    @Override
    public void onBackPressed(Context.OBJECT_TYPE objectType) {

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

    @Override
    public void onClick(Store store) {
        this.objectType = Context.OBJECT_TYPE.STORE;

        fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new StoreDetailsFragment(store), "STORE DETAILS").commit();


    }

    @Override
    public void onClick(Employee employee) {

        this.objectType = Context.OBJECT_TYPE.EMPLOYEE;
        fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new EmployeeDetailsFragment(employee), "EMPLOYEE DETAILS").commit();

    }

}
