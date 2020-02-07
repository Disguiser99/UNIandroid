package com.example.uni.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uni.R;
import com.example.uni.util.FriendList;

import java.util.List;

public class Adapter_FriendList extends RecyclerView.Adapter<Adapter_FriendList.ViewHolder>  {
  private List<FriendList> mFriendList;
  static class ViewHolder extends RecyclerView.ViewHolder{
      View friendlistView;
      ImageView icon;
      TextView username,content,single;
      public ViewHolder(View view){
          super(view);
          friendlistView = view;
          icon = (ImageView)view.findViewById(R.id.FriendList_Icon);
          username = (TextView)view.findViewById(R.id.FriendList_Name);
          content = (TextView)view.findViewById(R.id.FriendList_Content);
          single  = (TextView)view.findViewById(R.id.FriendList_single);
      }
  }
  public Adapter_FriendList(List<FriendList> friendLists){
      mFriendList = friendLists ;
  }
  @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
      View view = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.friendlist_item,parent,false);
      final ViewHolder holder = new ViewHolder(view);
      holder.friendlistView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              int position = holder.getAdapterPosition();
              FriendList friendList = mFriendList.get(position);
              //操作
          }
      });
     return holder;
  }
  @Override
  public void onBindViewHolder(ViewHolder holder,int position){
      FriendList friendList = mFriendList.get(position);
      holder.icon.setImageResource(friendList.getIconID());
      holder.username.setText(friendList.getUsername());
      holder.content.setText(friendList.getContent());
      holder.single.setText(friendList.getSingle());
  }
  @Override
    public int getItemCount(){
      return mFriendList.size();
    }
}




