package com.glooory.huaban.api;

import com.glooory.huaban.entity.PinsBean;
import com.glooory.huaban.entity.imagedetail.PinDetailBean;
import com.glooory.huaban.util.Constant;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Glooory on 2016/9/11 0011 13:56.
 */
public interface ImageDetailApi {

    //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头

    //获取某个采集的详细信息
    //https://api.huaban.com/pins/pinId
    @GET("pins/{pinId}")
    Observable<PinDetailBean> httpPinDetailService(@Header(Constant.AUTHORIZATION) String authorization,
                                                   @Path("pinId") int pinId);

    //根据pinId推荐相似的pin
    //https:api.huaban.com/pins/{pinId}/recommend/
    @GET("pins/{pinId}/recommend/")
    Observable<List<PinsBean>> httpRecommendService(@Header(Constant.AUTHORIZATION) String authorization,
                                                    @Path("pinId") int pinId,
                                                    @Query("page") int page,
                                                    @Query("per_page") int perPage);

}
