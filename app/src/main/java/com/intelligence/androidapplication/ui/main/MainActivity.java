package com.intelligence.androidapplication.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.intelligence.androidapplication.R;
import com.intelligence.androidapplication.event.PendingEvent;
import com.intelligence.androidapplication.parameter.MainParameter;
import com.intelligence.androidapplication.retrofit.ApiCallback;
import com.intelligence.androidapplication.ui.BaseActivity;
import com.intelligence.androidapplication.ui.main.model.MainModel;
import com.intelligence.androidlibrary.rxjava.RxBus;
import com.intelligence.androidlibrary.slidesidemenu.SlideSideMenuTransitionLayout;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    @Bind(R.id.tv_name)
    TextView tv_name;//用户名
    private SlideSideMenuTransitionLayout mSlideSideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Grab the widget
        mSlideSideMenu = (SlideSideMenuTransitionLayout) findViewById(R.id.slide_side_menu);
        //启动侧拉
//       mSlideSideMenu.toggle();

        tv_name.setText("快速开发框架");
    }

    @Override
    public void onBackPressed() {
        if (mSlideSideMenu != null && mSlideSideMenu.closeSideMenu()) {
            // Closed the side menu, override the default back pressed behavior
            return;
        }
        super.onBackPressed();
    }


    @OnClick({R.id.tv_name})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_name://
                toastShow("o(>﹏<)o不要啊");
                break;
        }
    }


    /**
     * @接口请求
     */
    private void btnLogin() {
        //加载对话框
        showProgressDialog();
        //请求参数
        MainParameter mainParameter = new MainParameter();
        mainParameter.setUserName("name");
        mainParameter.setUserPswd("123");
        //开始请求
        addSubscription(apiStores.userLogin(mainParameter),
                new ApiCallback<MainModel>() {
                    @Override
                    public void onSuccess(MainModel model) {
                        //成功请求内容
                    }

                    @Override
                    public void onFailure(MainModel model, String msg) {
                        toastShow(msg);
                    }

                    @Override
                    public void onFailure(String msg) {
                        toastShow(msg);
                    }


                    @Override
                    public void onFinish() {
                        //不管失败/成功都会走
                        dismissProgressDialog();
                    }

                });
    }


    /**
     * @Rxjava
     * @用于布局，通知更新
     **/
    private void rxBusObservers() {
        //发送事件
        RxBus.getInstance().post(new PendingEvent());
        //接受事件
        addSubscription(RxBus.getInstance()
                .toObserverable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Object event) {
                        if (event instanceof PendingEvent) {
                            //是否有事件
                        }
                    }
                }));
    }


    /**
     * 退出登录
     */
    private void getQuit() {
        Intent intent = new Intent(mActivity, MainActivity.class);
        startActivity(intent);
        activityManagerUtil.finishAllActivity();
    }


    //双击返回退出程序
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {//如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else { //两次按键小于2秒时，退出应用
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}
