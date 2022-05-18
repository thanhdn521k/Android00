package com.example.nhom18androidxedap.adapterAdmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.nhom18androidxedap.R;
import com.example.nhom18androidxedap.model.Notify;

import java.util.List;

public class NotifyAdapterAdmin extends RecyclerView.Adapter<NotifyAdapterAdmin.NotifyViewHolder>{

    private List<Notify> notifyList;
    private IClickItem iClickItem;

    public NotifyAdapterAdmin(IClickItem iClickItem) {
        this.iClickItem = iClickItem;
    }

    public void setData(List<Notify> list){
        this.notifyList = list;
        notifyDataSetChanged();
    }

    public Notify getNotify(int position){
        return notifyList.get(position);
    }

    @NonNull
    @Override
    public NotifyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_notify_manage,parent,false);
        return new NotifyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyViewHolder holder, int position) {
        final Notify notify = notifyList.get(position);
        if(notify == null){
            return;
        }
        holder.tvTime.setText(notify.getTime());
        holder.tvTitle.setText(notify.getTitle());
        holder.btnEditNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItem.editNotify(notify);
            }
        });
        holder.btnDeleteNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItem.deleteNotify(notify);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(notifyList != null){
            return  notifyList.size();
        }
        return 0;
    }

    public class NotifyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle,tvTime;
        private Button btnEditNotify,btnDeleteNotify;

        public NotifyViewHolder(@NonNull View view) {
            super(view);

            tvTitle = view.findViewById(R.id.tv_title);
            tvTime = view.findViewById(R.id.tv_time);
            btnEditNotify = view.findViewById(R.id.btnEditNotify);
            btnDeleteNotify = view.findViewById(R.id.btnDeleteNotify);

        }


    }

    public interface IClickItem{
        void editNotify(Notify notify);

        void deleteNotify(Notify notify);
    }

}
