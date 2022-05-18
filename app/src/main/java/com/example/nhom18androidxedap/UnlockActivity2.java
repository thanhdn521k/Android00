package com.example.nhom18androidxedap;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nhom18androidxedap.database.DatabaseHelper;

public class UnlockActivity2 extends AppCompatActivity {

    Button btnBack,btnUnlock;
    TextView tvAddCodeBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock2);

        Intent intent1 = getIntent();
        String username = intent1.getStringExtra("username");
        DatabaseHelper dataBaseHelper = new DatabaseHelper(UnlockActivity2.this);
        int idUser = dataBaseHelper.getIdUser(username);
        String codeBill = dataBaseHelper.getCodeBill(idUser);
        Log.d("codeBill",codeBill);

        btnUnlock = findViewById(R.id.btn_add_code_bill_2);
        tvAddCodeBill = findViewById(R.id.add_code_bill_2);

        btnUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvAddCodeBill.getText().toString().equals(codeBill))
                {
                    Intent intent = new Intent(UnlockActivity2.this,ScanQR2.class);
                    intent.putExtra("username",username);
                    intent.putExtra("codeBill",codeBill);
                    startActivity(intent);
                }else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(UnlockActivity2.this).create(); //Read Update
                    alertDialog.setTitle("Thông báo!");
                    alertDialog.setMessage("Không tồn tại mã hóa đơn ! Vui lòng kiểm tra lại mã hóa đơn trong phần mua vé");

                    alertDialog.setButton("Đóng", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertDialog.show();
                }
            }
        });
    }
}