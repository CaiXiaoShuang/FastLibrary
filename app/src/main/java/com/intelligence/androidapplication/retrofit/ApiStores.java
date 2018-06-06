package com.intelligence.androidapplication.retrofit;


import com.intelligence.androidapplication.parameter.MainParameter;
import com.intelligence.androidapplication.ui.main.model.MainModel;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiStores {
    //外网
    String API_SERVER_URL = "http://47.97.45.19:8080/api/UserAgreement/";
    //图片地址
    String API_IMAGE_URL = API_SERVER_URL + "base/file/downloadpic?imageId=";

    /**
     * @LoginParameter 请求参数
     * @MainModel 返回格式
     ***/
    @POST("InsertUserAgreement")
    @FormUrlEncoded
    Observable<MainModel> userLogin( @Field("strQuery") String strQuery);

}
