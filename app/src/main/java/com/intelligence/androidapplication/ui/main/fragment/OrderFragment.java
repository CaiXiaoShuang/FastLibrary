package com.intelligence.androidapplication.ui.main.fragment;

import android.os.Bundle;

import com.intelligence.androidapplication.R;

public class OrderFragment extends BaseFragment {

    public static OrderFragment newInstance() {
        Bundle args = new Bundle();
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_order;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initView() {

    }
}
