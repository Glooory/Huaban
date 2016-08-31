package com.glooory.huaban.api;

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

    @GET("all")
    Observable<PinsListBean> httpAllService(@Header(Constant.AUTHORIZATION) String authorization,
                                            @Query("limit") int limit);

    @GET("all")
    Observable<PinsListBean> httpAllMaxService(@Header(Constant.AUTHORIZATION) String authorization,
                                               @Query("max") int maxId,
                                               @Query("limit") int limit);

}
