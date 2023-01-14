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

public class changethepassword extends AppCompatActivity {

    private connect2Server connect;
    private String username;
    private String oldpwd;
    private String editpwd1;
    private String editpwd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changethepassword);

        Intent i = getIntent();
        username = i.getStringExtra("id");

        Button  btn1 = (Button) findViewById(R.id.confirm);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                oldpwd = ((EditText)findViewById(R.id.old_pwd)).getText().toString();
                editpwd1 = ((EditText)findViewById(R.id.new_pwd1)).getText().toString();
                editpwd2 = ((EditText)findViewById(R.id.new_pwd2)).getText().toString();

                if(isValid() != 0){
                    connect = new connect2Server("us-or-cera-1.natfrp.cloud", 22398);
                    try {
                        Client.latch = new CountDownLatch(1);
                        System.out.println("changepassword\n" + username + "\n" + oldpwd + "\n" + editpwd2);
                        connect.send("changepassword\n" + username + "\n" + oldpwd + "\n" + editpwd2);
                        Client.latch.await();
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }

                    if(connect.stringInfo.get(0).equals("1")){
                        Toast.makeText(changethepassword.this, "Change your password successful!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(changethepassword.this , personalsetting.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("movies", FilmListActivity.movie_all_object);
                        i.putExtras(bundle);
                        i.putExtra("id", username);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(changethepassword.this, "Old password incorrect!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        Button  btn2 = (Button) findViewById(R.id.cancel);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(changethepassword.this , personalsetting.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movies", FilmListActivity.movie_all_object);
                i.putExtras(bundle);
                i.putExtra("id",username);
                startActivity(i);
            }
        });

    }

    private int isValid(){        //This is the function that judge if the information is invalid
        if(TextUtils.isEmpty(oldpwd)){
            Toast.makeText(changethepassword.this, "Please Enter old password", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else if(TextUtils.isEmpty(editpwd1)){
            Toast.makeText(changethepassword.this, "Please Enter Password 1", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else if(TextUtils.isEmpty(editpwd2)){
            Toast.makeText(changethepassword.this, "Please Enter Password 2", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else if(!editpwd1.equals(editpwd2)){
            Toast.makeText(changethepassword.this, "Password doesn't correspond", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return 1;
    }
}
