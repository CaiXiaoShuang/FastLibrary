package com.intelligence.androidapplication.ui.main.fragment;

import android.os.Bundle;

import com.intelligence.androidapplication.R;

public class HomeFragment extends BaseFragment {

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

    }
}
