package com.example.nhom18androidxedap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Users;

public class SignInActivity extends AppCompatActivity {

    private LinearLayout layoutSignUp,layoutForgotPassword;
    private EditText edtUserName,edtPassword;
    private Button btnSignIn;
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initUI();
        initListener();
    }

    private void initUI(){
        layoutSignUp = findViewById(R.id.layout_sign_up);
        layoutForgotPassword = findViewById(R.id.layout_forgot_password);
        edtUserName = findViewById(R.id.edtUserNameSignIn);
        edtPassword = findViewById(R.id.edtPasswordSignIn);
        btnSignIn = findViewById(R.id.btnSignIn);
        databaseHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("configure",MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void initListener(){
        layoutSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);

            }
        });

        layoutForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignIn();
            }
        });
    }

    private void onClickSignIn() {
        String username = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if(username.isEmpty()){
            edtUserName.setError("Tài khoản không được để trống!");
            edtUserName.requestFocus();
        }else if(password.isEmpty()){
            edtPassword.setError("Mật khẩu không được để trống!");
            edtPassword.requestFocus();
        }else {
            Users user = databaseHelper.getUserByUserName(username);
            if(user == null){
                edtUserName.setError("Tài khoản không tồn tại.");
                edtUserName.requestFocus();
            }else {
                if(password.equals(user.getPassword())){
                    if(user.getAuthority().equals("Customer")){
                        Intent intent = new Intent(this, MapsActivity.class);
                        Log.d("username",username);
                        intent.putExtra("username",username);
                        editor.putInt("id",user.getId());
                        editor.commit();
                        startActivity(intent);
 //luc ma ong chuyen man` push username intent.putExtra("username",username);
                    }else{
                        editor.putInt("id",user.getId());
                        editor.commit();
                        Intent intent = new Intent(this, AdminActivity.class);
                        startActivity(intent);
                    }

                }else {
                    edtPassword.setError("Mật khẩu sai!");
                    edtPassword.requestFocus();
                }
            }
        }

    }
}