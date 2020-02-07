package com.example.uni.util;

import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;


import java.sql.Time;

public class Schedule extends LitePalSupport  {
    private int Day;
    private int Time ;
    private String Location;
    private String Name;

    public Schedule (int day,int time , String location ,String className){
        this.Day = day;
        this.Time = time ;
        this.Location = location;
        this.Name = className;
    }

    public int getDay(){return Day;}

    public void setDay(int day){this.Day = day ;}

    public int getTime(){
        return Time;
    }

    public void setTime(int time){
        this.Time = time ;
    }

    public String getLocation(){
        return Location;
    }

    public void setLocation(String location){
        this.Location = location;
    }

    public String getName(){
        return Name;
    }

    public void  setClassName(String ClassName){
        this.Name = ClassName;
    }
}
