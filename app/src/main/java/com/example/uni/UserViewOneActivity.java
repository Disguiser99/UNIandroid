package com.example.uni;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.uni.Helper.BottomNavigationViewHelper;
import com.example.uni.Helper.DrawerLeftEdgeSize;
import com.example.uni.Helper.MyViewPager;
import com.example.uni.util.Markalive;
import com.example.uni.util.PersonInfo;

import org.litepal.LitePal;

import java.util.ArrayList;

public class UserViewOneActivity extends AppCompatActivity  {

        private DrawerLayout mDrawerLayout;
        private BottomNavigationView bottomNavigation;
        private MyViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uni_userviewone);

        //mViewPager滑动翻页
        mViewPager   =  (MyViewPager)findViewById(R.id.mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                bottomNavigation.getMenu().getItem(position).setChecked(true);
                //滑动页面后做的事，这里与BottomNavigationView结合，使其与正确page对应
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //侧滑
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        //设置全屏侧滑
        DrawerLeftEdgeSize.setLeftEdgeSize(this,mDrawerLayout,0.4f);
        NavigationView navView  = (NavigationView)findViewById(R.id.nav_view);
        View headerView = navView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stu_number = LitePal.findFirst(Markalive.class).getStu_number();
                Intent intent = new Intent(UserViewOneActivity.this, Person_PageActivity.class);
                intent.putExtra("stu_number",stu_number);
                startActivity(intent);
            }
        });

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
               /* if (id == R.id.){
               }*/
               switch (id){
                   case R.id.Schedule_nav:
                       Intent intent = new Intent(UserViewOneActivity.this,ScheduleActivity.class);
                       startActivity(intent);
                       break;
                   case R.id.MealCard_nav:
                       Intent intent1 = new Intent(UserViewOneActivity.this, MealCardActivity.class);
                       startActivity(intent1);
                       break;
                   case R.id.Askforleave_nav:
                       Intent intent2 = new Intent(UserViewOneActivity.this, AskForLeaveActivity.class);
                       startActivity(intent2);
                       break;
                   case R.id.Modify_info_nav:
                       Intent intent3 = new Intent(UserViewOneActivity.this,ModifyPersonInfoActivity.class);
                       startActivity(intent3);
                       break;
                   case R.id.Exit_nav:
                       LitePal.deleteAll(PersonInfo.class);
                       Intent intent4 = new Intent(UserViewOneActivity.this,LoginActivity.class);
                       //销毁所有之前的活动
                       LitePal.deleteAll(Markalive.class);
                       intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                       startActivity(intent4);
                       break;
               }
                return true;
            }
        });

        //底部导航栏有几项就有几个Fragment
        final ArrayList<Fragment> fgLists=new ArrayList<>(3);
        fgLists.add(new MsgFragment());
        fgLists.add(new FriendFragment());
        fgLists.add(new CommunityFragment());

        //设置适配器用于装载Fragment
        FragmentPagerAdapter mPagerAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
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

        //底部
        bottomNavigation  = (BottomNavigationView)findViewById(R.id.bottomNavigation);
        BottomNavigationViewHelper.removeNavigationShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.msg_item:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.friend_item:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.community_item:
                        mViewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
    }
}
