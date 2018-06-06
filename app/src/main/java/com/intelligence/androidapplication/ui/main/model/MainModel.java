package com.intelligence.androidapplication.ui.main.model;

/**
 * Created by Administrator
 * on 2017/9/11.
 */

public class MainModel {
    private boolean exception;
    private String generateno;
    private String primarykey;
    private boolean timeout;
    private String url;
    private String ds;

    public boolean isException() {
        return exception;
    }

    public void setException(boolean exception) {
        this.exception = exception;
    }

    public String getGenerateno() {
        return generateno;
    }

    public void setGenerateno(String generateno) {
        this.generateno = generateno;
    }

    public String getPrimarykey() {
        return primarykey;
    }

    public void setPrimarykey(String primarykey) {
        this.primarykey = primarykey;
    }

    public boolean isTimeout() {
        return timeout;
    }

    public void setTimeout(boolean timeout) {
        this.timeout = timeout;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }
}
