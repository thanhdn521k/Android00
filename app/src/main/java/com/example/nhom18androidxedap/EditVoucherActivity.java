package com.example.nhom18androidxedap;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.TimePicker;


import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Voucher;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditVoucherActivity extends AppCompatActivity {

    private Button btnUpdate,btnEdit;
    private EditText edtCode,edtDiscount,edtMinBill;
    private TextView tvStartTime,tvEndTime;
    private DatabaseHelper databaseHelper;
    private Voucher mVoucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_voucher);

        initUI();
        initListener();
    }

    private void initUI() {
        btnUpdate = findViewById(R.id.btnUpdateVoucher);
        btnEdit = findViewById(R.id.btn_edit_voucher);
        edtCode = findViewById(R.id.editText_edit_code);
        edtDiscount = findViewById(R.id.editText_edit_discount);
        edtMinBill = findViewById(R.id.editText_edit_minBill);
        tvStartTime = findViewById(R.id.textView_edit_startTime);
        tvEndTime = findViewById(R.id.textView_edit_endTime);
        databaseHelper = new DatabaseHelper(EditVoucherActivity.this);
        mVoucher = (Voucher) getIntent().getExtras().get("voucher");
        if(mVoucher!=null){
            edtCode.setText(mVoucher.getCode());
            edtDiscount.setText(String.valueOf(mVoucher.getDiscount()));
            edtMinBill.setText(String.valueOf(mVoucher.getMin_bill()));
            tvStartTime.setText(mVoucher.getStart_at());
            tvEndTime.setText(mVoucher.getEnd_at());
        }
        edtCode.setEnabled(false);
        edtDiscount.setEnabled(false);
        edtMinBill.setEnabled(false);
        tvStartTime.setEnabled(false);
        tvEndTime.setEnabled(false);
    }

    private void initListener() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUpdateNotify();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEditNotify();
            }
        });
        tvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(tvStartTime);
            }
        });

        tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(tvEndTime);
            }
        });
    }

    private void onClickEditNotify() {
        edtCode.setEnabled(true);
        edtDiscount.setEnabled(true);
        edtMinBill.setEnabled(true);
        tvStartTime.setEnabled(true);
        tvEndTime.setEnabled(true);
    }

    private void onClickUpdateNotify() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strCode = edtCode.getText().toString().trim();
        String strDiscount = edtDiscount.getText().toString().trim();
        int discount = Integer.valueOf(strDiscount);
        String strMinBill = edtMinBill.getText().toString().trim();
        int minBill = Integer.valueOf(strMinBill);
        String startTime = tvStartTime.getText().toString().trim();
        String endTime = tvEndTime.getText().toString().trim();
//        if(strCode.isEmpty()){
//            edtCode.setError("Mã voucher không được để trống!");
//            edtCode.requestFocus();
//        }else
        if(strDiscount.isEmpty()){
            edtDiscount.setError("Khuyến mãi không được để trống!");
            edtDiscount.requestFocus();
        }else if(!strDiscount.matches("\\d+")){
            edtDiscount.setError("Khuyến mãi phải là chữ số!");
            edtDiscount.requestFocus();
        }else if(strMinBill.isEmpty()){
            edtMinBill.setError("Đơn tối thiểu không được để trống!");
            edtMinBill.requestFocus();
        }else if(!strMinBill.matches("\\d+")){
            edtMinBill.setError("Đơn tối thiểu phải là chữ số!");
            edtMinBill.requestFocus();
        }else if(startTime.isEmpty()){
            tvStartTime.setError("Thời gian bắt đầu không được để trống!");
            tvStartTime.requestFocus();
        }else if(endTime.isEmpty()){
            tvEndTime.setError("Thời gian kết thúc không được để trống!");
            tvEndTime.requestFocus();
        }else{
            mVoucher.setCode(strCode);
            mVoucher.setDiscount(discount);
            mVoucher.setMin_bill(minBill);
            mVoucher.setStart_at(startTime);
            mVoucher.setEnd_at(endTime);
            boolean check = databaseHelper.updateVoucher(mVoucher);
            if(check){
                new AlertDialog.Builder(this)
                        .setTitle("Sửa voucher").setMessage("Sửa voucher thành công!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                setResult(RESULT_OK,null);
                                finish();
                            }
                        }).show();
            }else{
                new AlertDialog.Builder(this)
                        .setTitle("Sửa voucher").setMessage("Sửa voucher thất bại!")
                        .setNegativeButton("OK",null).show();
                Reset();
            }

        }

    }

    protected  void Reset() {
        edtCode.setText(mVoucher.getCode());
        edtDiscount.setText(String.valueOf(mVoucher.getDiscount()));
        edtMinBill.setText(String.valueOf(mVoucher.getMin_bill()));
        tvStartTime.setText(mVoucher.getStart_at());
        tvEndTime.setText(mVoucher.getEnd_at());
    }



    private void setTime(TextView tv){
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(EditVoucherActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i3, int i4) {
                        calendar.set(i,i1,i2,i3,i4);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        tv.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },hour,minute,true);
                timePickerDialog.show();
            }
        },year,month,day);
        datePickerDialog.show();
    }
}