package com.example.nhom18androidxedap;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Bill;
import com.example.nhom18androidxedap.model.Users;
import com.example.nhom18androidxedap.model.Voucher;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class SelectTicketActivity extends AppCompatActivity {
    Spinner spinner1;
    Button btnPay;
    TextView tv1,tv2,tvTotal,tvUsername;
    EditText edQuanity,edVoucher;
    TextView date_time_start,date_time_end;
    int sumS,sumE,quanity,check = 0,codeBill;
    Double TimeE,TimeS;
    Bill bill;
    Voucher voucher1;
    Users user;
    String string1,string2,username;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ticket);

        spinner1 = findViewById(R.id.spin_ticket1);
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("50.000 / 1 giờ");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, com.karumi.dexter.R.layout.support_simple_spinner_dropdown_item,arrayList); // check lai
        spinner1.setAdapter(arrayAdapter);
        btnPay          = findViewById(R.id.btn_payment);
        tv1             = findViewById(R.id.tv_setSum1);
        tv2             = findViewById(R.id.tv_setSum2);
        tvTotal         = findViewById(R.id.total);
        edQuanity       = findViewById(R.id.ed_quanity);
        edVoucher       = findViewById(R.id.ed_voucher);
        date_time_start = findViewById(R.id.date_time_in_start);
        date_time_end   = findViewById(R.id.date_time_in_end);
        tvUsername      = findViewById(R.id.tv_username);

