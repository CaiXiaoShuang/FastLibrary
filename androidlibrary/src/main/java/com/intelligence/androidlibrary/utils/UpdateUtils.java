package com.intelligence.androidlibrary.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

public class UpdateUtils {
    /**
     * 获得地址成功
     * @param title 标题
     * @param message 内容
     * @param url  apk下载地址
     * @param forcedUpdate 是否强制下载 Y:是 N：否
     */
    public static void toVersionUpdate(final Context context,String title,String message,final String url,String forcedUpdate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("立即下载", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                downloadForWebView(context,url);
            }
        });
        if (TextUtils.equals(forcedUpdate, "Y")) {
            //强制更新
            builder.setCancelable(false);
        } else {
            builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setCancelable(true);
        }
        builder.show();
    }


    /**
     * 通过浏览器下载APK包
     *
     * @param context
     * @param url
     */
    public static void downloadForWebView(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }



}


