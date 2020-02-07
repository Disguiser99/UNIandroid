package com.example.uni;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uni.adapter.Adapter_Community;
import com.example.uni.adapter.Adapter_FriendList;
import com.example.uni.util.Community;
import com.example.uni.util.FriendList;

import java.util.ArrayList;
import java.util.List;

public class FriendFragment extends Fragment {

    //private List<FriendList> friendLists = new ArrayList<>();
    private  List<Community> communityList = new ArrayList<>();
    private Adapter_Community adapter_community;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //R.layout.fragment_my为该fragment的布局
        View view=inflater.inflate(R.layout.fragment_friend,container,false);
        initCommunityList();
        //initfriendList();
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.friend_recyclerview);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(layoutManager);

        //Adapter_FriendList adapter_friendList = new Adapter_FriendList(friendLists);
        //recyclerView.setAdapter(adapter_friendList);
         adapter_community = new Adapter_Community(communityList);
         recyclerView.setAdapter(adapter_community);

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

   private void initCommunityList(){
            Community community = new Community("夜跑",R.drawable.run_logo,"锻炼交友两不误!",99);
            communityList.add(community);
            community = new Community("学习",R.drawable.logo_learn,"相互监督更高效",99);
            communityList.add(community);
            community = new Community("拼车",R.drawable.logo_carpool,"经济出行更快乐",99);
            communityList.add(community);
            community = new Community("团购",R.drawable.logo_purcharse,"购打折更实惠",99);
            communityList.add(community);
            community = new Community("易物",R.drawable.logo_transaction,"以物换物更环保",99);
            communityList.add(community);
   }
/*
   private void initfriendList(){
      for (int i=0;i<10;i++){
          FriendList friendList = new FriendList("disguiser","归来是词","是",R.drawable.icon1);
          friendLists.add(friendList);
      }
   }*/
}
