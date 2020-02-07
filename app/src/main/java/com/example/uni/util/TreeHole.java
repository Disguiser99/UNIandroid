package com.example.uni.util;

import org.litepal.crud.LitePalSupport;

public class TreeHole {
    String Title;
    String Content;


    public TreeHole(String Title,String Content){
        this.Title = Title;
        this.Content = Content;
    }
    public String getTitle(){
        return Title;
    }
    public String getContent(){
        return Content;
    }

    public void setTitle(){
        this.Title = Title;
    }
    public void setContent(){
        this.Content = Content;
    }

}
