package com.example.uni;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.uni.adapter.Adapter_Com_list;
import com.example.uni.util.CommunityList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.uni.code.UrlCode.COM_COMMUNITYLIST_GET;

public class CommunityListActivity extends AppCompatActivity {
    private List<CommunityList> communityLists = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private String position;
    private Adapter_Com_list adapter_com_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_list);
        //改变默认策略允许在主进程中进行网络请求
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        refreshlist();
        position = getIntent().getStringExtra("position");
        Toolbar toolbar = (Toolbar)findViewById(R.id.comlist_toolbar);
        setSupportActionBar(toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.comlist_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshlist();
            }
        });
        uploadlist();
        RecyclerView recyclerView  = (RecyclerView)findViewById(R.id.comlist_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter_com_list = new Adapter_Com_list(communityLists);
        adapter_com_list.notifyDataSetChanged();
        recyclerView.setAdapter(adapter_com_list);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.comlist_meau,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case  R.id.push:
                Intent intent = new Intent(CommunityListActivity.this,Comlist_PushActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
                //Toast.makeText(this ,"you click this",Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }
    private void uploadlist(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                 try{
                     OkHttpClient client = new OkHttpClient.Builder()
                             .connectTimeout(30, TimeUnit.SECONDS)
                             .readTimeout(60, TimeUnit.SECONDS)
                             .build();

                     RequestBody requestBody = new FormBody.Builder()
                             .add("position",position)
                             .build();

                     Request request = new Request.Builder()
                             .url(COM_COMMUNITYLIST_GET)
                             .post(requestBody)
                             .build();
                     Response response = client.newCall(request).execute();
                     String responseData = response.body().string();
                     Log.d("CLA:",responseData);
                     if(responseData.equals("none")){
                     }else{
                         try{
                             JSONArray jsonArray = new JSONArray(responseData);
                             Log.d("CLP:","comList.size"+communityLists.size()+"jsonlen" + jsonArray.length());
                             int size = communityLists.size();
                             for(int i = 0 ; i<jsonArray.length()-size;i++){
                                 JSONObject jsonObject = jsonArray.getJSONObject(i);
                                 CommunityList communityList = new CommunityList();
                                 communityList.setMatser("发起者："+jsonObject.getString("stu_number"));
                                 communityList.setContact("联系方式："+jsonObject.getString("contact"));
                                 communityList.setContent(jsonObject.getString("content"));
                                 communityLists.add(i,communityList);
                                 Log.d("CLP::","comList.size"+communityLists.size()+"jsonlen" + jsonArray.length());
                             }
                         }catch (Exception e){
                             e.printStackTrace();
                         }
                     }
                 }catch (Exception e){
                     e.printStackTrace();
                 }
            }
        }).start();

    }
    private void refreshlist(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        uploadlist();
                        adapter_com_list.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

}
