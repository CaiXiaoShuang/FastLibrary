package com.intelligence.androidapplication.ui.main.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.intelligence.androidapplication.R;

import butterknife.Bind;

public class HomeFragment extends BaseFragment {
   @Bind(R.id.tv_wo)
    TextView tv_wo;
    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initView() {
        tv_wo.setText("首页Text");
    }
}
