package com.example.nhom18androidxedap;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;


import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Voucher;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddVoucherActivity extends AppCompatActivity {

    private EditText edtCode,edtDiscount,edtMinBill;
    private Button btnAddNewVoucher;
    private TextView tvStartTime,tvEndTime;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_voucher);
        initUI();
        initListener();
    }

    private void initUI(){
        btnAddNewVoucher = findViewById(R.id.buttonAddNewVoucher);
        edtCode = findViewById(R.id.editTextCode);
        edtDiscount = findViewById(R.id.editTextDiscount);
        edtMinBill = findViewById(R.id.editTextMinBill);
        tvStartTime = findViewById(R.id.textViewStartTime);
        tvEndTime = findViewById(R.id.textViewEndTime);
        databaseHelper = new DatabaseHelper(AddVoucherActivity.this);
    }

    private void initListener(){

        tvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSetStartTime();
            }
        });

        tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSetEndTime();
            }
        });

        btnAddNewVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAddVoucher();
            }
        });
    }

    private void onClickSetStartTime() {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddVoucherActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i3, int i4) {
                        calendar.set(i,i1,i2,i3,i4);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                        tvStartTime.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },hour,minute,true);
                timePickerDialog.show();
            }
        },year,month,day);
        datePickerDialog.show();
    }

    private void onClickSetEndTime() {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddVoucherActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i3, int i4) {
                        calendar.set(i,i1,i2,i3,i4);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        tvEndTime.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },hour,minute,true);
                timePickerDialog.show();
            }
        },year,month,day);
        datePickerDialog.show();
    }

    private void onClickAddVoucher() {
        String code = edtCode.getText().toString().trim();
        String strDiscount = edtDiscount.getText().toString().trim();
        int discount = Integer.valueOf(strDiscount);
        String strMinBill = edtMinBill.getText().toString().trim();
        int minBill = Integer.valueOf(strMinBill);
        String startTime = tvStartTime.getText().toString().trim();
        String endTime = tvEndTime.getText().toString().trim();

        if((databaseHelper.confirmCodeVoucher(code)!=null)){
            edtCode.setError("m?? voucher n??y ???? t???n t???i!");
            edtCode.requestFocus();
        }
        else if(strDiscount.isEmpty()){
            edtDiscount.setError("khuy???n m??i kh??ng ???????c ????? tr???ng!");
            edtDiscount.requestFocus();
        }else if(!strDiscount.matches("\\d+")){
            edtDiscount.setError("Khuy???n m??i ph???i l?? ch??? s???!");
            edtDiscount.requestFocus();
        }else if(strMinBill.isEmpty()){
            edtMinBill.setError("????n t???i thi???u kh??ng ???????c ????? tr???ng!");
            edtMinBill.requestFocus();
        }else if(!strMinBill.matches("\\d+")){
            edtMinBill.setError("????n t???i thi???u ph???i l?? ch??? s???!");
            edtMinBill.requestFocus();
        }else if(startTime.isEmpty()){
            tvStartTime.setError("Th???i gian b???t ?????u kh??ng ???????c ????? tr???ng!");
            tvStartTime.requestFocus();
        }else if(endTime.isEmpty()){
            tvEndTime.setError("Th???i gian k???t th??c kh??ng ???????c ????? tr???ng!");
            tvEndTime.requestFocus();
        }else{
            Voucher newVoucher = new Voucher(1,code,discount,minBill,startTime,endTime);
            boolean check = databaseHelper.addVoucher(newVoucher);
            if(check){
                new AlertDialog.Builder(this)
                        .setTitle("Th??m voucher").setMessage("Th??m voucher m???i th??nh c??ng!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                setResult(RESULT_OK,null);
                                AddVoucherActivity.this.finish();
                            }
                        }).show();
            }else{
                new AlertDialog.Builder(this)
                        .setTitle("Th??m voucher").setMessage("Th??m voucher m???i th???t b???i!")
                        .setNegativeButton("OK",null).show();
                Reset();
            }

        }

    }

    protected  void Reset() {
        edtCode.setText("");
        edtDiscount.setText("");
        edtMinBill.setText("");
        tvEndTime.setText("");
        tvEndTime.setText("");
    }
}