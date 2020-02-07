package com.example.uni.util;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.net.URL;

public class People_Thing extends LitePalSupport {
    private int iconID;
    private String username;
    private String content;
    private String time;
    private String  uri_string;
    private String stu_number ;

    public People_Thing(String username,String content,String time,int iconID,String  uri_string,String stu_number){
        this.username = username;
        this.content  = content;
        this.time      = time ;
        this.iconID    = iconID;
        this.uri_string = uri_string;
        this.stu_number = stu_number;
    }

    public String getStu_number(){return stu_number;}

    public void setStu_number(String stu_number){this.stu_number = stu_number;}

    public String getUsername(){
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setTime(String time){
        this.time = time ;
    }

    public String getTime(){
        return time;
    }

    public int getIconID() { return iconID; }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public String getUri_string(){ return uri_string; }

    public void setUri_string(String uri_string){this.uri_string = uri_string;}
}
