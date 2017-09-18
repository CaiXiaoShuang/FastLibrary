package com.intelligence.androidapplication.ui.main.fragment;

import android.os.Bundle;

import com.intelligence.androidapplication.R;

public class DeliveryFragment extends BaseFragment {

    public static DeliveryFragment newInstance() {
        Bundle args = new Bundle();
        DeliveryFragment fragment = new DeliveryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_delivery;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initView() {

    }
}
