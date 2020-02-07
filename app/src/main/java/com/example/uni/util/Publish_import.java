package com.example.uni.util;

public class Publish_import {
    private String name;
    private int imageId;
    public  Publish_import (String name,int imageId){
        this.name = name;
        this.imageId = imageId;
    }

    public String getName(){
        return name ;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getImageId(){
        return imageId;
    }
    public void setImageId(int imageId){
        this.imageId = imageId;
    }

}
