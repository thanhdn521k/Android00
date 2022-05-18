package com.example.nhom18androidxedap;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nhom18androidxedap.adapterAdmin.UserAdapterAdmin;
import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Users;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserManageActivity extends AppCompatActivity {

    public static int REQUEST_CODE_ADD_USER = 1;
    private ImageButton btnBackToMain;
    private Button btnCustomer,btnAdmin;
    private RecyclerView recyclerView;
    private List<Users> userList;
    private DatabaseHelper databaseHelper;
    private UserAdapterAdmin userAdapterAdmin;
    private TextView tvDeleteAll;
    private SearchView searchView;
    private Spinner spinner;
    private String status = "Customer";
    private int userID;
    private FloatingActionButton btnAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage);

        initUI();
        initListener();

        userAdapterAdmin = new UserAdapterAdmin(new UserAdapterAdmin.IClickItemUser() {
            @Override
            public void showUser(Users user) {
                onClickShowUser(user);
            }

            @Override
            public void deleteUser(Users user) {
                onclickDeleteUser(user);
            }
        });
        userList = databaseHelper.getAllCustomer();
        userAdapterAdmin.setData(userList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(userAdapterAdmin);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Users> list = new ArrayList<>();
                if(status.equals("Admin")){
                    list = databaseHelper.getAdminByUsername(s);
                }else{
                    list = databaseHelper.getCustomerByUserName(s);
                }
                userAdapterAdmin.setData(list);
                return true;
            }
        });
    }

    private void initUI() {
        btnBackToMain = findViewById(R.id.btn_user_back_to_main);
        btnCustomer = findViewById(R.id.btnCustomer);
        btnAdmin = findViewById(R.id.btnAdmin);
        btnAddUser = findViewById(R.id.btn_add_new_user); // loi
        recyclerView = findViewById(R.id.recyclerView_user_manage);
        databaseHelper = new DatabaseHelper(UserManageActivity.this);
        tvDeleteAll = findViewById(R.id.tvDeleteAllUser);
        searchView = findViewById(R.id.searchUser);
        spinner = findViewById(R.id.spinner_sort_user);
        Intent intent = getIntent();
        userID = intent.getIntExtra("id",-1);
    }

    private void initListener() {
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userList = databaseHelper.getAllAdmin();
                userAdapterAdmin.setData(userList);
                status = "Admin";
                btnCustomer.setTextColor(Color.GRAY);
                btnCustomer.setBackgroundColor(Color.WHITE);
                btnAdmin.setTextColor(Color.RED);
                btnAdmin.setBackgroundColor(Color.YELLOW);
            }
        });
        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userList = databaseHelper.getAllCustomer();
                userAdapterAdmin.setData(userList);
                status = "Customer";
                btnCustomer.setTextColor(Color.RED);
                btnCustomer.setBackgroundColor(Color.YELLOW);
                btnAdmin.setTextColor(Color.GRAY);
                btnAdmin.setBackgroundColor(Color.WHITE);
            }
        });

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAddUser();
            }
        });
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBackToMain();
            }
        });

        tvDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDeleteAllUser();
            }
        });

        final String[] sort = {"Tài khoản:A->Z","tài khoản:Z->A","ID: tăng dần", "ID: giảm dần"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(UserManageActivity.this, android.R.layout.simple_list_item_1,sort);
        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                onClickSortUser(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void onClickAddUser() {
        Intent intent = new Intent(this,AddUserActivity.class);
        intent.putExtra("status",status);
        startActivityForResult(intent,REQUEST_CODE_ADD_USER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_ADD_USER && resultCode == RESULT_OK){
            Intent intent = new Intent(UserManageActivity.this,UserManageActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void onClickSortUser(int i) {
        switch (i){
            case 0:
                if(status.equals("Customer")){
                    userList = databaseHelper.getCustomerByUserNameASC();
                }
                else{
                    userList = databaseHelper.getAdminByUsernameASC();
                }
                userAdapterAdmin.setData(userList);
                break;

            case 1:
                if(status.equals("Customer")){
                    userList = databaseHelper.getCustomerByUserNameDESC();
                }
                else{
                    userList = databaseHelper.getAdminByFullnameDESC();
                }
                userAdapterAdmin.setData(userList);
                break;

            case 2:
                if(status.equals("Customer")){
                    userList = databaseHelper.getCustomerByIdASC();
                }
                else{
                    userList = databaseHelper.getAdminByIdASC();
                }
                userAdapterAdmin.setData(userList);
                break;

            case 3:
                if(status.equals("Customer")){
                    userList = databaseHelper.getCustomerByIdDESC();
                }
                else{
                    userList = databaseHelper.getAdminByIdDESC();
                }
                userAdapterAdmin.setData(userList);
                break;

        }
    }

    private void onClickShowUser(Users user) {
        Intent intent = new Intent(this,ShowUserActivity.class);
        int id = user.getId();
        intent.putExtra("id",id);
        startActivity(intent);
    }

    private void onclickDeleteUser(Users user) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa tài khoản này").setMessage("Bạn có chắc chắn ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean check = databaseHelper.deleteUser(user);
                        if(check){
                            Toast.makeText(UserManageActivity.this,"Xóa tài khoản này thành công!",Toast.LENGTH_LONG).show();
                            loadData();
                        }else {
                            Toast.makeText(UserManageActivity.this,"Xóa tài khoản này thất bại!",Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("No",null).show();
    }

    private void onClickDeleteAllUser() {
        new AlertDialog.Builder(this)
                .setTitle("Xóa tất cả tài khoản").setMessage("Bạn có chắc chắn ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(status.equals("Admin")){
                            boolean check = databaseHelper.deleteAllAdmin();
                            if(check){
                                Toast.makeText(UserManageActivity.this,"Xóa tất cả tài khoản admin thành công !",Toast.LENGTH_LONG).show();
                                loadData();
                            }else {
                                Toast.makeText(UserManageActivity.this,"Xóa tất cả tài khoản admin thất bại !",Toast.LENGTH_LONG).show();
                            }
                        }else{
                            boolean check = databaseHelper.deleteAllCustomer();
                            if(check){
                                Toast.makeText(UserManageActivity.this,"Xóa tất cả tài khoản khách hàng thành công !",Toast.LENGTH_LONG).show();
                                loadData();
                            }else {
                                Toast.makeText(UserManageActivity.this,"Xóa tất cả tài khoản khách hàng thất bại !",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                })
                .setNegativeButton("No",null).show();
    }


    private void onClickBackToMain() {
        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra("id",userID);
        startActivity(intent);
        finish();
    }

    private void loadData(){
        Intent intent = new Intent(this,UserManageActivity.class);
        startActivity(intent);
        finish();
    }
}