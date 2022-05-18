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

import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Users;


public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText edtFullName,edtEmail,edtUserName,edtNewPassword,edtConfirmNewPassword;
    private Button btnConfirm,btnBack;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initUI();
        initListener();
    }

    private void initUI() {
        edtFullName = findViewById(R.id.edtFullNameForgotPassword);
        edtEmail = findViewById(R.id.edtEmailForgotPassword);
        edtUserName = findViewById(R.id.edtUserNameForgotPassword);
        edtNewPassword = findViewById(R.id.edtNewPasswordForgotPassword);
        edtConfirmNewPassword = findViewById(R.id.edt_ReNewPassword_ForgotPassword);
        btnConfirm = findViewById(R.id.btnConfirmForgotPassword);
        btnBack = findViewById(R.id.btnBackForgotPassword);
        databaseHelper = new DatabaseHelper(this);

    }

    private void initListener() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickConfirm();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBack();
            }
        });
    }

    private void onClickConfirm() {
        String fullName = edtFullName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String userName = edtUserName.getText().toString().trim();
        String newPassword = edtNewPassword.getText().toString().trim();
        String confirmNewPassword = edtConfirmNewPassword.getText().toString().trim();
        Users user = databaseHelper.getUserByUserName(userName);
        if(fullName.isEmpty()){
            edtFullName.setError("Họ tên không được để trống!");
            edtFullName.requestFocus();
        }else if(email.isEmpty()){
            edtEmail.setError("Email không được để trống!");
            edtEmail.requestFocus();
        }else if(email.length()<11||!email.substring(email.length()-10,email.length()).equals("@gmail.com")){
            edtEmail.setError("Email không đúng định dạng!");
            edtEmail.requestFocus();
        }else if(userName.isEmpty()){
            edtUserName.setError("Tài khoản không được để trống!");
            edtUserName.requestFocus();
        }else if(newPassword.isEmpty()){
            edtNewPassword.setError("mật khẩu không được để trống!");
            edtNewPassword.requestFocus();
        }else if(confirmNewPassword.isEmpty()){
            edtConfirmNewPassword.setError("Nhập lại mật khẩu không được để trống");
            edtConfirmNewPassword.requestFocus();
        }else if(!confirmNewPassword.equals(newPassword)){
            edtConfirmNewPassword.setError("Nhập lại mật khẩu không đúng");
            edtConfirmNewPassword.requestFocus();
        }else{
            if(user == null){
                edtUserName.setError("Tài khoản này không tồn tại!");
                edtUserName.requestFocus();
            }else{
                String userFullName = user.getFullname();
                String userEmail = user.getGmail();
                if(!userFullName.equals(fullName)){
                    edtFullName.setError("Họ tên không đúng!");
                    edtFullName.requestFocus();
                }else if(!userEmail.equals(email)){
                    edtEmail.setError("Email không đúng!");
                    edtEmail.requestFocus();
                }else if(!confirmNewPassword.equals(newPassword)){
                    edtConfirmNewPassword.setError("Mật khẩu không trùng khớp!");
                    edtConfirmNewPassword.requestFocus();
                }else{
                    user.setPassword(newPassword);
                    boolean check = databaseHelper.updateUser(user);
                    if(check){
                        new AlertDialog.Builder(this)
                                .setTitle("Thay đổi mật khẩu").setMessage("Thay đổi mật khẩu mới thành công!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(ForgotPasswordActivity.this, MapsActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).show();
                    }else{
                        new AlertDialog.Builder(this)
                                .setTitle("Thay đổi mật khẩu").setMessage("Thay đổi mật khẩu mới thất bại!")
                                .setNegativeButton("OK",null).show();
                        reset();

                    }
                }
            }
        }
    }

    private  void reset(){
        edtFullName.setText("");
        edtEmail.setText("");
        edtUserName.setText("");
        edtNewPassword.setText("");
        edtConfirmNewPassword.setText("");
    }

    private void onClickBack() {
        Intent intent = new Intent(this,SignInActivity.class);
        startActivity(intent);
        finish();
    }
}