package com.example.universalyoga.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.universalyoga.databinding.ActivitySplashBinding;
import com.example.universalyoga.db.DBHandler;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;
    DBHandler dbHandler;
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();
    }

    public void init(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                String email = sharedpreferences.getString("email_key", "defaultValue");
//                Cursor cursor = dbHandler.getData();
                if (email == "defaultValue" || email == null){
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        }, 2000);
    }
}
