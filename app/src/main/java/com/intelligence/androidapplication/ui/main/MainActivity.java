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
import com.intelligence.androidapplication.ui.main.fragment.DeliveryFragment;
import com.intelligence.androidapplication.ui.main.fragment.HomeFragment;
import com.intelligence.androidapplication.ui.main.fragment.MyFragment;
import com.intelligence.androidapplication.ui.main.fragment.OrderFragment;
import com.intelligence.androidapplication.ui.main.model.MainModel;
import com.intelligence.androidapplication.utils.CommonDialog;
import com.intelligence.androidlibrary.bottomNavigationViews.BottomNavigationItem;
import com.intelligence.androidlibrary.bottomNavigationViews.BottomNavigationView;
import com.intelligence.androidlibrary.bottomNavigationViews.OnBottomNavigationItemClickListener;
import com.intelligence.androidlibrary.rxjava.RxBus;


import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements OnBottomNavigationItemClickListener {
    @BindView(R.id.bottomNavigation)
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
        btnLogin();
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
        //开始请求
        addSubscription(apiStores.userLogin("%22Content%22:%22%20%20%20%20%E6%9C%AC%E4%BA%BA%E5%B7%B2%E7%BB%8F%E4%BA%86%E8%A7%A3%E6%B1%9F%E8%8B%8F%E8%89%BE%E5%85%B0%E5%BE%97%E8%90%A5%E5%85%BB%E5%93%81%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8%EF%BC%88%E4%B8%8B%E7%A7%B0%E2%80%9C%E8%89%BE%E5%85%B0%E5%BE%97%E2%80%9D%EF%BC%89%E6%AD%A4%E6%AC%A1%E4%B8%BE%E5%8A%9E%E7%9A%84%E5%87%8F%E8%84%82%E7%BE%8E%E5%9E%8B%E8%AE%AD%E7%BB%83%E8%90%A5%EF%BC%88%E4%B8%8B%E7%A7%B0%E2%80%9C%E8%AE%AD%E7%BB%83%E8%90%A5%E2%80%9D%EF%BC%89%E7%9A%84%E7%9B%B8%E5%85%B3%E7%9F%A5%E8%AF%86%E4%BB%A5%E5%8F%8A%E6%BD%9C%E5%9C%A8%E9%A3%8E%E9%99%A9%EF%BC%8C%E5%B9%B6%E5%AE%8C%E5%85%A8%E6%B8%85%E6%A5%9A%E4%BA%86%E8%AE%AD%E7%BB%83%E8%90%A5%E7%9A%84%E6%B4%BB%E5%8A%A8%E8%A7%84%E5%88%99%EF%BC%8C%E6%9C%AC%E4%BA%BA%E6%84%BF%E6%84%8F%E5%8F%82%E4%B8%8E%E6%AD%A4%E6%AC%A1%E6%B4%BB%E5%8A%A8%EF%BC%8C%E5%B9%B6%E8%87%AA%E6%84%BF%E4%BD%9C%E5%87%BA%E5%A6%82%E4%B8%8B%E6%89%BF%E8%AF%BA%EF%BC%9A\\n1.%20%E6%9C%AC%E4%BA%BA%E6%89%BF%E8%AF%BA%E6%B2%A1%E6%9C%89%E9%9A%90%E7%9E%92%E6%9C%8D%E7%94%A8%E8%8D%AF%E7%89%A9%E6%88%96%E7%96%BE%E7%97%85%EF%BC%8C%E5%B9%B6%E7%9F%A5%E6%99%93%E6%9C%8D%E7%94%A8%E8%8D%AF%E7%89%A9%E6%88%96%E6%82%A3%E7%97%85%E6%9C%9F%E9%97%B4%E5%8F%82%E5%8A%A0%E6%9C%AC%E9%A1%B9%E7%9B%AE%E7%9A%84%E9%A3%8E%E9%99%A9%E5%8F%8A%E6%B3%A8%E6%84%8F%E4%BA%8B%E9%A1%B9%EF%BC%8C%E5%A6%82%E6%9C%89%E4%B8%8D%E9%80%82%E4%BC%9A%E7%AB%8B%E5%8D%B3%E4%B8%AD%E6%AD%A2%EF%BC%8C%E5%BF%85%E8%A6%81%E6%97%B6%E4%BC%9A%E5%8F%8A%E6%97%B6%E5%B0%B1%E5%8C%BB%EF%BC%8C%E5%9B%A0%E7%A7%81%E8%87%AA%E6%9C%8D%E7%94%A8%E8%8D%AF%E7%89%A9%E6%88%96%E9%9A%90%E7%9E%92%E7%96%BE%E7%97%85%E8%80%8C%E5%AF%BC%E8%87%B4%E7%9A%84%E5%90%84%E9%A1%B9%E4%BC%A4%E5%AE%B3%EF%BC%8C%E4%B8%8E%E4%B8%BE%E5%8A%9E%E6%96%B9%E6%97%A0%E5%85%B3%E3%80%82\\n2.%20%E6%9C%AC%E4%BA%BA%E6%89%BF%E8%AF%BA%E5%B0%86%E5%9C%A8%E6%B4%BB%E5%8A%A8%E5%BC%80%E5%A7%8B%E5%90%8E%E7%9A%8412%E5%91%A8%E5%86%85%EF%BC%8C%E4%B8%A5%E6%A0%BC%E6%8C%89%E7%85%A7%E6%B4%BB%E5%8A%A8%E4%B8%BE%E5%8A%9E%E6%96%B9%E7%9A%84%E8%A6%81%E6%B1%82%E6%8C%89%E6%97%B6%E6%8C%89%E9%87%8F%E9%A3%9F%E7%94%A8%E4%BA%A7%E5%93%81%EF%BC%8C%E6%8B%8D%E6%91%84%E5%B9%B6%E4%B8%8A%E4%BC%A0%E5%A5%B6%E6%98%94%E7%85%A7%E7%89%87%E3%80%81%E5%8D%88%E9%A5%AD%E7%85%A7%E7%89%87%E5%9C%A8%E5%B9%B3%E5%8F%B0%E4%B8%AD%E8%BF%9B%E8%A1%8C%E6%89%93%E5%8D%A1%EF%BC%8C%E6%AF%8F%E5%91%A8%E6%89%93%E5%8D%A1%E6%AC%A1%E6%95%B0%E4%B8%8D%E5%BE%97%E5%B0%91%E4%BA%8E18%E6%AC%A1%E3%80%82%E7%B4%AF%E8%AE%A1%E4%B8%89%E6%AC%A1%E5%91%A8%E6%89%93%E5%8D%A1%E6%AC%A1%E6%95%B0%E5%B0%91%E4%BA%8E18%E6%AC%A1%EF%BC%8C%E5%B0%86%E8%A7%86%E4%BD%9C%E8%87%AA%E6%84%BF%E9%80%80%E5%87%BA%E8%AE%AD%E7%BB%83%E8%90%A5%EF%BC%8C%E8%AE%AD%E7%BB%83%E8%BF%9B%E7%A8%8B%E4%B8%AD%E6%AD%A2%E3%80%82\\n3.%20%E6%9C%AC%E4%BA%BA%E6%89%BF%E8%AF%BA%E7%A7%AF%E6%9E%81%E5%8F%82%E5%8A%A0%E4%B8%BE%E5%8A%9E%E6%96%B9%E6%AF%8F%E5%91%A8%E4%B8%BE%E8%A1%8C%E7%9A%84%E5%AE%9A%E6%97%B6%E5%AE%9A%E7%82%B9%E4%BD%93%E9%87%8D%E6%B5%8B%E8%AF%95%E8%AF%84%E6%AF%94%E6%B4%BB%E5%8A%A8%EF%BC%8C%E9%99%A4%E9%9D%9E%E7%89%B9%E6%AE%8A%E5%8E%9F%E5%9B%A0%EF%BC%8C%E4%B8%8D%E5%BE%97%E8%AF%B7%E5%81%87%E3%80%82%E7%B4%AF%E8%AE%A1%E4%B8%89%E6%AC%A1%E7%BC%BA%E5%B8%AD%E7%A7%B0%E9%87%8D%E6%B4%BB%E5%8A%A8%E5%B0%86%E8%A7%86%E4%BD%9C%E8%87%AA%E6%84%BF%E9%80%80%E5%87%BA%E8%AE%AD%E7%BB%83%E8%90%A5%EF%BC%8C%E8%AE%AD%E7%BB%83%E8%BF%9B%E7%A8%8B%E4%B8%AD%E6%AD%A2%E3%80%82\\n4.%20%E6%9C%AC%E4%BA%BA%E5%90%8C%E6%84%8F%E8%87%AA%E6%84%BF%E9%80%80%E5%87%BA%E8%AE%AD%E7%BB%83%E8%90%A5%EF%BC%88%E6%88%96%E8%A7%86%E4%BD%9C%E8%87%AA%E6%84%BF%E9%80%80%E5%87%BA%E8%AE%AD%E7%BB%83%E8%90%A5%EF%BC%89%E5%90%8E%EF%BC%8C%E7%BC%B4%E7%BA%B3%E7%9A%84%E6%8A%A5%E5%90%8D%E8%B4%B9%E4%B8%8D%E4%BA%88%E9%80%80%E8%BF%98%EF%BC%8C%E5%B9%B6%E4%B8%94%E4%B8%BE%E5%8A%9E%E6%96%B9%E4%B8%8D%E5%86%8D%E7%BB%A7%E7%BB%AD%E5%8F%91%E6%94%BE%E8%AE%AD%E7%BB%83%E8%90%A5%E7%9A%84%E7%9B%B8%E5%85%B3%E9%85%8D%E5%A5%97%E4%BA%A7%E5%93%81%E3%80%82\\n5.%20%E6%9C%AC%E4%BA%BA%E6%89%BF%E8%AF%BA%EF%BC%9A%E6%8E%88%E6%9D%83%E8%89%BE%E5%85%B0%E5%BE%97%E5%9C%A8%E7%BD%91%E7%BB%9C%E3%80%81%E7%94%B5%E8%A7%86%E3%80%81%E5%B9%B3%E9%9D%A2%E7%AD%89%E5%AA%92%E4%BD%93%E4%BD%BF%E7%94%A8%E6%9C%AC%E4%BA%BA%E5%8F%82%E4%B8%8E%E8%AE%AD%E7%BB%83%E8%90%A5%E7%9A%84%E9%9B%86%E4%BD%93%E6%B4%BB%E5%8A%A8%E7%85%A7%E7%89%87%EF%BC%8C%E5%B9%B6%E4%B8%8D%E4%BC%9A%E5%9B%A0%E4%B8%AA%E4%BA%BA%E8%82%96%E5%83%8F%E6%9D%83%E4%BA%8B%E5%AE%9C%E4%B8%8E%E8%89%BE%E5%85%B0%E5%BE%97%E6%9C%89%E4%BB%BB%E4%BD%95%E6%B3%95%E5%BE%8B%E7%BA%A0%E7%BA%B7%E3%80%82\\n6.%20%E6%9C%AC%E4%BA%BA%E7%9F%A5%E6%99%93%E5%9C%A8%E8%AE%AD%E7%BB%83%E5%BC%80%E5%A7%8B%E4%B8%80%E5%91%A8%E5%86%85%EF%BC%8C%E5%A6%82%E6%9C%89%E4%B8%8D%E9%80%82%EF%BC%8C%E5%8F%AF%E4%BB%A5%E7%94%B3%E8%AF%B7%E9%80%80%E5%87%BA%E6%AF%94%E8%B5%9B%EF%BC%8C%E4%BD%86%E5%BF%85%E9%A1%BB%E4%BA%A4%E5%9B%9E%E8%AE%AD%E7%BB%83%E8%90%A5%E9%85%8D%E5%A5%97%E4%BA%A7%E5%93%81%EF%BC%8C%E6%89%8D%E8%83%BD%E9%80%80%E5%9B%9E%E6%8A%A5%E5%90%8D%E8%B4%B9%E3%80%82%E8%B6%85%E8%BF%87%E4%B8%80%E5%91%A8%E9%80%80%E5%87%BA%E8%AE%AD%E7%BB%83%EF%BC%8C%E6%8A%A5%E5%90%8D%E8%B4%B9%E4%B8%8D%E4%BA%88%E9%80%80%E8%BF%98%E3%80%82\\n%22,%22UserID%22:10655,%22Type%22:%22124%22"),
                new ApiCallback<MainModel>() {
                    @Override
                    public void onSuccess(MainModel model) {
                        //成功请求内容
                        toastShow("请求成功");
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
