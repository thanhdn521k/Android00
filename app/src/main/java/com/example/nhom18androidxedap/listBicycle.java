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
import android.widget.Toast;

import com.example.nhom18androidxedap.adapterAdmin.AdapterBikeAdmin;
import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Bicycle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class listBicycle extends AppCompatActivity {

    DatabaseHelper dataBaseHelper;
    FloatingActionButton btnAdd;
    ListView lvBike;
    //    ImageButton btnAdd;
    Button btnSearch;
    EditText txtSearch;
    ArrayList<Bicycle> list;
    ImageButton btnBackToMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bicycle);

        lvBike = findViewById(R.id.listBike);
        btnAdd = findViewById(R.id.btnAdd);
        btnSearch = findViewById(R.id.btnSearch);
        txtSearch = findViewById(R.id.txtSearch);
        btnBackToMain = findViewById(R.id.btn_notify_back_to_main);

        dataBaseHelper = new DatabaseHelper(listBicycle.this);
        list = dataBaseHelper.getAll();

        AdapterBikeAdmin adapterBike = new AdapterBikeAdmin(listBicycle.this, list);
        lvBike.setAdapter(adapterBike);
        registerForContextMenu(lvBike);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(listBicycle.this, DetailBike.class);
                startActivity(i);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = txtSearch.getText().toString();
                list = dataBaseHelper.getByKey(key);
                AdapterBikeAdmin adapterBike = new AdapterBikeAdmin(listBicycle.this, list);
                lvBike.setAdapter(adapterBike);
                registerForContextMenu(lvBike);
            }
        });

        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(listBicycle.this, AdminActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
//        menu.setHeaderTitle()
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.edit){

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int i = info.position;
            Bicycle bicycle = list.get(i);
            Intent intent = new Intent(listBicycle.this, DetailBike.class);
            intent.putExtra("id", bicycle.getId());
            intent.putExtra("code", bicycle.getCode());
//            intent.putExtra("type", bicycle.getType());
//            intent.putExtra("price", bicycle.getPrice());
            intent.putExtra("status", bicycle.getCondition());
            intent.putExtra("note", bicycle.getNote());
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.viewBill){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int i = info.position;
            Bicycle bicycle = list.get(i);
            Intent intent = new Intent(listBicycle.this, ListBill.class);
//            intent.putExtra("id", bicycle.getId());
            intent.putExtra("code", bicycle.getCode());
            startActivity(intent);
            return true;
        }
        else {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int i = info.position;
            Bicycle bicycle = list.get(i);
            if (dataBaseHelper.checkExistBill(bicycle.getId())) {
                Toast.makeText(this, "Không thể xoá!!!!", Toast.LENGTH_SHORT).show();
            }
            else {
                if (dataBaseHelper.deleteBike(bicycle.getId()) > 0) {
                    Toast.makeText(this, "SUCCESSFUlL", Toast.LENGTH_SHORT).show();
                }
                String key = txtSearch.getText().toString();
                list = dataBaseHelper.getByKey(key);
                AdapterBikeAdmin adapterBike = new AdapterBikeAdmin(listBicycle.this, list);
                lvBike.setAdapter(adapterBike);
                registerForContextMenu(lvBike);
            }
        }
        return true;
    }
}