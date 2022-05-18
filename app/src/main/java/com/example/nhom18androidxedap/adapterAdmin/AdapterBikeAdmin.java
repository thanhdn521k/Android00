package com.example.nhom18androidxedap.adapterAdmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.nhom18androidxedap.R;
import com.example.nhom18androidxedap.model.Bicycle;

import java.util.ArrayList;

public class AdapterBikeAdmin<B> extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Bicycle> listBike;

//    public AdapterBike(Context context, int layout, ArrayList<Bike> listBike) {
//        this.context = context;
//        this.layout = layout;
//        this.listBike = listBike;
//    }

    public AdapterBikeAdmin(Context context, ArrayList<Bicycle> listBike) {
        this.context = context;
        //this.layout = layout;
        this.listBike = listBike;
    }

    @Override
    public int getCount() {
        return this.listBike.size();
    }

    @Override
    public Object getItem(int i) {
        return listBike.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.item_bike, null);
        TextView txtMaXe_list = (TextView) view.findViewById(R.id.MaXe);
//        TextView txtLoaiXe_list = (TextView) view.findViewById(R.id.LoaiXe);
        TextView txtStatus_list = (TextView) view.findViewById(R.id.TTXe);
//        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = layoutInflater.inflate(layout, null);
        Bicycle bicycle = listBike.get(i);

//        TextView txtMaXe_list = view.findViewById(R.id.txtMaXe_list);
//        TextView txtLoaiXe_list = view.findViewById(R.id.txtLoaiXe_list);
//        TextView txtStatus_list = view.findViewById(R.id.txtStatus_list);

        txtMaXe_list.setText("Mã xe: " + bicycle.getCode());
//        txtLoaiXe_list.setText("Loại xe: " + bicycle.getType());
        if (bicycle.isStatus()==true){
            txtStatus_list.setText("Trạng thái xe: Đang cho thuê");
        }
        else txtStatus_list.setText("Trạng thái xe: Đang trống");

        return view;
    }
}
