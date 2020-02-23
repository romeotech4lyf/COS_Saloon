package com.tech4lyf.cossaloon;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;

public class ChangeOfStyle extends Application {

    public static android.content.Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        context = getApplicationContext();

    }

    public static Context getContext(){
        return context;
    }
}
