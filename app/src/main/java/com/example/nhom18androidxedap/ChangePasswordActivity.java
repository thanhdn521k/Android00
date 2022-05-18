package com.example.nhom18androidxedap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Users;

import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText edtNewPassword,edtConfirmNewPassword,edtOldPassword;
    private Button btnChangePassword,btnBackChangePassword;
    private DatabaseHelper databaseHelper;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initUI();
        initListener();
    }

    private void initUI(){
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmNewPassword = findViewById(R.id.edtConfirmNewPassword);
        edtOldPassword = findViewById(R.id.edtOldPassword);
        btnChangePassword = findViewById(R.id.btnChangePassWord);
        databaseHelper = new DatabaseHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("configure",MODE_PRIVATE);
        userID = sharedPreferences.getInt("id",-1);
    }

    private void initListener() {
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickChangePassword();
            }
        });
    }

    private void onclickChangePassword() {
        Users user = databaseHelper.getUserById(userID);
        String oldPassword = edtOldPassword.getText().toString().trim();
        String newPassword = edtNewPassword.getText().toString().trim();
        String confirmNewPassword = edtConfirmNewPassword.getText().toString().trim();


        if(oldPassword.isEmpty()){
            edtOldPassword.setError("Mật khẩu cũ không được để trống!");
            edtOldPassword.requestFocus();
        }
        else if(!oldPassword.equals(user.getPassword())){
            edtOldPassword.setError("Mật khẩu cũ không đúng!");
            edtOldPassword.requestFocus();
        }
        else if(newPassword.isEmpty()){
            edtNewPassword.setError("Mật khẩu mới không được để trống!");
            edtNewPassword.requestFocus();
        }else if(confirmNewPassword.isEmpty()){
            edtConfirmNewPassword.setError("Nhập lại mật khẩu không được để trống!");
            edtConfirmNewPassword.requestFocus();
        }else if(!confirmNewPassword.equals(newPassword)){
            edtConfirmNewPassword.setError("mật khẩu không trùng nhau!");
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
                                Intent intent = new Intent(ChangePasswordActivity.this, SignInActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).show();
            }else{
                new AlertDialog.Builder(this)
                        .setTitle("Thay đổi mật khẩu").setMessage("Thay đổi mật khẩu mới thất bại!")
                        .setNegativeButton("OK",null).show();
                edtNewPassword.setText("");
                edtConfirmNewPassword.setText("");
            }
        }

    }

}