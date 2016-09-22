package com.glooory.huaban.api;

import com.glooory.huaban.module.login.TokenBean;
import com.glooory.huaban.module.login.UserInfoBean;
import com.glooory.huaban.util.Constant;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Glooory on 2016/9/1 0001.
 */
public interface LoginApi {

    //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头

    //https 用户登录  的第一步
    // Authorization 报头一个固定的值 内容 grant_type=password&password=密码&username=账号
    @FormUrlEncoded
    @POST("https://huaban.com/oauth/access_token/")
    Observable<TokenBean> httpsTokenRx(@Header(Constant.AUTHORIZATION) String authorization,
                                       @Field("grant_type") String grant,
                                       @Field("username") String username,
                                       @Field("password") String password);

    //登录第二步  用上一步结果联网
    @GET("users/me")
    Observable<UserInfoBean> httpsUserRx(@Header(Constant.AUTHORIZATION) String authorization);

}
