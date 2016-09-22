package com.glooory.huaban.api;

import com.glooory.huaban.entity.BoardListInfoBean;
import com.glooory.huaban.entity.PinsListBean;
import com.glooory.huaban.module.login.UserInfoBean;
import com.glooory.huaban.module.user.UserBoardListBean;
import com.glooory.huaban.module.user.UserFollowingBean;
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

    //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头

    //https://api.huaban.com/users/15246080
    //获取个人信息
    @GET("users/{userId}")
    Observable<UserInfoBean> httpsUserInfoRx(@Header(Constant.AUTHORIZATION) String authorization,
                                             @Path("userId") String userId);


    //获取我的画板集合信息
    // https://api.huaban.com/last_board/?extra=recommend_tags
    @GET("last_boards/")
    Observable<BoardListInfoBean> httpsBoardListInfo(@Header(Constant.AUTHORIZATION) String authorization,
                                                     @Query("extra") String extra);


    // 用户的画板信息
    // https://api.huaban.com/users/12345678/boards?limit=20
    @GET("users/{userId}/boards")
    Observable<UserBoardListBean> httpUserBoardService(@Header(Constant.AUTHORIZATION) String mAuthorization,
                                                       @Path("userId") String userId,
                                                       @Query("limit") int limit);

    //https://api.huaban.com/users/12345678/boards?max=1123644&limit=20
    @GET("users/{userId}/boards")
    Observable<UserBoardListBean> httpUserBoardMaxService(@Header(Constant.AUTHORIZATION) String mAuthorization,
                                                          @Path("userId") String userId,
                                                          @Query("max") int max,
                                                          @Query("limit") int limit);


    //用户的所有采集信息
    //https:api.huaban.com/users/12345678/pins?limit=20
    @GET("users/{userId}/pins")
    Observable<PinsListBean> httpUserPinsService(@Header(Constant.AUTHORIZATION) String authorization,
                                                 @Path("userId") String userId,
                                                 @Query("limit") int limit);

    //https:api.huaban.com/users/12345678/pins?max=1234567&limit=20
    @GET("users/{userId}/pins")
    Observable<PinsListBean> httpUserPinsMaxService(@Header(Constant.AUTHORIZATION) String authorization,
                                                    @Path("userId") String userId,
                                                    @Query("max") int max,
                                                    @Query("limit") int limit);

    //用户所喜欢的采集
    //https://api.huaban.com/users/12345678/likes?limit=20
    @GET("users/{userId}/likes")
    Observable<PinsListBean> httpUserLikesService(@Header(Constant.AUTHORIZATION) String authorization,
                                                  @Path("userId") String userId,
                                                  @Query("limit") int limit);

    //https://api.huaban.com/users/12345678/likes?max=1234567&limit=20
    @GET("users/{userId}/likes")
    Observable<PinsListBean> httpUserLikesMaxService(@Header(Constant.AUTHORIZATION) String authorization,
                                                     @Path("userId") String userId,
                                                     @Query("max") int max,
                                                     @Query("limit") int limit);

    //用户所关注的用户
    //https:api.huaban.com/users/{userId}/following?limit=20
    @GET("users/{userId}/following")
    Observable<UserFollowingBean> httpUserFollowingService(@Header(Constant.AUTHORIZATION) String authorization,
                                                           @Path("userId") String userId,
                                                           @Query("limit") int limit);

    //用户所关注的用户
    //https:api.huaban.com/users/{userId}/following?max=1354654143&limit=20
    @GET("users/{userId}/following")
    Observable<UserFollowingBean> httpUserFollowingMaxService(@Header(Constant.AUTHORIZATION) String authorization,
                                                           @Path("userId") String userId,
                                                           @Query("max") int seq,
                                                           @Query("limit") int limit);

}


