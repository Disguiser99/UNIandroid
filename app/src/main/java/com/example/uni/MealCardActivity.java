package com.example.uni;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uni.util.StudentID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MealCardActivity extends AppCompatActivity {

    private EditText studentId,studentId1,address,contact;
    private TextView college_classesorinfo;
    String  responseData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealcard);

        studentId = (EditText)findViewById(R.id.studentId);
        college_classesorinfo = (TextView)findViewById(R.id.college_classesorinfo);
        studentId1 =  (EditText)findViewById(R.id.studentId1);
        address = (EditText)findViewById(R.id.address);
        contact =(EditText)findViewById(R.id.contact);

        Button search = (Button)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String inputText = studentId.getText().toString();
                 if( inputText.length() != 10){
                     Toast.makeText(MealCardActivity.this,"请输入十位学号", Toast.LENGTH_LONG).show();
                 }else{
                     search_serverdatabase_StudentID();
                 }
            }
        });

        Button pick = (Button)findViewById(R.id.pick);
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  stuId = studentId1.getText().toString();
                if(stuId.length() != 10){
                    Toast.makeText(MealCardActivity.this,"请输入十位学号！",Toast.LENGTH_LONG).show();
                    return;
                }
                String add  = address.getText().toString();
                String cont = contact.getText().toString();
                if((add==null || add.length()==0) && cont==null || cont.length()==0 ){
                    Toast.makeText(MealCardActivity.this,"地址和联系方式不能均不填写！",Toast.LENGTH_LONG).show();
                    return;
                }

                AlertDialog.Builder dialog = new AlertDialog.Builder(MealCardActivity.this);
                dialog.setTitle("提交确认");
                dialog.setMessage("学号:"+studentId1.getText().toString()+"\n"+"地址:"+address.getText().toString()+"\n"+"联系方式:"+contact.getText().toString());
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        submit_mealcard();
                        // Toast.makeText(Meal_CardActivity.this,"提交成功！感谢您的帮助！",Toast.LENGTH_LONG).show();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });
    }

    public void   search_serverdatabase_StudentID(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .build();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("studentId", studentId.getText().toString())
                            .build();
                    Request request = new Request.Builder()
                            .url("http://118.25.42.59:8000/mealcard_search/")
                            .post(requestBody)
                            .build();

                    Response response = client.newCall(request).execute();
                    responseData = response.body().string();
                    Log.d("MCA:",responseData);
                    if(responseData.equals("none")) {
                        search_database_StudentID(studentId.getText().toString());
                    }else{
                        //college_classesorinfo.setText(responseData);
                        //解析传下来的json
                        try{
                            JSONArray jsonArray = new JSONArray(responseData);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            if(jsonObject.getString("address")==null||jsonObject.getString("address").length()==0){
                                college_classesorinfo.setText("联系方式："+jsonObject.getString("contact"));
                            }else if(jsonObject.getString("contact")==null||jsonObject.getString("contact").length()==0){
                                college_classesorinfo.setText("地址："+jsonObject.getString("address"));
                            }else {
                                college_classesorinfo.setText("联系方式："+jsonObject.getString("contact")+"  地址："+jsonObject.getString("address"));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void search_database_StudentID(String inputText){


           String forenumber  = inputText.substring(0,5);
           Log.d("Meal_Card",forenumber);
           StudentID studentID  = LitePal.where("forenumber = ?",forenumber).findFirst(StudentID.class);
           String classesnum;
           //college.setText(studentID.getCollege());
           //major.setText(studentID.getMajor());
           if(inputText.charAt(6)== '0'){
               classesnum = inputText.substring(4,8);
           }else{
               classesnum = inputText.substring(5,7)+"0"+ inputText.charAt(7);
           }
          college_classesorinfo.setText(studentID.getCollege()+" "+studentID.getMajor()+" "+classesnum);
    }

    public void submit_mealcard(){
        //十位学号检测
        //重复检测
        //地址与联系方式不能全空
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .build();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("studentId", studentId1.getText().toString())
                            .add("address", address.getText().toString())
                            .add("contact",contact.getText().toString())
                            .build();

                    Request request = new Request.Builder()
                            .url("http://118.25.42.59:8000/mealcard_pick/")//测试
                            .post(requestBody)
                            .build();

                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if(responseData.equals("success")==true){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MealCardActivity.this,"提交成功！感谢您的帮助！",Toast.LENGTH_LONG).show();
                            }
                        });
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MealCardActivity.this,"提交失败！",Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                } catch (Exception e) {
                       e.printStackTrace();
                }
            }
        }).start();
    }
}
