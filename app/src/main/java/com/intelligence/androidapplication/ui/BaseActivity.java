package com.intelligence.androidapplication.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.intelligence.androidapplication.retrofit.ApiStores;
import com.intelligence.androidapplication.retrofit.AppClient;
import com.intelligence.androidapplication.ui.main.fragment.BaseFragment;
import com.intelligence.androidlibrary.base.ActivityManagerUtil;
import com.intelligence.androidlibrary.rxjava.RxBus;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class BaseActivity extends AppCompatActivity{
    protected BaseFragment currentFragment;
    public Activity mActivity;
    public ActivityManagerUtil activityManagerUtil;
    public ApiStores apiStores;
    private CompositeSubscription mCompositeSubscription;
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        activityManagerUtil = ActivityManagerUtil.getInstance();
        activityManagerUtil.pushOneActivity(this);
        apiStores = AppClient.retrofit().create(ApiStores.class);
    }

    public String getStringById(int id) {
        return getResources().getString(id);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this);
        //结束Activity&从栈中移除该Activity
        activityManagerUtil.popOneActivity(this);
        onUnsubscribe();
    }


    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    public void rxBusObservers(Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        Subscription subscription = RxBus.getInstance()
                .toObserverable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        mCompositeSubscription.add(subscription);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                super.onBackPressed();//返回
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public ProgressDialog progressDialog;

    public ProgressDialog showProgressDialog() {
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("加载中");
        progressDialog.show();
        return progressDialog;
    }

    public ProgressDialog showProgressDialog(CharSequence message) {
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            // progressDialog.hide();会导致android.view.WindowLeaked
            progressDialog.dismiss();
        }
    }

    public void toastShow(CharSequence text) {
        Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show();
    }


    /**
     * 用Fragment替换视图
     *
     * @param resView        将要被替换掉的视图
     * @param targetFragment 用来替换的Fragment
     */
    public void changeFragment(int resView, BaseFragment targetFragment) {
        if (targetFragment.equals(currentFragment)) {
            return;
        }
        android.support.v4.app.FragmentTransaction transaction
                = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (!targetFragment.isAdded()) {
            transaction.add(resView, targetFragment, targetFragment.getClass().getName());
        }
        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
        }
        if (currentFragment != null && currentFragment.isVisible()) {
            transaction.hide(currentFragment);
        }
        currentFragment = targetFragment;
        transaction.commit();
    }

}
