package com.example.nhom18androidxedap;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.nhom18androidxedap.adapterAdmin.VoucherAdapterAdmin;
import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Voucher;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class VoucherManageActivity extends AppCompatActivity {

    public static int REQUEST_CODE_ADD_VOUCHER = 1;
    public static int REQUEST_CODE_EDIT_VOUCHER = 2;
    private ImageButton btnBackToMain;
    private RecyclerView recyclerView;
    private List<Voucher> voucherList;
    private DatabaseHelper databaseHelper;
    private VoucherAdapterAdmin voucherAdapterAdmin;
    private TextView tvDeleteAll;
    private SearchView searchView;
    private Spinner spinner;
    private Button btnSearchTimeVoucher;
//    private TextView tvTimeVoucher;
    private EditText edtTimeVoucher;
    private int userID;
    private FloatingActionButton btnAddVoucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_manage);

        initUI();
        initListener();

        voucherAdapterAdmin = new VoucherAdapterAdmin(new VoucherAdapterAdmin.IClickItemVoucher() {
            @Override
            public void editVoucher(Voucher voucher) {
                onClickEditVoucher(voucher);
            }

            @Override
            public void deleteVoucher(Voucher voucher) {
                onClickDeleteVoucher(voucher);
            }
        });
        voucherList = databaseHelper.getAllVoucher();
        voucherAdapterAdmin.setData(voucherList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(voucherAdapterAdmin);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Voucher> list = databaseHelper.getVoucherByCode(s);
                voucherAdapterAdmin.setData(list);
                return true;
            }
        });
    }

    private void initUI(){
        btnAddVoucher = findViewById(R.id.btn_add_new_voucher);
        btnBackToMain = findViewById(R.id.btn_voucher_back_to_main);
        recyclerView = findViewById(R.id.recyclerView_voucher_manage);
        databaseHelper = new DatabaseHelper(VoucherManageActivity.this);
        tvDeleteAll = findViewById(R.id.tvDeleteAllVoucher);
        searchView = findViewById(R.id.searchVoucher);
        edtTimeVoucher = findViewById(R.id.edt_time_voucher);
        btnSearchTimeVoucher = findViewById(R.id.btn_search_time_voucher);
        spinner = findViewById(R.id.spinner_sort_voucher);
        Intent intent = getIntent();
        userID = intent.getIntExtra("id",-1);
    }


    private void initListener(){
        btnAddVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickAddVoucher();
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
                onClickDeleteAllVoucher();
            }
        });

        btnSearchTimeVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSearchTimeVoucher();
            }
        });

        edtTimeVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickTimeVoucher();
            }
        });


        btnSearchTimeVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSearchTimeVoucher();
            }
        });

        final String[] sort = {"Code:A->Z","Code:Z->A","Mức khuyến mãi: tăng dần",
                "Mức khuyến mãi: giảm dần","Đơn tối thiểu: tăng dần","Đơn tối thiểu: giảm dần"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(VoucherManageActivity.this, android.R.layout.simple_list_item_1,sort);
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
                voucherList = databaseHelper.getVoucherByCodeASC();
                voucherAdapterAdmin.setData(voucherList);
                break;

            case 1:
                voucherList = databaseHelper.getVoucherByCodeDESC();
                voucherAdapterAdmin.setData(voucherList);
                break;

            case 2:
                voucherList = databaseHelper.getVoucherByDiscountASC();
                voucherAdapterAdmin.setData(voucherList);
                break;

            case 3:
                voucherList = databaseHelper.getVoucherByDiscountDESC();
                voucherAdapterAdmin.setData(voucherList);
                break;

            case 4:
                voucherList = databaseHelper.getVoucherByMinBillASC();
                voucherAdapterAdmin.setData(voucherList);
                break;

            case 5:
                voucherList = databaseHelper.getVoucherByMinBillDESC();
                voucherAdapterAdmin.setData(voucherList);
                break;
        }
    }

    private void onClickSearchTimeVoucher() {
        String date = edtTimeVoucher.getText().toString().trim();
        if(!date.isEmpty()){
            voucherList = databaseHelper.getVoucherByDate(date);
            voucherAdapterAdmin.setData(voucherList);
        }else{
            voucherList = databaseHelper.getAllVoucher();
            voucherAdapterAdmin.setData(voucherList);
        }

    }

    private void onClickTimeVoucher() {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                edtTimeVoucher.setText(simpleDateFormat.format(calendar.getTime()));

            }
        },year,month,day);
        datePickerDialog.show();
    }

    private void onClickBackToMain() {
        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra("id",userID);
        startActivity(intent);
        finish();
    }

    private void onclickAddVoucher() {
        Intent intent = new Intent();
        intent.setClass(VoucherManageActivity.this, AddVoucherActivity.class);
        startActivityForResult(intent,REQUEST_CODE_ADD_VOUCHER);
    }

    private void onClickDeleteAllVoucher() {
        new AlertDialog.Builder(this)
                .setTitle("Xóa tất cả voucher").setMessage("Bạn có chắc chắn ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean check = databaseHelper.deleteAllVoucher();
                        if(check){
                            Toast.makeText(VoucherManageActivity.this,"Xóa tất cả voucher thành công !",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(VoucherManageActivity.this,VoucherManageActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(VoucherManageActivity.this,"Xóa tất cả voucher thất bại !",Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("No",null).show();
    }

    private void onClickDeleteVoucher(Voucher voucher) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa voucher này").setMessage("Bạn có chắc chắn ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean check = databaseHelper.deleteVoucher(voucher);
                        if(check){
                            Toast.makeText(VoucherManageActivity.this,"Xóa voucher này thành công !",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(VoucherManageActivity.this,VoucherManageActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(VoucherManageActivity.this,"Xóa voucher này thất bại !",Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("No",null).show();
    }

    private void onClickEditVoucher(Voucher voucher) {
        Intent intent = new Intent(VoucherManageActivity.this, EditVoucherActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("voucher",voucher);
        intent.putExtras(bundle);
        startActivityForResult(intent,REQUEST_CODE_ADD_VOUCHER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_ADD_VOUCHER && resultCode == RESULT_OK){
            Intent intent = new Intent(VoucherManageActivity.this,VoucherManageActivity.class);
            startActivity(intent);
            finish();
        }else if(requestCode == REQUEST_CODE_EDIT_VOUCHER && resultCode == RESULT_OK){
            Intent intent = new Intent(VoucherManageActivity.this,VoucherManageActivity.class);
            startActivity(intent);
            finish();
        }
    }
}