package com.intelligence.androidapplication.retrofit;


import android.support.compat.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppClient {
    public static Retrofit retrofit = null;

    public static Retrofit retrofit() {
        if (retrofit == null) {
            /**
             *设置缓存
             */
//            File cacheFile = new File(ShipperApplication.getContext().getExternalCacheDir(), "ShipperCache");
//            Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
//            Interceptor cacheInterceptor = new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request request = chain.request();
//                    if (!ShipperUtils.networkIsAvailable(ShipperApplication.getContext())) {
//                        /**
//                         * 1.  noCache  不使用缓存，全部走网络
//                         * 2.  noStore   不使用缓存，也不存储缓存
//                         * 3.  onlyIfCached 只使用缓存
//                         * 4.  maxAge  设置最大失效时间，失效则不使用 需要服务器配合
//                         * 5.  maxStale 设置最大失效时间，失效则不使用 需要服务器配合 感觉这两个类似 还没怎么弄清楚，清楚的同学欢迎留言
//                         * 6.  minFresh 设置有效时间，依旧如上
//                         * 7.  FORCE_NETWORK 只走网络
//                         * 8.  FORCE_CACHE 只走缓存
//                         */
//
//                        request = request.newBuilder()
//                                .cacheControl(CacheControl.FORCE_CACHE)
//                                .build();
//                        Log.d("wxl", "networkIsAvailable");
//                    }
//                    Response response = chain.proceed(request);
//                    if (ShipperUtils.networkIsAvailable(ShipperApplication.getContext())) {
//                        int maxAge = 0;
//                        // 有网络时 设置缓存超时时间0个小时
//                        response.newBuilder()
//                                .removeHeader("Shipper")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
//                                .header("Cache-Control", "public, max-age=" + maxAge)
//                                .build();
//                    } else {
//                        // 无网络时，设置超时为4周
//                        int maxStale = 60 * 60 * 24 * 28;
//                        response.newBuilder()
//                                .removeHeader("Shipper")
//                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                                .build();
//                    }
//                    return response;
//                }
//            };
            /**
             *  公共参数
             */
            Interceptor addQueryParameterInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Request request;
                    HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                            // Provide your custom parameter here
                            .addQueryParameter("platform", "android")
                            .addQueryParameter("version", "1.0.0")
                            .build();
                    request = originalRequest.newBuilder().url(modifiedUrl).build();

                    return chain.proceed(request);
                }
            };
            /**
             * 设置头
             */

            Interceptor headerInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Request.Builder requestBuilder = originalRequest.newBuilder()
                            .header("AppType", "TPOS")
                            .header("Content-Type", "application/json")
                            .header("Accept", "application/json")
                            .method(originalRequest.method(), originalRequest.body());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);

                }
            };
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
            }


            //设置cookie（http://stackoverflow.com/questions/24263921/how-to-implement-cookie-handling-on-android-using-okhttp）
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            builder.cookieJar(new JavaNetCookieJar(cookieManager));
            //设置缓存
            //builder.cache(cache).addInterceptor(cacheInterceptor);
            //公共参数
            //builder.addInterceptor(addQueryParameterInterceptor);
            //设置超时
            builder.connectTimeout(15, TimeUnit.SECONDS);
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);
            //以上设置结束，才能build(),不然设置白搭
            OkHttpClient okHttpClient = builder.build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiStores.API_SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)//构建OkHttpClient对象
                    .build();
        }
        return retrofit;

    }
}