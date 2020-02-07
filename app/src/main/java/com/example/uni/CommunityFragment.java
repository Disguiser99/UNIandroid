package com.example.uni;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.uni.adapter.Adapter_Publish_Import;
import com.example.uni.util.Publish_import;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {
    @Nullable
    private List<Publish_import> publish_importList = new ArrayList<>();
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private String[] titles = new String[]{"人","物","树洞"};
    private Button btnPopup;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //R.layout.fragment_my为该fragment的布局
        //super.onActivityCreated(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_community, container, false);
        //右上角的标签
        Toolbar toolbar  = (Toolbar)view.findViewById(R.id.toolbar_community);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        mViewPager = (ViewPager) view.findViewById(R.id.community_viewPager);

        final ArrayList<Fragment> fgLists = new ArrayList<>(3);
        fgLists.add(new Com_People_Fragment());
        fgLists.add(new Com_Thing_Fragment());
        fgLists.add(new Com_TreeHole_Fragment());

        //设置适配器用于装载Fragment
        FragmentPagerAdapter mPagerAdapter=new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fgLists.get(position);  //得到Fragment
            }
            @Override
            public int getCount() {
                return fgLists.size();  //得到数量
            }
        };
        mViewPager.setAdapter(mPagerAdapter);   //设置适配器
        mViewPager.setOffscreenPageLimit(2);

        tabLayout = (TabLayout)view.findViewById(R.id.tab_lay);
        for(int i = 0;i<titles.length;i++){
            tabLayout.addTab(tabLayout.newTab());
        }
        tabLayout.setupWithViewPager(mViewPager);
        for(int i=0;i<titles.length;i++){
            tabLayout.getTabAt(i).setText(titles[i]);
        }
        initPublish_import();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.toolbar_community,menu);
    }
    //控制toolbar下面的业务逻辑
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.add_push:
                View popupView =((AppCompatActivity) getActivity()).getLayoutInflater().inflate(R.layout.popupwindow,null);
                ListView lv_msg = (ListView)popupView.findViewById(R.id.lv_msg);

                Adapter_Publish_Import adapter_publish_import = new Adapter_Publish_Import(getActivity(),R.layout.publish_import_item,publish_importList);
                lv_msg.setAdapter(adapter_publish_import);
                // TODO: 创建PopupWindow对象，指定宽度和高度
                final PopupWindow window = new PopupWindow(popupView,400,550);
                window.setAnimationStyle(R.style.popup_window_anim);
               // TODO: 设置背景颜色
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
              // TODO:  设置可以获取焦点
                window.setFocusable(true);
              // TODO:  设置可以触摸弹出框以外的区域
                window.setOutsideTouchable(true);
                // TODO：更新popupwindow的状态

                window.update();
                window.showAtLocation(view, Gravity.CENTER,0,0);
                bgAlpha(0.618f);
                window.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        bgAlpha(1.0f);
                    }
                });
                lv_msg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                Intent intent = new Intent(getContext(), Com_Push_PeopleActivity.class);
                                getActivity().startActivity(intent);
                                window.dismiss();
                                break;
                            //case 1:    物的
                            case 2:
                                Intent intent2 = new Intent(getContext(),Com_Push_TreeActivity.class);
                                getActivity().startActivity(intent2);
                                window.dismiss();
                                break;
                        }
                        // Toast.makeText(getContext(),"you click me"+position,Toast.LENGTH_LONG).show();
                    }
                });
                // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
                // window.showAsDropDown(btnPopup, 0, 20);
               // window.showAsDropDown(tabLayout, 0, 0);//msgView就是我们menu中的btn_msg
                // Toast.makeText(getContext(),"you click me",Toast.LENGTH_LONG).show();
                //break;
        }
        return true;
    }
    private  void initPublish_import(){
        //寻人表白
        //拾物遗物
        //倾诉心声
        Publish_import people = new Publish_import("寻人表白",R.drawable.icon1);
        publish_importList.add(people);
        Publish_import thing = new Publish_import("拾物遗物",R.drawable.icon2);
        publish_importList.add(thing);
        Publish_import spoken = new Publish_import("倾诉心声",R.drawable.icon3);
        publish_importList.add(spoken);
    }

    public void bgAlpha(float f){
        WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
        layoutParams.alpha = f;
        getActivity().getWindow().setAttributes(layoutParams);
    }
}

//https://blog.csdn.net/csdnzouqi/article/details/51433633 popouwindow
