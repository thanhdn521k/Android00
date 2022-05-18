package com.example.nhom18androidxedap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Users;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtFullName,edtEmail,edtPassword,edtUserName,edtRePassword;
    private Button btnSignUp;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initUI();
        initListener();
    }

    private void initUI(){
        edtFullName = findViewById(R.id.edtFullNameSignUp);
        edtEmail = findViewById(R.id.edtEmailSignUp);
        edtPassword = findViewById(R.id.edtPasswordSignUp);
        edtUserName = findViewById(R.id.edtUserNameSignUp);
        edtRePassword = findViewById(R.id.edt_RePassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        databaseHelper = new DatabaseHelper(SignUpActivity.this);

    }

    private void initListener(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignUp();
            }
        });

    }

    private void onClickSignUp() {
        String strFullName = edtFullName.getText().toString().trim();
        String strEmail = edtEmail.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();
        String strUserName = edtUserName.getText().toString().trim();
        String strRePassword = edtRePassword.getText().toString().trim();

        if(strFullName.isEmpty()){
            edtFullName.setError("Họ tên không được để trống!");
            edtFullName.requestFocus();
        }else if(strEmail.isEmpty()){
            edtEmail.setError("Email không được để trống!");
            edtEmail.requestFocus();
        }else if(strEmail.length()<11||!strEmail.substring(strEmail.length()-10,strEmail.length()).equals("@gmail.com")){
            edtEmail.setError("Email không đúng định dạng!");
            edtEmail.requestFocus();
        }else if(strUserName.isEmpty()){
            edtUserName.setError("Tài khoản không được để trống!");
            edtUserName.requestFocus();
        }else if(databaseHelper.getUserByUserName(strUserName)!=null){
            edtUserName.setError("Tài khoản này đã tồn tại");
            edtUserName.requestFocus();
        }else if(strPassword.isEmpty()){
            edtPassword.setError("Mật khẩu không được để trống!");
            edtPassword.requestFocus();
        }else if(strRePassword.isEmpty()){
            edtRePassword.setError("Xác nhận mật khẩu không được để trống!");
            edtRePassword.requestFocus();
        }else if(!strRePassword.equals(strPassword)){
            edtRePassword.setError("Xác nhận mật khẩu không đúng!");
            edtRePassword.requestFocus();
        }else {
            Users user = new Users(1,strUserName,strPassword,"Customer",strFullName,strEmail);
            boolean check = databaseHelper.addUser(user);
            if(check){
                new AlertDialog.Builder(this)
                        .setTitle("Đăng ký").setMessage("Đăng ký thành công")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            }
                        }).show();
            }else{
                new AlertDialog.Builder(this)
                        .setTitle("Đăng ký").setMessage("Đăng ký thất bại!")
                        .setNegativeButton("OK",null).show();
            }
        }

    }
}