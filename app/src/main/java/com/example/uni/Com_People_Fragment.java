package com.example.uni;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.MalformedJsonException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.uni.adapter.Adapter_People_Thing;
import com.example.uni.util.Markalive;
import com.example.uni.util.People_Thing;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.uni.code.UrlCode.COM_PEOPLE_GET;

public class Com_People_Fragment extends Fragment {

    private List<People_Thing> people_thingList = new ArrayList<>();
    private Adapter_People_Thing adapter_people_thing;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //R.layout.fragment_my为该fragment的布局
        View view=inflater.inflate(R.layout.fragment_com_people,container,false);
       // initPeople_Thing();
        //改变默认策略允许在主进程中进行网络请求
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.people_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.fcp_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshlist();
             //   searchdatabase();
            }
        });

        adapter_people_thing = new Adapter_People_Thing(people_thingList);
        searchdatabase();
        recyclerView.setAdapter(adapter_people_thing);

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void searchdatabase(){
        List<People_Thing> people_things = null;
        try {
        people_things = LitePal.order("time desc").find(People_Thing.class);
            Log.d("CPFFFFF :",people_things.size()+"");
        }catch (Exception e){
            e.printStackTrace();
        }
        if(people_things.size()!=0) {
            //Log.d("CF :",people_things.size()+"");
            for (int i = 0; i < people_things.size() ; i++) {
                people_thingList.add(i, people_things.get(i));
            }
            /*for (int i = 0; i < people_things.size(); i++) {
                Log.d("CPF,,:","i:"+i+"peoplesize:"+(people_things.size()-i-1));
                people_thingList.add(i, people_things.get(people_things.size()-i-1));
            }*/
            adapter_people_thing.notifyDataSetChanged();
        }
    }
    private void initPeople_Thing(){
        //people_thingList;
      // LitePal.deleteAll(People_Thing.class);
      // people_thingList.clear();
       adapter_people_thing.notifyDataSetChanged();
       new Thread(new Runnable() {
            @Override
            public void run() {
               try {
                   OkHttpClient client = new OkHttpClient.Builder()
                           .connectTimeout(30, TimeUnit.SECONDS)
                           .readTimeout(60, TimeUnit.SECONDS)
                           .build();

                   Request request = new Request.Builder()
                           .url(COM_PEOPLE_GET)
                           .build();
                   Response response = client.newCall(request).execute();
                   String responseData = response.body().string();
                   Log.d("CPF:",responseData);
                   if(responseData.equals("none")){
                   }else{
                       try{
                           JSONArray jsonArray = new JSONArray(responseData);
                           int size = people_thingList.size();
                           Log.d("CPFFF： ",jsonArray.length()+"  "+people_thingList.size());
                           for(int i = 0; i<(jsonArray.length()-size );i++){
                               JSONObject jsonObject = jsonArray.getJSONObject(i);
                               String uri ="http://118.25.42.59:8000/uploads/"+ jsonObject.getString("image");
                               //URL picUrl = new URL(uri);
                               //Bitmap bitmap = BitmapFactory.decodeStream(picUrl.openStream());
                               //picUrl.openStream().close();
                               //String username,String content,String time,int iconID,String  uri_string
                               People_Thing people_thing = new People_Thing(jsonObject.getString("nickname"),
                                       jsonObject.getString("content"),jsonObject.getString("time"),R.drawable.head,uri,jsonObject.getString("stu_number"));
                               people_thingList.add(i,people_thing);
                               people_thing.save();
                               Log.d("CPF::",jsonObject.getString("nickname")+
                                       jsonObject.getString("content")+jsonObject.getString("time")+R.drawable.head+uri+people_thingList.size());
                           }
                       }catch (Exception e){
                           e.printStackTrace();
                       }
                   }
               }catch (Exception e){
                   getActivity().runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           Toast.makeText(getContext(),"刷新失败！",Toast.LENGTH_LONG).show();
                       }
                   });
                   e.printStackTrace();
               }
            }
        }).start();
        /*
        for (int i = 0 ;i<5;i++){
            People_Thing people_thing = new People_Thing("Disguiser","要是多一次坚持，结局会不会不一样\n" +
                    "要是多一点勇气，是不是能改变一切\n" +
                    "我不敢去想，有什么侥幸，我怕想着的事情不会发生\n" +
                    "是不是想念一个人，她就会出现在任何地方\n","2019/2/4",R.drawable.icon1,);
             people_thingList.add(people_thing);
        }*/
   }
   private void refreshlist(){
          new Thread(new Runnable() {
              @Override
              public void run() {
                  try{
                      Thread.sleep(2000);
                  }catch (InterruptedException e){
                      e.printStackTrace();
                  }
                  getActivity().runOnUiThread(new Runnable(){
                     @Override
                     public void run(){
                         initPeople_Thing();
                         adapter_people_thing.notifyDataSetChanged();
                         //people_thingList.clear();
                         swipeRefreshLayout.setRefreshing(false);
                     }
                  });
              }
          }).start();
          //people_thingList.clear();
   }

}