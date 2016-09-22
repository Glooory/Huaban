package com.glooory.huaban.api;

import com.glooory.huaban.entity.PinsListBean;
import com.glooory.huaban.module.type.TypeUserListBean;
import com.glooory.huaban.module.user.UserBoardListBean;
import com.glooory.huaban.util.Constant;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Glooory on 2016/9/16 0016 20:50.
 */
public interface TypeApi {

    //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头

    //分类浏览的网络请求

    //请求该分类的采集
    //https://api.huaban.com/favourite/{type}/?limit=20
    @GET("favorite/{type}")
    Observable<PinsListBean> httpTypePinsService(@Header(Constant.AUTHORIZATION) String authorization,
                                                 @Path("type") String type,
                                                 @Query("limit") int limit);

    //请求该分类的采集
    //https://api.huaban.com/favorite/{type}/?limit=20&max=
    @GET("favorite/{type}")
    Observable<PinsListBean> httpTypePinsMaxService(@Header(Constant.AUTHORIZATION) String authorization,
                                                    @Path("type") String type,
                                                    @Query("max") int maxId,
                                                    @Query("limit") int limit);

    //请求该分类的相关画板
    //https://api.huaban.com/boards/favorite/{type}/?limit=20
    @GET("boards/favorite/{type}")
    Observable<UserBoardListBean> httpTypeBoardsService(@Header(Constant.AUTHORIZATION) String authorization,
                                                        @Path("type") String type,
                                                        @Query("limit") int limit);

    //请求该分类的相关画板
    //https://api.huaban.com/boards/favorite/{type}/?limit=20&max=
    @GET("boards/favorite/{type}")
    Observable<UserBoardListBean> httpTypeBoardsMaxService(@Header(Constant.AUTHORIZATION) String authorization,
                                                           @Path("type") String type,
                                                           @Query("max") int maxId,
                                                           @Query("limit") int limit);

    //请求该分类的相关的用户
    //https://api.huaban.com/users/favorite/{type}/?limit=20
    @GET("users/favorite/{type}")
    Observable<TypeUserListBean> httpTypeUsersService(@Header(Constant.AUTHORIZATION) String authorization,
                                                      @Path("type") String type,
                                                      @Query("limit") int limit);

    //请求该分类的相关的用户
    //https://api.huaban.com/users/favorite/{type}/?limit=20&max
    @GET("users/favorite/{type}")
    Observable<TypeUserListBean> httpTypeUsersMaxService(@Header(Constant.AUTHORIZATION) String authorization,
                                                         @Path("type") String type,
                                                         @Query("max") int maxSeq,
                                                         @Query("limit") int limit);
}
