package com.intelligence.androidapplication.ui.navigation;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MenuItem;

import com.intelligence.androidapplication.R;
import com.intelligence.androidapplication.ui.BaseActivity;
import com.intelligence.androidapplication.ui.navigation.fragment.HomeFragment;
import com.intelligence.androidapplication.ui.navigation.fragment.MessageFragment;
import com.intelligence.androidapplication.ui.navigation.fragment.PublishFragment;
import com.intelligence.androidapplication.ui.navigation.widget.NoSlidingViewPaper;

import java.util.ArrayList;

import butterknife.Bind;

public class NavigationActivity  extends BaseActivity {
   @Bind(R.id.vp_main_container)
   NoSlidingViewPaper mViewPager;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_message:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_publish:
                    mViewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        /*初始化显示内容*/
        final ArrayList<Fragment> fgLists = new ArrayList<>(3);
        fgLists.add(new HomeFragment());
        fgLists.add(new MessageFragment());
        fgLists.add(new PublishFragment());
        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fgLists.get(position);
            }

            @Override
            public int getCount() {
                return fgLists.size();
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2); //预加载剩下两页

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        /*给底部导航栏菜单项添加点击事件*/
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
