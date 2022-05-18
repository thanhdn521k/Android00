package com.example.nhom18androidxedap.adapterAdmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nhom18androidxedap.R;
import com.example.nhom18androidxedap.model.TKe;

import java.util.ArrayList;

public class AdapterTke extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<TKe> listTke;

    public AdapterTke(Context context, ArrayList<TKe> listTke) {
        this.context = context;
        this.listTke = listTke;
    }

    @Override
    public int getCount() {
        return this.listTke.size();
    }

    @Override
    public Object getItem(int i) {
        return listTke.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.item_tke, null);
        TextView txtMonth = view.findViewById(R.id.month);
        TextView txtDoanhThu = view.findViewById(R.id.doanhthu);
        TextView txtTongXe = view.findViewById(R.id.soxe);

        TKe tKe = listTke.get(i);

        txtMonth.setText("Tháng : " + tKe.getMonth());
        txtDoanhThu.setText("Tổng doanh thu: " + tKe.getTotal());
        txtTongXe.setText("Tổng số xe cho thuê: "+ tKe.getQuantity());

        return view;
    }
}
