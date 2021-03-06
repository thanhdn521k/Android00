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
            edtFullName.setError("H??? t??n kh??ng ???????c ????? tr???ng!");
            edtFullName.requestFocus();
        }else if(email.isEmpty()){
            edtEmail.setError("Email kh??ng ???????c ????? tr???ng!");
            edtEmail.requestFocus();
        }else if(email.length()<11||!email.substring(email.length()-10,email.length()).equals("@gmail.com")){
            edtEmail.setError("Email kh??ng ????ng ?????nh d???ng!");
            edtEmail.requestFocus();
        }else if(userName.isEmpty()){
            edtUserName.setError("T??i kho???n kh??ng ???????c ????? tr???ng!");
            edtUserName.requestFocus();
        }else if(newPassword.isEmpty()){
            edtNewPassword.setError("m???t kh???u kh??ng ???????c ????? tr???ng!");
            edtNewPassword.requestFocus();
        }else if(confirmNewPassword.isEmpty()){
            edtConfirmNewPassword.setError("Nh???p l???i m???t kh???u kh??ng ???????c ????? tr???ng");
            edtConfirmNewPassword.requestFocus();
        }else if(!confirmNewPassword.equals(newPassword)){
            edtConfirmNewPassword.setError("Nh???p l???i m???t kh???u kh??ng ????ng");
            edtConfirmNewPassword.requestFocus();
        }else{
            if(user == null){
                edtUserName.setError("T??i kho???n n??y kh??ng t???n t???i!");
                edtUserName.requestFocus();
            }else{
                String userFullName = user.getFullname();
                String userEmail = user.getGmail();
                if(!userFullName.equals(fullName)){
                    edtFullName.setError("H??? t??n kh??ng ????ng!");
                    edtFullName.requestFocus();
                }else if(!userEmail.equals(email)){
                    edtEmail.setError("Email kh??ng ????ng!");
                    edtEmail.requestFocus();
                }else if(!confirmNewPassword.equals(newPassword)){
                    edtConfirmNewPassword.setError("M???t kh???u kh??ng tr??ng kh???p!");
                    edtConfirmNewPassword.requestFocus();
                }else{
                    user.setPassword(newPassword);
                    boolean check = databaseHelper.updateUser(user);
                    if(check){
                        new AlertDialog.Builder(this)
                                .setTitle("Thay ?????i m???t kh???u").setMessage("Thay ?????i m???t kh???u m???i th??nh c??ng!")
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
                                .setTitle("Thay ?????i m???t kh???u").setMessage("Thay ?????i m???t kh???u m???i th???t b???i!")
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