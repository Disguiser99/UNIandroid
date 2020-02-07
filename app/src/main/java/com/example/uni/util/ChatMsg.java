package com.example.uni.util;

import java.util.ArrayList;
import java.util.List;

public class ChatMsg {

    private int iconID;
    private String username;
    private String content;
    private String time;

   public ChatMsg(String username,String content,String time,int iconID){
       this.username = username;
       this.content  = content;
       this.time      = time ;
       this.iconID    = iconID;
   }

   //public static List<ChatMsg> chatMsgList = new ArrayList<>();

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

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }


}
