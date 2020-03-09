package com.tech4lyf.cossaloon.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.cossaloon.Models.Administrator;
import com.tech4lyf.cossaloon.Models.Employee;
import com.tech4lyf.cossaloon.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    TextView signIn;
    TextInputEditText editPhoneNumber;
    TextInputEditText editPassword;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceAdminPhoneNumber;
    DatabaseReference databaseReferenceAdminPassword;
    DatabaseReference databaseReferenceEmployees;
    boolean loginStatus;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String dateToday;
    String previousDate;
    private Boolean isAdministrator;
    private String enteredPhoneNumber;
    private String enteredPassword;
    private String savedPhoneNumber;
    private String savedPassword;
    private Boolean isAdministratorPasswordCorrect;
    private ProgressDialog dialog;
    private DatabaseReference databaseReferenceAdministrators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //  startActivity(new Intent(LoginActivity.this, EmployeeHomeActivity.class));
        //  this.finish();




        FirebaseApp.initializeApp(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceAdministrators = firebaseDatabase.getReference().child("Administrators");
        databaseReferenceAdminPassword = firebaseDatabase.getReference().child("AdminPassword");
        databaseReferenceEmployees = firebaseDatabase.getReference().child("Employees");
        //  test();

        String key = databaseReferenceAdministrators.push().getKey();

        //      databaseReferenceAdministrators.child(key).setValue(new Administrator("8989898989","admin@123"));


        //
        editPhoneNumber = findViewById(R.id.edit_phone_number);
        editPassword = findViewById(R.id.edit_password);
        signIn = findViewById(R.id.sign_in);


        //
        sharedpreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();


        //
        previousDate = sharedpreferences.getString("lastLoginDate", null);
        dateToday = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());



            if (previousDate != null) {
                if (previousDate.equals(dateToday) || sharedpreferences.getString("isAdministrator", "false").equals("true")) {
                    savedPhoneNumber = sharedpreferences.getString("phoneNumber", null);
                    savedPassword = sharedpreferences.getString("password", null);
                    Log.d("date", dateToday);
                    if (savedPhoneNumber != null && savedPassword != null)
                        login(savedPhoneNumber, savedPassword);
                }

            }


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enteredPhoneNumber = String.valueOf(editPhoneNumber.getText());
                enteredPassword = editPassword.getText().toString();
                login(enteredPhoneNumber, enteredPassword);

            }
        });
    }

    void login(final String phoneNumber, final String password) {
        editor.putString("lastLoginDate", dateToday).apply();
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Logging you in");
        dialog.setCancelable(true);
        dialog.show();

        tryAdminLogin(phoneNumber, password);


    }

    private void tryAdminLogin(final String phoneNumber, final String password) {
        databaseReferenceAdministrators.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot administrator_ : dataSnapshot.getChildren()) {
                        Administrator administrator = administrator_.getValue(Administrator.class);
                        if (administrator != null) {
                            if (administrator.getPassword().equals(password) && administrator.getPhoneNumber().equals(phoneNumber)) {
                                dialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                                editor.putString("phoneNumber", phoneNumber).commit();
                                editor.putString("password", password).commit();
                                editor.putString("isAdministrator", "true").commit();
                                startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class).putExtra("key", administrator_.getKey()));
                                LoginActivity.this.finish();

                            }
                        }
                     }
                    tryEmployeeLogin(phoneNumber, password);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void tryEmployeeLogin(final String phoneNumber, final String password) {
        databaseReferenceEmployees.orderByChild("phoneNumber").equalTo(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot employee_ : dataSnapshot.getChildren()) {
                            Employee employee = employee_.getValue(Employee.class);
                            if (employee != null) {
                                if (employee.getPassword().equals(password)) {
                                    dialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Welcome " + employee.getName(), Toast.LENGTH_SHORT).show();
                                    editor.putString("phoneNumber", phoneNumber).commit();
                                    editor.putString("password", password).commit();
                                    editor.putString("isAdministrator", "false").commit();
                                    startActivity(new Intent(LoginActivity.this, EmployeeHomeActivity.class).putExtra("Employee", employee));
                                    LoginActivity.this.finish();


                                }
                            }
                        }

                        Toast.makeText(getApplicationContext(), "Wrong Password!", Toast.LENGTH_LONG).show();
                        dialog.cancel();

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

    }


}
