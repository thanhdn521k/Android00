package com.example.nhom18androidxedap.adapterAdmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.nhom18androidxedap.R;
import com.example.nhom18androidxedap.model.Users;

import java.util.List;

public class UserAdapterAdmin extends RecyclerView.Adapter<UserAdapterAdmin.UserViewHolder>{

    private List<Users> userList;
    private IClickItemUser iClickItem;

    public UserAdapterAdmin(IClickItemUser iClickItem) {
        this.iClickItem = iClickItem;
    }

    public void setData(List<Users> list){
        this.userList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_user_manage,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        final Users user = userList.get(position);
        if(user == null){
            return;
        }

        holder.tvID.setText("ID: " + user.getId());
        holder.tvUserName.setText( user.getUsername());
        holder.btnShowUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItem.showUser(user);
            }
        });
        holder.btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItem.deleteUser(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(userList != null){
            return  userList.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView tvID,tvUserName;
        private Button btnShowUser,btnDeleteUser;

        public UserViewHolder(@NonNull View view) {
            super(view);

            tvID = view.findViewById(R.id.tv_id);
            tvUserName = view.findViewById(R.id.textView_username);
            btnShowUser = view.findViewById(R.id.btnShowUser);
            btnDeleteUser = view.findViewById(R.id.btnDeleteUser);
        }
    }

    public interface IClickItemUser{
        void showUser(Users user);

        void deleteUser(Users user);
    }
}
