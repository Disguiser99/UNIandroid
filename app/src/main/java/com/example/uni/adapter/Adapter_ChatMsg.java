package com.example.uni.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uni.Interface.IItemTouchHelperAdapter;
import com.example.uni.R;
import com.example.uni.util.ChatMsg;

import java.util.Collections;
import java.util.List;

public class Adapter_ChatMsg extends RecyclerView.Adapter<Adapter_ChatMsg.ViewHolder>  implements IItemTouchHelperAdapter {
  private List<ChatMsg> mchatMsgList;
  static class  ViewHolder extends RecyclerView.ViewHolder{
      View chatmsgView;
      ImageView icon;
      TextView  username,content,time;
      public ViewHolder(View view){
          super(view);
          chatmsgView = view ;
          icon = (ImageView)view.findViewById(R.id.ChatIcon);
          username  = (TextView)view.findViewById(R.id.ChatName);
          content   = (TextView)view.findViewById(R.id.ChatContent);
          time      = (TextView)view.findViewById(R.id.ChatTime);
      }
  }
   public Adapter_ChatMsg(List<ChatMsg> chatMsgList){
      mchatMsgList = chatMsgList;
   }
   @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
      View view = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.chatmsg_item,parent,false);
      final ViewHolder holder = new ViewHolder(view);
      holder.chatmsgView.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View v){
                int position = holder.getAdapterPosition();
                ChatMsg chatMsg = mchatMsgList.get(position);
                //操作
          }
      });
      return holder;
   }
   @Override
    public void onBindViewHolder(ViewHolder holder ,int position){
      ChatMsg chatMsg = mchatMsgList.get(position);
      holder.icon.setImageResource(chatMsg.getIconID());
      holder.username.setText(chatMsg.getUsername());
      holder.content.setText(chatMsg.getContent());
      holder.time.setText(chatMsg.getTime());
   }
   @Override
    public int getItemCount(){
      return mchatMsgList.size();
   }
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mchatMsgList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mchatMsgList.remove(position);
        notifyItemRemoved(position);
    }
}
