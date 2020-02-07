package com.example.uni;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uni.Helper.MyItemTouchHelperCallback;
import com.example.uni.adapter.Adapter_ChatMsg;
import com.example.uni.util.ChatMsg;

import java.util.ArrayList;
import java.util.List;

public class MsgFragment extends Fragment {

    private List<ChatMsg>  chatMsgList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //R.layout.fragment_my为该fragment的布局
        View view=inflater.inflate(R.layout.fragment_msg,container,false);
        //初始化信息
        initChatMsg();
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.msg_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        Adapter_ChatMsg adapter_chatMsg = new Adapter_ChatMsg(chatMsgList);
        recyclerView.setAdapter(adapter_chatMsg);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(new MyItemTouchHelperCallback(adapter_chatMsg));
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initChatMsg(){
        for (int i = 0;i<10;i++){
            ChatMsg chatMsg = new ChatMsg("disguiser","您的倾慕者戳了你一下！","2018/10",R.drawable.head);
            chatMsgList.add(chatMsg);
        }
    }

}
