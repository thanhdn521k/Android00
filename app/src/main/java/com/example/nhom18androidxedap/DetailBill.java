package com.example.nhom18androidxedap;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Bicycle;

import java.util.ArrayList;

public class DetailBill extends AppCompatActivity {

    Button btnSave, btnBack;
    EditText txtmaHD, txtnameKH, txtgioThue, txtgioTra, txttotal;
    ListView listBill;
    ListView listBicycle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bill);

        btnBack = findViewById(R.id.buttonBack);
//        btnSave = findViewById(R.id.buttonSave);
        txtmaHD = findViewById(R.id.txtMaHoaDon);
        txtnameKH = findViewById(R.id.txtFullNameUser);
//        txtmaXe = findViewById(R.id.txtmaXeBill);
        txtgioThue = findViewById(R.id.txtGioThue);
        txtgioTra = findViewById(R.id.txtGioTra);
        txttotal = findViewById(R.id.txtTongTien);
        listBill = findViewById(R.id.listBill);
        listBicycle = findViewById(R.id.listBicycle);

        DatabaseHelper dataBaseHelper = new DatabaseHelper(DetailBill.this);

        Intent intent = this.getIntent();
        int id = intent.getIntExtra("id", 0);
        String code = intent.getStringExtra("code");
        String name_KH = intent.getStringExtra("nameKH");
//        String maXe = intent.getStringExtra("maXe");
        ArrayList<Bicycle> listBicycle1 = (ArrayList<Bicycle>) intent.getSerializableExtra("listBicycle");
        String gioThue = intent.getStringExtra("gioThue");
        String gioTra = intent.getStringExtra("gioTra");
        int total = intent.getIntExtra("total", 0);
        String gia = total + "";

        txtmaHD.setText(code);
        txtnameKH.setText(name_KH);
//        txtmaXe.setText(maXe);
        txtgioThue.setText(gioThue);
        txtgioTra.setText(gioTra);
        txttotal.setText(gia);

        txtmaHD.setEnabled(false);
        txtnameKH.setEnabled(false);
//        txtmaXe.setEnabled(false);
        txtgioThue.setEnabled(false);
        txtgioTra.setEnabled(false);
        txttotal.setEnabled(false);
        ArrayList<String> listBicycleCode = new ArrayList<>();
        for (int i = 0; i < listBicycle1.size(); i++){
            listBicycleCode.add(listBicycle1.get(i).getCode());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(DetailBill.this, android.R.layout.simple_list_item_1, listBicycleCode);
        listBicycle.setAdapter(arrayAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailBill.this, ListBill.class);
                startActivity(i);
            }
        });
//
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Bicycle bicycle = new Bicycle();
//                bicycle.setId(bicycle1.getId());
//                int idBike = bicycle.getId();
//                if (idBike > 0) {
//                    try {
//                        bicycle = new Bicycle(bicycle.getId(), txtMaXe.getText().toString(), txtLoaiXe.getText().toString(),
//                                txtNote.getText().toString(), txtTTXe.getText().toString(), Integer.parseInt(txtGia.getText().toString()), true);
//                        // Toast.makeText(DetailBike.this, bike.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                    catch (Exception e){
//                        // Toast.makeText(DetailBike.this, "Error!!!!!", Toast.LENGTH_SHORT).show();
//                        bicycle = new Bicycle(bicycle.getId(), null, null,
//                                null, null, 0, true);
//                    }
//                    boolean kq = dataBaseHelper.updateBike(bicycle);
//                    if (kq == true) Toast.makeText(DetailBike.this, "SUCCESSFULLY!!!!", Toast.LENGTH_SHORT).show();
//                    else Toast.makeText(DetailBike.this, "FAIL!!!!", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(DetailBike.this, MainActivity.class);
//                    startActivity(i);
//                }
//                else {
//                    try {
//                        bicycle = new Bicycle(-1, txtMaXe.getText().toString(), txtLoaiXe.getText().toString(),
//                                txtNote.getText().toString(), txtTTXe.getText().toString(), Integer.parseInt(txtGia.getText().toString()), true);
//                        // Toast.makeText(DetailBike.this, bike.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                    catch (Exception e){
//                        // Toast.makeText(DetailBike.this, "Error!!!!!", Toast.LENGTH_SHORT).show();
//                        bicycle = new Bicycle(-1, null, null,
//                                null, null, 0, true);
//                    }
//                    boolean kq = dataBaseHelper.addBike(bicycle);
//                    if (kq == true) Toast.makeText(DetailBike.this, "SUCCESSFULLY!!!!", Toast.LENGTH_SHORT).show();
//                    else Toast.makeText(DetailBike.this, "FAIL!!!!", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(DetailBike.this, MainActivity.class);
//                    startActivity(i);
//                }
//
//            }
//        });
    }
}
