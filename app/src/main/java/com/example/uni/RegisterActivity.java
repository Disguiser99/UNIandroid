package com.example.uni;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uni.ball_bounce.BallDialog;
import com.example.uni.ball_bounce.BounceBallView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edit_stu_nmber,edit_epassword,edit_password;
    private final OkHttpClient client = new OkHttpClient();
    private BounceBallView bbv1;
    private BallDialog dialog;
    private Dialog dialog1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uni_register);

        edit_stu_nmber = (EditText)findViewById(R.id.stu_number);
        edit_epassword = (EditText)findViewById(R.id.epassword);
        edit_password  = (EditText)findViewById(R.id.password);

        Button  button_register = (Button)findViewById(R.id.register_post) ;
        button_register.setOnClickListener(this);
        Button  button_login    = (Button)findViewById(R.id.regitser_return);
        button_login.setOnClickListener(this);
        dialog  = new BallDialog();
        bbv1 = (BounceBallView) findViewById(R.id.bbv1);
        bbv1.config()
                .ballCount(15)
                .bounceCount(3)
                .ballDelay(220)
                .duration(3300)
                .radius(15)
                .isPhysicMode(true)
                .isRamdomPath(true)
                .isRandomColor(true)
                .isRandomRadius(true)
                .apply();
    }
   @Override
    public void onClick (View v){
        if(v.getId()==R.id.register_post){
            dialog.setCancelable(false);
            dialog.show(getSupportFragmentManager(),"1");
            Register();
        }
        if (v.getId()==R.id.regitser_return){
            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
        }
   }
   public void Register(){
         final String stu_number = edit_stu_nmber.getText().toString().trim();
         final String epassword  = edit_epassword.getText().toString().trim();
         final String password   = edit_password.getText().toString().trim();

       if(TextUtils.isEmpty(stu_number)||TextUtils.isEmpty(password)||TextUtils.isEmpty(epassword)){
           Toast toast = Toast.makeText(RegisterActivity.this ,"用户名或密码不能为空", Toast.LENGTH_SHORT);
           toast.show();
           return ;
       }
       RequestBody formBody = new FormBody.Builder()
               .add("stu_number",stu_number)
               .add("epassword",epassword)
               .add("password",password)
               .build();

       Request request = new Request.Builder()
               .url("")
               .post(formBody)
               .build();

       client.newCall(request).enqueue(new okhttp3.Callback(){
           @Override
           public void onFailure(Call call, IOException e){
               e.printStackTrace();
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       Toast toast = Toast.makeText(RegisterActivity.this,"密码或用户名错误",Toast.LENGTH_LONG);
                       toast.show();
                   }
               });
           }
           @Override public void onResponse(Call call, Response response)throws IOException {
               if (!response.isSuccessful()){

                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           Toast toast = Toast.makeText(RegisterActivity.this,"密码或用户名错误",Toast.LENGTH_LONG);
                           toast.show();
                       }
                   });
               }
               if(response.body().string().equals("success")) {
                   //保存跳转
                   /*
                   ---------------------
                   */
                   Intent intent = new Intent(RegisterActivity.this, UserViewOneActivity.class);
                   startActivity(intent);
                   Log.d("LoginActivity", "1");//这个if成立代表登录写跳赚
               }
           }
       });
   }
}

//引用https://blog.csdn.net/ccy0122/article/details/77427795
//自定义View之小球自由落体弹跳加载控件
