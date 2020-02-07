package com.example.uni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.uni.util.Markalive;
import com.example.uni.util.PersonInfo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.uni.code.UrlCode.LOGIN_URL;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edit_stu_number , edit_password ;
    private final OkHttpClient client = new OkHttpClient();
    //netFlag用于标记网络请求是否返回成功
    private int netFlag = 1;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uni_login);

        edit_stu_number = (EditText)findViewById(R.id.stu_number);
        edit_password   = (EditText)findViewById(R.id.password);

        Button login  =  (Button)findViewById(R.id.button_login);
        login.setOnClickListener(this);

         progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        //创建数据库
         Connector.getDatabase();
         Jump_login();
    }
    @Override
    public void onClick(View v){
      if (v.getId() == R.id.button_login){
             login();
        }
    }

    public void Jump_login(){
        List<Markalive> markalives = LitePal.findAll(Markalive.class);

        //已有登录过的账号
        if(markalives.size()>0){
            String stu_number = LitePal.findFirst(Markalive.class).getStu_number();
            //Log.d("LgA:",LitePal.where("StudentNum =?",stu_number).findFirst(PersonInfo.class).getNickname());
            //如果未有昵称，跳转到修改信息界面
            if(LitePal.where("StudentNum =?",stu_number).findFirst(PersonInfo.class).getNickname()==null) {
                Intent intent = new Intent(LoginActivity.this, ModifyPersonInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            //否则跳转到使用信息窗口界面
            else{
                Intent intent = new Intent(LoginActivity.this, UserViewOneActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

     public void login (){
        final String stu_number =  edit_stu_number.getText().toString().trim();//trim()去掉头尾空格
        final String password   =  edit_password.getText().toString().trim();
        //空检查
         if(TextUtils.isEmpty(stu_number)||TextUtils.isEmpty(password)){
             Toast toast = Toast.makeText(LoginActivity.this ,"用户名或密码不能为空", Toast.LENGTH_SHORT);
             toast.show();
             return ;
         }
         //每一次新登录，都先删除之前的个人信息。
         LitePal.deleteAll(PersonInfo.class);
         progressBar.setVisibility(View.VISIBLE);
         RequestBody formBody = new FormBody.Builder()
                 .add("stu_number",stu_number)
                 .add("password",password)
                 .build();
         Request request = new Request.Builder()
                 .url(LOGIN_URL)//加入url
                 .post(formBody)
                 .build();

         Log.d("LOA::",stu_number + password);
         client.newCall(request).enqueue(new okhttp3.Callback(){
             @Override public void onFailure(Call call, IOException e) {
                 e.printStackTrace();
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         progressBar.setVisibility(View.GONE);
                         Toast toast = Toast.makeText(LoginActivity.this, "密码或用户名错误", Toast.LENGTH_LONG);
                         toast.show();
                     }
                 });
             }
             @Override public void  onResponse(Call call, Response response) throws IOException {
                 if (!response.isSuccessful()){
                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             progressBar.setVisibility(View.GONE);
                             Toast toast = Toast.makeText(LoginActivity.this,"密码或用户名错误",Toast.LENGTH_LONG);
                             toast.show();
                         }
                     });
                 }
                 //返回成功
                 String responseData = response.body().string();
                 Log.d("Log8n",responseData);
                 //解析数据
                 parsePersonInfoJson(responseData);
                 if(netFlag == 0){
                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             progressBar.setVisibility(View.GONE);
                             Toast toast = Toast.makeText(LoginActivity.this,"密码或用户名错误",Toast.LENGTH_LONG);
                             toast.show();
                         }
                     });
                 }
             }
         });
    }
    private void parsePersonInfoJson(String jsonData){
        try{
            JSONArray  jsonArray = new JSONArray(jsonData);
            JSONObject jsonObject =  jsonArray.getJSONObject(0);
            String statue =jsonObject.getString("statue");
            if(statue.equals("success")==true) {
                PersonInfo personInfo = new PersonInfo();
                personInfo.setStudentNum(edit_stu_number.getText().toString().trim());
                personInfo.setPassword(edit_password.getText().toString().trim());
                personInfo.setName(jsonObject.getString("Name"));
                personInfo.setSex(jsonObject.getString("Sex"));
                personInfo.setCollege(jsonObject.getString("College"));
                personInfo.setMajor(jsonObject.getString("Major"));
                personInfo.setClasses(jsonObject.getString("Classes"));
                personInfo.save();
               // personInfo = LitePal.findFirst(PersonInfo.class);
               // Log.d("LoginActivity", personInfo.getName() + personInfo.getCollege() + personInfo.getMajor() + personInfo.getClasses());
                Markalive markalive = new Markalive();
                markalive.setStu_number(edit_stu_number.getText().toString().trim());
                markalive.save();
                Intent intent = new Intent(LoginActivity.this , UserViewOneActivity.class );
               startActivity(intent);
            }else if(statue.equals("failure")==true){
                netFlag = 0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
