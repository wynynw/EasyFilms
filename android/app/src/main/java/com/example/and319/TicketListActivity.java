package com.example.and319;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TicketListActivity extends AppCompatActivity {

    private connect2Server connect;
    private ArrayList<Object> ticket_all_object;
    private List<Ticket> ticket_list;
    private String username;
    private ArrayList<Object> movie_all_object;
    private RecyclerView.LayoutManager rLayoutManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int i = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewticket);
        connect = new connect2Server("us-or-cera-1.natfrp.cloud", 22398);
        Intent personal = getIntent();
        username = personal.getStringExtra("id");
        movie_all_object = (ArrayList<Object>) personal.getSerializableExtra("movies");

        System.out.println(username);

        try {
            Client.latch = new CountDownLatch(1);
            connect.send("order\n" + username);
            Client.latch.await();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        };

        ticket_list = new ArrayList<>();
        ticket_all_object = connect.objectInfo;
        fill_ticket_list(ticket_all_object);
        System.out.println("ticket num: " + ticket_list.size());

        RecyclerView listView = (RecyclerView)findViewById(R.id.ticket_recyclerview);
        listView.setHasFixedSize(true);
        TicketListAdapter TicketListAdapter = new TicketListAdapter(TicketListActivity.this, ticket_list);
        rLayoutManger = new LinearLayoutManager(this);
        listView.setLayoutManager(rLayoutManger);
        listView.setAdapter(TicketListAdapter);


        Button btn_back = (Button) findViewById(R.id.back_personalcenter);
        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(TicketListActivity.this , personalcenter.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movies", movie_all_object);
                i.putExtras(bundle);
                i.putExtra("id", username);
                startActivity(i);
            }
        });

    }

    public void fill_ticket_list(ArrayList<Object> ticket_all_object) {
        int i =0, count = 0;
        ArrayList<String> temp = new ArrayList<>();
        Ticket ticket;

        /*********这个地方修改temp加入的各项ticket的值，多两个***********/
        for(i=0; i<ticket_all_object.size(); i++) {
            temp.add(((Order)ticket_all_object.get(i)).getMName());
            String datetime = ((Order)ticket_all_object.get(i)).getDate() +
                    ((Order)ticket_all_object.get(i)).getMovieTime();
            temp.add(toStandardDateTime(datetime));
            temp.add(toStandardSeat(((Order)ticket_all_object.get(i)).getRoomX() +
                    ((Order)ticket_all_object.get(i)).getRoomY()));
            temp.add(((Order)ticket_all_object.get(i)).getID());
            temp.add("Not Used");
            ticket = new Ticket(temp.get(0), temp.get(1), temp.get(2), temp.get(3), temp.get(4));
            ticket_list.add(ticket);
            temp.clear();
        }
        /*********这个地方修改temp加入的各项ticket的值，多两个***********/


    }

    public String toStandardDateTime(String DateTime) {
        String year = DateTime.substring(0,4);
        String month = DateTime.substring(4,6);
        String day = DateTime.substring(6,8);
        String beginTime = DateTime.substring(8,10) + " : " + DateTime.substring(10,12);
        String endTime = DateTime.substring(12,14) + " : " + DateTime.substring(14,16);
        String standardDateTime = new String(year + "/" + month + "/" + day + "  " + beginTime + "~" + endTime );
        return standardDateTime;
    }

    public String toStandardSeat(String Seat) {

        String line = Seat.substring(0,1);
        String row = Seat.substring(1,2);
        String standardSeat = line + " line " + row + " row ";
        return standardSeat;
    }

}
