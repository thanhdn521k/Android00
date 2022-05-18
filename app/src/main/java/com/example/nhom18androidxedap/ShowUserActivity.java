package com.example.nhom18androidxedap;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Users;


public class ShowUserActivity extends AppCompatActivity {

    private EditText edtUsername,edtGmail,edtFullName,edtPassWord,edtAuthority;
    private Button btnBackToMain;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);

        initUI();
        initListener();
        showInformationUser();
    }

    private void initUI() {
        edtFullName = findViewById(R.id.edtShowFullName);
        edtGmail = findViewById(R.id.edtShowEmail);
        edtUsername = findViewById(R.id.edtShowUserName);
        edtPassWord = findViewById(R.id.edtShowPassword);
        edtAuthority = findViewById(R.id.edtShowAuthority);
        btnBackToMain = findViewById(R.id.btnShowUserBackToMain);
        databaseHelper = new DatabaseHelper(this);
    }

    private void initListener() {
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowUserActivity.this,UserManageActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showInformationUser() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",-1);
        Users user = databaseHelper.getUserById(id);
        if(user!=null){
            edtFullName.setText(user.getFullname());
            edtGmail.setText(user.getGmail());
            edtUsername.setText(user.getUsername());
            edtPassWord.setText(user.getPassword());
            edtAuthority.setText(user.getAuthority());
        }
    }
}