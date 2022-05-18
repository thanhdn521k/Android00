package com.example.nhom18androidxedap;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.nhom18androidxedap.adapterCustomer.NotifyAdapter;
import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Notify;

import java.util.ArrayList;
import java.util.List;

public class NotifyActivity extends AppCompatActivity {
    ListView lvThongBao;
    ArrayList<Notify> notify1ArrayList;
    NotifyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        DatabaseHelper dataBaseHelper = new DatabaseHelper(NotifyActivity.this);

        lvThongBao = findViewById(R.id.lv_thongbao);
        notify1ArrayList = new ArrayList<>();

        adapter = new NotifyAdapter(this,R.layout.dong_thong_bao,notify1ArrayList);
        lvThongBao.setAdapter(adapter);
        List<Notify> list = new ArrayList<Notify>();
        list = dataBaseHelper.getAllNotifyThanh();
        for(Notify s : list)
        {
            notify1ArrayList.add(s);
        }
    }
}