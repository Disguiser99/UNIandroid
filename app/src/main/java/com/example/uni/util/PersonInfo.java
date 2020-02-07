package com.example.uni.util;

import android.provider.ContactsContract;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.security.Signature;
import java.util.Calendar;

public class PersonInfo extends LitePalSupport
{
    private String StudentNum;
    private String Password;
    private String Name;
    private String College;
    private String Major;
    private String Classes;
    private String Nickname;
    private String Signature;
    private int heat;
    private int single_degree;
    private String sex;
    private String contact;
    private String TelephoneNumber;

    public String getContact(){return contact;}
    public void setContact(String contact){this.contact=contact;}

    public String getSex(){return sex;}
    public void setSex(String sex){this.sex =  sex;}

    public String getSignature(){return Signature;}
    public void setSignature(String Signature){this.Signature = Signature;}

    public int getHeat(){return heat;}
    public void setHeat(int heat){this.heat = heat;}

    public int getSingle_degree(){return  single_degree;}
    public void  setSingle_degree(int single_degree){this.single_degree = single_degree;}

    public String getNickname(){return Nickname;}
    public void setNickname(String Nickname){this.Nickname = Nickname;}

    public String getStudentNum(){
        return StudentNum;
    }
    public void setStudentNum(String StudentNum){
        this.StudentNum = StudentNum;
    }

    public String getPassword(){
        return Password;
    }
    public void setPassword(String Password){
        this.Password = Password;
    }

    public String getName(){
        return Name;
    }
    public void setName(String Name){
        this.Name = Name;
    }

    public String getCollege(){
        return College;
    }
    public void setCollege(String College){
        this.College = College;
    }

    public String getMajor(){
        return Major;
    }
    public void setMajor(String Major){
        this.Major = Major;
    }

    public String getClasses(){
        return Classes;
    }
    public void setClasses(String Classes){
        this.Classes = Classes;
    }

    public String getTelephoneNumber(){
        return TelephoneNumber;
    }
    public void setTelephoneNumber(String TelephoneNumber){this.TelephoneNumber = TelephoneNumber;}


}
