package com.example.nhom18androidxedap;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nhom18androidxedap.adapterCustomer.BillAdapter;
import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Bicycle;
import com.example.nhom18androidxedap.model.Bill;
import com.example.nhom18androidxedap.model.Users;
import com.example.nhom18androidxedap.model.Voucher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BuyTicketActivity extends AppCompatActivity {

    Button btnBuyTicket;
    Bicycle bicycle;
    Users user;
    Voucher voucher;
    DatabaseHelper dataBaseHelper = new DatabaseHelper(BuyTicketActivity.this);
    ListView lvBuy;
    ArrayList<Bill> billList;
    BillAdapter adapter;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);




        btnBuyTicket = findViewById(R.id.btn_buy_ticket);
        Intent intent1 = getIntent();
        username = intent1.getStringExtra("username");
        int idUser = dataBaseHelper.getIdUser(username);
        int count = dataBaseHelper.isHaveBill(idUser);

        lvBuy = findViewById(R.id.lv_buy);
        billList = new ArrayList<>();
        adapter = new BillAdapter(this,R.layout.dong_mua_ve,billList);
        lvBuy.setAdapter(adapter);
        List<Bill> list = new ArrayList<Bill>();
        list = dataBaseHelper.getAllBill(idUser);
        for(Bill s : list)
        {
            billList.add(s);
        }
        registerForContextMenu(lvBuy);

        btnBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count != 0)
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(BuyTicketActivity.this);
                    alertDialog.setTitle("Thông báo!");
                    alertDialog.setMessage("Bạn đang trong thời gian thuê !");
                    alertDialog.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(BuyTicketActivity.this,MapsActivity.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                        }
                    });
                    alertDialog.show();
                }
                else
                {
                    Intent intent = new Intent(BuyTicketActivity.this,SelectTicketActivity.class);
                    intent.putExtra("username",username);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bill, menu);
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.detailBill){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int i = info.position;
            Bill bill = billList.get(i);
//            Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(BuyTicketActivity.this, DetailBillCustomer.class);
            intent.putExtra("id", bill.getId());
            Log.d("dinhnhuthanh",username);
            intent.putExtra("username",username);
            intent.putExtra("code", bill.getCode());
            intent.putExtra("nameKH", bill.getUsers().getFullname());
//            intent.putExtra("maXe", bill.getBicycle().getCode());
            intent.putExtra("listBicycle", (Serializable) bill.getBicycleList());
            intent.putExtra("gioThue", bill.getStart_at());
            intent.putExtra("gioTra", bill.getEnd_at());
            intent.putExtra("total", bill.getTotal());
            startActivity(intent);
            return true;
        }

        return false;
    }
}