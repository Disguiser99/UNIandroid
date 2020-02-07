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

import static com.example.uni.code.UrlCode.COM_TREEHOLE_PUSH;

public class Com_Push_TreeActivity extends AppCompatActivity {
    private EditText edit_title,edit_content;
    private Button b_push;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_push_tree);
        edit_title   = (EditText)findViewById(R.id.edit_contact);
        edit_content = (EditText)findViewById(R.id.edit_content);
        b_push        = (Button) findViewById(R.id.b_push);
        b_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_title.getText().toString().equals("")||edit_content.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"题目或者内容不能为空",Toast.LENGTH_LONG).show();
                    return ;
                }
                    push_Treehole();
                //Intent intent = new Intent(Com_Push_TreeActivity.this,UseView_PageOneActivity.class);
                //startActivity(intent);
            }
        });
    }
    private void push_Treehole(){
        final String edittitle   = edit_title.getText().toString();
        final String editcontent = edit_content.getText().toString();
        final String Stu_number  = LitePal.findFirst(Markalive.class).getStu_number();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .build();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("edit_title", edittitle)
                            .add("edit_content", editcontent)
                            .add("stu_number",Stu_number)
                            .build();

                    Request request = new Request.Builder()
                            .url(COM_TREEHOLE_PUSH)
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
