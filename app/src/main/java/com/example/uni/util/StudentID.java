package com.example.uni.util;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

public class StudentID  extends LitePalSupport {
    private String forenumber;
    private String college;
    private String major;


    public StudentID(String forenumber,String college,String major){
        this.forenumber = forenumber;
        this.college    = college;
        this.major   = major;
    }

    public void setForenumber(String forenumber){
        this.forenumber = forenumber;
    }
    public String getForenumber(){
        return forenumber;
    }

    public void setCollege(String college){
        this.college = college;
    }
    public String getCollege(){
        return college;
    }
    public void setMajor(String major){
        this.major = major;
    }
    public String getMajor(){
        return major;
    }


}
