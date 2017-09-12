package com.intelligence.androidapplication.ui.navigation.fragment;

import android.widget.Toast;

import com.intelligence.androidapplication.R;

public class PublishFragment extends BaseFragement {

    @Override
    protected void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_publish;
    }

    @Override
    protected void getDataFromServer() {
        Toast.makeText(mContext, "PublishFragment页面请求数据了", Toast.LENGTH_SHORT).show();
    }
}
