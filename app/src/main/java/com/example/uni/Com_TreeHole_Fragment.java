package com.example.uni;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.uni.adapter.TestStackAdapter;
import com.example.uni.util.TreeHole;
import com.loopeer.cardstack.AllMoveDownAnimatorAdapter;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.UpDownAnimatorAdapter;
import com.loopeer.cardstack.UpDownStackAnimatorAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.uni.code.UrlCode.COM_TREEHOLE_GET;

public class Com_TreeHole_Fragment extends Fragment  implements CardStackView.ItemExpendListener{
    //private  String[] title = new String[] {"q","w","e","r"};
    //private  String[] content = new String[] {"q","w","e","r"};
    List<TreeHole>  treeHoles = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    private static Integer[] Colors = new Integer[]{
            R.color.color_1,
            R.color.color_2,
            R.color.color_3,
            R.color.color_4,
            R.color.color_5,
            R.color.color_6,
            R.color.color_7,
            R.color.color_8,
            R.color.color_9,
            R.color.color_10,
            R.color.color_11,
            R.color.color_12,
            R.color.color_13,
            R.color.color_14,
            R.color.color_15,
            R.color.color_16,
            R.color.color_17,
            R.color.color_18,
            R.color.color_19,
            R.color.color_20,
            R.color.color_21,
            R.color.color_22,
            R.color.color_23,
            R.color.color_24,
            R.color.color_25,
            R.color.color_26,
    };
    private CardStackView mStackView;
    private TestStackAdapter mTestStackAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //R.layout.fragment_my为该fragment的布局
        View view=inflater.inflate(R.layout.fragment_com_treehole,container,false);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.fct_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshlist();
            }
        });
        initWight(view);
        return view;
    }

    private void initWight (View view){
        mStackView = (CardStackView)view.findViewById(R.id.cardStackView);
        mTestStackAdapter = new TestStackAdapter(getContext());
        mStackView.setAdapter(mTestStackAdapter);
        mStackView.setItemExpendListener(this);
        /*
        for(int i = 0 ;i <4 ;i++){
            TreeHole treeHole = new TreeHole(title[i],content[i]);
            treeHoles.add(treeHole);
        }
        */
        refreshlist();
    }

    @Override
    public void onItemExpend(boolean expend) {
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        uploadlist();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
    public void uploadlist(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(60,TimeUnit.SECONDS)
                            .build();
                    Request request = new Request.Builder()
                            .url(COM_TREEHOLE_GET)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if(responseData.equals("none")){
                    }else {
                        try{
                            JSONArray jsonArray = new JSONArray(responseData);
                            int size = treeHoles.size();
                            Log.d("CtF: ","treesize"+size+"jsonlen"+jsonArray.length());
                            for(int i = 0 ; i<(jsonArray.length()-size);i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                TreeHole treeHole = new TreeHole(jsonObject.getString("title"),jsonObject.getString("content"));
                                treeHoles.add(i,treeHole);
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
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        Integer[] Colorlist = new Integer[treeHoles.size()];
                        for (int i = 0; i < treeHoles.size(); i++) {
                                  Colorlist[i] = Colors[i%26];
                        }
                        mTestStackAdapter.updateData(Arrays.asList(Colorlist),treeHoles);
                    }
                },200);
    }
}
//https://github.com/loopeer/CardStackView