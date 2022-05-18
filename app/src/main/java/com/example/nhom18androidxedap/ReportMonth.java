package com.example.nhom18androidxedap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;


import com.example.nhom18androidxedap.adapterAdmin.AdapterTke;
import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.TKe;

import java.util.ArrayList;

public class ReportMonth extends AppCompatActivity {

    ListView listTke;
    DatabaseHelper dataBaseHelper;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_month);

        listTke = findViewById(R.id.listTke);
        dataBaseHelper = new DatabaseHelper(ReportMonth.this);
        btnBack = findViewById(R.id.btn_notify_back_to_main);

        ArrayList<String> listMonth = dataBaseHelper.getMonth();

        ArrayList<TKe> listDoanhThu = new ArrayList<>();

        int sum = listMonth.size();
        for (int i = 0; i < sum; i++){
            listDoanhThu.add(dataBaseHelper.tke(listMonth.get(i)));
        }
        AdapterTke adapterTke = new AdapterTke(ReportMonth.this, listDoanhThu);
        listTke.setAdapter(adapterTke);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ReportMonth.this, AdminActivity.class);
                startActivity(intent1);
            }
        });
    }
}