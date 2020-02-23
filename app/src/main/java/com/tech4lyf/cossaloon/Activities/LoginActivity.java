package com.tech4lyf.cossaloon.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.cossaloon.Models.User;
import com.tech4lyf.cossaloon.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    public static final String myPreferences = "Sessions";
    public static final String userName = "userName";
    public static final String key = "key";
    public static final String name = "name";
    public static final String isAdmin = "isAdmin";
    public static final String loginStatus_ = "loginStatus";
    public static final String passWord = "passWord";
    MaterialCardView btnLogin;
    TextInputEditText editUserName, editPassword;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    boolean loginStatus;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Date date;
    String date_;
    String prev_date_;
    private String userName_, passWord_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
        this.finish();

        FirebaseApp.initializeApp(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users");
      //  test();

        editUserName = findViewById(R.id.editUserName);
        editPassword = findViewById(R.id.editPassWord);

        btnLogin = findViewById(R.id.buttonLogin);

        sharedpreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();


        date = Calendar.getInstance().getTime();
        prev_date_ = sharedpreferences.getString("lastLoginDate", null);
        date_ = new SimpleDateFormat("dd-MM-yyyy").format(date);

        if (prev_date_ != null) {
            if (prev_date_.equals(date_)) {
                userName_ = sharedpreferences.getString(userName, null);
                passWord_ = sharedpreferences.getString(passWord, null);
                Log.d("date", date_);
                if (userName_ != null && passWord_ != null)
                    login(userName_, passWord_);
            }

        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                userName_ = editUserName.getText().toString();
                passWord_ = editPassword.getText().toString();


                login(userName_, passWord_);


            }
        });
    }

    private void test() {
        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValue(new User(key,"testAdmin","Admin@2020","Phoenix","true"));
    }


    boolean login(final String username, final String password) {
        editor.putString("lastLoginDate", date_).apply();
        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Logging you in");
        dialog.setCancelable(true);
        dialog.show();

        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loginStatus = false;
                if (dataSnapshot != null) {
                    if (dataSnapshot.exists()) {
                        // dataSnapshot is the "issue" node with all children with id 0
                        for (DataSnapshot user : dataSnapshot.getChildren()) {
                            // do something with the individual "issues"
                            User usersBean = user.getValue(User.class);
                            if (usersBean != null) {
                                if (usersBean.getPassword().equals(password)) {
                                    dialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "LoginActivity Success! for user " + usersBean.getName(), Toast.LENGTH_SHORT).show();
                                    editor.putString(userName, usersBean.getUsername());
                                    editor.putString(key, usersBean.getKey());
                                    editor.putString(name, usersBean.getName());
                                    editor.putString(isAdmin, usersBean.getIsAdmin());
                                    editor.putString(loginStatus_, "true");
                                    editor.putString(passWord, usersBean.getPassword());
                                    editor.commit();

                                    if (usersBean.getIsAdmin().equals("true")) {
                                        Intent actDashboard = new Intent(LoginActivity.this, AdminHomeActivity.class);
                                        startActivity(actDashboard);
                                    } else {
                                        Intent actDashboard = new Intent(LoginActivity.this, UserHomeActivity.class);
                                        startActivity(actDashboard);
                                    }


                                } else {
                                    Toast.makeText(getApplicationContext(), "Password is wrong", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "LoginActivity failed!", Toast.LENGTH_SHORT).show();
            }
        });
        return false;
    }


}
