package com.example.uni;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uni.util.Markalive;
import com.example.uni.util.PersonInfo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import static com.example.uni.Helper.HttpNet.ClientBuild;
import static com.example.uni.Helper.HttpNet.RequestBuild;
import static com.example.uni.code.UrlCode.PERSON_NICKINFO_MODIFY_URL;
import static com.example.uni.code.UrlCode.PERSON_NICKINFO_PICK_URL;

public class ModifyPersonInfoActivity extends AppCompatActivity{
    private TextView TM_nick,TM_signature,TM_contact,TM_status;
    private Spinner spinner;
    private String nick,signature,contact;
    private int status,modi_position;
    private FloatingActionButton fab ;
    private JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_person_info);
        initPersonInfo();

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stu_number = LitePal.findFirst(Markalive.class).getStu_number();
                PersonInfo personInfo = new PersonInfo();
                personInfo.setNickname(nick);
                personInfo.setSignature(signature);
                personInfo.setContact(contact);
                personInfo.setSingle_degree(status);
                try {
                    personInfo.updateAll("StudentNum  = ?",stu_number);
                    Log.d("MPA:",stu_number);
                    upload_personInfo();
                }catch (Exception e){
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ModifyPersonInfoActivity.this,"修改失败！",Toast.LENGTH_LONG).show();
                        }
                    });
                }
               //PersonInfo personInfo1 = LitePal.where("StudentNum  = ?",stu_number).findFirst(PersonInfo.class);
                //Log.d("MPA:",personInfo1.getName()+personInfo1.getMajor()+personInfo1.getCollege()+personInfo1.getContact()+personInfo1.getSignature());
            }
        });
        TM_nick  =  (TextView)findViewById(R.id.TM_nick);
        TM_nick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modi_position = 0;
                initPopWindow(v);
            }
        });
        TM_signature  =  (TextView)findViewById(R.id.TM_signature);
        TM_signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modi_position = 1;
                initPopWindow(v);
            }
        });
        TM_contact    =  (TextView)findViewById(R.id.TM_contact);
        TM_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modi_position = 2 ;
                initPopWindow(v);
            }
        });
        TM_status     =  (TextView)findViewById(R.id.TM_status);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        TM_status.setText("     情感状态           "+"单身可撩");
                        status = 0;
                        break;
                    case 1:
                        TM_status.setText("     情感状态           "+"钢铁硬直");
                        status = 1;
                        break;
                    case 2:
                        TM_status.setText("     情感状态           "+"已有归属");
                        status = 2;
                        break;
                    case 3:
                        TM_status.setText("     情感状态           "+"随心随意");
                        status = 3;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initPopWindow(View v){
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.modify_info_item,null);
        Button  btinfo = (Button) view.findViewById(R.id.Bs_info);
        final EditText editinfo = (EditText)view.findViewById(R.id.edit_info);
        final PopupWindow popupWindow = new PopupWindow(view , ViewGroup.LayoutParams.MATCH_PARENT,800,true);
        switch (modi_position){
            case 0:
                btinfo.setText("  修改昵称  ");
                break;
            case 1:
                btinfo.setText("  修改签名  ");
                break;
            case 2:
                btinfo.setText("  联系方式  ");
                break;
        }
        btinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (modi_position){
                    case 0:
                        nick = editinfo.getText().toString();
                        popupWindow.dismiss();
                        TM_nick.setText("     修改昵称           "+ nick);
                        break;
                    case 1:
                        signature = editinfo.getText().toString();
                        TM_signature.setText("     修改签名           "+ signature);
                        popupWindow.dismiss();
                        break;
                    case 2:
                        contact  = editinfo.getText().toString();
                        TM_contact.setText("     联系方式           "+ contact);
                        popupWindow.dismiss();
                        break;
                }
            }
        });
        popupWindow.setAnimationStyle(R.anim.photo_preview);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER,0,0);
        bgAlpha(0.618f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                bgAlpha(1.0f);
            }
        });

    }
    public void bgAlpha(float f){
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = f;
        getWindow().setAttributes(layoutParams);
    }
    private void upload_personInfo(){
        Log.d("MLPA:","start");
        new Thread(new Runnable() {
            @Override
            public void run() {
              try {
                  OkHttpClient client = ClientBuild(30,60);
                  /*
                  OkHttpClient client = new OkHttpClient.Builder()
                          .connectTimeout(30, TimeUnit.SECONDS)
                          .readTimeout(60, TimeUnit.SECONDS)
                          .build();
                  */
                  String stu_number = LitePal.findFirst(Markalive.class).getStu_number();
                  RequestBody requestBody = new FormBody.Builder()
                          .add("stu_number", stu_number)
                          .add("nickname", nick)
                          .add("contact", contact)
                          .add("signature", signature)
                          .add("single_degree", status + "")
                          .build();
                  Request request = RequestBuild(PERSON_NICKINFO_MODIFY_URL, requestBody);
                  /*
                  Request request = new Request.Builder()
                          .url(PERSON_NICKINFO_MODIFY_URL)
                          .post(requestBody)
                          .build();
                  */
                  Response response = client.newCall(request).execute();
                  String responseData = response.body().string();

                  /**/
                  Log.d("reer",responseData);
                  if(responseData.equals("success")) {
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              Toast.makeText(ModifyPersonInfoActivity.this, "修改成功！", Toast.LENGTH_LONG).show();
                              Intent intent = new Intent(ModifyPersonInfoActivity.this, UserViewOneActivity.class);
                              startActivity(intent);
                          }
                      });
                  }else {
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              Toast.makeText(ModifyPersonInfoActivity.this,"修改失败！",Toast.LENGTH_LONG).show();
                          }
                      });
                  }
              }catch (Exception e){
                  e.printStackTrace();
                  runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          Toast.makeText(ModifyPersonInfoActivity.this,"修改失败！",Toast.LENGTH_LONG).show();
                      }
                  });
              }
            }
        }).start();
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
                    String stu_number = LitePal.findFirst(Markalive.class).getStu_number();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("stu_number", stu_number)
                            .build();
                    Request request = new Request.Builder()
                            .url(PERSON_NICKINFO_PICK_URL)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String ResponseData = response.body().string();
                    Log.d("MPA",ResponseData);
                    JSONArray jsonArray = new JSONArray(ResponseData);
                    jsonObject = jsonArray.getJSONObject(0);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           try {
                               nick = jsonObject.getString("nickname");
                               TM_nick.setText("     修改昵称           "+ nick);
                               signature = jsonObject.getString("signature");
                               TM_signature.setText("     修改签名           "+ signature);
                               contact = jsonObject.getString("contact");
                               TM_contact.setText("     联系方式           "+ contact);
                               switch (jsonObject.getString("single_degree")){
                                   case "0":
                                       status = 0;
                                       TM_status.setText("     情感状态           "+"单身可撩");
                                       break;
                                   case "1":
                                       status = 1;
                                       TM_status.setText("     情感状态           "+"钢铁硬直");
                                       break;
                                   case "2":
                                       status = 2;
                                       TM_status.setText("     情感状态           "+"已有归属");
                                       break;
                                   case "3":
                                       status = 3;
                                       TM_status.setText("     情感状态           "+"随心随意");
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
