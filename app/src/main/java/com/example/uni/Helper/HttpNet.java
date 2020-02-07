package com.example.uni.Helper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpNet {

    public static OkHttpClient ClientBuild(int connectTimOut,int readTimeOut){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(connectTimOut, TimeUnit.SECONDS)
                .readTimeout(readTimeOut,TimeUnit.SECONDS)
                .build();
        return client;
    }

    public static Request RequestBuild(String requestUrl, RequestBody requestBody){
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(requestBody)
                .build();
        return request;
    }
}
