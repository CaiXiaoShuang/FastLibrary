package com.intelligence.androidapplication;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.compat.BuildConfig;

import com.intelligence.androidlibrary.utils.CrashHandlerUtil;
import com.intelligence.androidlibrary.utils.LogUtil;
import com.intelligence.androidlibrary.utils.SharePreferenceUtils;

public class AndroidApplication extends Application {

    private int count = 0;
    private String TAG = "AndroidApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化文件存储方式
        SharePreferenceUtils.init(this);
        //log打印设置
        LogUtil.setLog(BuildConfig.DEBUG);
        //Realm数据配置相关
        //崩溃处理
        CrashHandlerUtil crashHandlerUtil = CrashHandlerUtil.getInstance();
        crashHandlerUtil.init(this);
        crashHandlerUtil.setCrashTip("很抱歉，程序出现异常，即将退出！");
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityStopped(Activity activity) {
                LogUtil.d(TAG, activity + "onActivityStopped");
                count--;
                if (count == 0) {
                    LogUtil.d(TAG, ">>>>>>>>>>>>>>>>>>>切到后台  lifecycle");
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
                LogUtil.d(TAG, activity + "onActivityStarted");
                if (count == 0) {
                    LogUtil.d(TAG, ">>>>>>>>>>>>>>>>>>>切到前台  lifecycle");
                }
                count++;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                LogUtil.d(TAG, activity + "onActivitySaveInstanceState");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                LogUtil.d(TAG, activity + "onActivityResumed");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                LogUtil.d(TAG, activity + "onActivityPaused");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                LogUtil.d(TAG, activity + "onActivityDestroyed");
            }

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                LogUtil.d(TAG, activity + "onActivityCreated");
            }
        });
    }
}


