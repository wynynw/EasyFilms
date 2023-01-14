package com.example.and319;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class LoginActivity extends AppCompatActivity{

    private AutoCompleteTextView mEmailView;
    private EditText mAccount;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private String account;
    private String password;
    private connect2Server connect;

    private void textToString() {
        account = mAccount.getText().toString();
        password = mPasswordView.getText().toString();
    }

    private int isCorrect(String info) {
        if(info.equals("1")) {
            return 1;
        }
        else {
            return 0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAccount = (EditText)findViewById(R.id.account);
        mPasswordView = (EditText) findViewById(R.id.password);
        //establish a new connection
        connect = new connect2Server("us-or-cera-1.natfrp.cloud", 22398);
        // Set up the login form.
        Button  btn1 = (Button) findViewById(R.id.email_regist_button);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(LoginActivity.this , register.class);
                startActivity(i);
            }
        });

        Button btn2 = (Button) findViewById(R.id.email_sign_in_button);
        btn2.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                textToString();
                try {
                    Client.latch = new CountDownLatch(1);
                    connect.send("login\n" + account + "\n" + password + "\n");
                    Client.latch.await();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                };

                if(connect.stringInfo.size() == 0){
                    Toast.makeText(LoginActivity.this, "Timeout!!!" , Toast.LENGTH_SHORT).show();
                }
                /*************************************这里是登录成功********************************************/
                else if(isCorrect(connect.stringInfo.get(0)) == 1) {
                    Intent i = new Intent(LoginActivity.this , personalcenter.class);

                    try {
                        Client.latch = new CountDownLatch(1);
                        connect.send("show\n");
                        Client.latch.await();
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    };

                    ArrayList<Object> movie_all_object = connect.objectInfo;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("movies", movie_all_object);
                    i.putExtras(bundle);
                    i.putExtra("id", account);
                    ActivityCompat.requestPermissions( LoginActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
                    startActivity(i);
                }
                /*************************************这里是登录失败*******************************************/
                else if(isCorrect(connect.stringInfo.get(0)) == 0){
                    System.out.println(connect.stringInfo.get(0));
                    System.out.println(isCorrect(connect.stringInfo.get(0)));
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("login error！");
                    builder.setMessage("please input correct account and password");
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(LoginActivity.this, "positive: " + which, Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(LoginActivity.this, "negative: " + which, Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();

                }
                else {
                    Toast.makeText(LoginActivity.this, "timeout!!", Toast.LENGTH_SHORT).show();
                }
                connect.stringInfo.clear();



                }
//            }
        });


}}

