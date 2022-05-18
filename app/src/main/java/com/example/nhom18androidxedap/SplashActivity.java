package com.example.nhom18androidxedap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Users;

public class SplashActivity extends AppCompatActivity {

    private Handler handler;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        databaseHelper = new DatabaseHelper(this);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        },2000);
    }

    private void nextActivity() {
        SharedPreferences sharedPreferences = getSharedPreferences("configure",MODE_PRIVATE);
        int userID = sharedPreferences.getInt("id",-1);
        Users user = databaseHelper.getUserById(userID);
        Intent intent;
        if(userID==0||user==null){
            intent = new Intent(SplashActivity.this, SignInActivity.class);
        }else{
            if(user.getAuthority().equals("Customer")){
                intent = new Intent(SplashActivity.this, MapsActivity.class);
                String username = user.getUsername();
                Log.d("username",username);
                intent.putExtra("username",username);
            }else{
                intent = new Intent(this, AdminActivity.class);
            }
        }
        startActivity(intent);
        finish();
    }
}