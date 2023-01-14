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


public class register extends AppCompatActivity {

    private EditText email;
    private EditText phone;
    private EditText pwd_1;
    private EditText pwd_2;
    private EditText user_name;

    private String email_string;
    private String phone_string;
    private String username;
    private String pwd1;
    private String pwd2;

    private void textToString(){   //This is the function that get String from EditText
        username = user_name.getText().toString();
        email_string = email.getText().toString();
        phone_string = phone.getText().toString();
        pwd1 = pwd_1.getText().toString();
        pwd2 = pwd_2.getText().toString();
    }

    private int isValid(){        //This is the function that judge if the information is invalid
        if(TextUtils.isEmpty(username)){
            Toast.makeText(register.this, "Please Enter Username", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else if(TextUtils.isEmpty(pwd1)){
            Toast.makeText(register.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else if(TextUtils.isEmpty(pwd2)){
            Toast.makeText(register.this, "Please Enter Password Again", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else if(!pwd1.equals(pwd2)){
            Toast.makeText(register.this, "Password doesn't correspond", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return 1;
    }

    private int isUnique(String info){
        if(info.equals("-1") == true) {
            Toast.makeText(register.this, "The user name has existed", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else if(info.equals("-2") == true) {
            Toast.makeText(register.this, "The email address has existed", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else {
            return 1;
        }
    }

    private int isExist(){
        return 0;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        user_name = (EditText)findViewById(R.id.resetpwd_edit_name);
        pwd_1 = (EditText)findViewById(R.id.resetpwd_edit_pwd_old);
        pwd_2 = (EditText)findViewById(R.id.resetpwd_edit_pwd_new);
        email = (EditText)findViewById(R.id.resetpwd_edit_email);
        phone = (EditText)findViewById(R.id.phone_number);

        Button btn_sure = (Button) findViewById(R.id.register_btn_sure);
        Button btn_cancel = (Button) findViewById(R.id.personalcenter_btn_cancel);

        btn_sure.setOnClickListener(new View.OnClickListener(){ //Register button
            @Override
            public void onClick(View view){

                textToString();
                if(isValid() != 0){
                    connect2Server connect = new connect2Server("us-or-cera-1.natfrp.cloud", 22398);
                    String user_info = username + "\n" + phone_string + "\n" + email_string + "\n" + pwd1;
                    try {
                        Client.latch = new CountDownLatch(1);
                        connect.send("signup\n" + user_info);
                        Client.latch.await();
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                    if(isUnique(connect.stringInfo.get(0)) != 0 ) {
                        Toast.makeText(register.this, "register successful", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(register.this, LoginActivity.class);
                        startActivity(i);
                    }
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener(){ //Cancel button
            @Override
            public void onClick(View view){

                Intent i = new Intent(register.this , LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
