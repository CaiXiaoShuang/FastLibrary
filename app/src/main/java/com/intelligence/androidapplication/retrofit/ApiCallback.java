package com.intelligence.androidapplication.retrofit;


import android.text.TextUtils;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public abstract class ApiCallback<M> extends Subscriber<M> {

    public abstract void onSuccess(M model);

    public abstract void onFailure(M model, String msg);

    public abstract void onFailure(String msg);

    public abstract void onFinish();


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        //LogUtil.d("onError=" + e.getMessage());
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            String msg = httpException.getMessage();
            //LogUtil.d("code=" + code);
            if (code == 504) {
                msg = "网络不给力";
            }
            if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试";
            }
            onFailure(msg);
        } else {
            if (!TextUtils.isEmpty(e.getMessage())) {
                onFailure("当前访问异常，请稍后再试");
            } else {
                onFailure(e.getMessage());
            }

        }
        onFinish();
    }

    @Override
    public void onNext(M model) {
        //LogUtil.d("onNext");
        if (model instanceof BaseModel) {
            BaseModel baseModel = (BaseModel) model;
            if (baseModel.isSuccess()) {
                onSuccess(model);
            } else {
                if (TextUtils.isEmpty(baseModel.getMessage())) {
                    onFailure(model, "当前访问异常，请稍后再试");
                } else {
                    onFailure(model, baseModel.getMessage());
                }
            }
        }
    }

    @Override
    public void onCompleted() {
        //LogUtil.d("onCompleted");
        onFinish();
    }


}
