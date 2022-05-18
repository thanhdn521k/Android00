package com.example.nhom18androidxedap;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nhom18androidxedap.adapterAdmin.NotifyAdapterAdmin;
import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Notify;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class NotifyManageActivity extends AppCompatActivity {

    public static int REQUEST_CODE_ADD_NOTIFY = 1;
    public static int REQUEST_CODE_EDIT_NOTIFY = 2;
    private ImageButton btnBackToMain;
    private RecyclerView mRecyclerView;
    private List<Notify> notifyList;
    private DatabaseHelper databaseHelper;
    private NotifyAdapterAdmin notifyAdapterAdmin;
    private TextView tvDeleteAll;
    private SearchView searchView;
    private Spinner spinner;
    private Button btnSearchTimeNotify;
    private EditText edtTimeNotify;
    private int userID;
    private FloatingActionButton btnAddNotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_manage);

        initUI();
        initListener();

        notifyAdapterAdmin = new NotifyAdapterAdmin(new NotifyAdapterAdmin.IClickItem() {
            @Override
            public void editNotify(Notify notify) {
                clickEditNotify(notify);
            }

            @Override
            public void deleteNotify(Notify notify) {
                clickDeleteNotify(notify);
            }
        });
        notifyList = databaseHelper.getAllNotify();
        notifyAdapterAdmin.setData(notifyList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(notifyAdapterAdmin);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Notify> list = databaseHelper.getNotifyByTitle(s);
                notifyAdapterAdmin.setData(list);
                return true;
            }
        });
    }

    private void initUI(){
        btnAddNotify = findViewById(R.id.btn_add_new_notify);
        btnBackToMain = findViewById(R.id.btn_notify_back_to_main);
        mRecyclerView = findViewById(R.id.recyclerView_notify_manage);
        databaseHelper = new DatabaseHelper(this);
        notifyList = databaseHelper.getAllNotify();
        tvDeleteAll = findViewById(R.id.tvDeleteAllNotify);
        searchView = findViewById(R.id.searchNotify);
        edtTimeNotify = findViewById(R.id.edt_time_notify);
        btnSearchTimeNotify = findViewById(R.id.btn_search_time_notify);
        spinner = findViewById(R.id.spinner_sort_notify);
        Intent intent = getIntent();
        userID = intent.getIntExtra("id",-1);
    }

    private void initListener(){
        btnAddNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAddNotify();
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
                onClickDeleteAll();
            }
        });

        btnSearchTimeNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSearchTime();
            }
        });

//        edtTimeNotify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onClickTimeNotify();
//            }
//        });

        final String[] sort = {"tiêu đề:A->Z","tiêu đề:Z->A","thời gian: tăng dần", "thời gian: giảm dần"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(NotifyManageActivity.this, android.R.layout.simple_list_item_1,sort);
        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                onClickSpinnerSort(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void onClickSpinnerSort(int i) {
        switch (i){
            case 0:
                notifyList = databaseHelper.getNotifyByTitleASC();
                notifyAdapterAdmin.setData(notifyList);
                break;

            case 1:
                notifyList = databaseHelper.getNotifyByTitleDESC();
                notifyAdapterAdmin.setData(notifyList);
                break;

            case 2:
                notifyList = databaseHelper.getNotifyByDateASC();
                notifyAdapterAdmin.setData(notifyList);
                break;

            case 3:
                notifyList = databaseHelper.getNotifyByDateDESC();
                notifyAdapterAdmin.setData(notifyList);
                break;

        }
    }

//    private void onClickTimeNotify() {
//        final Calendar calendar = Calendar.getInstance();
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        int month = calendar.get(Calendar.MONTH);
//        int year = calendar.get(Calendar.YEAR);
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                calendar.set(i,i1,i2);
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                tvTimeNotify.setText(simpleDateFormat.format(calendar.getTime()));
//            }
//        },year,month,day);
//        datePickerDialog.show();
//    }

    private void onClickSearchTime() {
        String date = edtTimeNotify.getText().toString().trim();
        notifyList = databaseHelper.getNotifyByDate(date);
        notifyAdapterAdmin.setData(notifyList);
    }

    private void onClickDeleteAll() {
        new AlertDialog.Builder(this)
                .setTitle("Xóa tất cả thông báo").setMessage("Bạn có chắc chắn ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean check = databaseHelper.deleteAllNotify();
                        if(check){
                            Toast.makeText(NotifyManageActivity.this,"Xóa tất cả thông báo thành công !",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(NotifyManageActivity.this,NotifyManageActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(NotifyManageActivity.this,"Xóa tất cả thông báo thất bại !",Toast.LENGTH_LONG).show();
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

    private void onClickAddNotify() {
        Intent intent = new Intent();
        intent.setClass(NotifyManageActivity.this, AddNotifyActivity.class);
        startActivityForResult(intent,REQUEST_CODE_ADD_NOTIFY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_ADD_NOTIFY && resultCode == RESULT_OK){
            Intent intent = new Intent(NotifyManageActivity.this,NotifyManageActivity.class);
            startActivity(intent);
            this.finish();
        } else if(requestCode == REQUEST_CODE_EDIT_NOTIFY && resultCode == RESULT_OK){
            Intent intent = new Intent(NotifyManageActivity.this,NotifyManageActivity.class);
            startActivity(intent);
            finish();

        }

    }
    private void clickEditNotify(Notify notify) {
        Intent intent = new Intent(NotifyManageActivity.this, EditNotifyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("notify",notify);
        intent.putExtras(bundle);
        startActivityForResult(intent,REQUEST_CODE_ADD_NOTIFY);
    }

    private void clickDeleteNotify(Notify notify) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa thông báo này ").setMessage("Bạn có chắc chắn ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean check = databaseHelper.deleteNotify(notify);
                        if(check){
                            Toast.makeText(NotifyManageActivity.this,"Xóa thông báo này thành công !",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(NotifyManageActivity.this,NotifyManageActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(NotifyManageActivity.this,"Xóa thông báo này thất bại !",Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("No",null).show();
    }
}