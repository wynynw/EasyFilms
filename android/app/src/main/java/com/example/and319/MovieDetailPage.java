package com.example.and319;

import android.content.Intent;
import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class MovieDetailPage extends AppCompatActivity {

    private MovieDetail movie_detail;
    private String username;
    private String locat;
    private connect2Server connect;
    private Bitmap bitmap;
    private ImageView image;
    private ArrayList<Object> movie_all_schedule;
    private ArrayList<Object> movie_all_object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        movie_detail = (MovieDetail) intent.getSerializableExtra("movie_detail");
        username = intent.getStringExtra("id");
        locat = intent.getStringExtra("locat");
        movie_all_schedule = (ArrayList<Object>) intent.getSerializableExtra("movie_schedule");
        movie_all_object = (ArrayList<Object>) intent.getSerializableExtra("movies");

        connect = new connect2Server("us-or-cera-1.natfrp.cloud", 22398);

        connect.download(movie_detail.getPoster());
        try {
            Client.latch = new CountDownLatch(1);
            Client.latch.await();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        bitmap = connect2Server.singleBitap;

        if(bitmap == null){
            System.out.println("null!!!");
        }
        else {
            System.out.println("size：" + bitmap.getByteCount());
        }

        image = (ImageView) findViewById(R.id.single_imageView);
        image.setImageBitmap(bitmap);


        TextView director = (TextView) findViewById(R.id.dir);
        director.setText("Director: " + movie_detail.getDirector());

        TextView actor = (TextView) findViewById(R.id.Ac);
        actor.setText("Theater: " + movie_detail.getTheater());

        TextView date = (TextView) findViewById(R.id.da);
        date.setText("TimeOn: " + movie_detail.getTimeOn().substring(0,4) + "/" +
                movie_detail.getTimeOn().substring(4, 6) + "/" +
                movie_detail.getTimeOn().substring(6, 8));

        TextView content = (TextView) findViewById(R.id.content);
        content.setText(movie_detail.getDetail());

        Button  btn8 = (Button) findViewById(R.id.ret);
        btn8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(MovieDetailPage.this , MoviePageActivity.class);
                Bundle object = new Bundle();
                object.putSerializable("movies", movie_all_object);
                i.putExtras(object);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movie_detail", movie_detail);
                Bundle bundle_movie_schedule = new Bundle();
                bundle_movie_schedule.putSerializable("movie_schedule", movie_all_schedule);
                i.putExtras(bundle_movie_schedule);
                i.putExtras(bundle);
                i.putExtra("id", username);
                i.putExtra("locat",locat);
                startActivity(i);
            }
        });
    }
}
