package com.intelligence.androidapplication.retrofit;

/**
 * Created by Administrator
 * on 2017/8/24.
 */

public class BaseModel {

    private boolean isSuccess;
    private String message;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
