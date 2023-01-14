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

public class changethebirth extends AppCompatActivity {

    private connect2Server connect;
    private String username;
    //private String edit_email;
    private String birth;
    private EditText ledEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changethebirth);
        connect = new connect2Server("us-or-cera-1.natfrp.cloud", 22398);

        Intent i = getIntent();
        username = i.getStringExtra("id");
        ledEdit = (EditText)findViewById(R.id.editbirth);


        Button  btn1 = (Button) findViewById(R.id.confirm);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                birth = ledEdit.getText().toString();
            if(isValid() != 0){
                try {
                    Client.latch = new CountDownLatch(1);
                    connect.send("birth\n" + username + "\n" +  ledEdit.getText().toString());
                    Client.latch.await();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                if(connect.stringInfo.size() != 0){
                    Toast.makeText(changethebirth.this, "Change your birth successful!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(changethebirth.this , personalsetting.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("movies", FilmListActivity.movie_all_object);
                    i.putExtras(bundle);
                    i.putExtra("id", username);
                    startActivity(i);
                }
                else{
                    Toast.makeText(changethebirth.this, "Change your birth failed!", Toast.LENGTH_SHORT).show();
                }

            }

                //System.out.println(ledEdit.getText().toString());
            }
        });

        Button  btn2 = (Button) findViewById(R.id.cancel);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(changethebirth.this , personalsetting.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movies", FilmListActivity.movie_all_object);
                i.putExtras(bundle);
                i.putExtra("id", username);
                startActivity(i);
            }
        });
    }

    private int isValid(){
        if(TextUtils.isEmpty(birth)){
            Toast.makeText(changethebirth.this, "Please enter your birth", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return 1;
    }
}
