package com.example.and319;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.example.and319.FilmListAdapter.InnerItemOnclickListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;

import android.widget.AdapterView.OnItemClickListener;
import android.util.AttributeSet;

public class FilmListActivity extends AppCompatActivity implements InnerItemOnclickListener,
        OnItemClickListener {
    private ListView listView;
    private FilmListAdapter filmListAdapter;
    private List<Film> film_list;
    private connect2Server connect;
    //private ArrayList<Object> movie_all_object;
    static ArrayList<Object> movie_all_object;
    static String username;
    static String locat;
    private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    //static Bitmap bitmap;
    private TextView titleTxt;
    private Toolbar toolbar;
    private FusedLocationProviderClient fusedLocationClient;
    private double user_lo;
    private double user_la;


    //文字滚动
    public class MarqueeTextView extends androidx.appcompat.widget.AppCompatTextView {
        public MarqueeTextView(Context context) {
            super(context);
        }

        public MarqueeTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        public boolean isFocused() {
            return true;
        }
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmovie);
        initTitleOne();

        connect = new connect2Server("us-or-cera-1.natfrp.cloud", 22398);

        Intent intent = getIntent();
        //movie_all_object = (ArrayList<Object>) login.getSerializableExtra("movies");
        try {
            Client.latch = new CountDownLatch(1);
            connect.send("show\n");
            Client.latch.await();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        };

        movie_all_object = connect.objectInfo;
        username = intent.getStringExtra("id");
        System.out.println("FilmListAct "+username);

        //get current location
        locat=intent.getStringExtra("locat");
        System.out.println("-------------"+locat);
        String[] temp1,temp2;
        temp1 = locat.split(" ");
        temp2 = temp1[1].split(",");
        user_lo = Double.parseDouble(temp2[0]);
        user_la = Double.parseDouble(temp2[1]);


        listView = (ListView)findViewById(R.id.ListViewFilmList);
        film_list = new ArrayList<>();

        int i;
        for(i=0; i<movie_all_object.size();i++){
            MovieDetail movie = (MovieDetail) movie_all_object.get(i);
            Bitmap bitmap;
            connect.download(movie.getPoster());
            try {
                Client.latch = new CountDownLatch(1);
                Client.latch.await();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        bitmaps = connect2Server.bitmaps;
        //bitmaps = connect.getBitmaps();


        for(i=0; i<movie_all_object.size();i++){
            //System.out.println(connect2Server.bitmaps.size());
            //System.out.println("Print Size： " + bitmaps.get(i).getByteCount() + "\n");
            //System.out.println("实际图片大小为： " + connect.getBitmaps().get(i).getByteCount() + "\n");
            //System.out.println("Actual Size： " + connect2Server.bitmaps.get(i).getByteCount() + "\n");
            MovieDetail movie = (MovieDetail) movie_all_object.get(i);
            double[] lola = movie.getLoLa();//获取电影经纬度
            double distance = getDistance(lola, user_lo, user_la);
            Film film  = new Film(bitmaps.get(i), movie.getmName(),"Theater:   " + movie.getTheater(), distance);
            film_list.add(film);
            System.out.println(movie.getmName() + " " + distance);
        }

        Collections.sort(film_list, new Comparator<Film>() {
            @Override
            public int compare(Film f1, Film f2) {
                if (f1.getDistance() >= f2.getDistance()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        filmListAdapter = new FilmListAdapter(FilmListActivity.this, film_list);
        filmListAdapter.setOnInnerItemOnClickListener(this);
        listView.setAdapter(filmListAdapter);
        listView.setOnItemClickListener(this);

        Button  btn_personal = (Button) findViewById(R.id.personal_center);
        btn_personal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(FilmListActivity.this , personalcenter.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movies", movie_all_object);
                i.putExtras(bundle);
                i.putExtra("id", username);
                startActivity(i);
            }
        });

    }
//初始化导航栏
    private void initTitleOne() {
        toolbar=(Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        setTitle("Movie List",true);
    }

//导航栏标题
    public void setTitle(String title,boolean isCenter){
        titleTxt = (TextView)findViewById(R.id.toolbar_title);
        if(isCenter){
            titleTxt.setText(title);
        }else{
            setTitle(title);
        }
    }


    @Override
    public void itemClick(View view) {
        int position;
        position = (Integer) view.getTag();
        String filmname = film_list.get(position).getFilmName();
        ArrayList<Object> movie_all_schedule;
        MovieDetail movieDetail;

        Bundle bundle_movie_list = new Bundle();
        Bundle bundle_movie_detail = new Bundle();
        Bundle bundle_movie_schedule = new Bundle();

        bundle_movie_list.putSerializable("movies", movie_all_object);
        try {
            Client.latch = new CountDownLatch(1);
            connect.send("movie\n" + filmname);   //接受moviedetail，一个特定电影的对象

            Client.latch.await();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        };
        bundle_movie_detail.putSerializable("movie_detail",(MovieDetail)connect.objectInfo.get(0));


        try {
            Client.latch = new CountDownLatch(1);
            connect.send("schedule\n" + filmname);  //接受onlinefilm，电影和档期
            Client.latch.await();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        };
        movie_all_schedule = connect.objectInfo;
        bundle_movie_schedule.putSerializable("movie_schedule", movie_all_schedule);


        Intent i = new Intent(FilmListActivity.this , MoviePageActivity.class);
        i.putExtra("locat",locat);
        i.putExtra("id", username);
        i.putExtras(bundle_movie_detail);
        i.putExtras(bundle_movie_schedule);
        i.putExtras(bundle_movie_list);
        startActivity(i);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public double getDistance(double[] lola, double user_lo, double user_la) {
        double dx = lola[0] - user_lo;
        double dy = lola[1] - user_la;
        return dx * dx + dy * dy;
    }

}