//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        LocalDateTime now = LocalDateTime.now();
//        date_time_start.setText(dtf.format(now));

        date_time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTimeDialogS(date_time_start);
            }
        });


        date_time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTimeDialogE(date_time_end);
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPaymentDialog(Gravity.CENTER);
            }
        });
    }

    private void dateTimeDialogE(TextView date_time_end) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth ) {
                year        = calendar.get(Calendar.YEAR);
                month       =  calendar.get(Calendar.MONTH);
                dayOfMonth  = calendar.get(Calendar.DAY_OF_MONTH);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        sumE = hourOfDay*60 + minute;
                        tv2.setText(String.valueOf(sumE));
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                        date_time_end.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(SelectTicketActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();

            }
        };
        new DatePickerDialog(SelectTicketActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void dateTimeDialogS(final TextView date_time_start) {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth ) {
                year        = calendar.get(Calendar.YEAR);
                month       =  calendar.get(Calendar.MONTH);
                dayOfMonth  = calendar.get(Calendar.DAY_OF_MONTH);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
//                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
//                        calendar.set(Calendar.MINUTE,minute);
                        hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                        minute = calendar.get(Calendar.MINUTE);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Log.d("thanh1",String.valueOf(hourOfDay));
                        Log.d("thanh2",String.valueOf(minute));
                        sumS = hourOfDay*60 + minute;
                        tv1.setText(String.valueOf(sumS));
                        date_time_start.setText(simpleDateFormat.format(calendar.getTime()));
                        Log.d("date_time_start",simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(SelectTicketActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();

            }
        };
        new DatePickerDialog(SelectTicketActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    private void openPaymentDialog(int gravity)
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_payment);

        Window window = dialog.getWindow();
        if(window == null)
        {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAtrribute = window.getAttributes();
        windowAtrribute.gravity = gravity;
        window.setAttributes(windowAtrribute);

        if(Gravity.BOTTOM == gravity)
        {
            dialog.setCancelable(true);
        }else
        {
            dialog.setCancelable(false);
        }

        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnAgree = dialog.findViewById(R.id.btn_agree);
        TextView tvTotal = dialog.findViewById(R.id.total);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectTicketActivity.this,UnlockActivity.class);
                //bill = new Bill(1,String.valueOf(codeBill), user , notify1 ,quanity,Integer.valueOf(tvTotal.getText().toString()),string1 + ":00",string2+ ":00",true);
                Bundle bundle = new Bundle();
                bundle.putString("codeBill",String.valueOf(codeBill));
                bundle.putSerializable("user",user);
                bundle.putSerializable("voucher",voucher1);
                bundle.putInt("quanity",quanity);
                bundle.putInt("total",Integer.valueOf(tvTotal.getText().toString()));
                bundle.putString("string1",string1);
                bundle.putString("string2",string2);
                bundle.putString("username",username);
                intent.putExtra("dulieu",bundle);
//                Log.d("codeBill",String.valueOf(codeBill));
//                intent.putExtra("codeBill",String.valueOf(codeBill));
//                intent.putExtra("username",tvUsername.getText());
                startActivity(intent);
            }
        });

        quanity = Integer.valueOf(edQuanity.getText().toString()); // chua xu li quanity trong database
        if(quanity == 0)
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Thông báo!");
            alertDialog.setMessage("Nhập số lượng vé !");
            alertDialog.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertDialog.show();
            dialog.dismiss();
            check = 1;
        }

        TimeS = Double.valueOf(tv1.getText().toString());
        TimeE = Double.valueOf(tv2.getText().toString());

        Double sumES = (TimeE - TimeS)/60;
        DatabaseHelper dataBaseHelper = new DatabaseHelper(SelectTicketActivity.this);
        int total = 0;
        int voucher = 0;
        String codeVoucher = edVoucher.getText().toString();
        int count = dataBaseHelper.isHaveVoucher(codeVoucher); //count!=0 thi co voucher
        Log.d("demso",String.valueOf(count));
        if(sumES < 1)
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Thông báo!");
            alertDialog.setMessage("Bạn phải nhập giờ thuê lớn hơn 1 giờ !");
            alertDialog.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertDialog.show();
            check = 1;
        }else if(sumES >= 1){
            if((sumES == Math.floor(sumES)) && !Double.isInfinite(sumES))
            {
                if(count!=0)
                {
                    Double doubleValueObject = new Double(sumES);
                    int newSumES = doubleValueObject.intValue();
                    total = 50000*newSumES*quanity;
                    if(codeVoucher.equals(""))
                    {
                        total = 50000*newSumES*quanity;
                    }else
                    {
                        int minBill = Integer.valueOf(dataBaseHelper.getMinBill(edVoucher.getText().toString()));
                        Log.d("minTotal",String.valueOf(total));
                        Log.d("minBill",String.valueOf(minBill));
                        if(total >= minBill)
                        {
                            String discount = dataBaseHelper.getDiscount(edVoucher.getText().toString());
                            voucher = Integer.valueOf(discount);
                            Log.d("minVoucher", String.valueOf(voucher));
                            total = (50000*newSumES*quanity)-voucher;
                        }else if(total < minBill)
                        {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                            alertDialog.setTitle("Thông báo!");
                            alertDialog.setMessage("Bạn chưa đạt giá trị đơn tối thiểu !");
                            alertDialog.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            alertDialog.show();
                            check = 1;
                        }

                    }
                    tvTotal.setText(String.valueOf(total));
                }else
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                    alertDialog.setTitle("Thông báo!");
                    alertDialog.setMessage("Voucher không tồn tại !");
                    alertDialog.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialog.show();
                    check = 1;
                }
            }else
            {
                if(count!=0)
                {
                    Double doubleValueObject = new Double(sumES);
                    int newSumES = doubleValueObject.intValue();
                    total = 50000*(newSumES+1)*quanity;
                    if(codeVoucher.equals(""))
                    {
                        total = 50000*(newSumES+1)*quanity;
                    }else
                    {
                        int minBill = Integer.valueOf(dataBaseHelper.getMinBill(edVoucher.getText().toString()));
                        if(total >= minBill)
                        {
                            String discount = dataBaseHelper.getDiscount(edVoucher.getText().toString());
                            voucher = Integer.valueOf(discount);
                            total = 50000*(newSumES+1)*quanity-voucher;
                        }else if(total < minBill)
                        {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                            alertDialog.setTitle("Thông báo!");
                            alertDialog.setMessage("Bạn chưa đạt giá trị đơn tối thiểu !");
                            alertDialog.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            alertDialog.show();
                            check = 1;
                        }
                    }
                    tvTotal.setText(String.valueOf(total));
                }
                else
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                    alertDialog.setTitle("Thông báo!");
                    alertDialog.setMessage("Voucher không tồn tại !");
                    alertDialog.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialog.show();
                    check = 1;
                }
            }
        }
        if(check == 1)
        {
            dialog.dismiss();
            check = 0;
        }else{
            string1 = (String) date_time_start.getText();
            string2 = (String) date_time_end.getText();
            Log.d("datetime",string1);
            Log.d("datetime2",string2);
            Intent intent1 = getIntent();
            username = intent1.getStringExtra("username");
            user = dataBaseHelper.getUser(username);
            Log.d("ktra",username);

            tvUsername.setText(username);

            //endtest
            voucher1 = dataBaseHelper.getVoucher(codeVoucher);
            Random rand = new Random();
            codeBill = rand.nextInt(1000000)+1;
            dialog.show();
        }
    }
}

