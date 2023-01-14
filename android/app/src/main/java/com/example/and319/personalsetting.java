package com.example.and319;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class personalsetting extends AppCompatActivity {

    private connect2Server connect;
    private Personal_info personal_info;
    private ArrayList<Object> movie_all_object;
    private String username;

    private TextView titleTxt;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalsetting);
        initTitleOne();
        Intent personalcenter = getIntent();
        username = personalcenter.getStringExtra("id");
        System.out.println("==============="+username);
        movie_all_object = (ArrayList<Object>) personalcenter.getSerializableExtra("movies");

        connect = new connect2Server("us-or-cera-1.natfrp.cloud", 22398);
        try {
            Client.latch = new CountDownLatch(1);
            connect.send("personal\n" + personalcenter.getStringExtra("id"));
            Client.latch.await();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        personal_info = (Personal_info) connect.objectInfo.get(0);

        Button  btn2 = (Button) findViewById(R.id.password);
        btn2.setText("Change password");
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(personalsetting.this , changethepassword.class);
                i.putExtra("id",username);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movies", movie_all_object);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        Button  btn3 = (Button) findViewById(R.id.email);
        btn3.setText("Email: " + personal_info.getEmail());
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(personalsetting.this , changetheemailaddress.class);
                i.putExtra("id",username);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movies", movie_all_object);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        String gender = new String();

        Button  btn4 = (Button) findViewById(R.id.gender);
        if(personal_info.getGender() == null){
            gender = "Gender";
        }
        else if(personal_info.getGender().equals("1")){
            gender = "Male";
        }
        else {
            gender = "Female";
        }
        btn4.setText(gender);
        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(personalsetting.this , changeyourgender.class);
                i.putExtra("id",username);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movies", movie_all_object);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        Button  btn5 = (Button) findViewById(R.id.phone);
        String phone = new String();
        if(personal_info.getPhone() == null){
            phone = "Set phone number";
        }
        else{
            phone = personal_info.getPhone();
        }
        btn5.setText("Phone: " + phone);
        btn5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(personalsetting.this , changeyourgender.class);
                i.putExtra("id",username);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movies", movie_all_object);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(personalsetting.this , changethephonenumber.class);
                i.putExtra("id",username);Bundle bundle = new Bundle();
                bundle.putSerializable("movies", movie_all_object);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        Button  btn6 = (Button) findViewById(R.id.birth);
        if(personal_info.getBirthday() != null){
            btn6.setText("Birth: " + personal_info.getBirthday());
        }
        else{
            btn6.setText("Set your bitrhday");
        }

        btn6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(personalsetting.this , changethebirth.class);
                i.putExtra("id",username);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movies", movie_all_object);
                i.putExtras(bundle);
                startActivity(i);
            }
        });


        Button btn_cancel = (Button) findViewById(R.id.back_personalcenter);
        btn_cancel.setText("BACK");

        btn_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(personalsetting.this , personalcenter.class);
                i.putExtra("id",username);Bundle bundle = new Bundle();
                bundle.putSerializable("movies", movie_all_object);
                i.putExtras(bundle);
                startActivity(i);
            }
        });


    }

    private void initTitleOne() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        setTitle("Personal Setting", true);
    }

    /**
     * 显示图标与是否在中间显示
     *
     * @param title    标题
     * @param isCenter 是否中间显示
     */
    public void setTitle(String title, boolean isCenter) {
        titleTxt = (TextView) findViewById(R.id.toolbar_title);
        if (isCenter) {
            titleTxt.setText(title);
        } else {
            setTitle(title);
        }

    }
}