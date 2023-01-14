package com.example.and319;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class MoviePageActivity extends AppCompatActivity {

    private connect2Server connect;
    private ArrayList<String> movie_candidate;
    static ArrayList<Object> movie_all_object;
    static ArrayList<Object> movie_all_schedule;
    static MovieDetail movie_detail;
    private String username;
    private String locat;
    private String url;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);
        connect = new connect2Server("us-or-cera-1.natfrp.cloud", 22398);

        Intent pre = getIntent();
        movie_all_schedule = (ArrayList<Object>) pre.getSerializableExtra("movie_schedule");
        movie_detail = (MovieDetail) pre.getSerializableExtra("movie_detail");
        movie_all_object = (ArrayList<Object>) pre.getSerializableExtra("movies");
        username = pre.getStringExtra("id");
        System.out.println("MoviePageAct "+username);
        locat = pre.getStringExtra("locat");
        url = movie_detail.getPoster();

        final Bitmap bitmap;
        connect.download(url);
        try {
            Client.latch = new CountDownLatch(1);
            Client.latch.await();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        bitmap = connect2Server.singleBitap;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int width = 240;
        int height = 360;
        float scaleWidth = ((float) width) / w;
        float scaleHeight = ((float) height) / h;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix,
                true);

        movie_candidate = new ArrayList<String>(0);
        for(Object obj: movie_all_schedule){
            String time = (((OnlineFilm)obj).getTime()).substring(0, 16);
            movie_candidate.add(time + ((OnlineFilm)obj).getRoom() + ((OnlineFilm)obj).getPrice());
        }

        TextView movie_name = (TextView)findViewById(R.id.movie_name);
        TextView movie_type = (TextView)findViewById(R.id.movie_type);
        TextView movie_actor = (TextView)findViewById(R.id.movie_actor);
        TextView movie_simple = (TextView)findViewById(R.id.content);
        ImageView movie_poster = (ImageView)findViewById(R.id.imageView);
        movie_name.setText("Movie Name:   " + movie_detail.getmName());
        movie_type.setText("Director:   " + movie_detail.getDirector());
        movie_actor.setText("Theater:   " + movie_detail.getTheater());
        movie_simple.setText("Content:   " + movie_detail.getSimple());
        movie_poster.setImageBitmap(newbm);

        Button btn_detail = (Button) findViewById(R.id.detail);
        btn_detail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(MoviePageActivity.this , MovieDetailPage.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movie_detail", movie_detail);
                i.putExtra("id", username);
                i.putExtra("locat",locat);
                Bundle bundle_movie_schedule = new Bundle();
                bundle_movie_schedule.putSerializable("movie_schedule", movie_all_schedule);
                i.putExtras(bundle_movie_schedule);
                Bundle bundle_movie_list = new Bundle();
                bundle_movie_list.putSerializable("movies", movie_all_object);
                i.putExtras(bundle_movie_list);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        System.out.println("MoviePage"+username);
        lv = (ListView) findViewById(R.id.TimeList);
        String[] times = new String[movie_candidate.size()];
        movie_candidate.toArray(times);
        for (int i = 0; i<times.length; i++) {
            times[i] = toStandardTimeRoomPrice(times[i]);
        }
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, times));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MoviePageActivity.this,SelectSeatActivity.class);

                String candidate = movie_candidate.get(position);
                String time = candidate.substring(8, 16);
                String present_movie_room = candidate.substring(16, 17);
                String present_movie_price = candidate.substring(17);
                String present_movie_time = candidate.substring(0,16);
                String present_time_room = present_movie_time + "\n" + present_movie_room;
                try {
                    Client.latch = new CountDownLatch(1);
                    connect.send("buy\n" + present_time_room);
                    Client.latch.await();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                };

                ArrayList<Object>  seatSelectedInfo = connect.objectInfo;
                Bundle bundle_seat_info = new Bundle();
                bundle_seat_info.putSerializable("seat_Selected_Info",seatSelectedInfo);

                intent.putExtras(bundle_seat_info);
                intent.putExtra("time", present_movie_time);
                intent.putExtra("name", movie_detail.getmName());
                intent.putExtra("price", present_movie_price);
                intent.putExtra("room", present_movie_room);
                intent.putExtra("id", username);
                intent.putExtra("locat",locat);
                Bundle bundle_movie_schedule = new Bundle();
                bundle_movie_schedule.putSerializable("movie_schedule", movie_all_schedule);
                intent.putExtras(bundle_movie_schedule);
                Bundle bundle_movie_list = new Bundle();
                bundle_movie_list.putSerializable("movies", movie_all_object);
                intent.putExtras(bundle_movie_list);
                startActivity(intent);
            }
        });

        Button btn_cancel = (Button) findViewById(R.id.back);
        btn_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(MoviePageActivity.this , FilmListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movies", movie_all_object);
                i.putExtras(bundle);
                i.putExtra("id", username);
                i.putExtra("locat",locat);
                startActivity(i);
            }
        });
    }

    public String toStandardTimeRoomPrice(String TimePriceRoom){
        String beginTime = TimePriceRoom.substring(8, 10) + " : " +TimePriceRoom.substring(10, 12);
        String endTime = TimePriceRoom.substring(12, 14) + " : " + TimePriceRoom.substring(14, 16);
        String room = TimePriceRoom.substring(16, 17);
        String price = TimePriceRoom.substring(17, TimePriceRoom.length());
        String standardDateTime = new String(beginTime + "~" + endTime + "   " + "Room "+ room  + "   $" + price+" ");
        return standardDateTime;
    }
}