package com.example.nhom18androidxedap;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SuccessPaymentActivity extends AppCompatActivity {

    Button btnBack;
    TextView tvCodebill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_payment);

        btnBack = (Button) findViewById(R.id.btn_back1);
        tvCodebill = findViewById(R.id.tv_code_bill);

        Intent intent1 = getIntent();
        String codeBill = intent1.getStringExtra("codeBill");
        String username = intent1.getStringExtra("username");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessPaymentActivity.this,MapsActivity.class);
                intent.putExtra("username",username);
                intent.putExtra("codeBill",codeBill);
                startActivity(intent);
            }
        });

        tvCodebill.setText(codeBill);
    }
}