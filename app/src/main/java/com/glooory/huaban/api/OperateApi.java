package com.glooory.huaban.api;

import com.glooory.huaban.entity.PinsBean;
import com.glooory.huaban.module.board.FollowBoardOperateBean;
import com.glooory.huaban.module.imagedetail.GatherInfoBean;
import com.glooory.huaban.module.imagedetail.GatherResultBean;
import com.glooory.huaban.module.imagedetail.LikePinOperateBean;
import com.glooory.huaban.module.user.UserBoardSingleBean;
import com.glooory.huaban.util.Constant;

import retrofit2.http.DELETE;
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

    //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头

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

    //关注某个用户的操作
    //https://api.huaban.com/users/13643543/{follow or unfollow}
    @POST("users/{userId}/{operate}")
    Observable<Void> httpFollowUserService(@Header(Constant.AUTHORIZATION) String authorization,
                                           @Path("userId") String userId,
                                           @Path("operate") String operate);

    //关注某个画板
    //https:api.huaban.com/boards/{boardId}/{follow or unfollow}\
    @POST("boards/{boardId}/{operate}")
    Observable<FollowBoardOperateBean> httpFollowBoardService(@Header(Constant.AUTHORIZATION) String authorization,
                                                              @Path("boardId") int boardId,
                                                              @Path("operate") String operate);

    //新建画板
    //https://api.huaban.com/boards  body=category=类型&description=描述&title=标题
    @FormUrlEncoded
    @POST("boards/")
    Observable<UserBoardSingleBean> httpAddBoard(@Header(Constant.AUTHORIZATION) String authorization,
                                                 @Field("title") String title,
                                                 @Field("description") String des,
                                                 @Field("category") String category);

    //修改某个画板的信息
    //https://api.huaban.com/boards/29646779 category=photography&description=%E6%B7%BB%E5%8A%A0%E6%8F%8F%E8%BF%B0&title=%E6%B7%BB%E5%8A%A0
    @FormUrlEncoded
    @POST("boards/{boardId}")
    Observable<UserBoardSingleBean> httpEditBoardService(@Header(Constant.AUTHORIZATION) String authorization,
                                                         @Path("boardId") String boardId,
                                                         @Field("title") String title,
                                                         @Field("description") String des,
                                                         @Field("category") String category);

    //删除某个画板
    //https://api.huaban.com/boards/29653031 POST BODY= _method=DELETE
    @FormUrlEncoded
    @POST("boards/{boardId}")
    Observable<UserBoardSingleBean> httpDeleteBoardService(@Header(Constant.AUTHORIZATION) String authorization,
                                                           @Path("boardId") String boardId,
                                                           @Field("_method") String operate);

    //编辑修改某个采集的信息
    //https://api.huaban.com/pins/865002387?board_id=32026507&text=%E6%9D%A5%E8%87%AA%E7%9B%B8%E5%86%8C
    @FormUrlEncoded
    @POST("pins/{pinId}")
    Observable<PinsBean> httpEditPinService(@Header(Constant.AUTHORIZATION) String authorization,
                                            @Path("pinId") String pinId,
                                            @Field("board_id") String boardId,
                                            @Field("text") String des);

    //删除某个采集
    //https://api.huaban.com/pins/864657103
    @DELETE("pins/{pin_id}")
    Observable<Void> httpDeletePinService(@Header(Constant.AUTHORIZATION) String authorization,
                                    @Path("pin_id") String pinId);
}
