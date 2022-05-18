package com.example.nhom18androidxedap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;

import com.example.nhom18androidxedap.database.DatabaseHelper;
import com.example.nhom18androidxedap.databinding.ActivityMapsBinding;
import com.example.nhom18androidxedap.model.Users;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;






public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks ,GoogleApiClient.OnConnectionFailedListener , NavigationView.OnNavigationItemSelectedListener {

    int REQUEST_CODE_CAMERA = 123;
    FloatingActionButton fab,fabHelp,fabUnlock;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    SearchView searchView;
    private CodeScanner mCodeScanner;
    private DrawerLayout mDrawerLayout;
    private FusedLocationProviderClient mLocationClient;
    String username,codeBill;
    DatabaseHelper dataBaseHelper = new DatabaseHelper(MapsActivity.this);
    TextView tvName,tvEmail;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int userID;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent1 = getIntent();
        username = intent1.getStringExtra("username");
        codeBill = intent1.getStringExtra("codeBill");
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        searchView = findViewById(R.id.sv_location);
        fab  = (FloatingActionButton) findViewById(R.id.fab);
        fabHelp = (FloatingActionButton) findViewById(R.id.fab_help);
        fabUnlock = (FloatingActionButton) findViewById(R.id.fab_unlock);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        initMap();
        mLocationClient = new FusedLocationProviderClient(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,
                toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close) ;
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
//viet
        tvName = navigationView.getHeaderView(0).findViewById(R.id.fullname);
        tvEmail = navigationView.getHeaderView(0).findViewById(R.id.gmail);
        sharedPreferences = getSharedPreferences("configure",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userID = sharedPreferences.getInt("id",-1);
        Log.d("userIDViet",String.valueOf(userID));
        showUserInformation(userID);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getCurrentLocation();
                //Log.v("myTag","FAB Clicked");
                if(ActivityCompat.checkSelfPermission(MapsActivity.this
                        ,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                    getLocation();
                }else
                {
                    ActivityCompat.requestPermissions(MapsActivity.this
                            ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
            }
        });
        fabHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseHelper.updateStatusBill();
                clickOpenBottomSheetDialog();
            }
        });
        fabUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseHelper.updateStatusBill();
                int idUser = dataBaseHelper.getIdUser(username);
                int count = dataBaseHelper.isHaveBill(idUser);
                if(count != 0 )
                {
                    Intent intent = new Intent(MapsActivity.this,UnlockActivity2.class);
                    intent.putExtra("username",username);
                    startActivity(intent);
                }else
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MapsActivity.this);
                    alertDialog.setTitle("Thông báo!");
                    alertDialog.setMessage("Bạn phải thuê xe để mở khóa");
                    alertDialog.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialog.show();
                }
            }
        });
    }

    private void clickOpenBottomSheetDialog() {
        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_layout,null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(viewDialog);
        bottomSheetDialog.show();

        Button btnHelp1 =  viewDialog.findViewById(R.id.help1);
        Button btnPhoneCall =  viewDialog.findViewById(R.id.btn_phoneCall);

        btnHelp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(MapsActivity.this,new String[]{Manifest.permission.CAMERA},REQUEST_CODE_CAMERA);
            }
        });

        btnPhoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0912345678"));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        int idUser1 = dataBaseHelper.getIdUser(username);
        int count1 = dataBaseHelper.isHaveBill(idUser1);
        if(count1 !=0 )
        {
            if(requestCode == REQUEST_CODE_CAMERA && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(MapsActivity.this, ReportActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        }else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Thông báo!");
            alertDialog.setMessage("Bạn cần mua vé trước khi báo sự cố !");
            alertDialog.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertDialog.show();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @SuppressLint("MissingPermission")
    private void getLocation()
    {
        mLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location!=null)
                {
                    try {
                        Geocoder geocoder = new Geocoder(MapsActivity.this,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );
                        LatLng latLng = new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,15);
                        mMap.moveCamera(cameraUpdate);
                        mMap.addMarker(new MarkerOptions().position(latLng).title(addresses.get(0).getAddressLine(0)));

                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation()
    {
        mLocationClient.getLastLocation().addOnCompleteListener(task ->
        {
            if(task.isSuccessful())
            {
                Location location = task.getResult();
                gotoLocation(location.getLatitude(),location.getLongitude());
            }
        });
    }

    private void gotoLocation(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude,longitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,18);
        mMap.moveCamera(cameraUpdate);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        searchView = findViewById(R.id.sv_location);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null || !location.equals(""))
                {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,6));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_ticket)
        {
            Intent intent = new Intent(MapsActivity.this,BuyTicketActivity.class);
            intent.putExtra("username",username);
            dataBaseHelper.updateStatusBill();
            startActivity(intent);
        }
        else if(id == R.id.nav_notify)
        {
            Intent intent = new Intent(MapsActivity.this,NotifyActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.nav_my_profile_customer){
            Intent intent = new Intent(this,MyProfileActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_sign_out_customer){
            editor.putInt("id",0);
            editor.commit();
            Intent intent = new Intent(this,SignInActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.nav_change_password_customer){
            Intent intent = new Intent(this,ChangePasswordActivity.class);
            startActivity(intent);
        }
        mDrawerLayout.close();
        return true;
    }
    //viet

    public void showUserInformation(int id){
        Users user = dataBaseHelper.getUserById(id);
        if(user == null ){
            return;
        }
        String fullname = user.getFullname();
        String email = user.getGmail();

        if(fullname == null){
            tvName.setVisibility(View.GONE);
        }else {
            tvName.setText(fullname);
        }
        tvEmail.setText(email);
    }

}