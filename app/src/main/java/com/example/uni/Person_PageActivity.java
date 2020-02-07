package com.example.uni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.uni.like.PeriscopeLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Person_PageActivity extends AppCompatActivity {

    private TextView T_number,T_contact,T_sex,T_name,T_statue,T_heat,T_college,T_major,T_class,T_signature;
    private String Stu_number;
    private JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stu_number =  getIntent().getStringExtra("stu_number");
        setContentView(R.layout.activity_person_page);
        T_statue  = (TextView)findViewById(R.id.T_statue);
        T_number  = (TextView)findViewById(R.id.T_number);
        T_contact = (TextView)findViewById(R.id.T_contact);
        T_sex      = (TextView)findViewById(R.id.T_sex);
        T_name     = (TextView)findViewById(R.id.T_name);
        T_heat     = (TextView)findViewById(R.id.T_heat);
        T_college  = (TextView)findViewById(R.id.T_college);
        T_major    = (TextView)findViewById(R.id.T_major);
        T_class    = (TextView)findViewById(R.id.T_class);
        T_signature = (TextView)findViewById(R.id.T_signature);
        initPersonInfo();
        final PeriscopeLayout periscopeLayout = (PeriscopeLayout)findViewById(R.id.periscope);
        periscopeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                periscopeLayout.addFavor();
            }
        });
    }
    private void initPersonInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .build();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("stu_number", Stu_number)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://118.25.42.59:8000/personinfo_pick/")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String ResponseData = response.body().string();
                    JSONArray jsonArray = new JSONArray(ResponseData);
                    jsonObject = jsonArray.getJSONObject(0);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                T_number.setText("学号："+jsonObject.getString("stu_number"));
                                T_class.setText("班级："+jsonObject.getString("inclass"));
                                T_sex.setText("性别："+jsonObject.getString("sex"));
                                T_contact.setText("联系方式："+jsonObject.getString("contact"));
                                T_signature.setText(jsonObject.getString("signature"));
                                T_name.setText(jsonObject.getString("nickname"));
                                T_heat.setText("热度"+jsonObject.getString("heat"));
                                T_college.setText("学院："+jsonObject.getString("academic"));
                                T_major.setText("专业："+jsonObject.getString("major"));
                                switch (jsonObject.getString("single_degree")){
                                    case "0":
                                        T_statue.setText("单身可撩");
                                        break;
                                    case "1":
                                        T_statue.setText("钢铁硬直");
                                        break;
                                    case "2":
                                        T_statue.setText("已有归属");
                                        break;
                                    case "3":
                                        T_statue.setText("随心随意");
                                        break;
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
