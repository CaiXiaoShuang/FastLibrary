package com.intelligence.androidapplication.ui.main.fragment;

import android.os.Bundle;

import com.intelligence.androidapplication.R;
import com.intelligence.androidapplication.utils.CommonDialog;

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
        final CommonDialog confirmDialog = new CommonDialog(mActivity);
        confirmDialog.show();
        confirmDialog.setClicklistener(new CommonDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {

            }
        });
    }
}
