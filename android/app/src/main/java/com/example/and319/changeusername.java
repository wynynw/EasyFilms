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

public class changeusername extends AppCompatActivity {

    private connect2Server connect;
    private String username;
    private String edit_user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeusername);

        Intent i = getIntent();
        username = i.getStringExtra("id");

        Button  btn1 = (Button) findViewById(R.id.confirm);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final EditText ledEdit = (EditText)findViewById(R.id.editname);
                edit_user_name = ledEdit.getText().toString();

                if(isValid() != 0){
                    connect = new connect2Server("us-or-cera-1.natfrp.cloud", 22398);
                    try {
                        Client.latch = new CountDownLatch(1);
                        connect.send("待定");
                        Client.latch.await();
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                    if(isUnique(connect.stringInfo.get(0)) != 0 ) {
                        Toast.makeText(changeusername.this, "Change username successful", Toast.LENGTH_SHORT).show();
                        /*Intent i = new Intent(changeusername.this, LoginActivity.class);
                        startActivity(i);*/
                    }
                }
                System.out.println(ledEdit.getText().toString());
            }
        });

        Button  btn2 = (Button) findViewById(R.id.cancel);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                /*这个地方要调用函数返回真名的*/
                Intent i = new Intent(changeusername.this , personalcenter.class);
                i.putExtra("id",username);
                startActivity(i);
                /*这个地方要调用函数返回真名的*/
            }
        });

    }

    private int isUnique(String info){
        if(info.equals("待定") == true) {
            Toast.makeText(changeusername.this, "The user name has existed", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else {
            return 1;
        }
    }

    private int isValid(){
        if(TextUtils.isEmpty(edit_user_name)){
            Toast.makeText(changeusername.this, "Please enter user name", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return 1;
    }
    //public void dialogShow(View v) {
       // LayoutInflater inflaterDl = LayoutInflater.from(this);
       // final RelativeLayout layout = (RelativeLayout) inflaterDl.inflate(R.layout.activity_changeusername, null);

      //  final Dialog dialog = new Dialog(actic.this);
       // dialog.create();
       // dialog.show();
       // dialog.getWindow().setContentView(layout);
       // Button btn1 = (Button) dialog.findViewById(R.id.confirm);
       // final EditText edit = (EditText) dialog.findViewById(R.id.editname;//输入密码

/*        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit.getText().toString();
            }

        });
  */

}



//    public void onClick(DialogInterface dialog, int whichButton) {
//        editText  = (EditText) DialogView.findViewById(R.id.editname);
//        String name = editText.getText().toString();
////      String password = editText2.getText().toString();
//        System.out.println(name);
//    }



