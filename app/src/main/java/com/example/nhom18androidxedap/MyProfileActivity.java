package com.example.nhom18androidxedap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Users;

public class MyProfileActivity extends AppCompatActivity {

    private EditText edtFullName,edtUserName,edtEmail,edtPassword;
    private Button btnUpdateProfile;
    private DatabaseHelper databaseHelper;
    private Users user;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        initUI();
        setUserInformation();

        initListener();
    }

    private void initUI(){
        edtFullName = findViewById(R.id.edtFullNameProfile);
        edtUserName = findViewById(R.id.edtUserNameProfile);
        edtEmail = findViewById(R.id.edtEmailProfile);
        edtPassword = findViewById(R.id.edtPasswordProfile);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        databaseHelper = new DatabaseHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("configure",MODE_PRIVATE);
        userID = sharedPreferences.getInt("id",-1);
        Log.d("userID",String.valueOf(userID));
    }
    private void initListener() {
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUpdateProfile();
            }
        });
    }

    private void onClickBackToMain() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        finish();
    }

    private void onClickUpdateProfile() {
        String strNewEmail = edtEmail.getText().toString().trim();
        String strNewFullName = edtFullName.getText().toString().trim();
        if(strNewFullName.isEmpty()){
            edtFullName.setError("Họ tên không được để trống!");
            edtFullName.requestFocus();
        }else if(strNewEmail.isEmpty()){
            edtEmail.setError("Email không được để trống!");
            edtEmail.requestFocus();
        }else if(strNewEmail.length()<11||!strNewEmail.substring(strNewEmail.length()-10,strNewEmail.length()).equals("@gmail.com")){
            edtEmail.setError("Email không đúng định dạng!");
            edtEmail.requestFocus();
        }else {
            user.setGmail(strNewEmail);
            user.setFullname(strNewFullName);
            boolean check = databaseHelper.updateUser(user);
            if(check){
                new AlertDialog.Builder(this)
                        .setTitle("Thay đổi hồ sơ").setMessage("Thay đổi hồ sơ thành công!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("vietabc",String.valueOf(user.getId()));
                                Intent intent = new Intent(MyProfileActivity.this, MapsActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).show();
            }else{
                new AlertDialog.Builder(this)
                        .setTitle("Thay đổi hồ sơ").setMessage("Thay đổi hồ sơ thất bại!")
                        .setNegativeButton("OK",null).show();
                setUserInformation();

            }

        }
    }

    private void setUserInformation() {
        if(userID!=(-1)){
            user = databaseHelper.getUserById(userID);
            if(user == null ){
                return;
            }
            edtFullName.setText(user.getFullname());
            edtEmail.setText(user.getGmail());
            edtUserName.setText(user.getUsername());
            edtPassword.setText(user.getPassword());
        }
    }
}
