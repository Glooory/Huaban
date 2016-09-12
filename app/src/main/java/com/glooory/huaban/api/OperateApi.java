package com.glooory.huaban.api;

import com.glooory.huaban.module.imagedetail.LikePinOperateBean;
import com.glooory.huaban.util.Constant;

import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Glooory on 2016/9/12 0012 17:06.
 */
public interface OperateApi {

    //对某个采集的喜欢或者取消喜欢的操作
    //https:api.huaban.com/pins/12345664/like
    //https:api.huaban.com/pins/12345664/unlike
    @POST("pins/{pinId}/{operate}")
    Observable<LikePinOperateBean> httpLikePinService(@Header(Constant.AUTHORIZATION) String authorization,
                                                      @Path("pinId") int pinId,
                                                      @Path("operate") String operate);


}
