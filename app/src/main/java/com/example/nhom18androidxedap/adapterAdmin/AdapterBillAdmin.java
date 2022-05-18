package com.example.nhom18androidxedap.adapterAdmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nhom18androidxedap.R;
import com.example.nhom18androidxedap.model.Bill;

import java.util.ArrayList;

public class AdapterBillAdmin<B> extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Bill> listBill;

//    public AdapterBike(Context context, int layout, ArrayList<Bike> listBike) {
//        this.context = context;
//        this.layout = layout;
//        this.listBike = listBike;
//    }

    public AdapterBillAdmin(Context context, ArrayList<Bill> listBill) {
        this.context = context;
        //this.layout = layout;
        this.listBill = listBill;
    }

    @Override
    public int getCount() {
        return listBill.size();
    }

    @Override
    public Object getItem(int i) {
        return listBill.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.item_bill, null);
        TextView txtMaHD_list = (TextView) view.findViewById(R.id.MaHoaDon);
//        TextView txtMaXe_list = (TextView) view.findViewById(R.id.MaXeBill);
        TextView txtUser_list = (TextView) view.findViewById(R.id.UserBill);
        TextView txtStatus_list = (TextView) view.findViewById(R.id.StatusBill);

        Bill bill = listBill.get(i);

//        ArrayList<Bicycle> bicycle = bill.getBicycle();
//        String codeBike;
//        if (bicycle != null) {
//             codeBike = bicycle.getCode();
//        } else  codeBike = null;

        txtMaHD_list.setText("Mã hoá đơn: " + bill.getCode());
//        txtMaXe_list.setText("Mã xe: " + codeBike);
        txtUser_list.setText("Tên KH: " + bill.getUsers().getUsername());
//        txtStatus_list.setText("Trạng thái: " + bill.getTotal());
        if (bill.isStatus() == true){
            txtStatus_list.setText("Trạng thái: Đang thuê");
        }
        else txtStatus_list.setText("Trạng thái: Đã trả");

        return view;
    }
}
