package com.example.nhom18androidxedap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Bicycle;

import java.util.ArrayList;

public class DetailBillCustomer extends AppCompatActivity {

    TextView tvCodeBill,tvFullname,tvStart,tvEnd,tvTotal;
    ListView lvIdBicycle;
    Button btnBack,btnTraXe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail_customer);

        tvCodeBill = findViewById(R.id.codeBill);
        tvFullname = findViewById(R.id.username);
        tvStart = findViewById(R.id.start_at);
        tvEnd = findViewById(R.id.end_at);
        tvTotal = findViewById(R.id.total);
        btnBack = findViewById(R.id.btnBack);
        btnTraXe = findViewById(R.id.btnTraXe);
        lvIdBicycle = findViewById(R.id.lvIdBicycle);

        Intent intent = this.getIntent();
        int id = intent.getIntExtra("id", 0);
        String username = intent.getStringExtra("username");
        String code = intent.getStringExtra("code");
        String name_KH = intent.getStringExtra("nameKH");
//        String maXe = intent.getStringExtra("maXe");
        ArrayList<Bicycle> listBicycle1 = (ArrayList<Bicycle>) intent.getSerializableExtra("listBicycle");
        String gioThue = intent.getStringExtra("gioThue");
        String gioTra = intent.getStringExtra("gioTra");
        int total = intent.getIntExtra("total", 0);
        String gia = total + "";

        tvCodeBill.setText(code);
        tvFullname.setText(name_KH);
        tvStart.setText(gioThue);
        tvEnd.setText(gioTra);
        tvTotal.setText(gia);

        ArrayList<String> listBicycleCode = new ArrayList<>();
        for (int i = 0; i < listBicycle1.size(); i++){
            listBicycleCode.add(listBicycle1.get(i).getCode());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(DetailBillCustomer.this, android.R.layout.simple_list_item_1, listBicycleCode);
        lvIdBicycle.setAdapter(arrayAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(DetailBillCustomer.this,MapsActivity.class);
                intent1.putExtra("username",username);
                startActivity(intent1);
            }
        });

        btnTraXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(DetailBillCustomer.this);
                databaseHelper.updateStatusBill2(code);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailBillCustomer.this);
                alertDialog.setTitle("Thông báo!");
                alertDialog.setMessage("Bạn đã trả xe thành công !");
                alertDialog.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent1 = new Intent(DetailBillCustomer.this,MapsActivity.class);
                        intent1.putExtra("username",username);
                        startActivity(intent1);
                    }
                });
                alertDialog.show();
            }
        });

    }


}