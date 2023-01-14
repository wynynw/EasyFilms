package com.example.and319;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.concurrent.CountDownLatch;

public class changeyourgender extends AppCompatActivity {

    private String username;
    private connect2Server connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeyourgender);

        Intent i = getIntent();
        username = i.getStringExtra("id");

        Button  btn2 = (Button) findViewById(R.id.cancel);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(changeyourgender.this , personalsetting.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movies", FilmListActivity.movie_all_object);
                i.putExtras(bundle);
                i.putExtra("id", username);
                startActivity(i);
            }
        });

        final Button confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect = new connect2Server("us-or-cera-1.natfrp.cloud", 22398);
                RadioGroup sex_selector = (RadioGroup) findViewById(R.id.sex_rg);
                switch (sex_selector.getCheckedRadioButtonId()){
                    case R.id.male_rb:
                        //System.out.println("male");
                        try {
                            Client.latch = new CountDownLatch(1);
                            connect.send("gender\n" + username + "\n" +  "1");
                            Client.latch.await();
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();
                        }

                        if(connect.stringInfo.size() != 0){
                            Toast.makeText(changeyourgender.this, "Change your gender successful!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(changeyourgender.this , personalsetting.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("movies", FilmListActivity.movie_all_object);
                            i.putExtras(bundle);
                            i.putExtra("id", username);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(changeyourgender.this, "Change your gender failed!", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case R.id.female_rb:
                        //System.out.println("female");
                        try {
                            Client.latch = new CountDownLatch(1);
                            connect.send("gender\n" + username + "\n" + "0");
                            Client.latch.await();
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();
                        }

                        if(connect.stringInfo.size() != 0){
                            Toast.makeText(changeyourgender.this, "Change your gender successful!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(changeyourgender.this , personalsetting.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("movies", FilmListActivity.movie_all_object);
                            i.putExtras(bundle);
                            i.putExtra("id", username);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(changeyourgender.this, "Change your gender failed!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case -1:
                        break;
                }
            }
        });

    }
}
