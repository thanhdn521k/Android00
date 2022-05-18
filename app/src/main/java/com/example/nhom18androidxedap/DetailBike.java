package com.example.nhom18androidxedap;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Bicycle;

public class DetailBike extends AppCompatActivity {

    Button btnSave, btnCancel;
    EditText txtMaXe, txtTTXe, txtNote;
    ListView listBike;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bike);

        btnCancel = findViewById(R.id.buttonCancel);
        btnSave = findViewById(R.id.buttonSave);
        txtMaXe = findViewById(R.id.txtMaXe);
//        txtLoaiXe = findViewById(R.id.txtLoaiXe);
//        txtGia = findViewById(R.id.txtGia);
        txtTTXe = findViewById(R.id.txtTTXe);
        txtNote = findViewById(R.id.txtNote);
        listBike = findViewById(R.id.listBike);

//        txtGia.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (!b && txtGia.getText().toString() != null)
//                txtGia.setText(String.valueOf(Integer.parseInt(txtGia.getText().toString())));
//            }
//        });

        txtMaXe.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                txtMaXe.setText(txtMaXe.getText().toString().trim());
            }
        });

        txtTTXe.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                txtTTXe.setText(txtTTXe.getText().toString().trim());
            }
        });

        txtNote.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                txtNote.setText(txtNote.getText().toString().trim());
            }
        });

        DatabaseHelper dataBaseHelper = new DatabaseHelper(DetailBike.this);

        Intent intent = this.getIntent();
        int id = intent.getIntExtra("id", 0);
        String code = intent.getStringExtra("code");
//        String type = intent.getStringExtra("type");
//        int price = intent.getIntExtra("price", 0);
        String condition = intent.getStringExtra("condition");
        String note = intent.getStringExtra("note");
//        String gia = price + "";

        Bicycle bicycle1 = new Bicycle(id, code, note, condition, true);
        txtMaXe.setText(code);
//        txtLoaiXe.setText(type);
//        txtGia.setText(gia);
        txtTTXe.setText(condition);
        txtNote.setText(note);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailBike.this, listBicycle.class);
                startActivity(i);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bicycle bicycle = new Bicycle();
                String MX = txtMaXe.getText().toString().trim();
                if (MX.equals("") || MX.length() == 0){
                    Toast.makeText(DetailBike.this, "Không được bỏ trống mã xe", Toast.LENGTH_SHORT).show();
                    txtMaXe.setText(MX);
                }
                else {
                    bicycle.setId(bicycle1.getId());
                    int idBike = bicycle.getId();
                    if (idBike > 0) {
                        try {
                            bicycle = new Bicycle(bicycle.getId(), txtMaXe.getText().toString(),
                                    txtNote.getText().toString(), txtTTXe.getText().toString(), true);
                            // Toast.makeText(DetailBike.this, bike.toString(), Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            // Toast.makeText(DetailBike.this, "Error!!!!!", Toast.LENGTH_SHORT).show();
                            bicycle = new Bicycle(bicycle.getId(), null, null, null, true);
                        }
                        boolean kq = dataBaseHelper.updateBike(bicycle);
                        if (kq == true) Toast.makeText(DetailBike.this, "SUCCESSFULLY!!!!", Toast.LENGTH_SHORT).show();
                        else Toast.makeText(DetailBike.this, "FAIL!!!!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(DetailBike.this, listBicycle.class);
                        startActivity(i);
                    }
                    else {
                        try {
                            bicycle = new Bicycle(-1, txtMaXe.getText().toString(),
                                    txtNote.getText().toString(), txtTTXe.getText().toString(),true);
                            // Toast.makeText(DetailBike.this, bike.toString(), Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            // Toast.makeText(DetailBike.this, "Error!!!!!", Toast.LENGTH_SHORT).show();
                            bicycle = new Bicycle(-1, null, null, null, true);
                        }
                        boolean kq = dataBaseHelper.addBike(bicycle);
                        if (kq == true) Toast.makeText(DetailBike.this, "SUCCESSFULLY!!!!", Toast.LENGTH_SHORT).show();
                        else Toast.makeText(DetailBike.this, "FAIL!!!!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(DetailBike.this, listBicycle.class);
                        startActivity(i);
                    }
                }
            }
        });
    }
}
