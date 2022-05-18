package com.example.nhom18androidxedap;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Notify;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddNotifyActivity extends AppCompatActivity {

    private Button btnAddNotify;
    private EditText edtTitle,edtDetail;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notify);

        initUI();
        initListener();
    }

    private void initUI(){
        btnAddNotify = findViewById(R.id.btn_add_notify);
        edtTitle = findViewById(R.id.editText_title);
        edtDetail = findViewById(R.id.editText_detail);
        databaseHelper = new DatabaseHelper(AddNotifyActivity.this);
    }

    private void initListener(){
        btnAddNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAddNotify();
            }
        });
    }

    private void onClickAddNotify() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(calendar.getTime());
        String title = edtTitle.getText().toString().trim();
        String detail = edtDetail.getText().toString().trim();
        if(title.isEmpty()){
            edtTitle.setError("Tiêu đề không được để trống!");
            edtTitle.requestFocus();
        }else if(detail.isEmpty()){
            edtTitle.setError("Nội dung không được để trống!");
            edtTitle.requestFocus();
        }else{
            Notify newNotify = new Notify(1,title,detail,time);
            boolean check = databaseHelper.addNotify(newNotify);
            if(check){
                new AlertDialog.Builder(this)
                        .setTitle("Thêm thông báo").setMessage("Thêm thông báo mới thành công!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                setResult(RESULT_OK,null);
                                finish();
                            }
                        }).show();
            }else{
                new AlertDialog.Builder(this)
                        .setTitle("Thêm thông báo").setMessage("Thêm thông báo mới thất bại!")
                        .setNegativeButton("OK",null).show();
                Reset();
            }

        }

    }

    protected  void Reset() {
        edtTitle.setText("");
        edtDetail.setText("");
    }

}