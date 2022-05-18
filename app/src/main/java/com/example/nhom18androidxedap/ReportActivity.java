package com.example.nhom18androidxedap;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Bicycle;
import com.example.nhom18androidxedap.model.Notify;
import com.example.nhom18androidxedap.model.Users;
import com.example.nhom18androidxedap.model.Voucher;

import com.google.zxing.Result;

import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    Button btnKeyboard,btnFlashLight;
    private boolean Flash = true;
    private Camera camera;
    private Camera.Parameters parameters;
    Bicycle bicycle,bicycle2;
    Users user,user1,user2,user3;
    Voucher voucher,voucher3,voucher4;
    Notify notify3,notify2;
    List<String> codeBicycleList;
    List<Integer> listIdBicycle = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        DatabaseHelper dataBaseHelper = new DatabaseHelper(ReportActivity.this);
//        bicycle = new Bicycle(1,"456","dungduoc","note1",true);
//        bicycle2 = new Bicycle(1,"123","dungduoc","note1",true);
//        dataBaseHelper.addBicycle(bicycle2);
//        dataBaseHelper.addBicycle(bicycle);
//        user1 = new Users(1,"thanh1","thanh123","user","dinhnhuthanh","thanh@gmail.com");
//        user = new Users(1,"thanh","thanh123","user","dinhnhuthanh","thanh@gmail.com");
//        dataBaseHelper.addUser(user);
//        dataBaseHelper.addUser(user1);
//        user2 = new Users(1,"thanh2","thanh123","user","dinhnhuthanh","thanh@gmail.com");
//        dataBaseHelper.addUser(user2);
//        voucher = new Voucher(1,"",1,50000,"2022-04-30 02:00:00","2022-09-03 04:00:00");
//        voucher3 = new Voucher(1,"999",50000,100000,"2022-04-30 02:00:00","2022-09-03 04:00:00");
//        dataBaseHelper.addVoucher(voucher3);
//        dataBaseHelper.addVoucher(voucher);
//        notify3 = new Notify1(1,"title3","detail3","2022-05-05 12:05:00");
//        notify2 = new Notify1(1,"title2","detail2","2022-05-06 13:01:00");
//        dataBaseHelper.addNotify(notify2);
//        dataBaseHelper.addNotify(notify3);
//        user3 = new Users(1,"thanh3","thanh123","user","dinhnhuthanh","thanh@gmail.com");
//        dataBaseHelper.addUser(user3);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        int idUser = dataBaseHelper.getIdUser(username);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String codeBill = dataBaseHelper.getCodeBill(idUser);
        int quantity = dataBaseHelper.getQuantityFromBill(codeBill);
        codeBicycleList = new ArrayList<>();
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int count = 0;
                        String codeBicycle;
                        codeBicycle = result.getText();
                        count = dataBaseHelper.isHaveCodeBicycle(codeBicycle);
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
                            if(codeList.contains(codeBicycle))
                            {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ReportActivity.this);
                                alertDialog.setTitle("Thông báo!");
                                alertDialog.setMessage("Bạn đã báo sự cố thành công !");
                                alertDialog.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent1 = new Intent(ReportActivity.this,MapsActivity.class);
                                        intent1.putExtra("username",username);
                                        intent1.putExtra("codeBill",codeBill);
                                        startActivity(intent1);
                                    }
                                });
                                alertDialog.show();
                                dataBaseHelper.addReport(1,String.valueOf(dtf.format(now)),idUser,dataBaseHelper.getIdBicycle(codeBicycle));
                            }else
                            {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ReportActivity.this);
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
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ReportActivity.this);
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
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

        btnKeyboard = (Button) findViewById(R.id.keyBoard);
        btnFlashLight = (Button) findViewById(R.id.flashlight);

        btnKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportActivity.this,ReportActivity2.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        getCamera();

//        btnFlashLight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(Flash == true)
//                {
//                    turnOnFlash();
//                }else
//                {
//                    turnOffFlash();
//                }
//            }
//        });



    }



    private void getCamera()
    {
        if(camera == null && parameters == null)
        {
            camera = Camera.open();
            parameters = camera.getParameters();
        }
    }

//    private void turnOnFlash()
//    {
//        if(Flash == true)
//        {
//            parameters = camera.getParameters();
//            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
//            camera.setParameters(parameters);
//            camera.startPreview();
//            Flash = false;
//        }
//    }
//
//    private void turnOffFlash()
//    {
//        if(Flash == false)
//        {
//            parameters = camera.getParameters();
//            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
//            camera.setParameters(parameters);
//            camera.stopPreview();
//            Flash = true;
//        }
//    }




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