package com.glooory.huaban.api;

import com.glooory.huaban.module.imagedetail.GatherInfoBean;
import com.glooory.huaban.module.imagedetail.GatherResultBean;
import com.glooory.huaban.module.imagedetail.LikePinOperateBean;
import com.glooory.huaban.util.Constant;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
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

    //采集图片前判断是否已经在自己的采集中
    //https:api.huaban.com/pins/134541447/repin?check=true
    @GET("pins/{viaId}/repin/")
    Observable<GatherInfoBean> httpGatheredInfoService(@Header(Constant.AUTHORIZATION) String authorization,
                                                       @Path("viaId") int viaId,
                                                       @Query("check") boolean check);

    //采集某个图片
    //https://api.huaban.com/pins/ body=board_id=17891564&text=描述内容&via=707423726
    @FormUrlEncoded
    @POST("pins/")
    Observable<GatherResultBean> httpGatherPinService(@Header(Constant.AUTHORIZATION) String authorization,
                                                      @Field("board_id") String boardId,
                                                      @Field("text") String des,
                                                      @Field("via") String pinsId);
}
