package com.example.and319;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class personalcenter extends AppCompatActivity {

    private connect2Server connect;
    private Personal_info personal_info;
    private ArrayList<Object> movie_all_object;
    private String username;
    private String locat;

    private LocationManager locationManager;
    private String locationProvider = null;
    private TextView titleTxt;
    private Toolbar toolbar;
    private void initTitleOne() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        setTitle("Personal Center", true);
    }

    public void setTitle(String title, boolean isCenter) {
        titleTxt = (TextView) findViewById(R.id.toolbar_title);
        if (isCenter) {
            titleTxt.setText(title);
        } else {
            setTitle(title);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalcenter);
        initTitleOne();
        Intent loginView = getIntent();
        movie_all_object = (ArrayList<Object>) loginView.getSerializableExtra("movies");
        username = loginView.getStringExtra("id");

        connect = new connect2Server("us-or-cera-1.natfrp.cloud", 22398);
        try {
            Client.latch = new CountDownLatch(1);
            connect.send("personal\n" + loginView.getStringExtra("id"));
            Client.latch.await();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        personal_info = (Personal_info) connect.objectInfo.get(0);

        TextView u = (TextView) findViewById(R.id.user);
        u.setText(username + ", Welcome!");

        requestLocate();


        Button btn_movies = (Button) findViewById(R.id.movies);
        btn_movies.setText("MOVIE LIST");
        btn_movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(personalcenter.this, FilmListActivity.class);
                TextView loshow=(TextView)findViewById(R.id.locateText);
                String user_locate=loshow.getText().toString();
                i.putExtra("id", username);
                i.putExtra("locat",user_locate);//字符串到filmlist里面处理
                startActivity(i);
            }
        });

        Button btn_ticket = (Button) findViewById(R.id.view);
        btn_ticket.setText("MY Tickets");
        btn_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(personalcenter.this, TicketListActivity.class);
                i.putExtra("id", username);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movies", movie_all_object);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        Button btn_set = (Button) findViewById(R.id.personal_setting);
        btn_set.setText("PERSONAL SETTING");
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(personalcenter.this, personalsetting.class);
                i.putExtra("id", username);
                startActivity(i);
            }
        });


        Button btn_logout = (Button) findViewById(R.id.logout);
        btn_logout.setText("LOGOUT");
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(personalcenter.this, LoginActivity.class);
                try {
                    Client.latch = new CountDownLatch(1);
                    connect.send("logout\n");
                    Client.latch.await();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                startActivity(i);
            }
        });



    }

    public ArrayList<Object> getMovie_all_object() {
        return movie_all_object;
    }

    public void requestLocate(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                //request
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        }else{
            //location manager
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            //provider
            List<String> providers = locationManager.getProviders(true);
            if (providers.contains(LocationManager.GPS_PROVIDER)) {
                locationProvider = LocationManager.GPS_PROVIDER;
            } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                locationProvider = LocationManager.NETWORK_PROVIDER;
            } else {
                Toast.makeText(this, "No Avaliable Locator", Toast.LENGTH_SHORT).show();
                return;
            }
            //get location
            locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
        }
    };
    //callback after request permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                // allowed
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //location manager
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    //provider
                    List<String> providers = locationManager.getProviders(true);
                    if (providers.contains(LocationManager.GPS_PROVIDER)) {
                        locationProvider = LocationManager.GPS_PROVIDER;
                    } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                        locationProvider = LocationManager.NETWORK_PROVIDER;
                    } else {
                        Toast.makeText(this, "No Avaliable Locator", Toast.LENGTH_SHORT).show();
                    }
                    //get location
                    try {
                        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);

                    } catch (SecurityException e) {
                    }
                }
                break;
            }
        }
    }

    public LocationListener locationListener = new LocationListener() {
        //status change
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
                }
        // enable
        @Override
        public void onProviderEnabled(String provider) {
                }
        // disable
        @Override
        public void onProviderDisabled(String provider) {
                }
        //location change
        @Override
        public void onLocationChanged(Location location) {
                if (location != null) {
                    TextView loshow=(TextView)findViewById(R.id.locateText);
                    NumberFormat df= NumberFormat.getNumberInstance() ;
                    df.setMaximumFractionDigits(2);
                    locat=df.format(location.getLatitude())+","+df.format(location.getLongitude());
                    loshow.setText("From "+locat);
                }
            }
        };
}