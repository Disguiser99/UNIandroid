package com.example.uni;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uni.Helper.initStudentID;
import com.example.uni.ball_bounce.BallDialog;
import com.example.uni.ball_bounce.BounceBallView;
import com.example.uni.Receiver.ScreenActionReceiver;
import com.example.uni.Service.MyService;
import com.example.uni.util.PersonInfo;
import com.example.uni.util.Schedule;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ScheduleActivity extends AppCompatActivity implements View.OnClickListener {
    private BounceBallView bbv1;
    private BallDialog ballDialog;
    Schedule[][] schedules = new Schedule[5][5];
    private Notification notification;
    private NotificationManager manager;
    private ScreenActionReceiver receiver;
    private ProgressDialog progressDialog;
    private TextView MON_1_LOCATION,MON_2_LOCATION,MON_3_LOCATION,MON_4_LOCATION,MON_5_LOCATION;
    private TextView MON_1_CLASS,MON_2_CLASS,MON_3_CLASS,MON_4_CLASS,MON_5_CLASS;
    private TextView TUE_1_LOCATION,TUE_2_LOCATION,TUE_3_LOCATION,TUE_4_LOCATION,TUE_5_LOCATION;
    private TextView TUE_1_CLASS,TUE_2_CLASS,TUE_3_CLASS,TUE_4_CLASS,TUE_5_CLASS;
    private TextView WED_1_LOCATION,WED_2_LOCATION,WED_3_LOCATION,WED_4_LOCATION,WED_5_LOCATION;
    private TextView WED_1_CLASS,WED_2_CLASS,WED_3_CLASS,WED_4_CLASS,WED_5_CLASS;
    private TextView THU_1_LOCATION,THU_2_LOCATION,THU_3_LOCATION,THU_4_LOCATION,THU_5_LOCATION;
    private TextView THU_1_CLASS,THU_2_CLASS,THU_3_CLASS,THU_4_CLASS,THU_5_CLASS;
    private TextView FRI_1_LOCATION,FRI_2_LOCATION,FRI_3_LOCATION,FRI_4_LOCATION,FRI_5_LOCATION;
    private TextView FRI_1_CLASS,FRI_2_CLASS,FRI_3_CLASS,FRI_4_CLASS,FRI_5_CLASS;
    private  int i = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Button Refresh   = (Button)findViewById(R.id.Refresh_schedule);
        Refresh.setOnClickListener(this);

        Button  Notification  = (Button)findViewById(R.id.Notification_schedule);
        Notification.setOnClickListener(this);

        initStudentID initStudentID1 = new initStudentID();
        initStudentID1.initStudentIDs();


        MON_1_LOCATION  = (TextView)findViewById(R.id.MON_1_Location);
        MON_1_CLASS = (TextView)findViewById(R.id.MON_1_class);
        MON_2_LOCATION  = (TextView)findViewById(R.id.MON_2_Location);
        MON_2_CLASS = (TextView)findViewById(R.id.MON_2_class);
        MON_3_LOCATION  = (TextView)findViewById(R.id.MON_3_Location);
        MON_3_CLASS = (TextView)findViewById(R.id.MON_3_class);
        MON_4_LOCATION  = (TextView)findViewById(R.id.MON_4_Location);
        MON_4_CLASS = (TextView)findViewById(R.id.MON_4_class);
        MON_5_LOCATION  = (TextView)findViewById(R.id.MON_5_Location);
        MON_5_CLASS = (TextView)findViewById(R.id.MON_5_class);

        TUE_1_LOCATION  = (TextView)findViewById(R.id.TUE_1_Location);
        TUE_1_CLASS = (TextView)findViewById(R.id.TUE_1_class);
        TUE_2_LOCATION  = (TextView)findViewById(R.id.TUE_2_Location);
        TUE_2_CLASS = (TextView)findViewById(R.id.TUE_2_class);
        TUE_3_LOCATION  = (TextView)findViewById(R.id.TUE_3_Location);
        TUE_3_CLASS = (TextView)findViewById(R.id.TUE_3_class);
        TUE_4_LOCATION  = (TextView)findViewById(R.id.TUE_4_Location);
        TUE_4_CLASS = (TextView)findViewById(R.id.TUE_4_class);
        TUE_5_LOCATION  = (TextView)findViewById(R.id.TUE_5_Location);
        TUE_5_CLASS = (TextView)findViewById(R.id.TUE_5_class);

        WED_1_LOCATION  = (TextView)findViewById(R.id.WED_1_Location);
        WED_1_CLASS = (TextView)findViewById(R.id.WED_1_class);
        WED_2_LOCATION  = (TextView)findViewById(R.id.WED_2_Location);
        WED_2_CLASS = (TextView)findViewById(R.id.WED_2_class);
        WED_3_LOCATION  = (TextView)findViewById(R.id.WED_3_Location);
        WED_3_CLASS = (TextView)findViewById(R.id.WED_3_class);
        WED_4_LOCATION  = (TextView)findViewById(R.id.WED_4_Location);
        WED_4_CLASS = (TextView)findViewById(R.id.WED_4_class);
        WED_5_LOCATION  = (TextView)findViewById(R.id.WED_5_Location);
        WED_5_CLASS = (TextView)findViewById(R.id.WED_5_class);

        THU_1_LOCATION = (TextView)findViewById(R.id.THU_1_Location);
        THU_1_CLASS = (TextView)findViewById(R.id.THU_1_class);
        THU_2_LOCATION  = (TextView)findViewById(R.id.THU_2_Location);
        THU_2_CLASS = (TextView)findViewById(R.id.THU_2_class);
        THU_3_LOCATION  = (TextView)findViewById(R.id.THU_3_Location);
        THU_3_CLASS = (TextView)findViewById(R.id.THU_3_class);
        THU_4_LOCATION  = (TextView)findViewById(R.id.THU_4_Location);
        THU_4_CLASS = (TextView)findViewById(R.id.THU_4_class);
        THU_5_LOCATION  = (TextView)findViewById(R.id.THU_5_Location);
        THU_5_CLASS = (TextView)findViewById(R.id.THU_5_class);

        FRI_1_LOCATION = (TextView)findViewById(R.id.FRI_1_Location);
        FRI_1_CLASS = (TextView)findViewById(R.id.FRI_1_class);
        FRI_2_LOCATION  = (TextView)findViewById(R.id.FRI_2_Location);
        FRI_2_CLASS = (TextView)findViewById(R.id.FRI_2_class);
        FRI_3_LOCATION  = (TextView)findViewById(R.id.FRI_3_Location);
        FRI_3_CLASS = (TextView)findViewById(R.id.FRI_3_class);
        FRI_4_LOCATION  = (TextView)findViewById(R.id.FRI_4_Location);
        FRI_4_CLASS = (TextView)findViewById(R.id.FRI_4_class);
        FRI_5_LOCATION  = (TextView)findViewById(R.id.FRI_5_Location);
        FRI_5_CLASS = (TextView)findViewById(R.id.FRI_5_class);

        SetText_Schedule();
    }

    @Override
    public void onClick (View v){
        if(v.getId()==R.id.Refresh_schedule){
           // Intent intent = new Intent(ScheduleActivity.this,Meal_CardActivity.class);
           // startActivity(intent);

            Refresh_Schedule();
        }
        if (v.getId()==R.id.Notification_schedule){
            //Refresh_Notification();
            // AlarmManager alarmManager = (AlarmManager)getSystemService(Service.ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();
            if(calendar.get(Calendar.DAY_OF_WEEK)==7||calendar.get(Calendar.DAY_OF_WEEK)==1){
                Toast.makeText(ScheduleActivity.this,"为了不让你嫌我烦，周末不推送课表",Toast.LENGTH_LONG).show();
                return;
            }
            if(calendar.get(Calendar.HOUR_OF_DAY)>=19){
                Toast.makeText(ScheduleActivity.this,"为了不让你嫌我烦，晚7点后不推送课表",Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = new Intent(ScheduleActivity.this,MyService.class);
            startService(intent);
            RegisterReceiver();
        }
    }

    public void SetText_Schedule(){
        List<Schedule> scheduleList = LitePal.findAll(Schedule.class);
        if(scheduleList.size()==0){
         ;
        }else {
            Schedule schedule = LitePal.where("Day = 1 and Time = 1").findFirst(Schedule.class);
            MON_1_LOCATION.setText(schedule.getLocation());
            MON_1_CLASS.setText(schedule.getName());
            schedule = LitePal.where("Day = 1 and Time = 2").findFirst(Schedule.class);
            MON_2_LOCATION.setText(schedule.getLocation());
            MON_2_CLASS.setText(schedule.getName());
            schedule = LitePal.where("Day = 1 and Time = 3").findFirst(Schedule.class);
            MON_3_LOCATION.setText(schedule.getLocation());
            MON_3_CLASS.setText(schedule.getName());
            schedule = LitePal.where("Day = 1 and Time = 4").findFirst(Schedule.class);
            MON_4_LOCATION.setText(schedule.getLocation());
            MON_4_CLASS.setText(schedule.getName());
            schedule = LitePal.where("Day = 1 and Time = 5").findFirst(Schedule.class);
            MON_5_LOCATION.setText(schedule.getLocation());
            MON_5_CLASS.setText(schedule.getName());

            schedule = LitePal.where("Day = 2 and Time = 1").findFirst(Schedule.class);
            TUE_1_LOCATION.setText(schedule.getLocation());
            TUE_1_CLASS.setText(schedule.getName());
            schedule = LitePal.where("Day = 2 and Time = 2").findFirst(Schedule.class);
            TUE_2_LOCATION.setText(schedule.getLocation());
            TUE_2_CLASS.setText(schedule.getName());
            schedule = LitePal.where("Day = 2 and Time = 3").findFirst(Schedule.class);
            TUE_3_LOCATION.setText(schedule.getLocation());
            TUE_3_CLASS.setText(schedule.getName());
            schedule = LitePal.where("Day = 2 and Time = 4").findFirst(Schedule.class);
            TUE_4_LOCATION.setText(schedule.getLocation());
            TUE_4_CLASS.setText(schedule.getName());
            schedule = LitePal.where("Day = 2 and Time = 5").findFirst(Schedule.class);
            TUE_5_LOCATION.setText(schedule.getLocation());
            TUE_5_CLASS.setText(schedule.getName());

            schedule = LitePal.where("Day = 3 and Time = 1").findFirst(Schedule.class);
            WED_1_LOCATION.setText(schedule.getLocation());
            WED_1_CLASS.setText(schedule.getName());
            schedule = LitePal.where("Day = 3 and Time = 2").findFirst(Schedule.class);
            WED_2_LOCATION.setText(schedule.getLocation());
            WED_2_CLASS.setText(schedule.getName());
            schedule = LitePal.where("Day = 3 and Time = 3").findFirst(Schedule.class);
            WED_3_LOCATION.setText(schedule.getLocation());
            WED_3_CLASS.setText(schedule.getName());
            schedule = LitePal.where("Day = 3 and Time = 4").findFirst(Schedule.class);
            WED_4_LOCATION.setText(schedule.getLocation());
            WED_4_CLASS.setText(schedule.getName());
            schedule = LitePal.where("Day = 3 and Time = 5").findFirst(Schedule.class);
            WED_5_LOCATION.setText(schedule.getLocation());
            WED_5_CLASS.setText(schedule.getName());

            schedule = LitePal.where("Day = 4 and Time = 1").findFirst(Schedule.class);
            THU_1_LOCATION.setText(schedule.getLocation());
            THU_1_CLASS.setText(schedule.getName());
            schedule = LitePal.where("Day = 4 and Time = 2").findFirst(Schedule.class);
            THU_2_LOCATION.setText(schedule.getLocation());
            THU_2_CLASS.setText(schedule.getName());
            schedule = LitePal.where("Day = 4 and Time = 3").findFirst(Schedule.class);
            THU_3_LOCATION.setText(schedule.getLocation());
            THU_3_CLASS.setText(schedule.getName());
            schedule = LitePal.where("Day = 4 and Time = 4").findFirst(Schedule.class);
            THU_4_LOCATION.setText(schedule.getLocation());
            THU_4_CLASS.setText(schedule.getName());
            schedule = LitePal.where("Day = 4 and Time = 5").findFirst(Schedule.class);
            THU_5_LOCATION.setText(schedule.getLocation());
            THU_5_CLASS.setText(schedule.getName());

            schedule = LitePal.where("Day = 5 and Time = 1").findFirst(Schedule.class);
            FRI_1_LOCATION.setText(schedule.getLocation());
            FRI_1_CLASS.setText(schedule.getName());
            schedule = LitePal.where("Day = 5 and Time = 2").findFirst(Schedule.class);
            FRI_2_LOCATION.setText(schedule.getLocation());
            FRI_2_CLASS.setText(schedule.getName());
            schedule = LitePal.where("Day = 5 and Time = 3").findFirst(Schedule.class);
            FRI_3_LOCATION.setText(schedule.getLocation());
            FRI_3_CLASS.setText(schedule.getName());
            schedule = LitePal.where("Day = 5 and Time = 4").findFirst(Schedule.class);
            FRI_4_LOCATION.setText(schedule.getLocation());
            FRI_4_CLASS.setText(schedule.getName());
            schedule = LitePal.where("Day = 5 and Time = 5").findFirst(Schedule.class);
            FRI_5_LOCATION.setText(schedule.getLocation());
            FRI_5_CLASS.setText(schedule.getName());
        }

    }

    public void Refresh_Schedule (){
        //删除原先数据
        LitePal.deleteAll(Schedule.class);
       /* progressDialog = new ProgressDialog(ScheduleActivity.this);
        progressDialog.setTitle("正在更新课表");
        progressDialog.setMessage("Loading...");
        progressDialog.show();*/
       ballDialog = new BallDialog();
       ballDialog.show(getSupportFragmentManager(),"1");
        /*bbv1 = (BounceBallView) findViewById(R.id.bbv1);
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
        bbv1.start();*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(60,TimeUnit.SECONDS)
                            .build();

                    PersonInfo personInfo = LitePal.findFirst(PersonInfo.class);
                    Log.d("Schedule",personInfo.getStudentNum()+personInfo.getPassword());
                    RequestBody requestBody = new FormBody.Builder()
                            .add("stu_number",personInfo.getStudentNum())
                            .add("password",personInfo.getPassword())
                            .build();
                    Request request = new Request.Builder()
                            .url("http://118.25.42.59:8000/getSchedule/")
                            .post(requestBody)
                            .build();
                    Response response   = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseScheduleJson(responseData);

                    //保存到数据库
                    for(int m = 0; m < 5 ; m++){
                        for (int n = 0 ;n < 5 ; n++){
                            Schedule schedule = new Schedule(schedules[m][n].getDay()+1,schedules[m][n].getTime()+1,schedules[m][n].getLocation(),schedules[m][n].getName());
                            schedule.save();
                            Log.d("schedule","success");
                            Log.d("schedule",schedules[m][n].getDay()+" "+schedules[m][n].getTime()+" "+schedules[m][n].getLocation()+" "+schedules[m][n].getName()+" " );
                        }
                    }

                    MON_1_LOCATION.setText(schedules[0][0].getLocation());
                    MON_1_CLASS.setText(schedules[0][0].getName());
                    MON_2_LOCATION.setText(schedules[0][1].getLocation());
                    MON_2_CLASS.setText(schedules[0][1].getName());
                    MON_3_LOCATION.setText(schedules[0][2].getLocation());
                    MON_3_CLASS.setText(schedules[0][2].getName());
                    MON_4_LOCATION.setText(schedules[0][3].getLocation());
                    MON_4_CLASS.setText(schedules[0][3].getName());
                    MON_5_LOCATION.setText(schedules[0][4].getLocation());
                    MON_5_CLASS.setText(schedules[0][4].getName());

                    TUE_1_LOCATION.setText(schedules[1][0].getLocation());
                    TUE_1_CLASS.setText(schedules[1][0].getName());
                    TUE_2_LOCATION.setText(schedules[1][1].getLocation());
                    TUE_2_CLASS.setText(schedules[1][1].getName());
                    TUE_3_LOCATION.setText(schedules[1][2].getLocation());
                    TUE_3_CLASS.setText(schedules[1][2].getName());
                    TUE_4_LOCATION.setText(schedules[1][3].getLocation());
                    TUE_4_CLASS.setText(schedules[1][3].getName());
                    TUE_5_LOCATION.setText(schedules[1][4].getLocation());
                    TUE_5_CLASS.setText(schedules[1][4].getName());

                    WED_1_LOCATION.setText(schedules[2][0].getLocation());
                    WED_1_CLASS.setText(schedules[2][0].getName());
                    WED_2_LOCATION.setText(schedules[2][1].getLocation());
                    WED_2_CLASS.setText(schedules[2][1].getName());
                    WED_3_LOCATION.setText(schedules[2][2].getLocation());
                    WED_3_CLASS.setText(schedules[2][2].getName());
                    WED_4_LOCATION.setText(schedules[2][3].getLocation());
                    WED_4_CLASS.setText(schedules[2][3].getName());
                    WED_5_LOCATION.setText(schedules[2][4].getLocation());
                    WED_5_CLASS.setText(schedules[2][4].getName());

                    THU_1_LOCATION.setText(schedules[3][0].getLocation());
                    THU_1_CLASS.setText(schedules[3][0].getName());
                    THU_2_LOCATION.setText(schedules[3][1].getLocation());
                    THU_2_CLASS.setText(schedules[3][1].getName());
                    THU_3_LOCATION.setText(schedules[3][2].getLocation());
                    THU_3_CLASS.setText(schedules[3][2].getName());
                    THU_4_LOCATION.setText(schedules[3][3].getLocation());
                    THU_4_CLASS.setText(schedules[3][3].getName());
                    THU_5_LOCATION.setText(schedules[3][4].getLocation());
                    THU_5_CLASS.setText(schedules[3][4].getName());

                    FRI_1_LOCATION.setText(schedules[4][0].getLocation());
                    FRI_1_CLASS.setText(schedules[4][0].getName());
                    FRI_2_LOCATION.setText(schedules[4][1].getLocation());
                    FRI_2_CLASS.setText(schedules[4][1].getName());
                    FRI_3_LOCATION.setText(schedules[4][2].getLocation());
                    FRI_3_CLASS.setText(schedules[4][2].getName());
                    FRI_4_LOCATION.setText(schedules[4][3].getLocation());
                    FRI_4_CLASS.setText(schedules[4][3].getName());
                    FRI_5_LOCATION.setText(schedules[4][4].getLocation());
                    FRI_5_CLASS.setText(schedules[4][4].getName());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ballDialog.dismiss();
                            //bbv1.cancel();
                            Toast.makeText(ScheduleActivity.this,"更新课表完成！",Toast.LENGTH_LONG).show();
                        }
                    });

                }catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ballDialog.dismiss();
                            //bbv1.cancel();
                            Toast.makeText(ScheduleActivity.this,"更新课表失败！",Toast.LENGTH_LONG).show();
                        }
                    });
                 //   Toast.makeText(ScheduleActivity.this,"更新课表失败！",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }).start();
        }
        private void parseScheduleJson(String jsonData){
          try {
              JSONArray jsonArray = new JSONArray(jsonData);
              for(int i = 0 , m = 0 , n = 0; i < jsonArray.length();i++){
                  JSONObject jsonObject = jsonArray.getJSONObject(i);
                  String info = jsonObject.getString("info");
                 // Schedule schedule = new Schedule(m,n,)
                  // schedules[m][n].setDay(m+1);
                 // schedules[m][n].setTime(n+1);
                  if(info.equals("")==true){
                      Schedule schedule = new Schedule(m,n,"","");
                      schedules[m][n] = schedule;
                      //  schedules[m][n].setLocation("");
                     // schedules[m][n].setClassName("");
                  }else{
                      Schedule schedule = new Schedule(m,n,info.split(",")[0],info.split(",")[1]);
                      schedules[m][n] = schedule;
                      // schedules[m][n].setLocation(info.split(",")[0]);
                     // schedules[m][n].setClassName(info.split(",")[1]);
                  }
                  n++;
                  if(n == 5){
                      m++;
                      n = 0;
                  }
              }
          }catch (Exception e){
              e.printStackTrace();
          }
        }
        public void RegisterReceiver(){
            receiver = new ScreenActionReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_SCREEN_ON);
            registerReceiver(receiver,filter);
        }
}
