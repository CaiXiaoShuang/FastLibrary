package com.intelligence.androidapplication.ui.main.fragment;

import android.os.Bundle;

import com.intelligence.androidapplication.R;

/**
 * Created by Administrator
 * on 2017/9/18.
 */

public class MyFragment extends BaseFragment {

    public static MyFragment newInstance() {
        Bundle args = new Bundle();
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initView() {

    }
}
