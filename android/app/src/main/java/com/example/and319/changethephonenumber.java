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

public class changethephonenumber extends AppCompatActivity {

    private connect2Server connect;
    private String username;
    private EditText phone_number;
    private String phonenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changethephonenumber);

        Intent i = getIntent();
        username = i.getStringExtra("id");
        phone_number = (EditText)findViewById(R.id.editphonenumber);

        Button  btn1 = (Button) findViewById(R.id.confirm);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                phonenumber = phone_number.getText().toString();
                if(isValid() != 0){
                    connect = new connect2Server("us-or-cera-1.natfrp.cloud", 22398);
                    try {
                        Client.latch = new CountDownLatch(1);
                        System.out.println(phonenumber);
                        connect.send("phone\n" + username + "\n" + phonenumber);
                        Client.latch.await();
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }

                    if(connect.stringInfo.size() != 0){
                        Toast.makeText(changethephonenumber.this, "Change your phone successful!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(changethephonenumber.this , personalsetting.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("movies", FilmListActivity.movie_all_object);
                        i.putExtras(bundle);
                        i.putExtra("id", username);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(changethephonenumber.this, "Change your phone failed!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(changethephonenumber.this, "Please Enter your phone", Toast.LENGTH_SHORT).show();
                }
                //System.out.println(ledEdit.getText().toString());
            }
        });

        Button  btn2 = (Button) findViewById(R.id.cancel);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(changethephonenumber.this , personalsetting.class);
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
            Toast.makeText(changethephonenumber.this, "The phone number has existed", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else {
            return 1;
        }
    }*/

    private int isValid(){
        if(TextUtils.isEmpty(phonenumber)){
            Toast.makeText(changethephonenumber.this, "Please enter phone number", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return 1;
    }

}
