package com.example.and319;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.CountDownLatch;

public class changetheemailaddress extends AppCompatActivity {

    private connect2Server connect;
    private String username;
    private String edit_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changetheemailaddress);

        Intent i = getIntent();
        username = i.getStringExtra("id");

        Button  btn1 = (Button) findViewById(R.id.confirm);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                edit_email = ((EditText)findViewById(R.id.editemail)).getText().toString();
                if(isValid() != 0){
                    connect = new connect2Server("us-or-cera-1.natfrp.cloud", 22398);
                    try {
                        Client.latch = new CountDownLatch(1);
                        connect.send("email\n" + username + "\n" + edit_email);
                        Client.latch.await();
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }

                    if(connect.stringInfo.size() != 0){
                        Toast.makeText(changetheemailaddress.this, "Change your email successful!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(changetheemailaddress.this , personalsetting.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("movies", FilmListActivity.movie_all_object);
                        i.putExtras(bundle);
                        i.putExtra("id", username);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(changetheemailaddress.this, "Change your email failed!", Toast.LENGTH_SHORT).show();
                    }

                }

                //System.out.println(ledEdit.getText().toString());
            }
        });

        Button  btn2 = (Button) findViewById(R.id.cancel);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(changetheemailaddress.this , personalsetting.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movies", FilmListActivity.movie_all_object);
                i.putExtras(bundle);
                i.putExtra("id", username);
                startActivity(i);
            }
        });
    }

    /*private int isUnique(String info){
        if(info.equals("待定") == true) {
            Toast.makeText(changetheemailaddress.this, "The email address has existed", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else {
            return 1;
        }
    }*/

    private int isValid(){
        if(TextUtils.isEmpty(edit_email)){
            Toast.makeText(changetheemailaddress.this, "Please enter email address", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return 1;
    }
}
