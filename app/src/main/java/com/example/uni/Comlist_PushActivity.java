package com.example.uni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uni.util.Markalive;

import org.litepal.LitePal;

import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Comlist_PushActivity extends AppCompatActivity {
    private EditText edit_contact,edit_content;
    private Button bt_push;
    private String  position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comlist_push);
        position = getIntent().getStringExtra("position");
        edit_contact = findViewById(R.id.comlist_edit_contact);
        edit_content = findViewById(R.id.comlist_edit_content);
        bt_push      = findViewById(R.id.comlist_push);
        bt_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_content.getText().toString().equals("")||edit_contact.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"联系方式和内容不能为空",Toast.LENGTH_LONG).show();
                    return;
                }
                 push_comlist();
                //Intent intent = new Intent(Comlist_PushActivity.this,UseView_PageOneActivity.class);
                //startActivity(intent);
            }
        });
    }
    private void push_comlist(){
        final String editcontact = edit_contact.getText().toString();
        final String editcontent = edit_content.getText().toString();
        final String stu_number  = LitePal.findFirst(Markalive.class).getStu_number();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(60,TimeUnit.SECONDS)
                            .build();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("edit_contact",editcontact)
                            .add("edit_content",editcontent)
                            .add("stu_number",stu_number)
                            .add("position",position)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://118.25.42.59:8000/com_communitylist_push/")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String ResponseData = response.body().string();
                    if(ResponseData.equals("success")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"发表成功!",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"发表失败！",Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"发表失败！",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();

    }
}
