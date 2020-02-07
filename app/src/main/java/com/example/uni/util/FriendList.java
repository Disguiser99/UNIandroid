package com.example.uni.util;

public class FriendList {
    private int iconID;
    private String username;
    private String content;
    private String single;

    public FriendList(String username,String content,String single,int iconID){
        this.username = username;
        this.content  = content;
        this.single   = single;
        this.iconID   = iconID;
    }

    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return username;
     }

    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return content;
    }

    public void setSingle(String single){
        this.single = single;
    }
    public String getSingle(){
        return single;
    }

    public void setIconID(){
        this.iconID = iconID;
    }

    public int getIconID(){
        return iconID;
    }

}
