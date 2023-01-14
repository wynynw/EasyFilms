package com.example.and319;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class BuyActivity extends AppCompatActivity {
    TextView movie_seat;
    TextView movie_name;
    TextView movie_time;
    TextView movie_price;
    String moviename;
    String movietime;
    String totalprice;
    String movieroom;
    //String seatNum;
    String username;
    String locat;
    String present_movie_price;
    ArrayList<String> seat = new ArrayList<String>();
    ArrayList<String> seatX = new ArrayList<>();
    ArrayList<String> seatY = new ArrayList<>();
    connect2Server connect;
    private ArrayList<Object> movie_all_object;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        connect = new connect2Server("us-or-cera-1.natfrp.cloud", 22398);
        Intent intent=getIntent();

        username = intent.getStringExtra("id");
        System.out.println("BuyAct "+username);
        locat = intent.getStringExtra("locat");
        movie_seat = (TextView)findViewById(R.id.movieseat);
        movie_name = (TextView)findViewById(R.id.moviename);
        movie_time = (TextView)findViewById(R.id.movietime);
        movie_price = (TextView)findViewById(R.id.price);

        movie_all_object = (ArrayList<Object>) intent.getSerializableExtra("movies");
        moviename = intent.getStringExtra("name");
        movietime = intent.getStringExtra("time");
        movieroom = intent.getStringExtra("room");
        totalprice = intent.getStringExtra("totalprice");
        present_movie_price = intent.getStringExtra("price");
        //seatNum = intent.getStringExtra("number");
        seat = (ArrayList<String>) intent.getSerializableExtra("movie_all_seat");
        seatX = (ArrayList<String>) intent.getSerializableExtra("seatX");
        seatY = (ArrayList<String>) intent.getSerializableExtra("seatY");

        for(int j = 0;j<seatX.size(); j++){
            System.out.println(seatX.get(j) + seatY.get(j));
        }

        movie_name.setText("Movie Name:\n" + moviename);
        movie_time.setText("Movie Time:\n" + toStandardDateTimeRoom(movietime + movieroom));
        movie_price.setText("Total Price: $" + totalprice);
        movie_seat.setText("Your Seat:\n" + seat.get(0));  //seat里面只有一个整坨，，，

        Button btn1 = (Button) findViewById(R.id.pay);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(BuyActivity.this ,PayActivity.class);
                i.putExtra("id", username);
                i.putExtra("locat",locat);
                i.putExtra("name", moviename);
                i.putExtra("time", movietime);
                i.putExtra("room", movieroom);
                //i.putExtra("number", seatNum);
                i.putExtra("movie_all_seat", (Serializable)seat);
                i.putExtra("seatX", seatX);
                i.putExtra("seatY", seatY);
                i.putExtra("totalprice", totalprice);
                i.putExtra("price", present_movie_price);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movies", movie_all_object);
                i.putExtras(bundle);
                //i.putExtra("approach", "card");
                startActivity(i);
            }
        });
        /*
        Button cash = (Button) findViewById(R.id.pay2);
        cash.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(BuyActivity.this ,PayActivity.class);
                i.putExtra("id", username);
                i.putExtra("name", moviename);
                i.putExtra("time", movietime);
                i.putExtra("room", movieroom);
                i.putExtra("number", seatNum);
                i.putExtra("movie_all_seat", (Serializable)seat);
                i.putExtra("seatX", seatX);
                i.putExtra("seatY", seatY);
                i.putExtra("totalprice", totalprice);
                i.putExtra("price", present_movie_price);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movies", movie_all_object);
                i.putExtra("approach", "cash");
                i.putExtras(bundle);

                startActivity(i);
            }
        });*/

        Button btn2 = (Button) findViewById(R.id.back);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(BuyActivity.this ,SelectSeatActivity.class);

                i.putExtra("id", username);
                i.putExtra("name", moviename);
                i.putExtra("time", movietime);
                i.putExtra("room", movieroom);
                //i.putExtra("number", seatNum);
                i.putExtra("movie_all_seat", (Serializable)seat);
                i.putExtra("seatX", seatX);
                i.putExtra("seatY", seatY);
                i.putExtra("price", present_movie_price);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movies", movie_all_object);
                i.putExtras(bundle);
                Bundle info = new Bundle();
                info.putSerializable("seat_Selected_Info", SelectSeatActivity.seat_Selected_Info);
                i.putExtras(info);
                startActivity(i);
            }
        });


    }





    public String toStandardTime(String DateTimeRoom) {
        String beginTime = DateTimeRoom.substring(8,10) + " : " + DateTimeRoom.substring(10,12);
        String endTime = DateTimeRoom.substring(12,14) + " : " + DateTimeRoom.substring(14,16);
        String standardDateTime = new String(beginTime + "~" + endTime);
        return standardDateTime;
    }

    public String toStandardDateTimeRoom(String DateTimeRoom) {
        String year = DateTimeRoom.substring(0,4);
        String month = DateTimeRoom.substring(4,6);
        String day = DateTimeRoom.substring(6,8);
        String beginTime = DateTimeRoom.substring(8,10) + " : " + DateTimeRoom.substring(10,12);
        String endTime = DateTimeRoom.substring(12,14) + " : " + DateTimeRoom.substring(14,16);
        String room = DateTimeRoom.substring(16,17);
        String standardDateTime = new String(year + "/" + month + "/" + day + "  " +
                beginTime + "~" + endTime + "   " + room + "Room" + "   ");
        return standardDateTime;
    }



}
