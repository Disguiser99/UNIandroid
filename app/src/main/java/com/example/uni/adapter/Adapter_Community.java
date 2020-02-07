package com.example.uni.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.uni.CommunityListActivity;
import com.example.uni.R;
import com.example.uni.util.Community;

import java.util.Calendar;
import java.util.List;

public class Adapter_Community extends RecyclerView.Adapter<Adapter_Community.ViewHolder> {
    private Context mcontext;
    private List<Community> communities;
    static  class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView ;
        ImageView iconID;
        TextView title , content ,count;
        public ViewHolder(View view){
            super(view);
            cardView = (CardView)view;
            title = (TextView)view.findViewById(R.id.community_title);
            iconID = (ImageView)view.findViewById(R.id.community_iconID);
            count = (TextView)view.findViewById(R.id.community_count);
            content = (TextView)view.findViewById(R.id.community_content);
        }
    }
    public Adapter_Community(List<Community> communities1){
        communities =communities1;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if (mcontext == null){
            mcontext = parent.getContext();
        }
        View view = LayoutInflater.from(mcontext).inflate(R.layout.community_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Intent intent= new Intent(mcontext, CommunityListActivity.class);
                intent.putExtra("position",position+"");
                mcontext.startActivity(intent);
            }
        });
           return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Community community =communities.get(position);
        holder.title.setText(community.getTitle());
        Glide.with(mcontext).load(community.getIconID()).into(holder.iconID);
        holder.count.setText(community.getCount()+"");
        holder.content.setText(community.getContent());
    }
     @Override
    public int getItemCount(){
        return communities.size();
     }
}
