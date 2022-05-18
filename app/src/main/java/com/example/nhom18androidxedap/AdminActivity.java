package com.example.nhom18androidxedap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.model.Users;
import com.google.android.material.navigation.NavigationView;


public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private int userID;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ImageView imgAvatar;
    private TextView tvName,tvEmail;
    private NavigationView mNavigationView;
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        initUI();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.navigation_view_admin);
        mNavigationView.setNavigationItemSelectedListener(this);

        mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        showUserInformation(userID);
    }

    private void initUI(){
        mNavigationView = findViewById(R.id.navigation_view_admin);
        tvName = mNavigationView.getHeaderView(0).findViewById(R.id.tvName);
        tvEmail = mNavigationView.getHeaderView(0).findViewById(R.id.tvEmail);
        databaseHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("configure",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userID = sharedPreferences.getInt("id",-1);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.nav_user){
            Intent intent = new Intent(this,UserManageActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.nav_notify){
            Intent intent = new Intent(this,NotifyManageActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.nav_voucher){
            Intent intent = new Intent(this,VoucherManageActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.nav_my_profile){
            Intent intent = new Intent(this,MyProfileActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_sign_out){
            editor.putInt("id",0);
            editor.commit();
            Intent intent = new Intent(this,SignInActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.nav_change_password){
            Intent intent = new Intent(this,ChangePasswordActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.ql_bicycle){
            Intent intent = new Intent(this,listBicycle.class);
            startActivity(intent);
        }
        else if (id == R.id.ql_bill){
            Intent intent = new Intent(this,ListBill.class);
            intent.putExtra("code", "");
            startActivity(intent);
        }
        else  if (id == R.id.tke){
            Intent intent = new Intent(this,ReportMonth.class);
            startActivity(intent);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    public void showUserInformation(int id){
        Users user = databaseHelper.getUserById(id);
        if(user == null ){
            return;
        }
        String username = user.getUsername();
        String email = user.getGmail();

        if(username == null){
            tvName.setVisibility(View.GONE);
        }else {
            tvName.setText(username);
        }
        tvEmail.setText(email);
    }


}