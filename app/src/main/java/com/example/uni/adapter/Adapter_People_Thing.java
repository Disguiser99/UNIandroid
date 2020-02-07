package com.example.uni.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.LocaleData;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.uni.Person_PageActivity;
import com.example.uni.R;
import com.example.uni.util.People_Thing;
import com.example.uni.util.PersonInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Adapter_People_Thing extends RecyclerView.Adapter<Adapter_People_Thing.ViewHolder> {
    private Context mContext;
    private List<People_Thing>  mpeople_thing_List;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        View people_thing_view;
        ImageView userIcon,reportIcon;
        TextView username,content,time;
        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            people_thing_view = view ;
            userIcon = (ImageView)view.findViewById(R.id.people_thing_icon);
            username = (TextView)view.findViewById(R.id.people_thing_username);
            reportIcon = (ImageView)view.findViewById(R.id.people_thing_reportID);
            content  = (TextView)view.findViewById(R.id.people_thing_content);
            time   = (TextView)view.findViewById(R.id.people_thing_time);
        }
    }
    public Adapter_People_Thing(List<People_Thing> people_thingsList){
        mpeople_thing_List = people_thingsList;
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent , int viewType){
        if (mContext == null){
        mContext = parent.getContext();
        }
        final View view  = LayoutInflater.from(mContext).inflate(R.layout.people_thing_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.reportIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d("APT::","pick");
                int position = holder.getAdapterPosition();
                Log.d("APT:",position+"");
                People_Thing people_thing = mpeople_thing_List.get(position);
                View views = LayoutInflater.from(mContext).inflate(R.layout.photo_preview_item_popip,null);

                ImageView imageView = (ImageView)views.findViewById(R.id.image);
                String uri_String = people_thing.getUri_string();
                Log.d("APT:",uri_String + " " + position);
                if(uri_String.startsWith("\uFEFF")){
                    uri_String = uri_String.substring(1);
                }
                URL picUrl = null;
                try {
                    picUrl = new URL(uri_String);
                }catch (IOException e){
                    e.printStackTrace();
                }
                final PopupWindow popupWindow =new PopupWindow(views , ViewGroup.LayoutParams.MATCH_PARENT,1200,true);
                Glide.with(mContext).load(picUrl).into(imageView);
                popupWindow.setAnimationStyle(R.anim.photo_preview);
                popupWindow.setTouchable(true);
                popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                        // 这里如果返回true的话，touch事件将被拦截
                        // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                    }
                });

                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
                popupWindow.showAtLocation( parent ,Gravity.CENTER,0,0);//showAtLocation(view, Gravity.CENTER,0,0);
                //bgAlpha(180,view);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                       // bgAlpha(255,view);
                    }
                });

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

        holder.userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 int position  = holder.getAdapterPosition();
                 People_Thing people_thing = mpeople_thing_List.get(position);
                 Intent intent  = new Intent(mContext, Person_PageActivity.class);
                 intent.putExtra("stu_number",people_thing.getStu_number());
                 mContext.startActivity(intent);
            }
        });
        return holder;//添加了点击效果这里一定要改
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        People_Thing people_thing = mpeople_thing_List.get(position);
        String uri_String = people_thing.getUri_string();
        if(uri_String.startsWith("\uFEFF")){
            uri_String = uri_String.substring(1);
        }
        URL picUrl = null;
        //Bitmap bitmap =null;
        try {
            picUrl = new URL(uri_String);
            /*HttpURLConnection connection = (HttpURLConnection) picUrl.openConnection();
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");
            InputStream in = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(in);
            in.close();*/
        }catch (IOException e){
            e.printStackTrace();
        }


        holder.username.setText(people_thing.getUsername());
        holder.content.setText(people_thing.getContent());
        Glide.with(mContext).load(people_thing.getIconID()).into(holder.userIcon);
        //holder.userIcon.setImageResource(people_thing.getIconID());
        //holder.reportIcon.setImageResource(people_thing.getReportID());

        Glide.with(mContext).load(picUrl).into(holder.reportIcon);
        //holder.reportIcon.setImageBitmap(bitmap);
        holder.time.setText(people_thing.getTime());
    }
    @Override
    public int getItemCount(){
        return mpeople_thing_List.size();
    }

    public void bgAlpha(int f,View view){
        //WindowManager.LayoutParams layoutParams = getWindow.getAttributes();
        //layoutParams.alpha = f;
        //getWindow().setAttributes(layoutParams);
        view.getBackground().setAlpha(f);
    }
}
//Glide.with 简直大哥