package com.example.uni.util;

public class Community {
    private String title ;
    private int iconID;
    private String content;
    private int count;
    public Community (String title,int iconID,String content,int count){
        this.title = title;
        this.iconID = iconID ;
        this.content = content;
        this.count = count;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    private void setIconID(int iconID){
        this.iconID =iconID;
    }
    public int getIconID(){
        return this.iconID;
    }
    public void setContent(){
        this.content = content;
    }
    public String getContent() {
        return content;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }
}
