package com.example.uni.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.uni.R;
import com.example.uni.util.CommunityList;

import java.util.List;

public class Adapter_Com_list extends RecyclerView.Adapter<Adapter_Com_list.ViewHolder> {
    private Context mContext;
    private List<CommunityList> communityLists;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View CommunityList_view;
        TextView text_master,text_contact,text_content;
        Button bt_coming;
        public ViewHolder(View view){
            super(view);
            CommunityList_view = view;
            text_master  = (TextView)view.findViewById(R.id.text_master);
            text_content = (TextView)view.findViewById(R.id.text_content);
            text_contact = (TextView)view.findViewById(R.id.text_contact);
            bt_coming    = (Button)view.findViewById(R.id.bt_coming);
        }
    }
    public Adapter_Com_list(List<CommunityList> communityLists1){
        communityLists = communityLists1;
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        if (mContext == null){
            mContext = parent.getContext();
        }
        final View view  = LayoutInflater.from(mContext).inflate(R.layout.communitylist_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.bt_coming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                CommunityList communityList = communityLists.get(position);

            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        CommunityList communityList = communityLists.get(position);
        holder.text_master.setText(communityList.getMatser());
        holder.text_content.setText(communityList.getContent());
        holder.text_contact.setText(communityList.getContact());
        //holder.bt_coming.setBackground;
    }
    @Override
    public int getItemCount(){
        return communityLists.size();
    }
}
