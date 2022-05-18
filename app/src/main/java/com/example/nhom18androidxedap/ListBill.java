package com.example.nhom18androidxedap;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;


import com.example.nhom18androidxedap.adapterAdmin.AdapterBillAdmin;
import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Bill;

import java.io.Serializable;
import java.util.ArrayList;

public class ListBill extends AppCompatActivity {

    DatabaseHelper dataBaseHelper;
    ListView lvBill;
    //    ImageButton btnAdd;
    Button btnSearch;
    EditText txtSearch;
    ArrayList<Bill> list;
    ImageButton btnBackToMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bill);
        dataBaseHelper = new DatabaseHelper(ListBill.this);

        Intent intent = this.getIntent();
        lvBill = findViewById(R.id.listBill);
//        btnAdd = findViewById(R.id.btnAdd);
        btnSearch = findViewById(R.id.btnSearchBill);
        txtSearch = findViewById(R.id.txtSearchBill);
        btnBackToMain = findViewById(R.id.btn_notify_back_to_main);

        list = dataBaseHelper.getAllBill();

        String key1 = intent.getStringExtra("code");
        txtSearch.setText(key1);
//        if (txtSearch.equals("") || txtSearch == null){
//            list = dataBaseHelper.getAllBill();
//        }
//        else
            list = dataBaseHelper.getBillByKey(key1);

        AdapterBillAdmin adapterBill = new AdapterBillAdmin(ListBill.this, list);
        lvBill.setAdapter(adapterBill);
        registerForContextMenu(lvBill);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = txtSearch.getText().toString();
                list = dataBaseHelper.getBillByKey(key);
                AdapterBillAdmin adapterBill = new AdapterBillAdmin(ListBill.this, list);
                lvBill.setAdapter(adapterBill);
                registerForContextMenu(lvBill);
            }
        });
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ListBill.this, AdminActivity.class);
                startActivity(intent1);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu3, menu);
//        menu.setHeaderTitle()
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.detail){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int i = info.position;
            Bill bill = list.get(i);
            Intent intent = new Intent(ListBill.this, DetailBill.class);
            intent.putExtra("id", bill.getId());
            intent.putExtra("code", bill.getCode());
            intent.putExtra("nameKH", bill.getUsers().getFullname());
//            intent.putExtra("maXe", bill.getBicycle().getCode());
            intent.putExtra("listBicycle", (Serializable) bill.getBicycleList());
            intent.putExtra("gioThue", bill.getStart_at());
            intent.putExtra("gioTra", bill.getEnd_at());
            intent.putExtra("total", bill.getTotal());
            startActivity(intent);
            return true;
        }
        return false;
    }
}