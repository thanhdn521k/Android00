package com.example.nhom18androidxedap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Users;

public class AddUserActivity extends AppCompatActivity {


    private Button btnSignUp;
    private EditText edtUsername,edtPassword,edtConfirmPassword;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        initUI();
        initListener();

    }

    private void initUI() {
        edtUsername = findViewById(R.id.edtUserNameAddUser);
        edtPassword = findViewById(R.id.edtPasswordAddUser);
        edtConfirmPassword = findViewById(R.id.edt_RePasswordAddUser);
        btnSignUp = findViewById(R.id.btnSignUpAddUser);
        databaseHelper = new DatabaseHelper(this);
    }

    private void initListener() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignUpUser();
            }
        });
    }

    private void onClickSignUpUser() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();
        Intent intent = getIntent();
        String status = intent.getStringExtra("status");
        if(username.isEmpty()){
            edtUsername.setError("Tài khoản không được để trống!");
            edtUsername.requestFocus();
        }else if(databaseHelper.getUserByUserName(username)!=null){
            edtUsername.setError("Tài khoản này đã tồn tại!");
            edtUsername.requestFocus();
        }else if(password.isEmpty()){
            edtPassword.setError("mật khẩu không được để trống!");
            edtPassword.requestFocus();
        }else if(confirmPassword.isEmpty()){
            edtConfirmPassword.setError("Xác nhận mật khẩu không được để trống");
            edtConfirmPassword.requestFocus();
        }else if(!confirmPassword.equals(password)){
            edtConfirmPassword.setError("Xác nhận mật khẩu không đúng");
            edtConfirmPassword.requestFocus();
        }else{
            Users newUser =  new Users(username,password,status);
            boolean check = databaseHelper.addUser(newUser);
            if(check){
                new AlertDialog.Builder(this)
                        .setTitle("Thêm tài khoản " + status).setMessage("Thêm tài khoản mới thành công!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                setResult(RESULT_OK,null);
                                finish();
                            }
                        }).show();
            }else{
                new AlertDialog.Builder(this)
                        .setTitle("Thêm tài khoản " + status).setMessage("Thêm tài khoản mới thất bại!")
                        .setNegativeButton("OK",null).show();
                Reset();
            }
        }
    }

    protected void Reset() {
        edtUsername.setText("");
        edtPassword.setText("");
        edtConfirmPassword.setText("");
    }



}