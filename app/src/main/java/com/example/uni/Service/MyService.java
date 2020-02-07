package com.example.uni.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.uni.R;
import com.example.uni.util.Schedule;

import org.litepal.LitePal;

import java.util.Calendar;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class MyService extends Service  {
    public MyService() {
    }

    Schedule[][] schedules = new Schedule[5][5];
    private String NewLocation;
    private String NewClassName;

    private Notification notification;
    private NotificationManager manager;

    @Override
    public IBinder onBind(Intent intent) {

        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void  onCreate(){
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent , int flags ,int startId) {
          ///神奇解决8.0+
      // new Thread(new Runnable() {
      //      @Override
      //      public void run() {
                Calendar calendar = Calendar.getInstance();
                Schedule schedule = new Schedule(-1,-1,"","");
                switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                    case 2: //星期一
                        if (calendar.get(Calendar.HOUR_OF_DAY) <= 7) {
                            schedule = LitePal.where("Day = 1 and Time = 1").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        }
                        else if (calendar.get(Calendar.HOUR_OF_DAY) == 8 || calendar.get(Calendar.HOUR_OF_DAY) == 9) {
                            schedule = LitePal.where("Day = 1 and Time = 2").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        }
                        else if (9 < calendar.get(Calendar.HOUR_OF_DAY) && calendar.get(Calendar.HOUR_OF_DAY) <= 13) {
                            schedule = LitePal.where("Day = 1 and Time = 3").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        } else if (14 <= calendar.get(Calendar.HOUR_OF_DAY) && calendar.get(Calendar.HOUR_OF_DAY) <= 15) {  //case 15:
                            schedule = LitePal.where("Day = 1 and Time = 4").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        }
                         else if (15<calendar.get(Calendar.HOUR_OF_DAY)&&calendar.get(Calendar.HOUR_OF_DAY)<=18) {
                            schedule = LitePal.where("Day = 1 and Time = 5").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        }  else {
                            NewLocation ="-1";
                             // onDestroy();
                           // NewLocation = "";
                           // NewClassName = "";
                        }
                        break;
                    case 3://星期二
                        if (calendar.get(Calendar.HOUR_OF_DAY) <= 7) {
                            schedule = LitePal.where("Day = 2 and Time = 1").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        }
                        else if (calendar.get(Calendar.HOUR_OF_DAY) == 2 || calendar.get(Calendar.HOUR_OF_DAY) == 9) {
                            schedule = LitePal.where("Day = 2 and Time = 2").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        }
                        else if (9 < calendar.get(Calendar.HOUR_OF_DAY) && calendar.get(Calendar.HOUR_OF_DAY) <= 13) {
                            schedule = LitePal.where("Day = 2 and Time = 3").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        } else if (14 <= calendar.get(Calendar.HOUR_OF_DAY) && calendar.get(Calendar.HOUR_OF_DAY) <= 15) {  //case 15:
                            schedule = LitePal.where("Day = 2 and Time = 4").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        }
                        else if (15<calendar.get(Calendar.HOUR_OF_DAY)&&calendar.get(Calendar.HOUR_OF_DAY)<=18) {
                            schedule = LitePal.where("Day = 2 and Time = 5").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        }  else {
                            NewClassName ="-1";
                            //onDestroy();
                            //NewLocation = "";
                            //NewClassName = "";
                        }
                        break;
                    case 4://星期三
                        if (calendar.get(Calendar.HOUR_OF_DAY) <= 7) {
                            schedule = LitePal.where("Day = 3 and Time = 1").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        }
                        else if (calendar.get(Calendar.HOUR_OF_DAY) == 8 || calendar.get(Calendar.HOUR_OF_DAY) == 9) {
                            schedule = LitePal.where("Day = 3 and Time = 2").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        }
                        else if (9 < calendar.get(Calendar.HOUR_OF_DAY) && calendar.get(Calendar.HOUR_OF_DAY) <= 13) {
                            schedule = LitePal.where("Day = 3 and Time = 3").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        } else if (14 <= calendar.get(Calendar.HOUR_OF_DAY) && calendar.get(Calendar.HOUR_OF_DAY) <= 15) {  //case 15:
                            schedule = LitePal.where("Day = 3 and Time = 4").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        }
                        else if (15<calendar.get(Calendar.HOUR_OF_DAY)&&calendar.get(Calendar.HOUR_OF_DAY)<=18) {
                            schedule = LitePal.where("Day = 3 and Time = 5").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        }  else {
                            NewLocation ="-1";
                            //  onDestroy();
                           // NewLocation = "";
                           // NewClassName = "";
                        }
                        break;
                    case 5://星期四
                        if (calendar.get(Calendar.HOUR_OF_DAY) <= 7) {
                            schedule = LitePal.where("Day = 4 and Time = 1").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        }
                        else if (calendar.get(Calendar.HOUR_OF_DAY) == 8 || calendar.get(Calendar.HOUR_OF_DAY) == 9) {
                            schedule = LitePal.where("Day = 4 and Time = 2").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        }
                        else if (9 < calendar.get(Calendar.HOUR_OF_DAY) && calendar.get(Calendar.HOUR_OF_DAY) <= 13) {
                            schedule = LitePal.where("Day = 4 and Time = 3").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        } else if (14 <= calendar.get(Calendar.HOUR_OF_DAY) && calendar.get(Calendar.HOUR_OF_DAY) <= 15) {  //case 15:
                            schedule = LitePal.where("Day = 4 and Time = 4").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        }
                        else if (15<calendar.get(Calendar.HOUR_OF_DAY)&&calendar.get(Calendar.HOUR_OF_DAY)<=18) {
                            schedule = LitePal.where("Day = 4 and Time = 5").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        }  else {
                            NewLocation ="-1";
                            //onDestroy();
                            //NewLocation = "";
                            ///NewClassName = "";
                        }
                        break;
                    case 6://星期五
                        if (calendar.get(Calendar.HOUR_OF_DAY) <= 7) {
                            schedule = LitePal.where("Day = 5 and Time = 1").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        }
                        else if (calendar.get(Calendar.HOUR_OF_DAY) == 8 || calendar.get(Calendar.HOUR_OF_DAY) == 9) {
                            schedule = LitePal.where("Day = 5 and Time = 2").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        }
                        else if (9 < calendar.get(Calendar.HOUR_OF_DAY) && calendar.get(Calendar.HOUR_OF_DAY) <= 13) {
                            schedule = LitePal.where("Day = 5 and Time = 3").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        } else if (14 <= calendar.get(Calendar.HOUR_OF_DAY) && calendar.get(Calendar.HOUR_OF_DAY) <= 15) {  //case 15:
                            schedule = LitePal.where("Day = 5 and Time = 4").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        }
                        else if (15<calendar.get(Calendar.HOUR_OF_DAY)&&calendar.get(Calendar.HOUR_OF_DAY)<=18) {
                            schedule = LitePal.where("Day = 5 and Time = 5").findFirst(Schedule.class);
                            NewClassName = schedule.getName();
                            NewLocation = schedule.getLocation();
                        }  else {
                            NewLocation ="-1";
                            //  onDestroy();
                            //NewLocation = "";
                            //NewClassName = "";
                        }
                        break;
                    case 7 :
                        NewLocation ="-1";
                        //onDestroy();
                        break;
                    case  1:
                        NewLocation ="-1";

                        //onDestroy();
                        break;
                }
        //    }
      //  }).start();

       /*
        List<Schedule> schedules1 = LitePal.findAll(Schedule.class);
        for(Schedule schedule : schedules1)
        {
            Log.d("schedule",schedule.getDay()+" "+schedule.getTime()+" "+schedule.getLocation()+" "+schedule.getName()+" " );
        }
        */
         if(NewLocation==null||NewLocation.length()==0){
             NewLocation = "暂无课程";
        }
         calendar = Calendar.getInstance();
            if(calendar.get(Calendar.HOUR_OF_DAY)>=19||calendar.get(Calendar.DAY_OF_WEEK)==7||calendar.get(Calendar.DAY_OF_WEEK)==1){
                  Log.d("Myservice","illegal");
                  onDestroy();
             }
             else {
                 String CHANNEL_ONE_ID = "com.example.uni";
                 String CHANNEL_ONE_NAME = "Channel One";
                 NotificationChannel notificationChannel = null;
                 if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                     notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                             CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
                     notificationChannel.enableLights(true);
                     notificationChannel.setLightColor(Color.RED);
                     notificationChannel.setShowBadge(true);
                     notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                     NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                     manager.createNotificationChannel(notificationChannel);
                 }
                 Log.d("w", "running");

                 Intent intents = new Intent("com.exapmle.uni.My_BroadCast");
                 PendingIntent pi = PendingIntent.getBroadcast(this, 0, intents, FLAG_UPDATE_CURRENT);
                 // RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.foreservice_remoteviews);
                 //NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                 Notification notification = new NotificationCompat.Builder(this, "default")
                         .setContentTitle(NewLocation)
                         .setContentText(NewClassName)
                         //.setShowWhen(false)
                         .setSmallIcon(R.mipmap.ic_launcher)
                         //.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                         .setContentIntent(pi)
                         .setChannelId(CHANNEL_ONE_ID)
                         // .setAutoCancel(true)
                         //.setContent(remoteViews)
                         .setPriority(NotificationCompat.PRIORITY_MAX)
                         .build();

                 // manager.notify(1, notification);
                 startForeground(1, notification);

                 //AlarmManager alarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
                 // Intent i = new Intent(this, MyService.class);
                 ///  PendingIntent pendingIntent = PendingIntent.getService(this, 0, i,0);
                 // alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+60*1000, pendingIntent);
                 // }
             }
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy(){
        Log.d("Myservice","Destroy");
        stopForeground(true);
        super.onDestroy();
    }
}
