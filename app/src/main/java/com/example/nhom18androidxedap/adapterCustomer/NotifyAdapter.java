package com.example.nhom18androidxedap.adapterCustomer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nhom18androidxedap.R;
import com.example.nhom18androidxedap.model.Notify;

import org.w3c.dom.Text;

import java.time.temporal.Temporal;
import java.util.List;

public class NotifyAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Notify> notifyList;

    public NotifyAdapter(Context context, int layout, List<Notify> notifyList) {
        this.context = context;
        this.layout = layout;
        this.notifyList = notifyList;
    }

    @Override
    public int getCount() {
        return notifyList.size();
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
        TextView tvTitle , tvDetail ,tvTime;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.tvTitle = (TextView) view.findViewById(R.id.title_noti);
            holder.tvDetail = (TextView) view.findViewById(R.id.detail_noti);
            holder.tvTime = (TextView) view.findViewById(R.id.time_noti);
            view.setTag(holder);
        }else
        {
            holder = (ViewHolder) view.getTag();
        }

        Notify notify1 = notifyList.get(i);

        holder.tvTitle.setText(notify1.getTitle());
        holder.tvDetail.setText(notify1.getDetail());
        holder.tvTime.setText(notify1.getTime());


        return view;
    }
}
