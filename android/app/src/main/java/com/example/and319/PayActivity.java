package com.example.and319;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class PayActivity extends AppCompatActivity {

    private String moviename;
    private String movietime;
    private String totalprice;
    private String movieroom;
    //private String seatNum;
    private String paypassword;
    private String username;
    private String locat;
    private ArrayList<Object> seat_Selected_Info = new ArrayList<>();
    private ArrayList<String> seatX = new ArrayList<>();
    private ArrayList<String> seatY = new ArrayList<>();
    private EditText password;
    connect2Server connect;
    private String tickets;
    private ArrayList<Object> movie_all_object;
    private String email;
    //private String approach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connect = new connect2Server("us-or-cera-1.natfrp.cloud", 22398);
        
        setContentView(R.layout.activity_pay);
        Intent intent = getIntent();
        //approach = intent.getStringExtra("approach");
        moviename = intent.getStringExtra("name");
        movietime = intent.getStringExtra("time");
        movieroom = intent.getStringExtra("room");
        totalprice = intent.getStringExtra("totalprice");
        //seatNum = intent.getStringExtra("number");
        //seat = (ArrayList<String>) intent.getSerializableExtra("movie_all_seat");
        seatX = (ArrayList<String>) intent.getSerializableExtra("seatX");
        seatY = (ArrayList<String>) intent.getSerializableExtra("seatY");
        username = intent.getStringExtra("id");
        password = (EditText) findViewById(R.id.password);
        movie_all_object = (ArrayList<Object>) intent.getSerializableExtra("movies");
        locat = intent.getStringExtra("locat");

        try {
            Client.latch = new CountDownLatch(1);
            connect.send("personal\n" + username);
            Client.latch.await();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        };

        email = ((Personal_info)connect.objectInfo.get(0)).getEmail();

        Button btn_back = (Button) findViewById(R.id.pay_back);
        btn_back.setOnClickListener(new View.OnClickListener(){ //Cancel button
            @Override
            public void onClick(View view){
                try {
                    Client.latch = new CountDownLatch(1);
                    connect.send("buy\n" + SelectSeatActivity.present_movie_time + "\n" +
                            SelectSeatActivity.present_movie_room);
                    Client.latch.await();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                };

                seat_Selected_Info = connect.objectInfo;

                Intent intent = new Intent(PayActivity.this, SelectSeatActivity.class);
                intent.putExtra("id", username);
                intent.putExtra("locat",locat);
                intent.putExtra("name", SelectSeatActivity.present_movie_name);
                intent.putExtra("time", SelectSeatActivity.present_movie_time);
                intent.putExtra("room", SelectSeatActivity.present_movie_room);
                intent.putExtra("seat_Selected_Info", (Serializable)seat_Selected_Info);
                intent.putExtra("price", SelectSeatActivity.present_movie_price);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movies", movie_all_object);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });



        Button btn_pay = (Button) findViewById(R.id.payconfirm);
        btn_pay.setOnClickListener(new View.OnClickListener(){ //Cancel button
            @Override
            public void onClick(View view){

                paypassword = password.getText().toString();
                if(isValid() != 0){

                    /*if(approach.equals("card")){
                        approach = "0";
                    }
                    else{
                        approach = "1";
                    }*/

                   /* String seats = "preorder\n" +approach+ "\n" + paypassword + "\n" + seatX.size() + "\n" + movietime.substring(8, 16) +
                            "\n" +  movietime.substring(0, 8) + "\n" + username +  "\n" + moviename + "\n" + movieroom +
                            "\n" + totalprice + "\n";*///去掉approach，只留一个按钮
                    String seats = "preorder\n" +"0"+"\n"+ paypassword + "\n" + seatX.size() + "\n" + movietime.substring(8, 16) +
                            "\n" +  movietime.substring(0, 8) + "\n" + username +  "\n" + moviename + "\n" + movieroom +
                            "\n" + totalprice + "\n";
                    for(int j = 0; j<seatX.size(); j++){
                        System.out.println("Real seat: " + seatX.get(j) + "\n" + seatY.get(j));
                        seats += seatX.get(j) + "\n" + seatY.get(j) + "\n";
                    }

                    seats += email + "\n";

                    try {
                        Client.latch = new CountDownLatch(1);
                        connect.send(seats);
                        Client.latch.await();
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }

                    tickets = connect.stringInfo.get(0);

                    //System.out.println(tickets);
                    if(tickets.equals("0")){
                        Toast.makeText(PayActivity.this, "Sorry! One or more seat has been taken！ \n" +
                                "Jumping to the select page...", Toast.LENGTH_SHORT).show();
                        try {
                            Thread.sleep(3000);
                        } catch(InterruptedException e){
                            e.printStackTrace();
                        }

                        try {
                            Client.latch = new CountDownLatch(1);
                            connect.send("buy\n" + SelectSeatActivity.present_movie_time + "\n" +
                                    SelectSeatActivity.present_movie_room);
                            Client.latch.await();
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();
                        };

                        seat_Selected_Info = connect.objectInfo;

                        Intent intent = new Intent(PayActivity.this, SelectSeatActivity.class);
                        intent.putExtra("id", username);
                        intent.putExtra("name", SelectSeatActivity.present_movie_name);
                        intent.putExtra("time", SelectSeatActivity.present_movie_time);
                        intent.putExtra("room", SelectSeatActivity.present_movie_room);
                        intent.putExtra("seat_Selected_Info", (Serializable)seat_Selected_Info);
                        intent.putExtra("price", SelectSeatActivity.present_movie_price);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("movies", movie_all_object);
                        intent.putExtras(bundle);
                        intent.putExtra("locat",locat);
                        startActivity(intent);

                    }
                    else{
                        Intent i = new Intent(PayActivity.this , successActivity.class);
                        i.putExtra("tickets", tickets);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("movies", movie_all_object);
                        i.putExtras(bundle);
                        startActivity(i);
                    }
                }


            }
        });
    }

    private int isValid(){        //This is the function that judge if the information is invalid
        if(TextUtils.isEmpty(paypassword)){
            Toast.makeText(PayActivity.this, "Please Enter the pay password", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return 1;
    }
}
