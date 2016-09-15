package com.glooory.huaban.api;

import com.glooory.huaban.entity.ListPinsBean;
import com.glooory.huaban.entity.PinsListBean;
import com.glooory.huaban.util.Constant;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Glooory on 2016/8/27 0027.
 */
public interface AllApi {

    //获取最新的采集数据，展示在最新Fragment中
    //https:api.huaban.com/all?limit=20
    @GET("all")
    Observable<PinsListBean> httpAllService(@Header(Constant.AUTHORIZATION) String authorization,
                                            @Query("limit") int limit);

    //获取最新的采集数据，展示在最新Fragment中
    //https:api.huaban.com/all?limit=20&max={pinId}
    @GET("all")
    Observable<PinsListBean> httpAllMaxService(@Header(Constant.AUTHORIZATION) String authorization,
                                               @Query("limit") int limit,
                                               @Query("max") int max
    );

    //获取用户已经关注了得用户和画板的更新情况，展示在首页上
    //https:api.huaban.com/following?limit=20
    @GET("following")
    Observable<ListPinsBean> httpHomePinsService(@Header(Constant.AUTHORIZATION) String authorization,
                                                 @Query("limit") int limit);

    //获取用户已经关注了得用户和画板的更新情况，展示在首页上
    //https:api.huaban.com/following?max={pinId}&limit=20
    @GET("following")
    Observable<ListPinsBean> httpHomePinsMaxService(@Header(Constant.AUTHORIZATION) String authorization,
                                                    @Query("max") int maxId,
                                                    @Query("limit") int limit);

    //获取热门采集
    //https:api.huaban.com/popular?limit=20
    @GET("popular")
    Observable<ListPinsBean> httpPopularPinsService(@Header(Constant.AUTHORIZATION) String authorization,
                                                    @Query("limit") int limit);

    //获取热门采集
    //https:api.huaban.com/popular?max={maxId}&limit=20
    @GET("popular")
    Observable<ListPinsBean> httpPopularPinsMaxService(@Header(Constant.AUTHORIZATION) String authorization,
                                                       @Query("max") int maxId,
                                                       @Query("limit") int limit);

}
