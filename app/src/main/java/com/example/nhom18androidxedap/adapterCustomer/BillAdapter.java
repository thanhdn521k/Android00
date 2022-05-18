package com.example.nhom18androidxedap.adapterCustomer;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nhom18androidxedap.R;
import com.example.nhom18androidxedap.model.Bill;

import java.util.List;

public class BillAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Bill> billList;

    public BillAdapter(Context context, int layout, List<Bill> billList) {
        this.context = context;
        this.layout = layout;
        this.billList = billList;
    }



    @Override
    public int getCount() {
        return billList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView tvCodeBill,tvStartTime,tvEndTime;
        ImageView imgDelete;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.tvCodeBill = view.findViewById(R.id.txtCodeBill);
            holder.tvStartTime = view.findViewById(R.id.startTimeBill);
            holder.tvEndTime = view.findViewById(R.id.endTimeBill);
            view.setTag(holder);
        }else
        {
            holder = (ViewHolder) view.getTag();
        }

        Bill bill = billList.get(i);

        holder.tvCodeBill.setText("Mã hóa đơn là : " + bill.getCode());
        holder.tvStartTime.setText("Thời gian bắt đầu : " + bill.getStart_at());
        holder.tvEndTime.setText("Thời gian kết thúc : " + bill.getEnd_at());

        return view;
    }
}
