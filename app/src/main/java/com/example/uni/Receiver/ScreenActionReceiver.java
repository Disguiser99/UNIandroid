package com.example.uni.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.example.uni.Service.MyService;

public class ScreenActionReceiver extends BroadcastReceiver {
    private String TAG = "ScreenActionReceiver";
    private boolean isRegisterReceiver = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String action = intent.getAction();
        if(action.equals(Intent.ACTION_SCREEN_ON)){

            Intent intent1 = new Intent(context, MyService.class);
            context.stopService(intent1);
            context.startService(intent1);

            Log.d(TAG, "屏幕解锁广播...");
        } else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            Log.d(TAG, "屏幕加锁广播...");
        }
    }
    public void registerScreenActionReceiver(Context mContext) {
        if (!isRegisterReceiver) {
            isRegisterReceiver = true;

            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_SCREEN_ON);
            Log.d(TAG, "注册屏幕解锁、加锁广播接收者...");
            mContext.registerReceiver(ScreenActionReceiver.this, filter);
        }
    }

    public void unRegisterScreenActionReceiver(Context mContext) {
        if (isRegisterReceiver) {
            isRegisterReceiver = false;
            Log.d(TAG, "注销屏幕解锁、加锁广播接收者...");
            mContext.unregisterReceiver(ScreenActionReceiver.this);
        }
    }

}
