package com.example.and319;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectSeatActivity extends AppCompatActivity {
    private String username;
    private String locat;
    private int[][] seatList;
    private String seatNum;
    private SelectSeatView searchSeat;
    private TextView tvResult;
    static ArrayList<Object> seat_Selected_Info;

    private String totalprice;
    static String present_movie_room;
    static String present_movie_name;
    static String present_movie_time;
    static String present_movie_price;
    private ArrayList<Object> movie_all_object;

    ArrayList<String> seat = new ArrayList<String>();
    ArrayList<String> seatX = new ArrayList<>();
    ArrayList<String> seatY = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        Intent intent=getIntent();

        movie_all_object = (ArrayList<Object>) intent.getSerializableExtra("movies");
        username = intent.getStringExtra("id");
        System.out.println("SelectSeatAct "+username);
        locat = intent.getStringExtra("locat");
        present_movie_name = intent.getStringExtra("name");
        present_movie_time = intent.getStringExtra("time");
        present_movie_room = intent.getStringExtra("room");
        present_movie_price = intent.getStringExtra("price");
        seat_Selected_Info = (ArrayList<Object>) intent.getSerializableExtra("seat_Selected_Info");

        //System.out.println("--time->>"+time);
        TextView movie_time=(TextView)findViewById(R.id.time);
        movie_time.setText("Time: " + toStandardTime(present_movie_time));


        Button btn_cancel = (Button) findViewById(R.id.back);
        btn_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SelectSeatActivity.this , MoviePageActivity.class);
                intent.putExtra("id", username);
                intent.putExtra("locat",locat);
                Bundle detail = new Bundle();
                detail.putSerializable("movie_detail", MoviePageActivity.movie_detail);
                intent.putExtras(detail);
                Bundle schedule = new Bundle();
                schedule.putSerializable("movie_schedule", MoviePageActivity.movie_all_schedule);
                intent.putExtras(schedule);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movies", movie_all_object);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



        Button btn_confirm = (Button) findViewById(R.id.confirm_button);
        btn_confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (seatX.size()==0) {
                   Toast.makeText(SelectSeatActivity.this, "Please select seats ", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SelectSeatActivity.this, BuyActivity.class);
                    intent.putExtra("id", username);
                    intent.putExtra("name", present_movie_name);
                    intent.putExtra("time", present_movie_time);
                    intent.putExtra("room", present_movie_room);
                    //intent.putExtra("number", seatNum);
                    intent.putExtra("movie_all_seat", (Serializable) seat);
                    intent.putExtra("seatX", seatX);
                    intent.putExtra("seatY", seatY);
                    intent.putExtra("totalprice", totalprice);
                    intent.putExtra("price", present_movie_price);
                    intent.putExtra("locat", locat);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("movies", movie_all_object);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        searchSeat = findViewById(R.id.search_seat);
        tvResult = findViewById(R.id.tv_result);

        seatList = new int[9][];
        for (int i = 0; i < 9; i++) {
            int[] indes = new int[13];
            for (int x = 0; x < 13; x++) {
                if (i == 4) {
                    if (x < 3 || x > 9) {
                        indes[x] = 0;
                    }
                    else {
                        indes[x] = 1;
                    }
                }
                else {
                    indes[x] = 1;
                }
            }
            seatList[i] = indes;
        }

        for(Object obj: seat_Selected_Info){
            int x = ((Seat) obj).getX();
            int y = ((Seat) obj).getY();
            seatList[x-1][y-1] = 2;
        }

        searchSeat.setSeatList(seatList);

        searchSeat.setChildSelectListener(new ChildSelectListener() {
            @Override
            public void onChildSelect(List<SelectRectBean> stringList) {
                seat.clear();
                seatX.clear();
                seatY.clear();
                totalprice = "";
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < stringList.size(); i++) {
                    SelectRectBean selectRectBean = stringList.get(i);
                    stringBuffer.append("line"+selectRectBean.getRow()+" ");
                    stringBuffer.append("seat"+selectRectBean.getColumn() + "\n");
                    seatX.add(selectRectBean.getRow() + "");
                    seatY.add(selectRectBean.getColumn() + "");
                }
                tvResult.setText(stringBuffer.toString());
                seat.add(stringBuffer.toString());
                totalprice = ((Integer.valueOf(present_movie_price)) * stringList.size()) + "";
                //seatNum = String.valueOf(stringList.size());
            }
        });

    }

    public String toStandardTime(String DateTimeRoom) {
        String beginTime = DateTimeRoom.substring(8,10) + " : " + DateTimeRoom.substring(10,12);
        String endTime = DateTimeRoom.substring(12,14) + " : " + DateTimeRoom.substring(14,16);
        String standardDateTime = new String(beginTime + "~" + endTime);
        return standardDateTime;
    }



}
