package com.example.and319;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class successActivity extends AppCompatActivity {

    String tickets;
    private ArrayList<Object> movie_all_object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Intent i = getIntent();

        tickets = i.getStringExtra("tickets");

        movie_all_object = (ArrayList<Object>)i.getSerializableExtra("movies");
        TextView ticket = (TextView) findViewById(R.id.ticketView);
        ticket.setText("Your Ticket Code is: \n" + tickets);

        Button btn1 = (Button) findViewById(R.id.backtomain);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(successActivity.this , personalcenter.class);
                //ArrayList<Object> movie_all_object = movie.movie_all_object;
                Bundle bundle_movie_list = new Bundle();
                bundle_movie_list.putSerializable("movies", movie_all_object);
                i.putExtra("id", FilmListActivity.username);
                i.putExtras(bundle_movie_list);
                startActivity(i);
            }
        });
    }
}
