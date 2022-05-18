package com.example.nhom18androidxedap;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Bicycle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReportActivity2 extends AppCompatActivity {

    Button btnScan,btnFlashlight,btnError;
    int REQUEST_CODE_CAMERA = 123;
    EditText codeBicycle;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report2);
        DatabaseHelper dataBaseHelper = new DatabaseHelper(ReportActivity2.this);

        btnScan = (Button) findViewById(R.id.btnQr);
        btnFlashlight = (Button) findViewById(R.id.btnFlashlight);
        btnError = (Button) findViewById(R.id.btn_Error);
        codeBicycle = (EditText) findViewById(R.id.editText_idBicycle) ;


        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        int idUser = dataBaseHelper.getIdUser(username);
        String codeBill = dataBaseHelper.getCodeBill(idUser);
        int quantity = dataBaseHelper.getQuantityFromBill(codeBill);


        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportActivity2.this,ReportActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        btnError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0;
                count = dataBaseHelper.isHaveCodeBicycle(codeBicycle.getText().toString());
                int idBill = dataBaseHelper.getIdBill(codeBill);
                List<Bicycle> BicycleList = new ArrayList<>();
                List<String>  codeList = new ArrayList<>();
                BicycleList = dataBaseHelper.getListBicycleByBill(idBill);
                Log.d("CODELIST2",BicycleList.toString());
                for (int i = 0 ; i<BicycleList.size();i++)
                {
                    String code = dataBaseHelper.getCodeBicycleByIdBicycle(BicycleList.get(i).getId());
                    codeList.add(code);
                }

                if(count != 0 )
                {
                    if(codeList.contains(codeBicycle.getText().toString()))
                    {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ReportActivity2.this);
                        alertDialog.setTitle("Thông báo!");
                        alertDialog.setMessage("Bạn đã báo sự cố thành công !");
                        alertDialog.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent1 = new Intent(ReportActivity2.this,MapsActivity.class);
                                intent1.putExtra("username",username);
                                intent1.putExtra("codeBill",codeBill);
                                startActivity(intent1);
                            }
                        });
                        alertDialog.show();
                        dataBaseHelper.addReport(1,String.valueOf(dtf.format(now)),idUser,dataBaseHelper.getIdBicycle(codeBicycle.getText().toString()));
                    }else
                    {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ReportActivity2.this);
                        alertDialog.setTitle("Thông báo!");
                        alertDialog.setMessage("Mã xe không khớp vui lòng kiểm tra lại hóa đơn !");
                        alertDialog.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        alertDialog.show();
                    }
                }else
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ReportActivity2.this);
                    alertDialog.setTitle("Thông báo!");
                    alertDialog.setMessage("Không tồn tại mã xe này !");
                    alertDialog.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialog.show();
                }

            }
        });
    }



}