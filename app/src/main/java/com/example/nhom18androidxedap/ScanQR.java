package com.example.nhom18androidxedap;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Bicycle;
import com.example.nhom18androidxedap.model.Bill;
import com.example.nhom18androidxedap.model.Users;
import com.example.nhom18androidxedap.model.Voucher;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

public class ScanQR extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    Button btnFinishScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        List<String> codeBicycleList;
        List<Bicycle> bicycleList;
        List<Bicycle> bicycleList2;
        DatabaseHelper dataBaseHelper = new DatabaseHelper(ScanQR.this);
        Intent intent1 = getIntent();
        Bundle bundle = intent1.getBundleExtra("dulieu");
        String username = bundle.getString("username");
        String codeBill = bundle.getString("codeBill");
        Users user = (Users) bundle.getSerializable("user");
        Voucher voucher = (Voucher) bundle.getSerializable("voucher");
        int quantity = bundle.getInt("quanity");
        int total =  bundle.getInt("total");
        String string1 = bundle.getString("string1");
        String string2 = bundle.getString("string2");
        codeBicycleList = new ArrayList<>();
        bicycleList = new ArrayList<>();
        bicycleList2 = new ArrayList<>();
        CodeScannerView scannerView = findViewById(R.id.scanner_view_id_bicycle);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String codeBicycle = result.getText();
                        int count = 0;
                        Toast.makeText(ScanQR.this, codeBicycle, Toast.LENGTH_SHORT).show();
                        if(dataBaseHelper.isHaveCodeBicycle(codeBicycle)==1)
                        {
                            AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(ScanQR.this);
                            alertDialog1.setTitle("Thông báo!");
                            alertDialog1.setMessage("Bạn đã quét thành công !");
                            alertDialog1.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            alertDialog1.show();

                            codeBicycleList.add(codeBicycle); //add bicycle vao list
                            if(quantity == codeBicycleList.size())
                            {
                                for (int j=0;j<codeBicycleList.size();j++)
                                {
                                    Bicycle getBicycle = dataBaseHelper.getBicycle(codeBicycleList.get(j));
                                    if (getBicycle.isStatus() == false) {
                                        bicycleList.add(getBicycle);
                                    }else {
                                        bicycleList2.add(getBicycle);
                                    }
                                }

                                if(quantity == bicycleList.size())
                                {
                                    Bill bill = new Bill(1, codeBill, user, bicycleList, voucher, quantity, total, string1, string2, true);
                                    dataBaseHelper.addBill(bill);
                                    for (int j = 0; j < quantity; j++) {
                                        dataBaseHelper.addBillDetail(1, dataBaseHelper.getIdBill(codeBill), bicycleList.get(j).getId()); //getIdBicycle
                                    }
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ScanQR.this);
                                    alertDialog.setTitle("Thông báo!");
                                    alertDialog.setMessage("Bạn đã quét đủ số lượng !");
                                    alertDialog.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(ScanQR.this, SuccessPaymentActivity.class);
                                            intent.putExtra("username",username);
                                            intent.putExtra("codeBill",codeBill);
                                            startActivity(intent);
                                        }
                                    });
                                    alertDialog.show();
                                }else{
                                    List<String> listCodeBicycle2 = new ArrayList<>();
                                    for (int i=0;i<bicycleList2.size();i++)
                                    {
                                        String codeBicycle2 = bicycleList2.get(i).getCode();
                                        listCodeBicycle2.add(codeBicycle2);
                                    }

                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ScanQR.this);
                                    alertDialog.setTitle("Thông báo!");
                                    alertDialog.setMessage("Mã xe " + listCodeBicycle2 + " đang có người sử dụng vui lòng quét lại mã !!!");
                                    alertDialog.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(ScanQR.this, ScanQR.class);
                                            Bundle bundle1 = new Bundle();
                                            bundle1.putString("username",username);
                                            bundle1.putString("codeBill",codeBill);
                                            bundle1.putSerializable("user",user);
                                            bundle1.putSerializable("voucher",voucher);
                                            bundle1.putInt("quanity",quantity);
                                            bundle1.putInt("total",total);
                                            bundle1.putString("string1",string1);
                                            bundle1.putString("string2",string2);
                                            intent.putExtra("dulieu",bundle1);
                                            startActivity(intent);
                                        }
                                    });
                                    alertDialog.show();
                                }


                            }
                        }
                        else {
                            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(ScanQR.this);
                            alertDialog2.setTitle("Thông báo!");
                            alertDialog2.setMessage("Không tồn tại mã xe này vui lòng quét lại !");
                            alertDialog2.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
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