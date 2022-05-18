package com.example.nhom18androidxedap;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SuccessReportActivity extends AppCompatActivity {

    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_report);

        btnBack = (Button) findViewById(R.id.btn_back);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessReportActivity.this,MapsActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
    }
}