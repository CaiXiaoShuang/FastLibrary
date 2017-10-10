package com.intelligence.androidapplication.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.intelligence.androidapplication.R;
import com.intelligence.androidapplication.event.PendingEvent;
import com.intelligence.androidapplication.parameter.MainParameter;
import com.intelligence.androidapplication.retrofit.ApiCallback;
import com.intelligence.androidapplication.ui.BaseActivity;
import com.intelligence.androidapplication.ui.main.fragment.HomeFragment;
import com.intelligence.androidapplication.ui.main.fragment.DeliveryFragment;
import com.intelligence.androidapplication.ui.main.fragment.MyFragment;
import com.intelligence.androidapplication.ui.main.fragment.OrderFragment;
import com.intelligence.androidapplication.ui.main.model.MainModel;
import com.intelligence.androidlibrary.bottomNavigationViews.BottomNavigationItem;
import com.intelligence.androidlibrary.bottomNavigationViews.BottomNavigationView;
import com.intelligence.androidlibrary.bottomNavigationViews.OnBottomNavigationItemClickListener;
import com.intelligence.androidlibrary.rxjava.RxBus;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements OnBottomNavigationItemClickListener {
    @Bind(R.id.bottomNavigation)
    BottomNavigationView bottomNavigationViews;
    private HomeFragment homeFragment;
    private DeliveryFragment deliveryFragment;
    private OrderFragment orderFragment;
    private MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        BottomNavigationItem item_main = new BottomNavigationItem("首页", getResources().getColor(R.color.cyan), R.mipmap.ic_tab_yes_1, R.mipmap.ic_tab_no_1);
        BottomNavigationItem item_delivery = new BottomNavigationItem("发货", getResources().getColor(R.color.cyan), R.mipmap.ic_tab_yes_2, R.mipmap.ic_tab_no_2);
        BottomNavigationItem item_order = new BottomNavigationItem("订单", getResources().getColor(R.color.cyan), R.mipmap.ic_tab_yes_3, R.mipmap.ic_tab_no_3);
        BottomNavigationItem item_my = new BottomNavigationItem("我的", getResources().getColor(R.color.cyan), R.mipmap.ic_tab_yes_4, R.mipmap.ic_tab_no_4);

        bottomNavigationViews.addTab(item_main);
        bottomNavigationViews.addTab(item_delivery);
        bottomNavigationViews.addTab(item_order);
        bottomNavigationViews.addTab(item_my);
        bottomNavigationViews.setItemActiveColorWithoutColoredBackground(getResources().getColor(R.color.cyan));
        bottomNavigationViews.isColoredBackground(false);
        bottomNavigationViews.setOnBottomNavigationItemClickListener(this);
        bottomNavigationViews.selectTab(0);
    }

    @Override
    public void onNavigationItemClick(int index) {
        switch (index) {
            case 0:
                if (null == homeFragment) {
                    homeFragment = HomeFragment.newInstance();
                }
                changeFragment(R.id.main_frame, homeFragment);
                break;
            case 1:
                if (null == deliveryFragment) {
                    deliveryFragment = DeliveryFragment.newInstance();
                }
                changeFragment(R.id.main_frame, deliveryFragment);
                break;

            case 2:
                if (null == orderFragment) {
                    orderFragment = OrderFragment.newInstance();
                }
                changeFragment(R.id.main_frame, orderFragment);
                break;
            case 3:
                if (null == myFragment) {
                    myFragment = MyFragment.newInstance();
                }
                changeFragment(R.id.main_frame, myFragment);
                break;

        }
    }






    //以下常用方法集成
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
