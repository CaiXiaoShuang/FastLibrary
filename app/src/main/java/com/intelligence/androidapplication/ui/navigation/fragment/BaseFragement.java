package com.intelligence.androidapplication.ui.navigation.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragement extends Fragment {
    public View mView;
    public boolean isViewInitiated; //当前页面是否初始化
    public boolean isVisibleToUser; //当前页面是否显示
    public boolean isDataRequested; //是否已经请求了数据
    public Context mContext;
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        mContext =getActivity();
        mView = inflater.inflate(getLayoutId(),container, false);
        isViewInitiated = true;
        initView(mView);
        prepareGetData();
        return mView;
    }
    /*布局*/
    public abstract int getLayoutId();
    /*服务器获取数据*/
    protected abstract void getDataFromServer();
    /*初始化页面布局和数据*/
    protected abstract void initView(View mView);
    /**
     * 当前页面是否展示
     * @param isVisibleToUser 显示为true， 不显示为false
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareGetData();
    }

    /**
     * 如果只想第一次进入该页面请求数据，return prepareGetData(false)
     * 如果想每次进入该页面就请求数据，return prepareGetData(true)
     * @return
     */
    private boolean prepareGetData(){
        return prepareGetData(false);
    }

    /**
     * 判断是否从服务器器获取数据
     * @param isforceUpdate 强制更新的标记
     * @return
     */
    protected boolean prepareGetData(boolean isforceUpdate) {
        if(isVisibleToUser && isViewInitiated && (!isDataRequested || isforceUpdate)){
            /*从服务器获取数据*/
            getDataFromServer();
            isDataRequested = true;
            return true;
        }
        return false;
    }
}
