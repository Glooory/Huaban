package com.glooory.huaban.api;

import com.glooory.huaban.entity.FollowerBean;
import com.glooory.huaban.entity.PinsListBean;
import com.glooory.huaban.module.board.SimpleBoardInfoBean;
import com.glooory.huaban.util.Constant;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Glooory on 2016/9/7 0007 18:30.
 */
public interface BoardApi {

    //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头

    //根据boardId获取board信息
    //https:api.huaban.com/boards/boardId
    @GET("boards/{boardId}")
    Observable<SimpleBoardInfoBean> httpForBoardInfoService(@Header(Constant.AUTHORIZATION) String authorization,
                                                            @Path("boardId") int boardId);

    //根据画板的Id 获取采集
    //https:api.huaban.com/boards/1234566/pins?limit=20
    @GET("boards/{boardId}/pins")
    Observable<PinsListBean> httpForBoardPinService(@Header(Constant.AUTHORIZATION) String authorization,
                                                    @Path("boardId") int boardId,
                                                    @Query("limit") int limit);


    //https://api.huaban.com/boards/1234566/pins?max=123456&limit=20
    @GET("boards/{boardId}/pins")
    Observable<PinsListBean> httpForBoardPinsMaxService(@Header(Constant.AUTHORIZATION) String authorization,
                                                        @Path("boardId") int boardId,
                                                        @Query("max") int max,
                                                        @Query("limit") int limit);

    //获取某个画板的关注者
    // https://api.huaban.com/boards/1346566/followers?limit=20
    @GET("boards/{boardId}/followers")
    Observable<FollowerBean> httpForBoardFollowerService(@Header(Constant.AUTHORIZATION) String authorization,
                                                         @Path("boardId") int boardId,
                                                         @Query("limit") int limit);

    // https://api.huaban.com/boards/1346566/followers?limit=20
    @GET("boards/{boardId}/followers")
    Observable<FollowerBean> httpForBoardFollowerMaxService(@Header(Constant.AUTHORIZATION) String authorization,
                                                            @Path("boardId") int boardId,
                                                            @Query("max") int seq,
                                                            @Query("limit") int limit);
}
