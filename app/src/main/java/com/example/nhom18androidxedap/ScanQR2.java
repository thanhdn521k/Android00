package com.example.nhom18androidxedap;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Bicycle;
import com.example.nhom18androidxedap.model.Bill;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ScanQR2 extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    DatabaseHelper dataBaseHelper = new DatabaseHelper(ScanQR2.this);
    List<String> codeBicycleList;
    List<Bicycle> bicycleList;
    List<Bicycle> bicycleList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr2);
        Intent intent1 = getIntent();
        String username = intent1.getStringExtra("username");
        int idUser = dataBaseHelper.getIdUser(username);
        String codeBill = dataBaseHelper.getCodeBill(idUser);
        int quantity = dataBaseHelper.getQuantityFromBill(codeBill);
        codeBicycleList = new ArrayList<>();
        bicycleList = new ArrayList<>();
        bicycleList2 = new ArrayList<>();
        CodeScannerView scannerView = findViewById(R.id.scanner_view_id_bicycle_2);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    String codeBicycle = result.getText();
                    int count = 0;
                    int idBill = dataBaseHelper.getIdBill(codeBill);
                    List<Bicycle> idBicycleList = new ArrayList<>();
                    List<String>  codeList = new ArrayList<>();
                    idBicycleList = dataBaseHelper.getListBicycleByBill(idBill);
                    Log.d("CODELIST2",idBicycleList.toString());
                    for (int i = 0 ; i<idBicycleList.size();i++)
                    {
                        String code = dataBaseHelper.getCodeBicycleByIdBicycle(idBicycleList.get(i).getId());
                        codeList.add(code);
                    }
                    Log.d("codeList",codeList.toString());
                    Toast.makeText(ScanQR2.this, codeBicycle, Toast.LENGTH_SHORT).show();
                        if(dataBaseHelper.isHaveCodeBicycle(codeBicycle)==1)
                        {
                            AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(ScanQR2.this);
                            alertDialog1.setTitle("Th??ng b??o!");
                            alertDialog1.setMessage("B???n ???? qu??t th??nh c??ng !");
                            alertDialog1.setNegativeButton("????ng", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            alertDialog1.show();

                            codeBicycleList.add(codeBicycle); //add bicycle vao list
                            Collections.sort(codeBicycleList);
                            Collections.sort(codeList);
                            Boolean bool = codeList.equals(codeBicycleList);
                            if(quantity == codeBicycleList.size())
                            {
                                if(bool == true)
                                {
                                    AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(ScanQR2.this);
                                    alertDialog2.setTitle("Th??ng b??o!");
                                    alertDialog2.setMessage("B???n ???? qu??t ????? s??? l?????ng !");
                                    alertDialog2.setPositiveButton("????ng", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(ScanQR2.this,MapsActivity.class);
                                            intent.putExtra("username",username);
                                            intent.putExtra("codeBill",codeBill);
                                            startActivity(intent);
                                        }
                                    });
                                    alertDialog2.show();
                                }else
                                {
                                    AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(ScanQR2.this);
                                    alertDialog2.setTitle("Th??ng b??o!");
                                    alertDialog2.setMessage("M?? xe kh??ng kh???p vui l??ng ki???m tra l???i l???ch s??? mua v?? !");
                                    alertDialog2.setNegativeButton("????ng", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //???
                                            Intent intent = new Intent(ScanQR2.this,MapsActivity.class);
                                            intent.putExtra("username",username);
                                            intent.putExtra("codeBill",codeBill);
                                            startActivity(intent);
                                        }
                                    });
                                    alertDialog2.show();
                                }
                            }
                        }
                        else {
                            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(ScanQR2.this);
                            alertDialog2.setTitle("Th??ng b??o!");
                            alertDialog2.setMessage("Kh??ng t???n t???i m?? xe n??y vui l??ng qu??t l???i !");
                            alertDialog2.setNegativeButton("????ng", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            alertDialog2.show();
                        }
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }



}