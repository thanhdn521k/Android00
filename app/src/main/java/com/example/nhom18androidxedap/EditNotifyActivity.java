package com.example.nhom18androidxedap;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Notify;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditNotifyActivity extends AppCompatActivity {

    private Button btnUpdate,btnEdit;
    private EditText edtTitle,edtDetail;
    private TextView tvTime;
    private DatabaseHelper databaseHelper;
    private Notify mNotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notify);

        initUI();
        initListener();
    }

    private void initUI(){
        btnUpdate = findViewById(R.id.btnUpdateNotify);
        btnEdit = findViewById(R.id.btn_edit_notify);
        edtTitle = findViewById(R.id.editText_edit_title);
        tvTime = findViewById(R.id.textView_edit_time);
        edtDetail = findViewById(R.id.editText_edit_detail);
        databaseHelper = new DatabaseHelper(EditNotifyActivity.this);
        mNotify = (Notify) getIntent().getExtras().get("notify");
        if(mNotify!=null){
            edtTitle.setText(mNotify.getTitle());
            tvTime.setText(mNotify.getTime());
            edtDetail.setText(mNotify.getDetail());
        }
        edtTitle.setEnabled(false);
        tvTime.setEnabled(false);
        edtDetail.setEnabled(false);
    }

    private void initListener(){
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
    }

    private void onClickEditNotify() {
        edtTitle.setEnabled(true);
        edtDetail.setEnabled(true);
    }

    private void onClickUpdateNotify() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strTitle = edtTitle.getText().toString().trim();
        String strTime = simpleDateFormat.format(calendar.getTime());
        String strDetail = edtDetail.getText().toString().trim();
        if(strTitle.isEmpty()){
            edtTitle.setError("Tiêu đề không được để trống!");
            edtTitle.requestFocus();
        }else if(strDetail.isEmpty()){
            edtDetail.setError("Nội dung không được để trống!");
            edtDetail.requestFocus();
        }else{
            mNotify.setTitle(strTitle);
            mNotify.setTime(strTime);
            mNotify.setDetail(strDetail);
            boolean check = databaseHelper.updateNotify(mNotify);
            if(check){
                new AlertDialog.Builder(this)
                        .setTitle("Sửa thông báo").setMessage("Cập nhật thông báo này thành công!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                setResult(RESULT_OK,null);
                                finish();
                            }
                        }).show();
            }else{
                new AlertDialog.Builder(this)
                        .setTitle("Sửa thông báo").setMessage("Cập nhật thông báo này thất bại!")
                        .setNegativeButton("OK",null).show();

                Reset();
            }

        }

    }


    protected  void Reset() {
        edtTitle.setText(mNotify.getTitle());
        tvTime.setText(mNotify.getTime());
        edtDetail.setText(mNotify.getDetail());
    }
}