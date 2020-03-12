package com.tech4lyf.cossaloon.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
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
import com.tech4lyf.cossaloon.AdminManageFragments.AddAreaFragment;
import com.tech4lyf.cossaloon.AdminManageFragments.AddEmployeeFragment;
import com.tech4lyf.cossaloon.AdminManageFragments.AddServiceFragment;
import com.tech4lyf.cossaloon.AdminManageFragments.AddStoreFragment;
import com.tech4lyf.cossaloon.ChangeOfStyle;
import com.tech4lyf.cossaloon.Context;
import com.tech4lyf.cossaloon.FormatData;
import com.tech4lyf.cossaloon.Listeners;
import com.tech4lyf.cossaloon.Models.Area;
import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.Models.Store;
import com.tech4lyf.cossaloon.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminHomeActivity extends AppCompatActivity implements Listeners.OnClickDashBoardItemListener, Listeners.OnClickStoreListListener, Listeners.OnClickEmployeeListListener, Listeners.OnBackPressedListener, View.OnClickListener, Listeners.OnClickAreaListListener {

    private static final String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_MEDIA_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static int level = 0;
    public static Context.OBJECT_TYPE objectType = Context.OBJECT_TYPE.NULL;
    private final int getDP = 1236;
    private TextView jobsToday;
    private TextView jobsThisMonth;
    private FragmentManager fragmentManager;
    private FloatingActionButton floatingActionButton;
    private Integer jobsTodayCount = 0;
    private Integer jobsThisMonthCount = 0;
    private DatabaseReference databaseReferenceJobsToday;
    private DatabaseReference databaseReferenceJobsThisMonth;
    private DatabaseReference databaseReferenceAdministrators;
    private String currentDate;
    private String currentMonth;
    private CircleImageView dasBoardDP;
    private CardView cardView1;
    private CardView cardView2;
    private FirebaseStorage storage;
    private StorageReference storageReferenceDP;
    private Uri dPUri;
    private String key;

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(final int stringId) {
        cardView1.setBackgroundColor(getResources().getColor(R.color.colorOffWhite));
        cardView2.setBackgroundColor(getResources().getColor(R.color.colorOffWhite));

        switch (stringId) {
            case R.string.stores:
                if (!getSupportFragmentManager().isDestroyed())
                    getSupportFragmentManager().beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new StoresFragment(), "STORES").commit();
                floatingActionButton.setImageResource(R.drawable.ic_add_black_24dp);
                floatingActionButton.setVisibility(View.VISIBLE);
                objectType = Context.OBJECT_TYPE.STORE;

                break;

            case R.string.services:
                if (!getSupportFragmentManager().isDestroyed())
                    getSupportFragmentManager().beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new ServicesFragment(), "SERVICE").commit();
                floatingActionButton.setImageResource(R.drawable.ic_add_black_24dp);
                floatingActionButton.setVisibility(View.VISIBLE);
                objectType = Context.OBJECT_TYPE.SERVICE;

                break;

            case R.string.employees:
                if (!getSupportFragmentManager().isDestroyed())
                    getSupportFragmentManager().beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new EmployeesFragment(), "EMPLOYEE").commit();
                floatingActionButton.setImageResource(R.drawable.ic_add_black_24dp);
                floatingActionButton.setVisibility(View.VISIBLE);
                objectType = Context.OBJECT_TYPE.EMPLOYEE;
                break;

            case R.string.areas:
                if (!getSupportFragmentManager().isDestroyed())
                    getSupportFragmentManager().beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new AreasFragment(), "AREA").commit();
                floatingActionButton.setImageResource(R.drawable.ic_add_black_24dp);
                floatingActionButton.setVisibility(View.VISIBLE);
                objectType = Context.OBJECT_TYPE.AREA;

            default:
                break;
        }

    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();
        setContentView(R.layout.activity_admin_home);
        Toolbar toolbar = findViewById(R.id.toolbar_admin);
        floatingActionButton = findViewById(R.id.admin_add_fab);
        jobsThisMonth = findViewById(R.id.dashBoard_month_jobs);
        jobsToday = findViewById(R.id.dashBoard_day_jobs);
        dasBoardDP = findViewById(R.id.dashBoard_profile_image);
        cardView1 = findViewById(R.id.admin_dash_board_dummy_card_view_one);
        cardView2 = findViewById(R.id.admin_dash_board_dummy_card_view_two);
        fragmentManager = getSupportFragmentManager();
        key = getIntent().getStringExtra("key");
        objectType = Context.OBJECT_TYPE.NULL;
        if (!fragmentManager.isDestroyed())
            fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new DefaultFragment(), "DEFAULT").commit();
        cardView1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        cardView2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        FirebaseApp.initializeApp(this);
        storage = FirebaseStorage.getInstance();
        storageReferenceDP = storage.getReference().child(key + "-dp");
        setSupportActionBar(toolbar);
        floatingActionButton.setImageResource(R.mipmap.logout);
        floatingActionButton.setVisibility(View.GONE);

        databaseReferenceAdministrators = FirebaseDatabase.getInstance().getReference().child("Administrators");
        databaseReferenceAdministrators.child(key).child("dPUriString").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String dP = dataSnapshot.getValue(String.class);
                    if (dP != null) {
                        Glide.with(ChangeOfStyle.getContext()).load(Uri.parse(dP)).into(dasBoardDP);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        currentDate = FormatData.getCurrentDeviceDate();
        currentMonth = FormatData.getCurrentDeviceMonth();

        databaseReferenceJobsToday = FirebaseDatabase.getInstance().getReference().child("Jobs").child("Today" + currentDate);
        databaseReferenceJobsThisMonth = FirebaseDatabase.getInstance().getReference().child("Jobs").child("ThisMonth" + currentMonth);

        fireBaseListener();


        floatingActionButton.setOnClickListener(this);
        dasBoardDP.setOnClickListener(this);
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
        else if ((AdminHomeActivity.level == 0)) {
            destroyFragments();
            if (!fragmentManager.isDestroyed())
                fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new DefaultFragment(), "DEFAULT").commit();
            floatingActionButton.setImageResource(R.mipmap.logout);
            floatingActionButton.setVisibility(View.GONE);
            objectType = Context.OBJECT_TYPE.NULL;
            cardView1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            cardView2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else if (AdminHomeActivity.level == 1) {
            if (objectType != null) {
                floatingActionButton.setVisibility(View.VISIBLE);
                Listeners.triggerOnBackPressedListener(objectType);
                cardView1.setBackgroundColor(getResources().getColor(R.color.colorOffWhite));
                cardView2.setBackgroundColor(getResources().getColor(R.color.colorOffWhite));
            }
        }


    }

    void destroyFragments() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (!getSupportFragmentManager().isDestroyed())
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    @Override
    public void onBackPressed(Context.OBJECT_TYPE objectType) {
        destroyFragments();

        switch (objectType) {
            case EMPLOYEE:
                if (!fragmentManager.isDestroyed())
                    fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new EmployeesFragment(), "EMPLOYEES").commit();
                break;
            case STORE:
                if (!fragmentManager.isDestroyed())
                    fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new StoresFragment(), "STORES").commit();
                break;
            case AREA:
                if (!fragmentManager.isDestroyed())
                    fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new AreasFragment(), "AREAS").commit();
                break;
            case SERVICE:
                if (!fragmentManager.isDestroyed())
                    fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new ServicesFragment(), "SERVICES").commit();
                break;
            default:
                break;
        }

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(Store store) {
        cardView1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        cardView2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        objectType = Context.OBJECT_TYPE.STORE;
        if (!fragmentManager.isDestroyed())
            fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new StoreDetailsFragment(store), "STORE DETAILS").commit();
        floatingActionButton.setVisibility(View.INVISIBLE);

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(Employee employee) {
        cardView1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        cardView2.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        objectType = Context.OBJECT_TYPE.EMPLOYEE;
        if (!fragmentManager.isDestroyed())
            fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new EmployeeDetailsFragment(employee), "EMPLOYEE DETAILS").commit();
        floatingActionButton.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.admin_add_fab:

                switch (level) {

                    case 1:
                        cardView1.setBackgroundColor(getResources().getColor(R.color.colorSeventyFivePercentBlack));
                        cardView2.setBackgroundColor(Color.parseColor("#00000000"));
                        switch (objectType) {
                            case EMPLOYEE:
                                if (!getSupportFragmentManager().isDestroyed())
                                    getSupportFragmentManager().beginTransaction().replace(R.id.admin_add_employees_fragment_container, new AddEmployeeFragment()).commit();
                                break;
                            case AREA:
                                if (!getSupportFragmentManager().isDestroyed())
                                    getSupportFragmentManager().beginTransaction().replace(R.id.admin_add_areas_fragment_container, new AddAreaFragment()).commit();
                                break;
                            case STORE:
                                if (!getSupportFragmentManager().isDestroyed())
                                    getSupportFragmentManager().beginTransaction().replace(R.id.admin_add_stores_fragment_container, new AddStoreFragment()).commit();
                                break;
                            case SERVICE:
                                if (!getSupportFragmentManager().isDestroyed())
                                    getSupportFragmentManager().beginTransaction().replace(R.id.admin_add_services_fragment_container, new AddServiceFragment()).commit();
                                break;
                            case NULL:
                                android.os.Process.killProcess(android.os.Process.myPid());
                            default:
                                break;
                        }
                        break;

                    case 0:
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;

                    default:
                        break;


                }
                break;

            case R.id.dashBoard_profile_image:
                startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), getDP);
                break;

            default:
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case getDP:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Uri uriImage = data.getData();
                        storageReferenceDP.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReferenceDP.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        if (uri != null) {
                                            dPUri = uri;
                                            databaseReferenceAdministrators.child(key).child("dPUriString").setValue(dPUri.toString());
                                            Glide.with(ChangeOfStyle.getContext()).load(dPUri).into(dasBoardDP);
                                        }

                                    }
                                });

                                Toast.makeText(ChangeOfStyle.getContext(), "DP Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ChangeOfStyle.getContext(), "DP Uploading Failed", Toast.LENGTH_SHORT).show();


                            }
                        });
                    }
                }
                break;
            default:
                break;
        }

    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(Area area) {
        cardView1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        cardView2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        objectType = Context.OBJECT_TYPE.AREA;
        if (!fragmentManager.isDestroyed())
            fragmentManager.beginTransaction().replace(R.id.dashBoard_admin_fragment_container, new AreaDetailsFragment(area), "AREA DETAILS").commit();
        floatingActionButton.setVisibility(View.INVISIBLE);

    }
}

