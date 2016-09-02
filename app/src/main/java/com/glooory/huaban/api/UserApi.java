package com.glooory.huaban.api;

import com.glooory.huaban.entity.BoardListInfoBean;
import com.glooory.huaban.module.login.UserInfoBean;
import com.glooory.huaban.util.Constant;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Glooory on 2016/9/1 0001.
 */
public interface UserApi {

    //https://api.huaban.com/users/15246080
    //获取个人信息
    @GET("users/{userId}")
    Observable<UserInfoBean> httpsUserInfoRx(@Header(Constant.AUTHORIZATION) String authorization,
                                             @Path("userId") String userId);


    //获取我的画板集合信息
    // https://api.huaban,com/last_board/?extra=recommend_tags
    @GET("last_boards/")
    Observable<BoardListInfoBean> httpsBoardListInfo(@Header(Constant.AUTHORIZATION) String authorization,
                                                   @Query("extra") String extra);


}
