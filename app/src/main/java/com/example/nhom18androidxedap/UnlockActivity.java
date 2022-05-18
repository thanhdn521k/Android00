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
import android.widget.Toast;

import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Users;
import com.example.nhom18androidxedap.model.Voucher;

import java.util.Map;

public class UnlockActivity extends AppCompatActivity {

    TextView tvAddCodeBill,tvTestCodeBill;
    Button btnAddCodeBill,btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);
        tvAddCodeBill = findViewById(R.id.add_code_bill);
        btnAddCodeBill = findViewById(R.id.btn_add_code_bill);
        tvTestCodeBill = findViewById(R.id.test_code_bill);

        DatabaseHelper dataBaseHelper = new DatabaseHelper(UnlockActivity.this);

        Intent intent1 = getIntent();
        Bundle bundle = intent1.getBundleExtra("dulieu");
        String username = bundle.getString("username");
        String codeBill = bundle.getString("codeBill");
        Users user = (Users) bundle.getSerializable("user");
        Voucher voucher = (Voucher) bundle.getSerializable("voucher");
        int quantity = bundle.getInt("quanity");
        int total =  bundle.getInt("total");
        String string1 = bundle.getString("string1") + ":00";
        String string2 = bundle.getString("string2") + ":00";


        Log.d("idUser",username);
        Log.d("idUser_CODEBILL",codeBill);
        tvTestCodeBill.setText(codeBill);
        tvAddCodeBill.setText(codeBill);
        btnAddCodeBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvTestCodeBill.getText().toString().equals(tvAddCodeBill.getText().toString())) {
                    Intent intent = new Intent(UnlockActivity.this,ScanQR.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("username",username);
                    bundle1.putString("codeBill",codeBill);
                    bundle1.putSerializable("user",user);
                    bundle1.putSerializable("voucher",voucher);
                    bundle1.putInt("quanity",quantity);
                    bundle1.putInt("total",total);
                    bundle1.putString("string1",string1);
                    bundle1.putString("string2",string2);
                    intent.putExtra("dulieu",bundle1);
                    startActivity(intent);
                }else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(UnlockActivity.this).create(); //Read Update
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