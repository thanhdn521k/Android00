package com.example.nhom18androidxedap.adapterAdmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.nhom18androidxedap.R;
import com.example.nhom18androidxedap.model.Voucher;

import java.util.List;

public class VoucherAdapterAdmin extends RecyclerView.Adapter<VoucherAdapterAdmin.VoucherViewHolder>{
    private List<Voucher> voucherList;
    private IClickItemVoucher iClickItem;

    public VoucherAdapterAdmin(IClickItemVoucher iClickItem) {
        this.iClickItem = iClickItem;
    }

    public void setData(List<Voucher> list){
        this.voucherList = list;
        notifyDataSetChanged();
    }

    public List<Voucher> getVoucherList() {
        return voucherList;
    }

    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_voucher_manage,parent,false);
        return new VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
        final Voucher voucher = voucherList.get(position);
        if(voucher == null){
            return;
        }

        holder.tvCode.setText("Mã: " + voucher.getCode());
        holder.tvDiscount.setText("Khuyến mãi: " + voucher.getDiscount() + " đồng");
        holder.tvMinBill.setText("Đơn tối thiểu: " + voucher.getMin_bill() + " đồng");
        holder.tvTime.setText("từ " + voucher.getStart_at() + " đến " + voucher.getEnd_at());
        holder.btnEditVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItem.editVoucher(voucher);
            }
        });
        holder.btnDeleteVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItem.deleteVoucher(voucher);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(voucherList != null){
            return  voucherList.size();
        }
        return 0;
    }

    public class VoucherViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCode,tvMinBill,tvDiscount,tvTime;
        private Button btnEditVoucher,btnDeleteVoucher;

        public VoucherViewHolder(@NonNull View view) {
            super(view);
            tvCode = view.findViewById(R.id.textViewCode);
            tvDiscount = view.findViewById(R.id.textViewDiscount);
            tvMinBill = view.findViewById(R.id.textViewMinBill);
            tvTime = view.findViewById(R.id.textViewTime);
            btnEditVoucher = view.findViewById(R.id.btnEditVoucher);
            btnDeleteVoucher = view.findViewById(R.id.btnDeleteVoucher);

        }
    }

    public interface IClickItemVoucher{
        void editVoucher(Voucher voucher);

        void deleteVoucher(Voucher voucher);
    }
}
